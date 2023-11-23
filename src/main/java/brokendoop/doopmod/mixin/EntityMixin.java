package brokendoop.doopmod.mixin;

import brokendoop.doopmod.util.IEntityHurtFramesDelay;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = Entity.class, remap = false)
public class EntityMixin implements IEntityHurtFramesDelay {
	@Unique
	public boolean hurtMarkedDelay = false;
	@Override
	public void markHurtDelay() {
		this.hurtMarkedDelay = true;
	}
	@Override
	public boolean hurtWithDelay(Entity entity, int damage1, DamageType type1, int damage2, DamageType type2, Boolean doHurtHeartsFlashTime, int isHFTDelay) {
		this.markHurtDelay();
		return false;
	}
}
