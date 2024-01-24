package org.dreamcat.cmonster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dreamcat.cmonster.browsers.Browser;
import org.dreamcat.cmonster.browsers.ChromeBrowser;
import org.dreamcat.cmonster.browsers.EdgeBrowser;
import org.dreamcat.cmonster.browsers.FirefoxBrowser;
import org.dreamcat.cmonster.browsers.InternetExplorerBrowser;
import org.dreamcat.cmonster.browsers.SafariBrowser;
import org.dreamcat.cmonster.cookies.Cookie;
import org.dreamcat.common.argparse.ArgParserField;
import org.dreamcat.common.argparse.ArgParserType;
import org.dreamcat.common.argparse.TypeArgParser;
import org.dreamcat.common.util.ObjectUtil;

@ArgParserType(helpFooter = "See https://github.com/jrrwll/cookie-monster for further details.")
public class CookieMonster {

    private static final String DEFAULT_BROWSER = "chrome";
    private static final String DEFAULT_DOMAIN = "facebook.com";

    public static final Map<String, Browser> SUPPORTED_BROWSERS;

    static {
        SUPPORTED_BROWSERS = new HashMap<>();
        SUPPORTED_BROWSERS.put("chrome", new ChromeBrowser());
        SUPPORTED_BROWSERS.put("edge", new EdgeBrowser());
        SUPPORTED_BROWSERS.put("firefox", new FirefoxBrowser());
        SUPPORTED_BROWSERS.put("ie", new InternetExplorerBrowser());
        SUPPORTED_BROWSERS.put("safari", new SafariBrowser());
    }

    @ArgParserField(firstChar = true, helpDesc = "Prints this help menu and exits")
    boolean help;
    @ArgParserField(firstChar = true,
            helpDesc = "Specifies the target browsers to search for cookie values.\neg: chrome edge firefox ie safari")
    List<String> browsers;
    @ArgParserField(firstChar = true, helpDesc = "Specifies the target domains to search for cookie values")
    List<String> domain;

    public static void main(String[] args) throws IOException {
        CookieMonster monster = new CookieMonster();
        TypeArgParser<CookieMonster> parser = new TypeArgParser<>(CookieMonster.class);
        try {
            parser.resolve(monster, args);
        } catch (Exception e) {
            System.out.println("Invalid arguments:" + e.getMessage());
            System.out.println(parser.getHelp());
            System.exit(1);
        }
        if (monster.help) {
            System.out.println(parser.getHelp());
            System.exit(0);
        }

        // get the selected browsers to search
        List<String> browsers = null;
        List<Browser> selectedBrowsers = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(monster.browsers)) {
            browsers = monster.browsers;
        }
        if (browsers != null) {
            for (String browser : browsers) {
                if (SUPPORTED_BROWSERS.containsKey(browser)) {
                    selectedBrowsers.add(SUPPORTED_BROWSERS.get(browser));
                } else {
                    System.err.println("Browser [" + browser + "] is not supported.");
                }
            }
        } else {
            selectedBrowsers.add(SUPPORTED_BROWSERS.get(DEFAULT_BROWSER));
        }
        System.out.println("Selected Browsers: " + selectedBrowsers.toString().replace("[", "").replace("]", ""));

        // get the selected domains to search
        List<String> domains;
        if (ObjectUtil.isNotEmpty(monster.domain)) {
            domains = monster.domain;
        } else {
            domains = Collections.singletonList(DEFAULT_DOMAIN);
        }
        System.out.println("Selected Domains: " + String.join(", ", domains));

        // search for the corresponding cookies
        dumpCookies(selectedBrowsers, domains);
    }

    private static void dumpCookies(List<Browser> browsers, List<String> domains) {
        System.out.println("============================================================");
        for (String domain : domains) {
            System.out.println("Searching cookies for domain: " + domain);
            System.out.println("============================================================");
            for (Browser browser : browsers) {
                System.out.println("Searching in browser: " + browser.getName());
                System.out.println("============================================================");
                Set<Cookie> cookies = browser.getCookiesForDomain(domain);
                if (cookies.isEmpty()) {
                    System.out.println("No cookies found.");
                } else {
                    for (Cookie cookie : cookies) {
                        System.out.println(cookie.toString());
                    }
                }
                System.out.println("============================================================");
            }
        }
        System.out.println("============================================================");
        System.out.println("Finished.");
    }

}
