package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider
{
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Minetorio.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        ModBlocks.TIN_ORE.get(),
                        ModBlocks.ZINC_ORE.get(),
                        ModBlocks.SILVER_ORE.get()
                );
        this.tag(ModTags.Blocks.NEEDS_COPPER_TOOL)
                .add(
                        ModBlocks.TIN_ORE.get(),
                        ModBlocks.TIN_BLOCK.get()
                );
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        ModBlocks.ZINC_ORE.get(),
                        ModBlocks.ZINC_BLOCK.get(),
                        ModBlocks.SILVER_BLOCK.get(),
                        ModBlocks.SILVER_ORE.get()
                );
        this.tag(ModTags.Blocks.NON_SOLID)
                .add(
                        ModBlocks.CRUDE_OIL_BLOCK.get(),
                        Blocks.AIR,
                        Blocks.WATER,
                        Blocks.LAVA
                );


    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
