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

import java.util.Hashtable;
import java.util.Map;


/**
 * HTML attribute hash table.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 486 $ ($Author: fgiust $)
 */
public class AttributeTable
{

    /**
     * href attribute.
     */
    protected static Attribute attrHref;

    /**
     * src attribute.
     */
    protected static Attribute attrSrc;

    /**
     * id attribute.
     */
    protected static Attribute attrId;

    /**
     * name attribute.
     */
    protected static Attribute attrName;

    /**
     * summary attribute.
     */
    protected static Attribute attrSummary;

    /**
     * alt attribute.
     */
    protected static Attribute attrAlt;

    /**
     * logdesc attribute.
     */
    protected static Attribute attrLongdesc;

    /**
     * usemap attribute.
     */
    protected static Attribute attrUsemap;

    /**
     * ismap attribute.
     */
    protected static Attribute attrIsmap;

    /**
     * language attribute.
     */
    protected static Attribute attrLanguage;

    /**
     * type attribute.
     */
    protected static Attribute attrType;

    /**
     * title attribute.
     */
    protected static Attribute attrTitle;

    /**
     * xmlns attribute.
     */
    protected static Attribute attrXmlns;

    /**
     * value attribute.
     */
    protected static Attribute attrValue;

    /**
     * content attribute.
     */
    protected static Attribute attrContent;

    /**
     * datafld attribute.
     */
    protected static Attribute attrDatafld;

    /**
     * width attribute.
     */
    protected static Attribute attrWidth;

    /**
     * height attribute.
     */
    protected static Attribute attrHeight;

    /**
     * attribute table instance.
     */
    private static AttributeTable defaultAttributeTable;

    /**
     * all the known attributes.
     */
    private static final Attribute[] ATTRS = {
        new Attribute("abbr", Dict.VERS_HTML40, AttrCheckImpl.TEXT),
        new Attribute("accept-charset", Dict.VERS_HTML40, AttrCheckImpl.CHARSET),
        new Attribute("accept", Dict.VERS_ALL, AttrCheckImpl.TYPE),
        new Attribute("accesskey", Dict.VERS_HTML40, AttrCheckImpl.CHARACTER),
        new Attribute("action", Dict.VERS_ALL, AttrCheckImpl.URL),
        new Attribute("add_date", Dict.VERS_NETSCAPE, AttrCheckImpl.TEXT), // A
        new Attribute("align", Dict.VERS_ALL, AttrCheckImpl.ALIGN), // set varies with element
        new Attribute("alink", Dict.VERS_LOOSE, AttrCheckImpl.COLOR),
        new Attribute("alt", Dict.VERS_ALL, AttrCheckImpl.TEXT),
        new Attribute("archive", Dict.VERS_HTML40, AttrCheckImpl.URLS), // space or comma separated list
        new Attribute("axis", Dict.VERS_HTML40, AttrCheckImpl.TEXT),
        new Attribute("background", Dict.VERS_LOOSE, AttrCheckImpl.URL),
        new Attribute("bgcolor", Dict.VERS_LOOSE, AttrCheckImpl.COLOR),
        new Attribute("bgproperties", Dict.VERS_PROPRIETARY, AttrCheckImpl.TEXT), // BODY "fixed" fixes background
        new Attribute("border", Dict.VERS_ALL, AttrCheckImpl.BOOL), // like LENGTH + "border"
        new Attribute("bordercolor", Dict.VERS_MICROSOFT, AttrCheckImpl.COLOR), // used on TABLE
        new Attribute("bottommargin", Dict.VERS_MICROSOFT, AttrCheckImpl.NUMBER), // used on BODY
        new Attribute("cellpadding", Dict.VERS_FROM32, AttrCheckImpl.LENGTH), // % or pixel values
        new Attribute("cellspacing", Dict.VERS_FROM32, AttrCheckImpl.LENGTH),
        new Attribute("char", Dict.VERS_HTML40, AttrCheckImpl.CHARACTER),
        new Attribute("charoff", Dict.VERS_HTML40, AttrCheckImpl.LENGTH),
        new Attribute("charset", Dict.VERS_HTML40, AttrCheckImpl.CHARSET),
        new Attribute("checked", Dict.VERS_ALL, AttrCheckImpl.BOOL), // i.e. "checked" or absent
        new Attribute("cite", Dict.VERS_HTML40, AttrCheckImpl.URL),
        new Attribute("class", Dict.VERS_HTML40, AttrCheckImpl.TEXT),
        new Attribute("classid", Dict.VERS_HTML40, AttrCheckImpl.URL),
        new Attribute("clear", Dict.VERS_LOOSE, AttrCheckImpl.CLEAR), // BR: left, right, all
        new Attribute("code", Dict.VERS_LOOSE, AttrCheckImpl.TEXT), // APPLET
        new Attribute("codebase", Dict.VERS_HTML40, AttrCheckImpl.URL), // OBJECT
        new Attribute("codetype", Dict.VERS_HTML40, AttrCheckImpl.TYPE), // OBJECT
        new Attribute("color", Dict.VERS_LOOSE, AttrCheckImpl.COLOR), // BASEFONT, FONT
        new Attribute("cols", Dict.VERS_IFRAME, AttrCheckImpl.COLS), // TABLE & FRAMESET
        new Attribute("colspan", Dict.VERS_FROM32, AttrCheckImpl.NUMBER),
        new Attribute("compact", Dict.VERS_ALL, AttrCheckImpl.BOOL), // lists
        new Attribute("content", Dict.VERS_ALL, AttrCheckImpl.TEXT), // META
        new Attribute("coords", Dict.VERS_FROM32, AttrCheckImpl.COORDS), // AREA, A
        new Attribute("data", Dict.VERS_HTML40, AttrCheckImpl.URL), // OBJECT
        new Attribute("datafld", Dict.VERS_MICROSOFT, AttrCheckImpl.TEXT), // used on DIV, IMG
        new Attribute("dataformatas", Dict.VERS_MICROSOFT, AttrCheckImpl.TEXT), // used on DIV, IMG
        new Attribute("datapagesize", Dict.VERS_MICROSOFT, AttrCheckImpl.NUMBER), // used on DIV, IMG
        new Attribute("datasrc", Dict.VERS_MICROSOFT, AttrCheckImpl.URL), // used on TABLE
        new Attribute("datetime", Dict.VERS_HTML40, AttrCheckImpl.DATE), // INS, DEL
        new Attribute("declare", Dict.VERS_HTML40, AttrCheckImpl.BOOL), // OBJECT
        new Attribute("defer", Dict.VERS_HTML40, AttrCheckImpl.BOOL), // SCRIPT
        new Attribute("dir", Dict.VERS_HTML40, AttrCheckImpl.TEXTDIR), // ltr or rtl
        new Attribute("disabled", Dict.VERS_HTML40, AttrCheckImpl.BOOL), // form fields
        new Attribute("enctype", Dict.VERS_ALL, AttrCheckImpl.TYPE), // FORM
        new Attribute("face", Dict.VERS_LOOSE, AttrCheckImpl.TEXT), // BASEFONT, FONT
        new Attribute("for", Dict.VERS_HTML40, AttrCheckImpl.IDREF), // LABEL
        new Attribute("frame", Dict.VERS_HTML40, AttrCheckImpl.TFRAME), // TABLE
        new Attribute("frameborder", (short) (Dict.VERS_FRAMESET | Dict.VERS_IFRAME), AttrCheckImpl.FBORDER), // 0 or 1
        new Attribute("framespacing", Dict.VERS_PROPRIETARY, AttrCheckImpl.NUMBER), // pixel value
        new Attribute("gridx", Dict.VERS_PROPRIETARY, AttrCheckImpl.NUMBER), // TABLE Adobe golive
        new Attribute("gridy", Dict.VERS_PROPRIETARY, AttrCheckImpl.NUMBER), // TABLE Adobe golive
        new Attribute("headers", Dict.VERS_HTML40, AttrCheckImpl.IDREF), // table cells
        new Attribute("height", Dict.VERS_ALL, AttrCheckImpl.LENGTH), // pixels only for TH/TD
        new Attribute("href", Dict.VERS_ALL, AttrCheckImpl.URL), // A, AREA, LINK and BASE
        new Attribute("hreflang", Dict.VERS_HTML40, AttrCheckImpl.LANG), // A, LINK
        new Attribute("hspace", Dict.VERS_ALL, AttrCheckImpl.NUMBER), // APPLET, IMG, OBJECT
        new Attribute("http-equiv", Dict.VERS_ALL, AttrCheckImpl.TEXT), // META
        new Attribute("id", Dict.VERS_HTML40, AttrCheckImpl.ID),
        new Attribute("ismap", Dict.VERS_ALL, AttrCheckImpl.BOOL), // IMG
        new Attribute("label", Dict.VERS_HTML40, AttrCheckImpl.TEXT), // OPT, OPTGROUP
        new Attribute("lang", Dict.VERS_HTML40, AttrCheckImpl.LANG),
        new Attribute("language", Dict.VERS_LOOSE, AttrCheckImpl.TEXT), // SCRIPT
        new Attribute("last_modified", Dict.VERS_NETSCAPE, AttrCheckImpl.TEXT), // A
        new Attribute("last_visit", Dict.VERS_NETSCAPE, AttrCheckImpl.TEXT), // A
        new Attribute("leftmargin", Dict.VERS_MICROSOFT, AttrCheckImpl.NUMBER), // used on BODY
        new Attribute("link", Dict.VERS_LOOSE, AttrCheckImpl.COLOR), // BODY
        new Attribute("longdesc", Dict.VERS_HTML40, AttrCheckImpl.URL), // IMG
        new Attribute("lowsrc", Dict.VERS_PROPRIETARY, AttrCheckImpl.URL), // IMG
        new Attribute("marginheight", Dict.VERS_IFRAME, AttrCheckImpl.NUMBER), // FRAME, IFRAME, BODY
        new Attribute("marginwidth", Dict.VERS_IFRAME, AttrCheckImpl.NUMBER), // ditto
        new Attribute("maxlength", Dict.VERS_ALL, AttrCheckImpl.NUMBER), // INPUT
        new Attribute("media", Dict.VERS_HTML40, AttrCheckImpl.MEDIA), // STYLE, LINK
        new Attribute("method", Dict.VERS_ALL, AttrCheckImpl.FSUBMIT), // FORM: get or post
        new Attribute("multiple", Dict.VERS_ALL, AttrCheckImpl.BOOL), // SELECT
        new Attribute("name", Dict.VERS_ALL, AttrCheckImpl.NAME),
        new Attribute("nohref", Dict.VERS_FROM32, AttrCheckImpl.BOOL), // AREA
        new Attribute("noresize", Dict.VERS_FRAMESET, AttrCheckImpl.BOOL), // FRAME
        new Attribute("noshade", Dict.VERS_LOOSE, AttrCheckImpl.BOOL), // HR
        new Attribute("nowrap", Dict.VERS_LOOSE, AttrCheckImpl.BOOL), // table cells
        new Attribute("object", Dict.VERS_HTML40_LOOSE, AttrCheckImpl.TEXT), // APPLET
        new Attribute("onblur", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onchange", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onclick", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("ondblclick", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onkeydown", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onkeypress", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onkeyup", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onload", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onmousedown", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onmousemove", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onmouseout", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onmouseover", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onmouseup", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onsubmit", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onreset", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onselect", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onunload", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onfocus", Dict.VERS_EVENTS, AttrCheckImpl.SCRIPT), // event
        new Attribute("onafterupdate", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // form fields
        new Attribute("onbeforeupdate", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // form fields
        new Attribute("onerrorupdate", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // form fields
        new Attribute("onrowenter", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // form fields
        new Attribute("onrowexit", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // form fields
        new Attribute("onbeforeunload", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // form fields
        new Attribute("ondatasetchanged", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // object, applet
        new Attribute("ondataavailable", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // object, applet
        new Attribute("ondatasetcomplete", Dict.VERS_MICROSOFT, AttrCheckImpl.SCRIPT), // object, applet
        new Attribute("profile", Dict.VERS_HTML40, AttrCheckImpl.URL), // HEAD
        new Attribute("prompt", Dict.VERS_LOOSE, AttrCheckImpl.TEXT), // ISINDEX
        new Attribute("readonly", Dict.VERS_HTML40, AttrCheckImpl.BOOL), // form fields
        new Attribute("rel", Dict.VERS_ALL, AttrCheckImpl.LINKTYPES), // A, LINK
        new Attribute("rev", Dict.VERS_ALL, AttrCheckImpl.LINKTYPES), // A, LINK
        new Attribute("rightmargin", Dict.VERS_MICROSOFT, AttrCheckImpl.NUMBER), // used on BODY
        new Attribute("rows", Dict.VERS_ALL, AttrCheckImpl.NUMBER), // TEXTAREA
        new Attribute("rowspan", Dict.VERS_ALL, AttrCheckImpl.NUMBER), // table cells
        new Attribute("rules", Dict.VERS_HTML40, AttrCheckImpl.TRULES), // TABLE
        new Attribute("scheme", Dict.VERS_HTML40, AttrCheckImpl.TEXT), // META
        new Attribute("scope", Dict.VERS_HTML40, AttrCheckImpl.SCOPE), // table cells
        new Attribute("scrolling", Dict.VERS_IFRAME, AttrCheckImpl.SCROLL), // yes, no or auto
        new Attribute("selected", Dict.VERS_ALL, AttrCheckImpl.BOOL), // OPTION
        new Attribute("shape", Dict.VERS_FROM32, AttrCheckImpl.SHAPE), // AREA, A
        new Attribute("showgrid", Dict.VERS_PROPRIETARY, AttrCheckImpl.BOOL), // TABLE Adobe golive
        new Attribute("showgridx", Dict.VERS_PROPRIETARY, AttrCheckImpl.BOOL), // TABLE Adobe golive
        new Attribute("showgridy", Dict.VERS_PROPRIETARY, AttrCheckImpl.BOOL), // TABLE Adobe golive
        new Attribute("size", Dict.VERS_LOOSE, AttrCheckImpl.NUMBER), // HR, FONT, BASEFONT, SELECT
        new Attribute("span", Dict.VERS_HTML40, AttrCheckImpl.NUMBER), // COL, COLGROUP
        new Attribute("src", Dict.VERS_ALL, AttrCheckImpl.URL), // IMG, FRAME, IFRAME
        new Attribute("standby", Dict.VERS_HTML40, AttrCheckImpl.TEXT), // OBJECT
        new Attribute("start", Dict.VERS_ALL, AttrCheckImpl.NUMBER), // OL
        new Attribute("style", Dict.VERS_HTML40, AttrCheckImpl.TEXT),
        new Attribute("summary", Dict.VERS_HTML40, AttrCheckImpl.TEXT), // TABLE
        new Attribute("tabindex", Dict.VERS_HTML40, AttrCheckImpl.NUMBER), // fields, OBJECT and A
        new Attribute("target", Dict.VERS_HTML40, AttrCheckImpl.TARGET), // names a frame/window
        new Attribute("text", Dict.VERS_LOOSE, AttrCheckImpl.COLOR), // BODY
        new Attribute("title", Dict.VERS_HTML40, AttrCheckImpl.TEXT), // text tool tip
        new Attribute("topmargin", Dict.VERS_MICROSOFT, AttrCheckImpl.NUMBER), // used on BODY
        new Attribute("type", Dict.VERS_FROM32, AttrCheckImpl.TYPE), // also used by SPACER
        new Attribute("usemap", Dict.VERS_ALL, AttrCheckImpl.BOOL), // things with images
        new Attribute("valign", Dict.VERS_FROM32, AttrCheckImpl.VALIGN),
        new Attribute("value", Dict.VERS_ALL, AttrCheckImpl.TEXT), // OPTION, PARAM
        new Attribute("valuetype", Dict.VERS_HTML40, AttrCheckImpl.VTYPE), // PARAM: data, ref, object
        new Attribute("version", Dict.VERS_ALL, AttrCheckImpl.TEXT), // HTML
        new Attribute("vlink", Dict.VERS_LOOSE, AttrCheckImpl.COLOR), // BODY
        new Attribute("vspace", Dict.VERS_LOOSE, AttrCheckImpl.NUMBER), // IMG, OBJECT, APPLET
        new Attribute("width", Dict.VERS_ALL, AttrCheckImpl.LENGTH), // pixels only for TD/TH
        new Attribute("wrap", Dict.VERS_NETSCAPE, AttrCheckImpl.TEXT), // textarea
        new Attribute("xml:lang", Dict.VERS_XML, AttrCheckImpl.TEXT), // XML language
        new Attribute("xml:space", Dict.VERS_XML, AttrCheckImpl.TEXT), // XML language
        new Attribute("xmlns", Dict.VERS_ALL, AttrCheckImpl.TEXT), // name space
        new Attribute("rbspan", Dict.VERS_XHTML11, AttrCheckImpl.NUMBER), // ruby markup
    };

    /**
     * Map containing all the installed attributes.
     */
    private Map attributeHashtable = new Hashtable();

    /**
     * lookup an installed Attribute.
     * @param name attribute name
     * @return Attribute or null if the attribute is not found
     */
    public Attribute lookup(String name)
    {
        return (Attribute) this.attributeHashtable.get(name);
    }

    /**
     * installs a new Attribute.
     * @param attr Atribute
     * @return installed Attribute
     */
    public Attribute install(Attribute attr)
    {
        return (Attribute) this.attributeHashtable.put(attr.getName(), attr);
    }

    /**
     * public method for finding attribute definition by name.
     * @param attval AttVal instance
     * @return Attribute with name = attval.name
     */
    public Attribute findAttribute(AttVal attval)
    {
        Attribute np;

        if (attval.attribute != null)
        {
            np = lookup(attval.attribute);
            return np;
        }

        return null;
    }

    /**
     * Does the given attibute contains an url?
     * @param attrname attribute name
     * @return <code>true</code> if the given attribute is expected to contain an URL
     */
    public boolean isUrl(String attrname)
    {
        Attribute np;

        np = lookup(attrname);
        return (np != null && np.getAttrchk() == AttrCheckImpl.URL);
    }

    /**
     * Does the given attibute contains a script?
     * @param attrname attribute name
     * @return <code>true</code> if the given attribute is expected to contain a script
     */
    public boolean isScript(String attrname)
    {
        Attribute np;

        np = lookup(attrname);
        return (np != null && np.getAttrchk() == AttrCheckImpl.SCRIPT);
    }

    /**
     * Does the given attibute contains a literal attribute?
     * @param attrname attribute name
     * @return <code>true</code> if the given attribute is expected to contain a literal attribute
     */
    public boolean isLiteralAttribute(String attrname)
    {
        Attribute np;

        np = lookup(attrname);
        return (np != null && np.isLiteral());
    }

    /**
     * Declare a new literal attribute.
     * @param name atribute name
     */
    public void declareLiteralAttrib(String name)
    {
        // Henry Zrepa reports that some folk are using embed with script attributes where newlines are signficant.
        // These
        // need to be declared and handled specially!
        Attribute attrib = lookup(name);

        if (attrib == null)
        {
            attrib = install(new Attribute(name, Dict.VERS_PROPRIETARY, null));
        }

        attrib.setLiteral(true);
    }

    /**
     * Returns the default attribute table instance.
     * @return AttributeTable instance
     */
    public static AttributeTable getDefaultAttributeTable()
    {
        if (defaultAttributeTable == null)
        {
            defaultAttributeTable = new AttributeTable();
            for (int i = 0; i < ATTRS.length; i++)
            {
                defaultAttributeTable.install(ATTRS[i]);
            }
            attrHref = defaultAttributeTable.lookup("href");
            attrSrc = defaultAttributeTable.lookup("src");
            attrId = defaultAttributeTable.lookup("id");
            attrName = defaultAttributeTable.lookup("name");
            attrSummary = defaultAttributeTable.lookup("summary");
            attrAlt = defaultAttributeTable.lookup("alt");
            attrLongdesc = defaultAttributeTable.lookup("longdesc");
            attrUsemap = defaultAttributeTable.lookup("usemap");
            attrIsmap = defaultAttributeTable.lookup("ismap");
            attrLanguage = defaultAttributeTable.lookup("language");
            attrType = defaultAttributeTable.lookup("type");
            attrTitle = defaultAttributeTable.lookup("title");
            attrXmlns = defaultAttributeTable.lookup("xmlns");
            attrValue = defaultAttributeTable.lookup("value");
            attrContent = defaultAttributeTable.lookup("content");
            attrDatafld = defaultAttributeTable.lookup("datafld");
            attrWidth = defaultAttributeTable.lookup("width");
            attrHeight = defaultAttributeTable.lookup("height");

            attrAlt.setNowrap(true);
            attrValue.setNowrap(true);
            attrContent.setNowrap(true);
        }
        return defaultAttributeTable;
    }

}