package com.chen1335.appliedGreg.data;

import com.chen1335.appliedGreg.AppliedGreg;
import com.gregtechceu.gtceu.api.registry.registrate.provider.GTBlockstateProvider;
import com.gregtechceu.gtceu.data.model.BlockstateModelLoader;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraftforge.data.event.GatherDataEvent;

public class DataMain {

    public static void runData(GatherDataEvent event) {

    }

    public static void init() {

        AppliedGreg.REGISTRATE.addDataGenerator(ProviderType.BLOCKSTATE,
                p -> BlockstateModelLoader.init((GTBlockstateProvider) p));
    }
}
