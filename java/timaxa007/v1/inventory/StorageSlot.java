package timaxa007.open_gui.v1.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import timaxa007.open_gui.v1.item.ItemBag;

public class StorageSlot extends Slot {

    public StorageSlot(IInventory inv, int id, int x, int y) {
        super(inv, id, x, y);
    }

    //Нельзя поставить в этот слот предмет с экземпляром ItemBackpack.
    //В дальнейшем я тут буду добавлять, чтобы нельзя из других модов вставлять предметы с хранением вещей.
    @Override
    public boolean isItemValid(ItemStack is) {
        return (is != null && is.getItem() instanceof ItemBag) ? false : super.isItemValid(is);
    }

}
