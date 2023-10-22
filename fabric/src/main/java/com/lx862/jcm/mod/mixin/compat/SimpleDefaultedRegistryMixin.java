package com.lx862.jcm.mod.mixin.compat;

import com.lx862.jcm.mod.Constants;
import net.minecraft.util.Identifier;
#if MC_VERSION < "11904"
import net.minecraft.util.registry.DefaultedRegistry;
#else
import net.minecraft.registry.SimpleDefaultedRegistry;
#endif
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Block renamed in JCM 2.0.0, unfortunately there's no easy way to migrate ids.
 * This class helps migrate these ids
 * See <a href="https://github.com/Noaaan/MythicMetals/blob/1.20/src/main/java/nourl/mythicmetals/mixin/DefaultedRegistryMixin.java">here</a>
 * And <a href="https://github.com/orgs/FabricMC/discussions/2361">https://github.com/orgs/FabricMC/discussions/2361</a>
 */
#if MC_VERSION < "11904"
    @Mixin(DefaultedRegistry.class)
#else
    @Mixin(SimpleDefaultedRegistry.class)
#endif
public class SimpleDefaultedRegistryMixin {
    @ModifyVariable(at = @At("HEAD"), method = "get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;", ordinal = 0, argsOnly = true)
    Identifier dataFixerRegistry(@Nullable Identifier id) {
        if (id != null && id.getNamespace().equals("jsblock")) {
            switch (id.getPath()) {
                case "bufferstop_1":
                    return new Identifier(Constants.MOD_ID, "buffer_stop");
                case "ceiling_1":
                    return new Identifier(Constants.MOD_ID, "ceiling_slanted");
                case "exit_sign_1":
                    return new Identifier(Constants.MOD_ID, "exit_sign_odd");
                case "faresaver_1":
                    return new Identifier(Constants.MOD_ID, "faresaver");
                case "helpline_3":
                    return new Identifier(Constants.MOD_ID, "helpline_standing_eal");
                case "helpline_4":
                    return new Identifier(Constants.MOD_ID, "helpline_standing");
                case "enquiry_machine_1":
                    return new Identifier(Constants.MOD_ID, "mtr_enquiry_machine");
                case "enquiry_machine_2":
                    return new Identifier(Constants.MOD_ID, "rv_enquiry_machine");
                case "enquiry_machine_3":
                    return new Identifier(Constants.MOD_ID, "mtr_enquiry_machine_wall");
                case "enquiry_machine_4":
                    return new Identifier(Constants.MOD_ID, "kcr_enquiry_machine");
                case "emg_stop_1":
                    return new Identifier(Constants.MOD_ID, "tcl_emg_stop_button");
                case "helpline_5":
                    return new Identifier(Constants.MOD_ID, "tml_emg_stop_button");
                case "helpline_6":
                    return new Identifier(Constants.MOD_ID, "sil_emg_stop_button");
                case "light_1":
                    return new Identifier(Constants.MOD_ID, "light_lantern");
                case "light_2":
                    return new Identifier(Constants.MOD_ID, "spot_lamp");
                case "mtr_stairs_1":
                    return new Identifier(Constants.MOD_ID, "mtr_stairs");
                case "op_button":
                    return new Identifier(Constants.MOD_ID, "operator_button");
                case "station_ceiling_1":
                    return new Identifier(Constants.MOD_ID, "wrl_station_ceiling");
                case "station_ceiling_1_pole":
                    return new Identifier(Constants.MOD_ID, "wrl_station_ceiling_pole");
                case "station_name_tall_stand":
                    return new Identifier(Constants.MOD_ID, "station_name_standing");
                case "ticket_barrier_1_entrance":
                    return new Identifier(Constants.MOD_ID, "thales_ticket_barrier_entrance");
                case "ticket_barrier_1_exit":
                    return new Identifier(Constants.MOD_ID, "thales_ticket_barrier_exit");
                case "ticket_barrier_1_bare":
                    return new Identifier(Constants.MOD_ID, "thales_ticket_barrier_bare");
                case "inter_car_barrier_1_left":
                    return new Identifier(Constants.MOD_ID, "lrt_inter_car_barrier_left");
                case "inter_car_barrier_1_middle":
                    return new Identifier(Constants.MOD_ID, "lrt_inter_car_barrier_middle");
                case "inter_car_barrier_1_right":
                    return new Identifier(Constants.MOD_ID, "lrt_inter_car_barrier_right");
                case "subsidy_machine_1":
                    return new Identifier(Constants.MOD_ID, "subsidy_machine");
                case "trespass_sign_1":
                    return new Identifier(Constants.MOD_ID, "mtr_trespass_sign");
                case "trespass_sign_2":
                    return new Identifier(Constants.MOD_ID, "kcr_trespass_sign");
                case "trespass_sign_3":
                    return new Identifier(Constants.MOD_ID, "lrt_trespass_sign");
                case "water_machine_1":
                    return new Identifier(Constants.MOD_ID, "water_machine");
            }
        }
        return id;
    }
}