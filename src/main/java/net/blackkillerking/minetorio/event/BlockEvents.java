package net.blackkillerking.minetorio.event;

import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.item.custom.tools.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID)
public class BlockEvents {
    private static final Set<BlockPos> BROKEN_BLOCKS = new HashSet<>();

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
}
