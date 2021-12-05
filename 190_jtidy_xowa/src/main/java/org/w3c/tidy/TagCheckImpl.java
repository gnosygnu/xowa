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
 * Check HTML attributes implementation.
 * @author Dave Raggett <a href="mailto:dsr@w3.org">dsr@w3.org </a>
 * @author Andy Quick <a href="mailto:ac.quick@sympatico.ca">ac.quick@sympatico.ca </a> (translation to Java)
 * @author Fabrizio Giustina
 * @version $Revision: 747 $ ($Author: fgiust $)
 */
public final class TagCheckImpl
{

    /**
     * CheckHTML instance.
     */
    public static final TagCheck HTML = new CheckHTML();

    /**
     * CheckSCRIPT instance.
     */
    public static final TagCheck SCRIPT = new CheckSCRIPT();

    /**
     * CheckTABLE instance.
     */
    public static final TagCheck TABLE = new CheckTABLE();

    /**
     * CheckCaption instance.
     */
    public static final TagCheck CAPTION = new CheckCaption();

    /**
     * CheckIMG instance.
     */
    public static final TagCheck IMG = new CheckIMG();

    /**
     * CheckAREA instance.
     */
    public static final TagCheck AREA = new CheckAREA();

    /**
     * CheckAnchor instance.
     */
    public static final TagCheck ANCHOR = new CheckAnchor();

    /**
     * CheckMap instance.
     */
    public static final TagCheck MAP = new CheckMap();

    /**
     * CheckSTYLE instance.
     */
    public static final TagCheck STYLE = new CheckSTYLE();

    /**
     * CheckTableCell instance.
     */
    public static final TagCheck TABLECELL = new CheckTableCell();

    /**
     * CheckLINK instance.
     */
    public static final TagCheck LINK = new CheckLINK();

    /**
     * CheckHR instance.
     */
    public static final TagCheck HR = new CheckHR();

    /**
     * CheckForm instance.
     */
    public static final TagCheck FORM = new CheckForm();

    /**
     * CheckMeta instance.
     */
    public static final TagCheck META = new CheckMeta();

    /**
     * don't instantiate.
     */
    private TagCheckImpl()
    {
        // unused
    }

    /**
     * Checker implementation for html tag.
     */
    public static class CheckHTML implements TagCheck
    {

        /**
         * xhtml namepace String.
         */
        private static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {

            AttVal attval;
            AttVal xmlns;

            xmlns = node.getAttrByName("xmlns");

            if (xmlns != null && XHTML_NAMESPACE.equals(xmlns.value))
            {
                lexer.isvoyager = true;
                if (!lexer.configuration.htmlOut) // Unless user has specified plain HTML output,
                {
                    lexer.configuration.xHTML = true; // output format will be XHTML.
                }
                // adjust other config options, just as in Configuration
                lexer.configuration.xmlOut = true;
                lexer.configuration.upperCaseTags = false;
                lexer.configuration.upperCaseAttrs = false;
            }

            for (attval = node.attributes; attval != null; attval = attval.next)
            {
                attval.checkAttribute(lexer, node);
            }
        }

    }

    /**
     * Checker implementation for script tags.
     */
    public static class CheckSCRIPT implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal lang, type;

            node.checkAttributes(lexer);

            lang = node.getAttrByName("language");
            type = node.getAttrByName("type");

            if (type == null)
            {
                AttVal missingType = new AttVal(null, null, '"', "type", "");
                lexer.report.attrError(lexer, node, missingType, Report.MISSING_ATTRIBUTE);

                // check for javascript
                if (lang != null)
                {
                    String str = lang.value;
                    if ("javascript".equalsIgnoreCase(str) || "jscript".equalsIgnoreCase(str))
                    {
                        node.addAttribute("type", "text/javascript");
                    }
                    else if ("vbscript".equalsIgnoreCase(str))
                    {
                        // per Randy Waki 8/6/01
                        node.addAttribute("type", "text/vbscript");
                    }
                }
                else
                {
                    node.addAttribute("type", "text/javascript");
                }
            }
        }

    }

    /**
     * Checker implementation for table.
     */
    public static class CheckTABLE implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal attval;
            Attribute attribute;
            boolean hasSummary = false;

            for (attval = node.attributes; attval != null; attval = attval.next)
            {
                attribute = attval.checkAttribute(lexer, node);

                if (attribute == AttributeTable.attrSummary)
                {
                    hasSummary = true;
                }
            }

            /* suppress warning for missing summary for HTML 2.0 and HTML 3.2 */
            if (!hasSummary && lexer.doctype != Dict.VERS_HTML20 && lexer.doctype != Dict.VERS_HTML32)
            {
                lexer.badAccess |= Report.MISSING_SUMMARY;

                // summary is not required, should be only an accessibility warning
                // AttVal missingSummary = new AttVal(null, null, '"', "summary", "");
                // lexer.report.attrError(lexer, node, missingSummary, Report.MISSING_ATTRIBUTE);
            }

            /* convert <table border> to <table border="1"> */
            if (lexer.configuration.xmlOut)
            {
                attval = node.getAttrByName("border");
                if (attval != null)
                {
                    if (attval.value == null)
                    {
                        attval.value = "1";
                    }
                }
            }

            /* <table height="..."> is proprietary */
            if ((attval = node.getAttrByName("height")) != null)
            {
                lexer.report.attrError(lexer, node, attval, Report.PROPRIETARY_ATTRIBUTE);
                lexer.versions &= Dict.VERS_PROPRIETARY;
            }

        }

    }

    /**
     * Checker implementation for table caption.
     */
    public static class CheckCaption implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal attval;
            String value = null;

            node.checkAttributes(lexer);

            for (attval = node.attributes; attval != null; attval = attval.next)
            {
                if ("align".equalsIgnoreCase(attval.attribute))
                {
                    value = attval.value;
                    break;
                }
            }

            if (value != null)
            {
                if ("left".equalsIgnoreCase(value) || "right".equalsIgnoreCase(value))
                {
                    lexer.constrainVersion(Dict.VERS_HTML40_LOOSE);
                }
                else if ("top".equalsIgnoreCase(value) || "bottom".equalsIgnoreCase(value))
                {
                    lexer.constrainVersion(~(Dict.VERS_HTML20 | Dict.VERS_HTML32));
                }
                else
                {
                    lexer.report.attrError(lexer, node, attval, Report.BAD_ATTRIBUTE_VALUE);
                }
            }
        }

    }

    /**
     * Checker implementation for hr.
     */
    public static class CheckHR implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal av = node.getAttrByName("src");

            node.checkAttributes(lexer);

            if (av != null)
            {
                lexer.report.attrError(lexer, node, av, Report.PROPRIETARY_ATTR_VALUE);
            }
        }
    }

    /**
     * Checker implementation for image tags.
     */
    public static class CheckIMG implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal attval;
            Attribute attribute;
            boolean hasAlt = false;
            boolean hasSrc = false;
            boolean hasUseMap = false;
            boolean hasIsMap = false;
            boolean hasDataFld = false;

            for (attval = node.attributes; attval != null; attval = attval.next)
            {
                attribute = attval.checkAttribute(lexer, node);

                if (attribute == AttributeTable.attrAlt)
                {
                    hasAlt = true;
                }
                else if (attribute == AttributeTable.attrSrc)
                {
                    hasSrc = true;
                }
                else if (attribute == AttributeTable.attrUsemap)
                {
                    hasUseMap = true;
                }
                else if (attribute == AttributeTable.attrIsmap)
                {
                    hasIsMap = true;
                }
                else if (attribute == AttributeTable.attrDatafld)
                {
                    hasDataFld = true;
                }
                else if (attribute == AttributeTable.attrWidth || attribute == AttributeTable.attrHeight)
                {
                    lexer.constrainVersion(~Dict.VERS_HTML20);
                }
            }

            if (!hasAlt)
            {
                lexer.badAccess |= Report.MISSING_IMAGE_ALT;
                AttVal missingAlt = new AttVal(null, null, '"', "alt", "");
                lexer.report.attrError(lexer, node, missingAlt, Report.MISSING_ATTRIBUTE);
                if (lexer.configuration.altText != null)
                {
                    node.addAttribute("alt", lexer.configuration.altText);
                }
            }

            if (!hasSrc && !hasDataFld)
            {
                AttVal missingSrc = new AttVal(null, null, '"', "src", "");
                lexer.report.attrError(lexer, node, missingSrc, Report.MISSING_ATTRIBUTE);
            }

            if (hasIsMap && !hasUseMap)
            {
                AttVal missingIsMap = new AttVal(null, null, '"', "ismap", "");
                lexer.report.attrError(lexer, node, missingIsMap, Report.MISSING_IMAGEMAP);
            }
        }

    }

    /**
     * Checker implementation for area.
     */
    public static class CheckAREA implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal attval;
            Attribute attribute;
            boolean hasAlt = false;
            boolean hasHref = false;

            for (attval = node.attributes; attval != null; attval = attval.next)
            {
                attribute = attval.checkAttribute(lexer, node);

                if (attribute == AttributeTable.attrAlt)
                {
                    hasAlt = true;
                }
                else if (attribute == AttributeTable.attrHref)
                {
                    hasHref = true;
                }
            }

            if (!hasAlt)
            {
                lexer.badAccess |= Report.MISSING_LINK_ALT;
                AttVal missingAlt = new AttVal(null, null, '"', "alt", "");
                lexer.report.attrError(lexer, node, missingAlt, Report.MISSING_ATTRIBUTE);
            }
            if (!hasHref)
            {
                AttVal missingHref = new AttVal(null, null, '"', "href", "");
                lexer.report.attrError(lexer, node, missingHref, Report.MISSING_ATTRIBUTE);
            }
        }

    }

    /**
     * Checker implementation for anchors.
     */
    public static class CheckAnchor implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            node.checkAttributes(lexer);

            lexer.fixId(node);
        }
    }

    /**
     * Checker implementation for image maps.
     */
    public static class CheckMap implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            node.checkAttributes(lexer);

            lexer.fixId(node);
        }
    }

    /**
     * Checker implementation for style tags.
     */
    public static class CheckSTYLE implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal type = node.getAttrByName("type");

            node.checkAttributes(lexer);

            if (type == null)
            {
                AttVal missingType = new AttVal(null, null, '"', "type", "");
                lexer.report.attrError(lexer, node, missingType, Report.MISSING_ATTRIBUTE);

                node.addAttribute("type", "text/css");
            }
        }
    }

    /**
     * Checker implementation for forms. Reports missing action attribute.
     */
    public static class CheckForm implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal action = node.getAttrByName("action");

            node.checkAttributes(lexer);

            if (action == null)
            {
                AttVal missingAttribute = new AttVal(null, null, '"', "action", "");
                lexer.report.attrError(lexer, node, missingAttribute, Report.MISSING_ATTRIBUTE);
            }
        }
    }

    /**
     * Checker implementation for meta tags. Reports missing content attribute.
     */
    public static class CheckMeta implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal content = node.getAttrByName("content");

            node.checkAttributes(lexer);

            if (content == null)
            {
                AttVal missingAttribute = new AttVal(null, null, '"', "content", "");
                lexer.report.attrError(lexer, node, missingAttribute, Report.MISSING_ATTRIBUTE);
            }

            // name or http-equiv attribute must also be set
        }
    }

    /**
     * Checker implementation for table cells.
     */
    public static class CheckTableCell implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            node.checkAttributes(lexer);

            // HTML4 strict doesn't allow mixed content for elements with %block; as their content model

            if (node.getAttrByName("width") != null || node.getAttrByName("height") != null)
            {
                lexer.constrainVersion(~Dict.VERS_HTML40_STRICT);
            }
        }
    }

    /**
     * add missing type attribute when appropriate.
     */
    public static class CheckLINK implements TagCheck
    {

        /**
         * @see org.w3c.tidy.TagCheck#check(org.w3c.tidy.Lexer, org.w3c.tidy.Node)
         */
        public void check(Lexer lexer, Node node)
        {
            AttVal rel = node.getAttrByName("rel");

            node.checkAttributes(lexer);

            if (rel != null && rel.value != null && rel.value.equals("stylesheet"))
            {
                AttVal type = node.getAttrByName("type");

                if (type == null)
                {
                    AttVal missingType = new AttVal(null, null, '"', "type", "");
                    lexer.report.attrError(lexer, node, missingType, Report.MISSING_ATTRIBUTE);

                    node.addAttribute("type", "text/css");
                }
            }
        }
    }

}