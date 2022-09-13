package mod.coda.ambients.client.renderer;

import javax.annotation.Nullable;
import mod.coda.ambients.client.model.ModelBird;
import mod.coda.ambients.common.entity.EntityBird;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class RenderBird extends RenderLiving<EntityBird> {
    public static ModelBase model;
    private static final ResourceLocation[] BIRD_TEXTURE = new ResourceLocation[6];

    public RenderBird(RenderManager render) {
        super(render, (ModelBase)new ModelBird(), 0.1f);
    }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityBird entity) {
        return BIRD_TEXTURE[entity.getVariant()];
    }

    static {
        for (int i = 0; i < BIRD_TEXTURE.length; ++i) {
            RenderBird.BIRD_TEXTURE[i] = new ResourceLocation("ambients", "textures/entity/texture_" + (i + 1) + ".png");
        }
    }
}