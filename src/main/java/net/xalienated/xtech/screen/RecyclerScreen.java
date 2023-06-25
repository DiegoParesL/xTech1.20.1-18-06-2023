package net.xalienated.xtech.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xalienated.xtech.XTech;

public class RecyclerScreen extends HandledScreen<RecyclerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(XTech.MOD_ID, "textures/gui/recycler_gui.png");

    public RecyclerScreen(RecyclerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title))/2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F,1.0F);
        int x = (width - backgroundWidth) /2;
        int y = (height - backgroundHeight) /2;
        context.drawTexture(TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight);
        if(handler.isCrafting()) {
            renderProgressArrow(context, x, y);
        }
        if(handler.hasFuel()){
            renderFireSpriteAllFireOn(context,x,y);
            renderFireSpriteFireOffChanging(context,x,y);
        }

    }

    private void renderProgressArrow(DrawContext context, int x, int y){
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 84, y + 23, 176, 14,handler.getScaledProgress(), 36);
        }
    }
    private void renderFireSpriteFireOffChanging(DrawContext context, int x, int y){
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 18, y + 33, 18, 33,13, 13-handler.getFireOn());
        }
    }
    private void renderFireSpriteAllFireOn(DrawContext context, int x, int y){
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE,  x + 18, y + 33, 176, 0,14, 14);
        }
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context,mouseX,mouseY);
    }
}
