package chanceCubes.rewards.giantRewards;

import java.util.Map;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TNTSlingReward extends BaseCustomReward
{
	public TNTSlingReward()
	{
		super(CCubesCore.MODID + ":TNT_Throw", 0);
	}

	@Override
	public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
	{
		Scheduler.scheduleTask(new Task("Throw TNT", 250, 10)
		{
			private EntityTNTPrimed tnt;

			@Override
			public void callback()
			{
				for(double xx = 1; xx > -1; xx -= 0.25)
				{
					for(double zz = 1; zz > -1; zz -= 0.25)
					{
						tnt = new EntityTNTPrimed(world, pos.getX(), pos.getY() + 1D, pos.getZ(), null);
						world.spawnEntity(tnt);
						tnt.setFuse(60);
						tnt.motionX = xx;
						tnt.motionY = Math.random();
						tnt.motionZ = zz;
					}
				}
			}

			@Override
			public void update()
			{
				tnt = new EntityTNTPrimed(world, pos.getX(), pos.getY() + 1D, pos.getZ(), player);
				world.spawnEntity(tnt);
				tnt.setFuse(60);
				tnt.motionX = -1 + (Math.random() * 2);
				tnt.motionY = Math.random();
				tnt.motionZ = -1 + (Math.random() * 2);
			}
		});
	}
}