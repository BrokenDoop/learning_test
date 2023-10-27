package brokendoop.doopmod.item;

import brokendoop.doopmod.entity.projectile.*;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemCreativeBlaster extends Item {
	public ItemCreativeBlaster(String name, int id) {
		super(name, id);
		this.setMaxDamage(899);
		this.maxStackSize = 1;
	}

	public boolean isFull3D() {
		return true;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		Entity entityToFire = null;
		if (entityplayer.inventory.consumeInventoryItem(Item.dustRedstone.id)) {
			entityToFire = new EntityLaser(world, entityplayer, true, 0);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.dye.id)) { //stupid method doesnt let you specify metadeta so I need to change this somehow to only use lapis.
			entityToFire = new  EntityLaserBlue(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.olivine.id)) {
			entityToFire = new EntityLaserGreen(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.quartz.id)) {
			entityToFire = new EntityLaserPink(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.coal.id)) {
			entityToFire = new  EntityLaserBlack(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.nethercoal.id)) {
			entityToFire = new  EntityLaserOrange(world, entityplayer, true);
		}
		if (entityToFire != null){
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "doopmod.laser.shot", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(entityToFire);
			}
		}
		return itemstack;
	}
}
