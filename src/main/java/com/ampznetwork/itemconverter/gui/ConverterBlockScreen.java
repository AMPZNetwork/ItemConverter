package com.ampznetwork.itemconverter.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ConverterBlockScreen extends AbstractContainerScreen<ConverterBlockMenu> {
    public static final int SLOT_SIZE = 20;
    public static final int INVENTORY_OFFSET = 100;

    public ConverterBlockScreen(ConverterBlockMenu menu, Inventory playerInventory, Component comp) {
        super(menu, playerInventory, comp);
    }

    @Override
    protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
        renderBackground(pose);
    }
}
