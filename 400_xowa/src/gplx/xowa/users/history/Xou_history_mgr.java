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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.xowa.htmls.hrefs.*; 
public class Xou_history_mgr implements Gfo_invk {
	private final    Xou_history_html html_mgr = new Xou_history_html(); private Xou_history_sorter sorter = new Xou_history_sorter().Sort_fld_(Xou_history_itm.Fld_view_end).Ascending_(false);
	private final    Io_url history_fil;
	private Ordered_hash itms = Ordered_hash_.New_bry();
	private boolean load_chk = false;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xou_history_mgr(Io_url history_fil) {this.history_fil = history_fil;}
	public int Len() {return itms.Count();}
	public void Clear() {itms.Clear();}
	public Xou_history_itm Get_at(int i) {return (Xou_history_itm)itms.Get_at(i);}		
	public String Get_at_last() {
		if (!load_chk) Load();
		int len = itms.Count(); if (len == 0) return String_.new_a7(Xoa_page_.Main_page_bry);	// if no history, return Main_page (which should go to home/wiki/Main_page)
		Xou_history_itm itm = (Xou_history_itm)itms.Get_at(0);
		return String_.new_u8(Bry_.Add(itm.Wiki(), Xoh_href_.Bry__wiki, itm.Page()));
	}
	public Xou_history_itm Get_or_null(byte[] wiki, byte[] page) {
		if (!load_chk) Load();
		byte[] key = Xou_history_itm.key_(wiki, page);
		return (Xou_history_itm)itms.Get_by(key);
	}
	public boolean Has(byte[] wiki, byte[] page) {
		if (!load_chk) Load();
		byte[] key = Xou_history_itm.key_(wiki, page);
		return itms.Has(key);
	}
	public void Add(Xoae_page page) {
		Xoa_url url = page.Url();
		Xoa_ttl ttl = page.Ttl();
		byte[] page_ttl = null;
		if (page.Redirect().Itms__len() > 0)		// page was redirected; add src ttl to history, not trg; EX: UK -> United Kingdom; add "UK"; DATE:2014-02-28
			page_ttl = page.Redirect().Itms__get_at(0).Ttl().Full_db();
		else {
			page_ttl = Bry_.Add(ttl.Ns().Name_db_w_colon(), ttl.Page_txt());  // use ttl.Page_txt() b/c it normalizes space/casing (url.Page_txt does not)
			if (url.Qargs_ary().length > 0)
				page_ttl = Bry_.Add(page_ttl, url.Qargs_mgr().To_bry());
		}
		Add(url, ttl, page_ttl);
	}
	public void Add(Xoa_url url, Xoa_ttl ttl, byte[] page_ttl) {
		if (gplx.xowa.users.history.Xoud_history_mgr.Skip_history(ttl)) return;
		if (!load_chk) Load();
		byte[] key = Xou_history_itm.key_(url.Wiki_bry(), page_ttl);
		Xou_history_itm itm = (Xou_history_itm)itms.Get_by(key);
		if (itm == null) {
			itm = new Xou_history_itm(url.Wiki_bry(), To_full_db_w_qargs(url, ttl));
			itms.Add(key, itm);
		}
		itm.Tally();
	}
	private byte[] To_full_db_w_qargs(Xoa_url url, Xoa_ttl ttl) {
		byte[] page = Xoa_ttl.Replace_spaces(ttl.Full_txt_wo_qarg());
		tmp_bfr.Add(page);
		Gfo_qarg_mgr_old qarg_mgr = url.Qargs_mgr();
		qarg_mgr.To_bry(tmp_bfr, gplx.langs.htmls.encoders.Gfo_url_encoder_.Href, Bool_.N);
		return tmp_bfr.To_bry_and_clear();
	}
	public void Sort() {itms.Sort_by(sorter);}
	public void Load() {
		if (load_chk) return;
		load_chk = true;
		itms.Clear();
		Xou_history_itm_srl.Load(Io_mgr.Instance.LoadFilBry(history_fil), itms);
		itms.Sort_by(sorter);
	}
	public void Save(Xoae_app app) {
		if (!load_chk) return; // nothing loaded; nothing to save
		int itms_len = itms.Count();
		if (itms_len == 0) return;	// no items; occurs when history disable;
		itms.Sort_by(sorter);
		if (itms_len > current_itms_max) itms = Archive(app);
		byte[] ary = Xou_history_itm_srl.Save(itms);
		Io_mgr.Instance.SaveFilBry(app.Usere().Fsys_mgr().App_data_history_fil(), ary);
	}
	public Ordered_hash Archive(Xoae_app app) {
		itms.Sort_by(sorter);
		int itms_len = itms.Count();
		Ordered_hash current_itms = Ordered_hash_.New_bry();
		Ordered_hash archive_itms = Ordered_hash_.New_bry();
		for (int i = 0; i < itms_len; i++) {
			Xou_history_itm  itm = (Xou_history_itm)itms.Get_at(i);
			Ordered_hash itms_hash = (i < current_itms_reset) ? current_itms : archive_itms;
			itms_hash.Add(itm.Key(), itm);
		}
		byte[] ary = Xou_history_itm_srl.Save(archive_itms);
		Io_url url = app.Usere().Fsys_mgr().App_data_history_fil().GenNewNameOnly(DateAdp_.Now().XtoStr_fmt_yyyyMMdd_HHmmss_fff());
		Io_mgr.Instance.SaveFilBry(url, ary);
		return current_itms;
	}	private int current_itms_max = 512, current_itms_reset = 256;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_grp))				return String_.new_u8(html_mgr.Html_grp().Fmt());
		else if	(ctx.Match(k, Invk_html_grp_))				html_mgr.Html_grp().Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_itm))				return String_.new_u8(html_mgr.Html_itm().Fmt());
		else if	(ctx.Match(k, Invk_html_itm_))				html_mgr.Html_itm().Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_current_itms_max_))		current_itms_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_current_itms_reset_))	current_itms_reset = m.ReadInt("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_html_grp = "html_grp", Invk_html_grp_ = "html_grp_", Invk_html_itm = "html_itm", Invk_html_itm_ = "html_itm_", Invk_current_itms_max_ = "current_itms_max_", Invk_current_itms_reset_ = "current_itms_reset_";
	public static final    byte[] Ttl_name = Bry_.new_a7("XowaPageHistory");
	public static final    byte[] Ttl_full = Bry_.new_a7("Special:XowaPageHistory");
}	
class Xou_history_itm_srl {
	public static void Load(byte[] ary, Ordered_hash list) {
	try {
		list.Clear();
		int aryLen = ary.length;
		if (aryLen == 0) return; // no file
		Int_obj_ref pos = Int_obj_ref.New_zero();
		while (true) {
			if (pos.Val() == aryLen) break;
			Xou_history_itm itm = Xou_history_itm.csv_(ary, pos);
			byte[] key = itm.Key();
			Xou_history_itm existing = (Xou_history_itm)list.Get_by(key);
			if (existing == null)	// new itm; add
				list.Add(itm.Key(), itm);
			else					// existing itm; update
				existing.Merge(itm);
		}
	}
	catch (Exception e) {throw Err_.new_parse_exc(e, Xou_history_itm.class, String_.new_u8(ary));}
	}
	public static byte[] Save(Ordered_hash list) {
		Bry_bfr bb = Bry_bfr_.New();
		int listLen = list.Count();
		for (int i = 0; i < listLen; i++)
			((Xou_history_itm)list.Get_at(i)).Save(bb);
		return bb.To_bry();
	}
}
