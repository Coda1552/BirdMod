package mod.coda.ambients.client.model;

import mod.coda.ambients.common.entity.EntityBat;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBase;

public class ModelBat extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer wingLeft;
    public ModelRenderer wingRight;
    public ModelRenderer head;
    public ModelRenderer wingLeft_1;
    public ModelRenderer wingLeft2;
    public ModelRenderer wingRight2;
    public ModelRenderer earLeft;
    public ModelRenderer earRight;
    public ModelRenderer head_1;
    
    public ModelBat() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        (this.body = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 21.5f, 0.0f);
        this.body.addBox(-1.5f, -2.5f, -1.5f, 3, 5, 3, 0.0f);
        (this.earLeft = new ModelRenderer((ModelBase)this, 26, 0)).setRotationPoint(-1.0f, -2.0f, 0.0f);
        this.earLeft.addBox(-2.0f, -3.0f, -0.5f, 2, 3, 1, 0.0f);
        (this.wingLeft_1 = new ModelRenderer((ModelBase)this, 0, 21)).setRotationPoint(0.0f, 2.5f, 0.0f);
        this.wingLeft_1.addBox(-1.5f, 0.0f, 0.0f, 3, 2, 0, 0.0f);
        this.earRight = new ModelRenderer((ModelBase)this, 26, 0);
        this.earRight.mirror = true;
        this.earRight.setRotationPoint(1.0f, -2.0f, 0.0f);
        this.earRight.addBox(0.0f, -3.0f, -0.5f, 2, 3, 1, 0.0f);
        (this.wingRight = new ModelRenderer((ModelBase)this, 0, 9)).setRotationPoint(-1.5f, -1.0f, 0.0f);
        this.wingRight.addBox(-4.0f, -2.5f, 0.0f, 4, 5, 0, 0.0f);
        (this.head_1 = new ModelRenderer((ModelBase)this, 9, 0)).setRotationPoint(0.0f, -1.0f, -1.5f);
        this.head_1.addBox(-1.0f, -1.0f, -1.0f, 2, 2, 1, 0.0f);
        this.wingLeft = new ModelRenderer((ModelBase)this, 0, 9);
        this.wingLeft.mirror = true;
        this.wingLeft.setRotationPoint(1.5f, -1.0f, 0.0f);
        this.wingLeft.addBox(0.0f, -2.5f, 0.0f, 4, 5, 0, 0.0f);
        (this.head = new ModelRenderer((ModelBase)this, 12, 0)).setRotationPoint(0.0f, -2.5f, 0.0f);
        this.head.addBox(-2.0f, -3.0f, -1.5f, 4, 3, 3, 0.0f);
        (this.wingRight2 = new ModelRenderer((ModelBase)this, 0, 15)).setRotationPoint(-4.0f, 0.0f, 0.0f);
        this.wingRight2.addBox(-4.0f, -2.5f, 0.0f, 4, 5, 0, 0.0f);
        this.wingLeft2 = new ModelRenderer((ModelBase)this, 0, 15);
        this.wingLeft2.mirror = true;
        this.wingLeft2.setRotationPoint(4.0f, 0.0f, 0.0f);
        this.wingLeft2.addBox(0.0f, -2.5f, 0.0f, 4, 5, 0, 0.0f);
        this.head.addChild(this.earLeft);
        this.body.addChild(this.wingLeft_1);
        this.head.addChild(this.earRight);
        this.body.addChild(this.wingRight);
        this.head.addChild(this.head_1);
        this.body.addChild(this.wingLeft);
        this.body.addChild(this.head);
        this.wingRight.addChild(this.wingRight2);
        this.wingLeft.addChild(this.wingLeft2);
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
        final float speed = 1.5f;
        final float degree = 2.0f;
        f = (float)entityIn.ticksExisted;
        f1 = 0.25f;
        this.wingLeft.rotateAngleX = 0.0f;
        this.wingLeft2.rotateAngleX = 0.0f;
        this.wingRight.rotateAngleX = 0.0f;
        this.wingRight2.rotateAngleX = 0.0f;
        this.body.rotateAngleX = MathHelper.cos(speed * -0.2f + 3.1415927f) * (degree * -2.0f) * f1 * 0.5f;
        this.head.rotateAngleX = MathHelper.cos(speed * 0.2f + 3.1415927f) * (degree * 2.0f) * f1 * 0.5f;
        this.wingLeft.rotateAngleY = MathHelper.cos(f * speed * 0.6f + 3.1415927f) * (degree * 2.0f) * f1 * 0.5f;
        this.wingLeft2.rotateAngleY = MathHelper.cos(f * speed * 0.6f + 3.1415927f) * (degree * 2.0f) * f1 * 0.5f;
        this.wingRight.rotateAngleY = MathHelper.cos(f * speed * 0.6f + 3.1415927f) * (degree * -2.0f) * f1 * 0.5f;
        this.wingRight2.rotateAngleY = MathHelper.cos(f * speed * 0.6f + 3.1415927f) * (degree * -2.0f) * f1 * 0.5f;
        this.body.offsetY = MathHelper.cos(f * speed * 0.1f + 3.1415927f) * (degree * 0.2f) * f1 * 0.5f;
        if (entityIn instanceof EntityBat) {
            final EntityBat bat = (EntityBat)entityIn;
            if (bat.getIsBatHanging()) {
                this.body.rotateAngleX = 3.1415927f;
                this.body.offsetY = 0.0f;
                this.wingLeft.rotateAngleY = (float)Math.toRadians(90.0);
                this.wingLeft.rotateAngleX = (float)Math.toRadians(-45.0);
                this.wingLeft2.rotateAngleY = (float)Math.toRadians(100.0);
                this.wingRight.rotateAngleY = (float)Math.toRadians(-90.0);
                this.wingRight.rotateAngleX = (float)Math.toRadians(-45.0);
                this.wingRight2.rotateAngleY = (float)Math.toRadians(-100.0);
                if (bat.isChild()) {
                    GlStateManager.translate(0.0f, -0.23f, 0.0f);
                }
                else {
                    GlStateManager.translate(0.0f, -0.46f, 0.0f);
                }
            }
        }
    }
    
    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}