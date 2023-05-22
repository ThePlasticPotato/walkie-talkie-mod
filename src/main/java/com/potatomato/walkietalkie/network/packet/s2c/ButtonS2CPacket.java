package com.potatomato.walkietalkie.network.packet.s2c;

import com.potatomato.walkietalkie.WalkieTalkie;
import com.potatomato.walkietalkie.client.gui.screen.WalkieTalkieScreen;
import com.potatomato.walkietalkie.item.WalkieTalkieItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class ButtonS2CPacket {

    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packet, PacketSender packetSender) {

        ItemStack stack = packet.readItemStack();
        if (stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
            WalkieTalkieScreen.getInstance().checkButtons(stack);
        }
    }
}
