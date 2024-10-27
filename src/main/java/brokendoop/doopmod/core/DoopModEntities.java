package brokendoop.doopmod.core;

import brokendoop.doopmod.DoopModConfig;
import brokendoop.doopmod.core.entity.EntityCrawlingZombie;
import brokendoop.doopmod.core.entity.EntityGeist;
import brokendoop.doopmod.core.entity.renderer.*;
import turniplabs.halplibe.helper.EntityHelper;

public class DoopModEntities {
	private static int startingID = DoopModConfig.cfg.getInt("IDs.startingEntityID");
	private static int nextID() {
		return ++startingID;
	}

	public static void initEntities() {
		EntityHelper.createEntity(EntityGeist.class, nextID(), "Geist", GeistRenderer::new);
		EntityHelper.createEntity(EntityCrawlingZombie.class, nextID(), "CrawlingZombie", CrawlingZombieRenderer::new);
	}
}
