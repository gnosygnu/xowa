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
import gplx.langs.htmls.docs.*;
import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_hdoc_wkr__make implements Xoh_hdoc_wkr {
	private Xoh_hzip_bfr bfr; private Xoh_page hpg; private Xoh_hdoc_ctx hctx; private byte[] src;
	private final Xoh_hdr_wtr wkr__hdr = new Xoh_hdr_wtr();
	private final Xoh_img_wtr wkr__img = new Xoh_img_wtr();		
	public void On_new_page(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hpg = hpg; this.hctx = hctx; this.src = src;
	}
	public void On_txt		(int rng_bgn, int rng_end)									{bfr.Add_mid(src, rng_bgn, rng_end);}
	public void On_escape	(gplx.xowa.htmls.core.wkrs.escapes.Xoh_escape_data data)	{bfr.Add(data.Hook());}
	public void On_xnde		(gplx.xowa.htmls.core.wkrs.xndes.Xoh_xnde_parser data)		{bfr.Add_mid(src, data.Src_bgn(), data.Src_end());}
	public void On_lnki		(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_data data)		{bfr.Add_mid(src, data.Src_bgn(), data.Src_end());}
	public void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data data)			{bfr.Add_mid(src, data.Src_bgn(), data.Src_end());}
	public void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_data data)		{}
	public boolean Process_parse(Xoh_data_itm data) {
		switch (data.Tid()) {
			case Xoh_hzip_dict_.Tid__img:	wkr__img.Init_by_parse(bfr, hpg, hctx, src, (gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_data)data); return true;
			case Xoh_hzip_dict_.Tid__hdr:	wkr__hdr.Init_by_parse(bfr, hpg, hctx, src, (gplx.xowa.htmls.core.wkrs.hdrs.Xoh_hdr_data)data); return true;
			case Xoh_hzip_dict_.Tid__lnke:	break;
			default:						throw Err_.new_unhandled(data.Tid());
		}
		bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
		return true;
	}
}
