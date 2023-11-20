package brokendoop.doopmod.mixin;

import brokendoop.doopmod.util.IEntityPlayerHurtFramesDelay;
import net.minecraft.core.achievement.stat.StatBase;
import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLivingMixin implements IEntityPlayerHurtFramesDelay {
	@Shadow
	public Gamemode gamemode = Gamemode.survival;
	@Shadow
	public void wakeUpPlayer(boolean flag, boolean flag1){}
	@Shadow
	public void addStat(StatBase statbase, int i){}
	@Shadow
	public abstract boolean isPlayerSleeping();
	@Shadow
	protected void alertWolves(EntityLiving entityliving, boolean flag){}

	public EntityPlayerMixin(World world) { super(world); }
	@Override
	public boolean hurtWithDelay(Entity entity, int damage, DamageType type, Boolean doHurtHeartsFlashTime, int isHFTDelay) {
		this.entityAge = 0;
		if (this.health <= 0) {
			return false;
		} else if (this.gamemode.isPlayerInvulnerable) {
			return false;
		} else {
			if (this.isPlayerSleeping() && !this.world.isClientSide) {
				this.wakeUpPlayer(true, true);
			}

			if (entity instanceof EntityMonster || entity instanceof EntityArrow) {
				if (this.world.difficultySetting == 0) {
					damage = 0;
				}

				if (this.world.difficultySetting == 1) {
					damage = damage / 3 + 1;
				}

				if (this.world.difficultySetting == 3) {
					damage = damage * 3 / 2;
				}
			}
			//apparently this doesn't anger wolves, so I need to re-add that code from the original method. (nvm its part of EntityWolf)
			this.addStat(StatList.damageTakenStat, damage);
			return super.hurtWithDelay(entity, damage, type, doHurtHeartsFlashTime, isHFTDelay);
		}
	}
}
