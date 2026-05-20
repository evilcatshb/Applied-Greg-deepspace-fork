package com.chen1335.appliedGreg.machine;

import appeng.api.config.Actionable;
import appeng.api.networking.IManagedGridNode;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.IStorageMounts;
import appeng.api.storage.IStorageProvider;
import appeng.api.storage.MEStorage;
import com.chen1335.appliedGreg.api.EUEnergyKey;
import com.chen1335.appliedGreg.mixinHooks.gtm.PowerSubstationMachineAccessor;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.trait.GridNodeHolder;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class MESubstationStorageBusPartMachine extends MultiblockPartMachine implements IStorageProvider, IGridConnectedMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(MESubstationStorageBusPartMachine.class,
            MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    protected boolean isOnline;
    protected final GridNodeHolder nodeHolder;
    private PowerSubstationMachineAccessor powerSubstationAccessor;

    private PowerSubstationMachine powerSubstationMachine;
    private final PowerSubstationMEStorage storage = new PowerSubstationMEStorage(this);


    public MESubstationStorageBusPartMachine(IMachineBlockEntity holder) {
        super(holder);
        nodeHolder = new GridNodeHolder(this);
        getMainNode().addService(IStorageProvider.class, this);
    }

    @Override
    public boolean canShared() {
        return false;
    }

    @Override
    public void addedToController(@NotNull IMultiController controller) {
        if (controller instanceof PowerSubstationMachine machine) {
            this.powerSubstationMachine = machine;
            this.powerSubstationAccessor = (PowerSubstationMachineAccessor) powerSubstationMachine;
            this.powerSubstationAccessor.AG$getTickSubscription().updateSubscription();
        }
        super.addedToController(controller);
    }

    private boolean canUse() {
        RecipeLogic.Status status = powerSubstationMachine.recipeLogic.getStatus();
        return powerSubstationMachine != null && powerSubstationMachine.isFormed() && powerSubstationMachine.isWorkingEnabled();
    }

    private PowerSubstationMachine.PowerStationEnergyBank getEnergyBank() {
        if (powerSubstationAccessor == null) {
            return null;
        }
        return powerSubstationAccessor.AG$getEnergyBank();
    }

    @Override
    public void mountInventories(IStorageMounts storageMounts) {
        storageMounts.mount(storage);
    }

    @Override
    public IManagedGridNode getMainNode() {
        return nodeHolder.getMainNode();
    }

    @Override
    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public void setOnline(boolean online) {
        isOnline = online;
        if (online) {
            IStorageProvider.requestUpdate(getMainNode());
        }
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }

    protected static class PowerSubstationMEStorage implements MEStorage {
        private final MESubstationStorageBusPartMachine machine;

        private void addNetOutLastSec(long amount) {
            if (machine.powerSubstationAccessor != null) {
                machine.powerSubstationAccessor.AG$addNetOutLastSec(amount);
            }
        }

        private void addNetInLastSec(long amount) {
            if (machine.powerSubstationAccessor != null) {
                machine.powerSubstationAccessor.AG$addNetInLastSec(amount);
            }
        }

        public PowerSubstationMEStorage(MESubstationStorageBusPartMachine machine) {
            this.machine = machine;
        }

        @Override
        public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
            PowerSubstationMachine.PowerStationEnergyBank energyBank = machine.getEnergyBank();
            if (what == EUEnergyKey.INSTANCE && energyBank != null && this.machine.isFormed() && machine.canUse()) {
                BigInteger stored = energyBank.getStored();
                long extracted = stored.min(BigInteger.valueOf(amount)).longValue();
                if (mode == Actionable.MODULATE) {
                    energyBank.drain(extracted);
                    addNetOutLastSec(extracted);
                }
                return extracted;
            }
            return MEStorage.super.extract(what, amount, mode, source);
        }

        @Override
        public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
            PowerSubstationMachine.PowerStationEnergyBank energyBank = machine.getEnergyBank();
            if (what == EUEnergyKey.INSTANCE && energyBank != null && this.machine.isFormed() && machine.canUse()) {
                BigInteger canInsert = energyBank.getCapacity().subtract(energyBank.getStored());
                long inserted = canInsert.min(BigInteger.valueOf(amount)).longValue();
                if (mode == Actionable.MODULATE) {
                    energyBank.fill(inserted);
                    addNetInLastSec(inserted);
                }
                return inserted;
            }
            return MEStorage.super.insert(what, amount, mode, source);
        }

        @Override
        public Component getDescription() {
            return Component.literal("Power Substation");
        }

        @Override
        public void getAvailableStacks(KeyCounter out) {
            PowerSubstationMachine.PowerStationEnergyBank energyBank = machine.getEnergyBank();
            if (energyBank != null && this.machine.isFormed() && machine.canUse()) {
                out.add(EUEnergyKey.INSTANCE, energyBank.getStored().longValue());
            }
        }
    }
}
