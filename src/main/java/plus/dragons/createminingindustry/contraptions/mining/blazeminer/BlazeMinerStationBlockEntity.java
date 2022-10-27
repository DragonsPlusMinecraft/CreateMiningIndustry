package plus.dragons.createminingindustry.contraptions.mining.blazeminer;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createminingindustry.entry.CmiBlocks;

import java.util.List;

public class BlazeMinerStationBlockEntity extends SmartTileEntity implements IHaveGoggleInformation {

    MineFieldSubTask mineFieldSubTask;
    BlazeMinerInventory blazeInv;
    SmartInventory stationInv; // 27
    BlockPos commandCenterPos;
    Phase phase;
    LazyOptional<IItemHandlerModifiable> handler = LazyOptional.of(() -> stationInv);
    int itemCollected;


    public BlazeMinerStationBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        blazeInv = new BlazeMinerInventory();
        stationInv = new SmartInventory(27,this);
        phase = Phase.SEARCHING_COMMAND_CENTER;
        itemCollected = 0;
        mineFieldSubTask = null;
        // Temp
        commandCenterPos = new BlockPos(0,0,0);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {

    }

/*    @Override
    public void tick() {
        super.tick();

        if(phase == Phase.IDLE){
            if(!level.isClientSide()){
                requestWork();
            }
        }
        else if(phase == Phase.REQUEST_TASK){
            if(!level.isClientSide()){
                requestTask();
            }
        }
        else if(phase == Phase.SEARCH_MINEABLE){
            if(!level.isClientSide()){
                searchingArea();
            }
        }
        else if(phase == Phase.BLINK_TO_MINEABLE){
            if(!level.isClientSide()){
                blinkToArea();
            }
        }
        else if(phase == Phase.MINE){
            if(!level.isClientSide()){
                mine();
            }
        }
        else if(phase == Phase.TRANSFER_ITEM){
            if(!level.isClientSide()){
                transportItem();
            }
        }
    }


    private void requestWork() {
        phase = Phase.REQUEST_TASK;
        setChanged();
    }

    private void requestTask() {
        // temp solution for test only
        // Search for Command Center below within 10 blocks
        MineCommandCenterBlockEntity cmd = null;
        for(int i=1;i<=10;i++){
            if(level.getBlockEntity(getBlockPos().below(i)) instanceof MineCommandCenterBlockEntity mineCommandCenterBlockEntity){
                cmd = mineCommandCenterBlockEntity;
                break;
            }
        }
        if(cmd!=null){
            mineFieldSubTask = cmd.nextTask();
            System.out.println("Get Task:" + mineFieldSubTask.getCachedArea());
            phase = Phase.SEARCH_MINEABLE;
            setChanged();
        }
    }


    private void searchingArea() {
        // Remove high area for test
        if(mineFieldSubTask.getTargetPos().getY()>100){
            phase = Phase.REQUEST_TASK;
            setChanged();
            return;
        }
        var i = 0;
        while(!validMineLocation(mineFieldSubTask.getTargetPos()) && !mineFieldSubTask.done() && i<16){
            mineFieldSubTask.nextPos();
            i++;
        }
        if(mineFieldSubTask.done()){
            phase = Phase.REQUEST_TASK;
            mineFieldSubTask = null;
            setChanged();
            return;
        }
        if(i==16) return;
        phase = Phase.MINE;
        setChanged();
    }

    private boolean validMineLocation(BlockPos pos){
        //temp solution
        var bs = level.getBlockState(pos);
        return !bs.isAir() && !bs.is(CmiBlocks.BLAZE_MINER_STATION.get()) && !bs.is(CmiBlocks.MINE_COMMAND_CENTER.get());
    }

    private void blinkToArea() {

    }

    private void mine() {
        //temp solution
        BlockHelper.destroyBlock(level, mineFieldSubTask.getTargetPos(), 1f, (stack) -> {
            if (stack.isEmpty())
                return;
            if (!level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS))
                return;
            if (level.restoringBlockSnapshots)
                return;
            itemCollected+=stack.getCount();
            var left = blazeInv.addItem(stack);
            if(!left.isEmpty())
                blazeInv.backupInv.addItem(left);
        });
        if(!blazeInv.backupInv.isEmpty()){
            phase = Phase.TRANSFER_ITEM;
            setChanged();
            return;
        }
        mineFieldSubTask.nextPos();
        phase = Phase.SEARCH_MINEABLE;
        setChanged();
    }

    private void transportItem() {
        var allItems = blazeInv.removeAllItems();
        allItems.addAll(blazeInv.backupInv.removeAllItems());
        for(var item: allItems){
            ItemEntity itementity = new ItemEntity(level, getBlockPos().getX(), getBlockPos().getY()+1, getBlockPos().getZ(), item);
            itementity.setDeltaMovement(0.5,0.5,0.5);
            level.addFreshEntity(itementity);
        }
        // TODO for test
        afterTransportItem();
    }

    private void afterTransportItem(){
        if(itemCollected>1000){
            if (!mineFieldSubTask.done()){
                MineCommandCenterBlockEntity cmd = null;
                for(int i=1;i<=10;i++){
                    if(level.getBlockEntity(getBlockPos().below(i)) instanceof MineCommandCenterBlockEntity mineCommandCenterBlockEntity){
                        cmd = mineCommandCenterBlockEntity;
                        break;
                    }
                }
                if(cmd!=null){
                    cmd.returnTask(mineFieldSubTask);
                    itemCollected = 0;
                    mineFieldSubTask = null;
                }
            }
            phase = Phase.IDLE;
            setChanged();
            return;
        }
        phase = Phase.MINE;
        setChanged();
        // Temp solution
    }*/

    @Override
    public void write(CompoundTag compoundTag, boolean clientPacket) {
        super.write(compoundTag, clientPacket);
        if(mineFieldSubTask !=null)
            compoundTag.put("mining_task", mineFieldSubTask.serializeNBT());
        compoundTag.put("blaze_inventory", blazeInv.createTag());
        compoundTag.put("station_inventory", stationInv.serializeNBT());
        compoundTag.put("center_pos", NbtUtils.writeBlockPos(commandCenterPos));
        compoundTag.putInt("collected", itemCollected);
        NBTHelper.writeEnum(compoundTag,"phase",phase);
        // TODO
    }

    @Override
    protected void read(CompoundTag compoundTag, boolean clientPacket) {
        super.read(compoundTag, clientPacket);
        if(compoundTag.contains("mining_task"))
            mineFieldSubTask.deserializeNBT((CompoundTag) compoundTag.get("mining_task"));
        blazeInv.fromTag((ListTag) compoundTag.get("blaze_inventory"));
        stationInv.deserializeNBT((CompoundTag) compoundTag.get("station_inventory"));
        commandCenterPos = NbtUtils.readBlockPos((CompoundTag) compoundTag.get("center_pos"));
        itemCollected = compoundTag.getInt("collected");
        phase = NBTHelper.readEnum(compoundTag,"phase",Phase.class);
        // TODO
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if (side != null && side != Direction.UP && isItemHandlerCap(capability))
            return handler.cast();
        return super.getCapability(capability, side);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // TODO
        /*ModLang.translate("gui.goggles.blaze_enchanter").forGoggles(tooltip);
        Pair<Enchantment, Integer> ei;
        if (targetItem != null && (ei = EnchantingGuideItem.getEnchantment(targetItem)) != null) {
            tooltip.add(Components.literal("     ").append(ei.getFirst().getFullname(ei.getSecond() + (hyper()? 1 : 0))));
        }
        containedFluidTooltip(tooltip, isPlayerSneaking, getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY));*/
        return true;
    }

    enum Phase{
        SEARCHING_COMMAND_CENTER,
        REQUEST_JOB,
        REQUEST_TASK,
        SEARCH_MINEABLE,
        BLINK_TO_MINEABLE,
        MINE,
        TRANSFER_ITEM
    }

    static class BlazeMinerInventory extends SimpleContainer{
        SimpleContainer backupInv;

        public BlazeMinerInventory() {
            super(9);
            backupInv = new SimpleContainer(9);
        }

        @Override
        public void fromTag(@NotNull ListTag pContainerNbt) {
            var listTag = pContainerNbt.get(0);
            var listTag2 = pContainerNbt.get(1);
            super.fromTag((ListTag) listTag);
            backupInv.fromTag((ListTag) listTag2);
        }

        @Override
        public ListTag createTag() {
            var listTag = super.createTag();
            var listTag2 = backupInv.createTag();
            var ret = new ListTag();
            ret.add(listTag);
            ret.add(listTag2);
            return ret;
        }
    }


}
