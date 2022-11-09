package biggestxuan.emcworld.client.event;

/**
 * EMC WORLD MOD
 * @Author Biggest_Xuan
 * 2022/10/13
 */

import biggestxuan.emcworld.EMCWorld;
import biggestxuan.emcworld.api.EMCWorldAPI;
import biggestxuan.emcworld.api.item.*;
import biggestxuan.emcworld.api.item.equipment.armor.ISpeedArmor;
import biggestxuan.emcworld.api.item.equipment.armor.IUpgradeableArmor;
import biggestxuan.emcworld.api.item.equipment.weapon.BaseWeaponItem;
import biggestxuan.emcworld.api.item.equipment.weapon.IAdditionsDamageWeapon;
import biggestxuan.emcworld.api.item.equipment.weapon.IRangeAttackWeapon;
import biggestxuan.emcworld.api.item.equipment.weapon.IUpgradeableWeapon;
import biggestxuan.emcworld.common.capability.EMCWorldCapability;
import biggestxuan.emcworld.common.config.ConfigManager;
import biggestxuan.emcworld.common.utils.MathUtils;
import biggestxuan.emcworld.common.utils.Sponsors.Sponsors;
import mekanism.common.item.gear.ItemHazmatSuitArmor;
import mekanism.common.registries.MekanismItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = EMCWorld.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = {Dist.CLIENT}
)
public class ItemToolTipEvent {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void tooltipEvent(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        final Item[] radiationItem = new Item[]{
                MekanismItems.PLUTONIUM_PELLET.getItem(),MekanismItems.ANTIMATTER_PELLET.getItem(),MekanismItems.POLONIUM_PELLET.getItem()
        };
        if(stack.equals(ItemStack.EMPTY)) return;
        if(stack.getItem() instanceof IPlayerDifficultyItem){
            IPlayerDifficultyItem item_3 = (IPlayerDifficultyItem) stack.getItem();
            double diff = item_3.requireDifficulty();
            if(Minecraft.getInstance().player == null) return;
            double player_diff = Minecraft.getInstance().player.getCapability(EMCWorldCapability.UTIL).orElseThrow(NullPointerException::new).getDifficulty();
            if(player_diff < diff){
                event.getToolTip().add(EMCWorld.tc("message.all_diff",diff));
                return;
            }
        }
        if(stack.getItem() instanceof IDifficultyItem){
            IDifficultyItem item_2 = (IDifficultyItem) stack.getItem();
            if(ConfigManager.DIFFICULTY.get() < item_2.getDifficulty()){
                event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.difficulty_cant_use",item_2.getDifficulty()));
                return;
            }
        }
        if(stack.getItem() instanceof INeedLevelItem){
            INeedLevelItem item_1 = (INeedLevelItem) stack.getItem();
            if(Minecraft.getInstance().player == null) return;
            ClientPlayerEntity player = Minecraft.getInstance().player;
            int playerLevel = player.getCapability(EMCWorldCapability.PLAYER_LEVEL).orElseThrow(NullPointerException::new).getLevel();
            int itemLevel = item_1.getUseLevel(stack);
            if(playerLevel < itemLevel && !player.isCreative()){
                event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.need_level_to_use",itemLevel));
                return;
            }
        }
        if(stack.getItem() instanceof IAdditionsDamageWeapon){
            IAdditionsDamageWeapon item = (IAdditionsDamageWeapon) stack.getItem();
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.weapon_god_addition_damage",String.format("%.2f",item.getAdditionsDamage(stack))));
        }
        if(stack.getItem() instanceof IRangeAttackWeapon){
            IRangeAttackWeapon item1 = (IRangeAttackWeapon) stack.getItem();
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.weapon_god_attack_range",String.format("%.1f",item1.getAttackRange(stack))));
        }
        if(stack.getItem() instanceof IUpgradeableArmor){
            IUpgradeableArmor item_1_1 = (IUpgradeableArmor) stack.getItem();
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.armor_god_hurt",String.format("%.2f",item_1_1.hurtRate(stack)*100)).append("%"));
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.armor_god_health",String.format("%.2f",item_1_1.extraHealth(stack))));
        }
        if(stack.getItem() instanceof ICostEMCItem && stack.getItem() instanceof BaseWeaponItem){
            ICostEMCItem item2 = (ICostEMCItem) stack.getItem();
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.weapon_god_emc_cost",String.format("%.2f",item2.costEMCWhenAttack(stack)*100)+"%"));
        }
        if(stack.getItem() instanceof ISpeedArmor){
            ISpeedArmor item_2_5 = (ISpeedArmor) stack.getItem();
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.armor_god_speed",String.format("%.2f",item_2_5.getSpeed(stack))));
        }
        if(stack.getItem() instanceof ISecondEMCItem){
            ISecondEMCItem item3 = (ISecondEMCItem) stack.getItem();
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.weapon_god_emc_second", MathUtils.thousandSign(item3.EMCModifySecond(stack))));
        }
        for(Item item: radiationItem){
            if(stack.getItem().getItem().equals(item)){
                event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.radiation_item"));
            }
        }
        if(stack.getItem() instanceof ItemHazmatSuitArmor || stack.getItem().equals(MekanismItems.MODULE_RADIATION_SHIELDING.getItem().getItem())){
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.unclear_limit"));
        }
        if(stack.getItem() instanceof ISponsorItem){
            ISponsorItem item4 = (ISponsorItem) stack.getItem();
            Sponsors sp = item4.getSponsor();
            int level = 0;
            String name;
            if(sp == null){
                name = "";
            }else{
                level = sp.getSponsorLevel();
                name = sp.getPlayerName();
            }
            event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.sponsoritem",name).setStyle(Style.EMPTY.withItalic(true).withColor(getColor(level))));
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if(player == null) return;
            if(new Sponsors(player.getScoreboardName(),player.getUUID(), EMCWorldAPI.getInstance().getUtilCapability(player).getLevel()).equals(sp)){
                event.getToolTip().add(EMCWorld.tc("tooltip.emcworld.sponsoract"));
            }
        }
    }

    private static Color getColor(int level){
        switch (level){
            case 2:
                return Color.fromRgb(0x00aaaa);
            case 3:
                return Color.fromRgb(0xffaa00);
            default:
                return Color.fromRgb(0x00aa00);
        }
    }
}