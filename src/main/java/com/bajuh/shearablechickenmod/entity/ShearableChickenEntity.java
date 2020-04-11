package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.Entry;
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

    private static final String NBT_SHEARED_STATE = "chicken_sheared_state";
    private static final String NBT_SHEARED_TIMER = "chicken_sheared_timer";

    private static final DataParameter<Boolean> SHEARED_STATE =
            EntityDataManager.createKey(ShearableChickenEntity.class, DataSerializers.BOOLEAN);

    private static final int SHEAR_TIMER_MIN = 100;
    private static final int SHEAR_TIMER_MAX = 500;

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
            Entry.LOGGER.debug(String.format("Sheared timer: %d", shearedTimer));
            if (shearedTimer <= 0)
                this.setSheared(false);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains(NBT_SHEARED_STATE))
            setSheared(compound.getBoolean(NBT_SHEARED_STATE));
        if (compound.contains(NBT_SHEARED_TIMER))
            shearedTimer = compound.getInt(NBT_SHEARED_TIMER);
    }

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

    private List<ItemStack> serverOnShearedLogic(ItemStack item, IWorld world, BlockPos pos, int fortune) {
        List<ItemStack> itemsToDrop = new ArrayList<>();

        if (isChild())
            return itemsToDrop;

        Entry.LOGGER.debug("Chicken is sheared");

        this.setSheared(true);
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        this.shearedTimer = RandomUtils.nextInt(SHEAR_TIMER_MIN, SHEAR_TIMER_MAX, this.rand);

        int featherDropCount = RandomUtils.nextInt(1, 3, this.rand);
        for(int j = 0; j < featherDropCount; ++j)
            itemsToDrop.add(new ItemStack(Items.FEATHER));

        return itemsToDrop;
    }
}
