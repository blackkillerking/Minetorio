package net.blackkillerking.minetorio.item.custom.leather;

import net.blackkillerking.minetorio.fluid.ModFluidTypes;
import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoakedHideItem extends Item {
    public SoakedHideItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack pStack, ItemEntity pEntity) {
        Level pLevel = pEntity.level();
        if(pLevel.isClientSide()){
            return false;
        }
        if(!pStack.hasTag()){
            CompoundTag data = new CompoundTag();
            data.putInt("drying_time", 3600);
            pStack.setTag(data);
        }

        CompoundTag tag = pStack.getOrCreateTag();
        int dryingTime = tag.getInt("drying_time");
        ItemEntity driedHide = new ItemEntity(pLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ(), new ItemStack(ModItems.DRIED_HIDE.get(), pStack.getCount()));

        if(!pEntity.isInFluidType()){
            if (dryingTime < 0) {
                pLevel.addFreshEntity(driedHide);
                pEntity.kill();

            } else{
                tag.putInt("drying_time", dryingTime - 1);
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(!pStack.hasTag()){
            return;
        }
        int time = pStack.getTag().getInt("drying_time");
        int timeInSeconds = (int) (time/20);
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.literal("Drying Time : " + timeInSeconds + "s left"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.minetorio.dried_hide.no_shift_tooltip"));
        }
    }
}
