package net.blackkillerking.minetorio.item;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Minetorio.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MINETORIO_INGREDIENTS =
            CREATIVE_MODE_TABS.register("minetorio_ingredients", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SILVER_INGOT.get()))
                    .title(Component.translatable("creativetab.minetorio_ingredients"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_TIN.get());
                        output.accept(ModItems.TIN_INGOT.get());
                        output.accept(ModItems.TIN_ROD.get());
                        output.accept(ModItems.TIN_SHEET.get());

                        output.accept(ModItems.RAW_ZINC.get());
                        output.accept(ModItems.ZINC_INGOT.get());
                        output.accept(ModItems.ZINC_ROD.get());
                        output.accept(ModItems.ZINC_SHEET.get());

                        output.accept(ModItems.RAW_SILVER.get());
                        output.accept(ModItems.SILVER_INGOT.get());
                        output.accept(ModItems.SILVER_ROD.get());
                        output.accept(ModItems.SILVER_SHEET.get());

                        output.accept(ModItems.COPPER_CLIPPER.get());
                        output.accept(ModItems.BLUNT_COPPER_CLIPPER.get());

                        output.accept(ModItems.COPPER_HAMMER.get());
                        output.accept(ModItems.BLUNT_COPPER_HAMMER.get());

                        output.accept(ModItems.HEATED_METAL.get());

                        output.accept(ModBlocks.TIN_ORE.get());
                        output.accept(ModBlocks.ZINC_ORE.get());
                        output.accept(ModBlocks.SILVER_ORE.get());

                        output.accept(ModBlocks.RAW_TIN_BLOCK.get());
                        output.accept(ModBlocks.RAW_ZINC_BLOCK.get());
                        output.accept(ModBlocks.RAW_SILVER_BLOCK.get());

                        output.accept(ModBlocks.TIN_BLOCK.get());
                        output.accept(ModBlocks.ZINC_BLOCK.get());
                        output.accept(ModBlocks.SILVER_BLOCK.get());

                        output.accept(ModBlocks.POLISHER.get());
                        output.accept(ModBlocks.METAL_SHAPING_STATION.get());

                        output.accept(ModItems.CRUDE_OIL_BUCKET.get());


                    })
                    .build());

    public static void register (IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
