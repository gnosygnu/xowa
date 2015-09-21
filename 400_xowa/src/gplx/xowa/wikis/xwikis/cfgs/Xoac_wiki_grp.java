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
package gplx.xowa.wikis.xwikis.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.langs.cfgs.*;
interface Xoac_wiki_obj {}
public class Xoac_wiki_grp implements Cfg_nde_obj, Xoac_wiki_obj {
	public Xoac_wiki_grp(byte[] key) {this.key_bry = key; this.name_bry = key_bry;}
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte[] Name_bry() {return name_bry;} private byte[] name_bry;
	public boolean Nde_typ_is_grp() {return true;}
	public byte[] Nde_key() {return key_bry;}
	public Cfg_nde_obj Nde_subs_make(byte[] itm_type, byte[] itm_key, byte[][] itm_atrs) {
		Cfg_nde_obj rv = null;
		if		(Bry_.Eq(itm_type, Make_grp))		rv = new Xoac_wiki_grp(itm_key);
		else if (Bry_.Eq(itm_type, Make_itm))		rv = new Xoac_wiki_itm(itm_key);
		else											throw Err_.new_unhandled(itm_type);
		rv.Nde_atrs_set(itm_atrs);
		return rv;
	}
	public int Nde_subs_len() {return itms.Count();}
	public Cfg_nde_obj Nde_subs_get_at(int i) {return (Cfg_nde_obj)itms.Get_at(i);}
	public Cfg_nde_obj Nde_subs_get(byte[] key) {return (Cfg_nde_obj)itms.Get_by(key);}
	public void Nde_subs_add(byte[] itm_key, Cfg_nde_obj itm_obj) {itms.Add(itm_key, itm_obj);}
	public void Nde_subs_del(byte[] key) {itms.Del(key);}
	public void Nde_atrs_set(byte[][] ary) {
		int ary_len = ary.length;
		if (ary_len > 0) name_bry = ary[0];
	}
	public static final byte[] Make_grp = Bry_.new_a7("grp"), Make_itm = Bry_.new_a7("itm");
	public int Itms_len() {return itms.Count();}
	public Cfg_nde_obj Itms_get_at(int i) {return (Cfg_nde_obj)itms.Get_at(i);}
	Ordered_hash itms = Ordered_hash_.new_bry_();
}
