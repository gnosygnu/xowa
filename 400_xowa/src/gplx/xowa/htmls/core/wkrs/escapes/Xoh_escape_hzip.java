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
package gplx.xowa.htmls.core.wkrs.escapes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_escape_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private byte escape_byte;
	public int Tid() {return Xoh_hzip_dict_.Tid__escape;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public String Key() {return Xoh_hzip_dict_.Key__escape;}
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_escape_data data = (Xoh_escape_data)data_obj;
		bfr.Add(hook);			// EX: 1,0
		bfr.Add(data.Hook());	// EX: 2
		hctx.Hzip__stat().Escape_add(escape_byte);
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		bfr.Add_byte(rdr.Read_byte());
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {
		Xoh_escape_hzip rv = new Xoh_escape_hzip();
		rv.pool_mgr = mgr; rv.pool_idx = idx;
		rv.hook = (byte[])args[0];
		rv.escape_byte = rv.hook[0];
		return rv;
	}
}
