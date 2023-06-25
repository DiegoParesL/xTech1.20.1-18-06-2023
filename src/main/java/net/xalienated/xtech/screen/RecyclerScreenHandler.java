package net.xalienated.xtech.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.xalienated.xtech.block.entity.RecyclerBlockEntity;
import net.xalienated.xtech.screen.slot.ModFuelSlot;
import net.xalienated.xtech.screen.slot.ModResultSlot;

public class RecyclerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    PropertyDelegate propertyDelegate;
    public final RecyclerBlockEntity blockEntity;
    public RecyclerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory,  inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(6));
    }
    public RecyclerScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity entity, PropertyDelegate delegate) {
        super(ModScreenHandlers.RECYCLER_SCREEN_HANDLER, syncId);
        checkSize((Inventory)entity,4);
        this.inventory = (Inventory)entity;
        this.propertyDelegate = delegate;
        inventory.onOpen(playerInventory.player);
        this.blockEntity = (RecyclerBlockEntity)entity;
        this.addSlot(new ModFuelSlot(((Inventory)entity), 0,18,50));
        this.addSlot(new Slot(((Inventory)entity), 1,66,16));
        this.addSlot(new Slot(((Inventory)entity), 2,66,50));
        this.addSlot(new ModResultSlot(((Inventory)entity), 3,114,33));


        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(delegate);
    }

    @Override
    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.RECYCLER_SCREEN_HANDLER;
    }
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    public int getFireOn() {
        int fireProgress = this.propertyDelegate.get(2);
        int maxfireProgress = this.propertyDelegate.get(3);  // Max Progress
        int progressArrowSize = 13; // This is the width in pixels of your arrow

        return maxfireProgress != 0 && fireProgress != 0 ? (maxfireProgress- fireProgress)*progressArrowSize / maxfireProgress : 0;
    }
    public boolean hasFuel() {
        return propertyDelegate.get(2) > 0;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
