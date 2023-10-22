package brokendoop.doopmod.entity;


import com.mojang.nbt.CompoundTag;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

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
	protected double laserSpeed;
	protected float laserBounce;
	protected float laserPierce;
	protected int laserSpread;
	protected float laserGravity;
	protected int laserDamage;
	protected int laserFireDamage;
	protected int laserType;
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

		this.setLaserHeading(
			(-MathHelper.sin((float)Math.toRadians(this.yRot)) * MathHelper.cos((float)Math.toRadians(this.xRot))),
			(-MathHelper.sin((float)Math.toRadians(this.xRot))),
			(MathHelper.cos((float)Math.toRadians(this.yRot)) * MathHelper.cos((float)Math.toRadians(this.xRot))),
			1.5F, 1.0F);
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
		++this.ticksInAir;
		Vec3d oldPos = Vec3d.createVector(this.x, this.y, this.z);
		Vec3d newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		hitResult = this.world.checkBlockCollisionBetweenPoints(oldPos, newPos, false, true);
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
						//laser pierce sound goes here I think
						laserPierce--;
					} else {
						this.remove();
						this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
					}
				}
			} else {
				this.xTile = hitResult.x;
				this.yTile = hitResult.y;
				this.zTile = hitResult.z;

				// Slows down arrow on collision
				/*this.xd = (float)(movingObjectPosition.location.xCoord - this.x);
				this.yd = (float)(movingObjectPosition.location.yCoord - this.y);
				this.zd = (float)(movingObjectPosition.location.zCoord - this.z);*/

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
			this.remove();
		}

		this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
		this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;

		if (laserType == 0){
			this.world.spawnParticle("laserdust", this.x, this.y, this.z, 0.9, 0 , 0);
		}

		if (this.isInWater()) {
			for(int i1 = 0; i1 < 4; ++i1) {
				float f6 = 0.25F;
				this.world.spawnParticle("bubble", this.x - this.xd * (double)f6, this.y - this.yd * (double)f6, this.z - this.zd * (double)f6, this.xd, this.yd, this.zd);
			}
		}
		calculateBounces();
		this.yd -= this.laserGravity;
		this.setPos(this.x, this.y, this.z);

	}
	//This voodoo witchcraft shit is essentially me pouring chemical X onto useless's code
	private void calculateBounces(){
		if (xTile == xTileOld && yTile == yTileOld && zTile == zTileOld) return; // Don't bounce if block hit is the same as the previous block
		if (world.getBlockId(this.xTile, this.yTile, this.zTile) == 0) return; // Don't bounce if hitting air
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
		this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
		if (laserBounce > 0) { // If bounces available
			setLaserHeading(deltaX, deltaY, deltaZ, 1.5f, 1f);
			laserBounce--;
		} else {
			this.remove();
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
}
