package net.blackkillerking.minetorio.item.custom;

import net.blackkillerking.minetorio.particle.ModParticals;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeatedMetalItem extends Item {
    public HeatedMetalItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if(!pLevel.isClientSide){
            if(pIsSelected && (int) pLevel.getGameTime() % 20 == 0){
                ServerLevel level = (ServerLevel) pLevel;
                level.sendParticles(ModParticals.HEATED_METAL_PARTICLES.get(),
                        pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 4, 1 ,1 ,1 , 0.25);
            }


        }
        pEntity.setSecondsOnFire(2);
    }
}
