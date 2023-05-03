package com.potatomato.walkietalkie.block.entity;

import com.potatomato.walkietalkie.WalkieTalkie;
import com.potatomato.walkietalkie.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<SpeakerBlockEntity> SPEAKER_BLOCK_ENTITY;

    public static void registerBlockEntities() {
    }

    static {
        SPEAKER_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(WalkieTalkie.MOD_ID, "speaker_block_entity"),
                FabricBlockEntityTypeBuilder.create(SpeakerBlockEntity::new, ModBlocks.SPEAKER).build());
    }



}
