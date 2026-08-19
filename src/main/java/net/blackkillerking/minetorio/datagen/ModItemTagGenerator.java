package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, tagLookupCompletableFuture, Minetorio.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Items.POLISHABLE_TOOLS)
                .add(
                        ModItems.BLUNT_COPPER_CLIPPER.get(),
                        ModItems.BLUNT_COPPER_HAMMER.get(),
                        ModItems.BLUNT_COPPER_ROLLER.get(),
                        ModItems.BLUNT_COPPER_SOLID_CONE.get(),
                        ModItems.BLUNT_COPPER_HOLLOW_CONE.get()
                );
        this.tag(ModTags.Items.CLIPPERS)
                .add(
                        ModItems.COPPER_CLIPPER.get()
                );
        this.tag(ModTags.Items.HAMMERS)
                .add(
                        ModItems.COPPER_HAMMER.get(),
                        ModItems.BASALT_HAMMER.get()
                );
        this.tag(ModTags.Items.ROLLERS)
                .add(
                        ModItems.COPPER_ROLLER.get()
                );
        this.tag(ModTags.Items.SOLID_CONES)
                .add(
                        ModItems.COPPER_SOLID_CONE.get()
                );
        this.tag(ModTags.Items.HOLLOW_CONES)
                .add(
                        ModItems.COPPER_HOLLOW_CONE.get()
                );
        this.tag(ModTags.Items.METAL_WORKING_TOOLS)
                .addTag(ModTags.Items.HAMMERS)
                .addTag(ModTags.Items.CLIPPERS)
                .addTag(ModTags.Items.ROLLERS)
                .addTag(ModTags.Items.SOLID_CONES)
                .addTag(ModTags.Items.HOLLOW_CONES);

        this.tag(ModTags.Items.HEATED_METALS)
                .add(
                      ModItems.HEATED_INGOT.get(),
                      ModItems.HEATED_SHEET.get(),
                      ModItems.HEATED_BAR.get(),
                      ModItems.HEATED_PANEL.get(),
                      ModItems.HEATED_ROD.get(),
                      ModItems.HEATED_WIRE.get(),
                      ModItems.HEATED_STRIPE.get(),
                      ModItems.HEATED_SCREWS.get(),
                      ModItems.HEATED_COLUMN.get(),
                      ModItems.HEATED_RING.get()
                );
        this.tag(ModTags.Items.PLANT_FIBERS)
                .add(
                        Items.WHEAT,
                        Items.GRASS,
                        Items.TALL_GRASS,
                        Items.SEAGRASS,
                        ModItems.PLANT_FIBER.get()
                );
        this.tag(ModTags.Items.ORES)
                .add(
                        Items.RAW_COPPER,
                        Items.RAW_IRON,
                        Items.RAW_GOLD,
                        ModItems.RAW_TIN.get(),
                        ModItems.RAW_ZINC.get(),
                        ModItems.RAW_SILVER.get()
                );
        this.tag(ModTags.Items.METAL_DERIVATIVES)
                .add(
                ModItems.TIN_INGOT.get(),
                ModItems.TIN_SHEET.get(),
                ModItems.TIN_BAR.get(),
                ModItems.TIN_STRIPE.get(),
                ModItems.TIN_ROD.get(),
                ModItems.TIN_PANEL.get(),
                ModItems.TIN_WIRE.get(),
                ModItems.TIN_SCREWS.get(),
                ModItems.TIN_COLUMN.get(),
                ModItems.TIN_RING.get(),

                ModItems.ZINC_INGOT.get(),
                ModItems.ZINC_SHEET.get(),
                ModItems.ZINC_BAR.get(),
                ModItems.ZINC_STRIPE.get(),
                ModItems.ZINC_ROD.get(),
                ModItems.ZINC_PANEL.get(),
                ModItems.ZINC_WIRE.get(),
                ModItems.ZINC_SCREWS.get(),
                ModItems.ZINC_COLUMN.get(),
                ModItems.ZINC_RING.get(),

                ModItems.SILVER_INGOT.get(),
                ModItems.SILVER_SHEET.get(),
                ModItems.SILVER_BAR.get(),
                ModItems.SILVER_STRIPE.get(),
                ModItems.SILVER_ROD.get(),
                ModItems.SILVER_PANEL.get(),
                ModItems.SILVER_WIRE.get(),
                ModItems.SILVER_SCREWS.get(),
                ModItems.SILVER_COLUMN.get(),
                ModItems.SILVER_RING.get(),

                Items.IRON_INGOT,
                ModItems.IRON_SHEET.get(),
                ModItems.IRON_BAR.get(),
                ModItems.IRON_STRIPE.get(),
                ModItems.IRON_ROD.get(),
                ModItems.IRON_PANEL.get(),
                ModItems.IRON_WIRE.get(),
                ModItems.IRON_SCREWS.get(),
                ModItems.IRON_COLUMN.get(),
                ModItems.IRON_RING.get(),

                Items.COPPER_INGOT,
                ModItems.COPPER_SHEET.get(),
                ModItems.COPPER_BAR.get(),
                ModItems.COPPER_STRIPE.get(),
                ModItems.COPPER_ROD.get(),
                ModItems.COPPER_PANEL.get(),
                ModItems.COPPER_WIRE.get(),
                ModItems.COPPER_SCREWS.get(),
                ModItems.COPPER_COLUMN.get(),
                ModItems.COPPER_RING.get(),

                Items.GOLD_INGOT,
                ModItems.GOLD_SHEET.get(),
                ModItems.GOLD_BAR.get(),
                ModItems.GOLD_STRIPE.get(),
                ModItems.GOLD_ROD.get(),
                ModItems.GOLD_PANEL.get(),
                ModItems.GOLD_WIRE.get(),
                ModItems.GOLD_SCREWS.get(),
                ModItems.GOLD_COLUMN.get(),
                ModItems.GOLD_RING.get()
                );
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
