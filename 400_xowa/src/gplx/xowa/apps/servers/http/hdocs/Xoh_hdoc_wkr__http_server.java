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
package gplx.xowa.apps.servers.http.hdocs;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.core.net.Gfo_protocol_itm;
import gplx.langs.htmls.docs.Gfh_atr;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.errs.ErrUtl;
import gplx.xowa.apps.servers.http.Http_server_wkr;
import gplx.xowa.htmls.Xoh_page;
import gplx.xowa.htmls.core.hzips.Xoh_data_itm;
import gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_;
import gplx.xowa.htmls.core.wkrs.Xoh_hdoc_ctx;
import gplx.xowa.htmls.core.wkrs.Xoh_hdoc_wkr;
import gplx.xowa.htmls.core.wkrs.addons.forms.Xoh_form_data;
import gplx.xowa.htmls.core.wkrs.imgs.Xoh_img_data;
import gplx.xowa.htmls.core.wkrs.lnkis.anchs.Xoh_anch_href_data;
import gplx.xowa.htmls.hrefs.Xoh_href_;
class Xoh_hdoc_wkr__http_server implements Xoh_hdoc_wkr {
	private BryWtr bfr; private Xoh_page hpg; private byte[] src;
	private byte[] root_http_dir; // EX: file:///C:/xowa/
	public BryWtr Bfr() {return bfr;}
	public void On_page_bgn(BryWtr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.bfr = bfr; this.hpg = hpg; this.src = src;
		if (root_http_dir == null) {
			this.root_http_dir = hpg.Wiki().App().Fsys_mgr().Http_root().To_http_file_bry();
			href_trie.Add_bry_byte(root_http_dir, Tid__fsys);
		}
	}
	public void On_page_end() {}
	public void On_txt    (int rng_bgn, int rng_end)                               {bfr.AddMid(src, rng_bgn, rng_end);}
	public void On_escape (gplx.xowa.htmls.core.wkrs.escapes.Xoh_escape_data data) {bfr.Add(data.Hook());}
	public void On_xnde   (gplx.xowa.htmls.core.wkrs.xndes.Xoh_xnde_parser data)   {bfr.AddMid(src, data.Src_bgn(), data.Src_end());}
	public void On_lnki   (gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_data data)     {
		// get atr (with null checks)
		Xoh_anch_href_data href_itm = data.Href_itm();
		if (href_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "lnki missing href; page=~{0} src=~{1}", hpg.Url_bry_safe(), BryLni.Mid(src, data.Src_bgn(), data.Src_end()));
			return;
		}
		Add_href(data.Src_bgn(), data.Src_end(), href_itm.Atr().Val_bgn(), href_itm.Atr().Val_end());
	}
	public boolean On_thm    (gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data data) {
		// NOTE: not parsing thm b/c Xoh_thm_data does not expose the <a> in the magnify div
		// In addition, On_thm would also need to parse any <a> or <img> in the caption
		return false;
	}
	public void On_gly(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_data data) {
		bfr.AddMid(src, data.Src_bgn(), data.Src_end());
	}
	public boolean Process_parse(Xoh_data_itm data) {
		switch (data.Tid()) {
			case Xoh_hzip_dict_.Tid__img: {
				Xoh_img_data img_data = (Xoh_img_data)data;
				Xoh_anch_href_data anch_href = img_data.Anch_href();
				Add_href(data.Src_bgn(), anch_href.Rng_end(), anch_href.Atr().Val_bgn(), anch_href.Atr().Val_end());
				Add_href(anch_href.Rng_end(), data.Src_end(), img_data.Img_src().Src_bgn(), img_data.Img_src().Src_end());
				return true;
			}
//				case Xoh_hzip_dict_.Tid__img_bare:
//				case Xoh_hzip_dict_.Tid__media:
//					Gfo_usr_dlg_.Instance.Warn_many("", "", "htxt_wkr does not support media; url=~{0}", hpg.Url_bry_safe());
//					return false;
			case Xoh_hzip_dict_.Tid__form:
				Xoh_form_data form_data = (Xoh_form_data)data;
				Gfh_atr action_atr = form_data.Action_atr();
				bfr.AddMid(src, form_data.Src_bgn(), action_atr.Val_bgn());
				if (BryLni.Eq(src, action_atr.Val_bgn(), action_atr.Val_bgn() + Xoh_href_.Bry__wiki.length, Xoh_href_.Bry__wiki)) {
					bfr.AddByteSlash().Add(hpg.Wiki().Domain_bry());
				}
				bfr.AddMid(src, action_atr.Val_bgn(), form_data.Src_end());
				return true;
			case Xoh_hzip_dict_.Tid__hdr:
			case Xoh_hzip_dict_.Tid__toc:
			case Xoh_hzip_dict_.Tid__lnke:
			default:
				bfr.AddMid(src, data.Src_bgn(), data.Src_end());
				break;
		}
		return true;
	}
	private void Add_href(int itm_bgn, int itm_end, int href_bgn, int href_end) {
		// add everything up to href_bgn
		bfr.AddMid(src, itm_bgn, href_bgn);

		// now "fix" href
		Btrie_rv trv = new Btrie_rv();
		Object tid_obj = href_trie.MatchAt(trv, src, href_bgn, href_end);
		if (tid_obj != null) {
			byte tid = ((ByteVal)tid_obj).Val();
			switch (tid) {
				case Tid__wiki:
					bfr.AddByteSlash().Add(hpg.Wiki().Domain_bry());
					break;
				case Tid__xcmd:
					bfr.AddStrA7("/exec/");
					href_bgn = trv.Pos();
					break;
				case Tid__site:
					href_bgn = trv.Pos();
					break;
				case Tid__fsys:
					bfr.Add(Http_server_wkr.Url__fsys);
					href_bgn = trv.Pos();
					break;
				case Tid__fsys_bug:
					bfr.Add(Http_server_wkr.Url__fsys);
					href_bgn = trv.Pos() - 5; // 5 = "file/".length
					break;
				default:
					throw ErrUtl.NewUnhandled(tid);
			}
		}

		// add remainder of href_val
		bfr.AddMid(src, href_bgn, href_end);

		// add everything after href
		bfr.AddMid(src, href_end, itm_end);
	}
	public static final byte[] Path_lnxusr_xowa_file = BryUtl.NewA7("file:////home/lnxusr/xowa/file/");
	private static final byte[] Bry__site = BryUtl.NewA7("/site");
	private static final byte
	  Tid__wiki      = 1
	, Tid__xcmd      = 2
	, Tid__site      = 3
	, Tid__fsys      = 4
	, Tid__fsys_bug  = 5
	;
	private static final Btrie_slim_mgr href_trie = Btrie_slim_mgr.ci_u8()
		.Add_bry_byte(Xoh_href_.Bry__wiki        , Tid__wiki)
		.Add_bry_byte(Gfo_protocol_itm.Bry_xcmd  , Tid__xcmd)
		.Add_bry_byte(Bry__site                  , Tid__site)
		.Add_bry_byte(Path_lnxusr_xowa_file      , Tid__fsys_bug)
		;
}
