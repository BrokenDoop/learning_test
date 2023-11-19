package brokendoop.doopmod.util;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;

public interface IEntityHurtFramesDelay {
	default void markHurtDelay() {}
	default boolean hurtWithDelay(Entity attacker, int baseDamage, DamageType type, Boolean doHeartsFlashTime, int InvulnDelay) {
		return false;
	}
}
