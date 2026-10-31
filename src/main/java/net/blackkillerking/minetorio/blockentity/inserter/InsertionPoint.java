package net.blackkillerking.minetorio.blockentity.inserter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InsertionPoint {

    public record Extractable(IItemHandler itemHandler, int slot, ItemStack stack){};
    private final Level level;
    private final BlockPos pos;

    public InsertionPoint(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    private List<IItemHandler> itemHandlers(){
        if(!level.isLoaded(pos)) return List.of();
        BlockEntity be = level.getBlockEntity(pos);
        if(be == null) return List.of();

        List<IItemHandler> itemHandlers = new ArrayList<>();
        addHandlers(be, null, itemHandlers);

        for(Direction dir : Direction.values()){
            addHandlers(be, dir, itemHandlers);
        }

        return itemHandlers;
    }

    private void addHandlers(BlockEntity pBlockEntity, Direction pDirection, List<IItemHandler> pIItemHandlers) {
        pBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, pDirection).ifPresent(
                handler -> {
                    for(IItemHandler handler1 : pIItemHandlers){
                        if(handler1 == handler){
                            return;
                        }
                        pIItemHandlers.add(handler);
                    }
                }
        );
    }

    @Nullable
    public Extractable getExtractable (int pAmount, Predicate<ItemStack> pRequestedStack){
        for(IItemHandler handler : itemHandlers()){
            for(int slot=0; slot < handler.getSlots(); slot++){
                ItemStack stack = handler.extractItem(slot, pAmount, true);
                if(!stack.isEmpty() && pRequestedStack.test(stack)){
                    return new Extractable(handler, slot, stack);
                }
            }
        }
        return null;
    }

    public ItemStack insertStack (ItemStack pStack, boolean pSimulate){
        for(IItemHandler handler : itemHandlers()){
            ItemStack remainder = ItemHandlerHelper.insertItem(handler, pStack, pSimulate);
            if(remainder.getCount() < pStack.getCount()){
                return remainder;
            }
        }
        return pStack;
    }

    public int accepts(ItemStack stack) {
        return stack.getCount() - insertStack(stack, true).getCount();
    }

}


