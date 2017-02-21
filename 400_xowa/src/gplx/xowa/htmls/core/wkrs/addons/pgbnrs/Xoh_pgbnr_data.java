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
package gplx.xowa.htmls.core.wkrs.addons.pgbnrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.addons.*;
import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_pgbnr_data implements Xoh_data_itm {// NOTE: some galleries fail hzip; use Hook_bry to catch them; PAGE:en.d:a; DATE:2016-06-24
	public int Tid()			{return Xoh_hzip_dict_.Tid__pgbnr;}
	public int Src_bgn()		{return src_bgn;} private int src_bgn;
	public int Src_end()		{return src_end;} private int src_end;
	public void Clear() {
		this.src_bgn = this.src_end = -1;
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag pgbnr_lhs, Gfh_tag unused) {
		this.Clear();
		this.src_bgn = pgbnr_lhs.Src_bgn(); 
		this.src_end = pgbnr_lhs.Src_end();
		return true;
	}
	public void Init_by_decode(int src_bgn, int src_end) {
	}
	public boolean Init_by_edit(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag pgbnr_lhs, Gfh_tag unused) {
		this.Clear();
		this.src_bgn = pgbnr_lhs.Src_bgn(); 
		this.src_end = pgbnr_lhs.Src_end();
		return true;
	}
//		public static final    byte[] Hook_bry = Bry_.new_a7(" class=\"pgbnr mw-pgbnr");

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_pgbnr_data rv = new Xoh_pgbnr_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
