package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.datagen.builders.HeatedMetalCookingBuilder;
import net.blackkillerking.minetorio.datagen.builders.MetalShapingRecipeBuilder;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProiver extends RecipeProvider {

    public ModRecipeProiver(PackOutput pOutput) {
        super(pOutput);
    }


    private final List<ItemLike> tinSmeltables = List.of(
            ModItems.RAW_TIN.get(),
            ModBlocks.TIN_ORE.get()
    );

    private final List<ItemLike> zincSmeltables = List.of(
            ModItems.RAW_ZINC.get(),
            ModBlocks.ZINC_ORE.get()
    );

    private final List<ItemLike> silverSmeltables = List.of(
            ModItems.RAW_SILVER.get(),
            ModBlocks.SILVER_ORE.get()
    );

    private final List<ItemLike> rawTinBlock = List.of(
            ModBlocks.RAW_TIN_BLOCK.get()
    );
    private final List<ItemLike> rawZincBlock = List.of(
            ModBlocks.RAW_ZINC_BLOCK.get()
    );

    private final List<ItemLike> rawSilverBlock = List.of(
            ModBlocks.RAW_SILVER_BLOCK.get()
    );

    private final Map<RegistryObject<Item>, RegistryObject<Item>> INGOT_TO_ROD = Map.of(
            ModItems.TIN_INGOT, ModItems.TIN_ROD,
            ModItems.ZINC_INGOT, ModItems.ZINC_ROD,
            ModItems.SILVER_INGOT, ModItems.SILVER_ROD

    );

    private final Map<RegistryObject<Item>, RegistryObject<Item>> INGOT_TO_SHEET = Map.of(
            ModItems.TIN_INGOT, ModItems.TIN_SHEET,
            ModItems.ZINC_INGOT, ModItems.ZINC_SHEET,
            ModItems.SILVER_INGOT, ModItems.SILVER_SHEET
    );


    private final List<String> metalTypes = List.of(
            "tin",
            "zinc",
            "silver",
            "iron",
            "copper",
            "gold"
    );

//    private final Map<Item, Integer> heatedMetalTypes = Map.of(
//            ModItems.HEATED_INGOT.get(), 1800,
//            ModItems.HEATED_SHEET.get(),1200,
//            ModItems.HEATED_BAR.get(),800,
//            ModItems.HEATED_STRIPE.get(),800,
//            ModItems.HEATED_PANEL.get(),1200,
//            ModItems.HEATED_ROD.get(),400,
//            ModItems.HEATED_WIRE.get(),200,
//            ModItems.HEATED_SCREWS.get(),40,
//            ModItems.HEATED_COLUMN.get(),800,
//            ModItems.HEATED_RING.get(), 40
//
//    );


    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get(),
                "minetorio:tin_ingot", "tin", "minetorio:tin_block", "tin");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.ZINC_INGOT.get(), RecipeCategory.MISC, ModBlocks.ZINC_BLOCK.get(),
                "minetorio:zinc_ingot", "zinc", "minetorio:zinc_block", "zinc");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), RecipeCategory.MISC, ModBlocks.SILVER_BLOCK.get(),
                "minetorio:silver_ingot", "silver", "minetorio:silver_block", "silver");

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_TIN.get(), RecipeCategory.MISC, ModBlocks.RAW_TIN_BLOCK.get(),
                "minetorio:raw_tin", "tin", "minetorio:raw_tin_block", "tin");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_ZINC.get(), RecipeCategory.MISC, ModBlocks.RAW_ZINC_BLOCK.get(),
                "minetorio:raw_zinc", "zinc", "minetorio:raw_zinc_block", "zinc");
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_SILVER.get(), RecipeCategory.MISC, ModBlocks.RAW_SILVER_BLOCK.get(),
                "minetorio:raw_silver", "silver", "minetorio:raw_silver_block", "silver");


        quickHeatedMetalSetup(RecipeCategory.MISC, ModItems.RAW_TIN.get(), ModBlocks.TIN_ORE.get(), ModItems.HEATED_INGOT.get(), "tin", 1800,50, 1200, pWriter, "tin");
        quickHeatedMetalSetup(RecipeCategory.MISC, ModItems.RAW_ZINC.get(), ModBlocks.ZINC_ORE.get(), ModItems.HEATED_INGOT.get(), "zinc", 1800, 70, 1200, pWriter, "zinc");
        quickHeatedMetalSetup(RecipeCategory.MISC, ModItems.RAW_SILVER.get(), ModBlocks.SILVER_ORE.get(), ModItems.HEATED_INGOT.get(), "silver", 1800, 70, 1200, pWriter, "silver");
        quickHeatedMetalSetup(RecipeCategory.MISC, Items.RAW_IRON, Blocks.IRON_ORE, ModItems.HEATED_INGOT.get(), "iron", 1800, 70, 1200, pWriter, "iron");
        quickHeatedMetalSetup(RecipeCategory.MISC, Items.RAW_COPPER, Blocks.COPPER_ORE, ModItems.HEATED_INGOT.get(), "copper", 1800, 70, 1200, pWriter, "copper");
        quickHeatedMetalSetup(RecipeCategory.MISC, Items.RAW_GOLD, Blocks.GOLD_ORE, ModItems.HEATED_INGOT.get(), "gold", 1800, 70, 1200, pWriter, "gold");

        for(String metalType : metalTypes){

            metalShapingIngotBasedRecipe(ModItems.HEATED_SHEET.get(), ModTags.Items.HAMMERS, null, 2,metalType, 1200, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_BAR.get(), ModTags.Items.CLIPPERS, null, 4,metalType, 800, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_STRIPE.get(), ModTags.Items.ROLLERS, null, 2,metalType, 800, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_ROD.get(), ModTags.Items.HAMMERS, ModTags.Items.CLIPPERS, 8,metalType, 400, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_PANEL.get(), ModTags.Items.HAMMERS, ModTags.Items.ROLLERS, 1,metalType, 1200, pWriter);
            metalShapingIngotBasedRecipe(ModItems.HEATED_WIRE.get(), ModTags.Items.CLIPPERS, ModTags.Items.ROLLERS, 16,metalType, 200, pWriter);

            metalShapingBarBasedRecipe(ModItems.HEATED_SCREWS.get(), ModTags.Items.HAMMERS, ModTags.Items.HOLLOW_CONES, 32,metalType, 40, pWriter);
            metalShapingBarBasedRecipe(ModItems.HEATED_COLUMN.get(), ModTags.Items.HAMMERS, ModTags.Items.SOLID_CONES, 1,metalType, 800, pWriter);

            metalShapingSheetBasedRecipe(ModItems.HEATED_RING.get(), ModTags.Items.HAMMERS, ModTags.Items.HOLLOW_CONES, 32,metalType, 40, pWriter);

            Minetorio.LOGGER.info("" + ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ingot")));
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_sheet")), ModItems.HEATED_SHEET.get(), 1, metalType, 1200, 0, 300, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_bar")), ModItems.HEATED_BAR.get(), 1, metalType, 800, 0, 200, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_stripe")), ModItems.HEATED_STRIPE.get(), 1, metalType, 800, 0, 200, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_panel")), ModItems.HEATED_PANEL.get(), 1, metalType, 1200, 0, 300, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_wire")), ModItems.HEATED_WIRE.get(), 1, metalType, 200, 0, 50, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_rod")), ModItems.HEATED_ROD.get(), 1, metalType, 400, 0, 100, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_screws")), ModItems.HEATED_SCREWS.get(), 1, metalType, 40, 0, 20, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_column")), ModItems.HEATED_COLUMN.get(), 1, metalType, 800, 0, 200, pWriter, metalType);
            heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ring")), ModItems.HEATED_RING.get(), 1, metalType, 40, 0, 20, pWriter, metalType);
            if(!(metalType == "iron" || metalType == "gold" || metalType == "copper")){
                heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_ingot")), ModItems.HEATED_INGOT.get(), 1, metalType, 1800, 0, 400, pWriter, metalType);
            }

        }
        heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", Items.IRON_INGOT, ModItems.HEATED_INGOT.get(), 1, "iron", 1800, 0, 400, pWriter, "iron");
        heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", Items.GOLD_INGOT, ModItems.HEATED_INGOT.get(), 1, "gold", 1800, 0, 400, pWriter, "gold");
        heatedIngotFromBlastingBuilder(RecipeCategory.MISC, "_from_reheating", Items.COPPER_INGOT, ModItems.HEATED_INGOT.get(), 1, "copper", 1800, 0, 400, pWriter, "copper");

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {

            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    private static void quickHeatedMetalSetup(RecipeCategory pCategory, ItemLike pIngredient_1, ItemLike pIngredient_2, Item pResult, String pType, int pCoolingTime, float pExperience, int pCookingTime, Consumer<FinishedRecipe> FinishedRecipeConsumer, String pGroup){
        heatedIngotFromSmeltingBuilder(pCategory, "_from_smelting", pIngredient_1, pResult, 1, pType, pCoolingTime,pExperience, pCookingTime, FinishedRecipeConsumer, pGroup);
        heatedIngotFromSmeltingBuilder(pCategory, "_from_smelting", pIngredient_2, pResult, 1, pType, pCoolingTime,pExperience, pCookingTime, FinishedRecipeConsumer, pGroup);
        heatedIngotFromBlastingBuilder(pCategory, "_from_blasting", pIngredient_1, pResult, 1, pType, (int) (pCoolingTime * 1.2),(float) (pExperience * 1.45), (int) (pCookingTime * 0.6), FinishedRecipeConsumer, pGroup);
        heatedIngotFromBlastingBuilder(pCategory, "_from_blasting", pIngredient_2, pResult, 1, pType, (int) (pCoolingTime * 1.2),(float) (pExperience * 1.45), (int) (pCookingTime * 0.6), FinishedRecipeConsumer, pGroup);
    }

    private static void heatedIngotFromSmeltingBuilder(RecipeCategory pCategory, String pRecipeName, ItemLike pIngredient, Item pResult, int pCount, String pType, int pCoolingTime, float pExperience, int pCookingTime, Consumer<FinishedRecipe> FinishedRecipeConsumer, String pGroup){
        HeatedMetalCookingBuilder
                .smelting(pCategory, Ingredient.of(pIngredient), pResult, pCount,  pType, pCoolingTime,pExperience, pCookingTime)
                .group(pGroup)
                .unlockedBy("has_raw_tin", has(pIngredient))
                .save(FinishedRecipeConsumer, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(pIngredient));
    }

    private static void heatedIngotFromBlastingBuilder(RecipeCategory pCategory, String pRecipeName, ItemLike pIngredient, Item pResult, int pCount, String pType, int pCoolingTime, float pExperience, int pCookingTime, Consumer<FinishedRecipe> FinishedRecipeConsumer, String pGroup){
        HeatedMetalCookingBuilder
                .blasting(pCategory, Ingredient.of(pIngredient), pResult, pCount,  pType, pCoolingTime, pExperience, pCookingTime)
                .group(pGroup)
                .unlockedBy("has_raw_tin", has(pIngredient))
                .save(FinishedRecipeConsumer, Minetorio.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(pIngredient));
    }

    private void metalShapingIngotBasedRecipe(Item pItem, TagKey<Item> pTools_1, @Nullable TagKey<Item> pTools_2, int pCount, String pMetalType, int pCoolingTime, Consumer<FinishedRecipe> pWriter) {
        CompoundTag metalData = new CompoundTag();
        metalData.putString("metal_type", pMetalType);

        List<Ingredient> heatedIngotShapingRecipe = new ArrayList<>();
        heatedIngotShapingRecipe.add(Ingredient.of(pTools_1));
        heatedIngotShapingRecipe.add(pTools_2 == null ? Ingredient.EMPTY : Ingredient.of(pTools_2));
        heatedIngotShapingRecipe.add(PartialNBTIngredient.of(metalData, ModItems.HEATED_INGOT.get()));

        Minetorio.LOGGER.info("" + heatedIngotShapingRecipe.get(2).getItems()[0].getTag().getString("metal_type")); //Will it return correctly?
        Minetorio.LOGGER.info("" + ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, pMetalType + "_ingot"))); //Will it get it correctly?

        new MetalShapingRecipeBuilder(pItem, heatedIngotShapingRecipe, pCount, pCoolingTime)
                .unlockedBy("has_" + pMetalType + "_ingot", has(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, pMetalType + "_ingot"))))
                .save(pWriter);
    }

    private void metalShapingBarBasedRecipe(Item pItem, TagKey<Item> pTools_1, @Nullable TagKey<Item> pTools_2, int pCount, String pMetalType, int pCoolingTime, Consumer<FinishedRecipe> pWriter) {
        CompoundTag metalData = new CompoundTag();
        metalData.putString("metal_type", pMetalType);

        List<Ingredient> heatedBarShapingRecipe = new ArrayList<>();
        heatedBarShapingRecipe.add(Ingredient.of(pTools_1));
        heatedBarShapingRecipe.add(pTools_2 == null ? Ingredient.EMPTY : Ingredient.of(pTools_2));
        heatedBarShapingRecipe.add(PartialNBTIngredient.of(metalData, ModItems.HEATED_BAR.get()));

        new MetalShapingRecipeBuilder(pItem, heatedBarShapingRecipe, pCount, pCoolingTime)
                .unlockedBy("has_" + pMetalType + "_bar", has(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, pMetalType + "_bar"))))
                .save(pWriter);
    }

    private void metalShapingSheetBasedRecipe(Item pItem, TagKey<Item> pTools_1, @Nullable TagKey<Item> pTools_2, int pCount, String pMetalType, int pCoolingTime, Consumer<FinishedRecipe> pWriter) {
        CompoundTag metalData = new CompoundTag();
        metalData.putString("metal_type", pMetalType);

        List<Ingredient> heatedSheetShapingRecipe = new ArrayList<>();
        heatedSheetShapingRecipe.add(Ingredient.of(pTools_1));
        heatedSheetShapingRecipe.add(pTools_2 == null ? Ingredient.EMPTY : Ingredient.of(pTools_2));
        heatedSheetShapingRecipe.add(PartialNBTIngredient.of(metalData, ModItems.HEATED_SHEET.get()));

        new MetalShapingRecipeBuilder(pItem, heatedSheetShapingRecipe, pCount, pCoolingTime)
                .unlockedBy("has_" + pMetalType + "_sheet", has(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, pMetalType + "_sheet"))))
                .save(pWriter);
    }
}