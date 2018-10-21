package timaxa007.open_gui.v1;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import timaxa007.open_gui.v1.block.BlockCrate;
import timaxa007.open_gui.v1.entity.EntityInventory;
import timaxa007.open_gui.v1.item.ItemBag;
import timaxa007.open_gui.v1.item.ItemReviver;
import timaxa007.open_gui.v1.network.OpenGuiEntityMessage;
import timaxa007.open_gui.v1.network.OpenGuiMessage;
import timaxa007.open_gui.v1.network.OpenGuiPositionMessage;
import timaxa007.open_gui.v1.tile.TileEntityCreate;

@Mod(modid = OpenGuiMod.MODID, name = OpenGuiMod.NAME, version = OpenGuiMod.VERSION)
public class OpenGuiMod {

	public static final String
	MODID = "open_gui1",
	NAME = "Open Gui and Container Mod",
	VERSION = "0.1";

	@Mod.Instance(MODID)
	public static OpenGuiMod instance;

	@SidedProxy(modId = MODID, serverSide = "timaxa007.open_gui.v1.ProxyCommon", clientSide = "timaxa007.open_gui.v1.client.ProxyClient")
	public static ProxyCommon proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	public static Block block_crate;
	public static Item
	item_bag,
	item_reviver;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, 0, Side.CLIENT);
		network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, 0, Side.SERVER);
		network.registerMessage(OpenGuiEntityMessage.Handler.class, OpenGuiEntityMessage.class, 1, Side.CLIENT);
		network.registerMessage(OpenGuiEntityMessage.Handler.class, OpenGuiEntityMessage.class, 1, Side.SERVER);
		network.registerMessage(OpenGuiPositionMessage.Handler.class, OpenGuiPositionMessage.class, 2, Side.CLIENT);
		network.registerMessage(OpenGuiPositionMessage.Handler.class, OpenGuiPositionMessage.class, 2, Side.SERVER);

		block_crate = new BlockCrate(Material.wood).setBlockName(MODID + ".crate").setBlockTextureName(MODID + ":crate").setCreativeTab(CreativeTabs.tabMisc).setHardness(2F).setStepSound(Block.soundTypeWood);
		GameRegistry.registerBlock(block_crate, "block_crate");
		GameRegistry.registerTileEntity(TileEntityCreate.class, MODID + ":TileEntityCreate");

		item_bag = new ItemBag().setUnlocalizedName(MODID + ".bag").setTextureName(MODID + ":bag").setCreativeTab(CreativeTabs.tabMisc).setHasSubtypes(true).setMaxDamage(0);
		GameRegistry.registerItem(item_bag, "item_bag");

		item_reviver = new ItemReviver().setUnlocalizedName(MODID + ".reviver").setTextureName(MODID + ":reviver").setCreativeTab(CreativeTabs.tabMisc).setHasSubtypes(true).setMaxDamage(0);
		GameRegistry.registerItem(item_reviver, "item_reviver");

		EntityRegistry.registerModEntity(EntityInventory.class, "EntityInventory", 0, instance, 64, 3, true);
		
		proxy.preInit(event);
	}

}
