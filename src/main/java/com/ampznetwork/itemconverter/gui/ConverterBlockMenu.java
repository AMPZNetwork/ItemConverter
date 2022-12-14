package com.ampznetwork.itemconverter.gui;

import com.ampznetwork.itemconverter.ItemConverter;
import com.ampznetwork.itemconverter.block.ConverterBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Optional;
import java.util.function.Function;

import static com.ampznetwork.itemconverter.gui.ConverterBlockScreen.*;

public class ConverterBlockMenu extends AbstractContainerMenu {

    private final IItemHandler dataInventory;
    private final Function<Player, Boolean> stillValid;
    private final ConverterBlockEntity block;

    //client
    public ConverterBlockMenu(ConverterBlockEntity block, int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(
                block,
                containerId,
                playerInventory,
                Optional.of(block)
                        .map(ConverterBlockEntity::getDataInventory)
                        .orElseGet(() -> new ItemStackHandler(3)),
                extraData,
                ($) -> true
        );
    }

    //server
    public ConverterBlockMenu(ConverterBlockEntity block, int containerId, Inventory playerInventory, IItemHandler dataInventory, FriendlyByteBuf extraData, Function<Player, Boolean> stillValid) {
        super(ItemConverter.converter_block_menu_type.get(), containerId);

        this.block = block;
        this.dataInventory = dataInventory;
        this.stillValid = stillValid;

        addSlot(new SlotItemHandler(dataInventory, ConverterBlockEntity.SlotConfig, 0,0));
        addSlot(new SlotItemHandler(dataInventory, ConverterBlockEntity.SlotInput, (int)((double)9 * SLOT_SIZE / 2 - 30), -20));
        addSlot(new SlotItemHandler(dataInventory, ConverterBlockEntity.SlotOutput, (int)((double)9 * SLOT_SIZE / 2 + 30), -20));
        for (int i = 0; i < 4*9; i++)
            addSlot(new Slot(playerInventory, i, i % 9 * SLOT_SIZE, i / 9 * SLOT_SIZE + INVENTORY_OFFSET));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        //todo

        // The quick moved slot stack
        ItemStack quickMovedStack = ItemStack.EMPTY;
        // The quick moved slot
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);

        // If the slot is in the valid range and the slot is not empty
        if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
            // Get the raw stack to move
            ItemStack rawStack = quickMovedSlot.getItem();
            // Set the slot stack to a copy of the raw stack
            quickMovedStack = rawStack.copy();

    /*
    The following quick move logic can be simplified to if in data inventory,
    try to move to player inventory/hotbar and vice versa for containers
    that cannot transform data (e.g. chests).
    */

            // If the quick move was performed on the data inventory result slot
            if (quickMovedSlotIndex == ConverterBlockEntity.SlotOutput) {
                // Try to move the result slot into the player inventory/hotbar
                if (!this.moveItemStackTo(rawStack, 5, 41, true)) {
                    // If cannot move, no longer quick move
                    return ItemStack.EMPTY;
                }

                // Perform logic on result slot quick move
                quickMovedSlot.onQuickCraft(rawStack, quickMovedStack);
            }
            // Else if the quick move was performed on the player inventory or hotbar slot
            else if (quickMovedSlotIndex >= 5 && quickMovedSlotIndex < 41) {
                // Try to move the inventory/hotbar slot into the data inventory input slots
                if (!this.moveItemStackTo(rawStack, ConverterBlockEntity.SlotInput, 5, false)) {
                    // If cannot move and in player inventory slot, try to move to hotbar
                    if (quickMovedSlotIndex < 32) {
                        if (!this.moveItemStackTo(rawStack, 32, 41, false)) {
                            // If cannot move, no longer quick move
                            return ItemStack.EMPTY;
                        }
                    }
                    // Else try to move hotbar into player inventory slot
                    else if (!this.moveItemStackTo(rawStack, 5, 32, false)) {
                        // If cannot move, no longer quick move
                        return ItemStack.EMPTY;
                    }
                }
            }
            // Else if the quick move was performed on the data inventory input slots, try to move to player inventory/hotbar
            else if (!this.moveItemStackTo(rawStack, 5, 41, false)) {
                // If cannot move, no longer quick move
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                // If the raw stack has completely moved out of the slot, set the slot to the empty stack
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                // Otherwise, notify the slot that the stack count has changed
                quickMovedSlot.setChanged();
            }

    /*
    The following if statement and Slot#onTake call can be removed if the
    menu does not represent a container that can transform stacks (e.g.
    chests).
    */
            if (rawStack.getCount() == quickMovedStack.getCount()) {
                // If the raw stack was not able to be moved to another slot, no longer quick move
                return ItemStack.EMPTY;
            }
            // Execute logic on what to do post move with the remaining stack
            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack; // Return the slot stack
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid.apply(player);
    }
}
