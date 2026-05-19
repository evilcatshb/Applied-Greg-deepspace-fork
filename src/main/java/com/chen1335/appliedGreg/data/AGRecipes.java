package com.chen1335.appliedGreg.data;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.core.definitions.AEParts;
import com.chen1335.appliedGreg.AppliedGreg;
import com.chen1335.appliedGreg.definitions.AGItems;
import com.chen1335.appliedGreg.definitions.AGMachines;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.wireFine;
import static com.gregtechceu.gtceu.common.data.GTMachines.SUBSTATION_ENERGY_INPUT_HATCH;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLY_LINE_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CustomTags.CIRCUITS_ARRAY;

public class AGRecipes {
    public static void recipeAddition(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(
                provider,
                "eu_cell_housing",
                AGItems.EU_CELL_HOUSING.asStack(),
                "ABA",
                "B B",
                "CCC",
                'A', AEBlocks.QUARTZ_GLASS.asItem(),
                'B', Items.REDSTONE,
                'C', new MaterialEntry(TagPrefix.ingot, RedAlloy)
        );

        VanillaRecipeHelper.addShapelessRecipe(provider, "eu_cell_1k", AGItems.EU_CELL_1K.asStack(), AGItems.EU_CELL_HOUSING.asStack(), AEItems.CELL_COMPONENT_1K.asItem());
        VanillaRecipeHelper.addShapelessRecipe(provider, "eu_cell_4k", AGItems.EU_CELL_4K.asStack(), AGItems.EU_CELL_HOUSING.asStack(), AEItems.CELL_COMPONENT_4K.asItem());
        VanillaRecipeHelper.addShapelessRecipe(provider, "eu_cell_16k", AGItems.EU_CELL_16K.asStack(), AGItems.EU_CELL_HOUSING.asStack(), AEItems.CELL_COMPONENT_16K.asItem());
        VanillaRecipeHelper.addShapelessRecipe(provider, "eu_cell_64k", AGItems.EU_CELL_64K.asStack(), AGItems.EU_CELL_HOUSING.asStack(), AEItems.CELL_COMPONENT_64K.asItem());
        VanillaRecipeHelper.addShapelessRecipe(provider, "eu_cell_256k", AGItems.EU_CELL_256K.asStack(), AGItems.EU_CELL_HOUSING.asStack(), AEItems.CELL_COMPONENT_256K.asItem());


        for (int i = 0; i < AGMachines.ME_ENERGY_INPUT_HATCH.length; i++) {
            MachineDefinition definition = AGMachines.ME_ENERGY_INPUT_HATCH[i];
            MachineDefinition transformer = GTMachines.TRANSFORMER[i];
            if (transformer != null) {
                ASSEMBLER_RECIPES.recipeBuilder(definition.getId())
                        .inputItems(GTMachines.ENERGY_INPUT_HATCH[i], 1)
                        .inputItems(transformer)
                        .inputItems(CIRCUITS_ARRAY[i], 1)
                        .inputItems(AEParts.FE_P2P_TUNNEL.asItem(), 1)
                        .outputItems(definition.asStack())
                        .duration(200).EUt(VA[EV]).save(provider);
            }
        }

        for (int i = 0; i < AGMachines.ME_ENERGY_OUTPUT_HATCH.length; i++) {

            MachineDefinition definition = AGMachines.ME_ENERGY_OUTPUT_HATCH[i];
            MachineDefinition transformer = GTMachines.TRANSFORMER[i];
            if (transformer != null) {
                ASSEMBLER_RECIPES.recipeBuilder(definition.getId())
                        .inputItems(GTMachines.ENERGY_OUTPUT_HATCH[i], 1)
                        .inputItems(transformer)
                        .inputItems(CIRCUITS_ARRAY[i], 1)
                        .inputItems(AEParts.FE_P2P_TUNNEL.asItem(), 1)
                        .outputItems(definition.asStack())
                        .duration(200).EUt(VA[EV]).save(provider);
            }
        }

        for (int i = 0; i < AGMachines.ME_DIODE.length; i++) {
            MachineDefinition definition = AGMachines.ME_DIODE[i];
            MachineDefinition transformer = GTMachines.TRANSFORMER[i];
            MachineDefinition diode = GTMachines.DIODE[i];
            if (transformer != null && diode != null) {
                ASSEMBLER_RECIPES.recipeBuilder(definition.getId())
                        .inputItems(diode, 1)
                        .inputItems(transformer)
                        .inputItems(AEParts.FE_P2P_TUNNEL.asItem(), 1)
                        .outputItems(definition.asStack())
                        .duration(200).EUt(VA[HV]).save(provider);
            }
        }


        ASSEMBLY_LINE_RECIPES.recipeBuilder(AppliedGreg.makeId("me_power_substation_storage_bus"))
                .inputItems(SUBSTATION_ENERGY_INPUT_HATCH[LuV], 1)
                .inputItems(GTMultiMachines.ACTIVE_TRANSFORMER, 1)
                .inputItems(CustomTags.LuV_CIRCUITS, 4)
                .inputItems(GTItems.POWER_INTEGRATED_CIRCUIT, 4)
                .inputItems(AEParts.FE_P2P_TUNNEL.asItem(), 1)
                .inputItems(wireFine, Europium, 32)
                .inputItems(wireFine, Europium, 32)
                .inputItems(wireFine, Europium, 32)
                .inputItems(wireFine, Europium, 32)
                .inputFluids(SolderingAlloy, L * 4)
                .inputFluids(Lubricant, 500)
                .outputItems(AGMachines.ME_POWER_SUBSTATION_STORAGE_BUS)
                .scannerResearch(b -> b.researchStack(SUBSTATION_ENERGY_INPUT_HATCH[LuV].asStack())
                        .duration(2400)
                        .EUt(VA[LuV]))
                .duration(1200).EUt(VA[LuV]).save(provider);
    }
}
