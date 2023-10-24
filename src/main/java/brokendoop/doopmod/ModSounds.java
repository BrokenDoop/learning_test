package brokendoop.doopmod;

import turniplabs.halplibe.helper.SoundHelper;

public class ModSounds {


	public static void register() {
		SoundHelper.addSound(DoopMod.MOD_ID, "laser/shot.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laser/hit.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laser/bounce.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laser/pierce.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laser/inwater.wav");
	}

}
