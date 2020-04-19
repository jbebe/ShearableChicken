package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.Constants;
import com.bajuh.shearablechickenmod.helper.RandomUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.List;

public class ShearableChickenEntity extends ChickenEntity implements IShearable {

    // Persist shearable state and timer by overriding {read,write}Additional()
    private static final String NBT_SHEARED_STATE = "chicken_sheared_state";
    private static final String NBT_SHEARED_TIMER = "chicken_sheared_timer";

    // Make state available on Client by using DataParameter
    private static final DataParameter<Boolean> SHEARED_STATE =
            EntityDataManager.createKey(ShearableChickenEntity.class, DataSerializers.BOOLEAN);

    // Tick countdown variable. Handled only on server-side.
    private int shearedTimer = 0;

    public ShearableChickenEntity(EntityType<? extends ChickenEntity> type, World worldIn) {
        super(type, worldIn);
    }

    //
    // Overrides
    //

    @Override
    protected void registerData(){
        super.registerData();

        this.dataManager.register(SHEARED_STATE, false);
    }

    @Override
    public boolean isShearable(ItemStack item, IWorldReader world, BlockPos pos) {
        return !this.isSheared() && !this.isChild();
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IWorld world, BlockPos pos, int fortune) {
        if (isServer()) {
            return serverOnShearedLogic(item, world, pos, fortune);
        }

        return new ArrayList<>();
    }

    @Override
    public void livingTick()
    {
        super.livingTick();

        if (isServer() && isSheared()){
            shearedTimer -= 1;

            if (shearedTimer <= 0)
                this.setSheared(false);
        }
    }

    // Read state and timer from save file
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains(NBT_SHEARED_STATE))
            setSheared(compound.getBoolean(NBT_SHEARED_STATE));
        if (compound.contains(NBT_SHEARED_TIMER))
            shearedTimer = compound.getInt(NBT_SHEARED_TIMER);
    }

    // Save state and timer to save file
    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putBoolean(NBT_SHEARED_STATE, isSheared());
        compound.putInt(NBT_SHEARED_TIMER, shearedTimer);
    }

    //
    // Getters setters
    //

    public void setSheared(boolean sheared) {
        this.dataManager.set(SHEARED_STATE, sheared);
    }

    public boolean isSheared() {
        return this.dataManager.get(SHEARED_STATE);
    }

    public boolean isClient(){
        return this.world.isRemote;
    }

    public boolean isServer(){
        return !isClient();
    }

    //
    // Logic
    //

    // Remarks: fortune is ignored as seen in SheepEntity
    private List<ItemStack> serverOnShearedLogic(ItemStack item, IWorld world, BlockPos pos, int fortune) {
        List<ItemStack> itemsToDrop = new ArrayList<>();

        // Don't drop when children
        if (isChild())
            return itemsToDrop;

        // Shearing part
        this.setSheared(true);
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        this.shearedTimer = RandomUtils.nextInt(Constants.SHEAR_TICK_MIN, Constants.SHEAR_TICK_MAX, this.rand);

        // Dropping part
        int featherDropCount = RandomUtils.nextInt(1, 3, this.rand);
        for(int j = 0; j < featherDropCount; ++j)
            itemsToDrop.add(new ItemStack(Items.FEATHER));

        return itemsToDrop;
    }
}
