package net.blackkillerking.minetorio.event;


import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.block.ModBlocks;
import net.blackkillerking.minetorio.block.entity.PrimitiveOvenBlockEntity;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPattern;
import net.blackkillerking.minetorio.block.multiblock.MultiBlockPatternRegistry;
import net.blackkillerking.minetorio.comand.ReturnHomeCommand;
import net.blackkillerking.minetorio.comand.SetHomeCommand;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.item.custom.tools.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.*;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID)

public class ModEvents {

    private static final Set<BlockPos> BROKEN_BLOCKS = new HashSet<>();


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
            if(isValidStructure(player.getDirection().getOpposite(), level, event.getPos())){
                level.setBlock(event.getPos(), ModBlocks.PRIMITIVE_OVEN.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, player.getDirection().getOpposite()), 3);
            }
        }
    }

    private static MultiBlockPattern getStructure() {
        return MultiBlockPatternRegistry.get("primitive_oven");
    }

    private static boolean isValidStructure(Direction pDirection, Level pLevel, BlockPos pPos){
        return getStructure().structureMatches(pLevel, pPos, pDirection);
    }

    @SubscribeEvent
    public static void onHammerUsage (BlockEvent.BreakEvent event){

        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if(heldItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer){
            BlockPos initalBlockPos = event.getPos();
            if (BROKEN_BLOCKS.contains(initalBlockPos)) {
                return;
            }

            for (BlockPos blockpos: HammerItem.getBlocksToBreak(hammer.getRange(), initalBlockPos, serverPlayer)){
                if(blockpos == initalBlockPos || !hammer.isCorrectToolForDrops(heldItem, event.getLevel().getBlockState(blockpos))){
                    continue;
                }

                BROKEN_BLOCKS.add(blockpos);
                serverPlayer.gameMode.destroyBlock(blockpos);
                BROKEN_BLOCKS.remove(blockpos);
            }
        }
    }

    @SubscribeEvent
    public static void onCommandRegister (RegisterCommandsEvent event){
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerClone (PlayerEvent.Clone event){
        event.getEntity().getPersistentData().putIntArray("minetorio.homepos", event.getOriginal().getPersistentData().getIntArray("minetorio.homepos"));
    }

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
}
