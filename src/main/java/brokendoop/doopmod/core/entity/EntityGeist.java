package brokendoop.doopmod.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityGeist extends EntityMonster {

	protected float prevMoveSpeed;
	public float prevLimbDrag;
	public float limbDrag;
	public float setLimbDrag;
	public float angerBuffer = 0;
	public float seenBuffer = 0;
	public static class Limb {
		public float rotationTargetX = (float) Math.PI / 8;
		public float rotationTargetY;
		public float rotationTargetZ;
		public float rotationCurrentX = (float) Math.PI / 8;
		public float rotationCurrentY;
		public float rotationCurrentZ;
	}
	public Limb rightArm = new Limb();
	public Limb leftArm = new Limb();
	public Limb rightLeg = new Limb();
	public Limb leftLeg = new Limb();

	public EntityGeist(World world) {
		super(world);
		this.moveSpeed = 0.5F;
		this.prevMoveSpeed = this.moveSpeed;
		this.attackStrength = 5;

	}
	protected void init() {
		super.init();
		this.entityData.define(16, (byte) 0);
	}

	protected boolean makeStepSound() {
		return false;
	}

	public boolean canRide() {
		return false;
	}

	public String getEntityTexture() { //skin variants don't work rn, fix that shit later
		if (this.isGeistAngry()) {
			return this.isGeistAngry() ? "/assets/doopmod/textures/entity/geist_angry/" + this.getSkinVariant() + ".png" : super.getEntityTexture();
		} else if (this.isGeistShy()) {
			return this.isGeistShy() ? "/assets/doopmod/textures/entity/geist_shy/" + this.getSkinVariant() + ".png" : super.getEntityTexture();
		} else
			return "/assets/doopmod/textures/entity/geist/" + this.getSkinVariant() + ".png";
	}
	public String getDefaultEntityTexture() {
		if (this.isGeistAngry()) {
			return this.isGeistAngry() ? "/assets/doopmod/textures/entity/geist_angry/0.png" : super.getEntityTexture();
		} else if (this.isGeistShy()) {
			return this.isGeistShy() ? "/assets/doopmod/textures/entity/geist_shy/0.png" : super.getEntityTexture();
		} else
			return "/assets/doopmod/textures/entity/geist/0.png";
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Angry", this.isGeistAngry());
		tag.putBoolean("Shy", this.isGeistShy());
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setGeistAngry(tag.getBoolean("Angry"));
		this.setGeistShy(tag.getBoolean("Shy"));
	}

	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		super.moveEntityWithHeading(moveStrafing, moveForward);



		//logic for limbs dragging
		this.prevLimbDrag = this.limbDrag;
		double deltaX = this.x - this.xo;
		double deltaZ = this.z - this.zo;
		float distanceMoved = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ) * 4.0F;
		if (distanceMoved > 1.0F) {
			distanceMoved = 1.0F;
		}

		double forwardX = -Math.sin(Math.toRadians(this.yRot));
		double forwardZ = Math.cos(Math.toRadians(this.yRot));
		double forwardMovement = this.xd * forwardX + this.zd * forwardZ;
		if(forwardMovement >= 0) {
			this.limbDrag += (distanceMoved - this.limbDrag) * 0.4F;
		} else if (forwardMovement < 0){
			this.limbDrag += (-distanceMoved - this.limbDrag) * 0.4F;
		}

	}

	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		this.angerBuffer--;
		this.seenBuffer--;
		boolean isSeen = this.isSeenByPlayer(getClosestPlayerToEntityLooking());


		//basic implementation - going to redo it for readability (and hopefully fix a bug when the Geist gets out of distance/'the world is reloaded' and doesn't reset it's state)
		// if there is a target and its within the "sight radius" (16)
		if (this.entityToAttack != null) {
			if (isSeen && !(this.angerBuffer >= 0) || this.seenBuffer >= 0) { // set shy
				this.setGeistAngry(false);
				this.setGeistShy(true);

				if (this.entityToAttack.distanceTo(this) < 12 && !this.isGeistTooClose()) { // if geist is within a range it will stop moving, too close and it moves backwards.
					this.moveSpeed = incrementMoveSpeed(0, 0.1F); // scale speed to 0
				} else if (this.isGeistTooClose()){
					this.moveSpeed = incrementMoveSpeed(-this.prevMoveSpeed * 0.25F, 0.1F);
				} else {// set move speed when out of radius
					this.moveSpeed = incrementMoveSpeed(this.prevMoveSpeed, 0.05F);
				}





				if (isSeen) {
					this.seenBuffer = 10; //set the buffer, so it stays "shy" for a moment after looking away
				}

			} else { // set angry
				this.setGeistShy(false);
				this.setGeistAngry(true);

				if (!this.isGeistTooClose() || this.angerBuffer >= 0) { // increase chase speed if geist is far or hit
					this.moveSpeed = incrementMoveSpeed(this.prevMoveSpeed * 2.2F, 0.05F); // scale speed to 2.2x

				} else { // scale move speed to 2.2X when geist is close.
					this.moveSpeed = incrementMoveSpeed(this.prevMoveSpeed * 2.0F, 0.05F); //move speed is capped or something, need to fix.

				}
			}
		} else { // set back to no-target (unaware)
			this.setGeistShy(false);
			this.setGeistAngry(false);
			this.moveSpeed = incrementMoveSpeed(this.prevMoveSpeed, 0.05F);
		}

	}

	public float incrementMoveSpeed(float targetMoveSpeed, float adjustmentPerTick) {
		if (this.moveSpeed < targetMoveSpeed) {
			this.moveSpeed += adjustmentPerTick;
			if (this.moveSpeed > targetMoveSpeed) {
				this.moveSpeed = targetMoveSpeed;
			}
		} else if (this.moveSpeed > targetMoveSpeed) {
			this.moveSpeed -= adjustmentPerTick;
			if (this.moveSpeed < targetMoveSpeed) {
				this.moveSpeed = targetMoveSpeed;
			}
		}
	return this.moveSpeed;
	}


	protected Entity findPlayerToAttack() {
		EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 6.0);
		return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
	}

	public boolean hurt(Entity attacker, int i, DamageType type) {
		if (super.hurt(attacker, i, type)) {
			if (this.passenger != attacker && this.vehicle != attacker) {
				if (attacker != this) {
					this.seenBuffer = 0;
					this.angerBuffer = 80;
				}
            }
            return true;
        } else {
			return false;
		}
	}

	protected void attackEntity(Entity entity, float distance) {
		if (!(this.isGeistShy()) && this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
			this.attackTime = 20;
			entity.hurt(this, this.attackStrength, DamageType.COMBAT);
		}

	}
	public void setLimbDrag(float limbDrag){
		this.setLimbDrag = limbDrag;
	}
	public float getLimbDrag(){
		return this.setLimbDrag;
	}

	public EntityPlayer getClosestPlayerToEntityLooking(){
		EntityPlayer closestPlayerLooking = this.world.getClosestPlayerToEntity(this, 24);
		if (closestPlayerLooking != null) {
			ItemStack pumpkinHead = closestPlayerLooking.inventory.armorItemInSlot(3);

			if (pumpkinHead != null && pumpkinHead.getItem() == Block.pumpkinCarvedIdle.asItem()) {
				return null;
			} else if (isSeenByPlayer(closestPlayerLooking)) {
				return closestPlayerLooking;
			}
		}
		return null;
	}

	public boolean isGeistTooClose() {
		if (this.entityToAttack != null) {
			return this.entityToAttack.distanceTo(this) < 4;
		} else
			return false;
	}

	public boolean isSeenByPlayer(EntityPlayer player){
		if (player != null) {

			Vec3d playerLook = player.getLookAngle().normalize();
			Vec3d toEntity = Vec3d.createVector(this.x - player.x,(this.y + (double) this.getHeadHeight()) - (player.y + (double) player.getHeadHeight()),this.z - player.z);
			double distance = toEntity.lengthVector();
			toEntity = toEntity.normalize();
			double dotProduct = (playerLook.xCoord * toEntity.xCoord) + (playerLook.yCoord * toEntity.yCoord) + (playerLook.zCoord * toEntity.zCoord);


			Vec3d playerPos = Vec3d.createVector(player.x, player.y + (double) player.getHeadHeight(), player.z);
			Vec3d thisPos = Vec3d.createVector(this.x, this.y + (double) this.getHeadHeight(), this.z);
			HitResult hitResult = this.world.checkBlockCollisionBetweenPoints(playerPos, thisPos, false, true);

			if (hitResult == null) {
				return dotProduct > 0.85D - (0.005D * distance) && player.canEntityBeSeen(this);
			} else
				return false;
		}
        return false;
    }


	public boolean isGeistShy() {
		return (this.entityData.getByte(16) & 1) != 0;
	}

	public void setGeistShy(boolean flag) {
		byte byte0 = this.entityData.getByte(16);
		if (flag) {
			this.entityData.set(16, (byte)(byte0 | 1));
		} else {
			this.entityData.set(16, (byte)(byte0 & -2));
		}
	}

	public boolean isGeistAngry() {
		return (this.entityData.getByte(16) & 2) != 0;
	}

	public void setGeistAngry(boolean flag) {
		byte byte0 = this.entityData.getByte(16);
		if (flag) {
			this.entityData.set(16, (byte)(byte0 | 2));
		} else {
			this.entityData.set(16, (byte)(byte0 & -3));
		}
	}



}
