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
package gplx.xowa.parsers.mws.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import gplx.xowa.parsers.htmls.*;
public class Xomw_sanitizer_mgr {
	private final    Mwh_doc_wkr__atr_bldr atr_bldr = new Mwh_doc_wkr__atr_bldr();
	private final    Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	public void Fix_tag_attributes(Bry_bfr bfr, byte[] tag_name, byte[] atrs) {
		atr_bldr.Atrs__clear();
		atr_parser.Parse(atr_bldr, -1, -1, atrs, 0, atrs.length);
		int len = atr_bldr.Atrs__len();

		// PORTED: Sanitizer.php|safeEncodeTagAttributes
		for (int i = 0; i < len; i++) {
			// $encAttribute = htmlspecialchars( $attribute );
			// $encValue = Sanitizer::safeEncodeAttribute( $value );
			// $attribs[] = "$encAttribute=\"$encValue\"";
			Mwh_atr_itm itm = atr_bldr.Atrs__get_at(i);
			bfr.Add_byte_space();	// "return count( $attribs ) ? ' ' . implode( ' ', $attribs ) : '';"
			bfr.Add_bry_escape_html(itm.Key_bry(), itm.Key_bgn(), itm.Key_end());
			bfr.Add_byte_eq().Add_byte_quote();
			bfr.Add(itm.Val_as_bry());	// TODO.XO:Sanitizer::encode
			bfr.Add_byte_quote();
		}
	}
}
