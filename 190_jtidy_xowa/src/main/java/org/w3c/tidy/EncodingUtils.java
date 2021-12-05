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
 * @author Fabrizio Giustina
 * @version $Revision: 622 $ ($Author: fgiust $)
 */
public final class EncodingUtils
{

    /**
     * the big-endian (default) UNICODE BOM.
     */
    public static final int UNICODE_BOM_BE = 0xFEFF;

    /**
     * the default (big-endian) UNICODE BOM.
     */
    public static final int UNICODE_BOM = UNICODE_BOM_BE;

    /**
     * the little-endian UNICODE BOM.
     */
    public static final int UNICODE_BOM_LE = 0xFFFE;

    /**
     * the UTF-8 UNICODE BOM.
     */
    public static final int UNICODE_BOM_UTF8 = 0xEFBBBF;

    /**
     * states for ISO 2022 A document in ISO-2022 based encoding uses some ESC sequences called "designator" to switch
     * character sets. The designators defined and used in ISO-2022-JP are: "ESC" + "(" + ? for ISO646 variants "ESC" +
     * "$" + ? and "ESC" + "$" + "(" + ? for multibyte character sets. State ASCII.
     */
    public static final int FSM_ASCII = 0;

    /**
     * state ESC.
     */
    public static final int FSM_ESC = 1;

    /**
     * state ESCD.
     */
    public static final int FSM_ESCD = 2;

    /**
     * state ESCDP.
     */
    public static final int FSM_ESCDP = 3;

    /**
     * state ESCP.
     */
    public static final int FSM_ESCP = 4;

    /**
     * state NONASCII.
     */
    public static final int FSM_NONASCII = 5;

    /**
     * Max UTF-88 valid char value.
     */
    public static final int MAX_UTF8_FROM_UCS4 = 0x10FFFF;

    /**
     * Max UTF-16 value.
     */
    public static final int MAX_UTF16_FROM_UCS4 = 0x10FFFF;

    /**
     * utf16 low surrogate.
     */
    public static final int LOW_UTF16_SURROGATE = 0xD800;

    /**
     * UTF-16 surrogates begin.
     */
    public static final int UTF16_SURROGATES_BEGIN = 0x10000;

    /**
     * UTF-16 surrogate pair areas: low surrogates begin.
     */
    public static final int UTF16_LOW_SURROGATE_BEGIN = 0xD800;

    /**
     * UTF-16 surrogate pair areas: low surrogates end.
     */
    public static final int UTF16_LOW_SURROGATE_END = 0xDBFF;

    /**
     * UTF-16 surrogate pair areas: high surrogates begin.
     */
    public static final int UTF16_HIGH_SURROGATE_BEGIN = 0xDC00;

    /**
     * UTF-16 surrogate pair areas: high surrogates end.
     */
    public static final int UTF16_HIGH_SURROGATE_END = 0xDFFF;

    /**
     * UTF-16 high surrogate.
     */
    public static final int HIGH_UTF16_SURROGATE = 0xDFFF;

    /**
     * UTF-8 bye swap: invalid char.
     */
    private static final int UTF8_BYTE_SWAP_NOT_A_CHAR = 0xFFFE;

    /**
     * UTF-8 invalid char.
     */
    private static final int UTF8_NOT_A_CHAR = 0xFFFF;

    /**
     * Mapping for Windows Western character set (128-159) to Unicode.
     */
    private static final int[] WIN2UNICODE = {
        0x20AC,
        0x0000,
        0x201A,
        0x0192,
        0x201E,
        0x2026,
        0x2020,
        0x2021,
        0x02C6,
        0x2030,
        0x0160,
        0x2039,
        0x0152,
        0x0000,
        0x017D,
        0x0000,
        0x0000,
        0x2018,
        0x2019,
        0x201C,
        0x201D,
        0x2022,
        0x2013,
        0x2014,
        0x02DC,
        0x2122,
        0x0161,
        0x203A,
        0x0153,
        0x0000,
        0x017E,
        0x0178};

    /**
     * John Love-Jensen contributed this table for mapping MacRoman character set to Unicode.
     */
    private static final int[] MAC2UNICODE = { // modified to only need chars 128-255/U+0080-U+00FF Terry T 19 Aug 01
        // x7F = DEL
        0x00C4,
        0x00C5,
        0x00C7,
        0x00C9,
        0x00D1,
        0x00D6,
        0x00DC,
        0x00E1,
        0x00E0,
        0x00E2,
        0x00E4,
        0x00E3,
        0x00E5,
        0x00E7,
        0x00E9,
        0x00E8,
        0x00EA,
        0x00EB,
        0x00ED,
        0x00EC,
        0x00EE,
        0x00EF,
        0x00F1,
        0x00F3,
        0x00F2,
        0x00F4,
        0x00F6,
        0x00F5,
        0x00FA,
        0x00F9,
        0x00FB,
        0x00FC,
        0x2020,
        0x00B0,
        0x00A2,
        0x00A3,
        0x00A7,
        0x2022,
        0x00B6,
        0x00DF,
        0x00AE,
        0x00A9,
        0x2122,
        0x00B4,
        0x00A8,
        0x2260,
        0x00C6,
        0x00D8,
        0x221E,
        0x00B1,
        0x2264,
        0x2265,
        0x00A5,
        0x00B5,
        0x2202,
        0x2211,
        // =BD U+2126 OHM SIGN
        0x220F,
        0x03C0,
        0x222B,
        0x00AA,
        0x00BA,
        0x03A9,
        0x00E6,
        0x00F8,
        0x00BF,
        0x00A1,
        0x00AC,
        0x221A,
        0x0192,
        0x2248,
        0x2206,
        0x00AB,
        0x00BB,
        0x2026,
        0x00A0,
        0x00C0,
        0x00C3,
        0x00D5,
        0x0152,
        0x0153,
        0x2013,
        0x2014,
        0x201C,
        0x201D,
        0x2018,
        0x2019,
        0x00F7,
        0x25CA,
        // =DB U+00A4 CURRENCY SIGN
        0x00FF,
        0x0178,
        0x2044,
        0x20AC,
        0x2039,
        0x203A,
        0xFB01,
        0xFB02,
        0x2021,
        0x00B7,
        0x201A,
        0x201E,
        0x2030,
        0x00C2,
        0x00CA,
        0x00C1,
        0x00CB,
        0x00C8,
        0x00CD,
        0x00CE,
        0x00CF,
        0x00CC,
        0x00D3,
        0x00D4,
        // xF0 = Apple Logo
        // =F0 U+2665 BLACK HEART SUIT
        0xF8FF,
        0x00D2,
        0x00DA,
        0x00DB,
        0x00D9,
        0x0131,
        0x02C6,
        0x02DC,
        0x00AF,
        0x02D8,
        0x02D9,
        0x02DA,
        0x00B8,
        0x02DD,
        0x02DB,
        0x02C7};

    /**
     * table to map symbol font characters to Unicode; undefined characters are mapped to 0x0000 and characters without
     * any unicode equivalent are mapped to '?'. Is this appropriate?
     */
    private static final int[] SYMBOL2UNICODE = {
        0x0000,
        0x0001,
        0x0002,
        0x0003,
        0x0004,
        0x0005,
        0x0006,
        0x0007,
        0x0008,
        0x0009,
        0x000A,
        0x000B,
        0x000C,
        0x000D,
        0x000E,
        0x000F,

        0x0010,
        0x0011,
        0x0012,
        0x0013,
        0x0014,
        0x0015,
        0x0016,
        0x0017,
        0x0018,
        0x0019,
        0x001A,
        0x001B,
        0x001C,
        0x001D,
        0x001E,
        0x001F,

        0x0020,
        0x0021,
        0x2200,
        0x0023,
        0x2203,
        0x0025,
        0x0026,
        0x220D,
        0x0028,
        0x0029,
        0x2217,
        0x002B,
        0x002C,
        0x2212,
        0x002E,
        0x002F,

        0x0030,
        0x0031,
        0x0032,
        0x0033,
        0x0034,
        0x0035,
        0x0036,
        0x0037,
        0x0038,
        0x0039,
        0x003A,
        0x003B,
        0x003C,
        0x003D,
        0x003E,
        0x003F,

        0x2245,
        0x0391,
        0x0392,
        0x03A7,
        0x0394,
        0x0395,
        0x03A6,
        0x0393,
        0x0397,
        0x0399,
        0x03D1,
        0x039A,
        0x039B,
        0x039C,
        0x039D,
        0x039F,

        0x03A0,
        0x0398,
        0x03A1,
        0x03A3,
        0x03A4,
        0x03A5,
        0x03C2,
        0x03A9,
        0x039E,
        0x03A8,
        0x0396,
        0x005B,
        0x2234,
        0x005D,
        0x22A5,
        0x005F,

        0x00AF,
        0x03B1,
        0x03B2,
        0x03C7,
        0x03B4,
        0x03B5,
        0x03C6,
        0x03B3,
        0x03B7,
        0x03B9,
        0x03D5,
        0x03BA,
        0x03BB,
        0x03BC,
        0x03BD,
        0x03BF,

        0x03C0,
        0x03B8,
        0x03C1,
        0x03C3,
        0x03C4,
        0x03C5,
        0x03D6,
        0x03C9,
        0x03BE,
        0x03C8,
        0x03B6,
        0x007B,
        0x007C,
        0x007D,
        0x223C,
        0x003F,

        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,

        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,
        0x0000,

        0x00A0,
        0x03D2,
        0x2032,
        0x2264,
        0x2044,
        0x221E,
        0x0192,
        0x2663,
        0x2666,
        0x2665,
        0x2660,
        0x2194,
        0x2190,
        0x2191,
        0x2192,
        0x2193,

        0x00B0,
        0x00B1,
        0x2033,
        0x2265,
        0x00D7,
        0x221D,
        0x2202,
        0x00B7,
        0x00F7,
        0x2260,
        0x2261,
        0x2248,
        0x2026,
        0x003F,
        0x003F,
        0x21B5,

        0x2135,
        0x2111,
        0x211C,
        0x2118,
        0x2297,
        0x2295,
        0x2205,
        0x2229,
        0x222A,
        0x2283,
        0x2287,
        0x2284,
        0x2282,
        0x2286,
        0x2208,
        0x2209,

        0x2220,
        0x2207,
        0x00AE,
        0x00A9,
        0x2122,
        0x220F,
        0x221A,
        0x22C5,
        0x00AC,
        0x2227,
        0x2228,
        0x21D4,
        0x21D0,
        0x21D1,
        0x21D2,
        0x21D3,

        0x25CA,
        0x2329,
        0x00AE,
        0x00A9,
        0x2122,
        0x2211,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,

        0x20AC,
        0x232A,
        0x222B,
        0x2320,
        0x003F,
        0x2321,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F,
        0x003F};

    /**
     * Array of valid UTF8 sequences.
     */
    private static final ValidUTF8Sequence[] VALID_UTF8 = {
        new ValidUTF8Sequence(0x0000, 0x007F, 1, new char[]{0x00, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}),
        new ValidUTF8Sequence(0x0080, 0x07FF, 2, new char[]{0xC2, 0xDF, 0x80, 0xBF, 0x00, 0x00, 0x00, 0x00}),
        new ValidUTF8Sequence(0x0800, 0x0FFF, 3, new char[]{0xE0, 0xE0, 0xA0, 0xBF, 0x80, 0xBF, 0x00, 0x00}),
        new ValidUTF8Sequence(0x1000, 0xFFFF, 3, new char[]{0xE1, 0xEF, 0x80, 0xBF, 0x80, 0xBF, 0x00, 0x00}),
        new ValidUTF8Sequence(0x10000, 0x3FFFF, 4, new char[]{0xF0, 0xF0, 0x90, 0xBF, 0x80, 0xBF, 0x80, 0xBF}),
        new ValidUTF8Sequence(0x40000, 0xFFFFF, 4, new char[]{0xF1, 0xF3, 0x80, 0xBF, 0x80, 0xBF, 0x80, 0xBF}),
        new ValidUTF8Sequence(0x100000, 0x10FFFF, 4, new char[]{0xF4, 0xF4, 0x80, 0x8F, 0x80, 0xBF, 0x80, 0xBF})};

    /**
     * number of valid utf8 sequances.
     */
    private static final int NUM_UTF8_SEQUENCES = VALID_UTF8.length;

    /**
     * Offset for utf8 sequences.
     */
    private static final int[] OFFSET_UTF8_SEQUENCES = {0, // 1 byte
        1, // 2 bytes
        2, // 3 bytes
        4, // 4 bytes
        NUM_UTF8_SEQUENCES}; // must be last

    /**
     * don't instantiate.
     */
    private EncodingUtils()
    {
        // unused
    }

    /**
     * Function for conversion from Windows-1252 to Unicode.
     * @param c char to decode
     * @return decoded char
     */
    protected static int decodeWin1252(int c)
    {
        return WIN2UNICODE[c - 128];
    }

    /**
     * Function to convert from MacRoman to Unicode.
     * @param c char to decode
     * @return decoded char
     */
    protected static int decodeMacRoman(int c)
    {
        if (127 < c)
        {
            c = MAC2UNICODE[c - 128];
        }
        return c;
    }

    /**
     * Function to convert from Symbol Font chars to Unicode.
     * @param c char to decode
     * @return decoded char
     */
    static int decodeSymbolFont(int c)
    {
        if (c > 255)
        {
            return c;
        }

        return SYMBOL2UNICODE[c];
    }

    /**
     * Decodes an array of bytes to a char.
     * @param c will contain the decoded char
     * @param firstByte first input byte
     * @param successorBytes array containing successor bytes (can be null if a getter is provided).
     * @param getter callback used to get new bytes if successorBytes doesn't contain enough bytes
     * @param count will contain the number of bytes read
     * @param startInSuccessorBytesArray starting offset for bytes in successorBytes
     * @return <code>true</code> if error
     */
    static boolean decodeUTF8BytesToChar(int[] c, int firstByte, byte[] successorBytes, GetBytes getter, int[] count,
        int startInSuccessorBytesArray)
    {
        byte[] buf = new byte[10];

        int ch = 0;
        int n = 0;
        int i, bytes = 0;
        boolean hasError = false;

        if (successorBytes.length != 0)
        {
            buf = successorBytes;
        }

        // special check if we have been passed an EOF char
        if (firstByte == StreamIn.END_OF_STREAM) //uint
        {
            // at present
            c[0] = firstByte;
            count[0] = 1;
            return false;
        }

        ch = TidyUtils.toUnsigned(firstByte); // first byte is passed in separately

        if (ch <= 0x7F) // 0XXX XXXX one byte
        {
            n = ch;
            bytes = 1;
        }
        else if ((ch & 0xE0) == 0xC0) /* 110X XXXX two bytes */
        {
            n = ch & 31;
            bytes = 2;
        }
        else if ((ch & 0xF0) == 0xE0) /* 1110 XXXX three bytes */
        {
            n = ch & 15;
            bytes = 3;
        }
        else if ((ch & 0xF8) == 0xF0) /* 1111 0XXX four bytes */
        {
            n = ch & 7;
            bytes = 4;
        }
        else if ((ch & 0xFC) == 0xF8) /* 1111 10XX five bytes */
        {
            n = ch & 3;
            bytes = 5;
            hasError = true;
        }
        else if ((ch & 0xFE) == 0xFC) /* 1111 110X six bytes */
        {
            n = ch & 1;
            bytes = 6;
            hasError = true;
        }
        else
        {
            // not a valid first byte of a UTF-8 sequence
            n = ch;
            bytes = 1;
            hasError = true;
        }

        for (i = 1; i < bytes; ++i)
        {
            int[] tempCount = new int[1]; // no. of additional bytes to get

            // successor bytes should have the form 10XX XXXX
            if (getter != null && (bytes - i > 0))
            {
                tempCount[0] = 1; // to simplify things, get 1 byte at a time
                int[] buftocopy = new int[]{buf[startInSuccessorBytesArray + i - 1]};

                getter.doGet(buftocopy, tempCount, false);
                //readRawBytesFromStream(buftocopy, tempCount, false);
                if (tempCount[0] <= 0) // EOF
                {
                    hasError = true;
                    bytes = i;
                    break;
                }
            }

            if ((buf[startInSuccessorBytesArray + i - 1] & 0xC0) != 0x80)
            {
                // illegal successor byte value
                hasError = true;
                bytes = i;
                if (getter != null)
                {
                    int[] buftocopy = new int[]{buf[startInSuccessorBytesArray + i - 1]};
                    tempCount[0] = 1; // to simplify things, unget 1 byte at a time
                    getter.doGet(buftocopy, tempCount, true);
                }
                break;
            }

            n = (n << 6) | (buf[startInSuccessorBytesArray + i - 1] & 0x3F);
        }

        if (!hasError && ((n == UTF8_BYTE_SWAP_NOT_A_CHAR) || (n == UTF8_NOT_A_CHAR)))
        {
            hasError = true;
        }

        if (!hasError && (n > MAX_UTF8_FROM_UCS4))
        {
            hasError = true;
        }

        // XOWA: don't fail on surrogate characters DATE:2014-08-27
//        if (!hasError && (n >= UTF16_LOW_SURROGATE_BEGIN) && (n <= UTF16_HIGH_SURROGATE_END))
//        {
//            // unpaired surrogates not allowed
//            hasError = true;
//        }

        if (!hasError)
        {
            int lo = OFFSET_UTF8_SEQUENCES[bytes - 1];
            int hi = OFFSET_UTF8_SEQUENCES[bytes] - 1;

            // check for overlong sequences
            if ((n < VALID_UTF8[lo].lowChar) || (n > VALID_UTF8[hi].highChar))
            {
                hasError = true;
            }
            else
            {
                hasError = true; // assume error until proven otherwise

                for (i = lo; i <= hi; i++)
                {
                    int tempCount;
                    char theByte; //unsigned

                    for (tempCount = 0; tempCount < bytes; tempCount++)
                    {
                        if (!TidyUtils.toBoolean(tempCount))
                        {
                            theByte = (char) firstByte;
                        }
                        else
                        {
                            theByte = (char) buf[startInSuccessorBytesArray + tempCount - 1];
                        }
                        if ((theByte >= VALID_UTF8[i].validBytes[(tempCount * 2)])
                            && (theByte <= VALID_UTF8[i].validBytes[(tempCount * 2) + 1]))
                        {
                            hasError = false;
                        }
                        if (hasError)
                        {
                            break;
                        }
                    }
                }
            }
        }

        count[0] = bytes;

        c[0] = n;

        // n = 0xFFFD;
        // replacement char - do this in the caller
        return hasError;

    }

    /**
     * Encode a char to an array of bytes.
     * @param c char to encode
     * @param encodebuf will contain the decoded bytes
     * @param putter if not null it will be called to write bytes to out
     * @param count number of bytes written
     * @return <code>false</code>= ok, <code>true</code>= error
     */
    static boolean encodeCharToUTF8Bytes(int c, byte[] encodebuf, PutBytes putter, int[] count)
    {
        int bytes = 0;

        byte[] buf = new byte[10];

        if (encodebuf != null)
        {
            buf = encodebuf;
        }

        boolean hasError = false;

        if (c <= 0x7F) // 0XXX XXXX one byte
        {
            buf[0] = (byte) c;
            bytes = 1;
        }
        else if (c <= 0x7FF) // 110X XXXX two bytes
        {
            buf[0] = (byte) (0xC0 | (c >> 6));
            buf[1] = (byte) (0x80 | (c & 0x3F));
            bytes = 2;
        }
        else if (c <= 0xFFFF) // 1110 XXXX three bytes
        {
            buf[0] = (byte) (0xE0 | (c >> 12));
            buf[1] = (byte) (0x80 | ((c >> 6) & 0x3F));
            buf[2] = (byte) (0x80 | (c & 0x3F));
            bytes = 3;
            if ((c == UTF8_BYTE_SWAP_NOT_A_CHAR) || (c == UTF8_NOT_A_CHAR))
            {
                hasError = true;
            }
            else if ((c >= UTF16_LOW_SURROGATE_BEGIN) && (c <= UTF16_HIGH_SURROGATE_END))
            {
                // unpaired surrogates not allowed
//                hasError = true;
            }
        }
        else if (c <= 0x1FFFFF) // 1111 0XXX four bytes
        {
            buf[0] = (byte) (0xF0 | (c >> 18));
            buf[1] = (byte) (0x80 | ((c >> 12) & 0x3F));
            buf[2] = (byte) (0x80 | ((c >> 6) & 0x3F));
            buf[3] = (byte) (0x80 | (c & 0x3F));
            bytes = 4;
            if (c > MAX_UTF8_FROM_UCS4)
            {
                hasError = true;
            }
        }
        else if (c <= 0x3FFFFFF) // 1111 10XX five bytes
        {
            buf[0] = (byte) (0xF8 | (c >> 24));
            buf[1] = (byte) (0x80 | (c >> 18));
            buf[2] = (byte) (0x80 | ((c >> 12) & 0x3F));
            buf[3] = (byte) (0x80 | ((c >> 6) & 0x3F));
            buf[4] = (byte) (0x80 | (c & 0x3F));
            bytes = 5;
            hasError = true;
        }
        else if (c <= 0x7FFFFFFF) // 1111 110X six bytes
        {
            buf[0] = (byte) (0xFC | (c >> 30));
            buf[1] = (byte) (0x80 | ((c >> 24) & 0x3F));
            buf[2] = (byte) (0x80 | ((c >> 18) & 0x3F));
            buf[3] = (byte) (0x80 | ((c >> 12) & 0x3F));
            buf[4] = (byte) (0x80 | ((c >> 6) & 0x3F));
            buf[5] = (byte) (0x80 | (c & 0x3F));
            bytes = 6;
            hasError = true;
        }
        else
        {
            hasError = true;
        }

        if (!hasError && putter != null) // don't output invalid UTF-8 byte sequence to a stream
        {
            int[] tempCount = new int[]{bytes};
            putter.doPut(buf, tempCount);

            if (tempCount[0] < bytes)
            {
                hasError = true;
            }
        }

        count[0] = bytes;
        return hasError;
    }

    /**
     * Getter callback: called to retrieve 1 or more additional UTF-8 bytes. The Getter callback can also unget if
     * necessary to re-synchronize the input stream.
     */
    static interface GetBytes
    {

        /**
         * Get one or more byte.
         * @param buf will contain the bytes.
         * @param count number of bytes actually stored in "buf". &lt;= 0 if error or EOF
         * @param unget unget bytes?
         */
        void doGet(int[] buf, int[] count, boolean unget);
    }

    /**
     * Putter callbacks: called to store 1 or more additional UTF-8 bytes.
     */
    static interface PutBytes
    {

        /**
         * Store one or more byte.
         * @param buf will contain the bytes.
         * @param count number of bytes actually stored in "buf". &lt;= 0 if error or EOF
         */
        void doPut(byte[] buf, int[] count);
    }
}
