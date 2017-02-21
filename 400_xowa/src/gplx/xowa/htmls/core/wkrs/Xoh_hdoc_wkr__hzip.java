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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.ttls.*;
public class Xoh_hdoc_wkr__hzip implements Xoh_hdoc_wkr {
	private final    Xoh_stat_itm stat_itm = new Xoh_stat_itm();
	private Xoh_hzip_bfr bfr; private Xoh_hdoc_ctx hctx; private byte[] src;
	private Xoh_page hpg;
	public void On_new_page(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hpg = hpg; this.hctx = hctx; this.src = src;
		stat_itm.Clear();
	}
	public void On_txt		(int rng_bgn, int rng_end)									{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_escape	(gplx.xowa.htmls.core.wkrs.escapes.Xoh_escape_data data)	{hctx.Pool_mgr__hzip().Mw__escape().Encode1(bfr, this, hctx, hpg, Bool_.Y, src, data).Pool__rls();}
	public void On_xnde		(gplx.xowa.htmls.core.wkrs.xndes.Xoh_xnde_parser data)		{hctx.Pool_mgr__hzip().Mw__xnde().Encode1(bfr, this, hctx, hpg, Bool_.Y, src, data).Pool__rls();}
	public void On_lnki		(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_data data)		{hctx.Pool_mgr__hzip().Mw__lnki().Encode1(bfr, this, hctx, hpg, Bool_.Y, src, data).Pool__rls();}
	public void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data data)			{hctx.Pool_mgr__hzip().Mw__thm().Encode1(bfr, this, hctx, hpg, Bool_.Y, src, data).Pool__rls();}
	public void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_data data)		{hctx.Pool_mgr__hzip().Mw__gly().Encode1(bfr, this, hctx, hpg, Bool_.Y, src, data).Pool__rls();}
	public boolean Process_parse(Xoh_data_itm data) {
		Xoh_hzip_wkr wkr = null;
		switch (data.Tid()) {
			case Xoh_hzip_dict_.Tid__hdr:		wkr = hctx.Pool_mgr__hzip().Mw__hdr(); break;
			case Xoh_hzip_dict_.Tid__lnke:		wkr = hctx.Pool_mgr__hzip().Mw__lnke(); break;
			case Xoh_hzip_dict_.Tid__img:		wkr = hctx.Pool_mgr__hzip().Mw__img(); break;
			case Xoh_hzip_dict_.Tid__img_bare:	wkr = hctx.Pool_mgr__hzip().Mw__img_bare(); break;
			case Xoh_hzip_dict_.Tid__toc:		wkr = hctx.Pool_mgr__hzip().Mw__toc(); break;
			case Xoh_hzip_dict_.Tid__media:		wkr = hctx.Pool_mgr__hzip().Mw__media(); break;
			default:							throw Err_.new_unhandled(data.Tid());
		}
		wkr.Encode1(bfr, this, hctx, hpg, Bool_.Y, src, data).Pool__rls();
		return true;
	}
}
