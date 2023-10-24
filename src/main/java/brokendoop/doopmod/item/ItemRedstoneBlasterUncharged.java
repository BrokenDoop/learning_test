package brokendoop.doopmod.item;


import net.minecraft.core.item.Item;


public class ItemRedstoneBlasterUncharged extends Item {
	public ItemRedstoneBlasterUncharged(String name, int id) {
		super(name, id);
		this.setMaxDamage(484);
		this.maxStackSize = 1;
	}
	public boolean isFull3D() {
		return true;
	}

}
