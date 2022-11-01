package com.ampznetwork.itemconverter.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class ConverterBlock extends Block {
    public ConverterBlock() {
        super(new Block.Properties(Material.STONE, MaterialColor.COLOR_GRAY));
    }
}
