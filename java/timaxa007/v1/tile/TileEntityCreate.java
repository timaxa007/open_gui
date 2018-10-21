package timaxa007.open_gui.v1.tile;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityCreate extends TileEntity {

	public InventoryBasic inventory = new InventoryBasic("tile.open_gui1.crate.name", false, 9 * 3);

	public TileEntityCreate() {}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (inventory == null) return;
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (inventory.getStackInSlot(i) == null) continue;
			NBTTagCompound slot_nbt = new NBTTagCompound();
			inventory.getStackInSlot(i).writeToNBT(slot_nbt);
			slot_nbt.setByte("Slot", (byte)i);
			list.appendTag(slot_nbt);
		}
		nbt.setTag("Inventory", list);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		inventory = new InventoryBasic("tile.open_gui1.crate.name", false, 9 * 3);
		if (nbt.hasKey("Inventory", NBT.TAG_LIST)) {
			NBTTagList list = nbt.getTagList("Inventory", NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); ++i) {
				NBTTagCompound slot_nbt = list.getCompoundTagAt(i);
				inventory.setInventorySlotContents((slot_nbt.getByte("Slot") & 255), ItemStack.loadItemStackFromNBT(slot_nbt));
			}
		}
	}

}
