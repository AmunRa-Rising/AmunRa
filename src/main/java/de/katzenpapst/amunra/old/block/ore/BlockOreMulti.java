package de.katzenpapst.amunra.old.block.ore;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.old.block.BlockBasicMeta;
import de.katzenpapst.amunra.old.block.SubBlock;
import de.katzenpapst.amunra.old.item.ItemBlockMulti;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOreMulti extends BlockBasicMeta {

	// the subblocks will be the different ores and doing overlays, the main block will define the stone
	// for hardness, explosion resistance and harvest level, the maximum of sub and mainblock will be used

	/**
	 * Harvest level for the main block
	 */
	protected int mbHarvestLevel = -1;

	/**
	 * Harvest tool, if set, it will override the tools of the subblocks
	 */
	protected String mbHarvestTool = null;

	public BlockOreMulti(String name, String texture, Material mat) {
		super(name, mat);
		this.textureName = texture;
	}

	@Override
	public float getBlockHardness(World par1World, int x, int y, int z) {
		return Math.max(super.getBlockHardness(par1World, x, y, z), this.blockHardness);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return Math.max(super.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ), this.getExplosionResistance(entity) // default resistance, should default to this.blockResistance / 5.0F
		);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return this.blockIcon;
	}

	public int getMultiblockHarvestLevel() {
		return mbHarvestLevel;
	}

	public String getMultiblockHarvestTool() {
		return mbHarvestTool;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return AmunRa.multiOreRendererId;
	}

	@Override
	public boolean isValueable(int metadata) {
		return true;
	}

	/**
	 * Registers the block with the GameRegistry and sets the harvestlevels for all subblocks
	 */
	@Override
	public void register() {
		GameRegistry.registerBlock(this, ItemBlockMulti.class, this.getUnlocalizedName());

		for (int i = 0; i < subBlocksArray.length; i++) {
			SubBlock sb = subBlocksArray[i];
			if (sb != null) {
				this.setHarvestLevel(mbHarvestTool == null ? sb.getHarvestTool(0) : mbHarvestTool, Math.max(sb.getHarvestLevel(0), this.getMultiblockHarvestLevel()), i);
				if (sb instanceof SubBlockOre) {
					SubBlockOre sbOre = (SubBlockOre) sb;
					for (String name : sbOre.getOredictNames()) {
						OreDictionary.registerOre(name, new ItemStack(this, 1, i));
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		super.registerBlockIcons(par1IconRegister);
		this.blockIcon = par1IconRegister.registerIcon(this.textureName);
	}

	public BlockOreMulti setMultiblockHarvestLevel(int level) {
		mbHarvestLevel = level;
		return this;
	}

	public BlockOreMulti setMultiblockHarvestTool(String tool) {
		mbHarvestTool = tool;
		return this;
	}

}
