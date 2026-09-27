package net.blackkillerking.minetorio;

import com.mojang.logging.LogUtils;
import net.blackkillerking.minetorio.registry.ModBlocks;
import net.blackkillerking.minetorio.registry.ModBlockEntites;
import net.blackkillerking.minetorio.registry.ModFluidTypes;
import net.blackkillerking.minetorio.registry.ModFluids;
import net.blackkillerking.minetorio.tabs.ModCreativeModTabs;
import net.blackkillerking.minetorio.registry.ModItems;
import net.blackkillerking.minetorio.registry.ModLootModifiers;
import net.blackkillerking.minetorio.registry.ModNetwork;
import net.blackkillerking.minetorio.registry.ModParticals;
import net.blackkillerking.minetorio.registry.ModRecipes;
import net.blackkillerking.minetorio.screen.KilnController.KilnControllerScreen;
import net.blackkillerking.minetorio.screen.MetalShapingStation.MetalShapingStationScreen;
import net.blackkillerking.minetorio.registry.ModMenuTypes;
import net.blackkillerking.minetorio.screen.PrimitiveOven.PrimitiveOvenScreen;
import net.blackkillerking.minetorio.registry.ModSound;
import net.blackkillerking.minetorio.registry.ModFeatures;
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

        ModFeatures.register(modEventBus);

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
                MenuScreens.register(ModMenuTypes.KILN_CONTROLLER_MENU.get(), KilnControllerScreen::new);
            });
        }
    }
}
