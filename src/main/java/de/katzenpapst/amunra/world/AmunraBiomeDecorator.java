package de.katzenpapst.amunra.world;

import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.World;

public class AmunraBiomeDecorator extends BiomeDecoratorSpace {

	protected World mWorld;

	protected List<WorldGenOre> oreGenList;

	public AmunraBiomeDecorator() {
	}

	@Override
	protected void decorate() {

		for (WorldGenOre oreGen : oreGenList) {
			this.generateOre(oreGen.amountPerChunk, oreGen, oreGen.minY, oreGen.maxY);
		}

	}

	@Override
	protected World getCurrentWorld() {
		return mWorld;
	}

	/**
	 * Override and return a list of ore generators.
	 * 
	 * @return
	 */
	protected List<WorldGenOre> getOreGenerators() {
		return new ArrayList<WorldGenOre>();
	}

	@Override
	protected void setCurrentWorld(World world) {
		mWorld = world;

		oreGenList = getOreGenerators();
	}

}
