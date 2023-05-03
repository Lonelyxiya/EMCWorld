package biggestxuan.emcworld.common.compact.CraftTweaker;

/**
 *  EMC WORLD MOD
 *  @Author Biggest_Xuan
 *  2023/02/23
 */

import biggestxuan.emcworld.EMCWorld;
import biggestxuan.emcworld.api.item.IEMCGod;
import biggestxuan.emcworld.common.exception.EMCWorldCommonException;
import biggestxuan.emcworld.common.registry.EWItems;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.emcworld.ItemUtils")
@SuppressWarnings("unused")
public class CrTItemUtils {
    @ZenCodeType.Method
    public static boolean isSword(IItemStack stack){
        return stack.getImmutableInternal().getItem() instanceof SwordItem;
    }

    @ZenCodeType.Method
    public static boolean isArmor(IItemStack stack){
        return stack.getImmutableInternal().getItem() instanceof ArmorItem;
    }

    @ZenCodeType.Method
    public static boolean isTool(IItemStack stack){
        return stack.getImmutableInternal().getItem() instanceof ToolItem;
    }

    @ZenCodeType.Method
    public static boolean isTier(IItemStack stack){
        return stack.getImmutableInternal().getItem() instanceof TieredItem;
    }

    @ZenCodeType.Method
    public static IIngredient getEMCGodItemWithLevel(int level){
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("level",level);
        return getEMCGodItems(nbt);
    }

    @ZenCodeType.Method
    public static IIngredient getEMCGodItemWithMaxLevel(){
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("level",30);
        nbt.putInt("prefix",10);
        nbt.putInt("star",8);
        nbt.putInt("max_star",8);
        nbt.putBoolean("star_init",true);
        return getEMCGodItems(nbt);
    }

    public static IIngredient getEMCGodItems(CompoundNBT nbt){
        List<ItemStack> list = new ArrayList<>();
        try{
            for(Item item : get()){
                if(item instanceof IEMCGod && !(item instanceof ArmorItem)){
                    ItemStack stack = new ItemStack(item);
                    CompoundNBT nbt1 = stack.getOrCreateTag();
                    nbt1.merge(nbt);
                    list.add(stack);
                }
            }
        }catch (Exception e){
            EMCWorld.LOGGER.error(e.getMessage());
            throw new EMCWorldCommonException("EMCWorld caused a error!");
        }
        ItemStack[] stacks = list.toArray(new ItemStack[0]);
        Ingredient in = Ingredient.of(stacks);
        return IIngredient.fromIngredient(in);
    }

    @Nonnull
    private static List<Item> get() throws Exception {
        List<Item> list = new ArrayList<>();
        Class<?> clz = Class.forName("biggestxuan.emcworld.common.registry.EWItems");
        Field[] fields = EWItems.class.getDeclaredFields();
        for(Field f : fields){
            f.setAccessible(true);
            int mod = f.getModifiers();
            if(Modifier.isFinal(mod) && Modifier.isStatic(mod)){
                Object o = f.get(clz);
                if(o instanceof RegistryObject<?>){
                    RegistryObject<Item> r = (RegistryObject<Item>) o;
                    list.add(r.get());
                }
            }
        }
        return list;
    }
}
