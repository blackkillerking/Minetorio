package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.custom.PolisherBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

        getVariantBuilder(ModBlocks.POLISHER.get()).forAllStates(state -> {
            if(!state.getValue(PolisherBlock.BROKEN)){
                return new ConfiguredModel[] {
                        new ConfiguredModel(new ModelFile.UncheckedModelFile(modLoc("block/polisher")))
                };
            }
            else{
                return new ConfiguredModel[] {
                        new ConfiguredModel(new ModelFile.UncheckedModelFile(modLoc("block/broken_polisher")))
                };
            }
        });


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

    private void axisBlockWithStates(RegistryObject<Block> block, BooleanProperty blockProperties, String stateOneName, String stateTwoName){
        getVariantBuilder(block.get()).forAllStates(state -> {
            if(state.getValue(blockProperties)){
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeBottomTop(
                        stateOneName,
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + stateOneName + "_side"),
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + stateOneName + "_top"),
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + stateOneName + "_top")
                ))};
            } else{
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeBottomTop(
                        stateTwoName,
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + stateTwoName + "_side"),
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + stateTwoName + "_top"),
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + stateTwoName + "_top")
                ))};

            }
        });
    }

    private void axisBlock(RegistryObject<Block> block, String name){
        simpleBlock(block.get(),
                models().cubeBottomTop(
                        name,
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + name + "_side"),
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + name + "_top"),
                        new ResourceLocation(Minetorio.MOD_ID, "block/" + name + "_top")
                ));
    }


}
