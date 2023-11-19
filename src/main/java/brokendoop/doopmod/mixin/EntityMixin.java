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
	public boolean hurtWithDelay(Entity attacker, int baseDamage, DamageType type, Boolean doHeartsFlashTime, int InvulnDelay) {
		this.markHurtDelay();
		return false;
	}
}
