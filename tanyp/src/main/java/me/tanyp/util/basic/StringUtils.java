package me.tanyp.util.basic;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() != 0;
    }

    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    public static List<String> split(String strings) {
        return split(strings, ",");
    }

    public static List<String> split(String strings, String delimiter) {
        List<String> ls = new ArrayList<>();
        strings = trim(strings);
        if (isNotEmpty(strings)) {
            String[] stringArray = strings.split(delimiter);
            for (String string : stringArray) {
                string = trim(string);
                if (isNotEmpty(string)) {
                    ls.add(string);
                }
            }
        }
        return ls;
    }

    /**
     * <p>Splits a String by Character type as returned by
     * {@code java.lang.Character.getType(char)}. Groups of contiguous
     * characters of the same type are returned as complete tokens, with the
     * following exception: the character of type
     * {@code Character.UPPERCASE_LETTER}, if any, immediately
     * preceding a token of type {@code Character.LOWERCASE_LETTER}
     * will belong to the following token rather than to the preceding, if any,
     * {@code Character.UPPERCASE_LETTER} token.
     * <pre>
     * StringUtils.splitByCharacterTypeCamelCase(null)         = null
     * StringUtils.splitByCharacterTypeCamelCase("")           = []
     * StringUtils.splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringUtils.splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringUtils.splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
     * StringUtils.splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
     * StringUtils.splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
     * </pre>
     *
     * @param str the String to split, may be {@code null}
     * @return an array of parsed Strings, {@code null} if null String input
     * @since 2.4
     */
    public static String[] splitByCharacterTypeCamelCase(final String str) {
        return splitByCharacterType(str, true);
    }

    private static String[] splitByCharacterType(final String str, final boolean camelCase) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final char[] c = str.toCharArray();
        final List<String> list = new ArrayList<String>();
        int tokenStart = 0;
        int currentType = Character.getType(c[tokenStart]);
        for (int pos = tokenStart + 1; pos < c.length; pos++) {
            final int type = Character.getType(c[pos]);
            if (type == currentType) {
                continue;
            }
            if (camelCase && type == Character.LOWERCASE_LETTER && currentType == Character.UPPERCASE_LETTER) {
                final int newTokenStart = pos - 1;
                if (newTokenStart != tokenStart) {
                    list.add(new String(c, tokenStart, newTokenStart - tokenStart));
                    tokenStart = newTokenStart;
                }
            } else {
                list.add(new String(c, tokenStart, pos - tokenStart));
                tokenStart = pos;
            }
            currentType = type;
        }
        list.add(new String(c, tokenStart, c.length - tokenStart));
        return list.toArray(new String[list.size()]);
    }

    public static String join(Object[] array) {
        return join(array, ",");
    }

    public static String join(Object[] array, String delimiter) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(128);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                continue;
            }
            String str = String.valueOf(array[i]);
            if (StringUtils.isEmpty(str)) {
                continue;
            }
            sb.append(str);
            if (i != array.length - 1 && isNotEmpty(delimiter)) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static boolean equals(String str1, String str2) {
        return (str1 == null ? (str2 == null ? true : false) : (str2 == null ? false : str1.equals(str2)));
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return (str1 == null ? false : str2 == null ? true : str1
                .equalsIgnoreCase(str2));
    }

    public static int indexOf(String str, char searchChar) {
        if (str == null || str.length() == 0)
            return -1;

        return str.indexOf(searchChar);
    }

    public static int indexOf(String str, char searchChar, int startPos) {
        if (str == null || str.length() == 0)
            return -1;

        return str.indexOf(searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null)
            return -1;

        return str.indexOf(searchStr);
    }

    public static int indexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }

        if (searchStr.length() == 0 && startPos >= str.length())
            return str.length();

        return str.indexOf(searchStr, startPos);
    }

    public static int lastIndexOf(String str, char searchChar) {
        if (str == null || str.length() == 0)
            return -1;

        return str.lastIndexOf(searchChar);
    }

    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if (str == null || str.length() == 0)
            return -1;

        return str.lastIndexOf(searchChar, startPos);
    }

    public static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null)
            return -1;

        return str.lastIndexOf(searchStr);
    }

    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null)
            return -1;

        return str.lastIndexOf(searchStr, startPos);
    }

    public static boolean contains(String str, char searchChar) {
        if (str == null || str.length() == 0)
            return false;

        return str.indexOf(searchChar) >= 0;
    }

    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null)
            return false;

        return str.indexOf(searchStr) >= 0;
    }

    public static int indexOfAny(String str, char[] searchChars) {
        if (str == null || str.length() == 0 || searchChars == null
                || searchChars.length == 0)
            return -1;

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            for (int j = 0; j < searchChars.length; ++j)
                if (searchChars[j] == ch)
                    return i;

        }
        return -1;
    }

    public static int indexOfAny(String str, String searchChars) {
        if (str == null || str.length() == 0 || searchChars == null
                || searchChars.length() == 0)
            return -1;

        return indexOfAny(str, searchChars.toCharArray());
    }

    public static int indexOfAnyBut(String str, char[] searchChars) {
        if (str == null || str.length() == 0 || searchChars == null
                || searchChars.length == 0)
            return -1;

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            for (int j = 0; j < searchChars.length; ++j)
                if (searchChars[j] == ch)
                    return i;
        }
        return -1;
    }

    public static int indexOfAnyBut(String str, String searchChars) {
        if (str == null || str.length() == 0 || searchChars == null
                || (searchChars.length() == 0))
            return -1;

        for (int i = 0; i < str.length(); ++i)
            if (searchChars.indexOf(str.charAt(i)) < 0)
                return i;

        return -1;
    }

    public static boolean containsOnly(String str, char[] valid) {
        if ((valid == null) || (str == null))
            return false;

        if (str.length() == 0)
            return true;

        if (valid.length == 0)
            return false;

        return (indexOfAnyBut(str, valid) == -1);
    }

    public static boolean containsOnly(String str, String validChars) {
        if ((str == null) || (validChars == null))
            return false;

        return containsOnly(str, validChars.toCharArray());
    }

    public static boolean containsNone(String str, char[] invalidChars) {
        if ((str == null) || (invalidChars == null))
            return true;

        int strSize = str.length();
        int validSize = invalidChars.length;
        for (int i = 0; i < strSize; ++i) {
            char ch = str.charAt(i);
            for (int j = 0; j < validSize; ++j)
                if (invalidChars[j] == ch)
                    return false;

        }

        return true;
    }

    public static boolean containsNone(String str, String invalidChars) {
        if (str == null || invalidChars == null)
            return true;

        return containsNone(str, invalidChars.toCharArray());
    }

    public static int indexOfAny(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null))
            return -1;

        int sz = searchStrs.length;

        int ret = 2147483647;

        int tmp = 0;
        for (int i = 0; i < sz; ++i) {
            String search = searchStrs[i];
            if (search == null)
                continue;

            tmp = str.indexOf(search);
            if (tmp == -1) {
                continue;
            }

            if (tmp < ret)
                ret = tmp;

        }

        return ret == 2147483647 ? -1 : ret;
    }

    public static int lastIndexOfAny(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null))
            return -1;

        int sz = searchStrs.length;
        int ret = -1;
        int tmp = 0;
        for (int i = 0; i < sz; ++i) {
            String search = searchStrs[i];
            if (search == null)
                continue;

            tmp = str.lastIndexOf(search);
            if (tmp > ret)
                ret = tmp;
        }

        return ret;
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0)
            start = 0;

        if (start > str.length()) {
            return "";
        }

        return str.substring(start);
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0)
            end = str.length() + end;

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return "";
        }

        if (start < 0)
            start = 0;

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    public static String left(String str, int len) {
        if (str == null)
            return null;

        if (len < 0)
            return "";

        if (str.length() <= len)
            return str;

        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (str == null)
            return null;

        if (len < 0)
            return "";

        if (str.length() <= len)
            return str;

        return str.substring(str.length() - len);
    }

    public static String mid(String str, int pos, int len) {
        if (str == null)
            return null;

        if ((len < 0) || (pos > str.length()))
            return "";

        if (pos < 0)
            pos = 0;

        if (str.length() <= pos + len)
            return str.substring(pos);

        return str.substring(pos, pos + len);
    }

    public static String substringBefore(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0))
            return str;

        if (separator.length() == 0)
            return "";

        int pos = str.indexOf(separator);
        if (pos == -1)
            return str;

        return str.substring(0, pos);
    }

    public static String substringAfter(String str, String separator) {
        if ((str == null) || (str.length() == 0))
            return str;

        if (separator == null)
            return "";

        int pos = str.indexOf(separator);
        if (pos == -1)
            return "";

        return str.substring(pos + separator.length());
    }

    public static String substringBeforeLast(String str, String separator) {
        if (str == null || separator == null || str.length() == 0
                || separator.length() == 0)
            return str;

        int pos = str.lastIndexOf(separator);
        if (pos == -1)
            return str;

        return str.substring(0, pos);
    }

    public static String substringAfterLast(String str, String separator) {
        if (str == null || str.length() == 0)
            return str;

        if (separator == null || separator.length() == 0)
            return "";

        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == str.length() - separator.length())
            return "";

        return str.substring(pos + separator.length());
    }

    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag);
    }

    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null)
            return null;

        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1)
                return str.substring(start + open.length(), end);
        }

        return null;
    }

    public static List<Integer> splitIdsToList(String str) {
        return splitIdsToList(str, ",");
    }

    public static List<Integer> splitIdsToList(String str, String regex) {
        List<Integer> ids = new ArrayList<Integer>();
        if (!isEmpty(str)) {
            String[] idsStr = str.split(regex);
            for (String id : idsStr) {
                try {
                    ids.add(Integer.parseInt(id));
                } catch (NumberFormatException ex) {

                }
            }
        }
        return ids;
    }

    public static void main(String[] args) {
        List<Integer> ids = splitIdsToList("");
        System.out.print(ids.toString());
    }
}
