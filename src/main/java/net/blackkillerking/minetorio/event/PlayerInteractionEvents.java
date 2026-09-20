package net.blackkillerking.minetorio.event;


import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPatternRegistry;
import net.blackkillerking.minetorio.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID)

public class PlayerInteractionEvents {

    @SubscribeEvent
    public static void OnFlintUsage (PlayerInteractEvent.RightClickBlock event){
        if(event.getLevel().isClientSide()) return;
        boolean isFlint = event.getItemStack().is(Items.FLINT);
        boolean isHittingStone = event.getLevel().getBlockState(event.getPos()).is(Blocks.STONE);
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ItemEntity sharpenedFlint = new ItemEntity(event.getLevel(), event.getPos().getX(), event.getPos().getY() + 1, event.getPos().getZ(), new ItemStack(ModItems.SHARPENED_FLINT.get(), 1));
        Random r = new Random();
        int rand = r.nextInt(3);

        if (isFlint && isHittingStone){
            event.getItemStack().shrink(1);
            if(rand == 0){
                event.getLevel().addFreshEntity(sharpenedFlint);
            } else {
                player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1, 1, false);
            }
        }
    }

    @SubscribeEvent
    public static void onStickOnMud(PlayerInteractEvent.RightClickBlock  event) {
        Level level = event.getLevel();
        if(level.isClientSide()) return;
        if(!level.getBlockState(event.getPos()).is(Blocks.MUD)) return;

        Player player = event.getEntity();
        boolean isSneaking = player.isCrouching();
        boolean isStick = player.getMainHandItem().is(Items.STICK);
        boolean isDirectionPlane = Direction.Plane.HORIZONTAL.test(player.getDirection()) ;

        if(event.getHand().equals(InteractionHand.MAIN_HAND) && isStick && isDirectionPlane && !isSneaking){
            if(MultiBlockPatternRegistry.get("primitive_oven").structureMatches(level, event.getPos(), player.getDirection().getOpposite())){
                level.setBlock(event.getPos(), ModBlocks.PRIMITIVE_OVEN.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, player.getDirection().getOpposite()), 3);
            }
        }
    }
}
