package de.katzenpapst.amunra.old.tile;

import de.katzenpapst.amunra.AmunRa;
import de.katzenpapst.amunra.mothership.fueldisplay.MothershipFuelDisplay;
import de.katzenpapst.amunra.mothership.fueldisplay.MothershipFuelDisplayFluid;
import de.katzenpapst.amunra.mothership.fueldisplay.MothershipFuelRequirements;
import de.katzenpapst.amunra.old.block.ARBlocks;
import de.katzenpapst.amunra.proxy.ARSidedProxy.ParticleType;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GCFluids;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * This is supposed to be used for any jet blocks
 * 
 * @author katzenpapst
 */
public class TileEntityMothershipEngineJet extends TileEntityMothershipEngineAbstract {

	// public static final int MAX_LENGTH = 10;
	// protected PositionedSoundRecord leSound;

//    protected final MothershipFuel fuelType;
	protected MothershipFuelDisplay fuelType = null;

	public TileEntityMothershipEngineJet() {
		this.boosterBlock = ARBlocks.blockMsEngineRocketBooster;
		this.containingItems = new ItemStack[1];

		this.fuel = GCFluids.fluidFuel;
		fuelType = new MothershipFuelDisplayFluid(this.fuel);
	}

	@Override
	public void beginTransit(long duration) {

		MothershipFuelRequirements reqs = this.getFuelRequirements(duration);

		int fuelReq = reqs.get(fuelType);

		this.fuelTank.drain(fuelReq, true);

		super.beginTransit(duration);

	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, int side) {
		return slotID == 0;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {

		// here, fluid is fuel
		if (!FluidUtil.testFuel(FluidRegistry.getFluidName(fluid)))
			return false;

		return super.canFill(from, fluid);
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, int side) {
		return this.isItemValidForSlot(slotID, itemstack);
	}

	@Override
	public boolean canRunForDuration(long duration) {
		MothershipFuelRequirements reqs = getFuelRequirements(duration);

		return reqs.get(fuelType) <= fuelTank.getFluidAmount();
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public EnumFacing getFront() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MothershipFuelRequirements getFuelRequirements(long duration) {
		int totalFuelNeed = (int) Math.ceil(this.getFuelUsagePerTick() * duration * AmunRa.config.mothershipFuelFactor);

		MothershipFuelRequirements result = new MothershipFuelRequirements();

		result.add(fuelType, totalFuelNeed);

		return result;
	}

	/**
	 * This should return how much fuel units are consumed per AU travelled, in millibuckets
	 * 
	 * @return
	 */
	public float getFuelUsagePerTick() {
		return 2.0F;
	}

	@Override
	public String getInventoryName() {
		return GCCoreUtil.translate("tile.mothershipEngineRocket.name");
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Calculates tank capacity based on the boosters
	 * 
	 * @return
	 */
	@Override
	protected int getTankCapacity() {
		return 10000 * this.numBoosters;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getThrust() {
		return this.getNumBoosters() * 2000000.0D;
	}

	@Override
	protected boolean isItemFuel(ItemStack itemstack) {

		FluidStack containedFluid = null;
		if (itemstack.getItem() instanceof IFluidContainerItem) {
			containedFluid = ((IFluidContainerItem) itemstack.getItem()).getFluid(itemstack);
		}
		if (containedFluid == null) {
			containedFluid = FluidContainerRegistry.getFluidForFilledItem(itemstack);
		}
		if (containedFluid != null) {
			if (containedFluid.getFluid() == fuel)
				return true;
			return FluidUtil.testFuel(FluidRegistry.getFluidName(containedFluid));
		}

		return false;
	}

	@Override
	protected boolean isItemFuel(ItemStack fuel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
		if (slotID == 0 && itemstack != null)
			return this.isItemFuel(itemstack);
		/*
		 * FluidStack containedFluid = FluidContainerRegistry.getFluidForFilledItem(itemstack); if(containedFluid.getFluid() == fuel) { return true; }
		 */
		return false;
	}

	@Override
	public boolean shouldUseEnergy() {
		return false;
	}

	@Override
	protected void spawnParticles() {

		Vector3 particleStart = getExhaustPosition(1);
		Vector3 particleDirection = getExhaustDirection().scale(5);

		AmunRa.proxy.spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.world, particleStart, particleDirection);
		AmunRa.proxy.spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.world, particleStart, particleDirection);
		AmunRa.proxy.spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.world, particleStart, particleDirection);
		AmunRa.proxy.spawnParticles(ParticleType.PT_MOTHERSHIP_JET_FLAME, this.world, particleStart, particleDirection);

	}

	@Override
	protected void startSound() {
		super.startSound();
		AmunRa.proxy.playTileEntitySound(this, new ResourceLocation(AmunRa.TEXTUREPREFIX + "mothership.engine.rocket"));
	}

}
