package com.potatomato.walkietalkie.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TagTalkieItem extends Item {

    public int getRange() {
        return RANGE;
    }

    private final int RANGE;

    public static final String NBT_KEY_TAGCANAL = "walkietalkie.tagcanal";


    public TagTalkieItem(Settings settings, int range) {
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
            nbtCompound.putString(NBT_KEY_TAGCANAL, "archrecieve");
            stack.setNbt(nbtCompound);
        }

    }


}
