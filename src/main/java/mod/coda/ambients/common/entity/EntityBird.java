package mod.coda.ambients.common.entity;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import mod.coda.ambients.client.SimpleAmbientsSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.World;

public class EntityBird extends EntityAnimal
{
    private static final DataParameter<Integer> VARIANT;
    public int peckTimer;
    public boolean peck;
    public int peckCooldown;
    
    public EntityBird(final World worldIn) {
        super(worldIn);
        this.peckTimer = 0;
        this.peckCooldown = 0;
        this.setSize(0.2f, 0.3f);
        this.moveHelper = (EntityMoveHelper)new EntityFlyHelper((EntityLiving)this);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.1));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register((DataParameter)EntityBird.VARIANT, (Object)0);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(this.rand.nextInt(6));
        return livingdata;
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
    }
    
    protected PathNavigate getNewNavigator(final World worldIn) {
        final PathNavigateFlying pathnavigateflying = new PathNavigateFlying((EntityLiving)this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return (PathNavigate)pathnavigateflying;
    }
    
    public float getEyeHeight() {
        return this.height * 0.6f;
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.world.isRemote) {
            if (!this.peck && this.peckCooldown >= 1200 && this.getRNG().nextInt(20) == 0) {
                this.peck = true;
                this.peckCooldown = 0;
            }
            if (this.peck) {
                ++this.peckTimer;
            }
            else {
                ++this.peckCooldown;
            }
            if (this.peckTimer >= 100) {
                this.peck = false;
                this.peckTimer = 0;
            }
        }
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    public boolean onGround() {
        return this.onGround;
    }
    
    protected SoundEvent getAmbientSound() {
        return SimpleAmbientsSounds.BIRD_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PARROT_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PARROT_DEATH;
    }
    
    protected void playStepSound(final BlockPos pos, final Block blockIn) {
        this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15f, 1.0f);
    }
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_PARROT;
    }
    
    public int getVariant() {
        return (int)this.dataManager.get((DataParameter)EntityBird.VARIANT);
    }
    
    private void setVariant(final int value) {
        this.dataManager.set((DataParameter)EntityBird.VARIANT, (Object)value);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("variant", this.getVariant());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("variant"));
    }
    
    public boolean isBreedingItem(final ItemStack stack) {
        return stack.getItem() == Items.WHEAT_SEEDS;
    }
    
    @Nullable
    public EntityAgeable createChild(final EntityAgeable ageable) {
        if (ageable instanceof EntityBird) {
            final EntityBird child = new EntityBird(this.world);
            final EntityBird dropper = (EntityBird)(this.rand.nextBoolean() ? ageable : this);
            child.setVariant(dropper.getVariant());
            return (EntityAgeable)child;
        }
        return null;
    }
    
    static {
        VARIANT = EntityDataManager.createKey((Class)EntityBird.class, DataSerializers.VARINT);
    }
}