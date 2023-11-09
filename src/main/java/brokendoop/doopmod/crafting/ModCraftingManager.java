package brokendoop.doopmod.crafting;

import turniplabs.halplibe.helper.RecipeHelper;
import brokendoop.doopmod.block.ModBlocks;
import brokendoop.doopmod.item.ModItems;

public class ModCraftingManager {

	public static void register() {

		RecipeHelper.Crafting.createRecipe(ModBlocks.focuscrystalblock, 1, new Object[]{
			"III",
			"III",
			"III",
			'I', ModItems.focuscrystal});
		RecipeHelper.Crafting.createShapelessRecipe(ModItems.focuscrystal, 9, new Object[]{ModBlocks.focuscrystalblock});
	}
}
