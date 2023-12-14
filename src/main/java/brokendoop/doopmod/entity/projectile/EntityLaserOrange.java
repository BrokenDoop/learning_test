package brokendoop.doopmod.entity.projectile;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityLaserOrange extends EntityLaser{
	protected boolean doSmokeParticles = false;
	public EntityLaserOrange(World world) { super(world, 5); }

	public EntityLaserOrange(World world, double d, double d1, double d2) { super(world, d, d1, d2, 5); }

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
		int meta = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
		if (block != null && (block.blockMaterial == Material.water || block.blockMaterial == Material.ice || block.blockMaterial == Material.snow || block.blockMaterial == Material.topSnow)) {
			this.doSmokeParticles = true;
			this.hitSound = "random.fizz";
			this.hitSoundPitch = 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F;
			if (block.id == Block.layerSnow.id) {
				if (meta > 0) {
					--meta;
					this.world.setBlockAndMetadataWithNotify(this.xTile, this.yTile, this.zTile, Block.layerSnow.id, meta);
				} else if (meta == 0) {
					this.world.setBlockWithNotify(this.xTile, this.yTile, this.zTile, 0);
				}
			}
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
		float green = (float) ((Math.random() * 0.2F + 1F) * 0.45);
		this.world.spawnParticle("laserdust", pOffsetX, pOffsetY, pOffsetZ, 0.9, green, 0);
		if (this.removed) {
			createSphericalParticles(0.25, 8, 0.9, green, 0, 1);
			if (this.doSmokeParticles) {
				createSphericalParticles(0.45, 8, 0, 0, 0, 1, 1);
			}
		}

		if (hitResult != null) {
			if (hitResult.entity != null) {
				hitResult.entity.remainingFireTicks = 150;
			}
		}
	}
}
