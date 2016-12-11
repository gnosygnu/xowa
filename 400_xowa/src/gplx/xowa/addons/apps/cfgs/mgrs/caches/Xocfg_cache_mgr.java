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
package gplx.xowa.addons.apps.cfgs.mgrs.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
import gplx.dbs.*;
import gplx.xowa.addons.apps.cfgs.dbs.*; import gplx.xowa.addons.apps.cfgs.dbs.tbls.*;
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
	public void Clear() {grps.Clear();}
	public String Get(String ctx, String key) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		return grp.Get(ctx);
	}
	public void Set(String ctx, String key, String val) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		grp.Set(ctx, val);
		db_usr.Set_str(ctx, key, val);
		grp.Pub(ctx, val);
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
	public void Dflt(String key, String val) {
		Xocfg_cache_grp grp = Grps__get_or_load(key);
		grp.Dflt_(val);
	}
	private Xocfg_cache_grp Grps__get_or_load(String key) {
		Xocfg_cache_grp grp = (Xocfg_cache_grp)grps.Get_by(key);
		if (grp == null) {
			grp = Load_grp(key);
			grps.Add(key, grp);
		}
		return grp;
	}
	private Xocfg_cache_grp Load_grp(String key) {
		// get data from db
		Xocfg_itm_row meta_itm = db_app.Tbl__itm().Select_by_key_or_null(key);
		if (meta_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "cfg:grp not found; key=~{0}", key);
			return new Xocfg_cache_grp(key, "");
		}
		Xocfg_val_row[] itms = db_usr.Tbl__val().Select_all(meta_itm.Key());

		// make
		Xocfg_cache_grp rv = new Xocfg_cache_grp(key, meta_itm.Dflt());
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Xocfg_val_row itm = itms[0];
			String itm_ctx = itm.Ctx();
			rv.Add(itm_ctx, new Xocfg_cache_itm(itm_ctx, key, itm.Val()));
		}
		return rv;
	}
}
