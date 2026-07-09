package net.blackkillerking.minetorio.utils;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items{

        public static final TagKey<Item> POLISHABLE_TOOLS = tag("polishable_tools");
        public static final TagKey<Item> CLIPPER_ABLE_INGOTS = tag("clipper_able_ingots");
        public static final TagKey<Item> CLIPPERS = tag("clippers");
        public static final TagKey<Item> HAMMERS = tag("hammers");

        public static final TagKey<Item> SHEET = tag("sheet");

        public static final TagKey<Item> TIN_METALS = tag("tin_metals");
        public static final TagKey<Item> ZINC_METALS = tag("zinc_metals");
        public static final TagKey<Item> SILVER_METALS = tag("silver_metals");
        public static final TagKey<Item> IRON_METALS = tag("iron_metals");
        public static final TagKey<Item> COPPER_METALS = tag("copper_metals");
        public static final TagKey<Item> GOLD_METALS = tag("gold_metals");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(Minetorio.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name){
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks{

        public static final TagKey<Block> NEEDS_COPPER_TOOL = tag("need_copper_tool");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(Minetorio.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name){
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }
}
