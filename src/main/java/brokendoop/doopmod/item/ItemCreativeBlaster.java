package brokendoop.doopmod.item;

import brokendoop.doopmod.entity.projectile.*;
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
		EntityLaser laserToFire = null;
		if (entityplayer.inventory.consumeInventoryItem(Item.dustRedstone.id)) {
			laserToFire = new EntityLaser(world, entityplayer, true, 0);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.dye.id)) { //stupid method doesnt let you specify metadeta so I need to change this somehow to only use lapis.
			laserToFire = new  EntityLaserBlue(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.olivine.id)) {
			laserToFire = new EntityLaserGreen(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.quartz.id)) {
			laserToFire = new EntityLaserPink(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.coal.id)) {
			laserToFire = new  EntityLaserBlack(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.nethercoal.id)) {
			laserToFire = new  EntityLaserOrange(world, entityplayer, true);
		} else if (entityplayer.inventory.consumeInventoryItem(Item.dustGlowstone.id)) {
			laserToFire = new  EntityLaserYellow(world, entityplayer, true);


			//I tried calculating an offset for the firing angle but I couldnt do it without it breaking when you look up/down in different ways.

			//laserToFire.setLaserHeading(newDirectionX, newDirectionY, newDirectionZ, 1.5f, 1.0f);
		}
		if (laserToFire != null){
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "doopmod.laser.shot", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(laserToFire);
			}
		}
		return itemstack;
	}
}
