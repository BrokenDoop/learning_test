package brokendoop.doopmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DoopMod implements ModInitializer {
    public static final String MOD_ID = "doopmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("DoopMod initialized.");
    }
}
