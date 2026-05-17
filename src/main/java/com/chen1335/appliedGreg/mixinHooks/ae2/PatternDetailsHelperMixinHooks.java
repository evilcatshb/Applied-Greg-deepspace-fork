package com.chen1335.appliedGreg.mixinHooks.ae2;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import com.chen1335.appliedGreg.definitions.AGItems;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

public class PatternDetailsHelperMixinHooks {
    public static void encodeProcessingPattern(GenericStack[] in, GenericStack[] out, CallbackInfoReturnable<ItemStack> cir) {
        int configuration = -1;
        for (GenericStack genericStack : in) {
            if (genericStack != null && genericStack.what() instanceof AEItemKey itemKey && itemKey.getItem() == GTItems.PROGRAMMED_CIRCUIT.asItem()) {
                if (itemKey.getTag() != null) {
                    configuration = itemKey.getTag().getInt("Configuration");
                }
                break;
            }
        }
        ArrayList<GenericStack> genericStacks = new ArrayList<>(Arrays.asList(in));
        genericStacks.removeIf(genericStack -> genericStack != null && genericStack.what() instanceof AEItemKey itemKey && itemKey.getItem() == GTItems.PROGRAMMED_CIRCUIT.asItem());
        if (configuration >= 0) {
            cir.setReturnValue(AGItems.ENCODE_ABLE_PROCESSING_PATTERN_ITEM.get().encode(genericStacks.toArray(new GenericStack[]{}), out, configuration));
        }

    }
}
