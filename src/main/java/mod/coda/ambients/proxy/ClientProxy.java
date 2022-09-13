package mod.coda.ambients.proxy;

import mod.coda.ambients.common.entity.EntityBat;
import mod.coda.ambients.common.entity.EntityBird;
import mod.coda.ambients.client.renderer.RenderBird;
import mod.coda.ambients.client.renderer.RenderBat;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{    
    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBird.class, RenderBird::new);
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBat.class, RenderBat::new);
    }
}