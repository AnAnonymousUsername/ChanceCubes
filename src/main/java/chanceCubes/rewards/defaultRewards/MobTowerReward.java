package chanceCubes.rewards.defaultRewards;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class MobTowerReward extends BaseCustomReward
{
	//@formatter:off
	private List<Class<? extends Entity>> entities = Arrays.asList(EntityCreeper.class, EntitySkeleton.class, EntityBlaze.class,
			EntityEnderman.class, EntityEndermite.class, EntityPigZombie.class, EntitySilverfish.class, EntitySlime.class,
			EntitySnowman.class, EntitySpider.class, EntityWitch.class, EntityZombie.class, EntityBat.class, EntityChicken.class,
			EntityCow.class, EntityOcelot.class, EntityParrot.class, EntityPig.class, EntityRabbit.class, EntitySheep.class,
			EntityVillager.class, EntityWolf.class);
	//@formatter:on

	public MobTowerReward()
	{
		super(CCubesCore.MODID + ":Mob_Tower", 0);
	}

	@Override
	public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
	{
		player.sendMessage(new TextComponentString("How did they end up like that? O.o"));
		int minHeight = super.getSettingAsInt(settings, "min_height", 7, 0, 20);
		int maxHeight = minHeight - super.getSettingAsInt(settings, "max_height", 13, 1, 50);
		if(maxHeight < 1)
			maxHeight = 1;
		int height = RewardsUtil.rand.nextInt(maxHeight) + minHeight;
		Entity last;
		try
		{
			last = entities.get(RewardsUtil.rand.nextInt(entities.size())).getConstructor(World.class).newInstance(world);
			last.setPosition(pos.getX(), pos.getY(), pos.getZ());
			world.spawnEntity(last);
		} catch(Exception e)
		{
			player.sendMessage(new TextComponentString("Uh oh! Something went wrong and the reward could not be spawned! Please repot this to the mod dev!"));
			return;
		}

		for(int i = 0; i < height; i++)
		{
			try
			{
				Entity ent = entities.get(RewardsUtil.rand.nextInt(entities.size())).getConstructor(World.class).newInstance(world);
				ent.setPosition(pos.getX(), pos.getY(), pos.getZ());
				world.spawnEntity(ent);
				ent.startRiding(last, true);
				last = ent;
				System.out.println(ent);
			} catch(Exception ignored)
			{
			}
		}
	}
}
