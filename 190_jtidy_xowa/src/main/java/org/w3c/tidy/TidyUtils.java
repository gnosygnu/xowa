/*
 *  Java HTML Tidy - JTidy
 *  HTML parser and pretty printer
 *
 *  Copyright (c) 1998-2000 World Wide Web Consortium (Massachusetts
 *  Institute of Technology, Institut National de Recherche en
 *  Informatique et en Automatique, Keio University). All Rights
 *  Reserved.
 *
 *  Contributing Author(s):
 *
 *     Dave Raggett <dsr@w3.org>
 *     Andy Quick <ac.quick@sympatico.ca> (translation to Java)
 *     Gary L Peskin <garyp@firstech.com> (Java development)
 *     Sami Lempinen <sami@lempinen.net> (release management)
 *     Fabrizio Giustina <fgiust at users.sourceforge.net>
 *
 *  The contributing author(s) would like to thank all those who
 *  helped with testing, bug fixes, and patience.  This wouldn't
 *  have been possible without all of you.
 *
 *  COPYRIGHT NOTICE:
 *
 *  This software and documentation is provided "as is," and
 *  the copyright holders and contributing author(s) make no
 *  representations or warranties, express or implied, including
 *  but not limited to, warranties of merchantability or fitness
 *  for any particular purpose or that the use of the software or
 *  documentation will not infringe any third party patents,
 *  copyrights, trademarks or other rights.
 *
 *  The copyright holders and contributing author(s) will not be
 *  liable for any direct, indirect, special or consequential damages
 *  arising out of any use of the software or documentation, even if
 *  advised of the possibility of such damage.
 *
 *  Permission is hereby granted to use, copy, modify, and distribute
 *  this source code, or portions hereof, documentation and executables,
 *  for any purpose, without fee, subject to the following restrictions:
 *
 *  1. The origin of this source code must not be misrepresented.
 *  2. Altered versions must be plainly marked as such and must
 *     not be misrepresented as being the original source.
 *  3. This Copyright notice may not be removed or altered from any
 *     source or altered source distribution.
 *
 *  The copyright holders and contributing author(s) specifically
 *  permit, without fee, and encourage the use of this source code
 *  as a component for supporting the Hypertext Markup Language in
 *  commercial products. If you use this source code in a product,
 *  acknowledgment is not required but would be appreciated.
 *
 */

package org.w3c.tidy;

/**
 * Utility class with handy methods, mainly for String handling or for reproducing c behaviours.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public final class TidyUtils
{

    /**
     * char type: digit.
     */
    private static final short DIGIT = 1;

    /**
     * char type: letter.
     */
    private static final short LETTER = 2;

    /**
     * char type: namechar.
     */
    private static final short NAMECHAR = 4;

    /**
     * char type: whitespace.
     */
    private static final short WHITE = 8;

    /**
     * char type: newline.
     */
    private static final short NEWLINE = 16;

    /**
     * char type: lowercase.
     */
    private static final short LOWERCASE = 32;

    /**
     * char type: uppercase.
     */
    private static final short UPPERCASE = 64;

    /**
     * used to classify chars for lexical purposes.
     */
    private static short[] lexmap = new short[128];

    static
    {
        mapStr("\r\n\f", (short) (NEWLINE | WHITE));
        mapStr(" \t", WHITE);
        mapStr("-.:_", NAMECHAR);
        mapStr("0123456789", (short) (DIGIT | NAMECHAR));
        mapStr("abcdefghijklmnopqrstuvwxyz", (short) (LOWERCASE | LETTER | NAMECHAR));
        mapStr("ABCDEFGHIJKLMNOPQRSTUVWXYZ", (short) (UPPERCASE | LETTER | NAMECHAR));
    }

    /**
     * utility class, don't instantiate.
     */
    private TidyUtils()
    {
        // unused
    }

    /**
     * Converts a int to a boolean.
     * @param value int value
     * @return <code>true</code> if value is != 0
     */
    static boolean toBoolean(int value)
    {
        return value != 0;
    }

    /**
     * convert an int to unsigned (& 0xFF).
     * @param c signed int
     * @return unsigned int
     */
    static int toUnsigned(int c)
    {
        return c & 0xFF;
    }

    /**
     * check if the first String contains the second one.
     * @param s1 full String
     * @param len1 maximum position in String
     * @param s2 String to search for
     * @return true if s1 contains s2 in the range 0-len1
     */
    static boolean wsubstrn(String s1, int len1, String s2)
    {
        int searchIndex = s1.indexOf(s2);
        return searchIndex > -1 && searchIndex <= len1;
    }

    /**
     * check if the first String contains the second one (ignore case).
     * @param s1 full String
     * @param len1 maximum position in String
     * @param s2 String to search for
     * @return true if s1 contains s2 in the range 0-len1
     */
    static boolean wsubstrncase(String s1, int len1, String s2)
    {
        return wsubstrn(s1.toLowerCase(), len1, s2.toLowerCase());
    }

    /**
     * return offset of cc from beginning of s1, -1 if not found.
     * @param s1 String
     * @param len1 maximum offset (values > than lenl are ignored and returned as -1)
     * @param cc character to search for
     * @return index of cc in s1
     */
    static int wstrnchr(String s1, int len1, char cc)
    {
        int indexOf = s1.indexOf(cc);
        if (indexOf < len1)
        {
            return indexOf;
        }

        return -1;
    }

    /**
     * Same as wsubstrn, but without a specified length.
     * @param s1 full String
     * @param s2 String to search for
     * @return <code>true</code> if s2 is found in s2 (case insensitive search)
     */
    static boolean wsubstr(String s1, String s2)
    {
        int i;
        int len1 = s1.length();
        int len2 = s2.length();

        for (i = 0; i <= len1 - len2; ++i)
        {
            if (s2.equalsIgnoreCase(s1.substring(i)))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Is the character a hex digit?
     * @param c char
     * @return <code>true</code> if he given character is a hex digit
     */
    static boolean isxdigit(char c)
    {
        return Character.isDigit(c) || (Character.toLowerCase(c) >= 'a' && Character.toLowerCase(c) <= 'f');
    }

    /**
     * Check if the string valueToCheck is contained in validValues array (case insesitie comparison).
     * @param validValues array of valid values
     * @param valueToCheck value to search for
     * @return <code>true</code> if valueToCheck is found in validValues
     */
    static boolean isInValuesIgnoreCase(String[] validValues, String valueToCheck)
    {
        int len = validValues.length;
        for (int j = 0; j < len; j++)
        {
            if (validValues[j].equalsIgnoreCase(valueToCheck))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if substring s is in p and isn't all in upper case. This is used to check the case of SYSTEM, PUBLIC,
     * DTD and EN.
     * @param s substring
     * @param p full string
     * @param len how many chars to check in p
     * @return true if substring s is in p and isn't all in upper case
     */
    public static boolean findBadSubString(String s, String p, int len)
    {
        int n = s.length();
        int i = 0;
        String ps;

        while (n < len)
        {
            ps = p.substring(i, i + n);
            if (s.equalsIgnoreCase(ps))
            {
                return (!s.equals(ps));
            }

            ++i;
            --len;
        }

        return false;
    }

    /**
     * Is the given char a valid xml letter?
     * @param c char
     * @return <code>true</code> if the char is a valid xml letter
     */
    static boolean isXMLLetter(char c)
    {
        return ((c >= 0x41 && c <= 0x5a)
            || (c >= 0x61 && c <= 0x7a)
            || (c >= 0xc0 && c <= 0xd6)
            || (c >= 0xd8 && c <= 0xf6)
            || (c >= 0xf8 && c <= 0xff)
            || (c >= 0x100 && c <= 0x131)
            || (c >= 0x134 && c <= 0x13e)
            || (c >= 0x141 && c <= 0x148)
            || (c >= 0x14a && c <= 0x17e)
            || (c >= 0x180 && c <= 0x1c3)
            || (c >= 0x1cd && c <= 0x1f0)
            || (c >= 0x1f4 && c <= 0x1f5)
            || (c >= 0x1fa && c <= 0x217)
            || (c >= 0x250 && c <= 0x2a8)
            || (c >= 0x2bb && c <= 0x2c1)
            || c == 0x386
            || (c >= 0x388 && c <= 0x38a)
            || c == 0x38c
            || (c >= 0x38e && c <= 0x3a1)
            || (c >= 0x3a3 && c <= 0x3ce)
            || (c >= 0x3d0 && c <= 0x3d6)
            || c == 0x3da
            || c == 0x3dc
            || c == 0x3de
            || c == 0x3e0
            || (c >= 0x3e2 && c <= 0x3f3)
            || (c >= 0x401 && c <= 0x40c)
            || (c >= 0x40e && c <= 0x44f)
            || (c >= 0x451 && c <= 0x45c)
            || (c >= 0x45e && c <= 0x481)
            || (c >= 0x490 && c <= 0x4c4)
            || (c >= 0x4c7 && c <= 0x4c8)
            || (c >= 0x4cb && c <= 0x4cc)
            || (c >= 0x4d0 && c <= 0x4eb)
            || (c >= 0x4ee && c <= 0x4f5)
            || (c >= 0x4f8 && c <= 0x4f9)
            || (c >= 0x531 && c <= 0x556)
            || c == 0x559
            || (c >= 0x561 && c <= 0x586)
            || (c >= 0x5d0 && c <= 0x5ea)
            || (c >= 0x5f0 && c <= 0x5f2)
            || (c >= 0x621 && c <= 0x63a)
            || (c >= 0x641 && c <= 0x64a)
            || (c >= 0x671 && c <= 0x6b7)
            || (c >= 0x6ba && c <= 0x6be)
            || (c >= 0x6c0 && c <= 0x6ce)
            || (c >= 0x6d0 && c <= 0x6d3)
            || c == 0x6d5
            || (c >= 0x6e5 && c <= 0x6e6)
            || (c >= 0x905 && c <= 0x939)
            || c == 0x93d
            || (c >= 0x958 && c <= 0x961)
            || (c >= 0x985 && c <= 0x98c)
            || (c >= 0x98f && c <= 0x990)
            || (c >= 0x993 && c <= 0x9a8)
            || (c >= 0x9aa && c <= 0x9b0)
            || c == 0x9b2
            || (c >= 0x9b6 && c <= 0x9b9)
            || (c >= 0x9dc && c <= 0x9dd)
            || (c >= 0x9df && c <= 0x9e1)
            || (c >= 0x9f0 && c <= 0x9f1)
            || (c >= 0xa05 && c <= 0xa0a)
            || (c >= 0xa0f && c <= 0xa10)
            || (c >= 0xa13 && c <= 0xa28)
            || (c >= 0xa2a && c <= 0xa30)
            || (c >= 0xa32 && c <= 0xa33)
            || (c >= 0xa35 && c <= 0xa36)
            || (c >= 0xa38 && c <= 0xa39)
            || (c >= 0xa59 && c <= 0xa5c)
            || c == 0xa5e
            || (c >= 0xa72 && c <= 0xa74)
            || (c >= 0xa85 && c <= 0xa8b)
            || c == 0xa8d
            || (c >= 0xa8f && c <= 0xa91)
            || (c >= 0xa93 && c <= 0xaa8)
            || (c >= 0xaaa && c <= 0xab0)
            || (c >= 0xab2 && c <= 0xab3)
            || (c >= 0xab5 && c <= 0xab9)
            || c == 0xabd
            || c == 0xae0
            || (c >= 0xb05 && c <= 0xb0c)
            || (c >= 0xb0f && c <= 0xb10)
            || (c >= 0xb13 && c <= 0xb28)
            || (c >= 0xb2a && c <= 0xb30)
            || (c >= 0xb32 && c <= 0xb33)
            || (c >= 0xb36 && c <= 0xb39)
            || c == 0xb3d
            || (c >= 0xb5c && c <= 0xb5d)
            || (c >= 0xb5f && c <= 0xb61)
            || (c >= 0xb85 && c <= 0xb8a)
            || (c >= 0xb8e && c <= 0xb90)
            || (c >= 0xb92 && c <= 0xb95)
            || (c >= 0xb99 && c <= 0xb9a)
            || c == 0xb9c
            || (c >= 0xb9e && c <= 0xb9f)
            || (c >= 0xba3 && c <= 0xba4)
            || (c >= 0xba8 && c <= 0xbaa)
            || (c >= 0xbae && c <= 0xbb5)
            || (c >= 0xbb7 && c <= 0xbb9)
            || (c >= 0xc05 && c <= 0xc0c)
            || (c >= 0xc0e && c <= 0xc10)
            || (c >= 0xc12 && c <= 0xc28)
            || (c >= 0xc2a && c <= 0xc33)
            || (c >= 0xc35 && c <= 0xc39)
            || (c >= 0xc60 && c <= 0xc61)
            || (c >= 0xc85 && c <= 0xc8c)
            || (c >= 0xc8e && c <= 0xc90)
            || (c >= 0xc92 && c <= 0xca8)
            || (c >= 0xcaa && c <= 0xcb3)
            || (c >= 0xcb5 && c <= 0xcb9)
            || c == 0xcde
            || (c >= 0xce0 && c <= 0xce1)
            || (c >= 0xd05 && c <= 0xd0c)
            || (c >= 0xd0e && c <= 0xd10)
            || (c >= 0xd12 && c <= 0xd28)
            || (c >= 0xd2a && c <= 0xd39)
            || (c >= 0xd60 && c <= 0xd61)
            || (c >= 0xe01 && c <= 0xe2e)
            || c == 0xe30
            || (c >= 0xe32 && c <= 0xe33)
            || (c >= 0xe40 && c <= 0xe45)
            || (c >= 0xe81 && c <= 0xe82)
            || c == 0xe84
            || (c >= 0xe87 && c <= 0xe88)
            || c == 0xe8a
            || c == 0xe8d
            || (c >= 0xe94 && c <= 0xe97)
            || (c >= 0xe99 && c <= 0xe9f)
            || (c >= 0xea1 && c <= 0xea3)
            || c == 0xea5
            || c == 0xea7
            || (c >= 0xeaa && c <= 0xeab)
            || (c >= 0xead && c <= 0xeae)
            || c == 0xeb0
            || (c >= 0xeb2 && c <= 0xeb3)
            || c == 0xebd
            || (c >= 0xec0 && c <= 0xec4)
            || (c >= 0xf40 && c <= 0xf47)
            || (c >= 0xf49 && c <= 0xf69)
            || (c >= 0x10a0 && c <= 0x10c5)
            || (c >= 0x10d0 && c <= 0x10f6)
            || c == 0x1100
            || (c >= 0x1102 && c <= 0x1103)
            || (c >= 0x1105 && c <= 0x1107)
            || c == 0x1109
            || (c >= 0x110b && c <= 0x110c)
            || (c >= 0x110e && c <= 0x1112)
            || c == 0x113c
            || c == 0x113e
            || c == 0x1140
            || c == 0x114c
            || c == 0x114e
            || c == 0x1150
            || (c >= 0x1154 && c <= 0x1155)
            || c == 0x1159
            || (c >= 0x115f && c <= 0x1161)
            || c == 0x1163
            || c == 0x1165
            || c == 0x1167
            || c == 0x1169
            || (c >= 0x116d && c <= 0x116e)
            || (c >= 0x1172 && c <= 0x1173)
            || c == 0x1175
            || c == 0x119e
            || c == 0x11a8
            || c == 0x11ab
            || (c >= 0x11ae && c <= 0x11af)
            || (c >= 0x11b7 && c <= 0x11b8)
            || c == 0x11ba
            || (c >= 0x11bc && c <= 0x11c2)
            || c == 0x11eb
            || c == 0x11f0
            || c == 0x11f9
            || (c >= 0x1e00 && c <= 0x1e9b)
            || (c >= 0x1ea0 && c <= 0x1ef9)
            || (c >= 0x1f00 && c <= 0x1f15)
            || (c >= 0x1f18 && c <= 0x1f1d)
            || (c >= 0x1f20 && c <= 0x1f45)
            || (c >= 0x1f48 && c <= 0x1f4d)
            || (c >= 0x1f50 && c <= 0x1f57)
            || c == 0x1f59
            || c == 0x1f5b
            || c == 0x1f5d
            || (c >= 0x1f5f && c <= 0x1f7d)
            || (c >= 0x1f80 && c <= 0x1fb4)
            || (c >= 0x1fb6 && c <= 0x1fbc)
            || c == 0x1fbe
            || (c >= 0x1fc2 && c <= 0x1fc4)
            || (c >= 0x1fc6 && c <= 0x1fcc)
            || (c >= 0x1fd0 && c <= 0x1fd3)
            || (c >= 0x1fd6 && c <= 0x1fdb)
            || (c >= 0x1fe0 && c <= 0x1fec)
            || (c >= 0x1ff2 && c <= 0x1ff4)
            || (c >= 0x1ff6 && c <= 0x1ffc)
            || c == 0x2126
            || (c >= 0x212a && c <= 0x212b)
            || c == 0x212e
            || (c >= 0x2180 && c <= 0x2182)
            || (c >= 0x3041 && c <= 0x3094)
            || (c >= 0x30a1 && c <= 0x30fa)
            || (c >= 0x3105 && c <= 0x312c)
            || (c >= 0xac00 && c <= 0xd7a3)
            || (c >= 0x4e00 && c <= 0x9fa5)
            || c == 0x3007
            || (c >= 0x3021 && c <= 0x3029)
            || (c >= 0x4e00 && c <= 0x9fa5)
            || c == 0x3007 || (c >= 0x3021 && c <= 0x3029));
    }

    /**
     * Is the given char valid in xml name?
     * @param c char
     * @return <code>true</code> if the char is a valid xml name char
     */
    static boolean isXMLNamechar(char c)
    {
        return (isXMLLetter(c)
            || c == '.'
            || c == '_'
            || c == ':'
            || c == '-'
            || (c >= 0x300 && c <= 0x345)
            || (c >= 0x360 && c <= 0x361)
            || (c >= 0x483 && c <= 0x486)
            || (c >= 0x591 && c <= 0x5a1)
            || (c >= 0x5a3 && c <= 0x5b9)
            || (c >= 0x5bb && c <= 0x5bd)
            || c == 0x5bf
            || (c >= 0x5c1 && c <= 0x5c2)
            || c == 0x5c4
            || (c >= 0x64b && c <= 0x652)
            || c == 0x670
            || (c >= 0x6d6 && c <= 0x6dc)
            || (c >= 0x6dd && c <= 0x6df)
            || (c >= 0x6e0 && c <= 0x6e4)
            || (c >= 0x6e7 && c <= 0x6e8)
            || (c >= 0x6ea && c <= 0x6ed)
            || (c >= 0x901 && c <= 0x903)
            || c == 0x93c
            || (c >= 0x93e && c <= 0x94c)
            || c == 0x94d
            || (c >= 0x951 && c <= 0x954)
            || (c >= 0x962 && c <= 0x963)
            || (c >= 0x981 && c <= 0x983)
            || c == 0x9bc
            || c == 0x9be
            || c == 0x9bf
            || (c >= 0x9c0 && c <= 0x9c4)
            || (c >= 0x9c7 && c <= 0x9c8)
            || (c >= 0x9cb && c <= 0x9cd)
            || c == 0x9d7
            || (c >= 0x9e2 && c <= 0x9e3)
            || c == 0xa02
            || c == 0xa3c
            || c == 0xa3e
            || c == 0xa3f
            || (c >= 0xa40 && c <= 0xa42)
            || (c >= 0xa47 && c <= 0xa48)
            || (c >= 0xa4b && c <= 0xa4d)
            || (c >= 0xa70 && c <= 0xa71)
            || (c >= 0xa81 && c <= 0xa83)
            || c == 0xabc
            || (c >= 0xabe && c <= 0xac5)
            || (c >= 0xac7 && c <= 0xac9)
            || (c >= 0xacb && c <= 0xacd)
            || (c >= 0xb01 && c <= 0xb03)
            || c == 0xb3c
            || (c >= 0xb3e && c <= 0xb43)
            || (c >= 0xb47 && c <= 0xb48)
            || (c >= 0xb4b && c <= 0xb4d)
            || (c >= 0xb56 && c <= 0xb57)
            || (c >= 0xb82 && c <= 0xb83)
            || (c >= 0xbbe && c <= 0xbc2)
            || (c >= 0xbc6 && c <= 0xbc8)
            || (c >= 0xbca && c <= 0xbcd)
            || c == 0xbd7
            || (c >= 0xc01 && c <= 0xc03)
            || (c >= 0xc3e && c <= 0xc44)
            || (c >= 0xc46 && c <= 0xc48)
            || (c >= 0xc4a && c <= 0xc4d)
            || (c >= 0xc55 && c <= 0xc56)
            || (c >= 0xc82 && c <= 0xc83)
            || (c >= 0xcbe && c <= 0xcc4)
            || (c >= 0xcc6 && c <= 0xcc8)
            || (c >= 0xcca && c <= 0xccd)
            || (c >= 0xcd5 && c <= 0xcd6)
            || (c >= 0xd02 && c <= 0xd03)
            || (c >= 0xd3e && c <= 0xd43)
            || (c >= 0xd46 && c <= 0xd48)
            || (c >= 0xd4a && c <= 0xd4d)
            || c == 0xd57
            || c == 0xe31
            || (c >= 0xe34 && c <= 0xe3a)
            || (c >= 0xe47 && c <= 0xe4e)
            || c == 0xeb1
            || (c >= 0xeb4 && c <= 0xeb9)
            || (c >= 0xebb && c <= 0xebc)
            || (c >= 0xec8 && c <= 0xecd)
            || (c >= 0xf18 && c <= 0xf19)
            || c == 0xf35
            || c == 0xf37
            || c == 0xf39
            || c == 0xf3e
            || c == 0xf3f
            || (c >= 0xf71 && c <= 0xf84)
            || (c >= 0xf86 && c <= 0xf8b)
            || (c >= 0xf90 && c <= 0xf95)
            || c == 0xf97
            || (c >= 0xf99 && c <= 0xfad)
            || (c >= 0xfb1 && c <= 0xfb7)
            || c == 0xfb9
            || (c >= 0x20d0 && c <= 0x20dc)
            || c == 0x20e1
            || (c >= 0x302a && c <= 0x302f)
            || c == 0x3099
            || c == 0x309a
            || (c >= 0x30 && c <= 0x39)
            || (c >= 0x660 && c <= 0x669)
            || (c >= 0x6f0 && c <= 0x6f9)
            || (c >= 0x966 && c <= 0x96f)
            || (c >= 0x9e6 && c <= 0x9ef)
            || (c >= 0xa66 && c <= 0xa6f)
            || (c >= 0xae6 && c <= 0xaef)
            || (c >= 0xb66 && c <= 0xb6f)
            || (c >= 0xbe7 && c <= 0xbef)
            || (c >= 0xc66 && c <= 0xc6f)
            || (c >= 0xce6 && c <= 0xcef)
            || (c >= 0xd66 && c <= 0xd6f)
            || (c >= 0xe50 && c <= 0xe59)
            || (c >= 0xed0 && c <= 0xed9)
            || (c >= 0xf20 && c <= 0xf29)
            || c == 0xb7
            || c == 0x2d0
            || c == 0x2d1
            || c == 0x387
            || c == 0x640
            || c == 0xe46
            || c == 0xec6
            || c == 0x3005
            || (c >= 0x3031 && c <= 0x3035)
            || (c >= 0x309d && c <= 0x309e) || (c >= 0x30fc && c <= 0x30fe));
    }

    /**
     * Is the given character a single or double quote?
     * @param c char
     * @return <code>true</code> if c is " or '
     */
    static boolean isQuote(int c)
    {
        return (c == '\'' || c == '\"');
    }

    /**
     * Should always be able convert to/from UTF-8, so encoding exceptions are converted to an Error to avoid adding
     * throws declarations in lots of methods.
     * @param str String
     * @return utf8 bytes
     * @see String#getBytes()
     */
    public static byte[] getBytes(String str)
    {
        try
        {
            return str.getBytes("UTF8");
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            throw new Error("String to UTF-8 conversion failed: " + e.getMessage());
        }
    }

    /**
     * Should always be able convert to/from UTF-8, so encoding exceptions are converted to an Error to avoid adding
     * throws declarations in lots of methods.
     * @param bytes byte array
     * @param offset starting offset in byte array
     * @param length length in byte array starting from offset
     * @return same as <code>new String(bytes, offset, length, "UTF8")</code>
     */
    public static String getString(byte[] bytes, int offset, int length)
    {
        try
        {
            return new String(bytes, offset, length, "UTF8");
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            throw new Error("UTF-8 to string conversion failed: " + e.getMessage());
        }
    }

    /**
     * Return the last char in string. This is useful when trailing quotemark is missing on an attribute
     * @param str String
     * @return last char in String
     */
    public static int lastChar(String str)
    {
        if (str != null && str.length() > 0)
        {
            return str.charAt(str.length() - 1);
        }

        return 0;
    }

    /**
     * Determines if the specified character is whitespace.
     * @param c char
     * @return <code>true</code> if char is whitespace.
     */
    public static boolean isWhite(char c)
    {
        short m = map(c);
        return TidyUtils.toBoolean(m & WHITE);
    }

    /**
     * Is the given char a digit?
     * @param c char
     * @return <code>true</code> if the given char is a digit
     */
    public static boolean isDigit(char c)
    {
        short m;
        m = map(c);
        return TidyUtils.toBoolean(m & DIGIT);
    }

    /**
     * Is the given char a letter?
     * @param c char
     * @return <code>true</code> if the given char is a letter
     */
    public static boolean isLetter(char c)
    {
        short m;
        m = map(c);
        return TidyUtils.toBoolean(m & LETTER);
    }

    /**
     * Is the given char valid in name? (letter, digit or "-", ".", ":", "_")
     * @param c char
     * @return <code>true</code> if char is a name char.
     */
    public static boolean isNamechar(char c)
    {
        short map = map(c);

        return TidyUtils.toBoolean(map & NAMECHAR);
    }

    /**
     * Determines if the specified character is a lowercase character.
     * @param c char
     * @return <code>true</code> if char is lower case.
     */
    public static boolean isLower(char c)
    {
        short map = map(c);

        return TidyUtils.toBoolean(map & LOWERCASE);
    }

    /**
     * Determines if the specified character is a uppercase character.
     * @param c char
     * @return <code>true</code> if char is upper case.
     */
    public static boolean isUpper(char c)
    {
        short map = map(c);

        return TidyUtils.toBoolean(map & UPPERCASE);
    }

    /**
     * Maps the given character to its lowercase equivalent.
     * @param c char
     * @return lowercase char.
     */
    public static char toLower(char c)
    {
        short m = map(c);

        if (TidyUtils.toBoolean(m & UPPERCASE))
        {
            c = (char) (c + 'a' - 'A');
        }

        return c;
    }

    /**
     * Maps the given character to its uppercase equivalent.
     * @param c char
     * @return uppercase char.
     */
    public static char toUpper(char c)
    {
        short m = map(c);

        if (TidyUtils.toBoolean(m & LOWERCASE))
        {
            c = (char) (c + 'A' - 'a');
        }

        return c;
    }

    /**
     * Fold case of a char.
     * @param c char
     * @param tocaps convert to caps
     * @param xmlTags use xml tags? If true no change will be performed
     * @return folded char
     * @todo check the use of xmlTags parameter
     */
    public static char foldCase(char c, boolean tocaps, boolean xmlTags)
    {

        if (!xmlTags)
        {

            if (tocaps)
            {
                if (isLower(c))
                {
                    c = toUpper(c);
                }
            }
            else
            {
                // force to lower case
                if (isUpper(c))
                {
                    c = toLower(c);
                }
            }
        }

        return c;
    }

    /**
     * Classify chars in String and put them in lexmap.
     * @param str String
     * @param code code associated to chars in the String
     */
    private static void mapStr(String str, short code)
    {
        int c;
        for (int i = 0; i < str.length(); i++)
        {
            c = str.charAt(i);
            lexmap[c] |= code;
        }
    }

    /**
     * Returns the constant which defines the classification of char in lexmap.
     * @param c char
     * @return char type
     */
    private static short map(char c)
    {
        return (c < 128 ? lexmap[c] : 0);
    }

    /**
     * Is the given character encoding supported?
     * @param name character encoding name
     * @return <code>true</code> if encoding is supported, false otherwhise.
     */
    public static boolean isCharEncodingSupported(String name)
    {
        name = EncodingNameMapper.toJava(name);
        if (name == null)
        {
            return false;
        }

        try
        {
            "".getBytes(name);
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            return false;
        }
        return true;
    }
}