package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserBlack extends EntityLaser{
	public EntityLaserBlack(World world) {
		super(world, 4);
	}

	public EntityLaserBlack(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 4);
	}

	public EntityLaserBlack(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
		super(world, entityliving, doesArrowBelongToPlayer, 4);
	}

	protected void init() {
		this.laserBounce = 0;
		this.laserPierce = 0;
		this.laserSpread = 6;
		this.laserSpeed = 0.6F;
		this.laserGravity = 0F;
		this.laserDamage = 4;
		this.laserFireDamage = 5;
	}
	public void tick() {
		super.tick();
		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.05, 0.05, 0.05);
		if (this.ticksInAir == 26) {
			this.world.playSoundAtEntity(this, "doopmod.laser.hit", 1.0F, 1F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.remove();
		}
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.05, 0.05, 0.05);
		}
	}
}
