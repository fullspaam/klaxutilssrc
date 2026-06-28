package net.spaam.klax.utils;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import static net.spaam.klax.Klax.mc;
import java.awt.*;

public final class Utils {

	public static int getPing(Entity player) {
		if (mc.getNetworkHandler().getConnection() == null) return 0;

		PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry((player.getUuid()));
		if (playerListEntry == null) return 0;
		return playerListEntry.getLatency();
	}
}
