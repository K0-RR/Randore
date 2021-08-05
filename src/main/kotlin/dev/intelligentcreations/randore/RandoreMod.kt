package dev.intelligentcreations.randore

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
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
        
        val oreRandomStone = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            Identifier("randore", "ore_random_stone")
        )
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreRandomStone.value, RandoreMod.ORE_RANDOM_STONE)
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Feature.UNDERGROUND_ORES,
            oreRandomStone
        )
    }

    companion object {
        val RANDOM_ORE: Block = Block(FabricBlockSettings.of(Material.STONE).strength(4.0f))

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
                    UniformHeightProvider.create(YOffset.aboveBottom(0), YOffset.fixed(27))
                )
            ) // Inclusive min and max height
            .spreadHorizontally()
            .repeat(13) // Number of veins per chunk
    }
}
