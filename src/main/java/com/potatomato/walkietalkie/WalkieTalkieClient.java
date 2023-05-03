package com.potatomato.walkietalkie;

import com.potatomato.walkietalkie.screen.ModScreenHandlers;
import com.potatomato.walkietalkie.client.gui.screen.SpeakerScreen;
import com.potatomato.walkietalkie.network.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class WalkieTalkieClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.SPEAKER_SCREEN_HANDLER, SpeakerScreen::new);
    }
}
