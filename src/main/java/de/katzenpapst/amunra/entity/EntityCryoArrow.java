package de.katzenpapst.amunra.entity;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.mob.DamageSourceAR;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityCryoArrow extends EntityBaseLaserArrow {

	private static final ResourceLocation arrowTextures = new ResourceLocation(AmunRa.ASSETPREFIX, "textures/entity/cryoarrow.png");

	public EntityCryoArrow(World world) {
		super(world);
	}

	public EntityCryoArrow(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityCryoArrow(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}

	public EntityCryoArrow(World world, EntityLivingBase shootingEntity, EntityLivingBase target, float randMod) {
		super(world, shootingEntity, target, randMod);
	}

	@Override
	protected boolean doesFireDamage() {
		return false;
	}

	@Override
	protected float getDamage() {
		return 1.0F;
	}

	@Override
	protected DamageSource getDamageSource() {
		if (this.shootingEntity == null)
			return DamageSourceAR.causeLaserDamage("ar_coldray", this, this);// ("laserArrow", this, this).setProjectile();
		return DamageSourceAR.causeLaserDamage("ar_coldray", this, this.shootingEntity);
	}

	@Override
	protected int getEntityDependentDamage(Entity ent, int regularDamage) {
		if (ent instanceof EntityBlaze)
			return regularDamage * 2;
		return regularDamage;
	}

	@Override
	protected float getSpeed() {
		return 3.0F;
	}

	@Override
	public ResourceLocation getTexture() {
		return arrowTextures;
	}

	@Override
	protected void onImpactBlock(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);

		/*
		 * if(block == Blocks.water) { world.setBlock(x, y, z, Blocks.ice); } else
		 */ if (block == Blocks.lava) {
			world.setBlock(x, y, z, Blocks.obsidian);
		} else if (block == Blocks.fire) {
			world.setBlock(x, y, z, Blocks.air);
		} else if (world.getBlock(x, y + 1, z) == Blocks.fire) {
			world.setBlock(x, y + 1, z, Blocks.air);
		}
	}

	@Override
	protected void onImpactEntity(MovingObjectPosition mop) {
		if (mop.entityHit instanceof EntityLivingBase) {
			// setPotionEffect(Potion.poison.id, 30, 2, 1.0F);
			if (((EntityLivingBase) mop.entityHit).isBurning()) {
				((EntityLivingBase) mop.entityHit).extinguish();
			}
			((EntityLivingBase) mop.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 3));

			// how?
			// ((EntityLivingBase)mop).getEntityAttribute(p_110148_1_)
			// ((EntityLivingBase)mop.entityHit).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 500, 3));
		}
		// mop.entityHit
		// player.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
	}

	@Override
	protected void onPassThrough(int x, int y, int z) {

		Block b = world.getBlock(x, y, z);

		if (b == Blocks.water) {
			this.world.setBlock(x, y, z, Blocks.ice);
			inWater = false;
		}
		if (b == Blocks.lava) {
			this.playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
			this.world.setBlock(x, y, z, Blocks.obsidian);
		}

		// this.world.setBlock(x, y, z, Blocks.ice);

		//
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO Auto-generated method stub

	}

}
