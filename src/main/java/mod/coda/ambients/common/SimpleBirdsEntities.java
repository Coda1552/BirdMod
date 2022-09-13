package mod.coda.ambients.common;

import mod.coda.ambients.common.entity.EntityBat;
import mod.coda.ambients.common.entity.EntityBird;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import mod.coda.ambients.SimpleAmbientsMain;

public class SimpleBirdsEntities
{    
    public static void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation("ambients", "bird"), (Class<? extends Entity>)EntityBird.class, "ambients.bird", 1, (Object)SimpleAmbientsMain.instance, 64, 1, true, 1328273, 13096161);
        EntityRegistry.registerModEntity(new ResourceLocation("ambients", "bat"), (Class<? extends Entity>)EntityBat.class, "ambients.bat", 2, (Object)SimpleAmbientsMain.instance, 64, 1, true, 2166056, 8013933);
    }
}