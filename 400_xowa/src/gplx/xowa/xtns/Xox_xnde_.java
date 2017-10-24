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
package gplx.xowa.xtns; import gplx.*; import gplx.xowa.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.xndes.*;
import gplx.langs.htmls.docs.*;
public class Xox_xnde_ {
	public static Mwh_atr_itm[] Xatr__set(Xowe_wiki wiki, Mwh_atr_itm_owner1 owner, Hash_adp_bry hash, byte[] src, Xop_xnde_tkn xnde) {
		Mwh_atr_itm[] rv = wiki.Appe().Parser_mgr().Xnde__parse_atrs(src, xnde.Atrs_bgn(), xnde.Atrs_end());
		int len = rv.length;
		for (int i = 0; i < len; ++i) {
			Mwh_atr_itm xatr = rv[i]; if (xatr.Invalid()) continue;
			owner.Xatr__set(wiki, src, xatr, hash.Get_by_bry(xatr.Key_bry()));
		}
		return rv;
	}
	public static Mwh_atr_itm[] Parse_xatrs(Xowe_wiki wiki, Mwh_atr_itm_owner2 owner, Hash_adp_bry hash, byte[] src, Xop_xnde_tkn xnde) {
		Mwh_atr_itm[] rv = wiki.Appe().Parser_mgr().Xnde__parse_atrs(src, xnde.Atrs_bgn(), xnde.Atrs_end());
		int len = rv.length;
		for (int i = 0; i < len; ++i) {
			Mwh_atr_itm xatr = rv[i]; if (xatr.Invalid()) continue;
			byte xatr_tid = hash.Get_as_byte_or(xatr.Key_bry(), Byte_.Max_value_127);
			owner.Xatr__set(wiki, src, xatr, xatr_tid);
		}
		return rv;
	}
	public static Gfh_tag_rdr New_tag_rdr(Xop_ctx ctx, byte[] src, Xop_xnde_tkn xnde) {
		Gfh_tag_rdr rv = Gfh_tag_rdr.New__custom();
		rv.Init(ctx.Page().Url_bry_safe(), src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		return rv;
	}
	public static byte[] Extract_body_or_null(byte[] src, Xop_xnde_tkn xnde) {
		int body_bgn = xnde.Tag_open_end();
		int body_end = xnde.Tag_close_bgn();
		return body_bgn != -1 && body_end > body_bgn ? Bry_.Mid(src, body_bgn, body_end) : null;
	}
}
