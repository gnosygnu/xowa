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
package gplx.dbs.cfgs; import gplx.*; import gplx.dbs.*;
public class Db_cfg_grp {
	private OrderedHash itms = OrderedHash_.new_();
	public Db_cfg_grp(String grp) {this.grp = grp;}
	public String Grp() {return grp;} private String grp;
	public void Insert(String key, String val) {
		if (itms.Has(key)) throw Err_.new_fmt_("cfg_grp.Insert failed; key={0}", key);
		Fsm_cfg_itm itm = new Fsm_cfg_itm(grp, key, val);
		itms.Add(key, itm);
	}
	public void Update(String key, String val) {
		Fsm_cfg_itm itm = (Fsm_cfg_itm)itms.Fetch(key);
		if (itm == null) throw Err_.new_fmt_("cfg_grp.Update failed; key={0}", key);
		itm.Val_(val);
	}
	public void Upsert(String key, String val) {
		Fsm_cfg_itm itm = (Fsm_cfg_itm)itms.Fetch(key);
		if (itm == null) {
			itm = new Fsm_cfg_itm(grp, key, val);
			itms.Add(key, itm);
		}
		else
			itm.Val_(val);
	}
	public boolean Get_yn_or_y(String key) {return Get_yn_or(key, Bool_.Y);}
	public boolean Get_yn_or_n(String key) {return Get_yn_or(key, Bool_.N);}
	public boolean Get_yn_or(String key, boolean or) {
		String rv = Get_str_or(key, null);
		return rv == null ? or : Yn.parse_(rv);
	}
	public int Get_int_or(String key, int or) {
		String rv = Get_str_or(key, null);
		return rv == null ? or : Int_.parse_(rv);
	}
	public String Get_str_or(String key, String or) {
		Fsm_cfg_itm itm = (Fsm_cfg_itm)itms.Fetch(key);
		return itm == null ? or : itm.Val();
	}
	public static final Db_cfg_grp Null = new Db_cfg_grp(); Db_cfg_grp() {}
}
class Fsm_cfg_itm {
	public Fsm_cfg_itm(String grp, String key, String val) {this.grp = grp; this.key = key; this.val = val;}
	public String Grp() {return grp;} private String grp;
	public String Key() {return key;} private String key;
	public String Val() {return val;} public Fsm_cfg_itm Val_(String v) {val = v; return this;} private String val;
}
