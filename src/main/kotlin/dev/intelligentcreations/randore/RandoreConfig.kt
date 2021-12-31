package dev.intelligentcreations.randore

import io.github.redstoneparadox.paradoxconfig.config.ConfigCategory

object RandoreConfig: ConfigCategory("randore.json") {
    object RandomOreConfig: ConfigCategory("random_ore_config", "Random Ore") {
        var randomoregen: Boolean by option(true,"do_generate")
        var randomoregen_y_min: Int by option(1,"oregen_y_min")
        var randomoregen_y_max: Int by option(50,"oregen_y_max")
        var randomoregen_size: Int by option(3,"oregen_size")
        var randomoregen_times: Int by option(13,"veins_per_chunk")
    }
    object DeepslateRandomOreConfig: ConfigCategory("deepslate_random_ore_config", "Deepslate Random Ore") {
        var dsrandomoregen: Boolean by option(true,"do_generate")
        var dsrandomoregen_y_min: Int by option(-64,"oregen_y_min")
        var dsrandomoregen_y_max: Int by option(10,"oregen_y_max")
        var dsrandomoregen_size: Int by option(4,"oregen_size")
        var dsrandomoregen_times: Int by option(8,"veins_per_chunk")
    }
    object NetherRandomOreConfig: ConfigCategory("nether_random_ore_config", "Nether Random Ore") {
        var nrandomoregen: Boolean by option(true,"do_generate")
        var nrandomoregen_y_min: Int by option(0,"oregen_y_min")
        var nrandomoregen_y_max: Int by option(128,"oregen_y_max")
        var nrandomoregen_size: Int by option(5,"oregen_size")
        var nrandomoregen_times: Int by option(10,"veins_per_chunk")
    }
    object EndRandomOreConfig: ConfigCategory("end_random_ore_config", "End Random Ore") {
        var erandomoregen: Boolean by option(true,"do_generate")
        var erandomoregen_y_min: Int by option(10,"oregen_y_min")
        var erandomoregen_y_max: Int by option(80,"oregen_y_max")
        var erandomoregen_size: Int by option(7,"oregen_size")
        var erandomoregen_times: Int by option(10,"veins_per_chunk")
    }
}