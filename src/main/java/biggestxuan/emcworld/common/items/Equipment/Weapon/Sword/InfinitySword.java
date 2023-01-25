package biggestxuan.emcworld.common.items.Equipment.Weapon.Sword;

/*
 *  EMC WORLD MOD
 *  @Author Biggest_Xuan
 *  2022/10/20
 */

import biggestxuan.emcworld.api.item.*;
import biggestxuan.emcworld.api.item.equipment.weapon.BaseWeaponItem;
import biggestxuan.emcworld.api.item.equipment.weapon.IRangeAttackWeapon;
import biggestxuan.emcworld.api.item.equipment.weapon.IUpgradeableWeapon;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public class InfinitySword extends BaseWeaponItem implements IUpgradeableWeapon,IRangeAttackWeapon, ISecondEMCItem, IEMCRepairableItem, ICostEMCItem, IPlayerDifficultyItem, INeedLevelItem {
    public InfinitySword() {
        super(new IItemTier() {
            @Override
            public int getUses() {
                return 20000000;
            }

            @Override
            public float getSpeed() {
                return 100;
            }

            @Override
            public float getAttackDamageBonus() {
                return 888887;
            }

            @Override
            public int getLevel() {
                return 100;
            }

            @Override
            public int getEnchantmentValue() {
                return 100;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, 0, 1);
    }

    @Override
    public long getTickCost(ItemStack stack) {
        return 0;
    }

    @Override
    public int getUseLevel(ItemStack stack) {
        return 100;
    }

    @Override
    public long EMCModifySecond(ItemStack stack) {
        return 1000000000000L;
    }

    @Override
    public double getAttackRange(ItemStack stack) {
        return 64;
    }

    @Nonnull
    @Override
    public Rarity getRarity(ItemStack stack){
        return Rarity.EPIC;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public double getEMCCostRate() {
        return 0;
    }

    @Override
    public double costEMCWhenAttack(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) {
        return false;
    }

    @Override
    public double requireDifficulty() {
        return 3;
    }

    @Override
    public boolean canBeHurtBy(@Nonnull DamageSource p_234685_1_){
        return false;
    }

    @Override
    public float getAdditionsDamage(ItemStack stack) {
        return 0;
    }
}
