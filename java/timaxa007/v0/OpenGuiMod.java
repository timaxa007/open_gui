package timaxa007.open_gui.v0;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import timaxa007.open_gui.v0.network.OpenGuiEntityMessage;
import timaxa007.open_gui.v0.network.OpenGuiMessage;
import timaxa007.open_gui.v0.network.OpenGuiPositionMessage;

@Mod(modid = OpenGuiMod.MODID, name = OpenGuiMod.NAME, version = OpenGuiMod.VERSION)
public class OpenGuiMod {

	public static final String
	MODID = "open_gui0",
	NAME = "Open Gui and Container Mod",
	VERSION = "0.0";

	@Mod.Instance(MODID)
	public static OpenGuiMod instance;

	@SidedProxy(modId = MODID, serverSide = "timaxa007.open_gui.v0.ProxyCommon", clientSide = "timaxa007.open_gui.v0.client.ProxyClient")
	public static ProxyCommon proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, 0, Side.CLIENT);
		network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, 0, Side.SERVER);
		network.registerMessage(OpenGuiEntityMessage.Handler.class, OpenGuiEntityMessage.class, 1, Side.CLIENT);
		network.registerMessage(OpenGuiEntityMessage.Handler.class, OpenGuiEntityMessage.class, 1, Side.SERVER);
		network.registerMessage(OpenGuiPositionMessage.Handler.class, OpenGuiPositionMessage.class, 2, Side.CLIENT);
		network.registerMessage(OpenGuiPositionMessage.Handler.class, OpenGuiPositionMessage.class, 2, Side.SERVER);
	}

}
