package com.potatomato.walkietalkie.item;

import com.potatomato.walkietalkie.WalkieTalkie;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static ItemGroup WALKIETALKIE;

    static {
        WALKIETALKIE = FabricItemGroupBuilder.build(new Identifier(WalkieTalkie.MOD_ID, "walkietalkie"),() -> new ItemStack(ModItems.STONE_WALKIETALKIE))
                .setName("itemGroup.walkietalkie.walkietalkie");

    }
}
