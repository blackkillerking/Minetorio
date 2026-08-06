package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.loot.AddItemModifier;
import net.blackkillerking.minetorio.loot.EntityLootModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, Minetorio.MOD_ID);
    }

    @Override
    protected void start() {

        add("plant_strings_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.2f).build()}, ModItems.PLANT_FIBER.get()));
        add("plant_strings_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build()}, ModItems.PLANT_FIBER.get()));
        add("entity_add", new EntityLootModifier(
                new LootItemCondition[] {
                        AnyOfCondition.anyOf(
                                LootTableIdCondition.builder(new ResourceLocation("minecraft", "entities/cow")),
                                LootTableIdCondition.builder(new ResourceLocation("minecraft", "entities/pig")),
                                LootTableIdCondition.builder(new ResourceLocation("minecraft", "entities/sheep"))
                        ).build()}, ModItems.ANIMAL_HIDE.get(), 1, 3));
    }
}
