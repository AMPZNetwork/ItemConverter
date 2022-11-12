package com.ampznetwork.itemconverter;

import com.ampznetwork.itemconverter.block.ConverterBlock;
import com.ampznetwork.itemconverter.block.ConverterBlockEntity;
import com.ampznetwork.itemconverter.gui.ConverterBlockMenu;
import com.ampznetwork.itemconverter.gui.ConverterBlockScreen;
import com.ampznetwork.itemconverter.item.ConverterItem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ItemConverter.MODID)
@Mod.EventBusSubscriber(modid = ItemConverter.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemConverter {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "itemconverter";
    public static final DeferredRegister<Item> ITEMS;
    public static final DeferredRegister<Block> BLOCKS;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;
    public static final DeferredRegister<MenuType<?>> MENUS;
    public static final RegistryObject<ConverterBlock> converter_block;
    public static final RegistryObject<BlockItem> converter_block_item;
    public static final RegistryObject<ConverterItem> converter_item;
    public static final RegistryObject<BlockEntityType<ConverterBlockEntity>> converter_block_entity_type;
    public static final RegistryObject<MenuType<ConverterBlockMenu>> converter_block_menu_type;
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
        BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
        MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

        converter_block = BLOCKS.register("converter_block", ConverterBlock::new);
        converter_block_item = ITEMS.register("converter_block", () -> new BlockItem(converter_block.get(), new Item.Properties()));
        converter_item = ITEMS.register("converter_item", ConverterItem::new);
        converter_block_entity_type = BLOCK_ENTITY_TYPES.register("converter_block_entity", () ->
                BlockEntityType.Builder.of(ConverterBlockEntity::new, converter_block.get()).build(null));
        converter_block_menu_type = MENUS.register("converter_block_menu", () -> IForgeMenuType.create(ConverterBlockMenu::new));
    }

    public ItemConverter() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        MENUS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        MenuScreens.register(converter_block_menu_type.get(), ConverterBlockScreen::new);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }
}
