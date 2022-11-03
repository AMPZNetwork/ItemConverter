package com.ampznetwork.itemconverter.block;

import com.mojang.datafixers.types.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class ConverterBlockEntity extends BlockEntity {
    public ConverterBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(new BlockEntityType<>(ConverterBlockEntity::new, Set.of(new ConverterBlock()), null), p_155229_, p_155230_);
    }
}
