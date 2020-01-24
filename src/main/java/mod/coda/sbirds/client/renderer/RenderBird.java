package mod.coda.sbirds.client.renderer;

import javax.annotation.Nullable;

import mod.coda.sbirds.SimpleBirdsMain;
import mod.coda.sbirds.client.model.ModelBird;
import mod.coda.sbirds.common.entity.EntityBird;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBird extends RenderLiving<EntityBird> {
    private static final ResourceLocation[] BIRD_TEXTURE = new ResourceLocation[6];

    static {
        for (int i = 0; i < BIRD_TEXTURE.length; i++)
        	BIRD_TEXTURE[i] = new ResourceLocation(SimpleBirdsMain.MOD_ID, "textures/entity/texture_" + (i + 1) + ".png");
    }
	public RenderBird(RenderManager render) {
		super(render, new ModelBird(), 0.1F);
	}

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBird entity) {
        return BIRD_TEXTURE[entity.getVariant()];
    }

}