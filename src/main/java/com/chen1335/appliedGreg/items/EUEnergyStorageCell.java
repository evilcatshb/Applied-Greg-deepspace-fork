package com.chen1335.appliedGreg.items;

import appeng.api.storage.StorageCells;
import appeng.core.localization.Tooltips;
import appeng.items.storage.BasicStorageCell;
import appeng.me.cells.BasicCellInventory;
import com.chen1335.appliedGreg.api.EUEnergyKeyType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;

/**
 * EU能量存储单元 - 可以在AE2网络中存储GTCEU的EU能量
 */
public class EUEnergyStorageCell extends BasicStorageCell {


    public EUEnergyStorageCell(Properties properties, ItemLike coreItem, ItemLike housingItem, double idleDrain, int kilobytes) {
        super(properties, coreItem, housingItem, idleDrain, kilobytes, 8, 1, EUEnergyKeyType.INSTANCE);
    }

    @Override
    public void addCellInformationToTooltip(ItemStack is, List<Component> lines) {
        BasicCellInventory cellInventory = (BasicCellInventory) StorageCells.getCellInventory(is, null);
        if (cellInventory != null) {
            lines.add(Tooltips.bytesUsed(cellInventory.getUsedBytes(), cellInventory.getTotalBytes()));
            long storedItemCount = cellInventory.getStoredItemCount();
            lines.add(Component.translatable("tooltip.applied_greg.StoredEU", storedItemCount));
        }
    }

    @Override
    public Optional<TooltipComponent> getCellTooltipImage(ItemStack is) {
        return Optional.empty();
    }
}
