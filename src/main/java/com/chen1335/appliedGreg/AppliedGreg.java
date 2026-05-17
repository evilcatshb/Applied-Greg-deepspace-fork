package com.chen1335.appliedGreg;

import appeng.api.stacks.AEKeyTypes;
import com.chen1335.appliedGreg.api.EUEnergyKeyType;
import com.chen1335.appliedGreg.definitions.AGItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(AppliedGreg.MODID)
public class AppliedGreg {
    public static final String MODID = "applied_greg";
    public static final Logger LOGGER = LogUtils.getLogger();


    public AppliedGreg(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        AGItems.ITEM_DEFERRED_REGISTER.register(modEventBus);
        modEventBus.addListener((RegisterEvent event) -> {
            if (!event.getRegistryKey().equals(Registries.BLOCK)) {
                return;
            }
            AEKeyTypes.register(EUEnergyKeyType.INSTANCE);
        });
        modEventBus.register(this);
        LOGGER.info("Applied Greg initialized!");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void FMLConstructModEvent(FMLConstructModEvent event) {

    }


    public static ResourceLocation makeId(String id) {
        return ResourceLocation.tryBuild(MODID, id);
    }
}
