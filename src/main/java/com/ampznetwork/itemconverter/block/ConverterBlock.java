package com.ampznetwork.itemconverter.block;

import com.ampznetwork.itemconverter.ItemConverter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ConverterBlock extends Block implements EntityBlock {
    public ConverterBlock() {
        super(Block.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ConverterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ItemConverter.converter_block_entity_type.get() ? ConverterBlockEntity::tick : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(player instanceof ServerPlayer srvPlr))
            return InteractionResult.PASS;
        level.getBlockEntity(pos, ItemConverter.converter_block_entity_type.get())
                .ifPresent(entity -> entity.openMenu(srvPlr));
        return InteractionResult.SUCCESS;
    }
}
