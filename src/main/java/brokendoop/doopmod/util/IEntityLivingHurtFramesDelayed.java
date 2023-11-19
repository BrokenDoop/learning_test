package brokendoop.doopmod.util;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;

public interface IEntityLivingHurtFramesDelayed {
	default boolean hurtWithDelay(Entity entity, int damage, DamageType type, Boolean doHeartsFlashTime, int InvulnDelay) {
		return false;
	}
}
