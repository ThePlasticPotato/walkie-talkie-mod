package com.potatomato.walkietalkie;

import com.potatomato.walkietalkie.block.ModBlocks;
import com.potatomato.walkietalkie.block.entity.ModBlockEntities;
import com.potatomato.walkietalkie.config.ModConfig;
import com.potatomato.walkietalkie.screen.ModScreenHandlers;
import com.potatomato.walkietalkie.item.ModItems;
import com.potatomato.walkietalkie.network.ModMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkieTalkie implements ModInitializer {
	public static final String MOD_ID = "walkietalkie";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfig.loadModConfig();

		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModMessages.registerC2SPackets();
	}
}