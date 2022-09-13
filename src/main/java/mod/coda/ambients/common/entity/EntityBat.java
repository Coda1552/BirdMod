package mod.coda.ambients.common.entity;

import com.google.common.collect.Sets;
import java.util.Calendar;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.World;

public class EntityBat extends EntityAnimal
{
    private static final DataParameter<Byte> HANGING;
    private static final DataParameter<Integer> VARIANT;
    private BlockPos spawnPosition;
    
    public EntityBat(final World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.9f);
        this.setIsBatHanging(true);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(3, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.1));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register((DataParameter)EntityBat.HANGING, Byte.valueOf((byte)0));
        this.dataManager.register((DataParameter)EntityBat.VARIANT, Byte.valueOf((byte)0));
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setVariant(this.rand.nextInt(3));
        return livingdata;
    }
    
    public int getVariant() {
        return (int)this.dataManager.get((DataParameter)EntityBat.VARIANT);
    }
    
    private void setVariant(final int value) {
        this.dataManager.set((DataParameter)EntityBat.VARIANT, (Object)value);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("BatFlags", (byte)this.dataManager.get((DataParameter)EntityBat.HANGING));
        compound.setInteger("variant", this.getVariant());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set((DataParameter)EntityBat.HANGING, (Object)compound.getByte("BatFlags"));
        this.setVariant(compound.getInteger("variant"));
    }
    
    protected float getSoundVolume() {
        return 0.1f;
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }
    
    @Nullable
    public SoundEvent getAmbientSound() {
        return (this.getIsBatHanging() && this.rand.nextInt(4) != 0) ? null : SoundEvents.ENTITY_BAT_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BAT_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    protected void collideWithEntity(final Entity entityIn) {
    }
    
    protected void collideWithNearbyEntities() {
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0);
    }
    
    public boolean getIsBatHanging() {
        return ((byte)this.dataManager.get((DataParameter)EntityBat.HANGING) & 0x1) != 0x0;
    }
    
    public void setIsBatHanging(final boolean isHanging) {
        final byte b0 = (byte) this.dataManager.get((DataParameter)EntityBat.HANGING);
        if (isHanging) {
            this.dataManager.set((DataParameter)EntityBat.HANGING, (Object)(byte)(b0 | 0x1));
        }
        else {
            this.dataManager.set((DataParameter)EntityBat.HANGING, (Object)(byte)(b0 & 0xFFFFFFFE));
        }
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.getIsBatHanging()) {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.posY = MathHelper.floor(this.posY) + 1.0 - this.height;
        }
        else {
            this.motionY *= 0.6000000238418579;
        }
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
        final BlockPos blockpos = new BlockPos((Entity)this);
        final BlockPos blockpos2 = blockpos.up();
        if (this.getIsBatHanging()) {
            if (this.world.getBlockState(blockpos2).isNormalCube()) {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }
                if (this.world.getNearestPlayerNotCreative((Entity)this, 4.0) != null) {
                    this.setIsBatHanging(false);
                    this.world.playEvent((EntityPlayer)null, 1025, blockpos, 0);
                }
            }
            else {
                this.setIsBatHanging(false);
                this.world.playEvent((EntityPlayer)null, 1025, blockpos, 0);
            }
        }
        else {
            if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }
            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((double)(int)this.posX, (double)(int)this.posY, (double)(int)this.posZ) < 4.0) {
                this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }
            final double d0 = this.spawnPosition.getX() + 0.5 - this.posX;
            final double d2 = this.spawnPosition.getY() + 0.1 - this.posY;
            final double d3 = this.spawnPosition.getZ() + 0.5 - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5 - this.motionX) * 0.10000000149011612;
            this.motionY += (Math.signum(d2) * 0.699999988079071 - this.motionY) * 0.10000000149011612;
            this.motionZ += (Math.signum(d3) * 0.5 - this.motionZ) * 0.10000000149011612;
            final float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * 57.29577951308232) - 90.0f;
            final float f2 = MathHelper.wrapDegrees(f - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += f2;
            if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockpos2).isNormalCube()) {
                this.setIsBatHanging(true);
            }
        }
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    protected void updateFallState(final double y, final boolean onGroundIn, final IBlockState state, final BlockPos pos) {
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        if (!this.world.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging(false);
        }
        return super.attackEntityFrom(source, amount);
    }
    
    public static void registerFixesBat(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, (Class)EntityBat.class);
    }
    
    public boolean getCanSpawnHere() {
        final BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (blockpos.getY() >= this.world.getSeaLevel()) {
            return false;
        }
        final int i = this.world.getLightFromNeighbors(blockpos);
        int j = 4;
        if (this.isDateAroundHalloween(this.world.getCurrentDate())) {
            j = 7;
        }
        else if (this.rand.nextBoolean()) {
            return false;
        }
        return i <= this.rand.nextInt(j) && super.getCanSpawnHere();
    }
    
    public boolean isBreedingItem(final ItemStack stack) {
        return stack.getItem() == Items.SPIDER_EYE;
    }
    
    private boolean isDateAroundHalloween(final Calendar p_175569_1_) {
        return (p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20) || (p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3);
    }
    
    public float getEyeHeight() {
        return this.height / 2.0f;
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_BAT;
    }
    
    public EntityAgeable createChild(final EntityAgeable ageable) {
        if (ageable instanceof EntityBat) {
            final EntityBat child = new EntityBat(this.world);
            final EntityBat dropper = (EntityBat)(this.rand.nextBoolean() ? ageable : this);
            child.setVariant(dropper.getVariant());
            return (EntityAgeable)child;
        }
        return null;
    }
    
    static {
        HANGING = EntityDataManager.createKey((Class)EntityBat.class, DataSerializers.BYTE);
        VARIANT = EntityDataManager.createKey((Class)EntityBat.class, DataSerializers.VARINT);
    }
}