package com.chen1335.appliedGreg;

import com.chen1335.appliedGreg.data.AGRecipes;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class AGGTAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return AppliedGreg.REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        AGRecipes.recipeAddition(provider);
    }

    @Override
    public String addonModId() {
        return AppliedGreg.MODID;
    }
}
