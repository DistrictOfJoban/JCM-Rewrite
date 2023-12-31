package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.LCDPIDSBlockEntity;
import com.lx862.jcm.mod.block.entity.PIDSBlockEntity;
import com.lx862.jcm.mod.data.BlockProperties;
import com.lx862.jcm.mod.data.pids.PIDSManager;
import com.lx862.jcm.mod.data.pids.preset.PIDSPresetBase;
import com.lx862.jcm.mod.util.BlockUtil;
import org.mtr.mapping.holder.BlockState;
import org.mtr.mapping.holder.Direction;
import org.mtr.mapping.holder.World;
import org.mtr.mapping.mapper.GraphicsHolder;

public class LCDPIDSRenderer extends JCMBlockEntityRenderer<LCDPIDSBlockEntity> {
    public LCDPIDSRenderer(Argument dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderCurated(LCDPIDSBlockEntity blockEntity, float tickDelta, GraphicsHolder graphicsHolder, int light, int i1) {
        World world = blockEntity.getWorld2();
        if(world == null) return;

        PIDSPresetBase pidsPreset = getPreset(blockEntity);
        if(pidsPreset == null) return;

        BlockState state = blockEntity.getWorld2().getBlockState(blockEntity.getPos2());
        Direction facing = BlockUtil.getProperty(state, BlockProperties.FACING);
        
        graphicsHolder.push();
        scaleCentered(graphicsHolder, 0.009F, 0.009F, 0.009F);
        graphicsHolder.rotateYDegrees(90 - facing.asRotation());
        graphicsHolder.rotateZDegrees(180);
        graphicsHolder.translate(-20, -14.5, -14.5);

        pidsPreset.render(blockEntity, graphicsHolder, blockEntity.getWorld2(), facing, tickDelta, 0, 0, 153, 84, 0xFFFFFFFF, MAX_RENDER_LIGHT);
        graphicsHolder.pop();
    }

    private PIDSPresetBase getPreset(PIDSBlockEntity blockEntity) {
        return PIDSManager.getPreset(blockEntity.getPresetId(), PIDSManager.getPreset(blockEntity.getDefaultPresetId()));
    }
}
