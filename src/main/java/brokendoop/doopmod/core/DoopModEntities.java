package brokendoop.doopmod.core;

import brokendoop.doopmod.DoopModConfig;
import brokendoop.doopmod.core.entity.GeistEntity;
import brokendoop.doopmod.core.entity.renderer.*;
import turniplabs.halplibe.helper.EntityHelper;

public class DoopModEntities {
	private static int startingID = DoopModConfig.cfg.getInt("IDs.startingEntityID");
	private static int nextID() {
		return ++startingID;
	}

	public static void initEntities() {
		EntityHelper.createEntity(GeistEntity.class, nextID(), "Geist", GeistRenderer::new);
	}
}
