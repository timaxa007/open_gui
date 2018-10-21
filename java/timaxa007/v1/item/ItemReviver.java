package timaxa007.open_gui.v1.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import timaxa007.open_gui.v1.entity.EntityInventory;
import timaxa007.open_gui.v1.tile.TileEntityCreate;

public class ItemReviver extends Item {

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		//if (world.isRemote) return true;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityCreate) {
			TileEntityCreate te = (TileEntityCreate)tile;
			if (te.inventory == null) return true;
			EntityInventory entity = new EntityInventory(world);
			entity.setPosition(x + 0.5D, y + 0.5D, z + 0.5D);

			for (int i = 0; i < te.inventory.getSizeInventory(); ++i) {
				entity.inventory.setInventorySlotContents(i, te.inventory.getStackInSlot(i));
				te.inventory.setInventorySlotContents(i, null);
			}

			world.func_147480_a(x, y, z, false);
			if (!world.isRemote) world.spawnEntityInWorld(entity);
		}
		return true;
	}

}
