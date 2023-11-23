package brokendoop.doopmod.mixin;

import brokendoop.doopmod.util.IEntityHurtFramesDelay;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityMonster.class, remap = false)
public class EntityMonsterMixin extends EntityLivingMixin implements IEntityHurtFramesDelay {
	public EntityMonsterMixin(World world) { super(world); }

	@Override
	public boolean hurtWithDelay(Entity entity, int damage1, DamageType type1, int damage2, DamageType type2, Boolean doHurtHeartsFlashTime, int isHFTDelay) {
		if (super.hurtWithDelay(entity, damage1, type1, damage2, type2, doHurtHeartsFlashTime, isHFTDelay)) {
			if (this.passenger != entity && this.vehicle != entity) {
				if (entity != this) {
					((EntityPathfinderAccessor) this).setEntityToAttack(entity);
				}
            }
            return true;
        } else {
			return false;
		}
	}
}
