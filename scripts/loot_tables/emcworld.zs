# priority 27
import crafttweaker.api.item.IItemStack;
import mods.emcworld.math;
import mods.emcworld.configHelper;

public function tweakerBotania() as void{
    var ei = <item:extendedcrafting:ender_ingot>;
    var eei = <item:botania:elementium_ingot>;
    var iss = <item:mekanism:ingot_steel>;
    var name as string = "botania:gaia_guardian_2";
    var bn as string = "botania:gaia_guardian";
    var ii as IItemStack[] = [
        <item:mythicbotany:asgard_rune>,
        <item:mythicbotany:vanaheim_rune>,
        <item:mythicbotany:alfheim_rune>,
        <item:mythicbotany:midgard_rune>,
        <item:mythicbotany:joetunheim_rune>,
        <item:mythicbotany:muspelheim_rune>,
        <item:mythicbotany:niflheim_rune>,
        <item:mythicbotany:nidavellir_rune>,
        <item:mythicbotany:helheim_rune>,
        <item:emcworld:scroll_green>
    ];
    for i in [ei,eei]{
        modifyLootTable(i*8,1f,getRL(name));
        modifyLootTable(i*6,0.33f,getRL(name));
        modifyLootTable(i*5,0.22f,getRL(name));
        modifyLootTable(i*4,0.15f,getRL(name));
        modifyLootTable(i*3,0.1f,getRL(name));
        modifyLootTable(i*2,0.06f,getRL(name));
        modifyLootTable(i,0.04f,getRL(name));
    }
    for i in ii{
        modifyLootTable(i*3,0.1f,getRL(name));
        modifyLootTable(i*2,0.15f,getRL(name));
        modifyLootTable(i*1,0.2f,getRL(name));
    }
    for n in [name,bn] as string[]{
        for a in 1 .. 7{
            modifyLootTable(<item:emcworld:scroll_white>*a,0.22f-0.03f*a,getRL(n));
        }
    }
    modifyLootTable(iss,0.45f,getRL(bn));
    modifyLootTable(iss*2,0.4f,getRL(bn));
    modifyLootTable(iss*3,0.3f,getRL(bn));
    modifyLootTable(iss*4,0.25f,getRL(bn));
    modifyLootTable(iss*5,0.2f,getRL(bn));
    modifyLootTable(iss*6,0.15f,getRL(bn));
    modifyLootTable(iss*7,0.1f,getRL(bn));
    modifyLootTable(iss*8,0.05f,getRL(bn));
    modifyLootTable(<item:emcworld:base_emc_stored_totem>.withDamage(math.getRangeRandom(0,50000)),1f,getRL(name));
}

public function gns() as void{
    var names as string[] = [
        "good_nights_sleep:entities/baby_creeper",
        "good_nights_sleep:entities/gns_spawner",
        "good_nights_sleep:entities/herobrine",
        "good_nights_sleep:entities/tormenter"
    ];
    var bi = <item:extendedcrafting:black_iron_ingot>;
    if(configHelper.isFreeMode()) return;
    for i in names{
        modifyLootTable(<item:bloodmagic:ingot_hellforged>,0.1f,getRL(i));
        modifyLootTable(<item:bloodmagic:ingot_hellforged>*2,0.07f,getRL(i));
        modifyLootTable(bi*2,0.15f,getRL(i));
        modifyLootTable(bi*1,0.2f,getRL(i));
        modifyLootTable(bi*3,0.1f,getRL(i));
    }
}

public function ip() as void{
    var ri = <item:mekanism:ingot_refined_obsidian>;
    var ig = <item:emcworld:illager_gem>;
    var names as string[] = [
        "illagers_plus:structure/fort_common",
        "illagers_plus:structure/fort_rare",
        "illagers_plus:structure/ice_castle_common",
        "illagers_plus:structure/ice_castle_rare",
        "illagers_plus:structure/illager_archer_tower",
        "illagers_plus:structure/illager_centre",
        "illagers_plus:structure/illager_mine",
        "illagers_plus:structure/illager_tower_archery",
        "illagers_plus:structure/illager_tower_brewing",
        "illagers_plus:structure/illager_tower_common",
        "illagers_plus:structure/illager_tower_dendrology",
        "illagers_plus:structure/illager_tower_enchanting",
        "illagers_plus:structure/illager_tower_rare",
        "illagers_plus:structure/illager_tower_uncommon",
        "minecraft:chests/pillager_outpost"
    ];
    for i in [0,1]{
        var a as string = names[i];
        if(!configHelper.isFreeMode()){
            modifyLootTable(ig*3,0.06f,getRL(a));
            modifyLootTable(ig*4,0.04f,getRL(a));
            modifyLootTable(ig*5,0.02f,getRL(a));
            modifyLootTable(<item:emcworld:biggest_emc_gem>*4,0.1f,getRL(a));
            modifyLootTable(<item:emcworld:biggest_emc_gem>*7,0.05f,getRL(a));
            modifyLootTable(<item:emcworld:advanced_emc_stored_totem>.withDamage(math.getRangeRandom(7000000,9500000)),0.01f,getRL(a));
            modifyLootTable(<item:emcworld:biggest_emc_gem>*10,0.02f,getRL(a));
        }else{
            modifyLootTable(ig,0.1f,getRL(a));
            modifyLootTable(ig*2,0.08f,getRL(a));
        }
        
    }
    modifyLootTable(ri*3,0.04f,getRL(names[1]));
    for i in names{
        modifyLootTable(<item:emcworld:big_emc_gem>*5,0.05f,getRL(i));
        modifyLootTable(<item:emcworld:biggest_emc_gem>*3,0.1f,getRL(i));
        modifyLootTable(<item:emcworld:biggest_emc_gem>*6,0.1f,getRL(i));
        modifyLootTable(ig*2,0.06f,getRL(i));
        modifyLootTable(ig*3,0.04f,getRL(i));
    }
}

public function r() as void{
    k(<item:rats:oratchalcum_ingot>,1f);
    k(<item:rats:ratlantean_flame>,3.5f);
}

public function k(a as IItemStack,p as float) as void{
    var n = "rats:chest/dutchrat_ship";
    modifyLootTable(a,0.07f*p,getRL(n));
    modifyLootTable(a*2,0.03f*p,getRL(n));
    modifyLootTable(a*3,0.01f*p,getRL(n));
}

public function tweakerEMCWorld() as void{
    tweakerBotania();gns();ip();r();
}