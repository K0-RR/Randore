package dev.intelligentcreations.randore

import dev.intelligentcreations.randore.RandoreConfig.DeepslateRandomOreConfig.dsrandomoregen
import dev.intelligentcreations.randore.RandoreConfig.DeepslateRandomOreConfig.dsrandomoregen_size
import dev.intelligentcreations.randore.RandoreConfig.DeepslateRandomOreConfig.dsrandomoregen_times
import dev.intelligentcreations.randore.RandoreConfig.DeepslateRandomOreConfig.dsrandomoregen_y_max
import dev.intelligentcreations.randore.RandoreConfig.DeepslateRandomOreConfig.dsrandomoregen_y_min
import dev.intelligentcreations.randore.RandoreConfig.EndRandomOreConfig.erandomoregen
import dev.intelligentcreations.randore.RandoreConfig.EndRandomOreConfig.erandomoregen_size
import dev.intelligentcreations.randore.RandoreConfig.EndRandomOreConfig.erandomoregen_times
import dev.intelligentcreations.randore.RandoreConfig.EndRandomOreConfig.erandomoregen_y_max
import dev.intelligentcreations.randore.RandoreConfig.EndRandomOreConfig.erandomoregen_y_min
import dev.intelligentcreations.randore.RandoreConfig.NetherRandomOreConfig.nrandomoregen
import dev.intelligentcreations.randore.RandoreConfig.NetherRandomOreConfig.nrandomoregen_size
import dev.intelligentcreations.randore.RandoreConfig.NetherRandomOreConfig.nrandomoregen_times
import dev.intelligentcreations.randore.RandoreConfig.NetherRandomOreConfig.nrandomoregen_y_max
import dev.intelligentcreations.randore.RandoreConfig.NetherRandomOreConfig.nrandomoregen_y_min
import dev.intelligentcreations.randore.RandoreConfig.RandomOreConfig.randomoregen
import dev.intelligentcreations.randore.RandoreConfig.RandomOreConfig.randomoregen_size
import dev.intelligentcreations.randore.RandoreConfig.RandomOreConfig.randomoregen_times
import dev.intelligentcreations.randore.RandoreConfig.RandomOreConfig.randomoregen_y_max
import dev.intelligentcreations.randore.RandoreConfig.RandomOreConfig.randomoregen_y_min
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.item.*
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.CountPlacementModifier
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier
import net.minecraft.world.gen.decorator.SquarePlacementModifier
import net.minecraft.world.gen.feature.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class RandoreMod : ModInitializer {
    override fun onInitialize() {
        Registry.register(Registry.BLOCK, Identifier("randore", "random_ore"), RANDOM_ORE)
        Registry.register(
            Registry.ITEM,
            Identifier("randore", "random_ore"),
            BlockItem(RANDOM_ORE, FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS))
        )
        Registry.register(Registry.BLOCK, Identifier("randore", "deepslate_random_ore"), DEEPSLATE_RANDOM_ORE)
        Registry.register(
            Registry.ITEM,
            Identifier("randore", "deepslate_random_ore"),
            BlockItem(DEEPSLATE_RANDOM_ORE, FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS))
        )
        Registry.register(Registry.BLOCK, Identifier("randore", "nether_random_ore"), NETHER_RANDOM_ORE)
        Registry.register(
            Registry.ITEM,
            Identifier("randore", "nether_random_ore"),
            BlockItem(NETHER_RANDOM_ORE, FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS))
        )
        Registry.register(Registry.BLOCK, Identifier("randore", "end_random_ore"), END_RANDOM_ORE)
        Registry.register(
            Registry.ITEM,
            Identifier("randore", "end_random_ore"),
            BlockItem(END_RANDOM_ORE, FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS))
        )

        if (randomoregen) {
            Registry.register<ConfiguredFeature<*, *>, ConfiguredFeature<*, *>>(
                BuiltinRegistries.CONFIGURED_FEATURE,
                Identifier("randore", "random_ore_gen"), RANDOM_ORE_CONFIGURED_FEATURE
            )
            Registry.register(
                BuiltinRegistries.PLACED_FEATURE, Identifier("randore", "random_ore_gen"),
                RANDOM_ORE_PLACED_FEATURE
            )
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(
                    Registry.PLACED_FEATURE_KEY,
                    Identifier("randore", "random_ore_gen")
                )
            )
        } else {
            LOGGER.info("Not generating random ores.")
        }

        if (dsrandomoregen) {
            Registry.register<ConfiguredFeature<*, *>, ConfiguredFeature<*, *>>(
                BuiltinRegistries.CONFIGURED_FEATURE,
                Identifier("randore", "deepslate_random_ore_gen"), DEEPSLATE_RANDOM_ORE_CONFIGURED_FEATURE
            )
            Registry.register(
                BuiltinRegistries.PLACED_FEATURE, Identifier("randore", "deepslate_random_ore_gen"),
                DEEPSLATE_RANDOM_ORE_PLACED_FEATURE
            )
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(
                    Registry.PLACED_FEATURE_KEY,
                    Identifier("randore", "deepslate_random_ore_gen")
                )
            )
        } else {
            LOGGER.info("Not generating deepslate random ores.")
        }

        if (nrandomoregen) {
            Registry.register<ConfiguredFeature<*, *>, ConfiguredFeature<*, *>>(
                BuiltinRegistries.CONFIGURED_FEATURE,
                Identifier("randore", "nether_random_ore_gen"), NETHER_RANDOM_ORE_CONFIGURED_FEATURE
            )
            Registry.register(
                BuiltinRegistries.PLACED_FEATURE, Identifier("randore", "nether_random_ore_gen"),
                NETHER_RANDOM_ORE_PLACED_FEATURE
            )
            BiomeModifications.addFeature(
                BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(
                    Registry.PLACED_FEATURE_KEY,
                    Identifier("randore", "nether_random_ore_gen")
                )
            )
        } else {
            LOGGER.info("Not generating nether random ores.")
        }

        if (erandomoregen) {
            Registry.register<ConfiguredFeature<*, *>, ConfiguredFeature<*, *>>(
                BuiltinRegistries.CONFIGURED_FEATURE,
                Identifier("randore", "end_random_ore_gen"), END_RANDOM_ORE_CONFIGURED_FEATURE
            )
            Registry.register(
                BuiltinRegistries.PLACED_FEATURE, Identifier("randore", "end_random_ore_gen"),
                END_RANDOM_ORE_PLACED_FEATURE
            )
            BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(
                    Registry.PLACED_FEATURE_KEY,
                    Identifier("randore", "end_random_ore_gen")
                )
            )
        } else {
            LOGGER.info("Not generating end random ores.")
        }
    }

    companion object {
        val LOGGER: Logger = LogManager.getLogger("Rand'Ore")

        //Ore Blocks
        val RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2))
        val DEEPSLATE_RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(6.0f).requiresTool().breakByTool(FabricToolTags.PICKAXES, 3))
        val NETHER_RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1))
        val END_RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2))

        //Ore Generations
        private var RANDOM_ORE_CONFIGURED_FEATURE: ConfiguredFeature<*, *>? = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    RANDOM_ORE.defaultState,
                    randomoregen_size
                )
            ) // vein size
        var RANDOM_ORE_PLACED_FEATURE: PlacedFeature = RANDOM_ORE_CONFIGURED_FEATURE!!.withPlacement(
            CountPlacementModifier.of(randomoregen_times),  // number of veins per chunk
            SquarePlacementModifier.of(),  // spreading horizontally
            HeightRangePlacementModifier.uniform(YOffset.aboveBottom(randomoregen_y_min), YOffset.fixed(randomoregen_y_max))
        ) // height

        private var DEEPSLATE_RANDOM_ORE_CONFIGURED_FEATURE: ConfiguredFeature<*, *>? = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    DEEPSLATE_RANDOM_ORE.defaultState,
                    dsrandomoregen_size
                )
            ) // vein size
        var DEEPSLATE_RANDOM_ORE_PLACED_FEATURE: PlacedFeature = DEEPSLATE_RANDOM_ORE_CONFIGURED_FEATURE!!.withPlacement(
            CountPlacementModifier.of(dsrandomoregen_times),  // number of veins per chunk
            SquarePlacementModifier.of(),  // spreading horizontally
            HeightRangePlacementModifier.uniform(YOffset.aboveBottom(dsrandomoregen_y_min), YOffset.fixed(dsrandomoregen_y_max))
        ) // height

        private var NETHER_RANDOM_ORE_CONFIGURED_FEATURE: ConfiguredFeature<*, *>? = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreConfiguredFeatures.NETHERRACK,
                    NETHER_RANDOM_ORE.defaultState,
                    nrandomoregen_size
                )
            ) // vein size
        var NETHER_RANDOM_ORE_PLACED_FEATURE: PlacedFeature = NETHER_RANDOM_ORE_CONFIGURED_FEATURE!!.withPlacement(
            CountPlacementModifier.of(nrandomoregen_times),  // number of veins per chunk
            SquarePlacementModifier.of(),  // spreading horizontally
            HeightRangePlacementModifier.uniform(YOffset.aboveBottom(nrandomoregen_y_min), YOffset.fixed(nrandomoregen_y_max))
        ) // height

        private var END_RANDOM_ORE_CONFIGURED_FEATURE: ConfiguredFeature<*, *>? = Feature.ORE
            .configure(
                OreFeatureConfig(
                    BlockMatchRuleTest(Blocks.END_STONE),
                    END_RANDOM_ORE.defaultState,
                    erandomoregen_size
                )
            ) // vein size
        var END_RANDOM_ORE_PLACED_FEATURE: PlacedFeature = END_RANDOM_ORE_CONFIGURED_FEATURE!!.withPlacement(
            CountPlacementModifier.of(erandomoregen_times),  // number of veins per chunk
            SquarePlacementModifier.of(),  // spreading horizontally
            HeightRangePlacementModifier.uniform(YOffset.aboveBottom(erandomoregen_y_min), YOffset.fixed(erandomoregen_y_max))
        ) // height
    }
}
