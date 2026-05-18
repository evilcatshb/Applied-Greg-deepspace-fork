package com.chen1335.appliedGreg;

import appeng.api.stacks.AEKeyTypes;
import com.chen1335.appliedGreg.api.EUEnergyKeyType;
import com.chen1335.appliedGreg.data.DataMain;
import com.chen1335.appliedGreg.definitions.AGItems;
import com.chen1335.appliedGreg.definitions.AGMachines;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

@Mod(AppliedGreg.MODID)
public class AppliedGreg {
    public static final String MODID = "applied_greg";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(MODID);

    public static RegistryEntry<CreativeModeTab> TAB = REGISTRATE.defaultCreativeTab("applied_greg",
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("applied_greg", REGISTRATE))
                            .icon(() -> AGMachines.ME_POWER_SUBSTATION_STORAGE_BUS.asStack())
                            .title(Component.translatable("itemGroup.applied_greg"))
                            .build())
            .register();

    public AppliedGreg(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        AGItems.ITEM_DEFERRED_REGISTER.register(modEventBus);
        modEventBus.addListener((RegisterEvent event) -> {
            if (!event.getRegistryKey().equals(Registries.BLOCK)) {
                return;
            }
            AEKeyTypes.register(EUEnergyKeyType.INSTANCE);
        });
        modEventBus.addListener(DataMain::runData);
        modEventBus.register(this);
        REGISTRATE.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
        LOGGER.info("Applied Greg initialized!");
    }

    @SubscribeEvent
    public void FMLConstructModEvent(FMLConstructModEvent event) {
        event.enqueueWork(REGISTRATE::registerRegistrate);
        event.enqueueWork(DataMain::init);
    }


    public static ResourceLocation makeId(String id) {
        return ResourceLocation.tryBuild(MODID, id);
    }
}
