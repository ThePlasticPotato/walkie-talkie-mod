package com.potatomato.walkietalkie;

import com.potatomato.walkietalkie.item.*;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.packets.StaticSoundPacket;
import com.potatomato.walkietalkie.block.entity.SpeakerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class WalkieTalkieVoiceChatPlugin implements VoicechatPlugin {

    @Nullable
    public static VoicechatServerApi voicechatServerApi;

    @Override
    public String getPluginId() {
        return WalkieTalkie.MOD_ID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
    }

    private void onServerStarted(VoicechatServerStartedEvent event) {
        voicechatServerApi = event.getVoicechat();
    }

    private void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection senderConnection = event.getSenderConnection();

        if (senderConnection == null) {
            return;
        }

        if (!(senderConnection.getPlayer().getPlayer() instanceof PlayerEntity senderPlayer)) {
            return;
        }

        ItemStack senderStack = getWalkieTalkieActivate(senderPlayer);

        if (senderStack.getItem().getClass().equals(TagTalkieItem.class)) {
            for (PlayerEntity receiverPlayer : Objects.requireNonNull(senderPlayer.getServer()).getPlayerManager().getPlayerList()) {
                NbtCompound nbt = new NbtCompound();
                receiverPlayer.writeNbt(nbt);

                if (nbt.contains("archrecieve")){
                    // Send audio
                    VoicechatConnection connection = voicechatServerApi.getConnectionOf(receiverPlayer.getUuid());
                    if (connection == null) {
                        continue;
                    }

                    StaticSoundPacket packet = event.getPacket().toStaticSoundPacket();

                    voicechatServerApi.sendStaticSoundPacketTo(connection, packet);
                }
            }
            return;
        }


        if (getWalkieTalkieActivate(senderPlayer) == null) {
            return;
        }
        if (senderStack.getItem().getClass().equals(WalkieTalkieItem.class)) {
            if (isWalkieTalkieMute(senderStack)) {
                return;
            }
        }

        int senderCanal = getCanal(senderStack);

        for (SpeakerBlockEntity entity : SpeakerBlockEntity.getSpeakersActivateInRange(senderCanal, senderPlayer.getPos(), getRange(senderStack))) {
            entity.playSound(voicechatServerApi, event.getPacket());
        }

        for (PlayerEntity receiverPlayer : Objects.requireNonNull(senderPlayer.getServer()).getPlayerManager().getPlayerList()) {

            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid())) {
                continue;
            }

            ItemStack receiverStack = getWalkieTalkieActivateReciever(receiverPlayer);

            if (receiverStack == null) {
                continue;
            }

            int receiverRange = getRange(receiverStack);
            int receiverCanal = getCanal(receiverStack);

            if (!senderPlayer.getPos().isInRange(receiverPlayer.getPos(), receiverRange)) {
                continue;
            }

            if (receiverCanal != senderCanal) {
                continue;
            }

            // Send audio
            VoicechatConnection connection = voicechatServerApi.getConnectionOf(receiverPlayer.getUuid());
            if (connection == null) {
                continue;
            }

            StaticSoundPacket packet = event.getPacket().toStaticSoundPacket();

            voicechatServerApi.sendStaticSoundPacketTo(connection, packet);
        }
    }

    private ItemStack getWalkieTalkieActivate(PlayerEntity player) {

        ItemStack itemStack = null;

        int range = 0;

        PlayerInventory playerInventory = player.getInventory();

        ArrayList<ItemStack> inventory = new ArrayList<>();
        inventory.addAll(playerInventory.main);
        inventory.addAll(playerInventory.armor);
        inventory.addAll(playerInventory.offHand);

        ArrayList<ItemStack> hotbar = new ArrayList<>();
        hotbar.addAll(playerInventory.main.subList(0, 9));
        hotbar.addAll(playerInventory.offHand);


        for (ItemStack item : hotbar) {
            if ((item.getItem().getClass().equals(BasicTalkieItem.class) && item.hasNbt())) {

                BasicTalkieItem basicTalkieItem = (BasicTalkieItem) Objects.requireNonNull(item.getItem());

                if (basicTalkieItem.getRange() > range) {
                    itemStack = item;
                    range = basicTalkieItem.getRange();
                }
            }
        }

        for (ItemStack item : inventory) {

            if (item.getItem().getClass().equals(TagTalkieItem.class)) {
                return item;
            }


            if ((item.getItem().getClass().equals(WalkieTalkieItem.class) && item.hasNbt()) || (item.getItem().getClass().equals(ToggleableBasicTalkieItem.class) && item.hasNbt())) {
                if (item.getItem().getClass().equals(WalkieTalkieItem.class)) {
                    WalkieTalkieItem walkieTalkieItem = (WalkieTalkieItem) Objects.requireNonNull(item.getItem());

                    if (!Objects.requireNonNull(item.getNbt()).getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE)) {
                        continue;
                    }

                    if (walkieTalkieItem.getRange() > range) {
                        itemStack = item;
                        range = walkieTalkieItem.getRange();
                    }
                } else if (item.getItem().getClass().equals(ToggleableBasicTalkieItem.class)) {
                    ToggleableBasicTalkieItem walkieTalkieItem = (ToggleableBasicTalkieItem) Objects.requireNonNull(item.getItem());

                    if (!Objects.requireNonNull(item.getNbt()).getBoolean(ToggleableBasicTalkieItem.NBT_KEY_ACTIVATE)) {
                        continue;
                    }

                    if (walkieTalkieItem.getRange() > range) {
                        itemStack = item;
                        range = walkieTalkieItem.getRange();
                    }
                }

            }
            
        }

        return itemStack;

    }

    private ItemStack getWalkieTalkieActivateReciever(PlayerEntity player) {

        ItemStack itemStack = null;

        int range = 0;

        PlayerInventory playerInventory = player.getInventory();

        ArrayList<ItemStack> inventory = new ArrayList<>();
        inventory.addAll(playerInventory.main);
        inventory.addAll(playerInventory.armor);
        inventory.addAll(playerInventory.offHand);

        ArrayList<ItemStack> hotbar = new ArrayList<>();
        hotbar.addAll(playerInventory.main.subList(0, 9));
        hotbar.addAll(playerInventory.offHand);


        for (ItemStack item : hotbar) {
            if ((item.getItem().getClass().equals(BasicTalkieItem.class) && item.hasNbt())) {

                BasicTalkieItem basicTalkieItem = (BasicTalkieItem) Objects.requireNonNull(item.getItem());

                if (basicTalkieItem.getRange() > range) {
                    itemStack = item;
                    range = basicTalkieItem.getRange();
                }
            }
        }

        for (ItemStack item : inventory) {

            if ((item.getItem().getClass().equals(WalkieTalkieItem.class) && item.hasNbt()) || (item.getItem().getClass().equals(ToggleableBasicTalkieItem.class) && item.hasNbt())) {
                if (item.getItem().getClass().equals(WalkieTalkieItem.class)) {
                    WalkieTalkieItem walkieTalkieItem = (WalkieTalkieItem) Objects.requireNonNull(item.getItem());

                    if (!Objects.requireNonNull(item.getNbt()).getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE)) {
                        continue;
                    }

                    if (walkieTalkieItem.getRange() > range) {
                        itemStack = item;
                        range = walkieTalkieItem.getRange();
                    }
                } else if (item.getItem().getClass().equals(ToggleableBasicTalkieItem.class)) {
                    ToggleableBasicTalkieItem walkieTalkieItem = (ToggleableBasicTalkieItem) Objects.requireNonNull(item.getItem());

                    if (!Objects.requireNonNull(item.getNbt()).getBoolean(ToggleableBasicTalkieItem.NBT_KEY_ACTIVATE)) {
                        continue;
                    }

                    if (walkieTalkieItem.getRange() > range) {
                        itemStack = item;
                        range = walkieTalkieItem.getRange();
                    }
                }

            }

            if (item.getItem().getClass().equals(RadioRecieverItem.class) && item.hasNbt()) {
                RadioRecieverItem radioRecieverItem = (RadioRecieverItem) Objects.requireNonNull(item.getItem());

                if (radioRecieverItem.getRange() > range) {
                    itemStack = item;
                    range = radioRecieverItem.getRange();
                }
            }

        }

        return itemStack;

    }

    private int getCanal(ItemStack stack) {

        if (stack.getItem().getClass().equals(TagTalkieItem.class)) {
            return -1;
        }

        if (stack.getItem().getClass().equals(ToggleableBasicTalkieItem.class)) {
            return Objects.requireNonNull(stack.getNbt()).getInt(BasicTalkieItem.NBT_KEY_CANAL);
        }
        else if (stack.getItem().getClass().equals(RadioRecieverItem.class)) {
            return Objects.requireNonNull(stack.getNbt()).getInt(BasicTalkieItem.NBT_KEY_CANAL);
        }
        else if (stack.getItem().getClass().equals(BasicTalkieItem.class)) {
            return 999;
        }
        return Objects.requireNonNull(stack.getNbt()).getInt(WalkieTalkieItem.NBT_KEY_CANAL);
    }

    private int getRange(ItemStack stack) {
        if (stack.getItem().getClass().equals(BasicTalkieItem.class)) {
            BasicTalkieItem item = (BasicTalkieItem) Objects.requireNonNull(stack.getItem());
            return item.getRange();
        }
        WalkieTalkieItem item = (WalkieTalkieItem) Objects.requireNonNull(stack.getItem());
        return item.getRange();
    }

    private boolean isWalkieTalkieMute(ItemStack stack) {
        return Objects.requireNonNull(stack.getNbt()).getBoolean(WalkieTalkieItem.NBT_KEY_MUTE);
    }
}