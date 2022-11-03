package com.ampznetwork.itemconverter.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ConverterItemGui extends AbstractContainerMenu {
    private static final MenuType<ConverterItemGui> MENU_TYPE
            = new MenuType<>((int p_38851_, Inventory p_38852_) -> new ConverterItemGui(p_38851_));
    private final ItemStack[] input = new ItemStack[9];
    private final ItemStack[] output = new ItemStack[9];

    protected ConverterItemGui(int p_38852_) {
        super(MENU_TYPE, p_38852_);
    }

    @Override
    protected Slot addSlot(Slot p_38898_) {
        new Slot(, 0,0,0);
        return super.addSlot(p_38898_);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
