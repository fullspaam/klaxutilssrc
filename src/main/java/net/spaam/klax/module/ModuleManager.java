package net.spaam.klax.module;

import org.lwjgl.glfw.GLFW;

import net.spaam.klax.Klax;
import net.spaam.klax.event.events.ButtonListener;
import net.spaam.klax.module.modules.klax.T0temUtil;
import net.spaam.klax.module.setting.KeybindSetting;
import net.spaam.klax.utils.EncryptedString;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager implements ButtonListener {
	private final List<Module> modules = new ArrayList<>();

	public ModuleManager() {
		addModules();
		enableDefaultModules();
		addKeybinds();
	}

	private void enableDefaultModules() {
		for (Module module : modules) {
			if (module.isEnabled()) {
				module.onEnable();
			}
		}
	}

	public void addModules() {
		add(new T0temUtil());
	}

	public List<Module> getEnabledModules() {
		return modules.stream()
				.filter(Module::isEnabled)
				.toList();
	}


	public List<Module> getModules() {
		return modules;
	}

	public void addKeybinds() {
		Klax.INSTANCE.getEventManager().add(ButtonListener.class, this);

		for (Module module : modules) {
			if (module.getKey() != -1)
				module.addSetting(new KeybindSetting(EncryptedString.of("Keybind"), module.getKey(), true).setDescription(EncryptedString.of("Key to enabled the module")));
		}
	}

	public List<Module> getModulesInCategory(Category category) {
		return modules.stream()
				.filter(module -> module.getCategory() == category)
				.toList();
	}

	@SuppressWarnings("unchecked")
	public <T extends Module> T getModule(Class<T> moduleClass) {
		return (T) modules.stream()
				.filter(moduleClass::isInstance)
				.findFirst()
				.orElse(null);
	}

	public void add(Module module) {
		modules.add(module);
	}

	@Override
	public void onButtonPress(ButtonEvent event) {
        modules.forEach(module -> {
            if (module.getKey() == event.button && event.action == GLFW.GLFW_PRESS)
                module.toggle();
        });
	}
}
