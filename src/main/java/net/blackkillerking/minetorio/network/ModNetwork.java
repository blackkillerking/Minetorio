package net.blackkillerking.minetorio.network;

import net.blackkillerking.minetorio.Minetorio;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(Minetorio.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    private static int id = 0;
    public static void register() {
        INSTANCE.registerMessage(
                id++,
                ButtonPacket.class,
                ButtonPacket::encode,
                ButtonPacket::new,
                ButtonPacket::handle
        );
    }
}
