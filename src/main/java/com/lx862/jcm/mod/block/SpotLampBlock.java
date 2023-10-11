package com.lx862.jcm.mod.block;

import com.lx862.jcm.mod.block.base.VerticallyAttachedBlock;
import com.lx862.jcm.mod.gui.DemoScreen;
import com.lx862.jcm.mod.util.BlockUtil;
import com.lx862.jcm.mod.util.Utils;
import com.lx862.jcm.mod.util.VoxelUtil;
import org.mtr.mapping.holder.*;

public class SpotLampBlock extends VerticallyAttachedBlock {

    public SpotLampBlock(BlockSettings settings) {
        super(settings, true, true);
    }

    // TODO: FOR PREVIEW ONLY, REMOVE
    @Override
    public ActionResult onUse2(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        super.onUse2(state, world, pos, player, hand, hit);
        if(Utils.playerHoldingBrush(player) && world.isClient()) {
            MinecraftClient.getInstance().openScreen(new Screen(new DemoScreen()));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape2(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        if (BlockUtil.getProperty(state, TOP)) {
            return VoxelUtil.getShape16(4, 15.75, 4, 12, 16, 12);
        } else {
            return VoxelUtil.getShape16(4, 0, 4, 12, 0.25, 12);
        }
    }

    @Override
    protected boolean shouldBreakOnBlockUpdate() {
        return true;
    }
}
