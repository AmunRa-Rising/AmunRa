package de.katzenpapst.amunra;

import java.util.ArrayList;
import java.util.List;

import de.katzenpapst.amunra.client.gui.GuiArtificialGravity;
import de.katzenpapst.amunra.client.gui.GuiAtomBattery;
import de.katzenpapst.amunra.client.gui.GuiCrafter;
import de.katzenpapst.amunra.client.gui.GuiHydroponics;
import de.katzenpapst.amunra.client.gui.GuiIonEngine;
import de.katzenpapst.amunra.client.gui.GuiMothershipSelection;
import de.katzenpapst.amunra.client.gui.GuiMothershipSettings;
import de.katzenpapst.amunra.client.gui.GuiRocketEngine;
import de.katzenpapst.amunra.client.gui.GuiShuttleDock;
import de.katzenpapst.amunra.inventory.ContainerArtificalGravity;
import de.katzenpapst.amunra.inventory.ContainerAtomBattery;
import de.katzenpapst.amunra.inventory.ContainerCrafter;
import de.katzenpapst.amunra.inventory.ContainerHydroponics;
import de.katzenpapst.amunra.inventory.ContainerIonEngine;
import de.katzenpapst.amunra.inventory.ContainerMothershipSettings;
import de.katzenpapst.amunra.inventory.ContainerRocketEngine;
import de.katzenpapst.amunra.inventory.ContainerShuttleDock;
import de.katzenpapst.amunra.old.tile.TileEntityGravitation;
import de.katzenpapst.amunra.old.tile.TileEntityHydroponics;
import de.katzenpapst.amunra.old.tile.TileEntityIsotopeGenerator;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipController;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipEngineAbstract;
import de.katzenpapst.amunra.old.tile.TileEntityMothershipSettings;
import de.katzenpapst.amunra.old.tile.TileEntityShuttleDock;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	// @SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT)
			return null;

		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);

		switch (ID) {
			case GuiIds.GUI_ATOMBATTERY:
				return new GuiAtomBattery(player.inventory, (TileEntityIsotopeGenerator) tile);
			case GuiIds.GUI_MOTHERSHIPCONTROLLER:
				List<CelestialBody> possibleCelestialBodies = new ArrayList<CelestialBody>();
				return new GuiMothershipSelection(possibleCelestialBodies, (TileEntityMothershipController) tile, world);
			case GuiIds.GUI_MS_ROCKET_ENGINE:
				return new GuiRocketEngine(player.inventory, (TileEntityMothershipEngineAbstract) tile);
			case GuiIds.GUI_MS_SETTINGS:
				return new GuiMothershipSettings(player.inventory, (TileEntityMothershipSettings) tile);
			case GuiIds.GUI_MS_ION_ENGINE:
				return new GuiIonEngine(player.inventory, (TileEntityMothershipEngineAbstract) tile);
			case GuiIds.GUI_CRAFTING:
				return new GuiCrafter(player.inventory, world, x, y, z);
			case GuiIds.GUI_SHUTTLE_DOCK:
				return new GuiShuttleDock(player.inventory, (TileEntityShuttleDock) tile);
			case GuiIds.GUI_HYDROPONICS:
				return new GuiHydroponics(player.inventory, (TileEntityHydroponics) tile);
			case GuiIds.GUI_GRAVITY:
				return new GuiArtificialGravity(player.inventory, (TileEntityGravitation) tile);

		}

		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
			return null;

		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);

		switch (ID) {
			case GuiIds.GUI_ATOMBATTERY:
				return new ContainerAtomBattery(player.inventory, (TileEntityIsotopeGenerator) tile);
			case GuiIds.GUI_MS_ROCKET_ENGINE:
				return new ContainerRocketEngine(player.inventory, (TileEntityMothershipEngineAbstract) tile);
			case GuiIds.GUI_MS_SETTINGS:
				return new ContainerMothershipSettings(player.inventory, (TileEntityMothershipSettings) tile);
			case GuiIds.GUI_MS_ION_ENGINE:
				return new ContainerIonEngine(player.inventory, (TileEntityMothershipEngineAbstract) tile);
			case GuiIds.GUI_CRAFTING:
				return new ContainerCrafter(player.inventory, world, x, y, z);
			case GuiIds.GUI_SHUTTLE_DOCK:
				return new ContainerShuttleDock(player.inventory, (TileEntityShuttleDock) tile);
			case GuiIds.GUI_HYDROPONICS:
				return new ContainerHydroponics(player.inventory, (TileEntityHydroponics) tile);
			case GuiIds.GUI_GRAVITY:
				return new ContainerArtificalGravity(player.inventory, (IInventory) tile);
		}

		return null;
	}

}
