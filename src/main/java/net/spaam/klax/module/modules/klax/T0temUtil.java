package net.spaam.klax.module.modules.klax;

import net.spaam.klax.event.events.TickListener;
import net.spaam.klax.module.Category;
import net.spaam.klax.module.Module;
import net.spaam.klax.utils.InventoryUtils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public final class T0temUtil extends Module implements TickListener {
    private static final int SWITCH_DELAY = 0;
    private static final int EQUIP_DELAY = 1;
    private static final boolean SWITCH_BACK = true;

    private int switchClock, equipClock, switchBackClock;
    private int previousSlot = -1;
    boolean sent, active = false;

    public T0temUtil() {
        super("Text1", "Text2", -1, Category.modrinth);
    }

    @Override
    public void onEnable() {
        eventManager.add(TickListener.class, this);
        reset();

        super.onEnable();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            if (!isEnabled()) {
                super.setEnabled(true);
            }
        }
    }

    @Override
    public void setEnabledStatus(boolean enabled) {
        if (enabled) {
            super.setEnabledStatus(true);
        }
    }

    @Override
    public void toggle() {
        if (!isEnabled()) {
            super.setEnabled(true);
        }
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.currentScreen != null)
            return;

        if(mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
            if(active) {
                if (SWITCH_BACK && previousSlot != -1)
                    InventoryUtils.setInvSlot(previousSlot);
                reset();
                active = false;
            }
            return;
        }

        if(!active) {
            active = true;
            previousSlot = mc.player.getInventory().selectedSlot;
        }

        if(active) {
            if (switchClock < SWITCH_DELAY) {
                switchClock++;
                return;
            }

            if (InventoryUtils.selectItemFromHotbar(Items.TOTEM_OF_UNDYING)) {
                if (equipClock < EQUIP_DELAY) {
                    equipClock++;
                    return;
                }

                if (!sent) {
                    mc.getNetworkHandler().getConnection().send(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                    sent = true;
                }
            }
        }
    }

    public void reset() {
        switchClock = 0;
        equipClock = 0;
        switchBackClock = 0;
        previousSlot = -1;

        sent = false;
        active = false;
    }
}