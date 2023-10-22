package brokendoop.doopmod.entity;

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
		this.laserGravity = 0.01F;
		this.laserDamage = 2;
		this.laserFireDamage = 3;
	}
	public void tick(){
		super.tick();
		this.world.spawnParticle("reddust", this.x, this.y, this.z, 0, 0.9 , 0);
	}
}
