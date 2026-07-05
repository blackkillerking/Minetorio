package net.blackkillerking.minetorio.block.custom;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PolisherBlock extends Block {

    public PolisherBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int slotNumber = pPlayer.getInventory().selected;

        if(pHand == InteractionHand.MAIN_HAND && !pPlayer.isCrouching()){
            if(!pLevel.isClientSide){
                if(pPlayer.getMainHandItem().getItem() == ModItems.BLUNT_COPPER_CLIPPER.get()){
                    pPlayer.getInventory().setItem(slotNumber, new ItemStack(ModItems.COPPER_CLIPPER.get(), 1));
                    Minetorio.LOGGER.info("Item got replaced");
                    return InteractionResult.SUCCESS;
                }
            }

        }
        return InteractionResult.FAIL;
    }
}
