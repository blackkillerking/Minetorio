package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
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
                        ModItems.BLUNT_COPPER_HAMMER.get()
                );
        this.tag(ModTags.Items.CLIPPER_ABLE_INGOTS)
                .add(
                        ModItems.TIN_INGOT.get(),
                        ModItems.ZINC_INGOT.get(),
                        ModItems.SILVER_INGOT.get()
                );
        this.tag(ModTags.Items.CLIPPERS)
                .add(
                        ModItems.COPPER_CLIPPER.get()
                );
        this.tag(ModTags.Items.HAMMERS)
                .add(
                        ModItems.COPPER_HAMMER.get()
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
        this.tag(ModTags.Items.SHEET)
                .add(
                        ModItems.TIN_SHEET.get(),
                        ModItems.ZINC_SHEET.get(),
                        ModItems.SILVER_SHEET.get()
                );
        this.tag(ModTags.Items.METAL_WORKING_TOOLS)
                .add(
                        ModItems.COPPER_CLIPPER.get(),
                        ModItems.COPPER_HAMMER.get(),
                        ModItems.COPPER_ROLLER.get(),
                        ModItems.COPPER_SOLID_CONE.get(),
                        ModItems.COPPER_HOLLOW_CONE.get()
                );
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
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
