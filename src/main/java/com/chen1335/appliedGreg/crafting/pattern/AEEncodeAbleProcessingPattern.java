package com.chen1335.appliedGreg.crafting.pattern;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.KeyCounter;
import appeng.crafting.pattern.AEProcessingPattern;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

public class AEEncodeAbleProcessingPattern extends AEProcessingPattern {
    private final int configuration;

    public AEEncodeAbleProcessingPattern(AEItemKey definition) {
        super(definition);
        CompoundTag tag = Objects.requireNonNull(definition.getTag());
        this.configuration = tag.getInt("Configuration");
    }

    @Override
    public void pushInputsToExternalInventory(KeyCounter[] inputHolder, PatternInputSink inputSink) {
        super.pushInputsToExternalInventory(inputHolder, inputSink);
    }


    public int getConfiguration() {
        return configuration;
    }
}
