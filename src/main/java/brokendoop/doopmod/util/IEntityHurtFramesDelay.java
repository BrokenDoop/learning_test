package brokendoop.doopmod.util;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;

public interface IEntityHurtFramesDelay {
	default void markHurtDelay() {}
	default boolean hurtWithDelay(Entity entity, int damage1, DamageType type1, int damage2, DamageType type2, Boolean doHurtHeartsFlashTime, int isHFTDelay) {
		return false;
	}
}
