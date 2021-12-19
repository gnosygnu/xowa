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
package gplx.xowa.htmls.core.wkrs.lnkes;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.args.BryBfrArgUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.types.custom.brys.fmts.fmtrs.*; import gplx.core.threads.poolables.*; import gplx.types.custom.brys.wtrs.args.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_lnke_wtr implements BryBfrArg, Xoh_wtr_itm {
	private final BryBfrArgBry anch_href = BryBfrArgBry.NewEmpty(), anch_cls = BryBfrArgBry.NewEmpty(), anch_content = BryBfrArgBry.NewEmpty();
	private final Bfr_arg__hatr_bry anch_title = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__title);
	private final Xoh_lnke_wtr_arg__autonum autonum_arg = new Xoh_lnke_wtr_arg__autonum();
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		BryBfrArgUtl.Clear(anch_href, anch_cls, anch_content, anch_title);
		Xoh_lnke_data data = (Xoh_lnke_data)data_itm;
		anch_href.SetByMid(src, data.Href_bgn(), data.Href_end());
		anch_cls.SetByVal(Xoh_lnke_dict_.To_html_class(data.Lnke_tid()));
		if		(data.Title_exists())	anch_title.Set_by_mid(src, data.Title_bgn(), data.Title_end());
		if		(data.Auto_exists())	anch_content.SetByArg(autonum_arg.Set_by_auto_id(data.Auto_id()));
		else if (data.Capt_exists())	anch_content.SetByMid(src, data.Capt_bgn(), data.Capt_end());
		else							anch_content.SetByMid(src, data.Href_bgn(), data.Href_end());
		return true;
	}
	public void AddToBfr(BryWtr bfr) {
		fmtr.BldToBfrMany(bfr, anch_href, anch_cls, anch_content, anch_title);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_lnke_wtr rv = new Xoh_lnke_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	private static final BryFmtr fmtr = BryFmtr.New
	( "<a href=\"~{href}\" rel=\"nofollow\" class=\"external ~{cls}\"~{title}>~{content}</a>"
	, "href", "cls", "content", "title");
}
class Xoh_lnke_wtr_arg__autonum implements BryBfrArg {
	private int auto_id;
	public BryBfrArg Set_by_auto_id(int auto_id) {this.auto_id = auto_id; return this;}
	public void AddToBfr(BryWtr bfr) {
		bfr.AddByte(AsciiByte.BrackBgn).AddIntVariable(auto_id).AddByte(AsciiByte.BrackEnd);
	}
}
