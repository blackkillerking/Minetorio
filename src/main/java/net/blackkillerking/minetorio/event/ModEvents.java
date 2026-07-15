package net.blackkillerking.minetorio.event;


import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.comand.ReturnHomeCommand;
import net.blackkillerking.minetorio.comand.SetHomeCommand;
import net.blackkillerking.minetorio.item.ModItems;
import net.blackkillerking.minetorio.item.custom.CopperHammerItem;
import net.blackkillerking.minetorio.item.custom.HammerInterface;
import net.blackkillerking.minetorio.sound.ModSound;
import net.blackkillerking.minetorio.utils.ModTags;
import net.minecraft.client.gui.screens.social.PlayerEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID)

public class ModEvents {

    private static final Set<BlockPos> BROKEN_BLOCKS = new HashSet<>();

    public static List<BlockPos> getBlocksToBreak (int range, BlockPos initalBlockPos, ServerPlayer player){
        List<BlockPos> pos = new ArrayList<>();

        BlockHitResult direction = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (direction.getType() == HitResult.Type.MISS){
            return pos;
        }
        Direction dir = direction.getDirection();
        int inx = initalBlockPos.getX();
        int iny = initalBlockPos.getY();
        int inz = initalBlockPos.getZ();

        if(dir == Direction.DOWN || dir == Direction.UP){
            for (int x = -range; x <= range ; x++) {
                for (int y = -range; y <= range ; y++) {
                    pos.add(new BlockPos(inx + x, iny, inz + y));
                }

            }
        }

        if(dir == Direction.EAST || dir == Direction.WEST){
            for (int x = -range; x <= range ; x++) {
                for (int y = -range; y <= range ; y++) {
                    pos.add(new BlockPos(inx, iny + x, inz + y));
                }

            }
        }

        if(dir == Direction.NORTH || dir == Direction.SOUTH){
            for (int x = -range; x <= range ; x++) {
                for (int y = -range; y <= range ; y++) {
                    pos.add(new BlockPos(inx + x, iny + y, inz));
                }

            }
        }

        return pos;
    }

    @SubscribeEvent
    public static void onHammerUsage (BlockEvent.BreakEvent event){

        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if(heldItem.getItem() instanceof CopperHammerItem hammer && player instanceof ServerPlayer serverPlayer){
            BlockPos initalBlockPos = event.getPos();
            if (BROKEN_BLOCKS.contains(initalBlockPos)) {
                return;
            }

            for (BlockPos blockpos:getBlocksToBreak(hammer.getRange(), initalBlockPos, serverPlayer)){
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
    public static void onSheetCrafted(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        ItemStack craftedItem = event.getCrafting();
        boolean isSheet = craftedItem.is(ModTags.Items.SHEET);

        if(isSheet && player.level().isClientSide){
            player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), ModSound.HAMMER_CRAFTING.get(), SoundSource.PLAYERS, 1, 1, false);
            Minetorio.LOGGER.info("Sound fired");
        }
    }

    @SubscribeEvent
    public static void onIngotSmelted(PlayerEvent.ItemSmeltedEvent event) {
        Player player = event.getEntity();
        ItemStack craftedItem = event.getSmelting();
        boolean isIngot = craftedItem.is(ModItems.TIN_INGOT.get());

        if(isIngot   && player.level().isClientSide){
            player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), ModSound.HAMMER_CRAFTING.get(), SoundSource.PLAYERS, 1, 1, false);
            Minetorio.LOGGER.info("Sound fired");

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


}
