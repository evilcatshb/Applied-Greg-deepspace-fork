package com.chen1335.appliedGreg.mixins.gtm;

import com.chen1335.appliedGreg.mixinHooks.gtm.PowerSubstationMachineAccessor;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PowerSubstationMachine.class, remap = false)
public abstract class PowerSubstationMachineMixin implements PowerSubstationMachineAccessor {
    @Shadow
    private long netOutLastSec;

    @Shadow

    private long netInLastSec;

    @Accessor("energyBank")
    public abstract PowerSubstationMachine.PowerStationEnergyBank AG$getEnergyBank();

    @Accessor("tickSubscription")
    public abstract ConditionalSubscriptionHandler AG$getTickSubscription();

    @Unique
    public void AG$addNetOutLastSec(long amount) {
        netOutLastSec = netOutLastSec + amount;
    }

    @Unique
    public void AG$addNetInLastSec(long amount) {
        netInLastSec = netInLastSec + amount;
    }
}
