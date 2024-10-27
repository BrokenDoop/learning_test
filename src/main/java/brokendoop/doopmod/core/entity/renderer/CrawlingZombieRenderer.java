package brokendoop.doopmod.core.entity.renderer;

import brokendoop.doopmod.core.entity.EntityCrawlingZombie;
import net.minecraft.client.render.entity.LivingRenderer;

public class CrawlingZombieRenderer extends LivingRenderer<EntityCrawlingZombie> {
	public CrawlingZombieRenderer() {
		super(new ModelCrawlingZombie(), 0.6F);
	}
}
