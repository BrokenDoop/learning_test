package brokendoop.doopmod.core.entity.renderer;

import brokendoop.doopmod.core.entity.EntityGeist;
import net.minecraft.client.render.entity.LivingRenderer;

public class GeistRenderer extends LivingRenderer<EntityGeist> {
	public GeistRenderer() {
		super(new ModelGeist(), 0.5f);
	}

	public void render(EntityGeist entityGeist, double x, double y, double z, float yaw, float partialTick) {
		ModelGeist modelGeist = (ModelGeist) mainModel;
		modelGeist.setLivingAnimations(entityGeist, entityGeist.limbSwing, entityGeist.limbYaw, partialTick);

		entityGeist.rightArm.rotationCurrentX = interpolateRotateAngle(entityGeist.rightArm.rotationCurrentX, entityGeist.rightArm.rotationTargetX, partialTick);
		entityGeist.rightArm.rotationCurrentY = interpolateRotateAngle(entityGeist.rightArm.rotationCurrentY, entityGeist.rightArm.rotationTargetY, partialTick);
		entityGeist.rightArm.rotationCurrentZ = interpolateRotateAngle(entityGeist.rightArm.rotationCurrentZ, entityGeist.rightArm.rotationTargetZ, partialTick);

		entityGeist.leftArm.rotationCurrentX = interpolateRotateAngle(entityGeist.leftArm.rotationCurrentX, entityGeist.leftArm.rotationTargetX, partialTick);
		entityGeist.leftArm.rotationCurrentY = interpolateRotateAngle(entityGeist.leftArm.rotationCurrentY, entityGeist.leftArm.rotationTargetY, partialTick);
		entityGeist.leftArm.rotationCurrentZ = interpolateRotateAngle(entityGeist.leftArm.rotationCurrentZ, entityGeist.leftArm.rotationTargetZ, partialTick);

		entityGeist.rightLeg.rotationCurrentX = interpolateRotateAngle(entityGeist.rightLeg.rotationCurrentX, entityGeist.rightLeg.rotationTargetX, partialTick);
		entityGeist.rightLeg.rotationCurrentY = interpolateRotateAngle(entityGeist.rightLeg.rotationCurrentY, entityGeist.rightLeg.rotationTargetY, partialTick);
		entityGeist.rightLeg.rotationCurrentZ = interpolateRotateAngle(entityGeist.rightLeg.rotationCurrentZ, entityGeist.rightLeg.rotationTargetZ, partialTick);

		entityGeist.leftLeg.rotationCurrentX = interpolateRotateAngle(entityGeist.leftLeg.rotationCurrentX, entityGeist.leftLeg.rotationTargetX, partialTick);
		entityGeist.leftLeg.rotationCurrentY = interpolateRotateAngle(entityGeist.leftLeg.rotationCurrentY, entityGeist.leftLeg.rotationTargetY, partialTick);
		entityGeist.leftLeg.rotationCurrentZ = interpolateRotateAngle(entityGeist.leftLeg.rotationCurrentZ, entityGeist.leftLeg.rotationTargetZ, partialTick);

		modelGeist.bipedRightArm.rotateAngleX = entityGeist.rightArm.rotationCurrentX;
		modelGeist.bipedRightArm.rotateAngleY = entityGeist.rightArm.rotationCurrentY;
		modelGeist.bipedRightArm.rotateAngleZ = entityGeist.rightArm.rotationCurrentZ;

		modelGeist.bipedLeftArm.rotateAngleX = entityGeist.leftArm.rotationCurrentX;
		modelGeist.bipedLeftArm.rotateAngleY = entityGeist.leftArm.rotationCurrentY;
		modelGeist.bipedLeftArm.rotateAngleZ = entityGeist.leftArm.rotationCurrentZ;

		modelGeist.bipedRightLeg.rotateAngleX = entityGeist.rightLeg.rotationCurrentX;
		modelGeist.bipedRightLeg.rotateAngleY = entityGeist.rightLeg.rotationCurrentY;
		modelGeist.bipedRightLeg.rotateAngleZ = entityGeist.rightLeg.rotationCurrentZ;

		modelGeist.bipedLeftLeg.rotateAngleX = entityGeist.leftLeg.rotationCurrentX;
		modelGeist.bipedLeftLeg.rotateAngleY = entityGeist.leftLeg.rotationCurrentY;
		modelGeist.bipedLeftLeg.rotateAngleZ = entityGeist.leftLeg.rotationCurrentZ;

		super.render(entityGeist, x, y, z, yaw, partialTick);

		float limbDrag = entityGeist.prevLimbDrag + (entityGeist.limbDrag - entityGeist.prevLimbDrag) * partialTick;
		if (limbDrag > 1.0F) {
			limbDrag = 1.0F;
		}
		entityGeist.setLimbDrag(limbDrag);
	}

	public float interpolateRotateAngle (float current, float target, float partialTick){
		float interpolatedAngle = current + (target - current) * partialTick * 0.1F;
		if (current > target && interpolatedAngle < target) {
			interpolatedAngle = target;
		} else if (current < target && interpolatedAngle > target) {
			interpolatedAngle = target;
		}
		return interpolatedAngle;
	}



}
