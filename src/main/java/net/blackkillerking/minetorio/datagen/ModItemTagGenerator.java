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
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
