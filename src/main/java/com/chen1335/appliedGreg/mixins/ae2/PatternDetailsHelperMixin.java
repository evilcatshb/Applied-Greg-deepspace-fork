package com.chen1335.appliedGreg.mixins.ae2;

import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.GenericStack;
import com.chen1335.appliedGreg.mixinHooks.ae2.PatternDetailsHelperMixinHooks;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PatternDetailsHelper.class, remap = false)
public class PatternDetailsHelperMixin {

    @Inject(method = "encodeProcessingPattern", at = @At("HEAD"), cancellable = true)
    private static void encodeProcessingPattern(GenericStack[] in, GenericStack[] out, CallbackInfoReturnable<ItemStack> cir) {
        PatternDetailsHelperMixinHooks.encodeProcessingPattern(in, out, cir);
    }
}
