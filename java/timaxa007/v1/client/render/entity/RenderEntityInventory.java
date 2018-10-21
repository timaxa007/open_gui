package timaxa007.open_gui.v1.client.render.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import timaxa007.open_gui.v1.OpenGuiMod;
import timaxa007.open_gui.v1.entity.EntityInventory;

@SideOnly(Side.CLIENT)
public class RenderEntityInventory extends Render {

	private final RenderBlocks field_147920_a = new RenderBlocks();

	public RenderEntityInventory() {
		this.shadowSize = 0.5F;
	}

	public void doRender(EntityInventory entity, double x, double y, double z, float p_76986_8_, float parTick)  {
		Block block = OpenGuiMod.block_crate;
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.posY);
		int k = MathHelper.floor_double(entity.posZ);

		if (block != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y + 0.5F, (float)z);
			GL11.glRotatef(entity.rotationYaw, 0, 1, 0);
	        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * parTick - 90.0F, 0.0F, 1.0F, 0.0F);
			this.bindEntityTexture(entity);
			GL11.glDisable(GL11.GL_LIGHTING);

			this.field_147920_a.setRenderBoundsFromBlock(block);
			this.field_147920_a.renderBlockSandFalling(block, entity.worldObj, i, j, k, 0);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	protected ResourceLocation getEntityTexture(EntityInventory p_110775_1_) {
		return TextureMap.locationBlocksTexture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.getEntityTexture((EntityInventory)p_110775_1_);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float parTick) {
		this.doRender((EntityInventory)entity, x, y, z, p_76986_8_, parTick);
	}

}