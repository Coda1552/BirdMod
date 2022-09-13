package mod.coda.ambients.client;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SimpleAmbientsSounds
{
    public static SoundEvent BIRD_AMBIENT;
    
    public static void registerSounds() {
        SimpleAmbientsSounds.BIRD_AMBIENT = create("entity.bird.ambient");
    }
    
    private static SoundEvent create(final String s) {
        final ResourceLocation name = new ResourceLocation("ambients", s);
        final SoundEvent sound = (SoundEvent)new SoundEvent(name).setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register((SoundEvent)sound);
        return sound;
    }
}