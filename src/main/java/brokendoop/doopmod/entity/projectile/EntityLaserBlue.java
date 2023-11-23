package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserBlue extends EntityLaser{
	public EntityLaserBlue(World world) {
		super(world, 1);
	}

	public EntityLaserBlue(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 1);
	}

	public EntityLaserBlue(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
		super(world, entityliving, doesArrowBelongToPlayer, 1);
	}

	protected void init() {
		this.laserBounce = 6;
		this.laserPierce = 3;
		this.laserSpread = 3;
		this.laserSpeed = 0.7F;
		this.laserGravity = 0F;
		this.laserDamage = 2;
		this.laserFireDamage = 3;
	}
	public void tick() {
		super.tick();
		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.1, 0.1 , 1);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.1, 0.1 , 1, 1);
		}
	}
}

