package com.example.autototem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

/**
 * AutoTotem - Fabric client mod.
 *
 * Behavior:
 *  - Press the toggle key (default: Right Shift) to turn the mod ON/OFF.
 *    A message appears in the action bar telling you the current state.
 *  - Press the mode key (default: Right Control) to switch between:
 *       "On inventory open"  -> only checks/replaces the offhand item
 *                                while your survival inventory (E) is open.
 *       "Always active"      -> checks every tick, all the time, even
 *                                with no GUI open (but not while a chest,
 *                                furnace, crafting table, etc. is open).
 *  - When active, if the offhand slot does NOT contain a Totem of Undying,
 *    the mod looks through your main inventory + hotbar for a totem and
 *    swaps it into the offhand slot. Whatever was previously in the
 *    offhand slot is placed back where the totem was.
 *  - The swap is done with real "click slot" packets (the same thing that
 *    happens when you manually drag items in your inventory), so it is
 *    properly synced with the server - it is NOT a client-side-only fake.
 */
public class AutoTotemModClient implements ClientModInitializer {

    // ---- Config (very simple, in-memory only) ----
    public enum Mode {
        ON_INVENTORY_OPEN,
        ALWAYS
    }

    private static boolean enabled = true;
    private static Mode mode = Mode.ON_INVENTORY_OPEN;

    // Slot 45 is always the offhand slot in the player's own screen handler.
    private static final int OFFHAND_SLOT = 45;
    // Slots 9-44 = main inventory (9-35) + hotbar (36-44).
    private static final int FIRST_INV_SLOT = 9;
    private static final int LAST_INV_SLOT = 44;

    private KeyBinding toggleKey;
    private KeyBinding modeKey;

    @Override
    public void onInitializeClient() {

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autototem.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.autototem"
        ));

        modeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autototem.mode",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL,
                "category.autototem"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client) {
        // Handle key presses (works even while a screen is open).
        while (toggleKey.wasPressed()) {
            enabled = !enabled;
            sendStatusMessage(client, "AutoTotem: " + (enabled ? "§aON" : "§cOFF"));
        }
        while (modeKey.wasPressed()) {
            mode = (mode == Mode.ON_INVENTORY_OPEN) ? Mode.ALWAYS : Mode.ON_INVENTORY_OPEN;
            String modeName = (mode == Mode.ALWAYS) ? "§bAlways active" : "§eOnly when inventory is open";
            sendStatusMessage(client, "AutoTotem mode: " + modeName);
        }

        if (!enabled) return;

        ClientPlayerEntity player = client.player;
        if (player == null) return;

        boolean inventoryScreenOpen = client.currentScreen instanceof InventoryScreen;

        boolean shouldCheck;
        if (mode == Mode.ALWAYS) {
            // Only act while the "active" handler is the player's own
            // inventory handler (i.e. not while a chest/furnace/etc. is open).
            shouldCheck = player.currentScreenHandler == player.playerScreenHandler;
        } else {
            shouldCheck = inventoryScreenOpen;
        }

        if (shouldCheck) {
            trySwapTotemToOffhand(client, player);
        }
    }

    private void trySwapTotemToOffhand(MinecraftClient client, ClientPlayerEntity player) {
        ScreenHandler handler = player.currentScreenHandler;
        if (handler == null) return;

        // Safety: only touch the handler if it really has an offhand slot
        // in the position we expect (the normal player screen handler).
        if (!(handler instanceof PlayerScreenHandler) && handler != player.playerScreenHandler) {
            return;
        }

        Slot offhandSlot = handler.getSlot(OFFHAND_SLOT);
        if (offhandSlot == null) return;

        if (offhandSlot.getStack().getItem() == Items.TOTEM_OF_UNDYING) {
            return; // already a totem in the offhand, nothing to do
        }

        for (int i = FIRST_INV_SLOT; i <= LAST_INV_SLOT; i++) {
            Slot slot = handler.getSlot(i);
            if (slot == null) continue;
            if (slot.getStack().getItem() == Items.TOTEM_OF_UNDYING) {
                performSwap(client, handler, i);
                return;
            }
        }
    }

    /**
     * Emulates: click totem slot (pick it up) -> click offhand slot (place
     * totem, pick up old offhand item) -> click totem slot again (drop old
     * offhand item there). This uses the real click-slot network packets.
     */
    private void performSwap(MinecraftClient client, ScreenHandler handler, int totemSlotIndex) {
        if (client.interactionManager == null || client.player == null) return;

        client.interactionManager.clickSlot(handler.syncId, totemSlotIndex, 0, SlotActionType.PICKUP, client.player);
        client.interactionManager.clickSlot(handler.syncId, OFFHAND_SLOT, 0, SlotActionType.PICKUP, client.player);

        if (!handler.getCursorStack().isEmpty()) {
            client.interactionManager.clickSlot(handler.syncId, totemSlotIndex, 0, SlotActionType.PICKUP, client.player);
        }
    }

    private void sendStatusMessage(MinecraftClient client, String message) {
        if (client.player != null) {
            client.player.sendMessage(Text.literal(message), true); // true = action bar
        }
    }
}
