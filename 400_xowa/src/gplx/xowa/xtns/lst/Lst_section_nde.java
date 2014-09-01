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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*;
public class Lst_section_nde implements Xox_xnde, Xop_xnde_atr_parser {
	public byte[] Section_name() {return section_name;} private byte[] section_name;
	public void Xatr_parse(Xow_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_obj) {
		if (xatr_obj == null) return;
		byte xatr_tid = ((Byte_obj_val)xatr_obj).Val();
		switch (xatr_tid) {
			case Xatr_name: case Xatr_bgn: case Xatr_end:
				section_name = xatr.Val_as_bry(src); name_tid = xatr_tid; break;
		}
	}
	public Xop_xnde_tkn Xnde() {return xnde;} private Xop_xnde_tkn xnde;
	public byte Name_tid() {return name_tid;} private byte name_tid;
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xoa_app app = ctx.App();
		Xop_xatr_itm[] atrs = Xop_xatr_itm.Xatr_parse(app, this, wiki.Lang().Xatrs_section(), wiki, src, xnde);
		this.xnde = xnde;
		xnde.Atrs_ary_(atrs);
		ctx.Lst_section_mgr().Add(this);
	}
	public void Xtn_write(Bry_bfr bfr, Xoa_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {}	// NOTE: write nothing; <section> is just a bookmark
	public static final byte Xatr_name = 0, Xatr_bgn = 1, Xatr_end = 2;
	public static Hash_adp_bry new_xatrs_(Xol_lang lang) {
		Hash_adp_bry rv = Hash_adp_bry.ci_utf8_(lang.Case_mgr());	// UTF8:see xatrs below
		rv.Add_str_byte("name", Lst_section_nde.Xatr_name);
		Xatrs_add(rv, "begin", "end");
		switch (lang.Lang_id()) {	// NOTE: as of v315572b, i18n is done directly in code, not in magic.php; am wary of adding keywords for general words like begin/end, so adding them manually per language; DATE:2013-02-09
			case Xol_lang_itm_.Id_de: Xatrs_add(rv, "Anfang", "Ende"); break;
			case Xol_lang_itm_.Id_he: Xatrs_add(rv, "התחלה", "סוף"); break;
			case Xol_lang_itm_.Id_pt: Xatrs_add(rv, "começo", "fim"); break;
		}
		return rv;
	}
	private static void Xatrs_add(Hash_adp_bry hash, String key_begin, String key_end) {
		hash.Add_str_byte(key_begin	, Lst_section_nde.Xatr_bgn);
		hash.Add_str_byte(key_end	, Lst_section_nde.Xatr_end);
	}
}