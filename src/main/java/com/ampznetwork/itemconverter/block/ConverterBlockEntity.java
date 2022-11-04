package com.ampznetwork.itemconverter.block;

import com.ampznetwork.itemconverter.ItemConverter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ConverterBlockEntity extends BlockEntity {
    public ConverterBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ItemConverter.converter_block_entity_type.get(), p_155229_, p_155230_);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
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
}
