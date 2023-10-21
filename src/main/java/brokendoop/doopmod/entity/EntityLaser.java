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

	public EntityLaser(World world) {
		this(world, 0);
	}

	public EntityLaser(World world, int laserType) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.doesLaserBelongToPlayer = false;
		this.laserType = laserType;
		this.ticksInAir = 0;
		this.fireImmune = true;
		this.setSize(0.5F, 0.5F);
	}

	public EntityLaser(World world, double d, double d1, double d2, int laserType) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.doesLaserBelongToPlayer = false;
		this.laserType = laserType;
		this.ticksInAir = 0;
		this.fireImmune = true;
		this.setSize(0.5F, 0.5F);
		this.setPos(d, d1, d2);
		this.heightOffset = 0.0F;
	}

	public EntityLaser(World world, EntityLiving entityliving, boolean doesLaserBelongToPlayer, int laserType) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.doesLaserBelongToPlayer = doesLaserBelongToPlayer;
		this.laserType = laserType;
		this.ticksInAir = 0;
		this.fireImmune = true;
		this.owner = entityliving;
		this.setSize(0.5F, 0.5F);
		this.moveTo(entityliving.x, entityliving.y + (double)entityliving.getHeadHeight(), entityliving.z, entityliving.yRot, entityliving.xRot);
		this.x -= MathHelper.cos(this.yRot / 180.0F * 3.141593F) * 0.16F;
		this.y -= 0.1;
		this.z -= MathHelper.sin(this.yRot / 180.0F * 3.141593F) * 0.16F;
		this.setPos(this.x, this.y, this.z);
		this.heightOffset = 0.0F;
		this.xd = -MathHelper.sin(this.yRot / 180.0F * 3.141593F) * MathHelper.cos(this.xRot / 180.0F * 3.141593F);
		this.zd = MathHelper.cos(this.yRot / 180.0F * 3.141593F) * MathHelper.cos(this.xRot / 180.0F * 3.141593F);
		this.yd = -MathHelper.sin(this.xRot / 180.0F * 3.141593F);
		this.setLaserHeading(this.xd, this.yd, this.zd, 1.5F, 1.0F);
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

	public void setLaserHeading(double d, double d1, double d2, float f, float f1) {
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		double i = 0.0075 * laserSpread;
		d += this.random.nextGaussian() * i * (double)f1;
		d1 += this.random.nextGaussian() * i * (double)f1;
		d2 += this.random.nextGaussian() * i * (double)f1;
		double speed = laserSpeed;
		d *= speed;
		d1 *= speed;
		d2 *= speed;
		this.xd = d;
		this.yd = d1;
		this.zd = d2;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		this.yRotO = this.yRot = (float)(Math.atan2(d, d2) * 180.0 / Math.PI);
		this.xRotO = this.xRot = (float)(Math.atan2(d1, f3) * 180.0 / Math.PI);
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
			this.xRotO = this.xRot;
			this.yRotO = this.yRot;
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
			HitResult movingobjectposition = this.world.checkBlockCollisionBetweenPoints(oldPos, newPos, false, true);
			oldPos = Vec3d.createVector(this.x, this.y, this.z);
			newPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd, this.z + this.zd);
			if (movingobjectposition != null) {
				newPos = Vec3d.createVector(movingobjectposition.location.xCoord, movingobjectposition.location.yCoord, movingobjectposition.location.zCoord);
			}

			Entity entity = null;
			List list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.addCoord(this.xd, this.yd, this.zd).expand(1.0, 1.0, 1.0));
			double d = 0.0;

			float f5;
			for(int l = 0; l < list.size(); ++l) {
				Entity entity1 = (Entity)list.get(l);
				if (entity1.isPickable() && (entity1 != this.owner || this.ticksInAir >= 5)) {
					f5 = 0.3F;
					AABB axisalignedbb1 = entity1.bb.expand(f5, f5, f5);
					HitResult movingobjectposition1 = axisalignedbb1.func_1169_a(oldPos, newPos);
					if (movingobjectposition1 != null) {
						double d1 = oldPos.distanceTo(movingobjectposition1.location);
						if (d1 < d || d == 0.0) {
							entity = entity1;
							d = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new HitResult(entity);
			}

			float f1;
			if (movingobjectposition != null) {
				if (movingobjectposition.entity != null) {
					if (movingobjectposition.entity.hurt(this.owner, this.laserDamage, DamageType.COMBAT)) {
						if (entity instanceof EntityLiving) {
							entity.heartsFlashTime = 0;
						}
						movingobjectposition.entity.hurt(this.owner, this.laserFireDamage, DamageType.FIRE);
						this.world.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
							this.remove();
						}
				} else {
					this.xTile = movingobjectposition.x;
					this.yTile = movingobjectposition.y;
					this.zTile = movingobjectposition.z;
					this.xd = (float)(movingobjectposition.location.xCoord - this.x);
					this.yd = (float)(movingobjectposition.location.yCoord - this.y);
					this.zd = (float)(movingobjectposition.location.zCoord - this.z);
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

			for(this.xRot = (float)(Math.atan2(this.yd, f1) * 180.0 / Math.PI); this.xRot - this.xRotO < -180.0F; this.xRotO -= 360.0F) {
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
			float f3 = this.laserSpeed;
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
