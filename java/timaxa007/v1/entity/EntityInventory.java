package timaxa007.open_gui.v1.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.INpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import timaxa007.open_gui.v1.OpenGuiMod;

public class EntityInventory extends EntityCreature implements INpc {

	public InventoryBasic inventory = new InventoryBasic("entity.open_gui1.EntityInventory.name", false, 9 * 3);

	public EntityInventory(World world) {
		super(world);
		setSize(0.8F, 0.8F);
	}

	@Override
	protected boolean interact(EntityPlayer player) {
		//if (worldObj.isRemote) {
		if (player instanceof EntityPlayerMP) {
			if (inventory == null) return true;
			OpenGuiMod.proxy.openGui((byte)0, player, this);
		}
		return true;
	}

	@Override
	protected void dropEquipment(boolean p_82160_1_, int p_82160_2_) {
		super.dropEquipment(p_82160_1_, p_82160_2_);
		if (inventory == null) return;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (inventory.getStackInSlot(i) == null) continue;
			entityDropItem(inventory.getStackInSlot(i), 0.0F);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
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
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		inventory = new InventoryBasic("entity.open_gui1.EntityInventory.name", false, 9 * 3);
		if (nbt.hasKey("Inventory", NBT.TAG_LIST)) {
			NBTTagList list = nbt.getTagList("Inventory", NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); ++i) {
				NBTTagCompound slot_nbt = list.getCompoundTagAt(i);
				inventory.setInventorySlotContents((slot_nbt.getByte("Slot") & 255), ItemStack.loadItemStackFromNBT(slot_nbt));
			}
		}
	}

}
