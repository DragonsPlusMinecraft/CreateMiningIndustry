package plus.dragons.createminingindustry.contraptions.mining.blazeminer;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

// 16 x 16 block is area of a single mineTask, and height is 16;
// max fleid area 1s 5 x 5 unit;
public class MineFieldTask {
    static final int MAX_HEIGHT = 319, VERTICAL_SLICE = 24;
    public int x,z,xWidth, zWidth;
    boolean done = false;

    public MineFieldTask(int x, int z, int xWidth, int zWidth) {
        this.x = x;
        this.z = z;
        this.xWidth = xWidth;
        this.zWidth = zWidth;
    }

    private MineFieldTask() {}

    public boolean done(){
        return done;
    }

    public CompoundTag serializeNBT() {
        var ret = new CompoundTag();
        ret.putInt("x",x);
        ret.putInt("z",z);
        ret.putInt("x_width",xWidth);
        ret.putInt("z_width",zWidth);
        return ret;
    }

    public static MineFieldTask fromNBT(CompoundTag nbt) {
        var ret = new MineFieldTask();
        ret.done = nbt.getBoolean("done");
        ret.x = nbt.getInt("x");
        ret.z = nbt.getInt("z");
        ret.xWidth = nbt.getInt("x_width");
        ret.zWidth = nbt.getInt("z_width");
        return ret;
    }
}
