package com.lx862.jcm.mod.gui.widget;

import com.lx862.jcm.mod.gui.GuiHelper;
import com.lx862.jcm.mod.render.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.Widget;
import org.mtr.mapping.holder.MutableText;
import org.mtr.mapping.mapper.*;

import java.util.ArrayList;
import java.util.List;

public class ListViewWidget extends ClickableWidgetExtension implements RenderHelper {
    public static final int ENTRY_PADDING = 5;
    private final int entryHeight;
    private final List<ListEntry> entryList = new ArrayList<>();
    private final List<Double> entryHighlightAnimation = new ArrayList<>();
    public ListViewWidget(int entryHeight) {
        super(0, 0, 0, 0);
        this.entryHeight = entryHeight;
    }

    public void setXYSize(int x, int y, int width, int height) {
        setX2(x);
        setY2(y);
        setWidth2(width);
        setHeight2(height);
        positionWidgets();
    }

    public void add(MutableText text, Widget widget) {
        add(new ListEntry(text, widget));
    }

    public void add(ListEntry listEntry) {
        entryList.add(listEntry);
        entryHighlightAnimation.add(0.0);
    }

    public void addCategory(MutableText text) {
        entryList.add(new ListEntry(text, null, true));
        entryHighlightAnimation.add(0.0);
    }

    public void reset() {
        this.entryList.clear();
        this.entryHighlightAnimation.clear();
    }

    @Override
    public void render(GraphicsHolder graphicsHolder, int mouseX, int mouseY, float tickDelta) {
        GuiDrawing guiDrawing = new GuiDrawing(graphicsHolder);

        // Background
        drawRectangle(guiDrawing, getX2(), getY2(), width, height, 0x09FFFFFF);

        for(int i = 0; i < entryList.size(); i++) {
            ListEntry listEntry = entryList.get(i);
            int entryX = getX2();
            int entryY = getY2() + (i * entryHeight);

            if(listEntry.isCategory) {
                drawListCategory(graphicsHolder, listEntry, entryX, entryY);
            } else {
                drawListEntry(graphicsHolder, listEntry, entryX, entryY, mouseX, mouseY, i, tickDelta);
            }
        }
    }

    private void drawListEntry(GraphicsHolder graphicsHolder, ListEntry entry, int entryX, int entryY, int mouseX, int mouseY, int entryIndex, float tickDelta) {
        double highlightFadeSpeed = (tickDelta / 4);
        boolean entryHovered = inRectangle(mouseX, mouseY, entryX, entryY, width, entryHeight);
        if(entryHovered) {
            entryHighlightAnimation.set(entryIndex, Math.min(1, entryHighlightAnimation.get(entryIndex) + highlightFadeSpeed));
        } else {
            entryHighlightAnimation.set(entryIndex, Math.max(0, entryHighlightAnimation.get(entryIndex) - highlightFadeSpeed));
        }

        drawListEntryHighlight(new GuiDrawing(graphicsHolder), entryIndex, entryX, entryY);
        // We have to draw our widget (Right side) again after rendering the highlight so it doesn't get covered.
        GuiHelper.drawWidget(graphicsHolder, mouseX, mouseY, tickDelta, entry.widget);
        drawListEntryDescription(graphicsHolder, entry, entryX, entryY);
    }

    private void drawListEntryDescription(GraphicsHolder graphicsHolder, ListEntry entry, int entryX, int entryY) {
        int textHeight = MinecraftClient.getInstance().textRenderer.fontHeight;
        boolean hasIcon = entry.hasIcon();
        int iconSize = hasIcon ? entryHeight - ENTRY_PADDING : 0;
        int entryTextWidth = width - entry.widget.getWidth() - ENTRY_PADDING - iconSize;
        int textY = (entryHeight / 2) - (textHeight / 2);

        graphicsHolder.push();
        graphicsHolder.translate(entryX, entryY, 0);
        graphicsHolder.translate(ENTRY_PADDING, 0, 0);

        if(hasIcon) {
            entry.drawIconCallback.accept(new GuiDrawing(graphicsHolder), entryX + ENTRY_PADDING, entryY + ((entryHeight - iconSize) / 2), iconSize, iconSize);
            // Shift the text to the right
            graphicsHolder.translate(iconSize + ENTRY_PADDING, 0, 0);
        }

        scaleToFitBoundary(graphicsHolder, GraphicsHolder.getTextWidth(entry.title) + ENTRY_PADDING, entryTextWidth - iconSize, false);
        graphicsHolder.drawText(entry.title, 0, textY, 0xFFFFFFFF, true, MAX_RENDER_LIGHT);
        graphicsHolder.pop();
    }
    private void drawListEntryHighlight(GuiDrawing guiDrawing, int entryIndex, int x, int y) {
        int highlightAlpha = (int)(80 * entryHighlightAnimation.get(entryIndex));
        int highlightColor = (highlightAlpha << 24) | (190 << 16) | (190 << 8) | 190;

        if(entryHighlightAnimation.get(entryIndex) > 0) {
            drawRectangle(guiDrawing, x, y, width, entryHeight, highlightColor);
        }
    }

    private void drawListCategory(GraphicsHolder graphicsHolder, ListEntry listEntry, int entryX, int entryY) {
        drawRectangle(new GuiDrawing(graphicsHolder), entryX, entryY, width, entryHeight, 0xAA999999);
        graphicsHolder.drawCenteredText(listEntry.title, (entryX + width / 2), entryY + (entryHeight / 4), 0xFFFFFFFF);
    }

    private void positionWidgets() {
        int startX = getX2();
        int startY = getY2();

        for(int i = 0; i < entryList.size(); i++) {
            ListEntry listEntry = entryList.get(i);
            if(listEntry.isCategory) continue;
            int entryY = startY + (i * entryHeight);
            int x = (startX + width) - (listEntry.widget.getWidth()) - (ENTRY_PADDING);
            int y = (entryY) + ((entryHeight - listEntry.widget.getHeight()) / 2);
            listEntry.widget.setX(x);
            listEntry.widget.setY(y);
        }
    }
}