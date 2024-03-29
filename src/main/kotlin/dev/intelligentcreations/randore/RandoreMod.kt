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
import net.minecraft.world.gen.decorator.RangeDecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.heightprovider.UniformHeightProvider
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

        if (randomoregen == true) {
            val oreRandomStone = RegistryKey.of(
                Registry.CONFIGURED_FEATURE_KEY,
                Identifier("randore", "ore_random_stone")
            )
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreRandomStone.value, ORE_RANDOM_STONE)
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                oreRandomStone
            )
        } else {
            LOGGER.info("Not generating random ores.")
        }

        if (dsrandomoregen == true) {
            val oreRandomDeepslate = RegistryKey.of(
                Registry.CONFIGURED_FEATURE_KEY,
                Identifier("randore", "ore_random_deepslate")
            )
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreRandomDeepslate.value, ORE_RANDOM_DEEPSLATE)
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                oreRandomDeepslate
            )
        } else {
            LOGGER.info("Not generating deepslate random ores.")
        }

        if (nrandomoregen == true) {
            val oreRandomNether = RegistryKey.of(
                Registry.CONFIGURED_FEATURE_KEY,
                Identifier("randore", "ore_random_nether")
            )
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreRandomNether.value, ORE_RANDOM_NETHER)
            BiomeModifications.addFeature(
                BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                oreRandomNether
            )
        } else {
            LOGGER.info("Not generating nether random ores.")
        }

        if (erandomoregen == true) {
            val oreRandomEnd = RegistryKey.of(
                Registry.CONFIGURED_FEATURE_KEY,
                Identifier("randore", "ore_random_end")
            )
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreRandomEnd.value, ORE_RANDOM_END)
            BiomeModifications.addFeature(
                BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                oreRandomEnd
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
        private val ORE_RANDOM_STONE: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                    RANDOM_ORE.defaultState,
                    randomoregen_size
                )
            ) // Vein size
            .range(
                RangeDecoratorConfig( // You can also use one of the other height providers if you don't want a uniform distribution
                    UniformHeightProvider.create(YOffset.aboveBottom(randomoregen_y_min), YOffset.fixed(randomoregen_y_max))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(randomoregen_times) // Number of veins per chunk
        private val ORE_RANDOM_DEEPSLATE: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES,
                    DEEPSLATE_RANDOM_ORE.defaultState,
                    dsrandomoregen_size
                )
            ) // Vein size
            .range(
                RangeDecoratorConfig( // You can also use one of the other height providers if you don't want a uniform distribution
                    UniformHeightProvider.create(YOffset.aboveBottom(dsrandomoregen_y_min), YOffset.fixed(dsrandomoregen_y_max))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(dsrandomoregen_times) // Number of veins per chunk
        private val ORE_RANDOM_NETHER: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreFeatureConfig.Rules.BASE_STONE_NETHER,
                    NETHER_RANDOM_ORE.defaultState,
                    nrandomoregen_size
                )
            ) // Vein size
            .range(
                RangeDecoratorConfig( // You can also use one of the other height providers if you don't want a uniform distribution
                    UniformHeightProvider.create(YOffset.aboveBottom(nrandomoregen_y_min), YOffset.fixed(nrandomoregen_y_max))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(nrandomoregen_times) // Number of veins per chunk
        private val ORE_RANDOM_END: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    BlockMatchRuleTest(Blocks.END_STONE),
                    END_RANDOM_ORE.defaultState,
                    erandomoregen_size
                )
            ) // Vein size
            .range(
                RangeDecoratorConfig( // You can also use one of the other height providers if you don't want a uniform distribution
                    UniformHeightProvider.create(YOffset.aboveBottom(erandomoregen_y_min), YOffset.fixed(erandomoregen_y_max))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(erandomoregen_times) // Number of veins per chunk
    }
}
