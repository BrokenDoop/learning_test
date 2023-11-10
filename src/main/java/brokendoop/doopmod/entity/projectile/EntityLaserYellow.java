package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserYellow extends EntityLaser{
	public EntityLaserYellow(World world) {
		super(world, 6);
	}

	public EntityLaserYellow(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 6);
	}

	public EntityLaserYellow(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
		super(world, entityliving, doesArrowBelongToPlayer, 6);
	}

	protected void init() {
		this.laserBounce = 0;
		this.laserPierce = 0;
		this.laserSpread = 2;
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
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 1, 1, 0);
		if (this.ticksInAir == 15) {
			this.world.playSoundAtEntity(this, "doopmod.laser.hit", 1.0F, 1F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.remove();
		}
		if (this.removed) {
			createSphericalParticles(0.25, 8, 1, 1, 0);
		}
	}
}
