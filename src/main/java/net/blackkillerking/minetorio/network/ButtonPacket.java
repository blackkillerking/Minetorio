package net.blackkillerking.minetorio.network;

import net.blackkillerking.minetorio.block.entity.PrimitiveOvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ButtonPacket {

    private final BlockPos pos;
    private final int id;

    public ButtonPacket(BlockPos pos, int id){
        this.pos = pos;
        this.id = id;
    }

    public ButtonPacket(FriendlyByteBuf buf){
        this.pos = buf.readBlockPos();
        this.id = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeBlockPos(pos);
        buf.writeInt(id);
    }

    public void handle (Supplier<NetworkEvent.Context> pContext){
        NetworkEvent.Context context = pContext.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if(player == null) return;

            Level level = player.level();
            if(!(level.getBlockEntity(pos) instanceof PrimitiveOvenBlockEntity blockEntity)) return;
            if(player.distanceToSqr(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ()) > 64) return;
            blockEntity.handleButtonPress(id, player);
        });
        context.setPacketHandled(true);
    }
}
