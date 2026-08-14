package net.blackkillerking.minetorio;

import com.mojang.logging.LogUtils;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.entity.ModBlockEntites;
import net.blackkillerking.minetorio.block.entity.PrimitiveOvenBlockEntity;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPatternRegistry;
import net.blackkillerking.minetorio.fluid.ModFluidTypes;
import net.blackkillerking.minetorio.fluid.ModFluids;
import net.blackkillerking.minetorio.item.ModCreativeModTabs;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.loot.ModLootModifiers;
import net.blackkillerking.minetorio.network.ModNetwork;
import net.blackkillerking.minetorio.particle.ModParticals;
import net.blackkillerking.minetorio.recipe.ModRecipes;
import net.blackkillerking.minetorio.screen.MetalShapingStation.MetalShapingStationScreen;
import net.blackkillerking.minetorio.screen.ModMenuTypes;
import net.blackkillerking.minetorio.screen.PrimitiveOven.PrimitiveOvenScreen;
import net.blackkillerking.minetorio.sound.ModSound;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Minetorio.MOD_ID)
public class Minetorio
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "minetorio";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public Minetorio(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModNetwork.register();

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModCreativeModTabs.register(modEventBus);

        ModSound.register(modEventBus);

        ModParticals.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        ModBlockEntites.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);



        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);


    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_CRUDE_OIL.get(), RenderType.solid());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_CRUDE_OIL.get(), RenderType.solid());

                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_TANNIN.get(), RenderType.solid());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_TANNIN.get(), RenderType.solid());

                MenuScreens.register(ModMenuTypes.METAL_SHAPING_STATION_MENU.get(), MetalShapingStationScreen::new);
                MenuScreens.register(ModMenuTypes.PRIMITIVE_OVEN_MENU.get(), PrimitiveOvenScreen::new);
            });
        }
    }
}
