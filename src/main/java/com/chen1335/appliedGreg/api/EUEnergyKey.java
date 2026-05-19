package com.chen1335.appliedGreg.api;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;

/**
 * EU能量的AEKey - 表示特定数量的EU能量
 */
public class EUEnergyKey extends AEKey {
    public static final EUEnergyKey INSTANCE = new EUEnergyKey();
    
    private static final ResourceLocation ID = EUEnergyKeyType.ID;
    private static final Component DISPLAY_NAME = Component.translatable("key.applied_greg.eu_energy");
    
    private EUEnergyKey() {}
    
    @Override
    public AEKeyType getType() {
        return EUEnergyKeyType.INSTANCE;
    }
    
    @Override
    public AEKey dropSecondary() {
        return this;
    }
    
    @Override
    public CompoundTag toTag() {
        return new CompoundTag();
    }
    
    @Override
    public Object getPrimaryKey() {
        return this;
    }
    
    @Override
    public ResourceLocation getId() {
        return ID;
    }
    
    @Override
    public void writeToPacket(FriendlyByteBuf data) {

    }
    
    @Override
    protected Component computeDisplayName() {
        return DISPLAY_NAME;
    }
    
    @Override
    public void addDrops(long amount, List<ItemStack> drops, Level level, BlockPos pos) {

    }
    
    @Override
    public int hashCode() {
        return Objects.hash(EUEnergyKey.class);
    }
    
    @Override
    public String toString() {
        return "EU";
    }
}
