package mod.coda.ambients.client.renderer;

import javax.annotation.Nullable;
import mod.coda.ambients.client.model.ModelBat;
import mod.coda.ambients.common.entity.EntityBat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class RenderBat extends RenderLiving<EntityBat> {
    public static ModelBase model;
    private static final ResourceLocation[] BAT_TEXTURE = new ResourceLocation[3];

    public RenderBat(RenderManager render) {
        super(render, (ModelBase)new ModelBat(), 0.1f);
    }

    @Nullable
    protected ResourceLocation getEntityTexture(EntityBat entity) {
        return BAT_TEXTURE[entity.getVariant()];
    }

    static {
        for (int i = 0; i < BAT_TEXTURE.length; ++i) {
            RenderBat.BAT_TEXTURE[i] = new ResourceLocation("ambients", "textures/entity/bat/texture_" + (i + 1) + ".png");
        }
    }
}