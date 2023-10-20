package brokendoop.doopmod;

import brokendoop.doopmod.entity.ModEntities;
import brokendoop.doopmod.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ConfigHandler;

import java.util.Properties;


public class DoopMod implements ModInitializer {
    public static final String MOD_ID = "doopmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private void handleConfig() {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id","3100");
		prop.setProperty("starting_item_id","21100");
		ConfigHandler config = new ConfigHandler(MOD_ID,prop);
		UtilIdRegistrar.initIds(
			config.getInt("starting_block_id"),
			config.getInt("starting_item_id")
		);
		config.updateConfig();
	}

    @Override
    public void onInitialize() {
        LOGGER.info("DoopMod initialized.");
		handleConfig();

		ModItems.register();
		ModEntities.register();
    }
}
