package timaxa007.open_gui.v0.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import timaxa007.open_gui.v0.ProxyCommon;

public class ProxyClient extends ProxyCommon {

	//IGuiHandler

	private static GuiScreen getGui(final int id, final EntityPlayer player) {
		return null;
	}

	private static GuiScreen getGui(final int id, final EntityPlayer player, final Entity entity) {
		return null;
	}

	private static GuiScreen getGui(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		return null;
	}

	//FMLNetworkHandler.openGui(EntityPlayer entityPlayer, Object mod, int modGuiId, World world, int x, int y, int z)

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			GuiScreen gui = getGui(id, player);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}

	public void openGui(byte id, EntityPlayer player, Entity entity) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player, entity);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			GuiScreen gui = getGui(id, player, entity);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}

	public void openGui(byte id, EntityPlayer player, World world, int x, int y, int z) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player, world, x, y, z);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			GuiScreen gui = getGui(id, player, world, x, y, z);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}

}
