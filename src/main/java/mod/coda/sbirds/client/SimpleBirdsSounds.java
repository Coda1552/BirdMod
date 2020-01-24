package mod.coda.sbirds.client;

import java.util.ArrayList;
import java.util.List;

import mod.coda.sbirds.SimpleBirdsMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SimpleBirdsSounds {

	public static List<SoundEvent> SOUNDS = new ArrayList<>();
	
	public static final SoundEvent BIRD_AMBIENT = create("entity.bird.ambient");
	
    private static SoundEvent create(String s) {
        ResourceLocation name = new ResourceLocation(SimpleBirdsMain.MOD_ID, s);
        SoundEvent sound = new SoundEvent(name).setRegistryName(name);
        SOUNDS.add(sound);
        return sound;
    }
}