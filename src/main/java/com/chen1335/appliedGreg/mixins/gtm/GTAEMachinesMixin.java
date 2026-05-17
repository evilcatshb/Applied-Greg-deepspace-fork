package com.chen1335.appliedGreg.mixins.gtm;

import com.chen1335.appliedGreg.definitions.AGMachines;
import com.gregtechceu.gtceu.common.data.machines.GTAEMachines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GTAEMachines.class, remap = false)
public class GTAEMachinesMixin {
    @Inject(method = "init", at = @At("RETURN"))
    private static void onInit(CallbackInfo ci) {
        AGMachines.init();
    }
}
