package com.potatomato.walkietalkie.item;

import com.potatomato.walkietalkie.client.gui.screen.WalkieTalkieScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BasicTalkieItem extends Item {

    public int getRange() {
        return RANGE;
    }

    private final int RANGE;

    public static final String NBT_KEY_CANAL = "walkietalkie.canal";


    public BasicTalkieItem(Settings settings, int range) {
        super(settings);
        RANGE = range;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        return super.use(world, player, hand);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) {
            return;
        }

        if (!stack.hasNbt()) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putInt(BasicTalkieItem.NBT_KEY_CANAL, 999);
            stack.setNbt(nbtCompound);
        }

    }


}
