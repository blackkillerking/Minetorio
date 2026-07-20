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

                        output.accept(ModItems.TIN_SHEET.get());
                        output.accept(ModItems.TIN_BAR.get());
                        output.accept(ModItems.TIN_STRIPE.get());
                        output.accept(ModItems.TIN_ROD.get());
                        output.accept(ModItems.TIN_PANEL.get());
                        output.accept(ModItems.TIN_WIRE.get());
                        output.accept(ModItems.TIN_SCREWS.get());
                        output.accept(ModItems.TIN_COLUMN.get());
                        output.accept(ModItems.TIN_RING.get());

                        output.accept(ModItems.RAW_ZINC.get());
                        output.accept(ModItems.ZINC_INGOT.get());

                        output.accept(ModItems.ZINC_SHEET.get());
                        output.accept(ModItems.ZINC_BAR.get());
                        output.accept(ModItems.ZINC_STRIPE.get());
                        output.accept(ModItems.ZINC_ROD.get());
                        output.accept(ModItems.ZINC_PANEL.get());
                        output.accept(ModItems.ZINC_WIRE.get());
                        output.accept(ModItems.ZINC_SCREWS.get());
                        output.accept(ModItems.ZINC_COLUMN.get());
                        output.accept(ModItems.ZINC_RING.get());

                        output.accept(ModItems.RAW_SILVER.get());
                        output.accept(ModItems.SILVER_INGOT.get());

                        output.accept(ModItems.SILVER_SHEET.get());
                        output.accept(ModItems.SILVER_BAR.get());
                        output.accept(ModItems.SILVER_STRIPE.get());
                        output.accept(ModItems.SILVER_ROD.get());
                        output.accept(ModItems.SILVER_PANEL.get());
                        output.accept(ModItems.SILVER_WIRE.get());
                        output.accept(ModItems.SILVER_SCREWS.get());
                        output.accept(ModItems.SILVER_COLUMN.get());
                        output.accept(ModItems.SILVER_RING.get());

                        output.accept(ModItems.IRON_SHEET.get());
                        output.accept(ModItems.IRON_BAR.get());
                        output.accept(ModItems.IRON_STRIPE.get());
                        output.accept(ModItems.IRON_ROD.get());
                        output.accept(ModItems.IRON_PANEL.get());
                        output.accept(ModItems.IRON_WIRE.get());
                        output.accept(ModItems.IRON_SCREWS.get());
                        output.accept(ModItems.IRON_COLUMN.get());
                        output.accept(ModItems.IRON_RING.get());

                        output.accept(ModItems.COPPER_SHEET.get());
                        output.accept(ModItems.COPPER_BAR.get());
                        output.accept(ModItems.COPPER_STRIPE.get());
                        output.accept(ModItems.COPPER_ROD.get());
                        output.accept(ModItems.COPPER_PANEL.get());
                        output.accept(ModItems.COPPER_WIRE.get());
                        output.accept(ModItems.COPPER_SCREWS.get());
                        output.accept(ModItems.COPPER_COLUMN.get());
                        output.accept(ModItems.COPPER_RING.get());

                        output.accept(ModItems.GOLD_SHEET.get());
                        output.accept(ModItems.GOLD_BAR.get());
                        output.accept(ModItems.GOLD_STRIPE.get());
                        output.accept(ModItems.GOLD_ROD.get());
                        output.accept(ModItems.GOLD_PANEL.get());
                        output.accept(ModItems.GOLD_WIRE.get());
                        output.accept(ModItems.GOLD_SCREWS.get());
                        output.accept(ModItems.GOLD_COLUMN.get());
                        output.accept(ModItems.GOLD_RING.get());

                        output.accept(ModItems.COPPER_CLIPPER.get());
                        output.accept(ModItems.BLUNT_COPPER_CLIPPER.get());

                        output.accept(ModItems.COPPER_HAMMER.get());
                        output.accept(ModItems.BLUNT_COPPER_HAMMER.get());

                        output.accept(ModItems.COPPER_ROLLER.get());
                        output.accept(ModItems.BLUNT_COPPER_ROLLER.get());

                        output.accept(ModItems.COPPER_SOLID_CONE.get());
                        output.accept(ModItems.BLUNT_COPPER_SOLID_CONE.get());

                        output.accept(ModItems.COPPER_HOLLOW_CONE.get());
                        output.accept(ModItems.BLUNT_COPPER_HOLLOW_CONE.get());

                        output.accept(ModItems.HEATED_INGOT.get());

                        output.accept(ModItems.HEATED_SHEET.get());
                        output.accept(ModItems.HEATED_BAR.get());
                        output.accept(ModItems.HEATED_STRIPE.get());
                        output.accept(ModItems.HEATED_ROD.get());
                        output.accept(ModItems.HEATED_PANEL.get());
                        output.accept(ModItems.HEATED_WIRE.get());
                        output.accept(ModItems.HEATED_SCREWS.get());
                        output.accept(ModItems.HEATED_COLUMN.get());
                        output.accept(ModItems.HEATED_RING.get());

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
