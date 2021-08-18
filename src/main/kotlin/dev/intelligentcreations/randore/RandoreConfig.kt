package dev.intelligentcreations.randore

import io.github.redstoneparadox.paradoxconfig.config.ConfigCategory

object RandoreConfig: ConfigCategory("randore.json") {
    object RandomOreConfig: ConfigCategory("random_ore_config", "Random Ore") {
        var randomoregen_y_min: Long by option(1L,"oregen_y_min")
        var randomoregen_y_max: Long by option(27L,"oregen_y_max")
    }
    object DeepslateRandomOreConfig: ConfigCategory("deepslate_random_ore_config", "Deepslate Random Ore") {
        var dsrandomoregen_y_min: Long by option(-64L,"oregen_y_min")
        var dsrandomoregen_y_max: Long by option(10L,"oregen_y_max")
    }
    object NetherRandomOreConfig: ConfigCategory("nether_random_ore_config", "Nether Random Ore") {
        var nrandomoregen_y_min: Long by option(0L,"oregen_y_min")
        var nrandomoregen_y_max: Long by option(128L,"oregen_y_max")
    }
    object EndRandomOreConfig: ConfigCategory("end_random_ore_config", "End Random Ore") {
        var erandomoregen_y_min: Long by option(10L,"oregen_y_min")
        var erandomoregen_y_max: Long by option(80L,"oregen_y_max")
    }
}