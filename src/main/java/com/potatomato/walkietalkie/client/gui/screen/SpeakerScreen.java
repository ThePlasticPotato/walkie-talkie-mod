package com.potatomato.walkietalkie.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.potatomato.walkietalkie.WalkieTalkie;
import com.potatomato.walkietalkie.network.ModMessages;
import com.potatomato.walkietalkie.screen.SpeakerScreenHandler;
import com.potatomato.walkietalkie.client.gui.widget.ToggleImageButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class SpeakerScreen extends HandledScreen<SpeakerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(WalkieTalkie.MOD_ID, "textures/gui/gui_walkietalkie.png");
    private static final Identifier ACTIVATE_TEXTURE = new Identifier(WalkieTalkie.MOD_ID, "textures/icons/activate.png");

    private final int xSize = 195;
    private final int ySize = 76;

    private int guiLeft;
    private int guiTop;

    private ToggleImageButton activateButton;
    private Text canalText = Text.of("");

    public SpeakerScreen(SpeakerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.drawTexture(matrices, guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int titleWidth = this.textRenderer.getWidth(title.asOrderedText());
        this.textRenderer.draw(matrices, this.title, (float) (this.width / 2 - titleWidth / 2), (float) guiTop + 7, 4210752);

        updateActivateState();
        renderCanalText(matrices);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }
    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {

    }

    private void updateActivateState() {
        activateButton.setState(handler.isActivate());
    }

    private void renderCanalText(MatrixStack matrices) {
        canalText = Text.of(String.valueOf(handler.getCanal()));

        int canalWidth = this.textRenderer.getWidth(canalText.asOrderedText());
        this.textRenderer.draw(matrices, canalText, (float) (this.width / 2 - canalWidth / 2), (float) guiTop + 26, 4210752);
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;

        activateButton = new ToggleImageButton(guiLeft + 6, guiTop + ySize - 6 - 20, ACTIVATE_TEXTURE, button -> {
            sendUpdateSpeaker(0, false);
        }, handler.isActivate());
        this.addDrawableChild(activateButton);

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 10 + 40, guiTop + 20, 20, 20, Text.of(">"), button -> sendUpdateSpeaker(1, true)));

//        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), button -> {
//            sendUpdateSpeaker(1, false);
//        }).dimensions(this.width / 2 - 10 - 40, guiTop + 20, 20, 20).build());

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 10 - 40, guiTop + 20, 20, 20, Text.of("<"), button -> sendUpdateSpeaker(1, false)));

        canalText = Text.of(String.valueOf(handler.getCanal()));
    }

    private void sendUpdateSpeaker(int index, boolean status) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(index);
        buf.writeBoolean(status);

        ClientPlayNetworking.send(ModMessages.UPDATE_SPEAKER, buf);
    }


}
