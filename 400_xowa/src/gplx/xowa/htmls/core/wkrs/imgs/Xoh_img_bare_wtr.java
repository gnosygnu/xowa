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
package gplx.xowa.htmls.core.wkrs.imgs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_img_bare_wtr implements BryBfrArg, Xoh_wtr_itm {
	private byte[] src;
	private Xoh_img_bare_data data_itm;
	private Xoh_hdoc_ctx hctx;
	public void Init_by_parse(BryWtr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_img_bare_data data) {
		this.src = src; this.hctx = hctx;
		this.data_itm = data;
	}
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		this.src = src; this.hctx = hctx;
		this.data_itm = (Xoh_img_bare_data)data_itm;			
		return true;
	}
	public void AddToBfr(BryWtr bfr) {
		byte[] url_bry = BryUtl.Empty;
		switch (data_itm.Img_tid()) {
			case Xoh_img_bare_data.Img_tid__hiero:		url_bry = Xoh_img_bare_data.Url__hiero; break;
			case Xoh_img_bare_data.Img_tid__imap_btn:	url_bry = Xoh_img_bare_data.Url__imap; break;
		}
		bfr.AddMid(src, data_itm.Src_bgn(), data_itm.Dir_bgn());
		bfr.Add(hctx.Fsys__res()).Add(url_bry);	// NOTE: must use Fsys_res will be android_asset on drd; en.w:Hieroglyphics DATE:2016-01-31
		bfr.AddMid(src, data_itm.Dir_end(), data_itm.Src_end());
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_bare_wtr rv = new Xoh_img_bare_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
