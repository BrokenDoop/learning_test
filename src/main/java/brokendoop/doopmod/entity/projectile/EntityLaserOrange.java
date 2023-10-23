package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserOrange extends EntityLaser{
	public EntityLaserOrange(World world) {
		super(world, 5);
	}

	public EntityLaserOrange(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 5);
	}

	public EntityLaserOrange(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer) {
		super(world, entityliving, doesArrowBelongToPlayer, 5);
	}

	protected void init() {
		this.laserBounce = 0;
		this.laserPierce = 0;
		this.laserSpread = 3;
		this.laserSpeed = 1.2F;
		this.laserGravity = 0F;
		this.laserDamage = 0;
		this.laserFireDamage = 3;
	}
	public void tick(){
		super.tick();
		this.world.spawnParticle("laserdust", this.x, this.y, this.z, 0.9, 0.5 , 0.1);
		if (this.isInWater()) {
			this.remove();
			this.world.playSoundAtEntity(this, "random.fizz", 1F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
		}
		if (hitResult != null) {
			if (hitResult.entity != null) {
				hitResult.entity.fireHurt();
			}
		}

	}
}

