package net.blackkillerking.minetorio.comand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {

    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("home").then(Commands.literal("set").executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> pContext) {
        ServerPlayer serverPlayer = pContext.getSource().getPlayer();
        BlockPos playerPos = serverPlayer.blockPosition();
        String posString = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";

        serverPlayer.getPersistentData().putIntArray("minetorio.homepos", new int[] {playerPos.getX(), playerPos.getY(), playerPos.getZ()});

        pContext.getSource().sendSuccess(() -> Component.literal("Set home at: " + posString), true);
        return 1;
    }
}
