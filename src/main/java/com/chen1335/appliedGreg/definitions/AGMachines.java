package com.chen1335.appliedGreg.definitions;

import com.chen1335.appliedGreg.AppliedGreg;
import com.chen1335.appliedGreg.machine.MEEnergyHatchPartMachine;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.common.registry.GTRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FORMED;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.ALL_TIERS;


public class AGMachines {


    public static MachineDefinition[] ME_ENERGY_INPUT_HATCH = GTMachineUtils.registerTieredMachines(
            AppliedGreg.REGISTRATE,
            "me_energy_input_hatch",
            (holder, tier) -> new MEEnergyHatchPartMachine(holder, tier, IO.IN, 2),
            (tier, builder) -> builder
                    .langValue(GTValues.VNF[tier] + " ME Energy Hatch")
                    .rotationState(RotationState.ALL)
                    .modelProperty(IS_FORMED, false)
                    .abilities(PartAbility.INPUT_ENERGY)
                    .tooltips(Component.translatable("tooltip.applied_greg.me_energy_input_hatch"))
                    .overlayTieredHullModel(new ResourceLocation(GTRegistration.REGISTRATE.getModid(), "block/machine/part/" + "energy_input_hatch"))
                    .register(),
            ALL_TIERS
    );
    public static MachineDefinition[] ME_ENERGY_OUTPUT_HATCH = GTMachineUtils.registerTieredMachines(
            AppliedGreg.REGISTRATE,
            "me_energy_output_hatch",
            (holder, tier) -> new MEEnergyHatchPartMachine(holder, tier, IO.OUT, 2),
            (tier, builder) -> builder
                    .langValue(GTValues.VNF[tier] + " ME Dynamo Hatch")
                    .rotationState(RotationState.ALL)
                    .modelProperty(IS_FORMED, false)
                    .abilities(PartAbility.OUTPUT_ENERGY)
                    .tooltips(Component.translatable("tooltip.applied_greg.me_energy_output_hatch"))
                    .overlayTieredHullModel(new ResourceLocation(GTRegistration.REGISTRATE.getModid(), "block/machine/part/" + "energy_output_hatch"))
                    .register(),
            ALL_TIERS
    );

    public static void init() {

    }
}
