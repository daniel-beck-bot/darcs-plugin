/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <ich@weltraumschaf.de> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 */

package org.jenkinsci.plugins.darcs;

import junit.framework.TestCase;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public class DarcsChangeLogParserTest extends TestCase {

    public DarcsChangeLogParserTest(String name) {
        super(name);
    }

    public void testParse() {
        DarcsChangeLogParser sut  = new DarcsChangeLogParser();
        DarcsChangeSetList   list = null;
                
        try {
            URL resource = getClass().getResource("/changes.xml");
            list = sut.parse(null, new File(resource.toURI()));
        } catch (Exception e) {
            fail(e.toString());
        }

        if (null == list) {
            fail("list must not be null!");
        }
        
        assertEquals(8, list.size());
        List<DarcsChangeSet> logs = list.getLogs();
        assertEquals(8, logs.size());

        assertPatch(logs.get(0), false, new HashMap<String, String>() {{
            put("plainAuthor", "ich@weltraumschaf.de");
            put("name", "Implemented toString()");
            put("date", "20110214203531");
            put("localDate", "Mon Feb 14 21:35:31 CET 2011");
            put("hash", "20110214203531-7677a-1b935a82ba6408ffa9add3642cb52f233ff4ef54.gz");
            put("comment", "Ignore-this: 7c0271b552e03728baa7d4f33cb545f9");
        }});
        assertPatch(logs.get(1), false, new HashMap<String, String>() {{
            put("plainAuthor", "ich@weltraumschaf.de");
            put("name", "Implemented value in class Foo");
            put("date", "20110214203417");
            put("localDate", "Mon Feb 14 21:34:17 CET 2011");
            put("hash", "20110214203417-7677a-261f33e2608d68f088f15b077f7dcde2cc18a4b7.gz");
            put("comment", "Ignore-this: 79225cd08e4f7ec7dfc7a6cb4e7f5948");
        }});
        assertPatch(logs.get(2), false, new HashMap<String, String>() {{
            put("plainAuthor", "ich@weltraumschaf.de");
            put("name", "Implemented value in class Baz");
            put("date", "20110214203402");
            put("localDate", "Mon Feb 14 21:34:02 CET 2011");
            put("hash", "20110214203402-7677a-5eb558d8bd3df5b8edfa005479d8ff1e8139abe0.gz");
            put("comment", "Ignore-this: fd9e1a81cc792fd826a128794e92ba64");
        }});
        assertPatch(logs.get(3), false, new HashMap<String, String>() {{
            put("plainAuthor", "ich@weltraumschaf.de");
            put("name", "Implemented value in class Bar");
            put("date", "20110214203334");
            put("localDate", "Mon Feb 14 21:33:34 CET 2011");
            put("hash", "20110214203334-7677a-3f9e4a67068618fcfde454c0c097d1f8b96301df.gz");
            put("comment", "Ignore-this: 40e46e42b5023572d7e45607df47cab1");
        }});
        assertPatch(logs.get(4), false, new HashMap<String, String>() {{
            put("plainAuthor", "ich@weltraumschaf.de");
            put("name", "Implemented class Foo");
            put("date", "20110214201649");
            put("localDate", "Mon Feb 14 21:16:49 CET 2011");
            put("hash", "20110214201649-7677a-9c3c62c42467fe20e75a9ab62e52441ef7cdc8ba.gz");
            put("comment", "Ignore-this: 85d0cdf2679dfcab72469629d6a80945");
        }});
    }

    private void assertPatch(DarcsChangeSet cs, boolean isInverted, Map<String, String> expected) {
        assertEquals(expected.get("plainAuthor"), cs.getPlainAuthor());
        assertEquals(expected.get("name"), cs.getName());
        assertEquals(expected.get("comment"), cs.getComment());
        assertEquals(expected.get("date"), cs.getDate());
        assertEquals(expected.get("localDate"), cs.getLocalDate());
        assertEquals(expected.get("hash"), cs.getHash());
        assertEquals(isInverted, cs.isInverted());
    }
}
