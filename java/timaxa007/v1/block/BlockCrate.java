package timaxa007.open_gui.v1.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import timaxa007.open_gui.v1.OpenGuiMod;
import timaxa007.open_gui.v1.tile.TileEntityCreate;

public class BlockCrate extends Block implements ITileEntityProvider {

	public BlockCrate(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityCreate();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityCreate) {
			TileEntityCreate te = (TileEntityCreate)tile;
			if (te.inventory == null) return true;
			OpenGuiMod.proxy.openGui((byte)0, player, world, x, y, z);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityCreate) {
			TileEntityCreate te = (TileEntityCreate)tile;

			if (itemStack.hasDisplayName() && te.inventory != null) {
				te.inventory.func_110133_a(itemStack.getDisplayName());
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block blockOld, int metadataOld) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityCreate) {
			TileEntityCreate te = (TileEntityCreate)tile;
			if (te.inventory == null) return;
			for (int i1 = 0; i1 < te.inventory.getSizeInventory(); ++i1) {
				ItemStack itemstack = te.inventory.getStackInSlot(i1);

				if (itemstack != null) {
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j1 = world.rand.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
						entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, blockOld, metadataOld);
	}

}
