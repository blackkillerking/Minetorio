package net.blackkillerking.minetorio.item.custom;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.particle.ModParticals;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class AbstractHeatedMetal extends Item {

    public AbstractHeatedMetal(Properties pProperties) {
        super(pProperties);
    }

    public static Map<String, ItemLike> getItem = Map.of(
            "tin_ingot", ModItems.TIN_INGOT.get(),
            "tin_rod", ModItems.TIN_ROD.get(),
            "tin_sheet", ModItems.TIN_SHEET.get()
    );

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        long gameTime = pLevel.getGameTime();
        if((int) gameTime % 20 == 0 && !pLevel.isClientSide){
            pEntity.setSecondsOnFire(2);
            if(pIsSelected){
                ServerLevel level = (ServerLevel) pLevel;
                level.sendParticles(ModParticals.HEATED_METAL_PARTICLES.get(),
                        pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 4, 1 ,1 ,1 , 0.1);
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack pStack, ItemEntity pEntity) {
        Level pLevel = pEntity.level();
        if(pLevel.isClientSide){
            return false;
        }
        CompoundTag tag = pStack.getOrCreateTag();

        if(!pStack.hasTag()){
            return false;
        }

        long gameTime = pLevel.getGameTime();
        if((int) gameTime % 20 == 0){
            ServerLevel level = (ServerLevel) pLevel;
            level.sendParticles(ModParticals.HEATED_METAL_PARTICLES.get(),
                    pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 4, 1 ,1 ,1 , 0.1);
        }


        int coolingTime = tag.getInt("cooling_time");

        if (coolingTime < 0) {
            int count = pStack.getCount();
            String metalType = tag.getString("metal_type");
            String metalForm = pStack.getItem().toString().split("_")[1];
            ItemEntity cooledMetals = new ItemEntity(pLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ(), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Minetorio.MOD_ID, metalType + "_" + metalForm)), count));
            pEntity.kill();
            pLevel.addFreshEntity(cooledMetals);
        } else{
            tag.putInt("cooling_time", coolingTime - 1);
        }
       return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.literal("Type :" + pStack.getTag().getString("metal_type")));
            pTooltipComponents.add(Component.literal("Cooling TIme :" + pStack.getTag().getInt("cooling_time")));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.minetorio.abstract_heated_metal.no_shift_tooltip"));
        }
    }
}
