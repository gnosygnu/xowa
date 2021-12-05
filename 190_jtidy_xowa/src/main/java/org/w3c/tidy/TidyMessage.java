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
 * Message sent to listeners for validation errors/warnings and info.
 * @see Tidy#setMessageListener(TidyMessageListener)
 * @author Fabrizio Giustina
 * @version $Revision: 630 $ ($Author: fgiust $)
 */
public final class TidyMessage
{

    /**
     * Line in the source file (can be 0 if the message is not related to a particular line, such as a summary message).
     */
    private int line;

    /**
     * Column in the source file (can be 0 if the message is not related to a particular column, such as a summary
     * message).
     */
    private int column;

    /**
     * Level for this message. Can be TidyMessage.Level.SUMMARY | TidyMessage.Level.INFO | TidyMessage.Level.WARNING |
     * TidyMessage.Level.ERROR.
     */
    private Level level;

    /**
     * Formatted text for this message.
     */
    private String message;

    /**
     * Tidy internal error code.
     */
    private int errorCode;

    /**
     * Instantiates a new message.
     * @param errorCode Tidy internal error code.
     * @param line Line number in the source file
     * @param column Column number in the source file
     * @param level severity
     * @param message message text
     */
    public TidyMessage(int errorCode, int line, int column, Level level, String message)
    {
        this.errorCode = errorCode;
        this.line = line;
        this.column = column;
        this.level = level;
        this.message = message;
    }

    /**
     * Getter for <code>errorCode</code>.
     * @return Returns the errorCode.
     */
    public int getErrorCode()
    {
        return this.errorCode;
    }

    /**
     * Getter for <code>column</code>.
     * @return Returns the column.
     */
    public int getColumn()
    {
        return this.column;
    }

    /**
     * Getter for <code>level</code>.
     * @return Returns the level.
     */
    public Level getLevel()
    {
        return this.level;
    }

    /**
     * Getter for <code>line</code>.
     * @return Returns the line.
     */
    public int getLine()
    {
        return this.line;
    }

    /**
     * Getter for <code>message</code>.
     * @return Returns the message.
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Message severity enumeration.
     * @author fgiust
     * @version $Revision: 630 $ ($Author: fgiust $)
     */
    public static final class Level implements Comparable
    {

        /**
         * level = summary (0).
         */
        public static final Level SUMMARY = new Level(0);

        /**
         * level = info (1).
         */
        public static final Level INFO = new Level(1);

        /**
         * level = warning (2).
         */
        public static final Level WARNING = new Level(2);

        /**
         * level = error (3).
         */
        public static final Level ERROR = new Level(3);

        /**
         * short value for this level.
         */
        private short code;

        /**
         * Instantiates a new message with the given code.
         * @param code int value for this level
         */
        private Level(int code)
        {
            this.code = (short) code;
        }

        /**
         * Returns the int value for this level.
         * @return int value for this level
         */
        public short getCode()
        {
            return this.code;
        }

        /**
         * Returns the Level instance corresponding to the given int value.
         * @param code int value for the level
         * @return Level instance
         */
        public static Level fromCode(int code)
        {
            switch (code)
            {
                case 0 :
                    return SUMMARY;
                case 1 :
                    return INFO;
                case 2 :
                    return WARNING;
                case 3 :
                    return ERROR;

                default :
                    return null;
            }
        }

        /**
         * @see java.lang.Comparable#compareTo(Object)
         */
        public int compareTo(Object object)
        {
            return this.code - ((Level) object).code;
        }

        /**
         * @see java.lang.Object#equals(Object)
         */
        public boolean equals(Object object)
        {
            if (!(object instanceof Level))
            {
                return false;
            }
            return this.code == ((Level) object).code;
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString()
        {
            switch (code)
            {
                case 0 :
                    return "SUMMARY";
                case 1 :
                    return "INFO";
                case 2 :
                    return "WARNING";
                case 3 :
                    return "ERROR";

                default :
                    // should not happen
                    return "?";
            }
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        public int hashCode()
        {
            // new instances should not be created
            return super.hashCode();
        }
    }

}