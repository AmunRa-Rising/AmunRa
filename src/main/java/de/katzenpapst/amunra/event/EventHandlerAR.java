package de.katzenpapst.amunra.event;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.mob.DamageSourceAR;
import de.katzenpapst.amunra.mob.entity.IEntityNonOxygenBreather;
import de.katzenpapst.amunra.old.item.ItemThermalSuit;
import micdoodle8.mods.galacticraft.api.event.ZeroGravityEvent;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler.ThermalArmorEvent;
import micdoodle8.mods.galacticraft.core.util.OxygenUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandlerAR {

	@SubscribeEvent
	public void entityLivingEvent(LivingUpdateEvent event) {
		EntityLivingBase entityLiving = event.entityLiving;
		if (!(entityLiving instanceof IEntityNonOxygenBreather))
			return;

		if (entityLiving.ticksExisted % 100 == 0) {
			CelestialBody body;
			boolean isInSealedArea = OxygenUtil.isAABBInBreathableAirBlock(entityLiving);

			if (entityLiving.world.provider instanceof IGalacticraftWorldProvider) {
				body = ((IGalacticraftWorldProvider) entityLiving.world.provider).getCelestialBody();
			} else {
				body = GalacticraftCore.planetOverworld;
			}
			if (!((IEntityNonOxygenBreather) entityLiving).canBreatheIn(body.atmosphere, isInSealedArea)) {
				// should I add these events about suffocation that GC does?
				entityLiving.attackEntityFrom(DamageSourceAR.dsSuffocate, 1);
			}
		}
	}

	// gravity events. these should be client-only
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGravityEvent(ZeroGravityEvent.InFreefall event) {
		this.provessGravityEvent(event);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGravityEvent(ZeroGravityEvent.Motion event) {
		this.provessGravityEvent(event);
	}

	@SubscribeEvent
	public void onThermalArmorEvent(ThermalArmorEvent event) {
		// I sure hope this works with other mods...

		if (event.armorStack != null && event.armorStack.getItem() instanceof ItemThermalSuit) {
			event.setArmorAddResult(ThermalArmorEvent.ArmorAddResult.ADD);
			return;
		}

	}

	@SideOnly(Side.CLIENT)
	private void provessGravityEvent(ZeroGravityEvent event) {
		if (!(event.entity instanceof EntityPlayer))
			return;
		if (AmunRa.proxy.doCancelGravityEvent((EntityPlayer) event.entity)) {
			event.setCanceled(true);
		}

	}
}
