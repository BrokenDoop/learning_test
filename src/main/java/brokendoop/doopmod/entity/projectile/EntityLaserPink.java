package brokendoop.doopmod.entity.projectile;

import brokendoop.doopmod.entity.particle.EntityLaserdustFX;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserPink extends EntityLaser{
	protected int ticksScaleDelay;
	protected int ticksToScale;
	protected float particleScale;
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
		this.laserSpeed = 2.2F;
		this.laserGravity = 0F;
		this.laserDamage = 0;
		this.laserFireDamage = 0;
	}

	public void tick() {
		float particleMin = 0.85F;
		float particleMax = 1.75F;
		int minDamage = 3;
		int maxDamage = 9;
		this.ticksScaleDelay = 4; // 4 is around 8 blocks at 2.2 speed // may need to be adjusted
		this.ticksToScale = 14; // 14 is around 40 blocks at 2.2 speed and 4 delay
		float greenAndBlue = (float)((Math.random() * 0.2F + 1F) * 0.625);
		super.tick();
		if (this.ticksInAir >= this.ticksScaleDelay) {
			this.particleScale = Math.min(particleMin + (particleMax - particleMin) * ((float) (this.ticksInAir - this.ticksScaleDelay) / this.ticksToScale), particleMax);
			int scaledDamage = Math.min(minDamage + (maxDamage - minDamage) * ((this.ticksInAir - this.ticksScaleDelay) / this.ticksToScale), maxDamage);
			this.laserDamage = scaledDamage - 1;
			this.laserFireDamage = scaledDamage;
		} else {
			this.particleScale = particleMin;
			this.laserDamage = minDamage - 2;
			this.laserFireDamage = minDamage - 1;
		}
		if (this.ticksInAir == (this.ticksToScale + this.ticksScaleDelay)) { //this happens when the scaling is done.
			createSphericalParticles(0.6, 12, 0.95, greenAndBlue, greenAndBlue + 0.05, this.particleScale, 0);
		}
		if (this.ticksInAir == this.ticksScaleDelay) { //this happens when the scaling starts.
			createSphericalParticles(0.6, 12, 0.95, greenAndBlue, greenAndBlue + 0.05, this.particleScale, 0);
		}

		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		spawnParticle(new EntityLaserdustFX(world, pOffsetX, pOffsetY, pOffsetZ, 0, 0, 0, 0.95, greenAndBlue, greenAndBlue + 0.05, this.particleScale));
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.95, greenAndBlue, greenAndBlue + 0.05, this.particleScale);
		}
	}
	public float getLaserScale(float partialTick){
		float minScale = 0.0478125F; //these values are -15% and +75% of the regular laser models scale.
		float maxScale = 0.0984375F;
		if (this.ticksInAir >= this.ticksScaleDelay) {
			return Math.min(minScale + (maxScale - minScale) * ((this.ticksInAir - this.ticksScaleDelay + partialTick) / this.ticksToScale), maxScale);
		} else {
			return minScale;
		}
	}
}

