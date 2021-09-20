package dev.intelligentcreations.randore

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


// For support join https://discord.gg/v6v4pMv

class RandoreMod : ModInitializer {
    override fun onInitialize() {
        Registry.register(Registry.BLOCK, Identifier("randore", "random_ore"), RANDOM_ORE)
        Registry.register(
            Registry.ITEM,
            Identifier("randore", "random_ore"),
            BlockItem(RANDOM_ORE, FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS))
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
    }

    companion object {
        //Ore Blocks
        val RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2))
        val NETHER_RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1))
        val END_RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().breakByTool(FabricToolTags.PICKAXES, 2))

        //Ore Generations
        private val ORE_RANDOM_STONE: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                    RANDOM_ORE.defaultState,
                    3
                )
            ) // Vein size
            .range(
                RangeDecoratorConfig( // You can also use one of the other height providers if you don't want a uniform distribution
                    UniformHeightProvider.create(YOffset.aboveBottom(1), YOffset.fixed(27))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(13) // Number of veins per chunk
        private val ORE_RANDOM_NETHER: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    OreFeatureConfig.Rules.BASE_STONE_NETHER,
                    NETHER_RANDOM_ORE.defaultState,
                    5
                )
            ) // Vein size
            .range(
                RangeDecoratorConfig( // You can also use one of the other height providers if you don't want a uniform distribution
                    UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(128))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(10) // Number of veins per chunk
        private val ORE_RANDOM_END: ConfiguredFeature<*, *> = Feature.ORE
            .configure(
                OreFeatureConfig(
                    BlockMatchRuleTest(Blocks.END_STONE),
                    END_RANDOM_ORE.defaultState,
                    7
                )
            ) // Vein size
            .range(
                RangeDecoratorConfig( // You can also use one of the other height providers if you don't want a uniform distribution
                    UniformHeightProvider.create(YOffset.aboveBottom(10), YOffset.fixed(80))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(10) // Number of veins per chunk
    }
}
