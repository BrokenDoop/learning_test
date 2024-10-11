package brokendoop.doopmod;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static brokendoop.doopmod.DoopMod.MOD_ID;

public class DoopModConfig {
	private static final Toml properties = new Toml("DoopCraft Toml Config");
	public static TomlConfigHandler cfg;

	static {
		properties.addCategory("IDs")
			.addEntry("startingEntityID", 150);

		cfg = new TomlConfigHandler(MOD_ID, properties);
	}
}
