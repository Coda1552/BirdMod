package mod.coda.sbirds;

import java.awt.List;

import mod.coda.sbirds.client.SimpleBirdsSounds;
import mod.coda.sbirds.client.renderer.RenderBird;
import mod.coda.sbirds.common.SimpleBirdsEntities;
import mod.coda.sbirds.common.entity.EntityBird;
import mod.coda.sbirds.proxy.CommonProxy;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = SimpleBirdsMain.MOD_ID, name = SimpleBirdsMain.NAME, version = SimpleBirdsMain.VERSION, acceptedMinecraftVersions = "1.12.2")
@Mod.EventBusSubscriber(modid = SimpleBirdsMain.MOD_ID)
public class SimpleBirdsMain {

	public static final String MOD_ID = "sbirds";
	public static final String NAME = "Simple Birds";
	public static final String VERSION = "1.12.2-1.0.0";
	public static final String CLIENT_PROXY = "mod.coda.sbirds.proxy.ClientProxy";
	public static final String SERVER_PROXY = "mod.coda.sbirds.proxy.CommonProxy";

	public static final int ENTITY_BIRD = 150;

	@SidedProxy(serverSide = SERVER_PROXY, clientSide = CLIENT_PROXY)
	public static CommonProxy proxy;

	@Mod.Instance(MOD_ID)
	public static SimpleBirdsMain instance;

	@EventHandler
	public static void serverStarting(FMLServerStartingEvent event) {

		Biome.SpawnListEntry birdSpawn = new Biome.SpawnListEntry(EntityBird.class, 80, 2, 4);
		
		for(Biome type:BiomeDictionary.getBiomes(Type.FOREST)) {
			
			type.getSpawnableList(EnumCreatureType.CREATURE).add(birdSpawn);
			
		}
	}
	
	@Mod.EventBusSubscriber(modid = SimpleBirdsMain.MOD_ID)
	public static class Handlers {
		@SubscribeEvent
		public static void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
			final IForgeRegistry<EntityEntry> registry = event.getRegistry();
			int id = 0;
			for (SimpleBirdsEntities.EntityContainer a : SimpleBirdsEntities.CONTAINERS) {
				ResourceLocation r = new ResourceLocation(SimpleBirdsMain.MOD_ID, a.entityName);
				EntityEntry entry = EntityEntryBuilder.create().entity(a.entityClass).id(r, id++).name(SimpleBirdsMain.MOD_ID + "." + a.entityName).tracker(1024, 1, true).build();
				entry.setEgg(new EntityList.EntityEggInfo(r, a.primary, a.secondary));
				registry.register(entry);
			}
		}

		public static void registerSounds(RegistryEvent.Register<SoundEvent> s) {
			s.getRegistry()
					.registerAll(SimpleBirdsSounds.SOUNDS.toArray(new SoundEvent[SimpleBirdsSounds.SOUNDS.size()]));
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void registerRenders(ModelRegistryEvent e) {
			RenderingRegistry.registerEntityRenderingHandler(EntityBird.class, RenderBird::new);
		}
	}
}
