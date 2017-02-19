/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
public class XomwXml {
	// Format an XML element with given attributes and, optionally, text content.
	// Element and attribute names are assumed to be ready for literal inclusion.
	// Strings are assumed to not contain XML-illegal characters; special
	// characters (<, >, &) are escaped but illegals are not touched.
	// ARGS: contents defaults to ""
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Element(Bry_bfr bfr, byte[] element, List_adp attribs, byte[] contents, boolean allow_short_tag) {
		bfr.Add_byte(Byte_ascii.Angle_bgn).Add(element);
		if (attribs.Len() > 0) {
			Expand_attributes(bfr, attribs);
		}
		if (contents == null) {
			bfr.Add_byte(Byte_ascii.Angle_end);
		}
		else {
			if (allow_short_tag && contents == Bry_.Empty) {
				bfr.Add_str_a7(" />");
			}
			else {
				bfr.Add_byte(Byte_ascii.Angle_end);
				bfr.Add_bry_escape_html(contents);
				bfr.Add_byte(Byte_ascii.Angle_bgn).Add_byte(Byte_ascii.Slash).Add(element).Add_byte(Byte_ascii.Angle_end);
			}
		}
	}
	// Given an array of ('attributename' => 'value'), it generates the code
	// to set the XML attributes : attributename="value".
	// The values are passed to Sanitizer::encodeAttribute.
	// Return null if no attributes given.
	// @param array $attribs Array of attributes for an XML element
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Expand_attributes(Bry_bfr bfr, List_adp attribs) {
		int attribs_len = attribs.Len();
		for (int i = 0; i < attribs_len; i += 2) {
			// XO.MW: $out .= " {$name}=\"" . Sanitizer::encodeAttribute( $val ) . '"';
			bfr.Add_byte_space();
			bfr.Add((byte[])attribs.Get_at(i));
			bfr.Add_byte_eq().Add_byte_quote();
			XomwSanitizer.Encode_attribute(bfr, (byte[])attribs.Get_at(i + 1));
			bfr.Add_byte_quote();
		}
	}

	// This opens an XML element
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Open_element(Bry_bfr bfr, byte[] element, List_adp attribs) {
		bfr.Add_byte(Byte_ascii.Angle_bgn).Add(element);
		Expand_attributes(bfr, attribs);
		bfr.Add_byte(Byte_ascii.Angle_end);
	}

	// Shortcut to close an XML element
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Close_element(Bry_bfr bfr, byte[] element) {
		bfr.Add_byte(Byte_ascii.Angle_bgn).Add_byte(Byte_ascii.Slash).Add(element).Add_byte(Byte_ascii.Angle_end);
	}

	// Same as Xml::element(), but does not escape contents. Handy when the
	// content you have is already valid xml.
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Tags(Bry_bfr bfr, byte[] element, List_adp attribs, byte[] contents) {
		Open_element(bfr, element, attribs);
		bfr.Add(contents);
		bfr.Add_byte(Byte_ascii.Angle_bgn).Add_byte(Byte_ascii.Slash).Add(element).Add_byte(Byte_ascii.Angle_end);
	}
}
