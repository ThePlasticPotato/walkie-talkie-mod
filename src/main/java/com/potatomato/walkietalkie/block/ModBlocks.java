package com.potatomato.walkietalkie.block;

import com.potatomato.walkietalkie.WalkieTalkie;
import com.potatomato.walkietalkie.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block SPEAKER;

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(WalkieTalkie.MOD_ID, name), block);
    }
    public static void registerBlockItem(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registry.ITEM, new Identifier(WalkieTalkie.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks() {
    }

    static {
        SPEAKER = registerBlock("speaker",
                new SpeakerBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(0.8F)), ModItemGroup.WALKIETALKIE);
    }


}
