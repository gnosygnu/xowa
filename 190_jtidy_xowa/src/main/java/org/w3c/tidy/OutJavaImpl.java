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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;


/**
 * Output implementation using java writers.
 * @author Fabrizio Giustina
 * @version $Revision: 807 $ ($Author: fgiust $)
 */
public class OutJavaImpl implements Out
{

    /**
     * Java input stream writer.
     */
    private Writer writer;

    /**
     * Newline string.
     */
    private char[] newline;

    /**
     * Constructor.
     * @param configuration actual configuration instance (needed for newline configuration)
     * @param encoding encoding name
     * @param out output stream
     * @throws UnsupportedEncodingException if the undelining OutputStreamWriter doesn't support the rquested encoding.
     */
    protected OutJavaImpl(Configuration configuration, String encoding, OutputStream out)
        throws UnsupportedEncodingException
    {
        this.writer = new OutputStreamWriter(out, encoding);
        this.newline = configuration.newline;
    }

    /**
     * Constructor.
     * @param configuration actual configuration instance (needed for newline configuration)
     * @param out Writer
     */
    protected OutJavaImpl(Configuration configuration, Writer out)
    {
        this.writer = out;
        this.newline = configuration.newline;
    }

    /**
     * @see org.w3c.tidy.Out#outc(int)
     */
    public void outc(int c)
    {
        try
        {
            writer.write(c);
        }
        catch (IOException e)
        {
            // @todo throws exception
            System.err.println("OutJavaImpl.outc: " + e.getMessage());
        }
    }

    /**
     * @see org.w3c.tidy.Out#outc(byte)
     */
    public void outc(byte c)
    {
        try
        {
            writer.write(c);
        }
        catch (IOException e)
        {
            // @todo throws exception
            System.err.println("OutJavaImpl.outc: " + e.getMessage());
        }
    }

    /**
     * @see org.w3c.tidy.Out#newline()
     */
    public void newline()
    {
        try
        {
            writer.write(this.newline);
        }
        catch (IOException e)
        {
            // @todo throws exception
            System.err.println("OutJavaImpl.newline: " + e.getMessage());
        }
    }

    /**
     * @see org.w3c.tidy.Out#flush()
     */
    public void flush()
    {
        try
        {
            writer.flush();
        }
        catch (IOException e)
        {
            System.err.println("OutJavaImpl.flush: " + e.getMessage());
        }
    }

}
