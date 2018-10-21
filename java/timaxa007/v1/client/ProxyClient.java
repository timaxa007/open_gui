package timaxa007.open_gui.v1.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import timaxa007.open_gui.v1.ProxyCommon;
import timaxa007.open_gui.v1.client.gui.CreateGui;
import timaxa007.open_gui.v1.client.gui.EntityInventoryGui;
import timaxa007.open_gui.v1.client.gui.ItemBagGui;
import timaxa007.open_gui.v1.client.render.entity.RenderEntityInventory;
import timaxa007.open_gui.v1.entity.EntityInventory;
import timaxa007.open_gui.v1.inventory.InventoryItemStorage;
import timaxa007.open_gui.v1.tile.TileEntityCreate;

public class ProxyClient extends ProxyCommon {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityInventory.class, new RenderEntityInventory());
	}

	//IGuiHandler

	private static GuiScreen getGui(final int id, final EntityPlayer player) {
		ItemStack current = player.getCurrentEquippedItem();
		switch(id) {
		case 0:
			if (current == null) return null;
			return new ItemBagGui(player, new InventoryItemStorage(current));
		default:return null;
		}
	}

	private static GuiScreen getGui(final int id, final EntityPlayer player, final Entity entity) {
		switch(id) {
		case 0:
			if (!(entity instanceof EntityInventory)) return null;
			return new EntityInventoryGui(player, (EntityInventory)entity);
		default:return null;
		}
	}

	private static GuiScreen getGui(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch(id) {
		case 0:
			if (!(tile instanceof TileEntityCreate)) return null;
			return new CreateGui(player, (TileEntityCreate)tile);
		default:return null;
		}
	}

	//FMLNetworkHandler.openGui(EntityPlayer entityPlayer, Object mod, int modGuiId, World world, int x, int y, int z)

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player);
		else if (FMLCommonHandler.instance().getSide().isClient()) {
			GuiScreen gui = getGui(id, player);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}

	public void openGui(byte id, EntityPlayer player, Entity entity) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player, entity);
		else if (FMLCommonHandler.instance().getSide().isClient()) {
			GuiScreen gui = getGui(id, player, entity);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}

	public void openGui(byte id, EntityPlayer player, World world, int x, int y, int z) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player, world, x, y, z);
		else if (FMLCommonHandler.instance().getSide().isClient()) {
			GuiScreen gui = getGui(id, player, world, x, y, z);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}

}
