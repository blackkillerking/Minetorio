package net.blackkillerking.minetorio.datagen;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)


public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookUpProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModRecipeProiver(packOutput));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));
        ModBlockTagGenerator blockTagGenerator = new ModBlockTagGenerator(packOutput, lookUpProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagGenerator);
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookUpProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModFluidTagsProvider(packOutput, lookUpProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(packOutput));

    }
}
