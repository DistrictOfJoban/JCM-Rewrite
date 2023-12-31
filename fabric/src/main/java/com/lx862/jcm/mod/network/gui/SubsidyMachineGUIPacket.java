package com.lx862.jcm.mod.network.gui;

import com.lx862.jcm.mod.render.screen.SubsidyMachineScreen;
import org.mtr.mapping.holder.BlockPos;
import org.mtr.mapping.holder.MinecraftClient;
import org.mtr.mapping.holder.PacketBuffer;
import org.mtr.mapping.holder.Screen;
import org.mtr.mapping.registry.PacketHandler;

public class SubsidyMachineGUIPacket extends PacketHandler {
    private final BlockPos blockPos;
    private final int pricePerUse;
    private final int cooldown;

    public SubsidyMachineGUIPacket(PacketBuffer packetBuffer) {
        this.blockPos = packetBuffer.readBlockPos();
        this.pricePerUse = packetBuffer.readVarInt();
        this.cooldown = packetBuffer.readVarInt();
    }

    public SubsidyMachineGUIPacket(BlockPos blockPos, int pricePerUse, int cooldown) {
        this.blockPos = blockPos;
        this.pricePerUse = pricePerUse;
        this.cooldown = cooldown;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeBlockPos(blockPos);
        packetBuffer.writeVarInt(pricePerUse);
        packetBuffer.writeVarInt(cooldown);
    }

    @Override
    public void runClientQueued() {
        MinecraftClient.getInstance().openScreen(new Screen(new SubsidyMachineScreen(blockPos, pricePerUse, cooldown)));
    }
}
