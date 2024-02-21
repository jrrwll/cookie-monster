package org.dreamcat.cmonster;

import java.util.Set;
import org.dreamcat.cmonster.browsers.Browser;
import org.dreamcat.cmonster.browsers.ChromeBrowser;
import org.dreamcat.cmonster.browsers.EdgeBrowser;
import org.dreamcat.cmonster.cookies.Cookie;
import org.dreamcat.common.util.ObjectUtil;

/**
 * @author Jerry Will
 * @version 2024-01-16
 */
public class EdgeTest {

    public static void main(String[] args) {
        EdgeBrowser edgeBrowser = new EdgeBrowser();
        showCookies(edgeBrowser, "translate.google.com");
        showCookies(edgeBrowser, "google.com");

        ChromeBrowser chromeBrowser = new ChromeBrowser();
        showCookies(chromeBrowser, "translate.google.com");
        showCookies(chromeBrowser, "google.com");
    }

    private static void showCookies(Browser browser, String domain) {
        System.out.println("--- --- --- " + browser.getName() + " --- --- ---");
        Set<Cookie> cookies =  browser.getCookiesForDomain("translate.google.com");
        if (ObjectUtil.isEmpty(cookies)) {
            System.out.println("cookie not found");
            System.out.println("--- --- --- --- --- --- --- --- ---\n");
            return;
        }
        System.out.println("cookie for " + domain);
        cookies.forEach(System.out::println);
        System.out.println("--- --- --- --- --- --- --- --- ---\n");
    }
}
