package net.spaam.klax.module;

import net.spaam.klax.utils.EncryptedString;

public enum Category {
	modrinth(EncryptedString.of("Klax"));
	public final CharSequence name;

	Category(CharSequence name) {
		this.name = name;
	}
}
