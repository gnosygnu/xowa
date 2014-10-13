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
package gplx.xowa.html.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.wikis.*; import gplx.xowa.xtns.wdatas.*;
public class Xoh_page_body_cls {	// REF.MW:Skin.php|getPageClasses
	public static byte[] Calc(Bry_bfr tmp_bfr, Xoa_ttl ttl, int page_tid) {
		tmp_bfr.Add(Bry_id_prefix).Add_int_variable(ttl.Ns().Id());						// ns-0; note that special is ns--1 DATE:2014-09-24
		Add_type(tmp_bfr, ttl);															// ns-special || ns-talk || ns-subject
		tmp_bfr.Add_byte_space().Add(Bry_page_prefix).Add(Escape_cls(ttl.Full_db()));	// page-Page_title
		if (page_tid == Xow_page_tid.Tid_json) {
			switch (ttl.Ns().Id()) {
				case Xow_ns_.Id_main:
					tmp_bfr.Add_byte_space().Add(Bry_wb_entitypage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_itempage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_itempage).Add_byte(Byte_ascii.Dash).Add(ttl.Page_db());
					break;
				case Wdata_wiki_mgr.Ns_property:
					tmp_bfr.Add_byte_space().Add(Bry_wb_entitypage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_propertypage);
					tmp_bfr.Add_byte_space().Add(Bry_wb_propertypage).Add_byte(Byte_ascii.Dash).Add(ttl.Page_db());
					break;
				default:
					Gfo_usr_dlg_._.Warn_many("", "", "unexpected ns for page_body_cls; ttl=~{0}", String_.new_utf8_(ttl.Raw()));
					break;
			}
		}
		return tmp_bfr.Xto_bry_and_clear();
	}
	private static void Add_type(Bry_bfr tmp_bfr, Xoa_ttl ttl) {
		tmp_bfr.Add_byte_space();
		if (ttl.Ns().Id_special()) {
			tmp_bfr.Add(Bry_type_special);	// MW_TODO: add " mw-special-$canonicalName"
		}
		else if (ttl.Ns().Id_talk())
			tmp_bfr.Add(Bry_type_talk);
		else
			tmp_bfr.Add(Bry_type_subject);
	}
	public static byte[] Escape_cls(byte[] src) {	// REF.MW:Sanitizer.php|escapeClass; return rtrim( preg_replace(array( '/(^[0-9\\-])|[\\x00-\\x20!"#$%&\'()*+,.\\/:;<=>?@[\\]^`{|}~]|\\xC2\\xA0/', '/_+/' ), '_', $class ), '_' );
		Bry_bfr trg_bfr = null;
		int src_len = src.length; int bgn = -1;
		for (int i = 0; i < src_len; ++i) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Tab: case Byte_ascii.NewLine: case Byte_ascii.Space:
				case Byte_ascii.Bang: case Byte_ascii.Quote: case Byte_ascii.Hash: case Byte_ascii.Dollar: case Byte_ascii.Percent:
				case Byte_ascii.Amp: case Byte_ascii.Apos: case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Asterisk:
				case Byte_ascii.Plus: case Byte_ascii.Comma: case Byte_ascii.Dot: case Byte_ascii.Backslash: case Byte_ascii.Slash:
				case Byte_ascii.Colon: case Byte_ascii.Semic: case Byte_ascii.Gt: case Byte_ascii.Eq: case Byte_ascii.Lt:
				case Byte_ascii.Question: case Byte_ascii.At: case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end:
				case Byte_ascii.Pow: case Byte_ascii.Tick:
				case Byte_ascii.Curly_bgn: case Byte_ascii.Pipe: case Byte_ascii.Curly_end: case Byte_ascii.Tilde:
					if (trg_bfr == null)
						src[i] = Byte_ascii.Underline;
					else {
						if (bgn != -1) {
							trg_bfr.Add_mid(src, bgn, i);
							bgn = -1;
						}
						trg_bfr.Add_byte(Byte_ascii.Underline);
					}
					break;
				case Byte_ascii.Underline: 
					if (trg_bfr == null) {
						trg_bfr = Bry_bfr.new_(src_len);
						trg_bfr.Add_mid(src, 0, i);
					}
					if (bgn != -1) {
						trg_bfr.Add_mid(src, bgn, i);
						bgn = -1;
					}
					int repeat = 0;
					for (int j = i + 1; j < src_len; ++j) {
						if (src[j] == Byte_ascii.Underline)
							++repeat;
						else
							break;
					}
					trg_bfr.Add_byte(Byte_ascii.Underline);
					i += repeat;
					break;
				case -62:
					int next = i + 1;
					if (next < src_len && src[next] == -96) {	
						if (trg_bfr == null) {
							trg_bfr = Bry_bfr.new_(src_len);
							trg_bfr.Add_mid(src, 0, i);
						}
						trg_bfr.Add_byte(Byte_ascii.Underline);
						++i;
						continue;
					}
					if (trg_bfr != null && bgn == -1)
						bgn = i;
					break;
				default:
					if (trg_bfr != null && bgn == -1)
						bgn = i;
					break;
			}
		}
		if (bgn != -1) trg_bfr.Add_mid(src, bgn, src_len);
		return trg_bfr == null ? src : trg_bfr.Xto_bry_and_clear();
	}
	private static final byte[]
	  Bry_id_prefix			= Bry_.new_ascii_("ns-")
	, Bry_type_special		= Bry_.new_ascii_("ns-special")
	, Bry_type_talk			= Bry_.new_ascii_("ns-talk")
	, Bry_type_subject		= Bry_.new_ascii_("ns-subject")
	, Bry_page_prefix		= Bry_.new_ascii_("page-")
	, Bry_wb_entitypage		= Bry_.new_ascii_("wb-entitypage")
	, Bry_wb_itempage		= Bry_.new_ascii_("wb-itempage")
	, Bry_wb_propertypage	= Bry_.new_ascii_("wb-propertypage")
	;
	public static int Page_tid_wikitext = 0, Page_tid_wikidata_qid = 1, Page_tid_wikidata_pid = 2;
}
