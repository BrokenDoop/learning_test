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
		if (entityplayer.inventory.consumeInventoryItem(Item.dustRedstone.id)) {
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "doopmod.lasershot", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityLaser(world, entityplayer, true, 0));
			}
		} else if (entityplayer.inventory.consumeInventoryItem(Item.dye.id)) { //stupid method doesnt let you specify metadeta so I need to change this somehow to only use lapis.
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityLaserBlue(world, entityplayer, true));
			}
		} else if (entityplayer.inventory.consumeInventoryItem(Item.olivine.id)) {
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityLaserGreen(world, entityplayer, true));
			}
		} else if (entityplayer.inventory.consumeInventoryItem(Item.quartz.id)) {
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityLaserPink(world, entityplayer, true));
			}
		} else if (entityplayer.inventory.consumeInventoryItem(Item.coal.id)) {
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityLaserBlack(world, entityplayer, true));
			}
		} else if (entityplayer.inventory.consumeInventoryItem(Item.nethercoal.id)) {
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityLaserOrange(world, entityplayer, true));
			}
		}


		return itemstack;
	}
}
