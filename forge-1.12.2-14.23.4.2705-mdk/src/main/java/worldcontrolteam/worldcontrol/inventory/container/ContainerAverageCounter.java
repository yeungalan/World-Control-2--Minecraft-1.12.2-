package worldcontrolteam.worldcontrol.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import worldcontrolteam.worldcontrol.inventory.SlotFilterItemHandler;
import worldcontrolteam.worldcontrol.tileentity.TileEntityBaseAverageCounter;

import javax.annotation.Nonnull;

public class ContainerAverageCounter extends Container {

    public TileEntityBaseAverageCounter averageCounter;
    private EntityPlayer player;
    private double lastAverage = -1;

    public ContainerAverageCounter(EntityPlayer player, TileEntityBaseAverageCounter averageCounter) {
        super();

        this.averageCounter = averageCounter;
        this.player = player;

        //transformer upgrades
        addSlotToContainer(new SlotFilterItemHandler(averageCounter.inventory, 0, 8, 18));

        //inventory
        for (int i = 0; i < 3; i++)
            for (int k = 0; k < 9; k++)
                addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));

        for (int j = 0; j < 9; j++)
            addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 142));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer p, int slotId) {
        Slot slot = this.inventorySlots.get(slotId);
        if (slot != null) {
            ItemStack items = slot.getStack();
            if (!items.isEmpty()) {
                int initialCount = items.getCount();
                if (slotId < averageCounter.inventory.getSlots())//moving from counter to inventory
                {
                    mergeItemStack(items, averageCounter.inventory.getSlots(), inventorySlots.size(), false);
                    if (items.getCount() == 0)
                        slot.putStack(ItemStack.EMPTY);
                    else {
                        slot.onSlotChanged();
                        if (initialCount != items.getCount())
                            return items;
                    }
                } else for (int i = 0; i < averageCounter.inventory.getSlots(); i++) {
                    if (!averageCounter.isItemValid(i, items))
                        continue;
                    ItemStack targetStack = averageCounter.inventory.getStackInSlot(i);
                    if (targetStack.isEmpty()) {
                        Slot targetSlot = this.inventorySlots.get(i);
                        targetSlot.putStack(items);
                        slot.putStack(ItemStack.EMPTY);
                        break;
                    } else if (items.isStackable() && items.isItemEqual(targetStack)) {
                        mergeItemStack(items, i, i + 1, false);
                        if (items.getCount() == 0)
                            slot.putStack(ItemStack.EMPTY);
                        else {
                            slot.onSlotChanged();
                            if (initialCount != items.getCount())
                                return items;
                        }
                        break;
                    }

                }
            }
        }
        return ItemStack.EMPTY;
    }

}
