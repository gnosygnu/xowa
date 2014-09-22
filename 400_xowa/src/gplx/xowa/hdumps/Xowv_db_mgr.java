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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.xowa.dbs.*;
public class Xowv_db_mgr implements GfoInvkAble {
	private Io_url wiki_root_dir;
	public Xowv_db_mgr(String domain_str, Io_url wiki_root_dir) {
		this.tbl_mgr = new Xowd_db_tbl_mgr(); this.wiki_root_dir = wiki_root_dir;
		this.key__core = Xowv_db_mgr.Db_key__core__default(this, domain_str, wiki_root_dir);			
	}
	public Xowd_db_tbl_mgr Tbl_mgr() {return tbl_mgr;} private Xowd_db_tbl_mgr tbl_mgr;
	private Xodb_file[] db_files; private Db_provider_key[] db_keys; private int db_keys_len;
	public Db_provider_key Key__core() {return key__core;} private Db_provider_key key__core;		
	public Db_provider_key Key_by_idx(int idx) {if (!Int_.Between(idx, 0, db_keys_len)) throw Err_.new_("database does not exist: idx={0}", idx); return db_keys[idx];}
	private void Core_init(Db_provider provider) {
		db_files	= tbl_mgr.Tbl__dbs_new().Select_all(provider, wiki_root_dir);
		db_keys		= Db_keys_make(db_files);
		db_keys_len = db_keys.length;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_core_init))	Core_init((Db_provider)m.ReadObj("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_core_init = "core_init";
	private static Db_provider_key Db_key__core__default(Xowv_db_mgr db_mgr, String domain_str, Io_url wiki_root_dir) {
		Io_url core_db_url = wiki_root_dir.GenSubFil_ary(domain_str, ".000.sqlite3");
		GfoInvkAbleCmd init_cmd = GfoInvkAbleCmd.new_(db_mgr, Invk_core_init);
		return new Db_provider_key(core_db_url.Raw(), Db_conn_info_.sqlite_(core_db_url), init_cmd);
	}
	private static Db_provider_key[] Db_keys_make(Xodb_file[] ary) {
		int len = ary.length;
		Db_provider_key[] rv = new Db_provider_key[len];
		for (int i = 0; i < len; ++i) {
			Xodb_file itm = ary[i];
			Io_url itm_url = itm.Url();
			rv[i] = new Db_provider_key(itm_url.Raw(), Db_conn_info_.sqlite_(itm_url), GfoInvkAbleCmd.Null);
		}
		return rv;
	}
}
