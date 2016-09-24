package org.lion.oschina;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public String convert2RichText(String str) {
        String format = "<a href=\"%s\">%s</a>";

        String not_parse = str;
        int  startIndex = 0;
        int httpStartIndex = -1;
        int httpEndIndex = 0;
        StringBuilder sb = new StringBuilder();
        while ((httpStartIndex = str.substring(startIndex).indexOf("http://")) != -1) {
            startIndex = httpStartIndex;
            sb.append(str.substring(httpEndIndex-startIndex, startIndex));
            httpEndIndex = str.substring(startIndex).indexOf(" ");
            if (httpEndIndex == -1) {
                String substring = str.substring(httpStartIndex);
                sb.append(String.format(format, substring,substring));
                break;
            } else {
                String substring = str.substring(httpStartIndex, httpEndIndex);
                sb.append(String.format(format, substring,substring));
                startIndex = httpEndIndex;
            }
        }
        return sb.toString();
    }

    @Test
    public void test40() throws Exception {
        System.out.println(convert2RichText("dfadf http://www.baidu.com/link addf http://2016.qq.com/"));
    }
}