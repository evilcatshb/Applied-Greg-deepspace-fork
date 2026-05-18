package com.chen1335.appliedGreg.definitions;

import com.chen1335.appliedGreg.AppliedGreg;
import com.chen1335.appliedGreg.common.AGMachineUtils;
import com.chen1335.appliedGreg.machine.MEEnergyHatchPartMachine;
import com.chen1335.appliedGreg.machine.MESubstationStorageBusPartMachine;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FORMED;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.ALL_TIERS;


public class AGMachines {
    static {
        AppliedGreg.REGISTRATE.creativeModeTab(() -> AppliedGreg.TAB);
    }

    public static MachineDefinition[] ME_ENERGY_INPUT_HATCH = GTMachineUtils.registerTieredMachines(
            AppliedGreg.REGISTRATE,
            "me_energy_input_hatch",
            (holder, tier) -> new MEEnergyHatchPartMachine(holder, tier, IO.IN, 2),
            (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue(GTValues.VNF[tier] + " ME Energy Hatch")
                    .modelProperty(IS_FORMED, false)
                    .abilities(PartAbility.INPUT_ENERGY)
                    .tooltips(Component.translatable("tooltip.applied_greg.me_energy_input_hatch"))
                    .overlayTieredHullModel(AppliedGreg.makeId("block/machine/part/" + "me_energy_input_hatch"))
                    .register(),
            ALL_TIERS
    );
    public static MachineDefinition[] ME_ENERGY_OUTPUT_HATCH = GTMachineUtils.registerTieredMachines(
            AppliedGreg.REGISTRATE,
            "me_energy_output_hatch",
            (holder, tier) -> new MEEnergyHatchPartMachine(holder, tier, IO.OUT, 2),
            (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue(GTValues.VNF[tier] + " ME Dynamo Hatch")
                    .modelProperty(IS_FORMED, false)
                    .abilities(PartAbility.OUTPUT_ENERGY)
                    .tooltips(Component.translatable("tooltip.applied_greg.me_energy_output_hatch"))
                    .overlayTieredHullModel(AppliedGreg.makeId("block/machine/part/" + "me_energy_output_hatch"))
                    .register(),
            ALL_TIERS
    );

    public static MachineDefinition ME_POWER_SUBSTATION_STORAGE_BUS = AGMachineUtils.registerSingleTieredMachines(
            AppliedGreg.REGISTRATE,
            "me_power_substation_storage_bus",
            (holder, tier) -> new MESubstationStorageBusPartMachine(holder),
            (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue("ME Power Substation Storage Bus")
                    .modelProperty(IS_FORMED, false)
                    .abilities(PartAbility.SUBSTATION_INPUT_ENERGY, PartAbility.SUBSTATION_OUTPUT_ENERGY)
                    .tooltips(Component.translatable("tooltip.applied_greg.me_energy_output_hatch"))
                    .overlayTieredHullModel(AppliedGreg.makeId("block/machine/part/" + "me_energy_output_hatch"))
                    .register(),
            GTValues.LuV
    );


    public static void init() {

    }
}
