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
		this.laserSpeed = 2F;
		this.laserGravity = 0F;
		this.laserDamage = 3;
		this.laserFireDamage = 3;
	}
	public void tick() {
		super.tick();
		this.world.spawnParticle("laserdust", this.x, this.y, this.z, 1, 0.75, 0.75);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 1, 0.75, 0.75);
		}
	}
}

