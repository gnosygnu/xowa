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
package gplx.xowa.mws.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import gplx.core.btries.*;
import gplx.xowa.mws.utls.*;
public class Xomw_html_utl {
	private final    Bry_bfr tmp = Bry_bfr_.New();
	private final    Btrie_rv trv = new Btrie_rv();
	public void Raw_element(Bry_bfr bfr, byte[] element, Xomw_atr_mgr attribs, byte[] contents) {			
		Bry_.Lcase__all(element); // XO:lcase element

		Open_element__lcased(bfr, element, attribs);
		if (void_elements.Has(element)) {
			bfr.Del_by_1().Add(Bry__elem__lhs__inl);
		} 
		else {
			bfr.Add(contents);
			Close_element__lcased(bfr, element);
		}
	}
	private void Open_element__lcased(Bry_bfr bfr, byte[] element, Xomw_atr_mgr attribs) {
		// This is not required in HTML5, but let's do it anyway, for
		// consistency and better compression.
		// $element = strtolower($element);	// XO:handled by callers

		// Remove invalid input types
		if (Bry_.Eq(element, Tag__input)) {
			// PORTED.HEADER:valid_input_types
			byte[] type_atr_val = attribs.Get_val_or_null(Atr__type);
			if (type_atr_val != null && !valid_input_types.Has(type_atr_val)) {
				attribs.Del(Atr__type);
			}
		}

		// According to standard the default type for <button> elements is "submit".
		// Depending on compatibility mode IE might use "button", instead.
		// We enforce the standard "submit".
		if (Bry_.Eq(element, Tag__button) && attribs.Get_val_or_null(Atr__type) == null) {
			attribs.Set(Atr__type, Val__type__submit);
		}

		bfr.Add_byte(Byte_ascii.Angle_bgn).Add(element);
		Expand_attributes(bfr, attribs);	// TODO.XO:self::dropDefaults($element, $attribs)
		bfr.Add_byte(Byte_ascii.Angle_end);
	}
	public void Expand_attributes(Bry_bfr bfr, Xomw_atr_mgr atrs) {
		int len = atrs.Len();
		for (int i = 0; i < len; i++) {
			Xomw_atr_itm atr = (Xomw_atr_itm)atrs.Get_at(i);
			byte[] key = atr.Key_bry();
			byte[] val = atr.Val();

			// Support intuitive [ 'checked' => true/false ] form
			if (val == null) {	// TESTME
				continue;
			}

			// For boolean attributes, support [ 'foo' ] instead of
			// requiring [ 'foo' => 'meaningless' ].
			boolean bool_attrib = bool_attribs.Has(val);
			if (atr.Key_int() != -1 && bool_attrib) {
				key = val;
			}

			// Not technically required in HTML5 but we'd like consistency
			// and better compression anyway.
			key = Bry_.Xcase__build__all(tmp, Bool_.N, key);

			// PORTED.HEADER:$spaceSeparatedListAttributes

			// Specific features for attributes that allow a list of space-separated values
			if (space_separated_list_attributes.Has(key)) {
				// Apply some normalization and remove duplicates

				// Convert into correct array. Array can contain space-separated
				// values. Implode/explode to get those into the main array as well.
//					if (is_array($value)) {
					// If input wasn't an array, we can skip this step
//						$newValue = [];
//						foreach ($value as $k => $v) {
//							if (is_string($v)) {
							// String values should be normal `array('foo')`
							// Just append them
//								if (!isset($value[$v])) {
								// As a special case don't set 'foo' if a
								// separate 'foo' => true/false exists in the array
								// keys should be authoritative
//									$newValue[] = $v;
//								}
//							}
//							elseif ($v) {
							// If the value is truthy but not a String this is likely
							// an [ 'foo' => true ], falsy values don't add strings
//								$newValue[] = $k;
//							}
//						}
//						$value = implode(' ', $newValue);
//					}
//					$value = explode(' ', $value);

				// Normalize spacing by fixing up cases where people used
				// more than 1 space and/or a trailing/leading space
//					$value = array_diff($value, [ '', ' ' ]);

				// Remove duplicates and create the String
//					$value = implode(' ', array_unique($value));
			}
			// DELETE
			// elseif (is_array($value)) {
			//	throw new MWException("HTML attribute $key can not contain a list of values");
			// }

			if (bool_attrib) {
				bfr.Add_byte_space().Add(key).Add(Bry__atr__val__empty); // $ret .= " $key=\"\"";
			}
			else {
				// PORTED.HEADER:atr_val_encodings
				val = Php_str_.Strtr(val, atr_val_encodings, tmp, trv);
				bfr.Add_byte_space().Add(key).Add(Bry__atr__val__quote).Add(val).Add_byte_quote();
			}
		}
	}

	private void Close_element__lcased(Bry_bfr bfr, byte[] element) {
		bfr.Add(Bry__elem__rhs__bgn).Add(element).Add_byte(Byte_ascii.Angle_end);	// EX: "</", element, ">";
	}
	private static final    byte[]
	  Bry__elem__lhs__inl        = Bry_.new_a7("/>")
	, Bry__elem__rhs__bgn        = Bry_.new_a7("</")
	, Bry__atr__val__quote       = Bry_.new_a7("=\"")
	, Bry__atr__val__empty       = Bry_.new_a7("=\"\"")

	, Tag__input                 = Bry_.new_a7("input")
	, Tag__button                = Bry_.new_a7("button")
	, Atr__type                  = Bry_.new_a7("type")
	, Val__type__submit          = Bry_.new_a7("submit")
	;

	// List of void elements from HTML5, section 8.1.2 as of 2016-09-19
	private static final    Hash_adp_bry void_elements = Hash_adp_bry.cs().Add_many_str
	(
		"area",
		"super",
		"br",
		"col",
		"embed",
		"hr",
		"img",
		"input",
		"keygen",
		"link",
		"meta",
		"param",
		"source",
		"track",
		"wbr"
	);

	// Boolean attributes, which may have the value omitted entirely.  Manually
	// collected from the HTML5 spec as of 2011-08-12.
	private static final    Hash_adp_bry bool_attribs = Hash_adp_bry.ci_a7().Add_many_str(
		"async",
		"autofocus",
		"autoplay",
		"checked",
		"controls",
		"default",
		"defer",
		"disabled",
		"formnovalidate",
		"hidden",
		"ismap",
		// "itemscope", //XO:duplicate; added below
		"loop",
		"multiple",
		"muted",
		"novalidate",
		"open",
		"pubdate",
		"final   ",
		"required",
		"reversed",
		"scoped",
		"seamless",
		"selected",
		"truespeed",
		"typemustmatch",
		// HTML5 Microdata
		"itemscope"
	);

	private static final    Btrie_slim_mgr atr_val_encodings = Btrie_slim_mgr.cs()
	// Apparently we need to entity-encode \n, \r, \t, although the
	// spec doesn't mention that.  Since we're doing strtr() anyway,
	// we may as well not call htmlspecialchars().
	// @todo FIXME: Verify that we actually need to
	// escape \n\r\t here, and explain why, exactly.
	// We could call Sanitizer::encodeAttribute() for this, but we
	// don't because we're stubborn and like our marginal savings on
	// byte size from not having to encode unnecessary quotes.
	// The only difference between this transform and the one by
	// Sanitizer::encodeAttribute() is ' is not encoded.
	.Add_str_str("&"   , "&amp;")
	.Add_str_str("\""  , "&quot;")
	.Add_str_str(">"   , "&gt;")
	// '<' allegedly allowed per spec
	// but breaks some tools if not escaped.
	.Add_str_str("<"   , "&lt;")
	.Add_str_str("\n"  , "&#10;")
	.Add_str_str("\r"  , "&#13;")
	.Add_str_str("\t"  , "&#9;");

	// https://www.w3.org/TR/html401/index/attributes.html ("space-separated")
	// https://www.w3.org/TR/html5/index.html#attributes-1 ("space-separated")
	private static final    Hash_adp_bry space_separated_list_attributes = Hash_adp_bry.ci_a7().Add_many_str(
		"class", // html4, html5
		"accesskey", // as of html5, multiple space-separated values allowed
		// html4-spec doesn't document rel= as space-separated
		// but has been used like that and is now documented as such
		// in the html5-spec.
		"rel"
	);

	private static final    Hash_adp_bry valid_input_types = Hash_adp_bry.ci_a7().Add_many_str(
		// Remove invalid input types
		"hidden",
		"text",
		"password",
		"checkbox",
		"radio",
		"file",
		"submit",
		"image",
		"reset",
		"button",

		// HTML input types
		"datetime",
		"datetime-local",
		"date",
		"month",
		"time",
		"week",
		"number",
		"range",
		"email",
		"url",
		"search",
		"tel",
		"color"
	);
}
