package brokendoop.doopmod;

import turniplabs.halplibe.helper.SoundHelper;

public class ModSounds {


	public static void register() {
		SoundHelper.addSound(DoopMod.MOD_ID, "lasershot.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laserhit.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laserbounce.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laserpierce.wav");
	}

}
