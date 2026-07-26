package net.blackkillerking.minetorio.item.custom.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;

import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem implements Vanishable, IHammer {


    private final int range;
    private final Item bluntVariant;

    public HammerItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, int range, Item bluntVariant, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
        this.range = range;
        this.bluntVariant = bluntVariant;
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
            return new ItemStack(bluntVariant);
        }

        current.setDamageValue(newDamage);
        return current;
    }

    @Override
    public int getRange() {
        return this.range;
    }

    public static List<BlockPos> getBlocksToBreak (int range, BlockPos initalBlockPos, ServerPlayer player){
        List<BlockPos> pos = new ArrayList<>();

        BlockHitResult direction = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (direction.getType() == HitResult.Type.MISS){
            return pos;
        }
        Direction dir = direction.getDirection();
        int inx = initalBlockPos.getX();
        int iny = initalBlockPos.getY();
        int inz = initalBlockPos.getZ();

        if(dir == Direction.DOWN || dir == Direction.UP){
            for (int x = -range; x <= range ; x++) {
                for (int y = -range; y <= range ; y++) {
                    pos.add(new BlockPos(inx + x, iny, inz + y));
                }

            }
        }

        if(dir == Direction.EAST || dir == Direction.WEST){
            for (int x = -range; x <= range ; x++) {
                for (int y = -range; y <= range ; y++) {
                    pos.add(new BlockPos(inx, iny + x, inz + y));
                }

            }
        }

        if(dir == Direction.NORTH || dir == Direction.SOUTH){
            for (int x = -range; x <= range ; x++) {
                for (int y = -range; y <= range ; y++) {
                    pos.add(new BlockPos(inx + x, iny + y, inz));
                }

            }
        }
        return pos;
    }


}
