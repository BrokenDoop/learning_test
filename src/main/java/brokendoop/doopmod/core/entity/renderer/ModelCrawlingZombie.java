package brokendoop.doopmod.core.entity.renderer;

import brokendoop.doopmod.core.entity.EntityCrawlingZombie;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelZombie;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;

public class ModelCrawlingZombie extends ModelZombie {
	public EntityCrawlingZombie crawlingZombie;
	public ModelCrawlingZombie() {
		this.parts();
	}
	public void parts() {
		this.field_1279_h = false;
		this.field_1278_i = false;
		this.isSneak = false;
		this.bipedHead = new Cube(0, 0);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0.0F, 22.0F, -2.0F);
		this.bipedBody = new Cube(16, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4);
		this.bipedBody.setRotationPoint(0.0F, 22.0F, 0.0F);
		this.bipedRightArm = new Cube(40, 16);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
		this.bipedRightArm.setRotationPoint(-5.0F, 22.0F, 0.0F);
		this.bipedLeftArm = new Cube(40, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
		this.bipedLeftArm.setRotationPoint(5.0F, 22.0F, 0.0F);
	}
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
		this.crawlingZombie = (EntityCrawlingZombie) entityliving;

	}

	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		this.bipedBody.rotateAngleX = (float) Math.PI / 2;
		this.bipedHead.rotationPointY = 22.0F;
		this.bipedRightArm.rotateAngleX = -(float) Math.PI / 2 + MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbYaw * 0.2F;
		this.bipedLeftArm.rotateAngleX = -(float) Math.PI / 2 + MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbYaw * 0.2F;
		this.bipedBody.rotateAngleY =  MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbYaw * 0.11F;
		this.bipedRightArm.rotateAngleY = (float) Math.PI / 20;
		this.bipedLeftArm.rotateAngleY = -(float) Math.PI / 20;

	}
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		this.bipedHead.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedHeadOverlay.render(scale);
	}

}
