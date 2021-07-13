package ru.artic030.mod02.gui;

import ic2.core.ContainerBase;
import ic2.core.GuiIC2;
import ic2.core.gui.GuiElement;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser.GuiNode;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class BackgroundlessDynamicGUI extends DynamicGui {
   public static DynamicGui<?> create(IInventory base, EntityPlayer player, GuiNode guiNode) {
      return new BackgroundlessDynamicGUI(player, DynamicContainer.create(base, player, guiNode), guiNode);
   }

   protected BackgroundlessDynamicGUI(EntityPlayer player, ContainerBase<IInventory> container, GuiNode guiNode) {
      super(player, container, guiNode);
   }

   protected final void func_146976_a(float partialTicks, int mouseX, int mouseY) {
      mouseX -= this.guiLeft;
      mouseY -= this.guiTop;
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Iterator<GuiElement> var4 = this.elements.iterator();
      while(var4.hasNext()) {
         GuiElement<?> element = (GuiElement<?>)var4.next();
         if (element.isEnabled()) {
            this.drawElement(element, mouseX, mouseY);
         }
      }

   }

   protected void drawElement(GuiElement<?> element, int mouseX, int mouseY) {
      element.drawBackground(mouseX, mouseY);
   }

   public int getLeft() {
      return this.guiLeft;
   }

   public int getTop() {
      return this.guiTop;
   }
}
