package com.chen1335.appliedGreg.machine;

import appeng.api.config.Actionable;
import appeng.api.networking.IGridNodeListener;
import appeng.api.networking.IManagedGridNode;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.MEStorage;
import com.chen1335.appliedGreg.api.EUEnergyKey;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.trait.GridNodeHolder;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * LV ME 能源仓 - 从AE网络提取EU能量并为GT多方块机器供电
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MEEnergyHatchPartMachine extends EnergyHatchPartMachine implements IGridConnectedMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            MEEnergyHatchPartMachine.class, EnergyHatchPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    protected final GridNodeHolder nodeHolder;

    @DescSynced
    protected boolean isOnline;

    public void setOnline(boolean online) {
        this.isOnline = online;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    protected final IActionSource actionSource;

    private static final long MAX_TRANSFER_PER_TICK = GTValues.V[GTValues.LV];

    protected TickableSubscription autoIOSubs;

    public MEEnergyHatchPartMachine(IMachineBlockEntity holder, int tier, IO io, int amperage) {
        super(holder, tier, io, amperage);
        this.nodeHolder = createNodeHolder();
        this.actionSource = IActionSource.ofMachine(nodeHolder.getMainNode()::getNode);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected GridNodeHolder createNodeHolder() {
        return new GridNodeHolder(this);
    }

    @Override
    public IManagedGridNode getMainNode() {
        return nodeHolder.getMainNode();
    }

    @Override
    public void onMainNodeStateChanged(IGridNodeListener.State reason) {
        IGridConnectedMachine.super.onMainNodeStateChanged(reason);
        updateSubscription();
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateSubscription();
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if (autoIOSubs != null) {
            autoIOSubs.unsubscribe();
            autoIOSubs = null;
        }
    }

    protected void updateSubscription() {
        if (shouldSubscribe()) {
            autoIOSubs = subscribeServerTick(autoIOSubs, this::autoIO);
        } else if (autoIOSubs != null) {
            autoIOSubs.unsubscribe();
            autoIOSubs = null;
        }
    }

    protected boolean shouldSubscribe() {
        return isWorkingEnabled() && isOnline();
    }

    protected void autoIO() {
        if (!isWorkingEnabled() || !isOnline()) {
            return;
        }

        var grid = getMainNode().getGrid();
        if (grid == null) {
            return;
        }

        MEStorage networkInv = grid.getStorageService().getInventory();
        long energyStored = energyContainer.getEnergyStored();
        if (io == IO.IN) {
            long energyNeeded = energyContainer.getEnergyCapacity() - energyStored;
            if (energyNeeded <= 0) {
                return;
            }

            long maxTransfer = Math.min(energyNeeded, GTValues.V[tier] * amperage);
            long extracted = networkInv.extract(EUEnergyKey.INSTANCE, maxTransfer, Actionable.MODULATE, actionSource);

            if (extracted > 0) {
                energyContainer.addEnergy(extracted);
            }
        } else {
            if (energyStored <= 0) {
                return;
            }
            long maxTransfer = Math.min(energyStored, GTValues.V[tier] * amperage);
            long extracted = networkInv.insert(EUEnergyKey.INSTANCE, maxTransfer, Actionable.MODULATE, actionSource);

            if (extracted > 0) {
                energyContainer.removeEnergy(extracted);
            }
        }
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }
}
