package net.spaam.klax;

import net.spaam.klax.event.EventManager;
import net.spaam.klax.module.ModuleManager;
import net.spaam.klax.utils.rotation.RotatorManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import java.io.File;
import java.io.IOException;
import java.net.*;

@SuppressWarnings("all")
public final class Klax {
	public RotatorManager rotatorManager;
	public ModuleManager moduleManager;
	public EventManager eventManager;
	public static MinecraftClient mc;
	public String version = " b1.3";
	public static boolean BETA; //this was for beta kids but ablue never made it a reality, and you basically paid extra 10 bucks for nothing while ablue spent it all on war thunder to buy pre-historic tanks and estrogen 🤡🤡🤡
	public static Klax INSTANCE;
	public boolean guiInitialized;
	public Screen previousScreen = null;
	public long lastModified;
	public File klaxJar;

	public Klax() throws InterruptedException, IOException {
		INSTANCE = this;
		this.eventManager = new EventManager();
		this.moduleManager = new ModuleManager();
		this.rotatorManager = new RotatorManager();

		this.setLastModified();

		this.guiInitialized = false;
		mc = MinecraftClient.getInstance();
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public void resetModifiedDate() {
		this.klaxJar.setLastModified(lastModified);
	}

	public String getVersion() {
		return version;
	}

	public void setLastModified() {
		try {
			this.klaxJar = new File(Klax.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			this.lastModified = klaxJar.lastModified();
		} catch (URISyntaxException ignored) {}
	}
}