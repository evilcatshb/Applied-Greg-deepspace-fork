package com.chen1335.appliedGreg.machine;

import appeng.api.config.Actionable;
import appeng.api.networking.IGridNodeListener;
import appeng.api.networking.IManagedGridNode;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.MEStorage;
import com.chen1335.appliedGreg.api.EUEnergyKey;
import com.chen1335.appliedGreg.common.AGMachineModelProperties;
import com.chen1335.appliedGreg.mixins.gtceu.DiodePartMachineAccessor;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.common.machine.multiblock.part.DiodePartMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.trait.GridNodeHolder;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.UpdateListener;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MEDiodePartMachine extends DiodePartMachine implements IGridConnectedMachine {
    private final IActionSource actionSource;
    protected boolean isOnline;
    protected final GridNodeHolder nodeHolder;
    protected TickableSubscription autoIOSubs;

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(MEDiodePartMachine.class,
            DiodePartMachine.MANAGED_FIELD_HOLDER);

    @DescSynced
    @Persisted(key = "io_mode")
    @UpdateListener(methodName = "onIoUpdated")
    private String io = "out";

    public MEDiodePartMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier);
        nodeHolder = new GridNodeHolder(this);
        this.actionSource = IActionSource.ofMachine(nodeHolder.getMainNode()::getNode);
    }


    @Override
    protected InteractionResult onScrewdriverClick(Player playerIn, InteractionHand hand, Direction gridSide, BlockHitResult hitResult) {
        if (io.equals("out")) {
            io = "in";
        } else {
            io = "out";
        }
        if (!isRemote()) {
            this.scheduleRenderUpdate();
            if (Objects.equals(io, "out")) {
                playerIn.sendSystemMessage(Component.translatable("applied_greg.machine.me_diode.out"));
            } else {
                playerIn.sendSystemMessage(Component.translatable("applied_greg.machine.me_diode.in"));
            }
        }
        if (!playerIn.level().isClientSide) {
            reinitializeEnergyContainer();
            notifyBlockUpdate();
            markDirty();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onMainNodeStateChanged(IGridNodeListener.State reason) {
        IGridConnectedMachine.super.onMainNodeStateChanged(reason);
        updateSubscription();
    }

    @Override
    protected void reinitializeEnergyContainer() {
        super.reinitializeEnergyContainer();
        long tierVoltage = GTValues.V[getTier()];
        if (io == null || io.equals("out")) {
            this.energyContainer.resetBasicInfo(tierVoltage * MAX_AMPS * 2, tierVoltage, 0, tierVoltage, getAmp());
            this.energyContainer.setSideOutputCondition(s -> s != getFrontFacing());
        } else {
            this.energyContainer.resetBasicInfo(tierVoltage * MAX_AMPS * 2, tierVoltage, getAmp(), tierVoltage, 0);
            this.energyContainer.setSideInputCondition(s -> s != getFrontFacing());
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateSubscription();
    }

    private int getAmp() {
        return ((DiodePartMachineAccessor) this).AG$getAmps();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected void updateSubscription() {
        if (shouldSubscribe()) {
            autoIOSubs = subscribeServerTick(autoIOSubs, this::autoIO);
        } else if (autoIOSubs != null) {
            autoIOSubs.unsubscribe();
            autoIOSubs = null;
        }
    }

    protected void autoIO() {
        if (!isOnline()) {
            return;
        }

        var grid = getMainNode().getGrid();
        if (grid == null) {
            return;
        }

        MEStorage networkInv = grid.getStorageService().getInventory();
        if (networkInv == null) {
            return;
        }
        int amp = getAmp();
        long energyStored = energyContainer.getEnergyStored();
        if (io.equals("out")) {
            long energyNeeded = energyContainer.getEnergyCapacity() - energyStored;
            if (energyNeeded <= 0) {
                return;
            }

            long maxTransfer = Math.min(energyNeeded, GTValues.V[tier] * amp);
            long extracted = networkInv.extract(EUEnergyKey.INSTANCE, maxTransfer, Actionable.MODULATE, actionSource);

            if (extracted > 0) {
                energyContainer.addEnergy(extracted);
            }
        } else {
            if (energyStored <= 0) {
                return;
            }
            long maxTransfer = Math.min(energyStored, GTValues.V[tier] * amp);
            long extracted = networkInv.insert(EUEnergyKey.INSTANCE, maxTransfer, Actionable.MODULATE, actionSource);

            if (extracted > 0) {
                energyContainer.removeEnergy(extracted);
            }
        }
    }


    @Override
    public IManagedGridNode getMainNode() {
        return nodeHolder.getMainNode();
    }

    protected boolean shouldSubscribe() {
        return isWorkingEnabled() && isOnline();
    }

    @Override
    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void onIoUpdated(String newValue, String oldValue) {
        this.scheduleRenderUpdate();
    }

    @Override
    public void scheduleRenderUpdate() {
        if (!isRemote()) {
            setRenderState(getRenderState().setValue(AGMachineModelProperties.IS_DIODE_OUT, io.equals("out")));
        }
        super.scheduleRenderUpdate();
    }
}
