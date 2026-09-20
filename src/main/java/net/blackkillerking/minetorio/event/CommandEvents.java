package net.blackkillerking.minetorio.event;


import net.blackkillerking.minetorio.Minetorio;
import net.blackkillerking.minetorio.comand.ReturnHomeCommand;
import net.blackkillerking.minetorio.comand.SetHomeCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = Minetorio.MOD_ID)

public class CommandEvents {

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
