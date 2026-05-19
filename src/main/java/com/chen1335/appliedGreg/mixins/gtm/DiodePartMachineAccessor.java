package com.chen1335.appliedGreg.mixins.gtm;

import com.gregtechceu.gtceu.common.machine.multiblock.part.DiodePartMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = DiodePartMachine.class,remap = false)
public interface DiodePartMachineAccessor {
    @Accessor("amps")
    int AG$getAmps();
}
