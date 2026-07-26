package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.fluid.ModFluidTypes;
import net.blackkillerking.minetorio.fluid.ModFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, Minetorio.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(FluidTags.LAVA)
                .add(ModFluids.SOURCE_CRUDE_OIL.get())
                .add(ModFluids.FLOWING_CRUDE_OIL.get());
        tag(FluidTags.WATER)
                .add(ModFluids.SOURCE_TANNIN.get())
                .add(ModFluids.FLOWING_TANNIN.get());
    }
}
