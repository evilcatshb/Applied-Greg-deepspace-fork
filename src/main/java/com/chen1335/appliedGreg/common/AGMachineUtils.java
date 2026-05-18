package com.chen1335.appliedGreg.common;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;

import java.util.function.BiFunction;

public class AGMachineUtils {
    public static MachineDefinition registerSingleTieredMachines(GTRegistrate registrate,
                                                                 String name,
                                                                 BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                                 BiFunction<Integer, MachineBuilder<MachineDefinition, ?>, MachineDefinition> builder,
                                                                 int tier) {

        var register = registrate
                .machine(name, holder -> factory.apply(holder, tier))
                .tier(tier);
        return builder.apply(tier, register);
    }
}
