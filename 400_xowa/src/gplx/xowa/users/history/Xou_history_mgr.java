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
package gplx.xowa.users.history;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDateNow;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.StringUtl;
import gplx.core.net.qargs.Gfo_qarg_mgr_old;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xoa_page_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.htmls.hrefs.Xoh_href_;
public class Xou_history_mgr implements Gfo_invk {
	private final Xou_history_html html_mgr = new Xou_history_html(); private Xou_history_sorter sorter = new Xou_history_sorter().Sort_fld_(Xou_history_itm.Fld_view_end).Ascending_(false);
	private final Io_url history_fil;
	private Ordered_hash itms = Ordered_hash_.New_bry();
	private boolean load_chk = false;
	private final BryWtr tmp_bfr = BryWtr.New();
	private boolean log_all = false;
	public Xou_history_mgr(Io_url history_fil) {
		this.history_fil = history_fil;
	}
	public void Init_by_app(Xoa_app app) {
		app.Cfg().Bind_many_app(this, Cfg__enabled, Cfg__log_all);
	}
	public boolean Enabled() {return enabled;} private boolean enabled = true;
	public int Len() {return itms.Len();}
	public void Clear() {itms.Clear();}
	public Xou_history_itm Get_at(int i) {return (Xou_history_itm)itms.GetAt(i);}
	public String Get_at_last() {
		if (!load_chk) Load();
		int len = itms.Len(); if (len == 0) return StringUtl.NewA7(Xoa_page_.Main_page_bry);	// if no history, return Main_page (which should go to home/wiki/Main_page)
		Xou_history_itm itm = (Xou_history_itm)itms.GetAt(0);
		return StringUtl.NewU8(BryUtl.Add(itm.Wiki(), Xoh_href_.Bry__wiki, itm.Page()));
	}
	public Xou_history_itm Get_or_null(byte[] wiki, byte[] page) {
		if (!load_chk) Load();
		byte[] key = Xou_history_itm.key_(wiki, page);
		return (Xou_history_itm)itms.GetByOrNull(key);
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
		if (page.Redirect_trail().Itms__len() > 0)		// page was redirected; add src ttl to history, not trg; EX: UK -> United Kingdom; add "UK"; DATE:2014-02-28
			page_ttl = page.Redirect_trail().Itms__get_at_0th_or_null().Ttl().Page_db();
		else {
			page_ttl = BryUtl.Add(ttl.Ns().Name_db_w_colon(), ttl.Page_txt());  // use ttl.Page_txt() b/c it normalizes space/casing (url.Page_txt does not)
			if (url.Qargs_ary().length > 0)
				page_ttl = BryUtl.Add(page_ttl, url.Qargs_mgr().To_bry());
		}
		Add(page.Wiki().App(), url, ttl, page_ttl);
	}
	public void Add(Xoa_app app, Xoa_url url, Xoa_ttl ttl, byte[] page_ttl) {
		if (gplx.xowa.users.history.Xoud_history_mgr.Skip_history(ttl)) return;
		if (!load_chk) Load();
		byte[] key = Xou_history_itm.key_(url.Wiki_bry(), page_ttl);
		Xou_history_itm itm = (Xou_history_itm)itms.GetByOrNull(key);
		if (itm == null) {
			itm = new Xou_history_itm(url.Wiki_bry(), To_full_db_w_qargs(url, ttl));
			itms.Add(key, itm);
		}
		if (log_all)
			Io_mgr.Instance.AppendFilStr(history_fil.GenNewNameAndExt("log_all.csv"), StringUtl.Format("{0}|{1}|{2}\n", GfoDateNow.Get().ToStrFmtIso8561WithTz(), itm.Wiki(), itm.Page()));
		itm.Tally();
	}
	private byte[] To_full_db_w_qargs(Xoa_url url, Xoa_ttl ttl) {
		byte[] page = Xoa_ttl.Replace_spaces(ttl.Full_txt_wo_qarg());
		tmp_bfr.Add(page);
		Gfo_qarg_mgr_old qarg_mgr = url.Qargs_mgr();
		qarg_mgr.To_bry(tmp_bfr, gplx.langs.htmls.encoders.Gfo_url_encoder_.Href, BoolUtl.N);
		return tmp_bfr.ToBryAndClear();
	}
	public void Sort() {itms.SortBy(sorter);}
	public void Load() {
		if (load_chk) return;
		load_chk = true;
		itms.Clear();
		Xou_history_itm_srl.Load(Io_mgr.Instance.LoadFilBry(history_fil), itms);
		itms.SortBy(sorter);
	}
	public void Save(Xoae_app app) {
		if (!load_chk) return; // nothing loaded; nothing to save
		int itms_len = itms.Len();
		if (itms_len == 0) return;	// no items; occurs when history disable;
		itms.SortBy(sorter);
		if (itms_len > current_itms_max) itms = Archive(app);
		byte[] ary = Xou_history_itm_srl.Save(itms);
		Io_mgr.Instance.SaveFilBry(app.Usere().Fsys_mgr().App_data_history_fil(), ary);
	}
	public Ordered_hash Archive(Xoae_app app) {
		// sort all itms; other init
		itms.SortBy(sorter);
		Ordered_hash current_itms = Ordered_hash_.New_bry();
		Ordered_hash archive_itms = Ordered_hash_.New_bry();

		// iterate over all itms
		int itms_len = itms.Len();
		for (int i = 0; i < itms_len; i++) {
			Xou_history_itm itm = (Xou_history_itm)itms.GetAt(i);
			Ordered_hash itms_hash = (i < current_itms_reset) ? current_itms : archive_itms;	// if < cutoff, add to current file; else add to archive
			itms_hash.AddIfDupeUseNth(itm.Key(), itm);	// NOTE: dupes should not exist, but if they do, ignore it; else app won't close on first time; DATE:2016-07-14
		}

		// save archive
		byte[] ary = Xou_history_itm_srl.Save(archive_itms);
		Io_url url = app.Usere().Fsys_mgr().App_data_history_fil().GenNewNameOnly(GfoDateNow.Get().ToStrFmt_yyyyMMdd_HHmmss_fff());
		Io_mgr.Instance.SaveFilBry(url, ary);
		return current_itms;
	}	private int current_itms_max = 512, current_itms_reset = 256;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_grp))				return StringUtl.NewU8(html_mgr.Html_grp().Fmt());
		else if	(ctx.Match(k, Invk_html_grp_))				html_mgr.Html_grp().FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_itm))				return StringUtl.NewU8(html_mgr.Html_itm().Fmt());
		else if	(ctx.Match(k, Invk_html_itm_))				html_mgr.Html_itm().FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_current_itms_max_))		current_itms_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_current_itms_reset_))	current_itms_reset = m.ReadInt("v");
		else if	(ctx.Match(k, Cfg__enabled))				enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__log_all))				log_all = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_html_grp = "html_grp", Invk_html_grp_ = "html_grp_", Invk_html_itm = "html_itm", Invk_html_itm_ = "html_itm_", Invk_current_itms_max_ = "current_itms_max_", Invk_current_itms_reset_ = "current_itms_reset_";
	public static final byte[] Ttl_name = BryUtl.NewA7("XowaPageHistory");
	public static final byte[] Ttl_full = BryUtl.NewA7("Special:XowaPageHistory");
	private static final String Cfg__enabled = "xowa.app.page_history.enabled", Cfg__log_all = "xowa.app.page_history.log_all";
}	
class Xou_history_itm_srl {
	public static void Load(byte[] ary, Ordered_hash list) {
	try {
		list.Clear();
		int aryLen = ary.length;
		if (aryLen == 0) return; // no file
		IntRef pos = IntRef.NewZero();
		while (true) {
			if (pos.Val() == aryLen) break;
			Xou_history_itm itm = Xou_history_itm.csv_(ary, pos);
			byte[] key = itm.Key();
			Xou_history_itm existing = (Xou_history_itm)list.GetByOrNull(key);
			if (existing == null)	// new itm; add
				list.Add(itm.Key(), itm);
			else					// existing itm; update
				existing.Merge(itm);
		}
	}
	catch (Exception e) {throw ErrUtl.NewParse(e, Xou_history_itm.class, StringUtl.NewU8(ary));}
	}
	public static byte[] Save(Ordered_hash list) {
		BryWtr bb = BryWtr.New();
		int listLen = list.Len();
		for (int i = 0; i < listLen; i++)
			((Xou_history_itm)list.GetAt(i)).Save(bb);
		return bb.ToBry();
	}
}
