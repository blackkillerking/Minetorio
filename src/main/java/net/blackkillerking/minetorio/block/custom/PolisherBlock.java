package net.blackkillerking.minetorio.block.custom;

import com.google.common.base.Suppliers;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.function.Supplier;

public class PolisherBlock extends Block {

    private int uses = 0;
    public int maxUses = 10;

    public PolisherBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide()){
            return InteractionResult.FAIL;
        }
        Block block = pState.getBlock();
        boolean isMainHand = pHand.equals(InteractionHand.MAIN_HAND);
        boolean isPolishable = pPlayer.getMainHandItem().is(ModTags.Items.POLISHABLE_TOOLS);
        boolean isStone = pPlayer.getMainHandItem().is(Items.STONE);
        boolean isNotSneaking = !pPlayer.isCrouching();
        if(block == ModBlocks.POLISHER.get()){
            if(uses < maxUses && isMainHand && isPolishable && isNotSneaking){
                uses++;
                if(uses >= maxUses){
                    pLevel.setBlock(pPos, ModBlocks.BROKEN_POLISHER.get().defaultBlockState(), 3);
                    uses = 0;
                    return InteractionResult.FAIL;
                }
                pPlayer.getInventory().add(getTool(pPlayer.getMainHandItem().getItem()));
                pPlayer.getMainHandItem().shrink(1);
                return InteractionResult.SUCCESS;
            }
        } else if (block == ModBlocks.BROKEN_POLISHER.get()){
            if(isMainHand && isStone && isNotSneaking){
                pPlayer.getMainHandItem().shrink(1);
                pLevel.setBlock(pPos, ModBlocks.POLISHER.get().defaultBlockState(), 3);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.FAIL;
    }

    private static final Supplier<Map<Item, Item>> TOOL_SWAP = Suppliers.memoize(() -> Map.of(
            ModItems.BLUNT_COPPER_CLIPPER.get(), ModItems.COPPER_CLIPPER.get(),
            ModItems.BLUNT_COPPER_HAMMER.get(), ModItems.COPPER_HAMMER.get(),
            ModItems.BLUNT_COPPER_ROLLER.get(), ModItems.COPPER_ROLLER.get(),
            ModItems.BLUNT_COPPER_SOLID_CONE.get(), ModItems.COPPER_SOLID_CONE.get(),
            ModItems.BLUNT_COPPER_HOLLOW_CONE.get(), ModItems.COPPER_HOLLOW_CONE.get()
    ));

    private ItemStack getTool(Item bluntTool) {
        Item swapedTool = TOOL_SWAP.get().get(bluntTool);
        if(swapedTool == null){
            return ItemStack.EMPTY;
        }
        ItemStack newTool = new ItemStack(swapedTool, 1);
        newTool.setDamageValue(swapedTool.getMaxDamage()/2);
        return newTool;
    }
}
