package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Minetorio.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.RAW_TIN);
        simpleItem(ModItems.RAW_ZINC);
        simpleItem(ModItems.RAW_SILVER);

        simpleItem(ModItems.TIN_INGOT);
        simpleItem(ModItems.ZINC_INGOT);
        simpleItem(ModItems.SILVER_INGOT);

        simpleItem(ModItems.TIN_ROD);
        simpleItem(ModItems.ZINC_ROD);
        simpleItem(ModItems.SILVER_ROD);

        simpleItem(ModItems.TIN_SHEET);
        simpleItem(ModItems.ZINC_SHEET);
        simpleItem(ModItems.SILVER_SHEET);

        simpleItem(ModItems.COPPER_CLIPPER);
        simpleItem(ModItems.BLUNT_COPPER_CLIPPER);

        handHeldItem(ModItems.COPPER_HAMMER);
        simpleItem(ModItems.BLUNT_COPPER_HAMMER);

        simpleItem(ModItems.HEATED_METAL);

        withExistingParent(
                ModBlocks.POLISHER.getId().getPath(),
                new ResourceLocation(Minetorio.MOD_ID, "block/" + ModBlocks.POLISHER.getId().getPath())
        );

        customBBBlock(ModBlocks.POLISHER.get());
    }

    private ItemModelBuilder simpleItem (RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Minetorio.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handHeldItem (RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Minetorio.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder customBBBlock(Block block){
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(),
                new ResourceLocation(Minetorio.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
    }
}
