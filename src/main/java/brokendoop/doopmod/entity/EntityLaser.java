package brokendoop.doopmod.entity;


import com.mojang.nbt.CompoundTag;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityLaser extends Entity {
//your shits all fucked, fix it, need to fix this entirely.
	protected int xTile;
	protected int yTile;
	protected int zTile;
	public boolean doesLaserBelongToPlayer;
	public EntityLiving owner;
	protected int ticksInAir;
	protected float laserSpeed;
	protected float laserBounce;
	protected float laserPierce;
	protected int laserSpread;
	protected float laserGravity;
	protected int laserDamage;
	protected int laserFireDamage;
	protected int laserType;
	private static final float PI = (float) Math.PI;

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
			this.x - (MathHelper.cos(this.yRot / 180.0F * PI) * 0.16F),
			this.y - 0.1,
			this.z - (MathHelper.sin(this.yRot / 180.0F * PI) * 0.16F));

		this.setLaserHeading(
			(-MathHelper.sin(this.yRot / 180.0F * PI) * MathHelper.cos(this.xRot / 180.0F * PI)),
			(-MathHelper.sin(this.xRot / 180.0F * PI)),
			(MathHelper.cos(this.yRot / 180.0F * PI) * MathHelper.cos(this.xRot / 180.0F * PI)),
			1.5F, 1.0F);
	}

	protected void init() {
		this.laserBounce = 0;
		this.laserPierce = 3;
		this.laserSpread = 1;
		this.laserSpeed = 1F;
		this.laserGravity = 0.03F;
		this.laserDamage = 2;
		this.laserFireDamage = 3;
		if (!(this.owner instanceof EntityPlayer)) {
			this.doesLaserBelongToPlayer = false;
		}
	}

	public void setLaserHeading(double deltaX, double deltaY, double deltaZ, float f, float f1) {
		float currentSpeed = MathHelper.sqrt_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		deltaX /= currentSpeed;
		deltaY /= currentSpeed;
		deltaZ /= currentSpeed;
		double i = 0.0075 * laserSpread;
		deltaX += this.random.nextGaussian() * i * (double)f1;
		deltaY += this.random.nextGaussian() * i * (double)f1;
		deltaZ += this.random.nextGaussian() * i * (double)f1;
		double speed = laserSpeed;
		deltaX *= speed;
		deltaY *= speed;
		deltaZ *= speed;
		this.xd = deltaX;
		this.yd = deltaY;
		this.zd = deltaZ;
		float currentSpeedHorizontally = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		this.yRotO = this.yRot = (float)(Math.atan2(deltaX, deltaZ) * 180.0 / Math.PI);
		this.xRotO = this.xRot = (float)(Math.atan2(deltaY, currentSpeedHorizontally) * 180.0 / Math.PI);
		this.ticksInAir = 0;
	}

	public void lerpMotion(double xd, double yd, double zd) {
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt_double(xd * xd + zd * zd);
			this.yRotO = this.yRot = (float)(Math.atan2(xd, zd) * 180.0 / Math.PI);
			this.xRotO = this.xRot = (float)(Math.atan2(yd, f) * 180.0 / Math.PI);
			this.moveTo(this.x, this.y, this.z, this.yRot, this.xRot);
		}

	}

	public void tick() {
		super.tick();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
			this.yRotO = this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);
			this.xRotO = this.xRot = (float)(Math.atan2(this.yd, f) * 180.0 / Math.PI);
		}

		int i = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
		if (i > 0) {
			this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.remove();
		} else {

			++this.ticksInAir;
			Vec3d oldPos = Vec3d.createVector(this.x, this.y, this.z);
			Vec3d newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
			HitResult movingObjectPosition = this.world.checkBlockCollisionBetweenPoints(oldPos, newPos, false, true);
			oldPos = Vec3d.createVector(this.x, this.y, this.z);
			newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
			if (movingObjectPosition != null) {
				newPos = Vec3d.createVector(movingObjectPosition.location.xCoord, movingObjectPosition.location.yCoord, movingObjectPosition.location.zCoord);
			}

			Entity entity = null;
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).expand(1.0, 1.0, 1.0));
			double d = 0.0;

			float f5;
			for (Entity listEntity : list) {
				if (listEntity.isPickable() && (listEntity != this.owner || this.ticksInAir >= 5)) {
					f5 = 0.3F;
					AABB axisAlignedBB1 = listEntity.bb.expand(f5, f5, f5);
					HitResult movingObjectPosition1 = axisAlignedBB1.func_1169_a(oldPos, newPos);
					if (movingObjectPosition1 != null) {
						double d1 = oldPos.distanceTo(movingObjectPosition1.location);
						if (d1 < d || d == 0.0) {
							entity = listEntity;
							d = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingObjectPosition = new HitResult(entity);
			}

			float f1;
			if (movingObjectPosition != null) {
				if (movingObjectPosition.entity != null) {
					if (movingObjectPosition.entity.hurt(this.owner, this.laserDamage, DamageType.COMBAT)) {
						if (entity instanceof EntityLiving) {
							entity.heartsFlashTime = 0;
						}
						movingObjectPosition.entity.hurt(this.owner, this.laserFireDamage, DamageType.FIRE);
						this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
							this.remove();
						}
				} else {
					this.xTile = movingObjectPosition.x;
					this.yTile = movingObjectPosition.y;
					this.zTile = movingObjectPosition.z;
					this.xd = (float)(movingObjectPosition.location.xCoord - this.x);
					this.yd = (float)(movingObjectPosition.location.yCoord - this.y);
					this.zd = (float)(movingObjectPosition.location.zCoord - this.z);
					f1 = MathHelper.sqrt_double(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
					this.x -= this.xd / (double)f1 * 0.05;
					this.y -= this.yd / (double)f1 * 0.05;
					this.z -= this.zd / (double)f1 * 0.05;
				}
			}

			this.x += this.xd;
			this.y += this.yd;
			this.z += this.zd;
			f1 = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
			this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);

			// This actually rotates the laser apparently
			this.xRot = (float)(Math.atan2(this.yd, f1) * 180.0 / Math.PI);
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

			this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
			this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;
			f5 = this.laserGravity;
			if (this.isInWater()) {
				for(int i1 = 0; i1 < 4; ++i1) {
					float f6 = 0.25F;
					this.world.spawnParticle("bubble", this.x - this.xd * (double)f6, this.y - this.yd * (double)f6, this.z - this.zd * (double)f6, this.xd, this.yd, this.zd);
				}
			}

			this.yd -= f5;
			this.setPos(this.x, this.y, this.z);
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
