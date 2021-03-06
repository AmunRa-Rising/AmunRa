package de.katzenpapst.amunra.proxy;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ARSidedProxy {

	public enum ParticleType {
		PT_MOTHERSHIP_JET_FLAME,
		PT_MOTHERSHIP_ION_FLAME,
		PT_GRAVITY_DUST
	}

	public boolean doCancelGravityEvent(EntityPlayer player) {
		return false;
	}

	/**
	 * Doing this because EntityPlayerSP doesn't exist serverside
	 * 
	 * @param player
	 */
	public void handlePlayerArtificalGravity(EntityPlayer player, double gravity) {
		// noop on server
	}

	public void init(FMLInitializationEvent event) {
	}

	public void playTileEntitySound(TileEntity tile, ResourceLocation resource) {
		// noop
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void preInit(FMLPreInitializationEvent event) {

	}

	public void spawnParticles(ParticleType type, World world, Vector3 pos, Vector3 motion) {
		// noop
	}
}
