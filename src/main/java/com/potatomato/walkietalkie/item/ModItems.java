package com.potatomato.walkietalkie.item;

import com.potatomato.walkietalkie.WalkieTalkie;
import com.potatomato.walkietalkie.config.ModConfig;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.registry.Registry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item WOODEN_WALKIETALKIE = registerItem("wooden_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.woodenWalkieTalkieRange));
    public static final Item STONE_WALKIETALKIE = registerItem("stone_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.stoneWalkieTalkieRange));
    public static final Item IRON_WALKIETALKIE = registerItem("iron_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.ironWalkieTalkieRange));
    public static final Item DIAMOND_WALKIETALKIE = registerItem("diamond_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.diamondWalkieTalkieRange));
    public static final Item NETHERITE_WALKIETALKIE = registerItem("netherite_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE).fireproof(), ModConfig.netheriteWalkieTalkieRange));

    public static final Item BASICTALKIE = registerItem("basictalkie", new BasicTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.netheriteWalkieTalkieRange));

    public static final Item TOGGLEABLEBASICTALKIEONE = registerItem("toggleablebasictalkieone", new ToggleableBasicTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.ironWalkieTalkieRange, 420, 0));
    public static final Item TOGGLEABLEBASICTALKIETWO = registerItem("toggleablebasictalkietwo", new ToggleableBasicTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.diamondWalkieTalkieRange, 69, 1));

    public static final Item RADIORECIEVER = registerItem("radioreciever", new RadioRecieverItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.ironWalkieTalkieRange, 776));

    public static final Item RADIOTRANSMITTER = registerItem("radiotransmitter", new ToggleableBasicTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.diamondWalkieTalkieRange, 776, -1));

    public static final Item TAGTALKIE = registerItem("tagtalkie", new TagTalkieItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.netheriteWalkieTalkieRange));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(WalkieTalkie.MOD_ID, name), item);
    }
    public static void registerModItems() {

    }

}
