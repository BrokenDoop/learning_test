package brokendoop.doopmod.block;

import brokendoop.doopmod.DoopMod;
import brokendoop.doopmod.UtilIdRegistrar;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import turniplabs.halplibe.helper.BlockBuilder;



public class ModBlocks {

	public static final Block focuscrystalblock = new BlockBuilder(DoopMod.MOD_ID)
		.setHardness(0.5f)
		.setTextures("focuscrystalblock.png")
		.setBlockSound(BlockSounds.GLASS)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new BlockFocusCrystal("focuscrystalblock", UtilIdRegistrar.nextIdBlock(), Material.glass));








	public static void register() {}
}
