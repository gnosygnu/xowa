/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.parsers.lnkis.redlinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.gui.views.*; import gplx.xowa.pages.*; import gplx.xowa.html.hdumps.core.*;
public class Xog_redlink_mgr implements GfoInvkAble {
	private Xog_win_itm win; private Xog_html_itm html_itm; private Xowe_wiki wiki; private Xoae_page page;
	private Xopg_redlink_lnki_list redlink_lnki_list; private ListAdp lnki_list; private boolean log_enabled; private Gfo_usr_dlg usr_dlg; private int thread_id;		
	public Xog_redlink_mgr(Xog_win_itm win, Xoae_page page, boolean log_enabled) {
		this.win = win; this.page = page; this.wiki = page.Wikie();
		this.html_itm = page.Tab_data().Tab().Html_itm();	// NOTE: caching locally b/c page.Tab() is sometimes null
		this.redlink_lnki_list = page.Redlink_lnki_list();
		this.lnki_list = redlink_lnki_list.Lnki_list();
		this.thread_id = redlink_lnki_list.Thread_id();
		this.log_enabled = log_enabled; this.usr_dlg = log_enabled ? Gfo_usr_dlg_.I : Gfo_usr_dlg_.Null;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run)) Redlink();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_run = "run";
	public void Redlink() {
		synchronized (this) {	// NOTE: attempt to eliminate random IndexBounds errors; DATE:2014-09-02
			if (redlink_lnki_list.Disabled()) return;
			ListAdp work_list = ListAdp_.new_();
			OrderedHash page_hash = OrderedHash_.new_bry_();
			page_hash.Clear(); // NOTE: do not clear in Page_bgn, else will fail b/c of threading; EX: Open Page -> Preview -> Save; DATE:2013-11-17
			work_list.Clear();
			int len = lnki_list.Count();
			if (log_enabled) usr_dlg.Log_many("", "", "redlink.redlink_bgn: page=~{0} total_links=~{1}", String_.new_utf8_(page.Ttl().Raw()), len);
			for (int i = 0; i < len; i++) {	// make a copy of list else thread issues
				if (win.Usr_dlg().Canceled()) return;
				if (redlink_lnki_list.Thread_id() != thread_id) return;
				work_list.Add(lnki_list.FetchAt(i));
			}
			for (int i = 0; i < len; i++) {
				if (win.Usr_dlg().Canceled()) return;
				if (redlink_lnki_list.Thread_id() != thread_id) return;
				Xop_lnki_tkn lnki = (Xop_lnki_tkn)work_list.FetchAt(i);
				Xoa_ttl ttl = lnki.Ttl();
				Xowd_page_itm db_page = new Xowd_page_itm().Ttl_(ttl);
				byte[] full_txt = ttl.Full_db();
				if (!page_hash.Has(full_txt))
					page_hash.Add(full_txt, db_page);
			}
			int page_len = page_hash.Count();
			for (int i = 0; i < page_len; i += Batch_size) {
				if (win.Usr_dlg().Canceled()) return;
				if (redlink_lnki_list.Thread_id() != thread_id) return;
				int end = i + Batch_size;
				if (end > page_len) end = page_len;
				wiki.Db_mgr().Load_mgr().Load_by_ttls(win.Usr_dlg(), page_hash, Bool_.Y, i, end);
			}
			int redlink_count = 0;
			Bry_bfr bfr = null;
			boolean variants_enabled = wiki.Lang().Vnt_mgr().Enabled();
			Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();
			Xopg_redlink_idx_list redlink_mgr = page.Hdump_data().Redlink_mgr();
			for (int j = 0; j < len; j++) {
				Xop_lnki_tkn lnki = (Xop_lnki_tkn)work_list.FetchAt(j);
				byte[] full_db = lnki.Ttl().Full_db();
				Xowd_page_itm db_page = (Xowd_page_itm)page_hash.Fetch(full_db);
				if (db_page == null) continue;	// pages shouldn't be null, but just in case
				if (!db_page.Exists()) {
					String lnki_id = Xopg_redlink_lnki_list.Lnki_id_prefix + Int_.Xto_str(lnki.Html_id());
					if (variants_enabled) {
						Xowd_page_itm vnt_page = vnt_mgr.Convert_ttl(wiki, lnki.Ttl());
						if (vnt_page != null) {
							Xoa_ttl vnt_ttl = Xoa_ttl.parse_(wiki, lnki.Ttl().Ns().Id(), vnt_page.Ttl_page_db());
							html_itm.Html_atr_set(lnki_id, "href", "/wiki/" + String_.new_utf8_(vnt_ttl.Full_url()));
							if (!String_.Eq(vnt_mgr.Html_style(), ""))
								html_itm.Html_atr_set(lnki_id, "style", vnt_mgr.Html_style());
							continue;
						}
					}
					if (log_enabled) {
						if (bfr == null) bfr = Bry_bfr.new_();
						bfr.Add_int_variable(lnki.Html_id()).Add_byte_pipe().Add(Xop_tkn_.Lnki_bgn).Add(full_db).Add(Xop_tkn_.Lnki_end).Add_byte(Byte_ascii.Semic).Add_byte_space();
					}
					if (win.Usr_dlg().Canceled()) return;
					if (redlink_lnki_list.Thread_id() != thread_id) return;
					int uid = lnki.Html_id();
					gplx.xowa.files.gui.Js_img_mgr.Update_link_missing(html_itm, Xopg_redlink_lnki_list.Lnki_id_prefix + Int_.Xto_str(uid));
					redlink_mgr.Add(uid);
					++redlink_count;
				}
			}
			if (log_enabled)
				usr_dlg.Log_many("", "", "redlink.redlink_end: redlinks_run=~{0} links=~{1}", redlink_count, bfr == null ? String_.Empty : bfr.Xto_str_and_clear());
		}
	}
	public static final Xog_redlink_mgr Null = new Xog_redlink_mgr(); Xog_redlink_mgr() {}
	private static final int Batch_size = 32;
}
class Xog_redlink_wkr {
	public static void Redlink(Xog_html_itm html_itm, Int_list list) {
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			int uid = list.Get_at(i);
			Redlink(html_itm, uid);
		}
	}
	public static void Redlink(Xog_html_itm html_itm, int uid) {
		gplx.xowa.files.gui.Js_img_mgr.Update_link_missing(html_itm, Xopg_redlink_lnki_list.Lnki_id_prefix + Int_.Xto_str(uid));
	}
}
