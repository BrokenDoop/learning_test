package brokendoop.doopmod.entity.projectile;


import com.mojang.nbt.CompoundTag;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityLaser extends Entity {
	//your shits all fucked, fix it, need to fix this entirely.
	protected int xTile;
	protected int yTile;
	protected int zTile;
	protected int xTileOld;
	protected int yTileOld;
	protected int zTileOld;
	protected HitResult hitResult;
	public boolean doesLaserBelongToPlayer;
	public EntityLiving owner;
	protected int ticksInAir;
	protected int ticksSoundDelay;
	protected double laserSpeed;
	protected float laserBounce;
	protected float laserPierce;
	protected int laserSpread;
	protected float laserGravity;
	protected int laserDamage;
	protected int laserFireDamage;
	protected int laserType;
	private static final List<Integer> collisionExclusionList = new ArrayList<>();
	static {
		collisionExclusionList.add(0);
		collisionExclusionList.add(Block.glass.id);
		collisionExclusionList.add(Block.trapdoorGlass.id);
		collisionExclusionList.add(Block.ice.id);
	}
	public EntityLaser(World world) {
		this(world, 0);
	}

	public EntityLaser(World world, int laserType) {
		this(world, 0, 0, 0, laserType);
	}

	public EntityLaser(World world, double x, double y, double z, int laserType) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		xTileOld = xTile;
		yTileOld = yTile;
		zTileOld = zTile;
		this.doesLaserBelongToPlayer = false;
		this.laserType = laserType;
		this.ticksInAir = 0;
		this.ticksSoundDelay = 0;
		this.fireImmune = true;
		this.setSize(0.5F, 0.5F);
		this.setPos(x, y, z);
		this.heightOffset = 0.0F;
	}

	public EntityLaser(World world, EntityLiving entityliving, boolean doesLaserBelongToPlayer, int laserType) {
		this(world, laserType);
		this.doesLaserBelongToPlayer = doesLaserBelongToPlayer;
		this.owner = entityliving;
		this.moveTo(entityliving.x, entityliving.y + (double)entityliving.getHeadHeight(), entityliving.z, entityliving.yRot, entityliving.xRot);
		this.setPos(
			this.x - (MathHelper.cos((float)Math.toRadians(this.yRot)) * 0.16F),
			this.y - 0.1,
			this.z - (MathHelper.sin((float)Math.toRadians(this.yRot)) * 0.16F));

		Vec3d lookDir = entityliving.getLookAngle();
		this.setLaserHeading(lookDir.xCoord, lookDir.yCoord, lookDir.zCoord,1.5F, 1.0F);
	}

	protected void init() {
		this.laserBounce = 0;
		this.laserPierce = 3;
		this.laserSpread = 3;
		this.laserSpeed = 1.2F;
		this.laserGravity = 0F;
		this.laserDamage = 3;
		this.laserFireDamage = 4;
		if (!(this.owner instanceof EntityPlayer)) {
			this.doesLaserBelongToPlayer = false;
		}
	}

	public void setLaserHeading(double deltaX, double deltaY, double deltaZ, float f, float f1) {
		float currentSpeed = MathHelper.sqrt_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		// Gets unit vectors?
		deltaX /= currentSpeed;
		deltaY /= currentSpeed;
		deltaZ /= currentSpeed;

		// Add random spread
		double i = 0.0075 * laserSpread;
		deltaX += this.random.nextGaussian() * i * (double)f1;
		deltaY += this.random.nextGaussian() * i * (double)f1;
		deltaZ += this.random.nextGaussian() * i * (double)f1;

		// Scale unit vectors to speed
		deltaX *= laserSpeed;
		deltaY *= laserSpeed;
		deltaZ *= laserSpeed;

		// Set Laser velocity
		this.xd = deltaX;
		this.yd = deltaY;
		this.zd = deltaZ;

		// Set arrow rotation?
		float currentSpeedHorizontally = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		this.yRotO = this.yRot = (float)Math.toDegrees(Math.atan2(deltaX, deltaZ));
		this.xRotO = this.xRot = (float)Math.toDegrees(Math.atan2(deltaY, currentSpeedHorizontally));
		this.ticksInAir = 0;
		this.ticksSoundDelay = 0;
	}

	public void lerpMotion(double xd, double yd, double zd) {
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt_double(xd * xd + zd * zd);
			this.yRotO = this.yRot = (float)Math.toDegrees(Math.atan2(xd, zd));
			this.xRotO = this.xRot = (float)Math.toDegrees(Math.atan2(yd, f));
			this.moveTo(this.x, this.y, this.z, this.yRot, this.xRot);
		}

	}

	public void tick() {
		xTileOld = xTile;
		yTileOld = yTile;
		zTileOld = zTile;
		super.tick();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
			this.yRotO = this.yRot = (float)Math.toDegrees(Math.atan2(this.xd, this.zd));
			this.xRotO = this.xRot = (float)Math.toDegrees(Math.atan2(this.yd, f));
		}
		++this.ticksSoundDelay;
		++this.ticksInAir;
		Vec3d oldPos = Vec3d.createVector(this.x, this.y, this.z);
		Vec3d newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		hitResult = checkBlockCollisionBetweenPointsCustomBlacklist(oldPos, newPos, false, true, collisionExclusionList);
		oldPos = Vec3d.createVector(this.x, this.y, this.z);
		newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		if (hitResult != null) {
			newPos = Vec3d.createVector(hitResult.location.xCoord, hitResult.location.yCoord, hitResult.location.zCoord);
		}

		Entity entity = null;
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).expand(1.0, 1.0, 1.0));
		double closestEntity = 0.0;

		for (Entity listEntity : list) {
			if (listEntity.isPickable() && (listEntity != this.owner || this.ticksInAir >= 5)) {
				float expansionAmount = 0.3F;
				AABB axisAlignedBB1 = listEntity.bb.expand(expansionAmount, expansionAmount, expansionAmount);
				HitResult movingObjectPosition1 = axisAlignedBB1.func_1169_a(oldPos, newPos);
				if (movingObjectPosition1 != null) {
					double distanceToEntity = oldPos.distanceTo(movingObjectPosition1.location);
					if (distanceToEntity < closestEntity || closestEntity == 0.0) {
						entity = listEntity;
						closestEntity = distanceToEntity;
					}
				}
			}
		}

		if (entity != null) {
			hitResult = new HitResult(entity);
		}

		if (hitResult != null) {
			if (hitResult.entity != null) {
				if (hitResult.entity.hurt(this.owner, this.laserDamage, DamageType.COMBAT)) {
					if (entity instanceof EntityLiving) {
						entity.heartsFlashTime = 0;
					}
					hitResult.entity.hurt(this.owner, this.laserFireDamage, DamageType.FIRE);
					if (laserPierce > 0) {
						this.world.playSoundAtEntity(this, "doopmod.laser.pierce", 1.0F, 1F / (this.random.nextFloat() * 0.2F + 0.9F));
						laserPierce--;
					} else {
						this.remove();
						this.world.playSoundAtEntity(this, "doopmod.laser.hit", 1.0F, 1F / (this.random.nextFloat() * 0.2F + 0.9F));
					}
				}
			} else {
				this.xTile = hitResult.x;
				this.yTile = hitResult.y;
				this.zTile = hitResult.z;

				float speed = MathHelper.sqrt_double(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
				this.x -= this.xd / (double)speed * 0.05;
				this.y -= this.yd / (double)speed * 0.05;
				this.z -= this.zd / (double)speed * 0.05;
			}
		}

		this.x += this.xd;
		this.y += this.yd;
		this.z += this.zd;

		this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);

		// This actually rotates the laser apparently
		float horizontalSpeed = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
		this.xRot = (float) Math.toDegrees(Math.atan2(this.yd, horizontalSpeed));

		// Keeps rotation bounded to (-180, 180]
		while (this.xRot - this.xRotO < -180.0F) {
			this.xRotO -= 360.0F;
		}

		while(this.xRot - this.xRotO >= 180.0F) {
			this.xRotO += 360.0F;
		}

		while(this.yRot - this.yRotO < -180.0F) {
			this.yRotO -= 360.0F;
		}

		while(this.yRot - this.yRotO >= 180.0F) {
			this.yRotO += 360.0F;
		}
		if (this.ticksInAir == 400) {
			this.world.playSoundAtEntity(this, "doopmod.laser.hit", 1.0F, 1F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.remove();
		}

		this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
		this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;


		if (this.isInWater()) {
			for (int i1 = 0; i1 < 4; ++i1) {
				float f6 = 0.25F;
				this.world.spawnParticle("bubble", this.x - this.xd * (double)f6, this.y - this.yd * (double)f6, this.z - this.zd * (double)f6, this.xd, this.yd, this.zd);
			}
			if (this.ticksSoundDelay >= 3) {
				this.world.playSoundAtEntity(this, "doopmod.laser.sizzle", 0.3F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
				this.ticksSoundDelay = 0;
			}
		}

		calculateBounces();
		this.yd -= this.laserGravity;
		this.setPos(this.x, this.y, this.z);

		if (laserType == 0){
			this.world.spawnParticle("laserdust", this.x, this.y, this.z, 0.9, 0 , 0);
			if (this.removed) {
				createSphericalParticles(0.25, 8, 0.9, 0, 0);
			}
		}
	}

	//This voodoo witchcraft shit is essentially me pouring chemical X onto useless's code
	private void calculateBounces(){
		if (xTile == xTileOld && yTile == yTileOld && zTile == zTileOld) return; // Don't bounce if block hit is the same as the previous block
		int blockId = world.getBlockId(this.xTile, this.yTile, this.zTile);
		if (collisionExclusionList.contains(blockId)) {return;} // Don't collide with excluded blocks
		if (hitResult == null) return; // Don't bounce if ray-cast result is null
		Side sideHit = hitResult.side;

		// Calculate the exact position of the collision point
		double hitX = hitResult.location.xCoord;
		double hitY = hitResult.location.yCoord;
		double hitZ = hitResult.location.zCoord;


		double relX = hitX - xTile;
		double relY = hitY - yTile;
		double relZ = hitZ - zTile;


		double deltaX = xd;
		double deltaY = yd;
		double deltaZ = zd;


		double normalX = 0.0;
		double normalY = 0.0;
		double normalZ = 0.0;

		switch (sideHit) {
			case EAST:
				normalX = -1.0;
				break;
			case WEST:
				normalX = 1.0;
				break;
			case TOP:
				normalY = -1.0;
				break;
			case BOTTOM:
				normalY = 1.0;
				break;
			case NORTH:
				normalZ = 1.0;
				break;
			case SOUTH:
				normalZ = -1.0;
				break;
		}

		double dotProduct = deltaX * normalX + deltaY * normalY + deltaZ * normalZ;


		deltaX -= 2 * dotProduct * normalX;
		deltaY -= 2 * dotProduct * normalY;
		deltaZ -= 2 * dotProduct * normalZ;


		this.x = xTile + relX + deltaX * 0.05;
		this.y = yTile + relY + deltaY * 0.05;
		this.z = zTile + relZ + deltaZ * 0.05;

		//play stupid sound
		if (laserBounce > 0) { // If bounces available
			setLaserHeading(deltaX, deltaY, deltaZ, 1.5f, 1f);
			this.world.playSoundAtEntity(this, "doopmod.laser.bounce", 1.0F, 1F / (this.random.nextFloat() * 0.2F + 0.9F));
			laserBounce--;
		} else {
			this.world.playSoundAtEntity(this, "doopmod.laser.hit", 1.0F, 1F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.remove();
		}
	}

	//this is used to create particles in a radius around the removal/death point of the projectile
	public void createSphericalParticles(double radius, int numParticles, double red, double green, double blue) {
		for (int i = 0; i < numParticles; i++) {
			double theta = 2 * Math.PI * Math.random();
			double phi = Math.acos(2 * Math.random() - 1);
			double xP = this.x + radius * Math.sin(phi) * Math.cos(theta);
			double yP = this.y + radius * Math.sin(phi) * Math.sin(theta);
			double zP = this.z + radius * Math.cos(phi);
			this.world.spawnParticle("laserdust", xP, yP, zP, red, green, blue);
		}
	}


	public int getLaserType() {
		return this.laserType;
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		tag.putShort("xTile", (short)this.xTile);
		tag.putShort("yTile", (short)this.yTile);
		tag.putShort("zTile", (short)this.zTile);
		tag.putBoolean("player", this.doesLaserBelongToPlayer);
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		this.xTile = tag.getShort("xTile");
		this.yTile = tag.getShort("yTile");
		this.zTile = tag.getShort("zTile");
		this.doesLaserBelongToPlayer = tag.getBoolean("player");
	}

	public float getShadowHeightOffs() {
		return 0.0F;
	}
	public HitResult checkBlockCollisionBetweenPointsCustomBlacklist(Vec3d start, Vec3d end, boolean shouldCollideWithFluids, boolean flag1, List<Integer> collisionBlacklist) {
		HitResult movingobjectposition;
		if (Double.isNaN(start.xCoord) || Double.isNaN(start.yCoord) || Double.isNaN(start.zCoord)) {
			return null;
		}
		if (Double.isNaN(end.xCoord) || Double.isNaN(end.yCoord) || Double.isNaN(end.zCoord)) {
			return null;
		}
		int blockEndX = MathHelper.floor_double(end.xCoord);
		int blockEndY = MathHelper.floor_double(end.yCoord);
		int blockEndZ = MathHelper.floor_double(end.zCoord);
		int blockStartX = MathHelper.floor_double(start.xCoord);
		int blockStartY = MathHelper.floor_double(start.yCoord);
		int blockStartZ = MathHelper.floor_double(start.zCoord);
		int id = world.getBlockId(blockStartX, blockStartY, blockStartZ);
		int meta = world.getBlockMetadata(blockStartX, blockStartY, blockStartZ);
		Block block = Block.blocksList[id];
		if ((!flag1 || block == null || block.getCollisionBoundingBoxFromPool(world, blockStartX, blockStartY, blockStartZ) != null) && id > 0 && !collisionBlacklist.contains(block.id) && block.canCollideCheck(meta, shouldCollideWithFluids) && (movingobjectposition = block.collisionRayTrace(world, blockStartX, blockStartY, blockStartZ, start, end)) != null) {
			return movingobjectposition;
		}
		int l1 = 200;
		while (l1-- >= 0) {
			HitResult movingobjectposition1;
			if (Double.isNaN(start.xCoord) || Double.isNaN(start.yCoord) || Double.isNaN(start.zCoord)) {
				return null;
			}
			if (blockStartX == blockEndX && blockStartY == blockEndY && blockStartZ == blockEndZ) {
				return null;
			}
			boolean flag2 = true;
			boolean flag3 = true;
			boolean flag4 = true;
			double d = 999.0;
			double d1 = 999.0;
			double d2 = 999.0;
			if (blockEndX > blockStartX) {
				d = (double)blockStartX + 1.0;
			} else if (blockEndX < blockStartX) {
				d = (double)blockStartX + 0.0;
			} else {
				flag2 = false;
			}
			if (blockEndY > blockStartY) {
				d1 = (double)blockStartY + 1.0;
			} else if (blockEndY < blockStartY) {
				d1 = (double)blockStartY + 0.0;
			} else {
				flag3 = false;
			}
			if (blockEndZ > blockStartZ) {
				d2 = (double)blockStartZ + 1.0;
			} else if (blockEndZ < blockStartZ) {
				d2 = (double)blockStartZ + 0.0;
			} else {
				flag4 = false;
			}
			double d3 = 999.0;
			double d4 = 999.0;
			double d5 = 999.0;
			double d6 = end.xCoord - start.xCoord;
			double d7 = end.yCoord - start.yCoord;
			double d8 = end.zCoord - start.zCoord;
			if (flag2) {
				d3 = (d - start.xCoord) / d6;
			}
			if (flag3) {
				d4 = (d1 - start.yCoord) / d7;
			}
			if (flag4) {
				d5 = (d2 - start.zCoord) / d8;
			}
			int byte0 = 0;
			if (d3 < d4 && d3 < d5) {
				byte0 = blockEndX > blockStartX ? 4 : 5;
				start.xCoord = d;
				start.yCoord += d7 * d3;
				start.zCoord += d8 * d3;
			} else if (d4 < d5) {
				byte0 = blockEndY > blockStartY ? 0 : 1;
				start.xCoord += d6 * d4;
				start.yCoord = d1;
				start.zCoord += d8 * d4;
			} else {
				byte0 = blockEndZ > blockStartZ ? 2 : 3;
				start.xCoord += d6 * d5;
				start.yCoord += d7 * d5;
				start.zCoord = d2;
			}
			Vec3d vec3d2 = Vec3d.createVector(start.xCoord, start.yCoord, start.zCoord);
			vec3d2.xCoord = MathHelper.floor_double(start.xCoord);
			blockStartX = (int)vec3d2.xCoord;
			if (byte0 == 5) {
				--blockStartX;
				vec3d2.xCoord += 1.0;
			}
			vec3d2.yCoord = MathHelper.floor_double(start.yCoord);
			blockStartY = (int)vec3d2.yCoord;
			if (byte0 == 1) {
				--blockStartY;
				vec3d2.yCoord += 1.0;
			}
			vec3d2.zCoord = MathHelper.floor_double(start.zCoord);
			blockStartZ = (int)vec3d2.zCoord;
			if (byte0 == 3) {
				--blockStartZ;
				vec3d2.zCoord += 1.0;
			}
			int j2 = world.getBlockId(blockStartX, blockStartY, blockStartZ);
			int k2 = world.getBlockMetadata(blockStartX, blockStartY, blockStartZ);
			Block block1 = Block.blocksList[j2];
			if (flag1 && block1 != null && block1.getCollisionBoundingBoxFromPool(world, blockStartX, blockStartY, blockStartZ) == null || j2 <= 0 || (block!=null && !collisionBlacklist.contains(block.id)) || !block1.canCollideCheck(k2, shouldCollideWithFluids) || (movingobjectposition1 = block1.collisionRayTrace(world, blockStartX, blockStartY, blockStartZ, start, end)) == null) continue;
			return movingobjectposition1;
		}
		return null;
	}
}
