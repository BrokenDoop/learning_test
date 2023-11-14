package brokendoop.doopmod.entity.particle;

import net.minecraft.client.render.Tessellator;
import net.minecraft.core.entity.fx.EntityFX;
import net.minecraft.core.world.World;

public class EntityLaserdustFX extends EntityFX {
	float field_673_a;

	public EntityLaserdustFX(World world, double x, double y, double z, double red, double green, double blue) {
		this(world, x, y, z, 0, 0, 0, red, green, blue, 1.0F);
	}

	public EntityLaserdustFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ, double red, double green, double blue, float scale) {
		super(world, x, y, z, 0.0, 0.0, 0.0);
		this.xd *= 0.1;
		this.yd *= 0.1;
		this.zd *= 0.1;
		this.xd += motionX;
		this.yd += motionY;
		this.zd += motionZ;
		float f4 = (float)Math.random() * 0.4F + 0.6F;
		float f5 = (float)(Math.random() * 0.2) + 0.8F;
		this.particleRed = (float) (f5 * red * f4);
		this.particleGreen = (float) (f5 * green * f4);
		this.particleBlue = (float) (f5 * blue * f4);
		this.particleScale *= 0.75F;
		this.particleScale *= scale;
		this.field_673_a = this.particleScale;
		this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
		this.particleMaxAge = (int)((float)this.particleMaxAge * scale);
		this.noPhysics = false;
	}

	public void renderParticle(Tessellator t, float renderPartialTicks, float rotationX, float rotationXZ, float rotationZ, float rotationYZ, float rotationXY) {
		float f6 = ((float)this.particleAge + renderPartialTicks) / (float)this.particleMaxAge * 32.0F;
		if (f6 < 0.0F) {
			f6 = 0.0F;
		}

		if (f6 > 1.0F) {
			f6 = 1.0F;
		}

		this.particleScale = this.field_673_a * f6;
		float texMinU = (float)(this.particleTextureIndex % 16) / 16.0f;
		float texMaxU = texMinU + 0.0624375f;
		float texMinV = (float)(this.particleTextureIndex / 16) / 16.0f;
		float texMaxV = texMinV + 0.0624375f;
		float scale = 0.1f * this.particleScale;
		float posX = (float)(this.xo + (this.x - this.xo) * (double)renderPartialTicks - lerpPosX);
		float posY = (float)(this.yo + (this.y - this.yo) * (double)renderPartialTicks - lerpPosY);
		float posZ = (float)(this.zo + (this.z - this.zo) * (double)renderPartialTicks - lerpPosZ);
		float brightness = 1f;
		t.setColorOpaque_F(this.particleRed * brightness, this.particleGreen * brightness, this.particleBlue * brightness);
		t.addVertexWithUV(posX - rotationX * scale - rotationYZ * scale, posY - rotationXZ * scale, posZ - rotationZ * scale - rotationXY * scale, texMaxU, texMaxV);
		t.addVertexWithUV(posX - rotationX * scale + rotationYZ * scale, posY + rotationXZ * scale, posZ - rotationZ * scale + rotationXY * scale, texMaxU, texMinV);
		t.addVertexWithUV(posX + rotationX * scale + rotationYZ * scale, posY + rotationXZ * scale, posZ + rotationZ * scale + rotationXY * scale, texMinU, texMinV);
		t.addVertexWithUV(posX + rotationX * scale - rotationYZ * scale, posY - rotationXZ * scale, posZ + rotationZ * scale - rotationXY * scale, texMinU, texMaxV);
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.remove();
		}

		this.particleTextureIndex = 7 - this.particleAge * 8 / this.particleMaxAge;
		this.move(this.xd, this.yd, this.zd);
		if (this.y == this.yo) {
			this.xd *= 1.1;
			this.zd *= 1.1;
		}

		this.xd *= 0.9599999785423279;
		this.yd *= 0.9599999785423279;
		this.zd *= 0.9599999785423279;
		if (this.onGround) {
			this.xd *= 0.699999988079071;
			this.zd *= 0.699999988079071;
		}

	}
}
