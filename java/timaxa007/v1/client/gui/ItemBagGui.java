package timaxa007.open_gui.v1.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import timaxa007.open_gui.v1.inventory.InventoryItemStorage;
import timaxa007.open_gui.v1.inventory.ItemBagContainer;

public class ItemBagGui extends GuiContainer {

	private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
	private int inventoryRows;
	private InventoryPlayer inv_p;
	private InventoryItemStorage inv;

	public ItemBagGui(EntityPlayer player, InventoryItemStorage inventoryItemStorage) {
		super(new ItemBagContainer(player, inventoryItemStorage));
		inv_p = player.inventory;
		inv = inventoryItemStorage;
		inventoryRows = inventoryItemStorage.getSizeInventory() / 9;
		short short1 = 222;
		int i = short1 - 108;
		ySize = i + inventoryRows * 18;
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		if (mc.thePlayer.inventory.getItemStack() == null)
			super.keyTyped(p_73869_1_, p_73869_2_);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i1, int i2) {
		//Именование инвентаря Item Storage
		if (inv != null) fontRendererObj.drawString(
				(inv.hasCustomInventoryName() ? inv.getInventoryName() : StatCollector.translateToLocal("item.open_gui1.bag.name")), 8, 6, 4210752);
		//Именование инвентаря игрока
		if (inv_p != null) fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 8, inventoryRows * 18 + 19, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float i1, int i2, int i3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(field_147017_u);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, inventoryRows * 18 + 17);
		drawTexturedModalRect(k, l + inventoryRows * 18 + 17, 0, 126, xSize, 96);
	}

}
