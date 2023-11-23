package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserGreen extends EntityLaser{
	public EntityLaserGreen(World world) {
		super(world, 2);
	}

	public EntityLaserGreen(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 2);
	}

	public EntityLaserGreen(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
		super(world, entityliving, doesArrowBelongToPlayer, 2);
	}

	protected void init() {
		this.laserBounce = 6;
		this.laserPierce = 3;
		this.laserSpread = 2;
		this.laserSpeed = 0.8F;
		this.laserGravity = 0.015F;
		this.laserDamage = 2;
		this.laserFireDamage = 3;
	}
	public void tick() {
		super.tick();
		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0, 0.9 , 0);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0, 0.9 , 0, 1);
		}
	}
}
