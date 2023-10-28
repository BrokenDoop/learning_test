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
		this.laserPierce = 3;
		this.laserSpread = 0;
		this.laserSpeed = 1.2F;
		this.laserGravity = 0F;
		this.laserDamage = 3;
		this.laserFireDamage = 4;
	}
	public void tick() {
		super.tick();
		this.world.spawnParticle("laserdust", this.x, this.y, this.z, 1, 1, 0);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 1, 1, 0);
		}
	}
}