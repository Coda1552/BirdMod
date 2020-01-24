package mod.coda.sbirds.common;

import java.util.ArrayList;
import java.util.List;

import mod.coda.sbirds.common.entity.EntityBird;
import net.minecraft.entity.Entity;

public class SimpleBirdsEntities {

	public static final List<EntityContainer> CONTAINERS = new ArrayList<>();

	static {
		add(EntityBird.class, "bird", 0x144491, 0xC7D4E1);

	}

	private static void add(Class<? extends Entity> entityClass, String entityNameIn, int primary, int secondary) {
		CONTAINERS.add(new EntityContainer(entityClass, entityNameIn, primary, secondary));
	}

	public static class EntityContainer {
		public Class<? extends Entity> entityClass;
		public String entityName;
		public int primary, secondary;

		public EntityContainer(Class<? extends Entity> EntityClass, String entityNameIn, int primary, int secondary) {
			this.entityClass = EntityClass;
			this.entityName = entityNameIn;
			this.primary = primary;
			this.secondary = secondary;
		}
	}
}