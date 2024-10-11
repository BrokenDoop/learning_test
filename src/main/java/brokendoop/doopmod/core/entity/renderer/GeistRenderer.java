package brokendoop.doopmod.core.entity.renderer;

import brokendoop.doopmod.core.entity.GeistEntity;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelZombie;

public class GeistRenderer extends LivingRenderer<GeistEntity> {
	public GeistRenderer() {
		super(new ModelZombie(), 0.25f);

	}
}
