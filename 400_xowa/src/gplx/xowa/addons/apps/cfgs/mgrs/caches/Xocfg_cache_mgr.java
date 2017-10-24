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
package gplx.xowa.addons.apps.cfgs.mgrs.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
import gplx.dbs.*;
import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.dbs.tbls.*; import gplx.xowa.addons.apps.cfgs.enums.*;
public class Xocfg_cache_mgr {
	private final    Hash_adp grps = Hash_adp_.New();
	public Xocfg_cache_mgr() {
		this.db_app = new Xocfg_db_app(Db_conn_.Noop);
		this.db_usr = new Xocfg_db_usr(db_app, Db_conn_.Noop);
	}
	public void Init_by_app(Db_conn app_conn, Db_conn usr_conn) {
		this.db_app = new Xocfg_db_app(app_conn);
		this.db_usr = new Xocfg_db_usr(db_app, usr_conn);
	}
	public Xocfg_db_app Db_app() {return db_app;} private Xocfg_db_app db_app;
	public Xocfg_db_usr Db_usr() {return db_usr;} private Xocfg_db_usr db_usr;
	// public void Clear() {grps.Clear();} // TOMBSTONE: do not call .Clear b/c subscribers are kept in grps and are only loaded once at app startup
	public String Get(String ctx, String key) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		return grp.Get(ctx);
	}
	public String Get_or(String ctx, String key, String or) {
		Xocfg_cache_grp grp = (Xocfg_cache_grp)grps.Get_by(key);
		if (grp == null) {
			grp = Load_grp(key, or);
			grps.Add(key, grp);
		}
		return grp.Get(ctx);
	}
	public void Set(String ctx, String key, String val)				{Set(Bool_.Y, ctx, key, val);}
	public void Set_wo_save(String ctx, String key, String val)		{Set(Bool_.N, ctx, key, val);}
	public void Set(boolean save, String ctx, String key, String val) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		grp.Pub(ctx, val);	// publish first; if fail will throw error
		grp.Set(ctx, val);
		if (save) {
			if (String_.Eq(grp.Dflt(), val))
				db_usr.Del(ctx, key);
			else
				db_usr.Set_str(ctx, key, val);
		}
	}
	public void Del(String ctx, String key) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		grp.Del(ctx);
		db_usr.Del(ctx, key);
		grp.Pub(ctx, grp.Dflt());
	}
	public void Sub(Gfo_invk sub, String ctx, String key, String evt) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		grp.Sub(sub, ctx, evt);
	}
	public void Pub(String ctx, String key, String val) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		grp.Pub(ctx, val);
	}
	public void Dflt(String key, String val) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		grp.Dflt_(val);
		grp.Pub(Xocfg_mgr.Ctx__app, val);	// need to pub after dflt is changed; for now, just pub at app-level
	}
	public Xocfg_cache_grp Grps__get_or_load(String key) {
		Xocfg_cache_grp grp = (Xocfg_cache_grp)grps.Get_by(key);
		if (grp == null) {
			grp = Load_grp(key, "");
			grps.Add(key, grp);
		}
		return grp;
	}
	private Xocfg_cache_grp Load_grp(String key, String or) {
		// get data from db
		Xocfg_itm_row meta_itm = db_app.Tbl__itm().Select_by_key_or_null(key);
		if (meta_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:itm not found; key=~{0}", key);
			return new Xocfg_cache_grp(key, or, String_.Cls_val_name);
		}
		Xocfg_val_row[] itms = db_usr.Tbl__val().Select_all(meta_itm.Key());

		// make
		Xocfg_cache_grp rv = new Xocfg_cache_grp(key, meta_itm.Dflt(), meta_itm.Type());
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Xocfg_val_row itm = itms[0];
			String itm_ctx = itm.Ctx();
			rv.Add(itm_ctx, new Xocfg_cache_itm(itm_ctx, key, itm.Val()));
		}
		return rv;
	}
}
