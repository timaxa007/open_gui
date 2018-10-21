package timaxa007.open_gui.v1.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import timaxa007.open_gui.v1.OpenGuiMod;
import timaxa007.open_gui.v1.inventory.ItemBagContainer;

public class ItemBag extends Item {

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int slotID, boolean isHand) {
		if (entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)entity;
			if (player.openContainer instanceof ItemBagContainer)
				((ItemBagContainer)player.openContainer).update(player);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!player.isSneaking())
			OpenGuiMod.proxy.openGui((byte)0, player);
		return super.onItemRightClick(itemStack, world, player);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
		NBTTagCompound nbt = is.getTagCompound();
		if (nbt != null && nbt.hasKey("CustomSize")) list.add("Slots: " + (int)(nbt.getByte("CustomSize") & 255) + ".");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item id, CreativeTabs table, List list) {
		//list.add(new ItemStack(id, 1, 0));
		for (SizeStorage ss : SizeStorage.values())
			list.add(addNBT(id, ss));
	}

	public static ItemStack addNBT(Item item, SizeStorage size) {
		return addNBT(new ItemStack(item, 1, 0), size);
	}

	public static ItemStack addNBT(ItemStack is, SizeStorage size) {
		return addNBT(is, size.getSize());
	}

	public static ItemStack addNBT(ItemStack is, byte size) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("CustomSize", (byte)size);
		is.setTagCompound(nbt);
		return is;
	}

	public static enum SizeStorage {

		SIZE1((byte)9),	//(9 * 1)
		SIZE2((byte)18),	//(9 * 2)
		SIZE3((byte)27),	//(9 * 3)//размеры как у одинарного сундука
		SIZE4((byte)36),	//(9 * 4)
		SIZE5((byte)45),	//(9 * 5)
		SIZE6((byte)54),	//(9 * 6)//размеры как у двойного сундука
		SIZE7((byte)63),	//(9 * 7)
		SIZE8((byte)72),	//(9 * 8)
		SIZE9((byte)81);	//(9 * 9)

		private final byte size;

		SizeStorage(byte size) {
			this.size = size;
		}

		public byte getSize() {
			return size;
		}

	}

}
