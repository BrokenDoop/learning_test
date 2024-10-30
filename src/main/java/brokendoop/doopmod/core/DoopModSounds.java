package brokendoop.doopmod.core;

import turniplabs.halplibe.helper.SoundHelper;

import static brokendoop.doopmod.DoopMod.MOD_ID;

public class DoopModSounds {
	public static void initializeSounds() {
		SoundHelper.addSound(MOD_ID, "geist/death.ogg");
	}
}
