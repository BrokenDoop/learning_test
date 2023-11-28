package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserPink extends EntityLaser{
	public EntityLaserPink(World world) {
		super(world, 3);
	}

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
		this.laserSpeed = 0.000001F;
		this.laserGravity = 0F;
		this.laserDamage = 3;
		this.laserFireDamage = 4;
	}

	public void tick() {
		float minScale = 0.0478125F;
		float maxScale = 2F;
		int ticksToScale = 18;
		int ticksDelay = 18;
		this.laserScale = minScale;

		super.tick();
		if (this.ticksInAir == 160) {
			createSphericalParticles(1, 32, 1, 0, 0, 2);
		}
		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		double greenAndBlue = ((float)Math.random() * 0.2F + 1F) * 0.625;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.95, greenAndBlue, greenAndBlue + 0.05);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.95, greenAndBlue, greenAndBlue + 0.05, 1);
		}
	}
	public float getLaserScale(float partialTick){ //values are for testing. It won't get this large, it will be subtle.
		float minScale = 0.0478125F;
		float maxScale = 2F;
		int ticksToScale = 140;
		int ticksDelay = 20;
		if (this.ticksInAir >= ticksDelay) {
			return Math.min(minScale + (maxScale - minScale) * ((this.ticksInAir - ticksDelay + partialTick) / ticksToScale), maxScale);
		} else {
			return minScale;
		}
	}
}

