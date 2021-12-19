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
package gplx.xowa.htmls.core.wkrs.addons.forms;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_form_data implements Xoh_data_itm {
	public int Tid()			{return Xoh_hzip_dict_.Tid__form;}
	public int Src_bgn()		{return src_bgn;} private int src_bgn;
	public int Src_end()		{return src_end;} private int src_end;
	public Gfh_atr Action_atr() {return action_atr;} private Gfh_atr action_atr;
	public void Clear() {
		this.src_bgn = this.src_end = -1;
		this.action_atr = null;
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag cur, Gfh_tag nxt) {
		return Parse1(hdoc_wkr, hctx, tag_rdr, src, cur);
	}
	public boolean Parse1(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag lhs_tag) {
		// init
		this.Clear();
		this.src_bgn = lhs_tag.Src_bgn();

		// get action
		this.action_atr = lhs_tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__action);

		// get tail; </form>
		Gfh_tag rhs_tag = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__form);
		this.src_end = rhs_tag.Src_end();

		// process
		return hdoc_wkr.Process_parse(this);
	}
	public static final byte[] Hook_bry = BryUtl.NewA7("<form");

	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_form_data rv = new Xoh_form_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
}
