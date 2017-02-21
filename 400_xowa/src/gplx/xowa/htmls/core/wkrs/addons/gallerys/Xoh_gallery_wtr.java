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
package gplx.xowa.htmls.core.wkrs.addons.gallerys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.addons.*;
import gplx.core.brys.*; import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*; import gplx.core.threads.poolables.*; import gplx.core.brys.args.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*;
import gplx.xowa.htmls.sections.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_gallery_wtr implements gplx.core.brys.Bfr_arg, Xoh_wtr_itm {
	private Xoh_page hpg;
	private byte[] src; private int src_bgn, src_end;
	public void Init_by_parse(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_gallery_data data) {
		Init_by_decode(hpg, hctx, src, data);
		this.Bfr_arg__add(bfr);
	}
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		this.hpg = hpg; this.src = src;
		Xoh_gallery_data data = (Xoh_gallery_data)data_itm;
		this.src_bgn = data.Src_bgn();
		this.src_end = data.Src_end();
		return true;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		hpg.Xtn__gallery_exists_y_();
		bfr.Add_mid(src, src_bgn, src_end);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_gallery_wtr rv = new Xoh_gallery_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
