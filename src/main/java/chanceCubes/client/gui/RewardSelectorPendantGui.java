package chanceCubes.client.gui;

import chanceCubes.CCubesCore;
import chanceCubes.network.CCubesPacketHandler;
import chanceCubes.network.PacketRewardSelector;
import chanceCubes.registry.GiantCubeRegistry;
import chanceCubes.registry.global.GlobalCCRewardRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class RewardSelectorPendantGui extends GuiScreen
{
	private static final ResourceLocation guiTextures = new ResourceLocation(CCubesCore.MODID + ":textures/gui/container/gui_reward_selector_pendant.png");
	private GuiTextField rewardField;
	private String rewardName = "";
	private EntityPlayer player;
	private int imageWidth = 176;
	private int imageHeight = 54;
	private ItemStack stack;

	public RewardSelectorPendantGui(EntityPlayer player, ItemStack stack)
	{
		this.stack = stack;
		this.player = player;
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("Reward"))
			this.rewardName = stack.getTagCompound().getString("Reward");
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.rewardField = new GuiTextField(0, this.fontRenderer, i + 17, j + 10, 143, 12);
		this.rewardField.setTextColor(-1);
		this.rewardField.setDisabledTextColour(-1);
		this.rewardField.setEnableBackgroundDrawing(true);
		this.rewardField.setMaxStringLength(100);
		this.rewardField.setText(this.rewardName);
		this.buttonList.add(new GuiButton(0, i + 57, j + 27, 70, 20, I18n.format("Set Reward")));
	}

	public void onGuiClosed()
	{
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton button)
	{
		if(button.enabled)
		{
			if(button.id == 0)
			{
				if(GlobalCCRewardRegistry.INSTANCE.getRewardByName(this.rewardField.getText()) != null || GiantCubeRegistry.INSTANCE.getRewardByName(this.rewardField.getText()) != null)
				{
					NBTTagCompound nbt = stack.getTagCompound();
					if(nbt == null)
						nbt = new NBTTagCompound();
					nbt.setString("Reward", this.rewardName);
					stack.setTagCompound(nbt);

					CCubesPacketHandler.INSTANCE.sendToServer(new PacketRewardSelector(this.player.getCommandSenderEntity().getName(), this.rewardField.getText()));
					rewardName = this.rewardField.getText();
					this.player.closeScreen();
				}
				else
				{
					this.rewardField.setText("Invalid Name!");
					rewardName = "";
				}
			}
		}
	}

	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException
	{
		if(!this.rewardField.textboxKeyTyped(p_73869_1_, p_73869_2_))
			super.keyTyped(p_73869_1_, p_73869_2_);
	}

	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException
	{
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.rewardField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}

	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		this.mc.getTextureManager().bindTexture(guiTextures);
		this.drawTexturedModalRect((this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, 0, 0, this.imageWidth, this.imageHeight);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		this.rewardField.drawTextBox();
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
}