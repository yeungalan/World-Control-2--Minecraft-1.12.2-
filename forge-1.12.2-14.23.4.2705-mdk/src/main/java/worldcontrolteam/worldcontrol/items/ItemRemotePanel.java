package worldcontrolteam.worldcontrol.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import worldcontrolteam.worldcontrol.WorldControl;
import worldcontrolteam.worldcontrol.inventory.InventoryItem;
import worldcontrolteam.worldcontrol.utils.GuiLib;

public class ItemRemotePanel extends WCBaseItem {

    public ItemRemotePanel() {
        super("remote_panel");

        this.addPropertyOverride(new ResourceLocation("no_card"), (stack, worldIn, entityIn) -> {
            InventoryItem inv = new InventoryItem(stack);
            if (inv.getStackInSlot(0) == ItemStack.EMPTY)
                return 1;
            return 0;
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.isSneaking()) {
            player.openGui(WorldControl.INSTANCE, GuiLib.REMOTE_PANEL, world, 0, 0, 0);
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

}