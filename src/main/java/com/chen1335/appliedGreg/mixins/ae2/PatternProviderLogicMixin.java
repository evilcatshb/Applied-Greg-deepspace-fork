package com.chen1335.appliedGreg.mixins.ae2;

import appeng.api.crafting.IPatternDetails;
import appeng.api.stacks.KeyCounter;
import appeng.helpers.patternprovider.PatternProviderLogic;
import appeng.helpers.patternprovider.PatternProviderLogicHost;
import com.chen1335.appliedGreg.crafting.pattern.AEEncodeAbleProcessingPattern;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IHasCircuitSlot;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PatternProviderLogic.class,remap = false)
public class PatternProviderLogicMixin {
    @Shadow
    @Final
    private PatternProviderLogicHost host;

    @Shadow
    private Direction sendDirection;

    @Inject(method = "pushPattern", at = @At(value = "INVOKE", target = "Lappeng/helpers/patternprovider/PatternProviderLogic;sendStacksOut()Z"))
    private void onSendStacksOut(IPatternDetails patternDetails, KeyCounter[] inputHolder, CallbackInfoReturnable<Boolean> cir) {
        if (patternDetails instanceof AEEncodeAbleProcessingPattern processingPattern) {
            BlockEntity provider = this.host.getBlockEntity();
            BlockPos relative = provider.getBlockPos().relative(this.sendDirection);
            Level level = provider.getLevel();
            if (level != null) {
                if (level.getBlockEntity(relative) instanceof MetaMachineBlockEntity machineEntity) {
                    if (machineEntity.metaMachine instanceof IHasCircuitSlot circuitMachine && circuitMachine.getCircuitInventory().getSlots() > 0) {
                        circuitMachine.getCircuitInventory().setStackInSlot(0, IntCircuitBehaviour.stack(processingPattern.getConfiguration()));
                    }
                }
            }
        }
    }
}
