package fr.flaton.talkiewalkiemod.networking;

import fr.flaton.talkiewalkiemod.TalkieWalkieMod;
import fr.flaton.talkiewalkiemod.networking.packet.ButtonC2SPacket;
import fr.flaton.talkiewalkiemod.networking.packet.ButtonS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier BUTTON_PRESSED = new Identifier(TalkieWalkieMod.MOD_ID, "buttonpressed");
    public static final Identifier BUTTON_PRESSED_RESPONSE = new Identifier(TalkieWalkieMod.MOD_ID, "buttonpressedresponse");

    public static void registerC2SPackets() {

        ServerPlayNetworking.registerGlobalReceiver(BUTTON_PRESSED, ButtonC2SPacket::receive);

    }

    public static void registerS2CPackets() {

        ClientPlayNetworking.registerGlobalReceiver(BUTTON_PRESSED_RESPONSE, ButtonS2CPacket::receive);

    }

}