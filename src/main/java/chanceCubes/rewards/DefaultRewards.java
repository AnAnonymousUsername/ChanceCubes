package chanceCubes.rewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.config.CCubesSettings;
import chanceCubes.registry.global.GlobalCCRewardRegistry;
import chanceCubes.rewards.defaultRewards.*;
import chanceCubes.rewards.rewardparts.*;
import chanceCubes.rewards.rewardtype.*;
import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.rewards.variableTypes.StringVar;
import chanceCubes.util.CustomEntry;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.RewardData;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.SchematicUtil;
import chanceCubes.util.Task;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultRewards
{
	/**
	 * loads the default rewards of the Chance Cube
	 */
	public static void loadDefaultRewards()
	{
		RewardsUtil.initData();

		if(!CCubesSettings.enableHardCodedRewards)
			return;

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Tnt_Structure", -30, new BlockRewardType(RewardsUtil.addBlocksLists(RewardsUtil.fillArea(3, 1, 3, Blocks.TNT, -1, 0, -1, true, 0, false, false), RewardsUtil.fillArea(3, 1, 3, Blocks.REDSTONE_BLOCK, -1, 1, -1, true, 40, true, false)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Bedrock", -20, new BlockRewardType(new OffsetBlock(0, 0, 0, Blocks.BEDROCK, false))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Redstone_Diamond", 10, new ItemRewardType(RewardsUtil.generateItemParts(Items.REDSTONE, Items.DIAMOND))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Sethbling_Reward", 30, new MessageRewardType("Welcome back, SethBling here :)"), new ItemRewardType(RewardsUtil.generateItemParts(new ItemStack(Items.REDSTONE, 32), new ItemStack(Items.REPEATER, 3), new ItemStack(Items.COMPARATOR, 3), new ItemStack(Blocks.REDSTONE_LAMP, 3), new ItemStack(Blocks.REDSTONE_TORCH, 3)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":EXP", 35, new ExperienceRewardType(new ExpirencePart(100).setNumberofOrbs(10))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":EXP_Shower", 35, new ExperienceRewardType(new ExpirencePart(10), new ExpirencePart(10, 10), new ExpirencePart(10, 10), new ExpirencePart(10, 20), new ExpirencePart(10, 30), new ExpirencePart(10, 40), new ExpirencePart(10, 50), new ExpirencePart(10, 60), new ExpirencePart(10, 70), new ExpirencePart(10, 80), new ExpirencePart(10, 90), new ExpirencePart(10, 100), new ExpirencePart(10, 110), new ExpirencePart(10, 120), new ExpirencePart(10, 130), new ExpirencePart(10, 140), new ExpirencePart(10, 150))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Poison", -25, new EffectRewardType(new EffectPart(MobEffects.POISON, 25, 1).setRadius(30))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Wither_Status_Effect", -25, new EffectRewardType(new EffectPart(new StringVar(String.valueOf(Potion.getIdFromPotion(MobEffects.WITHER))), new IntVar(new Integer[] { 3, 5, 6, 8, 10 }), new IntVar(new Integer[] { 1, 2 })).setRadius(30))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Chat_Message", 0, new MessageRewardType("You have escaped the wrath of the Chance Cubes.........", "For now......")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Hearts", 0, new ParticleEffectRewardType(RewardsUtil.spawnXParticles(34, 5))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Explosion", 0, new ParticleEffectRewardType(new ParticlePart(2)), new SoundRewardType(SoundEvents.ENTITY_GENERIC_EXPLODE)));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Wool", 25, new ItemRewardType(RewardsUtil.generateItemParts(new ItemStack(Blocks.WOOL, 4, 0), new ItemStack(Blocks.WOOL, 4, 1), new ItemStack(Blocks.WOOL, 4, 2), new ItemStack(Blocks.WOOL, 4, 3), new ItemStack(Blocks.WOOL, 4, 4), new ItemStack(Blocks.WOOL, 4, 5), new ItemStack(Blocks.WOOL, 4, 6), new ItemStack(Blocks.WOOL, 4, 7), new ItemStack(Blocks.WOOL, 4, 8), new ItemStack(Blocks.WOOL, 4, 9), new ItemStack(Blocks.WOOL, 4, 10), new ItemStack(Blocks.WOOL, 4, 11), new ItemStack(Blocks.WOOL, 4, 12), new ItemStack(Blocks.WOOL, 4, 13), new ItemStack(Blocks.WOOL, 4, 14), new ItemStack(Blocks.WOOL, 4, 15)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Enchanting", 80, new ItemRewardType(new ItemPart(new ItemStack(Blocks.ENCHANTING_TABLE)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Bookshelves", 60, new ItemRewardType(new ItemPart(new ItemStack(Blocks.BOOKSHELF, 8)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Ores_Galore", 50, new ItemRewardType(RewardsUtil.generateItemParts(Items.COAL, Items.REDSTONE, Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND, Items.EMERALD))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Have_Another", -10, new ItemRewardType(new ItemPart(new ItemStack(CCubesBlocks.CHANCE_CUBE, 3))), new MessageRewardType("I hear you like Chance Cubes.", "So I put some Chance Cubes in your Chance Cubes!")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Icsahedron", 0, new ItemRewardType(new ItemPart(new ItemStack(CCubesBlocks.CHANCE_ICOSAHEDRON)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Saplings", 35, new MessageRewardType("Seems you have purchased the saplings DLC"), new ItemRewardType(RewardsUtil.generateItemParts(new ItemStack(Blocks.SAPLING, 4, 0), new ItemStack(Blocks.SAPLING, 4, 1), new ItemStack(Blocks.SAPLING, 4, 2), new ItemStack(Blocks.SAPLING, 4, 3), new ItemStack(Blocks.SAPLING, 4, 4), new ItemStack(Blocks.SAPLING, 4, 5)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Farmer", 35, new MessageRewardType("Time to farm!"), new ItemRewardType(new ItemPart(new ItemStack(Items.IRON_HOE)), new ItemPart(new ItemStack(Items.BUCKET)), new ItemPart(new ItemStack(Items.WHEAT_SEEDS, 16)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Rancher", 60, new ItemRewardType(new ItemPart(new ItemStack(Blocks.OAK_FENCE, 32)), new ItemPart(RewardsUtil.getSpawnEggForEntity(new ResourceLocation("Pig"))), new ItemPart(RewardsUtil.getSpawnEggForEntity(new ResourceLocation("Cow"))), new ItemPart(RewardsUtil.getSpawnEggForEntity(new ResourceLocation("Sheep"))))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Fighter", 25, new MessageRewardType("SPARTAAA!!!"), new ItemRewardType(RewardsUtil.generateItemParts(Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Pssst", -5, new MessageRewardType("Pssssst.... Over here!"), new EntityRewardType(new EntityPart("Creeper"))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Explorer", 30, new MessageRewardType("Lets go on a journey!"), new ItemRewardType(RewardsUtil.generateItemParts(new ItemStack(Items.COMPASS), new ItemStack(Items.CLOCK), new ItemStack(Blocks.TORCH, 64), new ItemStack(Items.IRON_PICKAXE)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Mitas", 50, new ItemRewardType(RewardsUtil.generateItemParts(new ItemStack(Items.GOLD_NUGGET, 32), new ItemStack(Items.GOLD_INGOT, 8), new ItemStack(Items.GOLDEN_CARROT, 16), new ItemStack(Items.GOLDEN_HELMET)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Horde", -25, new MessageRewardType("Release the horde!"), new EntityRewardType(RewardsUtil.spawnXEntities(EntityRewardType.getBasicNBTForEntity("Zombie"), 15))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Lava_Ring", -40, new BlockRewardType(new OffsetBlock(1, -1, 0, Blocks.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(1, -1, 1, Blocks.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(0, -1, 1, Blocks.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(-1, -1, 1, Blocks.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(-1, -1, 0, Blocks.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(-1, -1, -1, Blocks.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(0, -1, -1, Blocks.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(1, -1, -1, Blocks.LAVA, false).setRelativeToPlayer(true))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Rain", -5, new CommandRewardType("/weather thunder 20000")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Silverfish_Surround", -20, new BlockRewardType(new OffsetBlock(1, 0, 0, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(1, 1, 0, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 0, 1, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 1, 1, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(-1, 0, 0, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(-1, 1, 0, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 0, -1, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 1, -1, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 2, 0, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, -1, 0, Blocks.MONSTER_EGG, false).setRelativeToPlayer(true))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Fish_Dog", 20, new ItemRewardType(new ItemPart(new ItemStack(Items.FISH, 5)), new ItemPart(RewardsUtil.getSpawnEggForEntity(new ResourceLocation("wolf"))))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Bone_Cat", 20, new ItemRewardType(new ItemPart(new ItemStack(Items.BONE, 5)), new ItemPart(RewardsUtil.getSpawnEggForEntity(new ResourceLocation("ocelot"))))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":XP_Crystal", -60, new CommandRewardType("/summon xp_orb ~ ~1 ~ {Value:1,Passengers:[{id:\"ender_crystal\"}]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":TNT_Cat", -25, new CommandRewardType("/summon ocelot ~ ~1 ~ {CatType:0,Sitting:0,Passengers:[{id:\"tnt\",Fuse:80}]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Slime_Man", 10, new CommandRewardType("/summon slime ~ ~1 ~ {Size:3,Glowing:1b,Passengers:[{id:\"Slime\",Size:2,Glowing:1b,Passengers:[{id:\"Slime\",CustomName:\"Slime Man\",CustomNameVisible:1,Size:1,Glowing:1b}]}]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Sail_Away", 5, new BlockRewardType(new OffsetBlock(0, -1, 0, Blocks.WATER, false)), new CommandRewardType("/summon Boat %x %y %z"), new MessageRewardType("Come sail away!")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Witch", -15, new CommandRewardType("/summon witch %x %y %z ")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Spawn_Cluckington", 25, new CommandRewardType("/summon Chicken ~ ~1 ~ {CustomName:\"Cluckington\",CustomNameVisible:1,ActiveEffects:[{Id:1,Amplifier:3,Duration:199980}],Passengers:[{id:\"Zombie\",CustomName:\"wyld\",CustomNameVisible:1,IsVillager:0,IsBaby:1,ArmorItems:[{},{},{},{id:\"minecraft:skull\",Damage:3,tag:{SkullOwner:\"wyld\"},Count:1}]}]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Spawn_Jerry", 25, new CommandRewardType("/summon slime %x %y %z {Size:1,CustomName:\"Jerry\",CustomNameVisible:1}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Spawn_Glenn", 25, new CommandRewardType("/summon zombie %x %y %z {CustomName:\"Glenn\",CustomNameVisible:1}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Spawn_Dr_Trayaurus", 25, new CommandRewardType("/summon villager %x %y %z {CustomName:\"Dr Trayaurus\",CustomNameVisible:1,Profession:1}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Spawn_Pickles", 25, new CommandRewardType("/summon mooshroom ~ ~1 ~ {Age:-10000,CustomName:\"Pickles\"}"), new MessageRewardType("Why is his name pickles? The world may never know")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Want_To_Build_A_Snowman", 45, new MessageRewardType("Do you want to build a snowman?"), new ItemRewardType(new ItemPart(new ItemStack(Blocks.SNOW, 2)), new ItemPart(new ItemStack(Blocks.PUMPKIN)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Diamond_Block", 85, new BlockRewardType(new OffsetBlock(0, 0, 0, Blocks.DIAMOND_BLOCK, true, 200))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":TNT_Diamond", -35, new BlockRewardType(new OffsetBlock(0, 1, 0, Blocks.DIAMOND_BLOCK, false), new OffsetBlock(0, -1, 0, Blocks.DIAMOND_BLOCK, false), new OffsetBlock(1, 0, 0, Blocks.DIAMOND_BLOCK, false), new OffsetBlock(-1, 0, 0, Blocks.DIAMOND_BLOCK, false), new OffsetBlock(0, 0, 1, Blocks.DIAMOND_BLOCK, false), new OffsetBlock(0, 0, -1, Blocks.DIAMOND_BLOCK, false)), new CommandRewardType(RewardsUtil.executeXCommands("/summon tnt %x %y %z {Fuse:40}", 3, 5))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Anti_Slab", -15, new BlockRewardType(RewardsUtil.fillArea(3, 1, 3, Blocks.OBSIDIAN, -1, 2, -1, false, 0, false, true))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Chance_Cube_Cube", -10, new MessageRewardType("Hey, at least it isn't a Giant Chance Cube >:)"), new BlockRewardType((RewardsUtil.fillArea(2, 2, 2, CCubesBlocks.CHANCE_CUBE, -2, 0, -2, false, 1, false, false)))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Fake_TNT", 0, new SoundRewardType(new SoundPart(SoundEvents.ENTITY_TNT_PRIMED), new SoundPart(SoundEvents.ENTITY_TNT_PRIMED), new SoundPart(SoundEvents.ENTITY_TNT_PRIMED), new SoundPart(SoundEvents.ENTITY_GENERIC_EXPLODE, 120).setAtPlayersLocation(true))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Invisible_Ghasts", 0, new SoundRewardType(new SoundPart(SoundEvents.ENTITY_GHAST_SCREAM).setServerWide(true), new SoundPart(SoundEvents.ENTITY_GHAST_WARN).setServerWide(true), new SoundPart(SoundEvents.ENTITY_GHAST_WARN).setServerWide(true))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":No", 0, new BlockRewardType(new OffsetBlock(0, 0, 0, CCubesBlocks.CHANCE_CUBE, false)), new MessageRewardType("No")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Invisible_Creeper", -30, new CommandRewardType("/summon Creeper %x %y %z {ActiveEffects:[{Id:14,Amplifier:0,Duration:200,ShowParticles:0b}]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Knockback_Zombie", -35, new CommandRewardType("/summon Zombie ~ ~1 ~ {CustomName:\"Leonidas\",CustomNameVisible:1,IsVillager:0,IsBaby:1,HandItems:[{id:stick,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.knockbackResistance\",Name:\"generic.knockbackResistance\",Amount:100,Operation:0,UUIDLeast:724513,UUIDMost:715230}],ench:[{id:19,lvl:100}],display:{Name:\"The Spartan Kick\"}}},{}],HandDropChances:[0.0F,0.085F],ActiveEffects:[{Id:1,Amplifier:5,Duration:199980,ShowParticles:0b},{Id:8,Amplifier:2,Duration:199980}]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Actual_Invisible_Ghast", -80, new CommandRewardType("/summon Ghast ~ ~10 ~ {ActiveEffects:[{Id:14,Amplifier:0,Duration:2000,ShowParticles:0b}]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Giant_Chance_Cube", -45, new BlockRewardType(RewardsUtil.fillArea(3, 3, 3, CCubesBlocks.CHANCE_CUBE, -1, 0, -1, false, 0, true, false))), false);
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Fireworks", 5, new CommandRewardType(RewardsUtil.executeXCommands("/summon fireworks_rocket ~ ~1 ~ {LifeTime:15,FireworksItem:{id:fireworks,Count:1,tag:{Fireworks:{Explosions:[{Type:0,Colors:[I;16711680],FadeColors:[I;16711680]}]}}}}", 4))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":STRING!", 7, new BlockRewardType(RewardsUtil.fillArea(11, 5, 11, Blocks.TRIPWIRE, -5, 0, -5, false, 0, false, true)), new MessageRewardType("STRING!!!!")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":CARPET!", 7, new BlockRewardType(RewardsUtil.fillArea(11, 5, 11, Blocks.CARPET, -5, 0, -5, false, 0, false, true)), new MessageRewardType("CARPET!!!!")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":TNT_Bats", -50, new CommandRewardType(RewardsUtil.executeXCommands("/summon Bat ~ ~1 ~ {Passengers:[{id:\"tnt\",Fuse:80}]}", 10))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Nether_Jelly_Fish", -40, new CommandRewardType(RewardsUtil.executeXCommands("/summon bat ~ ~1 ~ {Passengers:[{id:\"magma_cube\",CustomName:\"Nether Jelly Fish\",CustomNameVisible:1,Size:3}]}", 10))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Pig_Of_Destiny", 15, new CommandRewardType("/summon Pig ~ ~1 ~ {CustomName:\"The Pig of Destiny\",CustomNameVisible:1,ArmorItems:[{},{},{id:diamond_chestplate,Count:1,tag:{ench:[{id:7,lvl:1000}]}},{}],ArmorDropChances:[0.085F,0.085F,0.0F,0.085F]}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Squid_Horde", 5, new MessageRewardType(new MessagePart("Release the horde!").setRange(32), new MessagePart("Of squids!!", 20).setRange(32)), new EntityRewardType(RewardsUtil.spawnXEntities(EntityRewardType.getBasicNBTForEntity("Squid"), 15)), new BlockRewardType(RewardsUtil.fillArea(3, 2, 3, Blocks.WATER, -1, 0, -1, false, 5, true, false))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":D-rude_SandStorm", -10, new BlockRewardType(RewardsUtil.fillArea(5, 3, 5, Blocks.SAND, -2, 0, -2, true, 0, false, true)), new MessageRewardType(new MessagePart("Well that was D-rude", 40))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Ice_Cold", -10, new BlockRewardType(RewardsUtil.fillArea(5, 3, 5, Blocks.ICE, -2, 0, -2, false, 0, false, true)), new MessageRewardType("<Shinauko> You're as cold as ice")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":DIY_Pie", 5, new BlockRewardType(new OffsetBlock(1, 0, 0, Blocks.PUMPKIN, false), new OffsetBlock(1, 1, 0, Blocks.REEDS, false)), new CommandRewardType("/summon Chicken ~ ~1 ~ {CustomName:\"Zeeth_Kyrah\",CustomNameVisible:1}"), new MessageRewardType("Do it yourself Pumpkin Pie!")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Watch_World_Burn", -5, new BlockRewardType(RewardsUtil.fillArea(7, 1, 7, Blocks.FIRE, -3, 0, -3, false, 0, true, true)), new MessageRewardType("Some people just want to watch the world burn.")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Coal_To_Diamonds", 10, new BlockRewardType(new OffsetBlock(0, 1, 0, Blocks.COAL_BLOCK, false), new OffsetBlock(0, -1, 0, Blocks.COAL_BLOCK, false), new OffsetBlock(1, 0, 0, Blocks.COAL_BLOCK, false), new OffsetBlock(-1, 0, 0, Blocks.COAL_BLOCK, false), new OffsetBlock(0, 0, 1, Blocks.COAL_BLOCK, false), new OffsetBlock(0, 0, -1, Blocks.COAL_BLOCK, false)), new CommandRewardType(RewardsUtil.executeXCommands("/summon tnt %x %y %z {Fuse:40}", 3, 5)), new ItemRewardType(new ItemPart(new ItemStack(Items.DIAMOND, 5), 50))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":SpongeBob_SquarePants", 15, new CommandRewardType("/summon Item ~ ~ ~ {Item:{id:sponge,Count:1,tag:{display:{Name:\"SpongeBob\"}}}}", "/summon Item ~ ~ ~ {Item:{id:leather_leggings,Count:1,tag:{display:{Name:\"SquarePants\"}}}}")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Hot_Tub", -15, new BlockRewardType(RewardsUtil.addBlocksLists(RewardsUtil.fillArea(7, 1, 7, Blocks.WATER, -3, -1, -3, false, 0, true, true), RewardsUtil.fillArea(7, 1, 7, Blocks.AIR, -3, -1, -3, false, 98, true, true), RewardsUtil.fillArea(7, 1, 7, Blocks.LAVA, -3, -1, -3, false, 100, true, true))), new MessageRewardType(new MessagePart("No no no. I wanted a hot tub!", 40))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Quidditch", -30, new CommandRewardType(RewardsUtil.executeXCommands("/summon Bat ~ ~ ~ {Passengers:[{id:\"Witch\"}]}", 7)), new MessageRewardType(new MessagePart("Quidditch anyone?").setRange(32))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":One_Man_Army", -10, new EntityRewardType("zombie_pigman"), new CommandRewardType(RewardsUtil.executeXCommands("/summon zombie_pigman ~ ~ ~ {Silent:1,ActiveEffects:[{Id:14,Amplifier:0,Duration:19980,ShowParticles:1b}]}", 9)), new MessageRewardType(new MessagePart("One man army").setRange(32))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Cuteness", 10, new CommandRewardType(RewardsUtil.executeXCommands("/summon rabbit ~ ~1 ~ {CustomName:\"Fluffy\",CustomNameVisible:1,RabbitType:5}", 20)), new MessageRewardType(new MessagePart("Cuteness overload!").setRange(32))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Silvermite_Stacks", -35, new CommandRewardType(RewardsUtil.executeXCommands("/summon Silverfish ~ ~1 ~ {Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\"}]}]}]}]}]}]}]}]}]}]}", 5))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Take_This", 55, new BlockRewardType(RewardsUtil.addBlocksLists(RewardsUtil.fillArea(1, 3, 1, Blocks.BRICK_BLOCK, 0, 0, 0, false, 1, false, false), RewardsUtil.fillArea(1, 3, 1, Blocks.AIR, 0, 0, 0, false, 0, false, false))), new CommandRewardType(new CommandPart("/summon item_frame ~ ~ ~1 {Item:{id:\"minecraft:stick\", Count:1b},Facing:0,ItemRotation:7}", 2), new CommandPart("/summon item_frame ~ ~1 ~1 {Item:{id:\"minecraft:diamond\", Count:1b},Facing:0,ItemRotation:0}", 2), new CommandPart("/summon item_frame ~ ~2 ~1 {Item:{id:\"minecraft:diamond\", Count:1b},Facing:0,ItemRotation:0}", 2)), new MessageRewardType("It's dangerous to go alone, here take this!")));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Invizible_Silverfish", -55, new CommandRewardType(RewardsUtil.executeXCommands("/summon Silverfish ~ ~1 ~ {Glowing:1b,ActiveEffects:[{Id:1,Amplifier:1,Duration:200000},{Id:14,Amplifier:0,Duration:20000}],Passengers:[{id:\"Silverfish\",ActiveEffects:[{Id:14,Amplifier:0,Duration:20000}]}]}", 5))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Arrow_Trap", -25, new SchematicRewardType(SchematicUtil.loadCustomSchematic(RewardData.getArrowTrapSchematic(), 1, -1, 1, 0, false, true, true, true, 0))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Trampoline", 15, new MessageRewardType("Time to bounce!"), new SchematicRewardType(SchematicUtil.loadCustomSchematic(RewardData.getTrampolineSchematic(), 1, -3, 1, 0, false, true, true, true, 0)), new BlockRewardType(new OffsetBlock(2, -2, -2, Blocks.REDSTONE_BLOCK, false, 3).setRelativeToPlayer(true).setCausesBlockUpdate(true), new OffsetBlock(2, -2, -2, Blocks.REDSTONE_WIRE, false, 5).setRelativeToPlayer(true).setCausesBlockUpdate(true))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Skeleton_Bats", -40, new CommandRewardType(RewardsUtil.executeXCommands("/summon Bat ~ ~1 ~ {Passengers:[{id:\"Skeleton\",ArmorItems:[{},{},{},{id:leather_helmet,Count:1}],HandItems:[{id:bow,Count:1},{}]}]}", 10))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Cave_Spider_Web", -30, new BlockRewardType(RewardsUtil.fillArea(7, 4, 7, Blocks.WEB, -3, 0, -3, false, 0, false, true)), new CommandRewardType(RewardsUtil.executeXCommands("/summon cave_spider ~ ~1 ~ {CustomName:\"CascadingDongs\",CustomNameVisible:1}", 6))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Guardians", -35, new BlockRewardType(RewardsUtil.fillArea(5, 5, 5, Blocks.WATER, -2, 0, -2, false, 0, false, false)), new EntityRewardType(new EntityPart(EntityRewardType.getBasicNBTForEntity("guardian"), 5), new EntityPart(EntityRewardType.getBasicNBTForEntity("guardian"), 5))));
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Cookie_Monster", -5, new MessageRewardType(new MessagePart("Here have some cookies!").setRange(32), new MessagePart("[Cookie Monster] Hey! Those are mine!", 30).setRange(32)), new CommandRewardType(new CommandPart("/summon item ~ ~1 ~ {Item:{id:\"minecraft:cookie\",Count:8b}}"), new CommandPart("/summon zombie ~ ~1 ~ {CustomName:\"Cookie Monster\",CustomNameVisible:1,IsVillager:0,IsBaby:1}", 30))));

		ItemStack stack;
		NBTTagCompound nbt;

		stack = new ItemStack(Items.STICK);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("sharpness"), 5);
		stack.setStackDisplayName("A Big Stick");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Roosevelt's_Stick", 70, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.FISHING_ROD);
		stack.setItemDamage(stack.getMaxDamage() / 2);
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Half_Fishingrod", 5, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
		stack.setStackDisplayName("Notch");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Notch", 70, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.NETHER_STAR);
		stack.setStackDisplayName("North Star");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Nether_Star", 100, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.DIAMOND_SWORD);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("sharpness"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("unbreaking"), 10);
		stack.setItemDamage(stack.getMaxDamage() - 2);
		stack.setStackDisplayName("The Divine Sword");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Divine_Sword", 95, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.DIAMOND_HELMET);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("protection"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("aqua_affinity"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("unbreaking"), 10);
		stack.setItemDamage(stack.getMaxDamage() - 2);
		stack.setStackDisplayName("The Divine Helmet");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Divine_Helmet", 95, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.DIAMOND_CHESTPLATE);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("blast_protection"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("thorns"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("unbreaking"), 10);
		stack.setItemDamage(stack.getMaxDamage() - 2);
		stack.setStackDisplayName("The Divine Chestplate");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Divine_Chestplate", 95, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.DIAMOND_LEGGINGS);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("projectile_protection"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("unbreaking"), 10);
		stack.setItemDamage(stack.getMaxDamage() - 2);
		stack.setStackDisplayName("The Divine Leggings");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Divine_Leggings", 95, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.DIAMOND_BOOTS);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("fire_protection"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("unbreaking"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("feather_falling"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("depth_strider"), 10);
		stack.setItemDamage(stack.getMaxDamage() - 2);
		stack.setStackDisplayName("The Divine Boots");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Divine_Boots", 95, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.WOODEN_PICKAXE);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("efficiency"), 10);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("fortune"), 3);
		stack.setStackDisplayName("Giga Breaker");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Giga_Breaker", 70, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.BOW);
		stack.setItemDamage(stack.getMaxDamage());
		stack.addEnchantment(RewardsUtil.getEnchantSafe("power"), 5);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("punch"), 3);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("flame"), 2);
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":One_Shot", 75, new ItemRewardType(new ItemPart(stack), new ItemPart(new ItemStack(Items.ARROW, 1)))));

		stack = new ItemStack(Items.FISH, 1, 2);
		stack.setStackDisplayName("Nemo");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Finding_Nemo", 10, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.FISH, 1, 2);
		stack.setStackDisplayName("Marlin");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Finding_Marlin", 10, new ItemRewardType(new ItemPart(stack))));

		stack = new ItemStack(Items.FIRE_CHARGE, 1);
		stack.addEnchantment(RewardsUtil.getEnchantSafe("fire_aspect"), 2);
		stack.setStackDisplayName("Why not?");
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Fire_Aspect_Fire", 60, new ItemRewardType(new ItemPart(stack))));

		TileEntitySign sign = new TileEntitySign();
		sign.signText[0] = new TextComponentString("The broken path");
		sign.signText[1] = new TextComponentString("to succeed");
		nbt = new NBTTagCompound();
		((TileEntity) sign).writeToNBT(nbt);
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Path_To_Succeed", 0, new BlockRewardType(new OffsetTileEntity(0, 0, -5, Blocks.STANDING_SIGN, nbt, true, 20), new OffsetBlock(0, -1, 0, Blocks.COBBLESTONE, true, 0), new OffsetBlock(0, -1, -1, Blocks.COBBLESTONE, true, 4), new OffsetBlock(0, -1, -2, Blocks.COBBLESTONE, true, 8), new OffsetBlock(0, -1, -3, Blocks.COBBLESTONE, true, 12), new OffsetBlock(0, -1, -4, Blocks.COBBLESTONE, true, 16), new OffsetBlock(0, -1, -5, Blocks.COBBLESTONE, true, 20))));

		OffsetTileEntity[] signs = new OffsetTileEntity[4];
		OffsetTileEntity temp;
		for(int i = 0; i < signs.length; i++)
		{
			sign = new TileEntitySign();
			sign.signText[0] = new TextComponentString("Help Me!");
			nbt = new NBTTagCompound();
			((TileEntity) sign).writeToNBT(nbt);
			temp = new OffsetTileEntity(i == 2 ? -2 : i == 3 ? 2 : 0, 1, i == 0 ? -2 : i == 1 ? 2 : 0, Blocks.WALL_SIGN, nbt, false, 5);
			temp.setBlockState(Blocks.WALL_SIGN.getDefaultState().withProperty(BlockWallSign.FACING, EnumFacing.byHorizontalIndex(i + 2)));
			signs[i] = temp;
		}

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Help_Me", -10, new BlockRewardType(RewardsUtil.addBlocksLists(RewardsUtil.fillArea(3, 1, 3, Blocks.STONEBRICK, -1, -1, -1, false, 0, true, false), RewardsUtil.fillArea(3, 3, 3, Blocks.IRON_BARS, -1, 0, -1, false, 0, true, false), RewardsUtil.fillArea(1, 3, 1, Blocks.AIR, 0, 0, 0, false, 1, true, false), signs)), new EntityRewardType(new EntityPart(EntityRewardType.getBasicNBTForEntity("Villager"), 5).setRemovedBlocks(false)), new CommandRewardType(new CommandPart("/summon tnt %x %y %z {Fuse:80}", 5))));

		OffsetBlock[] blocks = new OffsetBlock[35];
		int i = 0;
		for(int y = 0; y < 2; y++)
		{
			for(int x = 0; x < 5; x++)
			{
				for(int z = 0; z < 5; z++)
				{
					if(y == 1 && (x == 0 || x == 4 || z == 0 || z == 4))
						continue;
					blocks[i] = new OffsetBlock(x - 2, y, z - 2, Blocks.IRON_BLOCK, true, i * 5);
					i++;
				}
			}
		}
		blocks[i] = new OffsetBlock(0, 2, 0, Blocks.BEACON, true, 200);
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Beacon_Build", 100, new BlockRewardType(blocks)));

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Half_Heart", -30)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				player.setHealth(1f);
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":No_Exp", -40)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				player.experienceLevel = 0;
				player.experienceTotal = 0;
				player.experience = 0;
				player.sendMessage(new TextComponentString("Rip EXP"));
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Smite", -10)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ, false));
				player.sendMessage(new TextComponentString("Thou has been smitten!"));
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Cookie-splosion", 35)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				EntityItem cookie;
				for(double xx = 1; xx > -1; xx -= 0.25)
				{
					for(double zz = 1; zz > -1; zz -= 0.25)
					{
						cookie = new EntityItem(world, pos.getX(), pos.getY() + 1D, pos.getZ(), new ItemStack(Items.COOKIE));
						world.spawnEntity(cookie);
						cookie.motionX = xx;
						cookie.motionY = Math.random();
						cookie.motionZ = zz;
					}
				}
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Random_Status_Effect", 0)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				player.sendMessage(new TextComponentString("Selecting random potion effect to apply..."));

				Scheduler.scheduleTask(new Task("Cookie Monster", 30)
				{
					@Override
					public void callback()
					{
						PotionEffect effect = RewardsUtil.getRandomPotionEffect();
						player.sendMessage(new TextComponentString("You have been given " + I18n.translateToLocal(effect.getEffectName()).trim() + " " + (effect.getAmplifier() + 1) + " for " + (effect.getDuration() / 20) + " seconds!"));
						player.addPotionEffect(effect);
					}
				});
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Arrow_Spray", -15)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				EntityTippedArrow arrow;
				for(double xx = 1; xx > -1; xx -= 0.25)
				{
					for(double zz = 1; zz > -1; zz -= 0.25)
					{
						arrow = new EntityTippedArrow(world);
						arrow.setLocationAndAngles(pos.getX(), pos.getY() + 0.5f, pos.getZ(), 0, 0);
						arrow.motionX = xx;
						arrow.motionY = .3;
						arrow.motionZ = zz;
						world.spawnEntity(arrow);
					}
				}
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Lingering_Potions_Ring", -10)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				EntityPotion pot;
				for(double rad = -Math.PI; rad <= Math.PI; rad += (Math.PI / 10))
				{
					PotionType potionType = PotionType.REGISTRY.getObjectById(RewardsUtil.rand.nextInt(PotionType.REGISTRY.getKeys().size()));
					pot = new EntityPotion(world, player, PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potionType));
					pot.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
					pot.motionX = Math.cos(rad) * (0.1 + (0.05 * 3));
					pot.motionY = 1;
					pot.motionZ = Math.sin(rad) * (0.1 + (0.05 * 3));
					world.spawnEntity(pot);
				}
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Charged_Creeper", -40)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(0, 1, 0));
				EntityCreeper ent = new EntityCreeper(world);
				ent.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
				ent.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300, 99, true, false));
				ent.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 300, 99, true, false));
				world.spawnEntity(ent);

				Scheduler.scheduleTask(new Task("Charged Creeper Reward", 2)
				{
					@Override
					public void callback()
					{
						world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
						ent.setFire(0);
					}
				});
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Disco", 40)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				for(int xx = -4; xx < 5; xx++)
					for(int zz = -4; zz < 5; zz++)
						RewardsUtil.placeBlock(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byMetadata(RewardsUtil.rand.nextInt(16))), world, pos.add(xx, -1, zz));

				for(int i = 0; i < 10; i++)
				{
					EntitySheep sheep = new EntitySheep(world);
					sheep.setCustomNameTag("jeb_");
					sheep.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);
					world.spawnEntity(sheep);
				}

				RewardsUtil.placeBlock(CCubesBlocks.CHANCE_ICOSAHEDRON.getDefaultState(), world, pos.add(0, 3, 0));

				RewardsUtil.sendMessageToNearPlayers(world, pos, 32, "Disco Party!!!!");
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Ender_Crystal_Timer", -90)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				for(int i = 30; i > 0; i--)
					RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(0, i, 0));

				EntityEnderCrystal ent = new EntityEnderCrystal(world);
				ent.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
				world.spawnEntity(ent);

				EntityArrow arrow = new EntityTippedArrow(world, pos.getX() + 0.5, pos.getY() + 29, pos.getZ() + 0.5);
				arrow.motionX = 0;
				arrow.motionY = -0.25f;
				arrow.motionZ = 0;
				world.spawnEntity(arrow);
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":5_Prongs", -10)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				for(int xx = pos.getX() - 3; xx <= pos.getX() + 3; xx++)
					for(int zz = pos.getZ() - 3; zz <= pos.getZ() + 3; zz++)
						for(int yy = pos.getY(); yy <= pos.getY() + 4; yy++)
							RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, new BlockPos(xx, yy, zz));

				RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos);
				RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(0, 1, 0));
				RewardsUtil.placeBlock(CCubesBlocks.CHANCE_ICOSAHEDRON.getDefaultState(), world, pos.add(0, 2, 0));

				RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(-3, 0, -3));
				RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(-3, 1, -3));

				RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(-3, 0, 3));
				RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(-3, 1, 3));

				RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(3, 0, -3));
				RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(3, 1, -3));

				RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(3, 0, 3));
				RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(3, 1, 3));
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Inventory_Bomb", -55)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				player.inventory.dropAllItems();

				for(int i = 0; i < player.inventory.mainInventory.size(); i++)
					player.inventory.mainInventory.set(i, new ItemStack(Blocks.DEADBUSH, 64));

				for(int i = 0; i < player.inventory.armorInventory.size(); i++)
				{
					ItemStack stack = new ItemStack(Blocks.DEADBUSH, 64);
					if(i == 0)
					{
						stack.setStackDisplayName("ButtonBoy");
						stack.setCount(13);
					}
					else if(i == 1)
					{
						stack.setStackDisplayName("TheBlackswordsman");
						stack.setCount(13);
					}
					player.inventory.armorInventory.set(i, stack);
				}

				player.sendMessage(new TextComponentString("Inventory Bomb!!!!"));
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Nuke", -75)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				RewardsUtil.sendMessageToNearPlayers(world, pos, 32, "May death rain upon them");
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() - 6, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() - 6, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() - 6, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() - 6, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() - 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() - 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() - 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() - 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() + 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() + 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() + 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() + 2, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() + 6, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() + 6, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() + 6, player));
				world.spawnEntity(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() + 6, player));
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Random_Teleport", -15)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				int xChange = ((world.rand.nextInt(50) + 20) + pos.getX()) - 35;
				int zChange = ((world.rand.nextInt(50) + 20) + pos.getZ()) - 35;

				int yChange = -1;

				for(int yy = 0; yy <= world.getActualHeight(); yy++)
				{
					if(world.isAirBlock(new BlockPos(xChange, yy, zChange)) && world.isAirBlock(new BlockPos(xChange, yy + 1, zChange)))
					{
						yChange = yy;
						break;
					}
				}
				if(yChange == -1)
					return;

				player.setPositionAndUpdate(xChange, yChange, zChange);
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Rotten_Food", -30)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				for(int i = 0; i < player.inventory.mainInventory.size(); i++)
				{
					ItemStack stack = player.inventory.mainInventory.get(i);
					if(!stack.isEmpty() && stack.getItem() instanceof ItemFood)
						player.inventory.mainInventory.set(i, new ItemStack(Items.ROTTEN_FLESH, stack.getCount()));
				}

				player.sendMessage(new TextComponentString("Ewwww it's all rotten"));
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Thrown_In_Air", -35)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				int px = (int) Math.floor(player.posX);
				int py = (int) Math.floor(player.posY) + 1;
				int pz = (int) Math.floor(player.posZ);

				for(int y = 0; y < 40; y++)
					for(int x = -1; x < 2; x++)
						for(int z = -1; z < 2; z++)
							RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(px + x, py + y, pz + z));

				Scheduler.scheduleTask(new Task("Thrown_In_Air_Reward", 5)
				{
					@Override
					public void callback()
					{
						player.isAirBorne = true;
						player.motionY = 20;
						((EntityPlayerMP) player).connection.sendPacket(new SPacketEntityVelocity(player.getEntityId(), player.motionX, player.motionY, player.motionZ));
					}
				});
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Torches_To_Creepers", -40)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				for(int yy = -32; yy <= 32; yy++)
				{
					for(int xx = -32; xx <= 32; xx++)
					{
						for(int zz = -32; zz <= 32; zz++)
						{
							IBlockState b = world.getBlockState(pos.add(xx, yy, zz));
							if(b.getLightValue(world, pos) > 0 && b.getBlock() != Blocks.LAVA && !b.getBlock().hasTileEntity(b))
							{
								RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(xx, yy, zz));
								EntityCreeper creeper = new EntityCreeper(world);
								creeper.setLocationAndAngles(pos.getX() + xx + 0.5, pos.getY() + yy, pos.getZ() + zz + 0.5, 0, 0);
								world.spawnEntity(creeper);
							}
						}
					}
				}
				player.sendMessage(new TextComponentString("Those lights seem a little weird.... O.o"));
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Traveller", 15)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				int x = RewardsUtil.rand.nextInt(1000) + 200;
				int z = RewardsUtil.rand.nextInt(1000) + 200;

				BlockPos newPos = pos.add(x, 0, z);
				RewardsUtil.placeBlock(Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.WEST), world, newPos);
				TileEntityChest chest = (TileEntityChest) world.getTileEntity(newPos);
				for(int i = 0; i < 10; i++)
					chest.setInventorySlotContents(i, new ItemStack(RewardsUtil.getRandomItem()));

				RewardsUtil.sendMessageToNearPlayers(world, pos, 25, "" + newPos.getX() + ", " + newPos.getY() + ", " + newPos.getZ());
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Troll_Hole", -20)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				final BlockPos worldPos = new BlockPos(Math.floor(player.posX), Math.floor(player.posY) - 1, Math.floor(player.posZ));
				final RewardBlockCache cache = new RewardBlockCache(world, worldPos, new BlockPos(worldPos.getX(), worldPos.getY() + 1, worldPos.getZ()));

				for(int y = 0; y > -75; y--)
					for(int x = -2; x < 3; x++)
						for(int z = -2; z < 3; z++)
							cache.cacheBlock(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());

				Scheduler.scheduleTask(new Task("TrollHole", 35)
				{
					@Override
					public void callback()
					{
						cache.restoreBlocks(player);
						player.motionY = 0;
						player.fallDistance = 0;
					}
				});
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Saw_Nothing_Diamond", 0)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				EntityItem itemEnt = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.DIAMOND, 1));
				itemEnt.setInfinitePickupDelay();
				world.spawnEntity(itemEnt);

				Scheduler.scheduleTask(new Task("Saw_Nothing_Diamond", 100)
				{
					@Override
					public void callback()
					{
						itemEnt.setDead();
						player.sendMessage(new TextComponentString("You didn't see anything......"));
					}
				});
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new BaseCustomReward(CCubesCore.MODID + ":Hand_Enchant", 20)
		{
			@Override
			public void trigger(World world, BlockPos pos, EntityPlayer player, Map<String, Object> settings)
			{
				ItemStack toEnchant;
				if(!player.getHeldItemMainhand().isEmpty())
				{
					toEnchant = player.getHeldItemMainhand();
				}
				else
				{
					List<ItemStack> stacks = new ArrayList<>();
					for(ItemStack stack : player.inventory.mainInventory)
						if(!stack.isEmpty())
							stacks.add(stack);

					for(ItemStack stack : player.inventory.armorInventory)
						if(!stack.isEmpty())
							stacks.add(stack);

					if(stacks.size() == 0)
					{
						ItemStack dirt = new ItemStack(Blocks.DIRT);
						dirt.setStackDisplayName("A lonley piece of dirt");
						player.inventory.addItemStackToInventory(dirt);
						RewardsUtil.executeCommand(world, player, "/advancement grant @p only chancecubes:lonely_dirt");
						return;
					}

					toEnchant = stacks.get(RewardsUtil.rand.nextInt(stacks.size()));
				}

				CustomEntry<Enchantment, Integer> enchantment = RewardsUtil.getRandomEnchantmentAndLevel();
				toEnchant.addEnchantment(enchantment.getKey(), enchantment.getValue());
			}
		});

		GlobalCCRewardRegistry.INSTANCE.registerReward(new AnvilRain());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new HerobrineReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new SurroundedReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new CreeperSurroundedReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new WitherReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new TrollTNTReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new WaitForItReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new ClearInventoryReward());
		// GlobalCCRewardRegistry.INSTANCE.registerReward(new ZombieCopyCatReward());
		// GlobalCCRewardRegistry.INSTANCE.registerReward(new InventoryChestReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new ItemOfDestinyReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new JukeBoxReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BookOfMemesReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new TableFlipReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new MazeReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new OneIsLuckyReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new SkyblockReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new CakeIsALieReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new ItemRenamer());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new DoubleRainbow());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new WolvesToCreepersReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new DidYouKnowReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new ArmorStandArmorReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new RainingCatsAndCogsReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new ItemChestReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new MagicFeetReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new DigBuildReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new ChanceCubeRenameReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new CountDownReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new MobTowerReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new MontyHallReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new MatchingReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new TicTacToeReward());
		// GlobalCCRewardRegistry.INSTANCE.registerReward(new MobEffectsReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BossMimicReward());
		// GlobalCCRewardRegistry.INSTANCE.registerReward(new BossSlimeQueenReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BossWitchReward());
		GlobalCCRewardRegistry.INSTANCE.registerReward(new BossBlazeReward());

		MathReward math = new MathReward();
		MinecraftForge.EVENT_BUS.register(math);
		GlobalCCRewardRegistry.INSTANCE.registerReward(math);

		QuestionsReward question = new QuestionsReward();
		MinecraftForge.EVENT_BUS.register(question);
		GlobalCCRewardRegistry.INSTANCE.registerReward(question);

		CoinFlipReward coinFlip = new CoinFlipReward();
		MinecraftForge.EVENT_BUS.register(coinFlip);
		GlobalCCRewardRegistry.INSTANCE.registerReward(coinFlip);
	}
}
