package org.dreamcat.cmonster.cookies;

import java.io.File;
import java.util.Date;
import lombok.Getter;

@Getter
public class EncryptedCookie extends Cookie {

	protected byte[] encryptedValue;

	public EncryptedCookie(String name, byte[] encryptedValue, Date expires, String path, String domain, boolean secure, boolean httpOnly, File cookieStore) {
		super(name, expires, path, domain, secure, httpOnly, cookieStore);
		this.encryptedValue = encryptedValue;
		this.value = "(encrypted)";
	}

	public boolean isDecrypted() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Cookie [name=" + name + " (encrypted)]";
	}

}
