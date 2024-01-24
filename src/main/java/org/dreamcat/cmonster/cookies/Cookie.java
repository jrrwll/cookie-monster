package org.dreamcat.cmonster.cookies;

import java.io.File;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Cookie {

	protected String name;
	protected String value;
	protected Date expires;
	protected String path;
	protected String domain;
	protected boolean secure;
	protected boolean httpOnly;
	protected File cookieStore;

	public Cookie(String name, Date expires, String path, String domain, boolean secure, boolean httpOnly, File cookieStore) {
		this.name = name;
		this.expires = expires;
		this.path = path;
		this.domain = domain;
		this.secure = secure;
		this.httpOnly = httpOnly;
		this.cookieStore = cookieStore;
	}

	@Override
	public String toString() {
		return "Cookie [name=" + name + ", value=" + value + "]";
	}
}
