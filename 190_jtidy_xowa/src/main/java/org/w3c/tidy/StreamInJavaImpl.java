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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


/**
 * StreamIn Implementation using java writers.
 * @author Fabrizio Giustina
 * @version $Revision: 819 $ ($Author: steffenyount $)
 */
public class StreamInJavaImpl implements StreamIn
{

    /**
     * number of characters kept in buffer.
     */
    private static final int CHARBUF_SIZE = 16;

    /**
     * character buffer.
     */
    private int[] charbuf = new int[CHARBUF_SIZE];

    /**
     * actual position in buffer.
     */
    private int bufpos;

    /**
     * Java input stream reader.
     */
    private Reader reader;

    /**
     * has end of stream been reached?
     */
    private boolean endOfStream;

    /**
     * Is char pushed?
     */
    private boolean pushed;

    /**
     * current column number.
     */
    private int curcol;

    /**
     * last column.
     */
    private int lastcol;

    /**
     * current line number.
     */
    private int curline;

    /**
     * tab size in chars.
     */
    private int tabsize;

    private int tabs;

    /**
     * Instantiates a new StreamInJavaImpl.
     * @param stream
     * @param encoding
     * @param tabsize
     * @throws UnsupportedEncodingException
     */
    protected StreamInJavaImpl(InputStream stream, String encoding, int tabsize) throws UnsupportedEncodingException
    {
        reader = new InputStreamReader(stream, encoding);
        this.pushed = false;
        this.tabsize = tabsize;
        this.curline = 1;
        this.curcol = 1;
        this.endOfStream = false;
    }

    /**
     * Instantiates a new StreamInJavaImpl.
     * @param stream
     * @param encoding
     * @param tabsize
     */
    protected StreamInJavaImpl(Reader reader, int tabsize)
    {
        this.reader = reader;
        this.pushed = false;
        this.tabsize = tabsize;
        this.curline = 1;
        this.curcol = 1;
        this.endOfStream = false;
    }

    /**
     * @see org.w3c.tidy.StreamIn#readCharFromStream()
     */
    public int readCharFromStream()
    {
        int c;
        try
        {
            c = reader.read();
            if (c < 0)
            {
                endOfStream = true;
            }

        }
        catch (IOException e)
        {
            // @todo how to handle?
            endOfStream = true;
            return END_OF_STREAM;
        }

        return c;
    }

    /**
     * @see org.w3c.tidy.StreamIn#readChar()
     */
    public int readChar()
    {
        int c;

        if (this.pushed)
        {
            c = this.charbuf[--(this.bufpos)];
            if ((this.bufpos) == 0)
            {
                this.pushed = false;
            }

            if (c == '\n')
            {
                this.curcol = 1;
                this.curline++;
                return c;
            }

            this.curcol++;
            return c;
        }

        this.lastcol = this.curcol;

        if (this.tabs > 0)
        {
            this.curcol++;
            this.tabs--;
            return ' ';
        }

        c = readCharFromStream();

        if (c < 0)
        {
            endOfStream = true;
            return END_OF_STREAM;
        }

        if (c == '\n')
        {
            this.curcol = 1;
            this.curline++;
            return c;
        }
        else if (c == '\r') // \r\n
        {
            c = readCharFromStream();
            if (c != '\n')
            {
                if (c != END_OF_STREAM)
                {
                    ungetChar(c);
                }
                c = '\n';
            }
            this.curcol = 1;
            this.curline++;
            return c;
        }

        if (c == '\t')
        {
            this.tabs = this.tabsize - ((this.curcol - 1) % this.tabsize) - 1;
            this.curcol++;
            c = ' ';
            return c;
        }

        this.curcol++;

        return c;
    }

    /**
     * @see org.w3c.tidy.StreamIn#ungetChar(int)
     */
    public void ungetChar(int c)
    {
        this.pushed = true;
        if (this.bufpos >= CHARBUF_SIZE)
        {
            // pop last element
            System.arraycopy(this.charbuf, 0, this.charbuf, 1, CHARBUF_SIZE - 1);
            this.bufpos--;
        }
        this.charbuf[(this.bufpos)++] = c;

        if (c == '\n')
        {
            --this.curline;
        }

        this.curcol = this.lastcol;
    }

    /**
     * @see org.w3c.tidy.StreamIn#isEndOfStream()
     */
    public boolean isEndOfStream()
    {
        return endOfStream;
    }

    /**
     * Getter for <code>curcol</code>.
     * @return Returns the curcol.
     */
    public int getCurcol()
    {
        return this.curcol;
    }

    /**
     * Getter for <code>curline</code>.
     * @return Returns the curline.
     */
    public int getCurline()
    {
        return this.curline;
    }

    /**
     * @see org.w3c.tidy.StreamIn#setLexer(org.w3c.tidy.Lexer)
     */
    public void setLexer(Lexer lexer)
    {
        // unused in the java implementation
    }

}