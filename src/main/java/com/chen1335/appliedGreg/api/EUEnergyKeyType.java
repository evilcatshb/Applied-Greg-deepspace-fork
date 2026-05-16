package com.chen1335.appliedGreg.api;

import com.chen1335.appliedGreg.AppliedGreg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;

/**
 * EU能量的AEKey类型 - 用于在AE2网络中存储GTCEU的EU能量
 */
public class EUEnergyKeyType extends AEKeyType {
    public static final ResourceLocation ID = AppliedGreg.makeId("eu_energy");
    public static final EUEnergyKeyType INSTANCE = new EUEnergyKeyType();
    
    private EUEnergyKeyType() {
        super(ID, EUEnergyKey.class, Component.translatable("key_type.applied_greg.eu_energy"));
    }
    
    @Override
    public AEKey readFromPacket(FriendlyByteBuf input) {
        return EUEnergyKey.INSTANCE;
    }
    
    @Override
    public AEKey loadKeyFromTag(CompoundTag tag) {
        return EUEnergyKey.INSTANCE;
    }
    
    @Override
    public int getAmountPerOperation() {
        // 每次操作传输的能量量（EU）
        return 8192;
    }
    
    @Override
    public int getAmountPerByte() {
        // 每字节可以存储的EU数量
        return 8192;
    }
    
    @Override
    public String getUnitSymbol() {
        return "EU";
    }
}
