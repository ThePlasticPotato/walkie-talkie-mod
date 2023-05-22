package com.potatomato.walkietalkie.item;

import com.potatomato.walkietalkie.client.gui.screen.WalkieTalkieScreen;
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

public class ToggleableBasicTalkieItem extends BasicTalkieItem {

    private final int CANAL;
    public static final String NBT_KEY_ACTIVATE = "walkietalkie.activate";

    public ToggleableBasicTalkieItem(Settings settings, int range, int canal) {
        super(settings, range);
        CANAL = canal;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (world.isClient()) {
            if (player.getStackInHand(hand).hasNbt()) {

                ItemStack stack = player.getStackInHand(hand);

                if (stack.getItem().getClass().equals(ToggleableBasicTalkieItem.class)) {
                    ClientPlayNetworking.send(ModMessages.ACTIVATE_PRESSED, PacketByteBufs.empty());
                }
            }
        }

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
            nbtCompound.putBoolean(ToggleableBasicTalkieItem.NBT_KEY_ACTIVATE, true);
            stack.setNbt(nbtCompound);
        }

    }
}
