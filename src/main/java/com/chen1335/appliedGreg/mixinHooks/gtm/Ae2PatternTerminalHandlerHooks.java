package com.chen1335.appliedGreg.mixinHooks.gtm;

import appeng.api.stacks.GenericStack;
import com.gregtechceu.gtceu.common.data.GTItems;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;

import java.util.ArrayList;
import java.util.List;

public class Ae2PatternTerminalHandlerHooks {
    public static List<List<GenericStack>> modifyInputs(EmiRecipe emiRecipe, List<List<GenericStack>> old) {
        if (emiRecipe.getCatalysts().isEmpty()) {
            return old;
        }

        List<List<GenericStack>> newList = new ArrayList<>(old);
        for (EmiIngredient catalyst : emiRecipe.getCatalysts()) {
            for (EmiStack emiStack : catalyst.getEmiStacks()) {
                if (emiStack.getItemStack().getItem() == GTItems.PROGRAMMED_CIRCUIT.asItem()) {
                    GenericStack genericStack = GenericStack.fromItemStack(emiStack.getItemStack());
                    if (genericStack != null) {
                        newList.add(List.of(genericStack));
                    }
                }
            }

        }
        return newList.stream().toList();
    }
}
