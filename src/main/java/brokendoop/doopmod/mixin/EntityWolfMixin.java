package brokendoop.doopmod.mixin;

import brokendoop.doopmod.util.IEntityHurtFramesDelay;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.animal.EntityWolf;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityWolf.class, remap = false)
public class EntityWolfMixin extends EntityLivingMixin implements IEntityHurtFramesDelay {

	@Shadow
	public void setWolfSitting(boolean flag){}
	@Shadow
	public boolean isWolfTamed(){
        return false;
    }
	@Shadow
	public boolean isWolfAngry(){
		return false;
	}
	@Shadow
	public void setWolfAngry(boolean flag){}
	@Shadow
	public String getWolfOwner(){
        return null;
    }

	public EntityWolfMixin(World world) {
		super(world);
	}

	@Override
	public boolean hurtWithDelay(Entity entity, int damage1, DamageType type1, int damage2, DamageType type2, Boolean doHurtHeartsFlashTime, int isHFTDelay) {
		this.setWolfSitting(false);
		if (entity != null && !(entity instanceof EntityPlayer)) {
			damage1 = (damage1 + 1) / 2;
			damage2 = (damage2 + 1) / 2;
		}

		if (!super.hurtWithDelay(entity, damage1, type1, damage2, type2, doHurtHeartsFlashTime, isHFTDelay)) {
			return false;
		} else {
			if (!this.isWolfTamed() && !this.isWolfAngry()) {
				if (entity instanceof EntityPlayer) {
					this.setWolfAngry(true);
					((EntityPathfinderAccessor) this).setEntityToAttack(entity);
				}

				if (entity instanceof EntityLiving) {
					for(Entity entity1 : this.world
						.getEntitiesWithinAABB(
							EntityWolf.class,
							AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)
						)) {
						EntityWolf entitywolf = (EntityWolf)entity1;
						if (!entitywolf.isWolfTamed() && ((EntityPathfinderAccessor) entitywolf).getEntityToAttack() == null) {
							((EntityPathfinderAccessor) this).setEntityToAttack(entity);
							if (entity instanceof EntityPlayer) {
								entitywolf.setWolfAngry(true);
							}
						}
					}
				}
			} else if (entity != this && entity != null) {
				if (this.isWolfTamed() && entity instanceof EntityPlayer && ((EntityPlayer)entity).username.equalsIgnoreCase(this.getWolfOwner())) {
					return true;
				}
				((EntityPathfinderAccessor) this).setEntityToAttack(entity);
			}

			return true;
		}
	}
}
