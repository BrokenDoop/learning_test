package brokendoop.doopmod.entity;

import brokendoop.doopmod.entity.renderer.LaserRenderer;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ParticleHelper;

public class ModEntities {

	public static void register() {
		EntityHelper.createEntity(EntityLaser.class, new LaserRenderer(), 205, "laser");
		ParticleHelper.createParticle(EntityLaserdustFX.class, "laserdust");
	}

}
