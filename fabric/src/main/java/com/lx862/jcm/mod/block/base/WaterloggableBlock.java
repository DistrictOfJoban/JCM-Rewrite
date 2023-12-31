package com.lx862.jcm.mod.block.base;

import com.lx862.jcm.mod.util.BlockUtil;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import org.mtr.mapping.holder.*;
import org.mtr.mapping.tool.HolderBase;

import java.util.List;

public abstract class WaterloggableBlock extends JCMBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = new BooleanProperty(Properties.WATERLOGGED);

    public WaterloggableBlock(BlockSettings settings) {
        super(settings);
        setDefaultState2(getDefaultState2().with(new Property<>(WATERLOGGED.data), false));
    }

    @Override
    public void addBlockProperties(List<HolderBase<?>> properties) {
        properties.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState2(BlockState state) {
        return BlockUtil.getProperty(state, WATERLOGGED) ? new FluidState(Fluids.WATER.getStill(false)) : super.getFluidState2(state);
    }

    @Override
    public BlockState getPlacementState2(ItemPlacementContext ctx) {
        // TODO Deprecated
        return this.getDefaultState2().with(new Property<>(WATERLOGGED.data), ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate2(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (BlockUtil.getProperty(state, WATERLOGGED)) {
            #if MC_VERSION == "11802"
                world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world.data));
            #elif MC_VERSION < "11904"
                world.getFluidTickScheduler().schedule(pos.data, Fluids.WATER, Fluids.WATER.getTickRate(world.data));
            #else
                world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world.data));
            #endif
        }

        return super.getStateForNeighborUpdate2(state, direction, neighborState, world, pos, neighborPos);
    }
}
