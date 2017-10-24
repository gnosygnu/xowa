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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.hzips.*;
public class Xosync_hdoc_wtr implements Xoh_hdoc_wkr {
	private Xoh_hzip_bfr bfr;
	private byte[] src;
	
	public void Init_by_page(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.On_new_page(bfr, hpg, hctx, src, src_bgn, src_end);
	}
	public void On_new_page(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr;
		this.src = src;
	}
	public void On_txt		(int rng_bgn, int rng_end)	{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void Add_bfr		(Bry_bfr v)					{bfr.Add_bfr_and_clear(v);}
	public void Add_str		(String v)					{bfr.Add_str_u8(v);}
	public void Add_bry		(byte[] v)					{bfr.Add(v);}

	// not used
	public void On_escape	(gplx.xowa.htmls.core.wkrs.escapes.Xoh_escape_data data) {}
	public void On_xnde		(gplx.xowa.htmls.core.wkrs.xndes.Xoh_xnde_parser parser) {}
	public void On_lnki		(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_data parser) {}
	public void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data parser) {}
	public void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_data parser) {}
	public boolean Process_parse(Xoh_data_itm data) {return false;}
}
