package brokendoop.doopmod.core.entity.renderer;

import brokendoop.doopmod.core.entity.EntityGeist;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;


public class ModelGeist extends ModelBiped {

	public Cube tentacleLeft;
	public Cube tentacleRight;
	public Cube tentacleMid;
	public EntityGeist entityGeist;
	public float limbPitch;
	public ModelGeist() {
		this.parts();
	}
	public void parts() {
		this.field_1279_h = false;
		this.field_1278_i = false;
		this.isSneak = false;
		this.isRiding = false;
		this.bipedRightArm = new Cube(40, 16);
		this.bipedRightArm.addBox(-2F, -2.0F, -1.5F, 3, 12, 3);
		this.bipedRightArm.setRotationPoint(-5F, 2.0F, 0.0F);
		this.bipedLeftArm = new Cube(40, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1F, -2.0F, -1.5F, 3, 12, 3);
		this.bipedLeftArm.setRotationPoint(5F, 2.0F, 0.0F);
		this.bipedRightLeg = new Cube(0, 16);
		this.bipedRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3);
		this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		this.bipedLeftLeg = new Cube(0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3);
		this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		this.tentacleLeft = new Cube(52, 16);
		this.tentacleLeft.addBox(-1.0F, -0.75F, -1.0F, 2, 12, 2);
		this.tentacleLeft.setRotationPoint(2.0F, 2.5F, 2.0F);
		this.tentacleRight = new Cube(52, 16);
		this.tentacleRight.mirror = true;
		this.tentacleRight.addBox(-1.0F, -0.5F, -1.0F, 2, 12, 2);
		this.tentacleRight.setRotationPoint(-2.0F, 4.5F, 2.0F);
		this.tentacleMid = new Cube(52, 16);
		this.tentacleMid.addBox(-1.0F, -1.0F, -1.0F, 2, 12, 2);
		this.tentacleMid.setRotationPoint(0.0F, 8.5F, 2.0F);
	}



	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
		this.entityGeist = (EntityGeist) entityliving;
		float limbDrag = this.entityGeist.getLimbDrag();

		this.entityGeist.rightArm.rotationTargetX = (float) Math.PI / 8;
		this.entityGeist.rightArm.rotationTargetY = 0;
		this.entityGeist.rightArm.rotationTargetZ = 0;

		this.entityGeist.leftArm.rotationTargetX = (float) Math.PI / 8;
		this.entityGeist.leftArm.rotationTargetY = 0;
		this.entityGeist.leftArm.rotationTargetZ = 0;

		this.entityGeist.rightLeg.rotationTargetX = (float) Math.PI / 8;
		this.entityGeist.rightLeg.rotationTargetY = 0;
		this.entityGeist.rightLeg.rotationTargetZ = 0;

		this.entityGeist.leftLeg.rotationTargetX = (float) Math.PI / 8;
		this.entityGeist.leftLeg.rotationTargetY = 0;
		this.entityGeist.leftLeg.rotationTargetZ = 0;


		float legOscillationX = MathHelper.cos(limbPitch * 0.09F + 0.5F) * 0.08F + 0.05F;
		float legOscillationZ = MathHelper.sin(limbPitch * 0.067F + 0.5F) * 0.06F + 0.03F;
		float armOscillationX = MathHelper.sin(limbPitch * 0.09F) * 0.08F;
		float armOscillationZ = MathHelper.cos(limbPitch * 0.067F) * 0.06F + 0.05F;
		float legDragAmp = 0.8F;




		if (this.entityGeist.isGeistShy()) {
			this.entityGeist.rightLeg.rotationTargetX = -(float) Math.PI / 4;
			this.entityGeist.rightLeg.rotationTargetY = (float) Math.PI / 14;
			this.entityGeist.leftLeg.rotationTargetX = -(float) Math.PI / 4;
			this.entityGeist.leftLeg.rotationTargetY  = -(float) Math.PI / 14;
			//arm rotations are handled in setRotationAngles when covering the face to make them instant. This is here so it can interpolate from the pos
			this.entityGeist.rightArm.rotationCurrentX = -(float) Math.PI + ((float) Math.PI / 3) - (this.bipedHead.rotateAngleY / 3.75F);
			this.entityGeist.rightArm.rotationCurrentY = this.bipedHead.rotateAngleY / 2 - ((float) Math.PI / 8);
			this.entityGeist.leftArm.rotationCurrentX = -(float) Math.PI + ((float) Math.PI / 3) + (this.bipedHead.rotateAngleY / 3.75F);
			this.entityGeist.leftArm.rotationCurrentY = this.bipedHead.rotateAngleY / 2 + ((float) Math.PI / 8);
			armOscillationX = 0;
			armOscillationZ = 0;
			if (this.entityGeist.isGeistTooClose()) {
				this.entityGeist.rightLeg.rotationTargetX = -(float) Math.PI / 3;
				this.entityGeist.rightLeg.rotationTargetY = this.bipedHead.rotateAngleY / 2 + (float) Math.PI / 14;
				this.entityGeist.leftLeg.rotationTargetX = -(float) Math.PI / 3;
				this.entityGeist.leftLeg.rotationTargetY = this.bipedHead.rotateAngleY / 2 - (float) Math.PI / 14;
				legOscillationX = MathHelper.cos(limbPitch * 0.37F + 0.5F) * 0.3F;
				legOscillationZ = MathHelper.sin(limbPitch * 0.37F + 0.5F) * 0.06F;
			}
		} else {
			this.entityGeist.rightArm.rotationTargetX += limbDrag * 0.9F;
			this.entityGeist.leftArm.rotationTargetX += limbDrag * 0.9F;
			if (this.entityGeist.isGeistAngry()) {
				armOscillationX = MathHelper.sin(limbPitch * 0.067F) * 0.05F;
				armOscillationZ = MathHelper.cos(limbPitch * 0.09F) * 0.05F + 0.05F;
				legDragAmp = 0.4F;
				this.entityGeist.rightLeg.rotationTargetX = (float) Math.PI / 10;
				this.entityGeist.leftLeg.rotationTargetX = (float) Math.PI / 10;
				this.entityGeist.rightArm.rotationTargetX = -(float) Math.PI / 2;
				this.entityGeist.leftArm.rotationTargetX = -(float) Math.PI / 2;
				if (this.entityGeist.isGeistTooClose()) {
					armOscillationX = MathHelper.sin(limbPitch * 0.37F + (float)Math.PI) * 0.5F;
					armOscillationZ = MathHelper.cos(limbPitch * 0.37F) * 0.5F;
				}
			}
		}
		this.entityGeist.rightLeg.rotationTargetX += limbDrag * legDragAmp;
		this.entityGeist.leftLeg.rotationTargetX += limbDrag * legDragAmp;
		//sway/oscillation
		this.entityGeist.rightArm.rotationTargetZ += armOscillationZ;
		this.entityGeist.rightArm.rotationTargetX += armOscillationX;
		this.entityGeist.leftArm.rotationTargetZ -= armOscillationZ;
		this.entityGeist.leftArm.rotationTargetX -= armOscillationX;

		this.entityGeist.rightLeg.rotationTargetX += legOscillationX;
		this.entityGeist.rightLeg.rotationTargetZ += legOscillationZ;
		this.entityGeist.leftLeg.rotationTargetX -= legOscillationX;
		this.entityGeist.leftLeg.rotationTargetZ -= legOscillationZ;
	}

	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.limbPitch = limbPitch;
		this.bipedHead.rotateAngleY = headYaw * (float) Math.PI / 180F;
		this.bipedHead.rotateAngleX = headPitch * (float) Math.PI / 180F;
		this.bipedHeadOverlay.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadOverlay.rotateAngleX = this.bipedHead.rotateAngleX;

		//tentacle sway
		this.tentacleLeft.rotateAngleX = 0.4F * MathHelper.sin(limbPitch * 0.2F + 0) + (float) Math.PI / 2;
		this.tentacleRight.rotateAngleX = 0.4F * MathHelper.sin(limbPitch * 0.2F + 1) + (float) Math.PI / 2;
		this.tentacleMid.rotateAngleX = 0.4F * MathHelper.sin(limbPitch * 0.2F + 2) + (float) Math.PI / 2;

		if (this.entityGeist.isGeistShy()){
			this.bipedHead.rotateAngleY /= 1.25F;
			this.bipedHead.rotateAngleX = (float) Math.PI / 12;
			this.bipedRightArm.rotateAngleX = -(float) Math.PI + ((float) Math.PI / 3) - (this.bipedHead.rotateAngleY / 3.75F);
			this.bipedRightArm.rotateAngleY = this.bipedHead.rotateAngleY / 2 - ((float) Math.PI / 8);
			this.bipedLeftArm.rotateAngleX = -(float) Math.PI + ((float) Math.PI / 3) + (this.bipedHead.rotateAngleY / 3.75F);
			this.bipedLeftArm.rotateAngleY = this.bipedHead.rotateAngleY / 2 + ((float) Math.PI / 8);
		}


	}


	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		this.bipedHead.renderWithRotation(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
		this.bipedHeadOverlay.render(scale);
		this.tentacleLeft.render(scale);
		this.tentacleRight.render(scale);
		this.tentacleMid.render(scale);
	}
}
