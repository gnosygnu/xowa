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
import gplx.langs.htmls.docs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*; import gplx.xowa.htmls.core.wkrs.tocs.*;
import gplx.xowa.wikis.ttls.*; 
public class Xoh_hdoc_wkr__make implements Xoh_hdoc_wkr {
	private Bry_bfr bfr; private Xoh_page hpg; private Xoh_hdoc_ctx hctx; private byte[] src;
	private final Xoh_hdr_wtr wkr__hdr = new Xoh_hdr_wtr();
	private final Xoh_img_wtr wkr__img = new Xoh_img_wtr();
	private final Xoh_page_bfr page_bfr = new Xoh_page_bfr();
	private boolean toc_enabled;
	private int html_uid;
	public Bry_bfr Bfr() {return bfr;}
	public void On_page_bgn(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hpg = hpg; this.hctx = hctx; this.src = src;
		this.html_uid = 0;
		this.toc_enabled = !gplx.core.envs.Op_sys.Cur().Tid_is_drd();
		if (toc_enabled)
			this.page_bfr.Init(bfr);
	}
	public void On_page_end() {
		if (toc_enabled)
			page_bfr.Commit(hpg);
	}
	public void On_txt(int rng_bgn, int rng_end) {
		// text; just add it
		bfr.Add_mid(src, rng_bgn, rng_end);
	}
	public void On_escape(gplx.xowa.htmls.core.wkrs.escapes.Xoh_escape_data data) {
		// hzip escape byte ((byte)27); should never happen but if it does, add it
		bfr.Add(data.Hook());
	}
	public void On_xnde(gplx.xowa.htmls.core.wkrs.xndes.Xoh_xnde_parser data) {
		// regular xml node; just add it
		bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
	}
	public void On_lnki(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_data data)	{
		// <a> node
		// if not "/wiki/" link, just add html and exit; ISSUE#:391; DATE:2019-03-20
		if (data.Href_itm().Tid() != Xoh_anch_href_data.Tid__wiki) {
			bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
			return;
		}

		// increment html_uid and add "id=xolnki_"
		this.html_uid = Lnki_redlink_reg(hpg, hctx, data.Href_itm().Ttl_full_txt(), html_uid);
		int src_bgn_lhs = data.Src_bgn();
		int src_bgn_rhs = src_bgn_lhs + 3; // +3 to skip over "<a "
		if (Bry_.Match(src, src_bgn_lhs, src_bgn_rhs, Bry__a__bgn)) {
			bfr.Add(Bry__a__id);
			bfr.Add_int_variable(html_uid);
			bfr.Add_byte_quote().Add_byte_space();
			bfr.Add_mid(src, src_bgn_rhs, data.Src_end());
		}
		else {
			bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
			Gfo_usr_dlg_.Instance.Warn_many("", "", "anchor hook should start with <a; url=~{0}", hpg.Url_bry_safe());
		}
	}
	public boolean On_thm(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data data) {
		Xoh_img_data img_data = (gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_data)data.Img_data();
		// add thm html before img
		bfr.Add_mid(src, data.Src_bgn(), img_data.Src_bgn());
		// add img
		wkr__img.Init_by_parse(bfr, hpg, hctx, src, img_data);
		// add thm html after img
		bfr.Add_mid(src, img_data.Src_end(), data.Src_end());
		return true;
	}
	public void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_data data) {
		// <gallery> section; loop itms and call wkr__img on each image while concatenating anything inbetween
		int prv = data.Src_bgn();
		int len = data.Itms__len();
		for (int i = 0; i < len; i++) {
			gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_itm_data itm = data.Itms__get_at(i);
			bfr.Add_mid(src, prv, itm.Img_data().Src_bgn());
			prv = itm.Img_data().Src_end();
			wkr__img.Init_by_parse(bfr, hpg, hctx, src, (Xoh_img_data)itm.Img_data());
		}
		bfr.Add_mid(src, prv, data.Src_end());
		hpg.Xtn__gallery_exists_y_();
	}
	public boolean Process_parse(Xoh_data_itm data) {
		switch (data.Tid()) {
			case Xoh_hzip_dict_.Tid__hdr:
				return wkr__hdr.Init_by_parse(bfr, hpg, hctx, src, (Xoh_hdr_data)data);
			case Xoh_hzip_dict_.Tid__img:
				return wkr__img.Init_by_parse(bfr, hpg, hctx, src, (Xoh_img_data)data);
			case Xoh_hzip_dict_.Tid__toc:
				// process <xowa_toc> tag by splitting bfr into two: bfr-before-toc and bfr-after-toc
				if (toc_enabled) {
					Xoh_toc_data toc_data = (Xoh_toc_data)data;
					this.bfr = page_bfr.Split_by_toc(toc_data.Toc_mode());
				}
				break;
			// hzip_lnke just reconstructs html
			case Xoh_hzip_dict_.Tid__lnke:
			// hzip_img_bare just reconstructs html; note that img_bare is for icons (expand image)
			case Xoh_hzip_dict_.Tid__img_bare:
			default:
				bfr.Add_mid(src, data.Src_bgn(), data.Src_end());
				break;
			case Xoh_hzip_dict_.Tid__media:	
				Gfo_usr_dlg_.Instance.Warn_many("", "", "htxt_wkr does not support media; url=~{0}", hpg.Url_bry_safe());
				return false;
		}
		return true;
	}
	public static int Lnki_redlink_reg(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] href_bry, int html_uid) {
		if (hctx.Mode_is_diff()) return html_uid; // PERF: don't do redlinks during hzip_diff
		try {
			Xoa_ttl ttl = hpg.Wiki().Ttl_parse(Gfo_url_encoder_.Href.Decode(href_bry));
			Xopg_lnki_itm__hdump lnki_itm = new Xopg_lnki_itm__hdump(ttl);
			hpg.Html_data().Redlink_list().Add(lnki_itm);
			return lnki_itm.Html_uid();
		} 
		catch (Exception e) {
			Gfo_log_.Instance.Warn("failed to add lnki to redlinks", "page", hpg.Url_bry_safe(), "href_bry", href_bry, "e", Err_.Message_gplx_log(e));
			return html_uid;
		}
	}
	private static final byte[] Bry__a__bgn = Bry_.new_a7("<a "), Bry__a__id = Bry_.new_a7("<a id=\"xolnki_");
}
