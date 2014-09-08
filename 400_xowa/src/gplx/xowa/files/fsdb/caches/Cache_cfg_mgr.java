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
package gplx.xowa.files.fsdb.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.dbs.*; import gplx.xowa.dbs.tbls.*;
class Cache_cfg_mgr {
	private Xodb_xowa_cfg_tbl cfg_tbl = new Xodb_xowa_cfg_tbl();
	private Db_stmt update_stmt;
	public Cache_cfg_mgr(Cache_mgr cache_mgr) {}
	public int Next_id() {return next_id++;} private int next_id;
	public void Next_id_(int v) {next_id = v;}
	public long Cache_len() {return cache_len;} public Cache_cfg_mgr Cache_len_(long v) {cache_len = v; return this;} private long cache_len = 0;
	public void Cache_len_add(long v) {cache_len += v;}
	public long Cache_min() {return cache_min;} public Cache_cfg_mgr Cache_min_(long v) {cache_min = v; return this;} private long cache_min = Io_mgr.Len_mb * 75;
	public long Cache_max() {return cache_max;} public Cache_cfg_mgr Cache_max_(long v) {cache_max = v; return this;} private long cache_max = Io_mgr.Len_mb * 100;
	public void Db_init(Db_provider provider) {
		cfg_tbl.Provider_(provider);
		next_id = cfg_tbl.Select_val_as_int(Cfg_grp, Cfg_key__next_id);
		cache_len = cfg_tbl.Select_val_as_int(Cfg_grp, Cfg_key__cache_len);
		cache_max = cfg_tbl.Select_val_as_int(Cfg_grp, Cfg_key__cache_max);
	}
	public void Db_when_new(Db_provider provider) {
		cfg_tbl.Provider_(provider);
		cfg_tbl.Insert_str(Cfg_grp, Cfg_key__next_id, Int_.Xto_str(1));
		cfg_tbl.Insert_str(Cfg_grp, Cfg_key__cache_len, Long_.Xto_str(0));
		cfg_tbl.Insert_str(Cfg_grp, Cfg_key__cache_min, Long_.Xto_str(cache_min));
		cfg_tbl.Insert_str(Cfg_grp, Cfg_key__cache_max, Long_.Xto_str(cache_max));
	}
	public void Db_save() {
		if (update_stmt == null) update_stmt = cfg_tbl.Update_stmt();
		cfg_tbl.Update(update_stmt, Cfg_grp, Cfg_key__next_id, next_id);
		cfg_tbl.Update(update_stmt, Cfg_grp, Cfg_key__cache_len, cache_len);
		cfg_tbl.Update(update_stmt, Cfg_grp, Cfg_key__cache_min, cache_min);
		cfg_tbl.Update(update_stmt, Cfg_grp, Cfg_key__cache_max, cache_max);
	}
	public void Db_term() {
		if (update_stmt != null) update_stmt.Rls();
	}
	private static final String Cfg_grp = "fsdb.cache", Cfg_key__next_id = "next_id", Cfg_key__cache_min = "cache_min", Cfg_key__cache_max = "cache_max", Cfg_key__cache_len = "cache_len";
}
