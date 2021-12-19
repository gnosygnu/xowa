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
package gplx.xowa.addons.apps.cfgs;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.IntUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.Yn;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.apps.cfgs.mgrs.caches.Xocfg_cache_mgr;
import gplx.xowa.addons.apps.cfgs.mgrs.dflts.Xocfg_dflt_mgr;
import gplx.xowa.addons.apps.cfgs.mgrs.execs.Xocfg_exec_mgr;
import gplx.xowa.addons.apps.cfgs.mgrs.types.Xocfg_type_mgr;
public class Xocfg_mgr implements Gfo_invk {
	public Xocfg_mgr() {
		this.dflt_mgr = new Xocfg_dflt_mgr(cache_mgr);
	}
	public Xocfg_cache_mgr Cache_mgr() {return cache_mgr;} private final Xocfg_cache_mgr cache_mgr = new Xocfg_cache_mgr();
	public Xocfg_type_mgr Type_mgr() {return type_mgr;} private final Xocfg_type_mgr type_mgr = new Xocfg_type_mgr();
	public Xocfg_dflt_mgr Dflt_mgr() {return dflt_mgr;} private final Xocfg_dflt_mgr dflt_mgr;
	public Xocfg_exec_mgr Exec_mgr() {return exec_mgr;} private final Xocfg_exec_mgr exec_mgr = new Xocfg_exec_mgr();
	public void Init_by_app(Xoa_app app) {
		cache_mgr.Init_by_app
		( gplx.xowa.addons.apps.cfgs.dbs.Xocfg_db_app.New_conn(app)
		, app.User().User_db_mgr().Conn());
	}
	public void Sub_many_app	(Gfo_invk sub, String... keys)					{Bind_many(BoolUtl.N, sub, Xocfg_mgr.Ctx__app, keys);}
	public void Bind_many_app	(Gfo_invk sub, String... keys)					{Bind_many(BoolUtl.Y, sub, Xocfg_mgr.Ctx__app, keys);}
	public void Bind_many_wiki	(Gfo_invk sub, Xow_wiki wiki, String... keys)		{Bind_many(BoolUtl.Y, sub, wiki.Domain_itm().Abrv_xo_str(), keys);}
	private void Bind_many(boolean pub, Gfo_invk sub, String ctx, String... keys) {
		if (gplx.core.envs.Env_.Mode_testing()) return;
		for (String key : keys) {
			try {
				cache_mgr.Sub(sub, ctx, key, key);
				if (pub) {
					String val = cache_mgr.Get(ctx, key);
					cache_mgr.Pub(ctx, key, val);
				}
			}
			catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "bind failed: ctx=~{0} key=~{1} err=~{2}", ctx, key, ErrUtl.ToStrLog(e));
			}
		}
	}
	public boolean Get_bool_app_or(String key, boolean or) {
		String rv = Get_str(Ctx__app, key);
		return rv == null ? or : Yn.parse_or(rv, or);
	}
	public boolean Get_bool_wiki_or(Xow_wiki wiki, String key, boolean or) {
		String rv = Get_str(wiki.Domain_itm().Abrv_xo_str(), key);
		return rv == null ? or : Yn.parse_or(rv, or);
	}
	public int Get_int_app_or(String key, int or) {
		String rv = cache_mgr.Get_or(Ctx__app, key, null);
		return rv == null ? or : IntUtl.ParseOr(rv, or);
	}
	public long Get_long_app_or(String key, long or) {
		String rv = cache_mgr.Get_or(Ctx__app, key, null);
		return rv == null ? or : LongUtl.ParseOr(rv, or);
	}
	public GfoDate Get_date_app_or(String key, GfoDate or) {
		String rv = cache_mgr.Get_or(Ctx__app, key, null);
		return rv == null ? or : GfoDateUtl.ParseFmtOr(rv, Fmt__time, or);
	}
	public String[] Get_strary_app_or(String key, String dlm, String... or) {
		String rv = cache_mgr.Get_or(Ctx__app, key, null);
		return rv == null ? or : StringUtl.Split(StringUtl.Trim(rv), dlm);
	}
	public String Get_str_wiki_or(Xow_wiki wiki, String key, String or) {return cache_mgr.Get_or(wiki.Domain_itm().Abrv_xo_str(), key, or);}
	public String Get_str_app_or(String key, String or) {return cache_mgr.Get_or(Ctx__app, key, or);}
	public String Get_str_app(String key) {return Get_str(Xocfg_mgr.Ctx__app, key);}
	public boolean Get_bool_or(String ctx, String key, boolean or) {
		String rv = cache_mgr.Get(ctx, key);
		try		{return Yn.parse(rv);}
		catch	(Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse boolean; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public int Get_int_wiki_or(Xow_wiki wiki, String key, int or) {
		String rv = Get_str(wiki.Domain_itm().Abrv_xo_str(), key);
		try		{return IntUtl.Parse(rv);}
		catch	(Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse int; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public int Get_int_or(String ctx, String key, int or) {
		String rv = cache_mgr.Get(ctx, key);
		try		{return IntUtl.Parse(rv);}
		catch	(Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse int; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public Io_url Get_url_or(String ctx, String key, Io_url or) {
		String rv = cache_mgr.Get(ctx, key);
		try		{return Io_url_.new_any_(rv);}
		catch	(Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:failed to parse int; key=~{0} val=~{1}", key, rv);
			return or;
		}
	}
	public String To_ctx(Xow_wiki wiki) {return wiki.Domain_itm().Abrv_xo_str();}
	public String Get_str(String ctx, String key) {return cache_mgr.Get(ctx, key);}
	public void Set_bool_app(String key, boolean val)		{Set_str(Xocfg_mgr.Ctx__app, key, Yn.To_str(val));}
	public void Set_float_app(String key, float val)	{Set_str(Xocfg_mgr.Ctx__app, key, FloatUtl.ToStr(val));}
	public void Set_str_app(String key, String val)		{Set_str(Xocfg_mgr.Ctx__app, key, val);}
	public void Set_bry_app(String key, byte[] val)		{Set_str(Xocfg_mgr.Ctx__app, key, StringUtl.NewU8(val));}
	public void Set_date_app(String key, GfoDate val)	{Set_str(Xocfg_mgr.Ctx__app, key, val.ToUtc().ToStrFmt(Fmt__time));}
	public void Set_int_app(String key, int val)		{Set_str(Xocfg_mgr.Ctx__app, key, IntUtl.ToStr(val));}
	public void Set_bry_wiki(Xowe_wiki wiki, String key, byte[] val)	{Set_str(wiki.Domain_itm().Abrv_xo_str(), key, StringUtl.NewU8(val));}
	
	public void Set_str(String ctx, String key, String val) {
		cache_mgr.Set(ctx, key, val);
	}
	public void Del(String ctx, String key) {
		cache_mgr.Del(ctx, key);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, "set"))					cache_mgr.Set			((String)m.ReadValAt(0), (String)m.ReadValAt(1), (String)m.ReadValAt(2));
		else if	(ctx.Match(k, "set_temp"))				cache_mgr.Set_wo_save	((String)m.ReadValAt(0), (String)m.ReadValAt(1), (String)m.ReadValAt(2));
		else if	(ctx.Match(k, "set_dflt"))				dflt_mgr.Add			((String)m.ReadValAt(0), (String)m.ReadValAt(1));
		else if	(ctx.Match(k, "run"))					cache_mgr.Pub			((String)m.ReadValAt(0), (String)m.ReadValAt(1), (String)m.ReadValAt(2));
		else if	(ctx.Match(k, "get"))					return cache_mgr.Get	((String)m.ReadValAt(0), (String)m.ReadValAt(1));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static String Ctx__app = "app";
	public static String[] Parse_io_cmd(String raw) {
		String[] rv = new String[2];
		rv[0] = "";
		rv[1] = "";
		int pos = StringUtl.FindFwd(raw, "|");
		if (pos != BryFind.NotFound) {
			rv[0] = StringUtl.Mid(raw, 0, pos);
			rv[1] = StringUtl.Mid(raw, pos + 1);
		}
		return rv;
	}
	public static final String Fmt__time = "yyyy-MM-dd HH:mm:ss";
}
