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
package gplx.xowa.bldrs.setups.maints; import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.Yn;
import gplx.types.commons.GfoDate;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xowe_wiki;
public class Xow_maint_mgr implements Gfo_invk {
	public Xow_maint_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		maint_mgr = wiki.Appe().Setup_mgr().Maint_mgr();
	}	private Xowe_wiki wiki; private Xoa_maint_mgr maint_mgr;
	public GfoDate Wmf_dump_date() {
		maint_mgr.Wmf_dump_status_loaded_assert();
		return wmf_dump_date;
	}	public Xow_maint_mgr Wmf_dump_date_(GfoDate v) {this.wmf_dump_date = v; return this;} private GfoDate wmf_dump_date;
	public boolean Wmf_dump_done() {return wmf_dump_done;} public Xow_maint_mgr Wmf_dump_done_(boolean v) {this.wmf_dump_done = v; return this;} private boolean wmf_dump_done;
	public byte[] Wmf_dump_status() {return wmf_dump_status;} public Xow_maint_mgr Wmf_dump_status_(byte[] v) {this.wmf_dump_status = v; return this;} private byte[] wmf_dump_status;
	public GfoDate Wiki_dump_date() {
		if (wiki_dump_date == null)
			wiki_dump_date = wiki.Db_mgr().Dump_date_query();
		return wiki_dump_date;
	}	private GfoDate wiki_dump_date;
	public boolean Wiki_update_needed() {
		if (this.Wiki_dump_date() == null) return false;	// will be null if a custom wiki (i.e.: not on http://dumps.wikimedia.org/backup-index.html)
		if (this.Wmf_dump_date() == null) return false;		// also null if custom wiki
		return this.Wmf_dump_date().Diff(this.Wiki_dump_date()).TotalDays().ToDouble() > 1;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wmf_dump_date)) 			return GfoDateUtl_ToStrFmtOrAsStr(Wmf_dump_date(), "yyyy-MM-dd", "");
		else if	(ctx.Match(k, Invk_wmf_dump_date_)) 		Wmf_dump_date_(m.ReadDate("v"));
		else if	(ctx.Match(k, Invk_wmf_dump_done)) 			return Yn.To_str(wmf_dump_done);
		else if	(ctx.Match(k, Invk_wmf_dump_done_)) 		wmf_dump_done = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_wmf_dump_status)) 		return StringUtl.NewU8(wmf_dump_status);
		else if	(ctx.Match(k, Invk_wmf_dump_status_)) 		wmf_dump_status = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_wiki_dump_date)) 		return GfoDateUtl_ToStrFmtOrAsStr(Wiki_dump_date(), "yyyy-MM-dd", "");
		else if	(ctx.Match(k, Invk_wiki_dump_date_)) 		wiki_dump_date = m.ReadDate("v");
		else if	(ctx.Match(k, Invk_wiki_update_needed)) 	return Yn.To_str(Wiki_update_needed());
		else if	(ctx.Match(k, Invk_wiki_dump_date_)) 		wiki_dump_date = m.ReadDate("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_wmf_dump_date = "wmf_dump_date", Invk_wmf_dump_date_ = "wmf_dump_date_", Invk_wmf_dump_done = "wmf_dump_done", Invk_wmf_dump_done_ = "wmf_dump_done_"
	, Invk_wmf_dump_status = "wmf_dump_status", Invk_wmf_dump_status_ = "wmf_dump_status_", Invk_wiki_dump_date = "wiki_dump_date", Invk_wiki_dump_date_ = "wiki_dump_date_"
	, Invk_wiki_update_needed = "wiki_update_needed"
	;
	private static String GfoDateUtl_ToStrFmtOrAsStr(GfoDate v, String fmt, String or) {
		return v == null ? or : v.ToStrFmt(fmt);
	}
}
