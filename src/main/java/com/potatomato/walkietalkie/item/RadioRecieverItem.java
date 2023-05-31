package com.potatomato.walkietalkie.item;

import com.potatomato.walkietalkie.network.ModMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RadioRecieverItem extends BasicTalkieItem {

    private final int CANAL;

    public RadioRecieverItem(Settings settings, int range, int canal) {
        super(settings, range);
        CANAL = canal;
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
            nbtCompound.putInt(BasicTalkieItem.NBT_KEY_CANAL, CANAL);
            stack.setNbt(nbtCompound);
        }

    }
}
