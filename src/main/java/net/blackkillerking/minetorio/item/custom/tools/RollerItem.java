package net.blackkillerking.minetorio.item.custom.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RollerItem extends Item {

    private final Item bluntVariant;

    public RollerItem(Item bluntVariant, Properties pProperties) {
        super(pProperties);
        this.bluntVariant = bluntVariant;
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
            return new ItemStack(bluntVariant);
        }

        currentClipper.setDamageValue(newDamage);
        return currentClipper;
    }
}
