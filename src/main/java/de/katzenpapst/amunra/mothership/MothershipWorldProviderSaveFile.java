package de.katzenpapst.amunra.mothership;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class MothershipWorldProviderSaveFile extends WorldSavedData {

    final static String saveFileId = "mothershipData";

    public NBTTagCompound data = null;

    public MothershipWorldProviderSaveFile(String p_i2141_1_) {
        super(p_i2141_1_);

        data = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        data = nbt.getCompoundTag("data");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("data", data);
		return nbt;
    }

    public static MothershipWorldProviderSaveFile getSaveFile(World world) {
        MapStorage storage = world.getPerWorldStorage();
        MothershipWorldProviderSaveFile result = (MothershipWorldProviderSaveFile)storage.getOrLoadData(MothershipWorldProviderSaveFile.class, saveFileId);
        if (result == null) {
            result = new MothershipWorldProviderSaveFile(saveFileId);
            storage.setData(saveFileId, result);
        }
        return result;
    }

}
