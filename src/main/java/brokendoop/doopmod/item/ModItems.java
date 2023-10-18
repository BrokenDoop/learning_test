package brokendoop.doopmod.item;

import brokendoop.doopmod.DoopMod;
import brokendoop.doopmod.UtilIdRegistrar;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemHelper;

public class ModItems {

	public static final Item focuscrystal = ItemHelper.createItem(DoopMod.MOD_ID, new Item("focus.crystal", UtilIdRegistrar.nextIdItem()), "focus.crystal","focus_crystal.png");
	public static void register() {

	}
}
