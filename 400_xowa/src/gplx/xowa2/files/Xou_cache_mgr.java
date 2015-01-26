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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.dbs.*; import gplx.xowa.files.fsdb.caches.*;
public interface Xou_cache_mgr {
	Xou_cache_fil Fil__get_or_null(byte[] dir_name, byte[] fil_name, boolean fil_is_orig, int fil_w, double fil_time, int fil_page);
	void Fil__update(Xou_cache_fil fil);
	void Dir__clear();
	void Dir__add(Xou_cache_dir dir);
	byte[] Dir__get_by_id(int id);
}
class Xou_cache_mgr__basic implements Xou_cache_mgr {
	private final HashAdp dir_id_regy = HashAdp_.new_(); private final Int_obj_ref dir_id_regy_key = Int_obj_ref.neg1_();
	private final Hash_adp_bry dir_name_regy = Hash_adp_bry.cs_();
	private final OrderedHash fil_regy = OrderedHash_.new_bry_();
	private final Xou_cache_fil_tbl fil_tbl = new Xou_cache_fil_tbl();
	private final Xou_cache_dir_tbl dir_tbl = new Xou_cache_dir_tbl();
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Init() {
		dir_tbl.Select_all(this);
	}
	public void Dir__clear() {dir_id_regy.Clear();}
	public void Dir__add(Xou_cache_dir dir) {
		dir_id_regy.Add(Int_obj_ref.new_(dir.Dir_id()), dir);
		dir_name_regy.Add(dir.Dir_name(), dir);
	}
	public byte[] Dir__get_by_id(int id) {
		Xou_cache_dir rv = (Xou_cache_dir)dir_id_regy.Fetch(dir_id_regy_key.Val_(id));
		return rv == null ? null : rv.Dir_name();
	}
	public int Dir__get_by_name(byte[] name) {
		Xou_cache_dir rv = (Xou_cache_dir)dir_name_regy.Get_by_bry(name);
		return rv == null ? -1 : rv.Dir_id();
	}
	public void Fil__update(Xou_cache_fil fil) {
		if (fil.Cmd_mode() == Db_cmd_mode.Create) {
			byte[] key = fil.Key(tmp_bfr);
			fil_regy.Add(key, fil);	// add to cache
		}
		fil_tbl.Commit(fil);
		byte[] dir_name = fil.Dir_name();
		if (Dir__get_by_name(dir_name) == -1) {	// not in dir_cache
			Xou_cache_dir dir = new Xou_cache_dir(Fixme, dir_name);
			this.Dir__add(dir);
			dir_tbl.Commit(dir);
		}
	}
	public Xou_cache_fil Fil__get_or_null(byte[] dir_name, byte[] fil_name, boolean fil_is_orig, int fil_w, double fil_time, int fil_page) {
		byte[] key = Xou_cache_fil.Key_bld(tmp_bfr, dir_name, fil_name, fil_is_orig, fil_w, fil_time, fil_page);
		Xou_cache_fil rv = (Xou_cache_fil)fil_regy.Fetch(key);
		if (rv == null) {				// not in mem
			int dir_id = Dir__get_by_name(dir_name);
			rv = fil_tbl.Select_by_key(this, dir_id, fil_name, fil_is_orig, fil_w, fil_time, fil_page);
			if (rv != null)				// found in db
				fil_regy.Add(key, rv);	// add to cache
		}
		return rv;
	}
	public static final int Fixme = -1;
}
