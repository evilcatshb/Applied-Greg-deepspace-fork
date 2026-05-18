package com.chen1335.appliedGreg.mixinHooks.gtm;

import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.PowerSubstationMachine;


public interface PowerSubstationMachineAccessor {

    PowerSubstationMachine.PowerStationEnergyBank AG$getEnergyBank();

    ConditionalSubscriptionHandler AG$getTickSubscription();

    void AG$addNetOutLastSec(long amount);


    void AG$addNetInLastSec(long amount);
}
