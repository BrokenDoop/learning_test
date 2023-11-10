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
		this.laserPierce = 3;
		this.laserSpread = 3;
		this.laserSpeed = 1.2F;
		this.laserGravity = 0F;
		this.laserDamage = 3;
		this.laserFireDamage = 4;
	}
	public void tick() {
		super.tick();
		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.05, 0.05, 0.05);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.05, 0.05, 0.05);
		}
	}
}
