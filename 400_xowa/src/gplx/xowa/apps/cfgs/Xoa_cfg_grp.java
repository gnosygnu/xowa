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
package gplx.xowa.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_cfg_grp {
	private final Ordered_hash hash = Ordered_hash_.New_bry(); private final Xoa_cfg_mgr mgr;
	public Xoa_cfg_grp(Xoa_cfg_mgr mgr, Xoa_cfg_grp_tid tid, byte[] key_bry) {
		this.mgr = mgr; this.tid = tid; this.key_bry = key_bry; this.key_str = String_.new_u8(key_bry);
	}
	public Xoa_cfg_grp_tid Tid() {return tid;} private final Xoa_cfg_grp_tid tid;
	public byte[] Key_bry() {return key_bry;} private final byte[] key_bry;
	public String Key_str() {return key_str;} private final String key_str;
	public boolean Notify(Xoa_cfg_itm itm) {return mgr.Notify(this, itm);}
	public Xoa_cfg_itm Get_by_or_null(byte[] key) {return (Xoa_cfg_itm)hash.Get_by(key);}
	public Xoa_cfg_itm Get_by_or_make(byte[] key) {
		Xoa_cfg_itm rv = (Xoa_cfg_itm)hash.Get_by(key);
		if (rv == null) {
			rv = new Xoa_cfg_itm(this, key);
			hash.Add(key, rv);
		}
		return rv;
	}
	public Xoa_cfg_itm Get_by_wiki(byte[] domain_bry, int domain_tid) {
		Xoa_cfg_itm rv = (Xoa_cfg_itm)hash.Get_by(domain_bry);
		if (rv == null) { 			// match by domain_bry failed; try tid
			rv = (Xoa_cfg_itm)hash.Get_by(Xow_domain_tid_.Get_type_as_bry(domain_tid));
			if (rv == null)			// match by type failed; try all
				rv = (Xoa_cfg_itm)hash.Get_by(Xoa_cfg_grp_tid.Key_all_bry);	
				if (rv == null)		// match by all failed; try app
					rv = (Xoa_cfg_itm)hash.Get_by(Xoa_cfg_grp_tid.Key_app_bry);	
		}
		return rv;
	}
	public void Db_load_end() {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoa_cfg_itm itm = (Xoa_cfg_itm)hash.Get_at(i);
			itm.Val_load_done();
		}
	}
	public void Db_save(Xoa_cfg_db db) {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoa_cfg_itm itm = (Xoa_cfg_itm)hash.Get_at(i);
			if (!itm.Val_is_customized() && !itm.Val_is_dirty()) continue; // system default and unchanged; no need to save
			db.Cfg_save_run(mgr, this, itm);
		}	
	}
}
