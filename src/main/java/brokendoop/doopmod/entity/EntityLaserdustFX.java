package brokendoop.doopmod.entity;

import net.minecraft.client.render.Tessellator;
import net.minecraft.core.entity.fx.EntityFX;
import net.minecraft.core.world.World;

public class EntityLaserdustFX extends EntityFX {
	float field_673_a;

	public EntityLaserdustFX(World world, double d, double d1, double d2, float f, float f1, float f2) {
		this(world, d, d1, d2, 1.0F, f, f1, f2);
	}

	public EntityLaserdustFX(World world, double d, double d1, double d2, float f, float red, float green, float blue) {
		super(world, d, d1, d2, 0.0, 0.0, 0.0);
		this.xd *= 0.10000000149011612;
		this.yd *= 0.10000000149011612;
		this.zd *= 0.10000000149011612;
		float f4 = (float)Math.random() * 0.4F + 0.6F;
		this.particleRed = ((float)(Math.random() * 0.20000000298023224) + 0.8F) * red * f4;
		this.particleGreen = ((float)(Math.random() * 0.20000000298023224) + 0.8F) * green * f4;
		this.particleBlue = ((float)(Math.random() * 0.20000000298023224) + 0.8F) * blue * f4;
		this.particleScale *= 0.75F;
		this.particleScale *= f;
		this.field_673_a = this.particleScale;
		this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
		this.particleMaxAge = (int)((float)this.particleMaxAge * f);
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
		super.renderParticle(t, renderPartialTicks, rotationX, rotationXZ, rotationZ, rotationYZ, rotationXY);
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
