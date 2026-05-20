package com.chen1335.appliedGreg.crafting.pattern;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import appeng.crafting.pattern.AEProcessingPattern;
import appeng.crafting.pattern.ProcessingPatternItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ProgrammableProcessingPatternItem extends ProcessingPatternItem {

    public ProgrammableProcessingPatternItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag advancedTooltips) {
        super.appendHoverText(stack, level, lines, advancedTooltips);
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return;
        }
        lines.add(Component.translatable("applied_greg.tooltip.configuration", stack.getTag().getInt("Configuration")));
    }

    @Override
    public AEProcessingPattern decode(AEItemKey what, Level level) {
        if (what == null || !what.hasTag()) {
            return null;
        }

        try {
            return new AEEncodeAbleProcessingPattern(what);
        } catch (Exception e) {
            return null;
        }
    }

    public ItemStack encode(GenericStack[] sparseInputs, GenericStack[] sparseOutputs, int configuration) {
        ItemStack encode = encode(sparseInputs, sparseOutputs);
        encode.getOrCreateTag().putInt("Configuration", configuration);
        return encode;
    }
}
