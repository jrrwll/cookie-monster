package org.dreamcat.cmonster.browsers;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jerry Will
 * @version 2024-01-16
 */
public class EdgeBrowser extends ChromeBrowser {

    @Override
    public String getName() {
        return "Edge";
    }

    // todo support linux
    @Override
    public List<String> getCookieDirectories() {
        return Arrays.asList(
                "/AppData/Local/Microsoft/Edge/User Data",
                "/Library/Application Support/Microsoft Edge"
        );
    }
}
