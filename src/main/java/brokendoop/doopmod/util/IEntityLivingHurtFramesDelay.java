package brokendoop.doopmod.util;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;

public interface IEntityLivingHurtFramesDelay {
	default boolean hurtWithDelay_2(Entity entity, int damage, DamageType type, Boolean doHurtHeartsFlashTime, int isHFTDelay){
		return false;
	}
}
