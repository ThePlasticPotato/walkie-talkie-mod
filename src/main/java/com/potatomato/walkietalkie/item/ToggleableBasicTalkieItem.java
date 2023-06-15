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
    public static final String NBT_KEY_SFX = "walkietalkie.sfx";
    public final int SFX;

    public ToggleableBasicTalkieItem(Settings settings, int range, int canal, int sfx) {
        super(settings, range);
        CANAL = canal;
        SFX = sfx;
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

    public int getSFX() {
        return SFX;
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
            nbtCompound.putInt(ToggleableBasicTalkieItem.NBT_KEY_SFX, SFX);
            stack.setNbt(nbtCompound);
        }

    }
}
