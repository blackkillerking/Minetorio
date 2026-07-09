package net.blackkillerking.minetorio.block.custom;

import com.google.common.base.Suppliers;
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

    public static final BooleanProperty BROKEN = BooleanProperty.create("broken");
    private int uses = 0;
    public int maxUses = 10;


    public PolisherBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(BROKEN, false));
    }



    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (pState.getValue(BROKEN)){
            if(pHand == InteractionHand.MAIN_HAND && !pPlayer.isCrouching()){
                if(!pLevel.isClientSide){
                    if(pPlayer.getMainHandItem().getItem() == Items.STONE){
                        pPlayer.getMainHandItem().shrink(1);
                        uses = 0;
                        pLevel.setBlock(pPos, pState.setValue(BROKEN, false), 3);
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        else{
            if(pHand == InteractionHand.MAIN_HAND && !pPlayer.isCrouching()){
                if(!pLevel.isClientSide){
                    if(pPlayer.getMainHandItem().is(ModTags.Items.POLISHABLE_TOOLS)){
                        pPlayer.getInventory().add(getTool(pPlayer.getMainHandItem().getItem()));
                        uses++;
                        if (uses == maxUses){
                            pPlayer.getMainHandItem().shrink(1);
                            pLevel.setBlock(pPos, pState.setValue(BROKEN, true), 3);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }

    private static final Supplier<Map<Item, Item>> TOOL_SWAP = Suppliers.memoize(() -> Map.of(
            ModItems.BLUNT_COPPER_CLIPPER.get(), ModItems.COPPER_CLIPPER.get(),
            ModItems.BLUNT_COPPER_HAMMER.get(), ModItems.COPPER_HAMMER.get()
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BROKEN);
    }
}
