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
package gplx.xowa.cfgs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_cfg_grp {		
	public Xoa_cfg_grp(Xoa_cfg_mgr mgr, Xoa_cfg_grp_tid tid, byte[] key_bry) {
		this.mgr = mgr; this.tid = tid; this.key_bry = key_bry; this.key_str = String_.new_u8(key_bry);
	} 	Xoa_cfg_mgr mgr; Ordered_hash hash = Ordered_hash_.new_bry_();
	public Xoa_cfg_grp_tid Tid() {return tid;} private Xoa_cfg_grp_tid tid;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public String Key_str() {return key_str;} private String key_str;
	public Xoa_cfg_itm Get_itm_or_null(byte[] itm_key) {
		return (Xoa_cfg_itm)hash.Get_by(itm_key);
	}
	public Xoa_cfg_itm Get_itm_or_make(byte[] itm_key) {
		Xoa_cfg_itm rv = (Xoa_cfg_itm)hash.Get_by(itm_key);
		if (rv == null) {
			rv = new Xoa_cfg_itm(this, itm_key);
			hash.Add(itm_key, rv);
		}
		return rv;
	}
	public Xoa_cfg_itm Get_itm_by_wiki(byte[] domain, int wiki_tid) {
		Xoa_cfg_itm rv = (Xoa_cfg_itm)hash.Get_by(domain);
		if (rv == null) { 		// match by domain failed; try type
			rv = (Xoa_cfg_itm)hash.Get_by(Xow_domain_type_.Get_type_as_bry(wiki_tid));
			if (rv == null)		// match by type failed; try all
				rv = (Xoa_cfg_itm)hash.Get_by(Xoa_cfg_grp_tid.Key_all_bry);	
				if (rv == null)		// match by type failed; try app
					rv = (Xoa_cfg_itm)hash.Get_by(Xoa_cfg_grp_tid.Key_app_bry);	
		}
		return rv;
	}
	public void Db_customized_n_() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xoa_cfg_itm itm = (Xoa_cfg_itm)hash.Get_at(i);
			itm.Db_customized_(false).Db_dirty_(false);
		}
	}
	public void Db_loaded_y_() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xoa_cfg_itm itm = (Xoa_cfg_itm)hash.Get_at(i);
			itm.Db_customized_(true).Db_dirty_(false);
		}
	}
	public void Db_save(Xoa_cfg_db db) {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xoa_cfg_itm itm = (Xoa_cfg_itm)hash.Get_at(i);
			if (!itm.Db_customized() && !itm.Db_dirty()) continue; // system default and unchanged; no need to save
			db.Cfg_save_run(mgr, this, itm);
		}	
	}
	public void Clear() {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xoa_cfg_itm itm = (Xoa_cfg_itm)hash.Get_at(i);
			itm.Clear();
		}
		hash.Clear();
	}
	public boolean Notify(Xoa_cfg_itm itm) {return mgr.Notify(this, itm);}
}
