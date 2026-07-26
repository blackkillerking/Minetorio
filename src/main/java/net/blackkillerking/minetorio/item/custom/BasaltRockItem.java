package net.blackkillerking.minetorio.item.custom;

import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.Tags;

import java.util.Random;
import java.util.logging.Level;

public class BasaltRockItem extends Item {
    public BasaltRockItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()){
            return InteractionResult.FAIL;
        }
        ServerLevel level = (ServerLevel) pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        boolean isStone = level.getBlockState(blockPos).is(Blocks.STONE);
        ItemEntity sharpenedBasalt = new ItemEntity(level, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), new ItemStack(ModItems.SHARPENED_BASALT.get(), 1));
        Random rand = new Random();
        if(isStone){
            pContext.getItemInHand().shrink(1);
            if(rand.nextInt(4) == 0){
                level.addFreshEntity(sharpenedBasalt);
                return InteractionResult.CONSUME;
            } else {
                level.playLocalSound(blockPos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1, 1, false);
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.FAIL;
    }
}
