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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.xowa.langs.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Lst_section_nde implements Xox_xnde, Mwh_atr_itm_owner1 {
	public byte[] Section_name() {return section_name;} private byte[] section_name;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		byte xatr_id = ((Byte_obj_val)xatr_id_obj).Val();
		switch (xatr_id) {
			case Xatr_name: case Xatr_bgn: case Xatr_end:
				section_name = xatr.Val_as_bry(); name_tid = xatr_id; break;
		}
	}
	public Xop_xnde_tkn Xnde() {return xnde;} private Xop_xnde_tkn xnde;
	public byte Name_tid() {return name_tid;} private byte name_tid;
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Mwh_atr_itm[] atrs = Xox_xnde_.Xatr__set(wiki, this, wiki.Lang().Xatrs_section(), src, xnde);
		this.xnde = xnde;
		xnde.Atrs_ary_(atrs);
		ctx.Lst_section_mgr().Add(this);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {}	// NOTE: write nothing; <section> is just a bookmark
	public static final byte Xatr_name = 0, Xatr_bgn = 1, Xatr_end = 2;
	public static Hash_adp_bry new_xatrs_(Xol_lang_itm lang) {
		Hash_adp_bry rv = Hash_adp_bry.ci_u8(lang.Case_mgr());	// UTF8:see xatrs below
		rv.Add_str_byte("name", Lst_section_nde.Xatr_name);
		Xatrs_add(rv, "begin", "end");
		switch (lang.Lang_id()) {	// NOTE: as of v315572b, i18n is done directly in code, not in magic.php; am wary of adding keywords for general words like begin/end, so adding them manually per language; DATE:2013-02-09
			case Xol_lang_stub_.Id_de: Xatrs_add(rv, "Anfang", "Ende"); break;
			case Xol_lang_stub_.Id_he: Xatrs_add(rv, "התחלה", "סוף"); break;
			case Xol_lang_stub_.Id_pt: Xatrs_add(rv, "começo", "fim"); break;
		}
		return rv;
	}
	private static void Xatrs_add(Hash_adp_bry hash, String key_begin, String key_end) {
		hash.Add_str_byte(key_begin	, Lst_section_nde.Xatr_bgn);
		hash.Add_str_byte(key_end	, Lst_section_nde.Xatr_end);
	}
}