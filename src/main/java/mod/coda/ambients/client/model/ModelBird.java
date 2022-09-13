package mod.coda.ambients.client.model;

import net.minecraft.util.math.MathHelper;
import mod.coda.ambients.common.entity.EntityBird;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelBird extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer beak;
    public ModelRenderer wingRight;
    public ModelRenderer wingLeft;
    public ModelRenderer tail;
    public ModelRenderer crest;
    
    public ModelBird() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        (this.wingLeft = new ModelRenderer((ModelBase)this, 19, 0)).setRotationPoint(1.0f, -2.5f, 0.0f);
        this.wingLeft.addBox(0.0f, 0.0f, -1.0f, 1, 3, 2, 0.0f);
        this.setRotateAngle(this.wingLeft, 0.34906584f, 0.0f, 0.0f);
        (this.tail = new ModelRenderer((ModelBase)this, 31, 0)).setRotationPoint(0.0f, -1.0f, 1.0f);
        this.tail.addBox(-1.0f, 0.0f, 0.0f, 2, 1, 2, 0.0f);
        this.setRotateAngle(this.tail, -0.43633232f, 0.0f, 0.0f);
        (this.beak = new ModelRenderer((ModelBase)this, 17, 0)).setRotationPoint(0.0f, -2.5f, -1.5f);
        this.beak.addBox(-0.5f, -0.5f, -1.0f, 1, 1, 1, 0.0f);
        (this.wingRight = new ModelRenderer((ModelBase)this, 19, 0)).setRotationPoint(-1.0f, -2.5f, 0.0f);
        this.wingRight.addBox(-1.0f, 0.0f, -1.0f, 1, 3, 2, 0.0f);
        this.setRotateAngle(this.wingRight, 0.34906584f, 0.0f, 0.0f);
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 23.0f, 0.0f);
        this.body.addBox(-1.5f, -4.0f, -1.5f, 3, 4, 3, 0.0f);
        (this.legRight = new ModelRenderer((ModelBase)this, 13, 0)).setRotationPoint(-1.0f, 0.0f, 0.0f);
        this.legRight.addBox(-0.5f, 0.0f, -1.0f, 1, 1, 1, 0.0f);
        (this.legLeft = new ModelRenderer((ModelBase)this, 13, 0)).setRotationPoint(1.0f, 0.0f, 0.0f);
        this.legLeft.addBox(-0.5f, 0.0f, -1.0f, 1, 1, 1, 0.0f);
        (this.crest = new ModelRenderer((ModelBase)this, 1, 7)).setRotationPoint(0.0f, -4.0f, -1.5f);
        this.crest.addBox(0.0f, -3.0f, 0.0f, 0, 3, 2, 0.0f);
        this.body.addChild(this.wingLeft);
        this.body.addChild(this.tail);
        this.body.addChild(this.beak);
        this.body.addChild(this.wingRight);
        this.body.addChild(this.crest);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.legRight);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        if (((EntityLivingBase)entity).isChild()) {
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(0.0f, 24.0f * f5, 0.0f);
        }
        if (entity.isSneaking()) {
            GlStateManager.translate(0.0f, 0.2f, 0.0f);
        }
        this.body.render(f5);
    }
    
    public void setRotationAngles(float f, float f1, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        super.setRotationAngles(f, f1, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float speed = 2.0f;
        final float degree = 1.0f;
        if (entityIn instanceof EntityBird && entityIn.onGround) {
            final EntityBird bird = (EntityBird)entityIn;
            final ModelRenderer wingLeft = this.wingLeft;
            final ModelRenderer wingRight = this.wingRight;
            final float n = 0.0f;
            wingRight.rotateAngleZ = n;
            wingLeft.rotateAngleZ = n;
            if (bird.peck) {
                speed = 3.0f;
                f1 = 0.25f;
                f = (float)entityIn.ticksExisted;
                this.body.rotateAngleX = MathHelper.cos(f * speed * 0.2f + 3.1415927f) * (degree * 4.4f) * f1 * 0.5f + 6.8f;
                this.legLeft.rotateAngleX = MathHelper.cos(f * speed * 0.2f + 3.1415927f) * (degree * -4.4f) * f1 * 0.5f - 6.8f;
                this.legRight.rotateAngleX = MathHelper.cos(f * speed * 0.2f + 3.1415927f) * (degree * -4.4f) * f1 * 0.5f - 6.8f;
            }
            else {
                this.body.rotateAngleX = 0.0f;
                this.legLeft.rotateAngleX = MathHelper.cos(f * speed * 0.6f + 3.1415927f) * (degree * 2.0f) * f1 * 0.5f;
                this.legRight.rotateAngleX = MathHelper.cos(f * speed * 0.6f + 3.1415927f) * (degree * -2.0f) * f1 * 0.5f;
            }
        }
        else {
            f1 = 0.3f;
            this.body.rotateAngleX = 0.0f;
            this.wingRight.rotateAngleZ = MathHelper.cos(f * speed * 2.0f + 3.1415927f) * (degree * 2.0f) * f1 * 0.5f + 0.3f;
            this.wingLeft.rotateAngleZ = MathHelper.cos(f * speed * 2.0f + 3.1415927f) * (degree * -2.0f) * f1 * 0.5f - 0.3f;
        }
    }
    
    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}