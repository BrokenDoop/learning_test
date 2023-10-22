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
		this.laserPierce = 0;
		this.laserSpread = 6;
		this.laserSpeed = 0.5F;
		this.laserGravity = 0.009F;
		this.laserDamage = 4;
		this.laserFireDamage = 6;
	}
	public void tick(){
		super.tick();
		this.world.spawnParticle("laserdust", this.x, this.y, this.z, 0.2, 0.2 , 0.2);
	}
}
