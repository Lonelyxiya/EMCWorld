package biggestxuan.emcworld.common.blocks.WeaponUpgradeBlock.Slot;

/**
 *  EMC WORLD MOD
 *  @Author Biggest_Xuan
 *  2022/09/27
 */

import biggestxuan.emcworld.api.item.IUpgradeableItem;
import biggestxuan.emcworld.common.items.Equipment.PrefixScroll;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class WeaponSlot extends Slot {
    public WeaponSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack p_75214_1_) {
        return p_75214_1_.getItem() instanceof IUpgradeableItem;
    }
}
