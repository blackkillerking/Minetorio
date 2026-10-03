package net.blackkillerking.minetorio.blockentity.base;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public interface InserterAccess {

    IItemHandler getItemHandler();
    List<Integer> getInputSlots();
    List<Integer> getOutputSlots();

    default boolean isInserterAccessible(){
        return true;
    }

    default int getSlotToInsertOrThrow(ItemStack inserted_stack){
        for (int slot : getInputSlots()){
            if(!getItemHandler().isItemValid(slot, inserted_stack)) continue;
            if(getItemHandler().insertItem(slot, inserted_stack, true).isEmpty()) return slot;
        }
        return -1;
    }

    default void insertInSlot(int slot, ItemStack inserted_stack){
        getItemHandler().insertItem(slot, inserted_stack, false);
    }

    default int getTargetSlot(int hand_size, InserterAccess receiver){
        for (int slot : getOutputSlots()) {
            ItemStack available = getItemHandler().extractItem(slot, hand_size, true);
            if (available.isEmpty()) continue;

            int requested = available.getCount();
            if (requested <= 0) continue;

            ItemStack temp = available.copyWithCount(requested);
            if (receiver.getSlotToInsertOrThrow(temp) != -1) return slot;
        }
        return -1;
    }

   default void extractFromSlot(int slot, int extracted_amount){
        getItemHandler().extractItem(slot, extracted_amount, false);
    }
}
