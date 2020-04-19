package com.bajuh.shearablechickenmod.entity;

import com.bajuh.shearablechickenmod.init.ObjectRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

import java.util.Objects;

// Logic copied from EggItem. Might break later. :)
public class ShearableChickenEggItem extends Item {

    public ShearableChickenEggItem() {
        super(new Properties()
                .maxStackSize(1)
                .group(ItemGroup.MISC));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();

        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }

        ItemStack itemstack = context.getItem();
        BlockPos blockpos = context.getPos();
        Direction direction = context.getFace();
        BlockState blockstate = world.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block == Blocks.SPAWNER) {
            TileEntity tileentity = world.getTileEntity(blockpos);
            if (tileentity instanceof MobSpawnerTileEntity)
                return updateSpawnerType(world, itemstack, blockpos, blockstate, tileentity);
        }

        BlockPos blockpos2;
        if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
            blockpos2 = blockpos;
        } else {
            blockpos2 = blockpos.offset(direction);
        }

        if (ObjectRegistration.SHEARABLE_CHICKEN.get().spawn(
            world, itemstack, context.getPlayer(), blockpos2, SpawnReason.SPAWN_EGG, true,
            !Objects.equals(blockpos, blockpos2) && direction == Direction.UP) != null)
        {
            itemstack.shrink(1);
        }

        return ActionResultType.SUCCESS;
    }

    private ActionResultType updateSpawnerType(World world, ItemStack itemstack, BlockPos blockpos,
        BlockState blockstate, TileEntity tileentity)
    {
        AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic();
        abstractspawner.setEntityType(ObjectRegistration.SHEARABLE_CHICKEN.get());
        tileentity.markDirty();
        world.notifyBlockUpdate(blockpos, blockstate, blockstate, 3);
        itemstack.shrink(1);

        return ActionResultType.SUCCESS;
    }

}
