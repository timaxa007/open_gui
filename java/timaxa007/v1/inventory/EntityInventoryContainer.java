package timaxa007.open_gui.v1.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import timaxa007.open_gui.v1.entity.EntityInventory;

public class EntityInventoryContainer extends Container {

	private final EntityInventory entity;
	private int numRows;

	public EntityInventoryContainer(EntityPlayer player, EntityInventory entity) {
		this.entity = entity;
		if (entity.inventory == null) return;
		entity.inventory.openInventory();

		numRows = entity.inventory.getSizeInventory() / 9;
		int i = (numRows - 4) * 18;
		int j;
		int k;

		for (int id = 0; id < entity.inventory.getSizeInventory(); ++id) {
			addSlotToContainer(new Slot(entity.inventory, id, 8 + (id % 9) * 18, 18 + (id / 9) * 18));
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
		if (entity == null) return false;
		if (entity.inventory == null) return false;
		if (entity.isDead) return false;
		if (player.getDistanceSqToEntity(entity) > 64) return false;
		return entity.inventory.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot_i) {
		ItemStack is = null;
		Slot slot = (Slot)inventorySlots.get(slot_i);

		if (slot != null && slot.getHasStack()) {
			ItemStack is1 = slot.getStack();
			is = is1.copy();

			if (slot_i < entity.inventory.getSizeInventory()) {
				if (!mergeItemStack(is1, entity.inventory.getSizeInventory(), inventorySlots.size(), true)) return null;
			} else if (!mergeItemStack(is1, 0, entity.inventory.getSizeInventory(), false))
				return null;

			if (is1.stackSize == 0) slot.putStack((ItemStack)null);
			else slot.onSlotChanged();
		}

		return is;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (entity.inventory == null) return;
		entity.inventory.closeInventory();
	}

}
