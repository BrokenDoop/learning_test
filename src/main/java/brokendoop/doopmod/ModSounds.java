package brokendoop.doopmod;

import turniplabs.halplibe.helper.SoundHelper;

public class ModSounds {


	public static void register() {
		SoundHelper.addSound(DoopMod.MOD_ID, "lasershot.wav");
		SoundHelper.addSound(DoopMod.MOD_ID, "laserhit.ogg");
		SoundHelper.addSound(DoopMod.MOD_ID, "laserbounce.ogg");
		SoundHelper.addSound(DoopMod.MOD_ID, "laserpierce.ogg");
	}

}
