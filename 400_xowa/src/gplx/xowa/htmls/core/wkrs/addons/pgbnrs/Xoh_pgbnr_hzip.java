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
import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_pgbnr_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public int Tid()		{return Xoh_hzip_dict_.Tid__pgbnr;}
	public String Key()		{return Xoh_hzip_dict_.Key__pgbnr;}
	public byte[] Hook()	{return hook;} private byte[] hook;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_pgbnr_data data = (Xoh_pgbnr_data)data_obj;

		bfr.Add_mid(src, data.Src_bgn(), data.Src_end());

		hctx.Hzip__stat().Gallery_add();
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		int pgbnr_bgn = src_bgn;
		int pgbnr_end = Bry_find_.Find_fwd(src, Byte_ascii.Gt, src_bgn, src_end);
		if (pgbnr_end == -1) {
			Gfo_log_.Instance.Warn("hzip.pgbnr.end_not_found", "page", hpg.Url_bry_safe(), "src_bgn", src_bgn);
			pgbnr_end = pgbnr_bgn;
		}
		else
			++pgbnr_end;

		Xoh_pgbnr_data data = (Xoh_pgbnr_data)data_itm;
		data.Init_by_decode(pgbnr_bgn, pgbnr_end);
		rdr.Move_to(pgbnr_end);
	}

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_pgbnr_hzip rv = new Xoh_pgbnr_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
}
