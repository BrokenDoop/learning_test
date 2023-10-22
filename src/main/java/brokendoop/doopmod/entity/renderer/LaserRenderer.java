package brokendoop.doopmod.entity.renderer;

import brokendoop.doopmod.entity.projectile.EntityLaser;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import org.lwjgl.opengl.GL11;

public class LaserRenderer extends EntityRenderer<EntityLaser> {
	public LaserRenderer() {
	}

	public void renderLaser(EntityLaser Laser, double x, double y, double z, float yaw, float renderPartialTicks) {
		if (Laser.yRotO != 0.0F || Laser.xRotO != 0.0F) {
			this.loadTexture("assets/doopmod/entity/lasers.png");
			GL11.glEnable(3042);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0f);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glBlendFunc(770, 771);
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			GL11.glRotatef(Laser.yRotO + (Laser.yRot - Laser.yRotO) * renderPartialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(Laser.xRotO + (Laser.xRot - Laser.xRotO) * renderPartialTicks, 0.0F, 0.0F, 1.0F);
			Tessellator tessellator = Tessellator.instance;
			byte laserType;
			if (Laser.getLaserType() == 7) {
				laserType = 7;
			} else if (Laser.getLaserType() == 6) {
				laserType = 6;
			} else if (Laser.getLaserType() == 5) {
				laserType = 5;
			} else if (Laser.getLaserType() == 4) {
				laserType = 4;
			} else if (Laser.getLaserType() == 3) {
				laserType = 3;
			} else if (Laser.getLaserType() == 2) {
				laserType = 2;
			} else if (Laser.getLaserType() == 1) {
				laserType = 1;
			} else {
				laserType = 0;
			}

			float bodyMinU = 0.0F;
			float bodyMaxU = 0.8125F;
			float bodyMinV = (float)(laserType) / 16.0F;
			float bodyMaxV = (float)(1 + laserType) / 16.0F;
			float scale = 0.05625F;
			GL11.glEnable(32826);

			GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(-4.0F, 0.0F, 0.0F);

			for(int i = 0; i < 4; ++i) {
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glNormal3f(0.0F, 0.0F, scale);
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-6.5, -0.6, 0.0, bodyMinU, bodyMinV);
				tessellator.addVertexWithUV(6.5, -0.6, 0.0, bodyMaxU, bodyMinV);
				tessellator.addVertexWithUV(6.5, 0.6, 0.0, bodyMaxU, bodyMaxV);
				tessellator.addVertexWithUV(-6.5, 0.6, 0.0, bodyMinU, bodyMaxV);
				tessellator.draw();
			}

			GL11.glDisable(32826);
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}

	public void doRender(EntityLaser entity, double x, double y, double z, float yaw, float renderPartialTicks) {
		this.renderLaser(entity, x, y, z, yaw, renderPartialTicks);
	}


}







