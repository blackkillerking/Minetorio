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

        simpleItem(ModItems.WOOD_BARK);

        simpleItem(ModItems.OLIVE);
        simpleItem(ModItems.OLIVE_OIL);
        simpleItem(ModItems.OLIVE_SEEDS);

        simpleItem(ModItems.FLINT_FRAGMENT);
        simpleItem(ModItems.BASALT_ROCK);
        simpleItem(ModItems.SHARPENED_FLINT);
        simpleItem(ModItems.SHARPENED_BASALT);

        simpleItem(ModItems.ANIMAL_HIDE);
        simpleItem(ModItems.HIDE);
        simpleItem(ModItems.SOAKED_HIDE);
        simpleItem(ModItems.DRIED_HIDE);
        simpleItem(ModItems.TREATED_HIDE);

        simpleItem(ModItems.PLANT_FIBER);
        simpleItem(ModItems.FIRM_PLANT_FIBER);
        simpleItem(ModItems.STIFF_STICK);

        simpleItem(ModItems.WOODEN_AXE_HEAD);
        simpleItem(ModItems.FLINT_PICKAXE_HEAD);
        simpleItem(ModItems.FLINT_AXE_HEAD);
        simpleItem(ModItems.FLINT_SHOVEL_HEAD);
        simpleItem(ModItems.FLINT_HOE_HEAD);
        simpleItem(ModItems.BASALT_PICKAXE_HEAD);
        simpleItem(ModItems.BASALT_AXE_HEAD);
        simpleItem(ModItems.BASALT_SHOVEL_HEAD);
        simpleItem(ModItems.BASALT_HOE_HEAD);

        simpleItem(ModItems.FLINT_BASALT_PICKAXE_BODY);
        simpleItem(ModItems.FLINT_BASALT_AXE_BODY);
        simpleItem(ModItems.FLINT_BASALT_SHOVEL_HOE_BODY);
        simpleItem(ModItems.BASALT_HAMMER_BODY);

        handHeldItem(ModItems.FLINT_PICKAXE);
        handHeldItem(ModItems.FLINT_AXE);
        handHeldItem(ModItems.FLINT_SHOVEL);
        handHeldItem(ModItems.FLINT_HOE);
        handHeldItem(ModItems.FLINT_KNIFE);
        handHeldItem(ModItems.BASALT_PICKAXE);
        handHeldItem(ModItems.BASALT_AXE);
        handHeldItem(ModItems.BASALT_SHOVEL);
        handHeldItem(ModItems.BASALT_HOE);
        handHeldItem(ModItems.BASALT_KNIFE);
        handHeldItem(ModItems.CRACKED_BASALT_HAMMER);

        simpleItem(ModItems.RAW_TIN);
        simpleItem(ModItems.RAW_ZINC);
        simpleItem(ModItems.RAW_SILVER);

        simpleItem(ModItems.TIN_INGOT);
        simpleItem(ModItems.TIN_NUGGET);
        simpleItem(ModItems.TIN_SHEET);
        simpleItem(ModItems.TIN_BAR);
        simpleItem(ModItems.TIN_PANEL);
        simpleItem(ModItems.TIN_ROD);
        simpleItem(ModItems.TIN_WIRE);
        simpleItem(ModItems.TIN_STRIPE);
        simpleItem(ModItems.TIN_SCREWS);
        simpleItem(ModItems.TIN_COLUMN);
        simpleItem(ModItems.TIN_RING);

        simpleItem(ModItems.ZINC_INGOT);
        simpleItem(ModItems.ZINC_NUGGET);
        simpleItem(ModItems.ZINC_SHEET);
        simpleItem(ModItems.ZINC_BAR);
        simpleItem(ModItems.ZINC_PANEL);
        simpleItem(ModItems.ZINC_ROD);
        simpleItem(ModItems.ZINC_WIRE);
        simpleItem(ModItems.ZINC_STRIPE);
        simpleItem(ModItems.ZINC_SCREWS);
        simpleItem(ModItems.ZINC_COLUMN);
        simpleItem(ModItems.ZINC_RING);

        simpleItem(ModItems.SILVER_INGOT);
        simpleItem(ModItems.SILVER_NUGGET);
        simpleItem(ModItems.SILVER_SHEET);
        simpleItem(ModItems.SILVER_BAR);
        simpleItem(ModItems.SILVER_PANEL);
        simpleItem(ModItems.SILVER_ROD);
        simpleItem(ModItems.SILVER_WIRE);
        simpleItem(ModItems.SILVER_STRIPE);
        simpleItem(ModItems.SILVER_SCREWS);
        simpleItem(ModItems.SILVER_COLUMN);
        simpleItem(ModItems.SILVER_RING);

        simpleItem(ModItems.IRON_SHEET);
        simpleItem(ModItems.IRON_BAR);
        simpleItem(ModItems.IRON_PANEL);
        simpleItem(ModItems.IRON_ROD);
        simpleItem(ModItems.IRON_WIRE);
        simpleItem(ModItems.IRON_STRIPE);
        simpleItem(ModItems.IRON_SCREWS);
        simpleItem(ModItems.IRON_COLUMN);
        simpleItem(ModItems.IRON_RING);

        simpleItem(ModItems.COPPER_NUGGET);
        simpleItem(ModItems.COPPER_SHEET);
        simpleItem(ModItems.COPPER_BAR);
        simpleItem(ModItems.COPPER_PANEL);
        simpleItem(ModItems.COPPER_ROD);
        simpleItem(ModItems.COPPER_WIRE);
        simpleItem(ModItems.COPPER_STRIPE);
        simpleItem(ModItems.COPPER_SCREWS);
        simpleItem(ModItems.COPPER_COLUMN);
        simpleItem(ModItems.COPPER_RING);

        simpleItem(ModItems.GOLD_SHEET);
        simpleItem(ModItems.GOLD_BAR);
        simpleItem(ModItems.GOLD_PANEL);
        simpleItem(ModItems.GOLD_ROD);
        simpleItem(ModItems.GOLD_WIRE);
        simpleItem(ModItems.GOLD_STRIPE);
        simpleItem(ModItems.GOLD_SCREWS);
        simpleItem(ModItems.GOLD_COLUMN);
        simpleItem(ModItems.GOLD_RING);

        simpleItem(ModItems.COPPER_CLIPPER);
        simpleItem(ModItems.BLUNT_COPPER_CLIPPER);

        handHeldItem(ModItems.COPPER_HAMMER);
        simpleItem(ModItems.BLUNT_COPPER_HAMMER);

        simpleItem(ModItems.COPPER_ROLLER);
        simpleItem(ModItems.BLUNT_COPPER_ROLLER);

        simpleItem(ModItems.COPPER_SOLID_CONE);
        simpleItem(ModItems.BLUNT_COPPER_SOLID_CONE);

        simpleItem(ModItems.COPPER_HOLLOW_CONE);
        simpleItem(ModItems.BLUNT_COPPER_HOLLOW_CONE);

        simpleItem(ModItems.HEATED_INGOT);
        simpleItem(ModItems.HEATED_SHEET);
        simpleItem(ModItems.HEATED_BAR);
        simpleItem(ModItems.HEATED_PANEL);
        simpleItem(ModItems.HEATED_ROD);
        simpleItem(ModItems.HEATED_WIRE);
        simpleItem(ModItems.HEATED_STRIPE);
        simpleItem(ModItems.HEATED_SCREWS);
        simpleItem(ModItems.HEATED_COLUMN);
        simpleItem(ModItems.HEATED_RING);

        simpleItem(ModItems.CRUDE_OIL_BUCKET);
        simpleItem(ModItems.TANNIN_BUCKET);

        customBBBlock(ModBlocks.POLISHER.get());
        customBBBlock(ModBlocks.BROKEN_POLISHER.get());
        customBBBlock(ModBlocks.METAL_SHAPING_STATION.get());
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
