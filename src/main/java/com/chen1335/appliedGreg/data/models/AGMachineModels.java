package com.chen1335.appliedGreg.data.models;

import com.chen1335.appliedGreg.common.AGMachineModelProperties;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.machine.multiblock.part.DiodePartMachine;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.DIODE_AMP_MODE;
import static com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay.IN_OVERLAYS_FOR_AMP;
import static com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay.OUT_OVERLAYS_FOR_AMP;

public class AGMachineModels {
    public static MachineBuilder.ModelInitializer createMEDiodeModel() {
        return (ctx, prov, builder) -> {
            builder.forAllStatesModels(renderState -> {
                DiodePartMachine.AmpMode mode = renderState.getValue(DIODE_AMP_MODE);
                EnergyIOOverlay energyIn = IN_OVERLAYS_FOR_AMP.get(mode.getAmpValue());
                EnergyIOOverlay energyOut = OUT_OVERLAYS_FOR_AMP.get(mode.getAmpValue());
                Boolean out = renderState.getValue(AGMachineModelProperties.IS_DIODE_OUT);
                EnergyIOOverlay overlay = out? energyOut : energyIn;


                BlockModelBuilder model = prov.models().nested()
                        .parent(prov.models().getExistingFile(GTMachineModels.TRANSFORMER_LIKE))
                        .texture("overlay_in_io_emissive", ResourceLocation.parse("gtceu:block/overlay/appeng/me_output_bus"))
                        .texture("overlay_in_io", ResourceLocation.parse("gtceu:block/overlay/appeng/me_output_bus"))
                        .texture("overlay_in_tinted", ResourceLocation.parse("gtceu:block/overlay/appeng/me_output_bus"))
                        .texture("overlay_out_io_emissive", overlay.getIoPartEmissive())
                        .texture("overlay_out_io", overlay.getIoPart())
                        .texture("overlay_out_tinted", overlay.getTintedPart());
                GTMachineModels.tieredHullTextures(model, builder.getOwner().getTier());
                return model;
            });

            builder.addReplaceableTextures("bottom", "top", "side");
        };
    }
}
