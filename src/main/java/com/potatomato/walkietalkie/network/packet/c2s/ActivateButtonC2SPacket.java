package com.potatomato.walkietalkie.network.packet.c2s;

import com.potatomato.walkietalkie.item.ToggleableBasicTalkieItem;
import com.potatomato.walkietalkie.item.WalkieTalkieItem;
import com.potatomato.walkietalkie.network.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class ActivateButtonC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {

        ItemStack stack = player.getStackInHand(getHandUse(player));

        if (!stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
            if (stack.getItem().getClass().equals(ToggleableBasicTalkieItem.class)) {
                stack.getNbt().putBoolean(ToggleableBasicTalkieItem.NBT_KEY_ACTIVATE, !stack.getNbt().getBoolean(ToggleableBasicTalkieItem.NBT_KEY_ACTIVATE));

                PacketByteBuf packet = PacketByteBufs.create();
                packet.writeItemStack(stack);
                packetSender.sendPacket(ModMessages.BUTTON_PRESSED_RESPONSE, packet);
                return;
            }

            return;
        }

        stack.getNbt().putBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE, !stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE));

        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeItemStack(stack);
        packetSender.sendPacket(ModMessages.BUTTON_PRESSED_RESPONSE, packet);
    }

    private static Hand getHandUse(ServerPlayerEntity player) {

        ItemStack mainHand = player.getStackInHand(Hand.MAIN_HAND);
        ItemStack offHand = player.getStackInHand(Hand.OFF_HAND);

        if (mainHand.getItem() instanceof WalkieTalkieItem || mainHand.getItem() instanceof ToggleableBasicTalkieItem) {
            return Hand.MAIN_HAND;
        }
        if (offHand.getItem() instanceof WalkieTalkieItem || offHand.getItem() instanceof ToggleableBasicTalkieItem) {
            return Hand.OFF_HAND;
        }
        return null;
    }



}
