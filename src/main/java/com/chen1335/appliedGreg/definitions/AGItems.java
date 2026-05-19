package com.chen1335.appliedGreg.definitions;

import appeng.core.definitions.AEItems;
import appeng.items.materials.MaterialItem;
import com.chen1335.appliedGreg.AppliedGreg;
import com.chen1335.appliedGreg.crafting.pattern.EncodeAbleProcessingPatternItem;
import com.chen1335.appliedGreg.items.EUEnergyStorageCell;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AGItems {
    static {
        AppliedGreg.REGISTRATE.creativeModeTab(() -> AppliedGreg.TAB);
    }
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.ITEMS, AppliedGreg.MODID);

    public static final RegistryObject<EncodeAbleProcessingPatternItem> ENCODE_ABLE_PROCESSING_PATTERN_ITEM = ITEM_DEFERRED_REGISTER.register("encode_able_processing_pattern_item", EncodeAbleProcessingPatternItem::new);

    public static final ItemEntry<MaterialItem> EU_CELL_HOUSING = AppliedGreg.REGISTRATE.item("eu_cell_housing", MaterialItem::new)
            .defaultModel()
            .lang("ME EU Cell Housing")
            .register();


    public static final ItemEntry<EUEnergyStorageCell> EU_CELL_1K = AppliedGreg.REGISTRATE.item(
                    "eu_storage_cell_1k",
                    (properties) -> new EUEnergyStorageCell(properties.stacksTo(1), AEItems.CELL_COMPONENT_1K, EU_CELL_HOUSING.get(), 0.5f, 1))

            .defaultModel()
            .lang("1k ME EU Storage Cell")
            .register();

    public static final ItemEntry<EUEnergyStorageCell> EU_CELL_4K = AppliedGreg.REGISTRATE.item(
                    "eu_storage_cell_4k",
                    (properties) -> new EUEnergyStorageCell(properties.stacksTo(1), AEItems.CELL_COMPONENT_4K, EU_CELL_HOUSING.get(), 1f, 4))
            .defaultModel()
            .lang("4k ME EU Storage Cell")
            .register();

    public static final ItemEntry<EUEnergyStorageCell> EU_CELL_16K = AppliedGreg.REGISTRATE.item(
                    "eu_storage_cell_16k",
                    (properties) -> new EUEnergyStorageCell(properties.stacksTo(1), AEItems.CELL_COMPONENT_16K, EU_CELL_HOUSING.get(), 1.5f, 16))
            .defaultModel()
            .lang("16k ME EU Storage Cell")
            .register();

    public static final ItemEntry<EUEnergyStorageCell> EU_CELL_64K = AppliedGreg.REGISTRATE.item(
                    "eu_storage_cell_64k",
                    (properties) -> new EUEnergyStorageCell(properties.stacksTo(1), AEItems.CELL_COMPONENT_64K, EU_CELL_HOUSING.get(), 2f, 64))
            .defaultModel()
            .lang("64k ME EU Storage Cell")
            .register();

    public static final ItemEntry<EUEnergyStorageCell> EU_CELL_256K = AppliedGreg.REGISTRATE.item(
                    "eu_storage_cell_256k",
                    (properties) -> new EUEnergyStorageCell(properties.stacksTo(1), AEItems.CELL_COMPONENT_256K, EU_CELL_HOUSING.get(), 2.5f, 256))
            .defaultModel()
            .lang("256k ME EU Storage Cell")
            .register();
}
