package biggestxuan.emcworld.common.mixin;

/**
 *  EMC WORLD MOD
 *  @Author Biggest_Xuan
 *  2023/05/04
 */

import biggestxuan.emcworld.api.EMCWorldSince;
import biggestxuan.emcworld.api.event.PlayerEatFoodEvent;
import biggestxuan.emcworld.common.capability.EMCWorldCapability;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow
    public abstract float getCurrentItemAttackStrengthDelay();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "tick",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        PlayerEntity player = (PlayerEntity) (Object) this;
        player.getCapability(EMCWorldCapability.UTIL).ifPresent(c -> {
            player.getCapability(EMCWorldCapability.PLAYER_LEVEL).ifPresent(s -> {
                if(c.getTimer() > 0 && s.getProfession() == 5){
                    c.setTimer(0);
                    this.attackStrengthTicker = (int) this.getCurrentItemAttackStrengthDelay();
                }
            });
        });
    }

    @Inject(method = "eat",at = @At("HEAD"))
    @EMCWorldSince("0.9.0")
    public void _eat(World p_213357_1_, ItemStack p_213357_2_, CallbackInfoReturnable<ItemStack> cir){
        if(p_213357_2_.isEdible()){
            PlayerEntity player = (PlayerEntity) (Object) this;
            Food food = p_213357_2_.getItem().getFoodProperties();
            MinecraftForge.EVENT_BUS.post(new PlayerEatFoodEvent(player,food.getNutrition(),food.getSaturationModifier()));
        }
    }
}
