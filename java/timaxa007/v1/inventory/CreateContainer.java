package timaxa007.open_gui.v1.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import timaxa007.open_gui.v1.tile.TileEntityCreate;

public class CreateContainer extends Container {

	private final TileEntityCreate tile;
	private int numRows;

	public CreateContainer(EntityPlayer player, TileEntityCreate tile) {
		this.tile = tile;
		if (tile.inventory == null) return;
		tile.inventory.openInventory();

		numRows = tile.inventory.getSizeInventory() / 9;
		int i = (numRows - 4) * 18;
		int j;
		int k;

		for (int id = 0; id < tile.inventory.getSizeInventory(); ++id) {
			addSlotToContainer(new Slot(tile.inventory, id, 8 + (id % 9) * 18, 18 + (id / 9) * 18));
		}

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 85 + j * 18));
			}
		}

		for (j = 0; j < 9; ++j) {
			addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 143));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		if (tile == null) return false;
		if (tile.inventory == null) return false;
		return tile.inventory.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot_i) {
		ItemStack is = null;
		Slot slot = (Slot)inventorySlots.get(slot_i);

		if (slot != null && slot.getHasStack()) {
			ItemStack is1 = slot.getStack();
			is = is1.copy();

			if (slot_i < tile.inventory.getSizeInventory()) {
				if (!mergeItemStack(is1, tile.inventory.getSizeInventory(), inventorySlots.size(), true)) return null;
			} else if (!mergeItemStack(is1, 0, tile.inventory.getSizeInventory(), false))
				return null;

			if (is1.stackSize == 0) slot.putStack((ItemStack)null);
			else slot.onSlotChanged();
		}

		return is;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (tile.inventory == null) return;
		tile.inventory.closeInventory();
	}

}
