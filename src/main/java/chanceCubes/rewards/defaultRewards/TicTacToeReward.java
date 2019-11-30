package chanceCubes.rewards.defaultRewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TicTacToeReward extends BaseCustomReward
{
	public TicTacToeReward()
	{
		super(CCubesCore.MODID + ":Tic_Tac_Toe", 0);
	}

	@Override
	public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
	{
		player.sendMessage(new TextComponentString("Lets play Tic-Tac-Toe!"));
		player.sendMessage(new TextComponentString("Beat the Computer to get 500 Diamonds!"));
		player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, new ItemStack(Blocks.WOOL, 5, 14)));

		int mistakeChance = super.getSettingAsInt(settings, "mistakeChance", 0, 0, Integer.MAX_VALUE);

		RewardBlockCache cache = new RewardBlockCache(world, pos, player.getPosition());
		for(int x = -2; x < 3; x++)
			for(int z = -1; z < 2; z++)
				for(int y = 0; y < 5; y++)
					cache.cacheBlock(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());

		world.setBlockState(pos.add(-1, 0, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(-1, 1, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(-1, 2, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(-1, 3, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(-1, 4, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(1, 0, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(1, 1, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(1, 2, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(1, 3, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(1, 4, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(-2, 1, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(0, 1, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(2, 1, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(-2, 3, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(0, 3, 0), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(pos.add(2, 3, 0), Blocks.BEDROCK.getDefaultState());

		Scheduler.scheduleTask(new Task("Tic_Tac_Toe_Game", 6000, 5)
		{
			Board board = new Board();

			@Override
			public void callback()
			{
				cache.restoreBlocks(player);
			}

			@Override
			public void update()
			{
				for(int x = -1; x < 2; x++)
					for(int y = -1; y < 2; y++)
						if(board.board[x + 1][y + 1] == 0)
							if(!world.isAirBlock(pos.add(x * 2, y * 2 + 2, 0)))
								makeMove(x + 1, y + 1);
			}

			private void makeMove(int x, int y)
			{
				board.placeMove(x, y, 2);

				if(!board.isGameOver())
				{
					//Make CPU Move
					Turn benefit = Turn.CPU;
					if(mistakeChance > 0 && RewardsUtil.rand.nextInt(mistakeChance) == 0)
					{
						benefit = Turn.PLAYER;
						CCubesCore.logger.log(Level.INFO, "Tic-Tac-Toe AI making a bad move.");
					}
					board.minimax(0, Turn.CPU, benefit);
					board.placeMove(board.computersMove.x, board.computersMove.y, 1);
					world.setBlockState(pos.add(board.computersMove.x * 2 - 2, board.computersMove.y * 2, 0), RewardsUtil.getBlockStateFromBlockMeta(Blocks.WOOL, 11));
				}

				if(board.isGameOver())
				{
					if(board.hasCPUWon())
						player.sendMessage(new TextComponentString("The Computer won! Better luck next time!"));
					else if(board.hasPlayerWon())
					{
						player.sendMessage(new TextComponentString("You win. This time...."));
						player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, new ItemStack(Items.DIAMOND, 500)));
					}
					else
						player.sendMessage(new TextComponentString("You tied! Better luck next time!"));

					Task superTask = this;
					Scheduler.scheduleTask(new Task("Tic_Tac_Toe_Game_End_Delay", 40)
					{
						@Override
						public void callback()
						{
							superTask.delayLeft = 0;
						}
					});

				}
			}
		});
	}

	private static class Point
	{
		int x, y;

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	private static class Board
	{
		public int[][] board = new int[3][3];
		public Point computersMove;

		public boolean isGameOver()
		{
			return(hasPlayerWon() || hasCPUWon() || getAvailableStates().isEmpty());
		}

		public boolean hasCPUWon()
		{
			if((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1))
				return true;
			for(int i = 0; i < 3; ++i)
				if(((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1) || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1)))
					return true;
			return false;
		}

		public boolean hasPlayerWon()
		{
			if((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2))
				return true;
			for(int i = 0; i < 3; ++i)
				if((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2) || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2))
					return true;
			return false;
		}

		public List<Point> getAvailableStates()
		{
			List<Point> availablePoints = new ArrayList<>();
			for(int i = 0; i < 3; ++i)
				for(int j = 0; j < 3; ++j)
					if(board[i][j] == 0)
						availablePoints.add(new Point(i, j));
			return availablePoints;
		}

		public void placeMove(int x, int y, int player)
		{
			board[x][y] = player;
		}

		public int minimax(int depth, Turn turn, Turn benefit)
		{
			//Game status...
			if(benefit == Turn.PLAYER)
			{
				if(hasCPUWon())
					return -1;
				if(hasPlayerWon())
					return +1;
			}
			else
			{
				if(hasCPUWon())
					return +1;
				if(hasPlayerWon())
					return -1;
			}


			List<Point> pointsAvailable = getAvailableStates();
			if(pointsAvailable.isEmpty())
				return 0;
			int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
			for(int i = 0; i < pointsAvailable.size(); ++i)
			{
				Point point = pointsAvailable.get(i);
				if(turn == Turn.CPU)
				{
					placeMove(point.x, point.y, 1);
					int currentScore = minimax(depth + 1, Turn.PLAYER, benefit);
					max = Math.max(currentScore, max);

					if(currentScore >= 0 && depth == 0)
						computersMove = point;

					if(currentScore == 1)
					{
						board[point.x][point.y] = 0;
						break;
					}

					if(i == pointsAvailable.size() - 1 && max < 0 && depth == 0)
						computersMove = point;
				}
				else if(turn == Turn.PLAYER)
				{
					placeMove(point.x, point.y, 2);
					int currentScore = minimax(depth + 1, Turn.CPU, benefit);
					min = Math.min(currentScore, min);
					if(min == -1)
					{
						board[point.x][point.y] = 0;
						break;
					}
				}
				board[point.x][point.y] = 0;
			}
			return turn == Turn.CPU ? max : min;
		}
	}
	public enum Turn
	{
		CPU, PLAYER;
	}
}
