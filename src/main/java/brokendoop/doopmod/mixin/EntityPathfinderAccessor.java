package brokendoop.doopmod.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityPathfinder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityPathfinder.class, remap = false)
public interface EntityPathfinderAccessor {
	@Accessor
	Entity getEntityToAttack();
	@Accessor("entityToAttack")
	void setEntityToAttack(Entity entity);
}
