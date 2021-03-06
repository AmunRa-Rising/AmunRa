package de.katzenpapst.amunra.client.renderer;

import de.katzenpapst.amunra.AmunRa;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * A renderer to simply don't render blocks, for when the tile entity is supposed to do the rendering
 */
public class BlockRendererDummy implements ISimpleBlockRenderingHandler {

	public BlockRendererDummy() {
		AmunRa.dummyRendererId = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public int getRenderId() {
		return AmunRa.dummyRendererId;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		// don't
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		// don't do anything
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// ?
		return true;
	}

}
