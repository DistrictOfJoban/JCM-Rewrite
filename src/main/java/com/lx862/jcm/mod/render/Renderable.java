package com.lx862.jcm.mod.render;

import org.mtr.mapping.holder.Identifier;
import org.mtr.mapping.mapper.GuiDrawing;

public interface Renderable {
    int MAX_RENDER_LIGHT = 0xF000F0;
    default void drawTexture(GuiDrawing guiDrawing, Identifier identifier, double x, double y, double width, double height) {
        guiDrawing.beginDrawingTexture(identifier);
        guiDrawing.drawTexture(x, y, x + width, y + height, 0, 0, 1, 1);
        guiDrawing.finishDrawingTexture();
    }

    default void drawRectangle(GuiDrawing guiDrawing, double x, double y, double width, double height, int color) {
        guiDrawing.beginDrawingRectangle();
        guiDrawing.drawRectangle(x, y, x + width, y + height, color);
        guiDrawing.finishDrawingRectangle();
    }

    default boolean inRectangle(double targetX, double targetY, int rectX, int rectY, int rectW, int rectH) {
        return (targetX > rectX && targetX <= rectX + rectW) && (targetY > rectY && targetY <= rectY + rectH);
    }
}
