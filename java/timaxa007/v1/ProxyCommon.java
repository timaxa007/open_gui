package timaxa007.open_gui.v1;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import timaxa007.open_gui.v1.entity.EntityInventory;
import timaxa007.open_gui.v1.inventory.CreateContainer;
import timaxa007.open_gui.v1.inventory.EntityInventoryContainer;
import timaxa007.open_gui.v1.inventory.InventoryItemStorage;
import timaxa007.open_gui.v1.inventory.ItemBagContainer;
import timaxa007.open_gui.v1.network.OpenGuiEntityMessage;
import timaxa007.open_gui.v1.network.OpenGuiMessage;
import timaxa007.open_gui.v1.network.OpenGuiPositionMessage;
import timaxa007.open_gui.v1.tile.TileEntityCreate;

public class ProxyCommon {

	public void preInit(FMLPreInitializationEvent event) {
		
	}

	private static Container getContainer(final int id, final EntityPlayer player) {
		ItemStack current = player.getCurrentEquippedItem();
		switch(id) {
		case 0:
			if (current == null) return null;
			return new ItemBagContainer(player, new InventoryItemStorage(current));
		default:return null;
		}
	}

	private static Container getContainer(final int id, final EntityPlayer player, final Entity entity) {
		switch(id) {
		case 0:
			if (!(entity instanceof EntityInventory)) return null;
			return new EntityInventoryContainer(player, (EntityInventory)entity);
		default:return null;
		}
	}

	private static Container getContainer(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch(id) {
		case 0:
			if (!(tile instanceof TileEntityCreate)) return null;
			return new CreateContainer(player, (TileEntityCreate)tile);
		default:return null;
		}
	}

	//FMLNetworkHandler.openGui(EntityPlayer entityPlayer, Object mod, int modGuiId, World world, int x, int y, int z)

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Container container = getContainer(id, player);
			if (container == null) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			OpenGuiMessage message = new OpenGuiMessage();
			message.windowID = windowId;
			message.id = id;
			OpenGuiMod.network.sendTo(message, playerMP);

			player.openContainer = container;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}

	public void openGui(byte id, EntityPlayer player, Entity entity) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Container container = getContainer(id, player, entity);
			if (container == null) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			OpenGuiEntityMessage message = new OpenGuiEntityMessage();
			message.windowID = windowId;
			message.id = id;
			message.entityID = entity.getEntityId();
			OpenGuiMod.network.sendTo(message, playerMP);

			player.openContainer = container;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}

	public void openGui(byte id, EntityPlayer player, World world, int x, int y, int z) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Container container = getContainer(id, player, world, x, y, z);
			if (container == null) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			OpenGuiPositionMessage message = new OpenGuiPositionMessage();
			message.windowID = windowId;
			message.id = id;
			message.x = x;
			message.y = y;
			message.z = z;
			OpenGuiMod.network.sendTo(message, playerMP);

			player.openContainer = container;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}

}
