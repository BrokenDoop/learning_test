package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserPink extends EntityLaser {
	public EntityLaserPink(World world) { super(world, 3); }

	public EntityLaserPink(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 3);
	}

	public EntityLaserPink(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
		super(world, entityliving, doesArrowBelongToPlayer, 3);
	}

	protected void init() {
		this.laserBounce = 0;
		this.laserPierce = 0;
		this.laserSpread = 0;
		this.laserSpeed = 2.4F;
		this.laserGravity = 0F;
		this.laserDamage = 3;
		this.laserFireDamage = 4;
	}
	public void tick() {
		float endScale = 2F;
		float startScale = 0.0478125F;
		this.laserScale = startScale;
		double greenAndBlue = ((float)Math.random() * 0.2F + 1F) * 0.625;
		super.tick();
		if (this.laserScale < endScale) {
			this.laserScale = startScale + (endScale - startScale) * ((float) (this.ticksInAir) / 18);
		}
		if (this.ticksInAir == 18) {
			createSphericalParticles(1, 32, 1, 0, 0, 1);
		}

		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.95, greenAndBlue, greenAndBlue + 0.05);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.95, greenAndBlue, greenAndBlue + 0.05, 1);
		}
	}
}

