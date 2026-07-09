package net.blackkillerking.minetorio.item.custom;

import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;


public class CopperHammerItem extends AbstractHammerItem {


    public CopperHammerItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, 1, ModItems.BLUNT_COPPER_HAMMER,BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }
}
