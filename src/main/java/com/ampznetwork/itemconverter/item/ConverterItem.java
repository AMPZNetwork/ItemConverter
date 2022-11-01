package com.ampznetwork.itemconverter.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class ConverterItem extends Item {
    public ConverterItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null)
            return InteractionResult.FAIL;
        player.openItemGui(stack, context.getHand()); // todo
        return InteractionResult.SUCCESS;
    }
}
