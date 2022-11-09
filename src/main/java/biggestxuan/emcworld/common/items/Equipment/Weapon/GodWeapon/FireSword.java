package biggestxuan.emcworld.common.items.Equipment.Weapon.GodWeapon;

/**
 *  EMC WORLD MOD
 *  @Author Biggest_Xuan
 *  2022/09/28
 */

import biggestxuan.emcworld.api.item.equipment.weapon.BaseEMCGodWeapon;
import net.minecraft.item.ItemStack;

public class FireSword extends BaseEMCGodWeapon {
    public FireSword() {
        super(8.75f,"fire_sword",0xc90c0c);
    }

    @Override
    public float getAdditionsDamage(ItemStack stack) {
        int level = this.getLevel(stack);
        return (float) ((Math.pow(1.18f,level)*baseDamage)-baseDamage);
    }

    @Override
    public int getEnchantmentValue() {
        return 23;
    }

    @Override
    public double costEMCWhenAttack(ItemStack stack) {
        int level = this.getLevel(stack);
        return Math.pow(1.05f,level);
    }

    @Override
    public long EMCModifySecond(ItemStack stack) {
        int level = this.getLevel(stack);
        if(level <= 5) return 0;
        return Math.round(Math.pow(1.75,level-5)*8);
    }

    @Override
    public double getAttackRange(ItemStack stack){
        int level = getLevel(stack);
        if(level <= 10) return 0d;
        if(level <= 15) return (level-10) * 0.3d;
        if(level <= 18) return (level-15) * 0.35d + 1.5d;
        if(level <= 20) return (level-18) * 0.4d + 2.55d;
        if(level <= 23) return (level-20) * 0.45d + 3.35d;
        else return 4.7d + 0.95d;
    }
}