package com.lx862.jcm.mod.block.behavior;

import com.lx862.jcm.mod.util.ScoreboardUtil;
import com.lx862.jcm.mod.util.TextCategory;
import com.lx862.jcm.mod.util.TextUtil;
import org.mtr.mapping.holder.*;

public interface EnquiryMachine {
    default void tapEnquiryMachine(World world, PlayerEntity player) {
        int score = ScoreboardUtil.getPlayerMTRBalanceScore(world, player);

        player.sendMessage(Text.cast(TextUtil.translatable(TextCategory.HUD, "enquiry_machine.success", score)), true);
        //TODO: Replace Anvil Falling Sound with MTR Octopus Tap Sound
        player.playSound(new SoundEvent(net.minecraft.sound.SoundEvents.BLOCK_ANVIL_LAND), SoundCategory.BLOCKS, 1f, 1f);
    }
}
