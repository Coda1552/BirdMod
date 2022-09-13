package mod.coda.ambients;

import java.util.Iterator;
import mod.coda.ambients.client.renderer.RenderBat;
import mod.coda.ambients.client.renderer.RenderBird;
import mod.coda.ambients.client.SimpleAmbientsSounds;
import mod.coda.ambients.common.entity.EntityBat;
import mod.coda.ambients.common.entity.EntityBird;
import mod.coda.ambients.common.SimpleBirdsEntities;
import mod.coda.ambients.proxy.CommonProxy;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@Mod(modid = "ambients", name = "Simple Ambients", version = "1.12.2-1.2.1", acceptedMinecraftVersions = "1.12.2")
@Mod.EventBusSubscriber(modid = "ambients")
public class SimpleAmbientsMain
{
    public static final String MOD_ID = "ambients";
    public static final String NAME = "Simple Ambients";
    public static final String VERSION = "1.12.2-1.2.1";
    public static final String CLIENT_PROXY = "mod.coda.ambients.proxy.ClientProxy";
    public static final String SERVER_PROXY = "mod.coda.ambients.proxy.CommonProxy";
    @SidedProxy(serverSide = "mod.coda.ambients.proxy.CommonProxy", clientSide = "mod.coda.ambients.proxy.ClientProxy")
    public static CommonProxy proxy;
    @Mod.Instance("ambients")
    public static SimpleAmbientsMain instance;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        SimpleBirdsEntities.registerEntities();
        SimpleAmbientsSounds.registerSounds();
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerRenders(final ModelRegistryEvent e) {
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBird.class, RenderBird::new);
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBat.class, RenderBat::new);
    }
    
    @Mod.EventHandler
    public static void serverStarting(final FMLServerStartingEvent event) {
        final Biome.SpawnListEntry birdSpawn = new Biome.SpawnListEntry((Class)EntityBird.class, 80, 2, 4);
        final Biome.SpawnListEntry batSpawn = new Biome.SpawnListEntry((Class)EntityBat.class, 80, 2, 4);
        for (final Biome type : BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST)) {
            type.getSpawnableList(EnumCreatureType.CREATURE).add(birdSpawn);
        }
        for (final Biome type : BiomeDictionary.getBiomes(BiomeDictionary.Type.SPOOKY)) {
            type.getSpawnableList(EnumCreatureType.MONSTER).add(batSpawn);
        }
        for (final Biome type : BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP)) {
            type.getSpawnableList(EnumCreatureType.MONSTER).add(batSpawn);
        }
    }
}