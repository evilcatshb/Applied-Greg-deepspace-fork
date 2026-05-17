package com.chen1335.appliedGreg.definitions;

import appeng.core.definitions.AEItems;
import appeng.items.materials.MaterialItem;
import appeng.items.storage.BasicStorageCell;
import com.chen1335.appliedGreg.AppliedGreg;
import com.chen1335.appliedGreg.crafting.pattern.EncodeAbleProcessingPatternItem;
import com.chen1335.appliedGreg.items.EUEnergyStorageCell;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AGItems {

    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.ITEMS, AppliedGreg.MODID);

    public static final RegistryObject<EncodeAbleProcessingPatternItem> ENCODE_ABLE_PROCESSING_PATTERN_ITEM = ITEM_DEFERRED_REGISTER.register("encode_able_processing_pattern_item", EncodeAbleProcessingPatternItem::new);

    public static final RegistryObject<Item> EU_CELL_HOUSING = ITEM_DEFERRED_REGISTER.register("eu_cell_housing", () -> new MaterialItem(new Item.Properties()));

    // EU能量存储单元 - 不同容量
    // 4k = 可以存储约 4 * 1024 * 8192 = 33,554,432 EU
    public static final RegistryObject<BasicStorageCell> EU_CELL_1K = ITEM_DEFERRED_REGISTER.register(
            "eu_energy_cell_1k",
            () -> new EUEnergyStorageCell(new Item.Properties().stacksTo(1), AEItems.CELL_COMPONENT_1K, EU_CELL_HOUSING.get(), 0.5f, 1)
    );

    // 4k = 可以存储约 4 * 1024 * 8192 = 33,554,432 EU
    public static final RegistryObject<BasicStorageCell> EU_CELL_4K = ITEM_DEFERRED_REGISTER.register(
            "eu_energy_cell_4k",
            () -> new EUEnergyStorageCell(new Item.Properties().stacksTo(1), AEItems.CELL_COMPONENT_4K, EU_CELL_HOUSING.get(), 1f, 4)
    );

    // 16k = 可以存储约 134,217,728 EU
    public static final RegistryObject<BasicStorageCell> EU_CELL_16K = ITEM_DEFERRED_REGISTER.register(
            "eu_energy_cell_16k",
            () -> new EUEnergyStorageCell(new Item.Properties().stacksTo(1), AEItems.CELL_COMPONENT_16K, EU_CELL_HOUSING.get(), 1.5f, 16)
    );

    // 64k = 可以存储约 536,870,912 EU
    public static final RegistryObject<BasicStorageCell> EU_CELL_64K = ITEM_DEFERRED_REGISTER.register(
            "eu_energy_cell_64k",
            () -> new EUEnergyStorageCell(new Item.Properties().stacksTo(1), AEItems.CELL_COMPONENT_64K, EU_CELL_HOUSING.get(), 2f, 64)
    );

    // 256k = 可以存储约 2,147,483,648 EU
    public static final RegistryObject<BasicStorageCell> EU_CELL_256K = ITEM_DEFERRED_REGISTER.register(
            "eu_energy_cell_256k",
            () -> new EUEnergyStorageCell(new Item.Properties().stacksTo(1), AEItems.CELL_COMPONENT_256K, EU_CELL_HOUSING.get(), 2.5f, 256)
    );
}
