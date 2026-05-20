package com.chen1335.appliedGreg.mixins.emi;

import appeng.api.stacks.GenericStack;
import com.chen1335.appliedGreg.mixinHooks.gtm.Ae2PatternTerminalHandlerHooks;
import com.gregtechceu.gtceu.integration.emi.recipe.Ae2PatternTerminalHandler;
import dev.emi.emi.api.recipe.EmiRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = Ae2PatternTerminalHandler.class,remap = false)
public class Ae2PatternTerminalHandlerMixin {
    @Inject(method = "ofInputs", at = @At("RETURN"), cancellable = true)
    private static void modifyInputs(EmiRecipe emiRecipe, CallbackInfoReturnable<List<List<GenericStack>>> cir) {
        cir.setReturnValue(Ae2PatternTerminalHandlerHooks.modifyInputs(emiRecipe, cir.getReturnValue()));

    }
}
