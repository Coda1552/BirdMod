package mod.coda.ambients.common.event;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import mod.coda.ambients.common.entity.EntityBird;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "ambients")
public class CommonEvents
{
    @SubscribeEvent
    public static void addEntity(final EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityOcelot) {
            final EntityOcelot cat = (EntityOcelot)event.getEntity();
            if (cat != null) {
                cat.targetTasks.addTask(1, (EntityAIBase)new EntityAITargetNonTamed((EntityTameable)cat, (Class)EntityBird.class, false, (Predicate)null));
            }
        }
    }
}