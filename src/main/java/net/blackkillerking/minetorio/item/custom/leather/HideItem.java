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

public class HideItem extends Item {
    public HideItem(Properties pProperties) {
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
            data.putInt("soaking_time", 4800);
            pStack.setTag(data);
        }

        CompoundTag tag = pStack.getOrCreateTag();
        int soakingTime = tag.getInt("soaking_time");
        ItemEntity soakedHide = new ItemEntity(pLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ(), new ItemStack(ModItems.SOAKED_HIDE.get(), pStack.getCount()));

        if(pEntity.isInFluidType(ModFluidTypes.TANNIN_FLUID_TYPE.get())){
            if (soakingTime < 0) {
                pLevel.addFreshEntity(soakedHide);
                pEntity.kill();

            } else{
                tag.putInt("soaking_time", soakingTime - 1);
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(!pStack.hasTag()){
            return;
        }
        int time = pStack.getTag().getInt("soaking_time");
        int timeInSeconds = (int) (time/20);
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.literal("Soaking Time : " + timeInSeconds + "s left"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.minetorio.soaked_hide.no_shift_tooltip"));
        }
    }
}
