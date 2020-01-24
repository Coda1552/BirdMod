package mod.coda.sbirds.proxy;

import mod.coda.sbirds.client.renderer.RenderBird;
import mod.coda.sbirds.common.entity.EntityBird;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	
	public static void registerEntityRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBird.class, new IRenderFactory<EntityBird>() {

			@Override
			public Render<? super EntityBird> createRenderFor(RenderManager manager) {
				return new RenderBird(manager);
			}
		});
	}
}