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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import gplx.core.threads.*; import gplx.core.primitives.*; import gplx.core.js.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.specials.*;
class Xow_popup_mgr_ {
	public static String Bld_js_cmd(Js_wtr js_wtr, String cbk, byte[] mode, byte[] href, byte[] html) {
		js_wtr.Func_init(cbk);
		js_wtr.Prm_bry(mode);
		js_wtr.Prm_bry(href);
		js_wtr.Prm_bry(html);
		js_wtr.Func_term();
		return js_wtr.To_str_and_clear();
	}
}
class Load_popup_wkr implements Gfo_thread_wkr {
	private Xow_popup_itm itm; private Xoae_page cur_page; private Xoa_url tmp_url;
	private Hash_adp ns_allowed_regy; 
	private Int_obj_ref ns_allowed_regy_key = Int_obj_ref.New_zero();
	public Load_popup_wkr(Xowe_wiki wiki, Xoae_page cur_page, Xow_popup_itm itm, Xoa_url tmp_url, Hash_adp ns_allowed_regy, Int_obj_ref ns_allowed_regy_key) {
		this.wiki = wiki; this.cur_page = cur_page; this.itm = itm; this.tmp_url = tmp_url; this.ns_allowed_regy = ns_allowed_regy; this.ns_allowed_regy_key = ns_allowed_regy_key;
	}
	public String			Thread__name() {return "xowa.load_popup_wkr";}
	public boolean			Thread__resume() {return false;}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public byte[] Rslt_bry() {return rslt_bry;} private byte[] rslt_bry;
	public boolean Rslt_done() {return rslt_done;} private boolean rslt_done;
	public void Rslt_(byte[] bry) {this.rslt_done = true; rslt_bry = bry;}
	public void Thread__exec() {
		Xoae_app app = wiki.Appe();
		try {
			if (itm.Canceled()) return;
			cur_page.Popup_mgr().Itms().Add_if_dupe_use_nth(itm.Popup_id(), itm);
			app.Html__href_parser().Parse_as_url(tmp_url, itm.Page_href(), wiki, cur_page.Ttl().Full_url());	// NOTE: use Full_url, not Page_url, else anchors won't work for non-main ns; PAGE:en.w:Project:Sandbox; DATE:2014-08-07
			if (!Xoa_url_.Tid_is_pagelike(tmp_url.Tid())) return;		// NOTE: do not get popups for "file:///"; DATE:2015-04-05
			Xowe_wiki popup_wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_null(tmp_url.Wiki_bry());
			popup_wiki.Init_assert();
			Xoa_ttl popup_ttl = Xoa_ttl.Parse(popup_wiki, tmp_url.To_bry_page_w_anch());
			switch (popup_ttl.Ns().Id()) {
				case Xow_ns_.Tid__media:
				case Xow_ns_.Tid__file:
					return;		// do not popup for media or file
				case Xow_ns_.Tid__special:
					if (!Xow_special_meta_.Itm__popup_history.Match_ttl(popup_ttl)) return;	// do not popup for special, unless popupHistory; DATE:2015-04-20
					break;
			}
			if (ns_allowed_regy.Count() > 0 && !ns_allowed_regy.Has(ns_allowed_regy_key.Val_(popup_ttl.Ns().Id()))) return;
			itm.Init(popup_wiki.Domain_bry(), popup_ttl);
			Xoae_page popup_page = popup_wiki.Data_mgr().Load_page_by_ttl(popup_ttl);
			byte[] rv = popup_wiki.Html_mgr().Head_mgr().Popup_mgr().Parser().Parse(wiki, popup_page, cur_page.Tab_data().Tab(), itm);
			Xow_popup_mgr.Update_progress_bar(app, wiki, cur_page, itm);
			Rslt_(rv);
		}
		catch(Exception e) {
			app.Usr_dlg().Warn_many("", "", "failed to get popup: href=~{0} err=~{1}", itm.Page_href(), Err_.Message_gplx_full(e));
			Rslt_(null);
		}
		finally {
			app.Thread_mgr_old().Page_load_mgr().Resume();
		}
	}
}
