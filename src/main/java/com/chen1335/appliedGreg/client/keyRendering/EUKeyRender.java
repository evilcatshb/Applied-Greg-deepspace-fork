package com.chen1335.appliedGreg.client.keyRendering;

import appeng.api.client.AEKeyRenderHandler;
import com.chen1335.appliedGreg.api.EUEnergyKey;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class EUKeyRender implements AEKeyRenderHandler<EUEnergyKey> {

    @Override
    public void drawInGui(Minecraft minecraft, GuiGraphics guiGraphics, int x, int y, EUEnergyKey stack) {
        guiGraphics.renderItem(GTItems.ULTIMATE_BATTERY.asItem().getDefaultInstance(), x, y);
    }

    @Override
    public void drawOnBlockFace(PoseStack poseStack, MultiBufferSource buffers, EUEnergyKey what, float scale, int combinedLight, Level level) {

    }

    @Override
    public Component getDisplayName(EUEnergyKey stack) {
        return Component.literal("EU");
    }
}
