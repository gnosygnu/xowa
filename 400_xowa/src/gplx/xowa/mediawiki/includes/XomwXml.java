/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki.includes;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.List_adp;
public class XomwXml {
	// Format an XML element with given attributes and, optionally, text content.
	// Element and attribute names are assumed to be ready for literal inclusion.
	// Strings are assumed to not contain XML-illegal characters; special
	// characters (<, >, &) are escaped but illegals are not touched.
	// ARGS: contents defaults to ""
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Element(BryWtr bfr, byte[] element, List_adp attribs, byte[] contents, boolean allow_short_tag) {
		bfr.AddByte(AsciiByte.AngleBgn).Add(element);
		if (attribs.Len() > 0) {
			Expand_attributes(bfr, attribs);
		}
		if (contents == null) {
			bfr.AddByte(AsciiByte.AngleEnd);
		}
		else {
			if (allow_short_tag && contents == BryUtl.Empty) {
				bfr.AddStrA7(" />");
			}
			else {
				bfr.AddByte(AsciiByte.AngleEnd);
				bfr.AddBryEscapeHtml(contents);
				bfr.AddByte(AsciiByte.AngleBgn).AddByte(AsciiByte.Slash).Add(element).AddByte(AsciiByte.AngleEnd);
			}
		}
	}
	// Given an array of ('attributename' => 'value'), it generates the code
	// to set the XML attributes : attributename="value".
	// The values are passed to Sanitizer::encodeAttribute.
	// Return null if no attributes given.
	// @param array $attribs Array of attributes for an XML element
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Expand_attributes(BryWtr bfr, List_adp attribs) {
		int attribs_len = attribs.Len();
		for (int i = 0; i < attribs_len; i += 2) {
			// XO.MW: $out .= " {$name}=\"" . Sanitizer::encodeAttribute( $val ) . '"';
			bfr.AddByteSpace();
			bfr.Add((byte[])attribs.GetAt(i));
			bfr.AddByteEq().AddByteQuote();
			XomwSanitizer.encodeAttribute(bfr, (byte[])attribs.GetAt(i + 1));
			bfr.AddByteQuote();
		}
	}

	// This opens an XML element
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Open_element(BryWtr bfr, byte[] element, List_adp attribs) {
		bfr.AddByte(AsciiByte.AngleBgn).Add(element);
		Expand_attributes(bfr, attribs);
		bfr.AddByte(AsciiByte.AngleEnd);
	}

	// Shortcut to close an XML element
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Close_element(BryWtr bfr, byte[] element) {
		bfr.AddByte(AsciiByte.AngleBgn).AddByte(AsciiByte.Slash).Add(element).AddByte(AsciiByte.AngleEnd);
	}

	// Same as Xml::element(), but does not escape contents. Handy when the
	// content you have is already valid xml.
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Tags(BryWtr bfr, byte[] element, List_adp attribs, byte[] contents) {
		Open_element(bfr, element, attribs);
		bfr.Add(contents);
		bfr.AddByte(AsciiByte.AngleBgn).AddByte(AsciiByte.Slash).Add(element).AddByte(AsciiByte.AngleEnd);
	}
}
