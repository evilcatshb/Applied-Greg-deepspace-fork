package com.chen1335.appliedGreg.client;

import appeng.api.client.AEKeyRendering;
import com.chen1335.appliedGreg.AppliedGreg;
import com.chen1335.appliedGreg.api.EUEnergyKey;
import com.chen1335.appliedGreg.api.EUEnergyKeyType;
import com.chen1335.appliedGreg.client.keyRendering.EUKeyRender;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler {
    public static class FORGE {

    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AppliedGreg.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class MOD {
        @SubscribeEvent
        public static void FMLClientSetupEvent(FMLClientSetupEvent event) {
            AEKeyRendering.register(EUEnergyKeyType.INSTANCE, EUEnergyKey.class, new EUKeyRender());
        }
    }
}
