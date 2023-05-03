package com.potatomato.walkietalkie.screen;

import com.potatomato.walkietalkie.WalkieTalkie;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
    public static final ScreenHandlerType<SpeakerScreenHandler> SPEAKER_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(WalkieTalkie.MOD_ID, "speaker"),
                SPEAKER_SCREEN_HANDLER);

    }

    static {
        SPEAKER_SCREEN_HANDLER =
                new ExtendedScreenHandlerType<>(SpeakerScreenHandler::new);
    }


}
