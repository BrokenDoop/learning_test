package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
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
		this.laserFireDamage = 4;
	}
	@Override
	public void beforeBounces(){
		Block block = world.getBlock(this.xTile, this.yTile, this.zTile);
		if (block != null && (block.blockMaterial == Material.water || block.blockMaterial == Material.ice)) {
			this.hitSound = "random.fizz";
			this.hitSoundPitch = 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F;
		}
	}
	public void tick(){
		collisionExclusionList.add(Block.fluidLavaStill.id);
		collisionExclusionList.add(Block.fluidLavaFlowing.id);
		collisionExclusionList.remove(15);
		// Normal Laser tick
		super.tick();
		this.collideWithWater = true;
		double pOffsetX = this.x - this.xd;
		double pOffsetY = this.y - this.yd;
		double pOffsetZ = this.z - this.zd;
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.9, ((float)Math.random() * 0.3F + 0.9F) * 0.45 , 0);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.9, ((float)Math.random() * 0.3F + 0.9F) * 0.45, 0);
		}

		if (hitResult != null) {
			if (hitResult.entity != null) {
				hitResult.entity.remainingFireTicks = 150;
			}
		}
	}
}

