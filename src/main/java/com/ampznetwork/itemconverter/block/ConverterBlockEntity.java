package com.ampznetwork.itemconverter.block;

import com.ampznetwork.itemconverter.ItemConverter;
import com.ampznetwork.itemconverter.gui.ConverterBlockMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public class ConverterBlockEntity extends BlockEntity {
    private final MenuProvider MENU_PROVIDER = new SimpleMenuProvider(
            (containerId, playerInventory, player) -> new ConverterBlockMenu(containerId, playerInventory, new FriendlyByteBuf(Unpooled.buffer())),
            Component.translatable("menu.title.itemconverter.autoconverter"));

    public ConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ItemConverter.converter_block_entity_type.get(), pos, state);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        // todo do stuff on tick
    }

    @Override
    public CompoundTag getUpdateTag() {
        return super.getUpdateTag();

        // todo return update data
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);

        // todo process update data
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        // todo save block changes
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        // todo load block changes
    }

    public void openMenu(ServerPlayer plr) {
        NetworkHooks.openScreen(plr, MENU_PROVIDER);
    }
}
