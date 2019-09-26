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
package gplx.xowa.xtns.imaps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import gplx.xowa.apps.fsys.*;
import gplx.xowa.htmls.hdumps.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.*;
public class Imap_desc_hdump_wkr implements Xoh_hdump_wkr {
	private final    byte[] desc_icon_url;
	public Imap_desc_hdump_wkr(Xoa_fsys_mgr fsys_mgr) {
		this.desc_icon_url = Imap_xtn_mgr.Desc_icon_url(fsys_mgr);
	}
	public byte[] Key() {return KEY;}
	public int Process(Bry_bfr bfr, Xoh_hdoc_ctx hctx, Xoh_hdoc_wkr hdoc_wkr, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag tag) {
		Gfh_atr atr = tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__src);
		if (atr == Gfh_atr.Noop) {
			tag_rdr.Err_wkr().Warn("hiero tag missing src; tag=" + tag.To_str());
			bfr.Add_mid(src, tag.Src_bgn(), tag.Src_end());
		}
		else {
			Xoh_hdump_wkr_utl.Write_tag_with_val_at_atr_bgn(bfr, src, tag, atr, desc_icon_url);
		}
		return tag.Src_end();
	}
	public static byte[] KEY = Bry_.new_a7("imap-desc-icon");
	public static byte[] HDUMP_ATR = Xoh_hdump_wkr_utl.Build_hdump_atr(KEY);
}
