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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ConverterBlockEntity extends BlockEntity implements MenuProvider {
    public static final String TagInputKey = "input_slot";
    public static final String TagOutputKey = "output_slot";
    public static final String TagConfigKey = "configurator";
    public static final int SlotConfig = 0;
    public static final int SlotInput = 1;
    public static final int SlotOutput = 2;

    private final CompoundTag data = new CompoundTag();
    private final IItemHandler dataInventory = new ItemStackHandler(3);

    public IItemHandler getDataInventory() {
        return dataInventory;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("menu.title.itemconverter.autoconverter");
    }

    public ConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ItemConverter.converter_block_entity_type.get(), pos, state);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        // todo do stuff on tick
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveOwnData();
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        loadOwnData(nbt);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.merge(saveOwnData());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        loadOwnData(nbt);
    }

    private void loadOwnData(CompoundTag nbt) {
        for (String key : new String[]{TagInputKey, TagOutputKey, TagConfigKey})
            data.put(key, Objects.requireNonNullElseGet(nbt.get(key), CompoundTag::new));
        //todo: init item slots
    }

    private CompoundTag saveOwnData() {
        //todo: save item slots
        return data;
    }

    public void updateData(ItemStack configItem, ItemStack inputStack, ItemStack outputStack) {
        data.put(TagConfigKey, Objects.requireNonNull(configItem.getTag(), "Config tag was null"));
    }

    public void openMenu(ServerPlayer plr) {
        NetworkHooks.openScreen(plr, this);
    }

    @Nullable
    @Override
    public ConverterBlockMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new ConverterBlockMenu(this, containerId, playerInventory, new FriendlyByteBuf(Unpooled.buffer()));
    }
}
