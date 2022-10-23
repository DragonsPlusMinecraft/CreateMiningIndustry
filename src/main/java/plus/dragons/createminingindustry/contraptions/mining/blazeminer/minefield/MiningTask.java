package plus.dragons.createminingindustry.contraptions.mining.blazeminer.minefield;

import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.INBTSerializable;

public class MiningTask implements INBTSerializable<CompoundTag> {

    AABB dutyAABB;
    BlockPos.MutableBlockPos targetPos;

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.put("duty",NBTHelper.writeAABB(dutyAABB));
        tag.put("target",NbtUtils.writeBlockPos(targetPos));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.dutyAABB = NBTHelper.readAABB((ListTag) nbt.get("duty"));
        var pos = NbtUtils.readBlockPos((CompoundTag) nbt.get("target"));
        this.targetPos = new BlockPos.MutableBlockPos(pos.getX(),pos.getY(),pos.getZ());
    }
}
