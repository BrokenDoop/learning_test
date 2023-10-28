package brokendoop.doopmod.item;

import brokendoop.doopmod.entity.projectile.*;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
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
			EntityLaser laserToFireL = new  EntityLaserYellow(world, entityplayer, true);
			EntityLaser laserToFireR = new  EntityLaserYellow(world, entityplayer, true);
			laserToFire = new  EntityLaserYellow(world, entityplayer, true);


			double spreadAmount = 1;

			//Gross vector math
			Vec3d lookDir = entityplayer.getLookAngle();

			if (entityplayer.xRot > 89.9){
				entityplayer.xRot = 89.9f;
			}
			if (entityplayer.xRot < -89.9){
				entityplayer.xRot = -89.9f;
			}

			Vec3d lookDir2 = entityplayer.getLookAngle();

			lookDir2.rotateAroundY((float) Math.toRadians(90));
			lookDir2.yCoord = 0;
			Vec3d newVec = lookDir.addVector(lookDir2.normalize().xCoord * spreadAmount, lookDir2.normalize().yCoord * spreadAmount, lookDir2.normalize().zCoord * spreadAmount);
			laserToFireR.setLaserHeading(newVec.xCoord, newVec.yCoord, newVec.zCoord,1.5F, 1.0F);
			newVec = lookDir.addVector(-lookDir2.normalize().xCoord * spreadAmount, -lookDir2.normalize().yCoord * spreadAmount, -lookDir2.normalize().zCoord * spreadAmount);
			laserToFireL.setLaserHeading(newVec.xCoord, newVec.yCoord, newVec.zCoord,1.5F, 1.0F);

			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "doopmod.laser.shot", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(laserToFire);
				world.entityJoinedWorld(laserToFireL);
				world.entityJoinedWorld(laserToFireR);
			}
			return itemstack;
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
