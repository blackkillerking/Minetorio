package net.blackkillerking.minetorio.item.custom;

import com.google.common.base.Suppliers;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class AbstractHammerItem extends DiggerItem implements Vanishable, HammerInterface {


    private final int range;
    private final Supplier<? extends Item> bluntVariant;

    public AbstractHammerItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, int range, Supplier<? extends Item> bluntVariant,TagKey<Block> pBlocks, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, pBlocks, pProperties);
        this.range = range;
        this.bluntVariant = bluntVariant;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public Item getBluntVariant() {
        return bluntVariant.get();
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack current = itemStack.copy();
        int newDamage = current.getDamageValue() + 1;


        if (current.getMaxDamage() <= newDamage) {
            return new ItemStack(getBluntVariant());
        }

        current.setDamageValue(newDamage);
        return current;
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        super.onCraftedBy(pStack, pLevel, pPlayer);
    }
}
