package biggestxuan.emcworld.common.mixin;

/**
 *  EMC WORLD MOD
 *  @Author Biggest_Xuan
 *  2023/03/27
 */

import biggestxuan.emcworld.common.registry.EWDamageSource;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.AdaptableAffix;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

@Mixin(AdaptableAffix.class)
public abstract class AdaptableAffixMixin extends BasicAffix {
    public AdaptableAffixMixin(String id, AffixCategory category) {
        super(id, category);
    }

    @Inject(method = "onHurt",at = @At("HEAD"),cancellable = true,remap = false)
    public void cancel(IChampion champion, DamageSource source, float amount, float newAmount, CallbackInfoReturnable<Float> cir){
        if(EWDamageSource.isReallyDamage(source)){
            cir.setReturnValue(newAmount);
        }
    }
}
