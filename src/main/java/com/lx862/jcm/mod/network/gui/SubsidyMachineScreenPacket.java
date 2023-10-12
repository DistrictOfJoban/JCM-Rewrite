package com.lx862.jcm.mod.network.gui;

import com.lx862.jcm.mod.gui.SubsidyMachineScreen;
import org.mtr.mapping.holder.BlockPos;
import org.mtr.mapping.holder.MinecraftClient;
import org.mtr.mapping.holder.PacketBuffer;
import org.mtr.mapping.holder.Screen;
import org.mtr.mapping.registry.PacketHandler;

public class SubsidyMachineScreenPacket extends PacketHandler {
    private final BlockPos blockPos;
    private final int pricePerUse;
    private final int cooldown;

    public SubsidyMachineScreenPacket(PacketBuffer packetBuffer) {
        this.blockPos = packetBuffer.readBlockPos();
        this.pricePerUse = packetBuffer.readInt();
        this.cooldown = packetBuffer.readInt();
    }

    public SubsidyMachineScreenPacket(BlockPos blockPos, int pricePerUse, int cooldown) {
        this.blockPos = blockPos;
        this.pricePerUse = pricePerUse;
        this.cooldown = cooldown;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeBlockPos(blockPos);
        packetBuffer.writeInt(pricePerUse);
        packetBuffer.writeInt(cooldown);
    }

    @Override
    public void runClientQueued() {
        MinecraftClient.getInstance().openScreen(new Screen(new SubsidyMachineScreen(blockPos, pricePerUse, cooldown)));
    }
}
