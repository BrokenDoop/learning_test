package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityLaserOrange extends EntityLaser{
	public EntityLaserOrange(World world) {
		super(world, 5);
	}

	public EntityLaserOrange(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 5);
	}

	public EntityLaserOrange(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
		super(world, entityliving, doesArrowBelongToPlayer, 5);
	}

	protected void init() {
		this.laserBounce = 0;
		this.laserPierce = 0;
		this.laserSpread = 3;
		this.laserSpeed = 1.2F;
		this.laserGravity = 0F;
		this.laserDamage = 0;
		this.laserFireDamage = 4;
	}
	public void tick(){
		// Check for water collision
		Vec3d oldPos = Vec3d.createVector(this.x, this.y, this.z);
		Vec3d newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		HitResult waterHit = this.world.checkBlockCollisionBetweenPoints(oldPos, newPos, true, false);
		if (waterHit != null && waterHit.hitType == HitResult.HitType.TILE){
			Block block = world.getBlock(waterHit.x, waterHit.y, waterHit.z);
			if (block.blockMaterial == Material.water || block.blockMaterial == Material.ice) {
				this.remove();
				this.world.playSoundAtEntity(this, "random.fizz", 1F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
				createSphericalParticles(0.25, 8, 0.9, 0.5, 0.1);
				return;
			}
		}

		// Normal Laser tick
		super.tick();
		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.9, ((float)Math.random() * 0.3F + 0.9F) * 0.45 , 0);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.9, ((float)Math.random() * 0.3F + 0.9F) * 0.45, 0);
		}

		// Burn Entity on hit
		if (hitResult != null) {
			if (hitResult.entity != null) {
				hitResult.entity.remainingFireTicks = 150;
			}
		}
	}
}

