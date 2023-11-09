package brokendoop.doopmod.block;

import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.material.Material;


public class BlockFocusCrystal extends BlockTransparent {
	public BlockFocusCrystal(String key, int id, Material material) {
		super(key, id, material, false);
	}

	public int getRenderBlockPass() {
		return 1;
	}

}
