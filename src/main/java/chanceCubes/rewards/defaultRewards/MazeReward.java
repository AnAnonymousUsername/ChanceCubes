package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.IChanceCubeReward;
import chanceCubes.util.CCubesDamageSource;
import chanceCubes.util.MazeGenerator;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketTitle.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class MazeReward implements IChanceCubeReward
{

	@Override
	public void trigger(final World world, final BlockPos pos, final EntityPlayer player)
	{
		player.sendMessage(new TextComponentString("Generating maze..... May be some lag..."));
		final MazeGenerator gen = new MazeGenerator(world, pos, player.getPosition());
		gen.generate(world, 20, 20);
		BlockPos initialPos = new BlockPos(pos.getX() - 8, pos.getY(), pos.getZ() - 8);
		player.setPositionAndUpdate(initialPos.getX() - 0.5, initialPos.getY(), initialPos.getZ() - 0.5);

		Scheduler.scheduleTask(new Task("Maze_Reward_Update", 900, 20)
		{
			@Override
			public void callback()
			{
				gen.endMaze(world, player);
				if(RewardsUtil.isPlayerOnline(player))
					player.attackEntityFrom(CCubesDamageSource.MAZE_FAIL, Float.MAX_VALUE);
			}

			@Override
			public void update()
			{
				if(initialPos.getDistance(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()) < 2)
				{
					this.delayLeft++;
					return;
				}

				int time = this.delayLeft / 20;
				TextComponentString message = new TextComponentString(String.valueOf(time));
				message.getStyle().setBold(true);
				RewardsUtil.setPlayerTitle(player, new SPacketTitle(Type.ACTIONBAR, message, 0, 20, 0));

				if(!world.getBlockState(new BlockPos(gen.endBlockWorldCords.getX(), gen.endBlockWorldCords.getY(), gen.endBlockWorldCords.getZ())).getBlock().equals(Blocks.STANDING_SIGN))
				{
					gen.endMaze(world, player);
					player.sendMessage(new TextComponentString("Hey! You won!"));
					player.sendMessage(new TextComponentString("Here, have a item!"));
					player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, new ItemStack(RewardsUtil.getRandomItem(), 1)));
					Scheduler.removeTask(this);
				}
			}
		});

		player.sendMessage(new TextComponentString("Beat the maze and find the sign!"));
		player.sendMessage(new TextComponentString("You have 45 seconds!"));
	}

	@Override
	public int getChanceValue()
	{
		return -25;
	}

	@Override
	public String getName()
	{
		return CCubesCore.MODID + ":Maze";
	}
}