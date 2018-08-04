package worldcontrolteam.worldcontrol.api.card;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import worldcontrolteam.worldcontrol.api.screen.IScreenElement;

/**
 * File created by mincrmatt12 on 6/17/2018.
 * Originally written for WorldControl.
 * <p>
 * See LICENSE.txt for license information.
 */
public interface ICard {
    CardState update(World world, ItemStack card);

    IScreenElement getRenderer(ItemStack stack);

    @SideOnly(Side.CLIENT)
    Gui getConfigGui(World world, ItemStack card);
}
