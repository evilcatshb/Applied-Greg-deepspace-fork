package com.chen1335.appliedGreg.common;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.StorageCell;
import com.chen1335.appliedGreg.AppliedGreg;
import com.chen1335.appliedGreg.api.EUEnergyKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AppliedGreg.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void RightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemInHand = event.getEntity().getItemInHand(event.getHand());
        StorageCell cellInventory = StorageCells.getCellInventory(itemInHand, null);
        if (cellInventory != null) {
            cellInventory.insert(EUEnergyKey.INSTANCE, 10000, Actionable.MODULATE, IActionSource.empty());
        }
    }
}
