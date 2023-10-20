package brokendoop.doopmod.entity;

import brokendoop.doopmod.entity.renderer.LaserRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import turniplabs.halplibe.helper.EntityHelper;

public class ModEntities {

	public static void register() {
		EntityHelper.createEntity(EntityLaser.class, new LaserRenderer() , 200, "laser");
	}
}
