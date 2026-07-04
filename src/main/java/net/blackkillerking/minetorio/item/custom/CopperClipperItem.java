package net.blackkillerking.minetorio.item.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CopperClipperItem extends Item {

    public CopperClipperItem(Properties pProperties) {
        super(pProperties
                .durability(128));
    }


    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack currentClipper = itemStack.copy();
        int newDamage = currentClipper.getDamageValue() + 1;

        if (currentClipper.getMaxDamage() <= newDamage){
            return ItemStack.EMPTY;
        }

        currentClipper.setDamageValue(newDamage);
        return currentClipper;
    }
}
