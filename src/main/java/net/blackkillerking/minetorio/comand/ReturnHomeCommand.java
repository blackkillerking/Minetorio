package net.blackkillerking.minetorio.comand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ReturnHomeCommand {

    public ReturnHomeCommand (CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("home").then(Commands.literal("back").executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> pContext) {
        ServerPlayer serverPlayer = pContext.getSource().getPlayer();
        boolean hasHomeSet = serverPlayer.getPersistentData().getIntArray("minetorio.homepos").length != 0;

        if (hasHomeSet){
            int[] playerPos = serverPlayer.getPersistentData().getIntArray("minetorio.homepos");
            serverPlayer.teleportTo(playerPos[0], playerPos[1], playerPos[2]);
            pContext.getSource().sendSuccess(() -> Component.literal("Returned Home"), true);
            return 1;
        }
        else {
            pContext.getSource().sendFailure(Component.literal("Home not set"));
            return -1;
        }
    }
}
