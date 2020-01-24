package mod.coda.sbirds.client.model;

import mod.coda.sbirds.common.entity.EntityBird;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelBird extends ModelBase {
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
        this.wingLeft = new ModelRenderer(this, 19, 0);
        this.wingLeft.setRotationPoint(1.0F, -2.5F, 0.0F);
        this.wingLeft.addBox(0.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(wingLeft, 0.3490658503988659F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 31, 0);
        this.tail.setRotationPoint(0.0F, -1.0F, 1.0F);
        this.tail.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
        this.setRotateAngle(tail, -0.4363323129985824F, 0.0F, 0.0F);
        this.beak = new ModelRenderer(this, 17, 0);
        this.beak.setRotationPoint(0.0F, -2.5F, -1.5F);
        this.beak.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 1, 0.0F);
        this.wingRight = new ModelRenderer(this, 19, 0);
        this.wingRight.setRotationPoint(-1.0F, -2.5F, 0.0F);
        this.wingRight.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(wingRight, 0.3490658503988659F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.body.addBox(-1.5F, -4.0F, -1.5F, 3, 4, 3, 0.0F);
        this.legRight = new ModelRenderer(this, 13, 0);
        this.legRight.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.legRight.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.legLeft = new ModelRenderer(this, 13, 0);
        this.legLeft.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.legLeft.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.crest = new ModelRenderer(this, 1, 7);
        this.crest.setRotationPoint(0.0F, -4.0F, -1.5F);
        this.crest.addBox(0.0F, -3.0F, 0.0F, 0, 3, 2, 0.0F);
        this.body.addChild(this.wingLeft);
        this.body.addChild(this.tail);
        this.body.addChild(this.beak);
        this.body.addChild(this.wingRight);
        this.body.addChild(this.crest);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.legRight);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (((EntityLivingBase) entity).isChild()) {
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
		}
		if (entity.isSneaking())
			GlStateManager.translate(0.0F, 0.2F, 0.0F);
		this.body.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(f, f1, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		float speed = 2.0f;
		float degree = 1.0f;

		if (entityIn instanceof EntityBird && entityIn.onGround) {
			EntityBird bird = (EntityBird) entityIn;

			this.wingLeft.rotateAngleZ = this.wingRight.rotateAngleZ = 0.0F;
			if (bird.peck) {
				speed = 3.0f;
				f1 = 0.25f;
				f = entityIn.ticksExisted;
				this.body.rotateAngleX = MathHelper.cos((f * speed * 0.2F) + (float) Math.PI) * (degree * 4.4F) * f1 * 0.5F + 6.8F;
				this.legLeft.rotateAngleX = MathHelper.cos((f * speed * 0.2F) + (float) Math.PI) * (degree * -4.4F) * f1 * 0.5F + -6.8F;
				this.legRight.rotateAngleX = MathHelper.cos((f * speed * 0.2F) + (float) Math.PI) * (degree * -4.4F) * f1 * 0.5F + -6.8F;
			} 
			else {
				this.body.rotateAngleX = 0.0f;
				this.legLeft.rotateAngleX = MathHelper.cos((f * speed * 0.6F) + (float) Math.PI) * (degree * 2.0F) * f1 * 0.5F;
				this.legRight.rotateAngleX = MathHelper.cos((f * speed * 0.6F) + (float) Math.PI) * (degree * -2.0F) * f1 * 0.5F;
			}
		}
		else {
			f1 = 0.3f;
			this.body.rotateAngleX = 0.0f;
			this.wingRight.rotateAngleZ = MathHelper.cos((f * speed * 2.0F) + (float) Math.PI) * (degree * 2.0F) * f1 * 0.5F + 0.3F;
			this.wingLeft.rotateAngleZ = MathHelper.cos((f * speed * 2.0F) + (float) Math.PI) * (degree * -2.0F) * f1 * 0.5F + -0.3F;
		}
	}
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
