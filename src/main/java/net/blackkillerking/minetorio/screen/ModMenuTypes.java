package net.blackkillerking.minetorio.screen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.screen.KilnController.DefaultKilnControllerMenu;
import net.blackkillerking.minetorio.screen.KilnController.DefaultKilnControllerScreen;
import net.blackkillerking.minetorio.screen.KilnController.FormedKilnControllerMenu;
import net.blackkillerking.minetorio.screen.MetalShapingStation.MetalShapingStationMenu;
import net.blackkillerking.minetorio.screen.PrimitiveOven.PrimitiveOvenMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Minetorio.MOD_ID);


    public static final RegistryObject<MenuType<MetalShapingStationMenu>> METAL_SHAPING_STATION_MENU =
            registerMenuType(MetalShapingStationMenu::new, "metal_shaping_station_menu");

    public static final RegistryObject<MenuType<PrimitiveOvenMenu>> PRIMITIVE_OVEN_MENU =
            registerMenuType(PrimitiveOvenMenu::new, "primitive_oven_menu");

    public static final RegistryObject<MenuType<FormedKilnControllerMenu>> FORMED_KILN_CONTROLLER_MENU =
            registerMenuType(FormedKilnControllerMenu::new, "formed_kiln_controller_menu");

    public static final RegistryObject<MenuType<DefaultKilnControllerMenu>> DEFAULT_KILN_CONTROLLER_MENU =
            registerMenuType(DefaultKilnControllerMenu::new, "default_kiln_controller_menu");



    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register (IEventBus eventBus){
        MENUS.register(eventBus);
    }
}



