package fr.flaton.talkiewalkiemod.item;

import fr.flaton.talkiewalkiemod.gui.TalkieWalkieGui;
import fr.flaton.talkiewalkiemod.gui.TalkieWalkieScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TalkieWalkieItem extends Item {

    public TalkieWalkieItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (world.isClient()) {
            MinecraftClient.getInstance().setScreen(new TalkieWalkieScreen(Text.translatable(""),new TalkieWalkieGui()));
        }




        if (!world.isClient()) {


            if (user.getStackInHand(hand).hasNbt()) {

                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.copyFrom(user.getStackInHand(hand).getNbt());

                if (!user.isSneaking()) {
                    nbtCompound.putBoolean("talkiewalkiemod.activate", !(nbtCompound.getBoolean("talkiewalkiemod.activate")));
                } else {
                    int canal = nbtCompound.getInt("talkiewalkiemod.canal");

                    if (canal >= 16) {
                        canal = 1;
                    } else {
                        canal ++;
                    }

                    nbtCompound.putInt("talkiewalkiemod.canal", canal);

                }

                user.sendMessage(Text.literal(nbtCompound.toString()), true);

                user.getStackInHand(hand).setNbt(nbtCompound);

            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) {
            return;
        }

        if (!stack.hasNbt()) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putBoolean("talkiewalkiemod.activate", false);
            nbtCompound.putInt("talkiewalkiemod.canal", 1);
            stack.setNbt(nbtCompound);
        }

    }


}