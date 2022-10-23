package plus.dragons.createminingindustry.contraptions.mining.blazeminer.minefield;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

// 16 x 16 block is area of a single mineTask, and height is 16;
// max fleid area 1s 5 x 5 unit;
public class MineFieldInfo implements INBTSerializable<CompoundTag> {
    static int MAX_HEIGHT = -64, MIN_HEIGHT = 319, VERTICAL_SLICE = 16;
    public ResourceLocation dimension;
    public BlockPos originalPos;
    public int xWidth, zWidth;
    public MineTaskArea distributied = new MineTaskArea(0,0,0);
    public Set<MineTaskArea> extraAvailable = new HashSet<>();

    public MineFieldInfo(ResourceLocation dimension, BlockPos originalPos, int xWidth, int zWidth) {
        this.dimension = dimension;
        this.originalPos = originalPos;
        this.xWidth = xWidth;
        this.zWidth = zWidth;
    }

    public MineTaskArea nextTaskArea(MineTaskArea mineTaskArea){
        if(!extraAvailable.isEmpty()){
            var ret = extraAvailable.iterator().next();
            extraAvailable.remove(ret);
            return ret;
        }
        if((distributied.x+1)*16>=xWidth){
            if((distributied.z+1)*16>=zWidth){
                if(distributied.y==VERTICAL_SLICE-1)
                    distributied = new MineTaskArea(0,0,0);
                else distributied = new MineTaskArea(0, distributied.y+1,0);
            }
            else distributied = new MineTaskArea(distributied.x, distributied.y, distributied.z+1);
        }
        else distributied = new MineTaskArea(distributied.x+1, distributied.y, distributied.z);
        return distributied;
    }

    public void returnTaskArea(MineTaskArea mineTaskArea){
        extraAvailable.add(mineTaskArea);
    }

    @Override
    public CompoundTag serializeNBT() {
        var ret = new CompoundTag();
        ret.putString("dimension", dimension.toString());
        ret.put("original_position",NbtUtils.writeBlockPos(originalPos));
        ret.putInt("",xWidth);
        ret.putInt("z_width",zWidth);
        ret.put("distributed", distributied.serializeNBT());
        if(!extraAvailable.isEmpty()){
            ret.putByte("has_extra", (byte) 1);
            ret.putInt("extra_size", extraAvailable.size());
            var list = new ListTag();
            for(var task:extraAvailable){
                list.add(task.serializeNBT());
            }
            ret.put("extra_list",list);
        }
        return ret;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        dimension = new ResourceLocation(nbt.getString("dimension"));
        originalPos = NbtUtils.readBlockPos((CompoundTag) nbt.get("original_position"));
        xWidth = nbt.getInt("x_width");
        zWidth = nbt.getInt("z_width");
        distributied = MineTaskArea.deserializeNBT((CompoundTag) nbt.get("distributed"));
        if(nbt.contains("has_extra")){
            var size = nbt.getInt("extra_size");
            ListTag list = (ListTag) nbt.get("extra_list");
            for(int i=0;i<size;i++){
                extraAvailable.add(MineTaskArea.deserializeNBT((CompoundTag) list.get(i)));
            }
        }
    }

    public record MineTaskArea(int x, int y, int z){
        // order -> x, z, y
        public AABB toAABB(MineFieldInfo mineFleid){
            var origin = mineFleid.originalPos;
            var x1 = origin.getX() + x * 16;
            var z1 = origin.getZ() + z * 16;
            var x2 = (x + 1) * 16 > mineFleid.xWidth? origin.getX() + mineFleid.xWidth - 1: x1 + 15;
            var z2 = (z + 1) * 16 > mineFleid.zWidth? origin.getZ() + mineFleid.zWidth - 1: z1 + 15;
            return new AABB(x1,MIN_HEIGHT,z1,x2,MIN_HEIGHT,z2);
        }


        public CompoundTag serializeNBT() {
            var ret = new CompoundTag();
            ret.putInt("x",x);
            ret.putInt("y",y);
            ret.putInt("z",z);
            return ret;
        }

        public static MineTaskArea deserializeNBT(CompoundTag nbt) {
            var x = nbt.getInt("x");
            var y = nbt.getInt("x");
            var z = nbt.getInt("x");
            return new MineTaskArea(x,y,z);
        }
    }
}
