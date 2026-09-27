package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.registry.ModBlocks;
import net.blackkillerking.minetorio.block.crops.OliveBushBlock;
import net.blackkillerking.minetorio.block.blockentities.KilnControllerBlock;
import net.blackkillerking.minetorio.block.blockentities.PrimitiveOvenBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Minetorio.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        blockWithItem(ModBlocks.TIN_ORE);
        blockWithItem(ModBlocks.ZINC_ORE);
        blockWithItem(ModBlocks.SILVER_ORE);

        blockWithItem(ModBlocks.RAW_TIN_BLOCK);
        blockWithItem(ModBlocks.RAW_ZINC_BLOCK);
        blockWithItem(ModBlocks.RAW_SILVER_BLOCK);

        blockWithItem(ModBlocks.TIN_BLOCK);
        blockWithItem(ModBlocks.ZINC_BLOCK);
        blockWithItem(ModBlocks.SILVER_BLOCK);

        blockWithItem(ModBlocks.OLD_LOG);
        blockWithItem(ModBlocks.BASALT_BLOCK);
        blockWithItem(ModBlocks.FLINT_BLOCK);

        blockWithItem(ModBlocks.ASH_BLOCK);
        blockWithItem(ModBlocks.STEEL_BLOCK);
        blockWithItem(ModBlocks.CHARCOAL_BLOCK);
        blockWithItem(ModBlocks.COKE_BLOCK);

        simpleBlock(ModBlocks.POLISHER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/polisher")));
        simpleBlock(ModBlocks.BROKEN_POLISHER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/broken_polisher")));

        simpleBlock(ModBlocks.METAL_SHAPING_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/metal_shaping_station")));

        simpleBlock(ModBlocks.ANIMAL_HIDE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/animal_hide_block")));
        simpleBlock(ModBlocks.HIDE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/hide_block")));
        simpleBlock(ModBlocks.TREATED_HIDE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/treated_hide_block")));
        simpleBlock(ModBlocks.LEATHER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/leather_block")));

        makeCropModel((BushBlock) ModBlocks.OLIVE_BUSH.get(), "olive_bush_stage", "olive_bush_stage");

        horizontalFacingBlockWithOnSwitch(ModBlocks.PRIMITIVE_OVEN, PrimitiveOvenBlock.ON,"primitive_oven");
        horizontalFacingBlockWithOnSwitch(ModBlocks.KILN_CONTROLLER, KilnControllerBlock.ON, "kiln_controller");

        doorBlockWithRenderType((DoorBlock) ModBlocks.BRICK_DOOR.get(), modLoc("block/brick_door_bottom"), modLoc("block/brick_door_top"), "cutout");
        blockItem(ModBlocks.BRICK_DOOR, "_bottom");
    }

    private void makeCropModel(BushBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, BushBlock block, String modelName, String textureName){
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((OliveBushBlock) block).getAgeProperty()),
                new ResourceLocation(Minetorio.MOD_ID, "block/" + textureName + state.getValue(((OliveBushBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }

    private void blockItem (RegistryObject<Block> block, String appendix){
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("minetorio:block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + appendix));
    }

    private void blockItem (RegistryObject<Block> block){
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("minetorio:block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void horizontalFacingBlockWithOnSwitch(RegistryObject<Block> block, BooleanProperty property, String blockName){
        ResourceLocation top_and_side = modLoc("block/" + blockName + "_side");
        ResourceLocation front_off = modLoc("block/" + blockName + "_front_off");
        ResourceLocation front_on = modLoc("block/" + blockName + "_front_on");

        ModelFile off = models().orientable(blockName, top_and_side, front_off, top_and_side);
        ModelFile on = models().orientable(blockName + "_lit", top_and_side, front_on, top_and_side);

        horizontalBlock(block.get(), state ->
                state.getValue(property) ? on : off);

        itemModels().withExistingParent(blockName, modLoc("block/" + blockName));
    }


}
