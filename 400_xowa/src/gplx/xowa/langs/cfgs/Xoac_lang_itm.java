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
package gplx.xowa.langs.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xoac_lang_itm implements Cfg_nde_obj, Xoac_lang_obj {
	public Xoac_lang_itm(byte[] key) {this.key_bry = key; local_name_bry = key_bry; uid = uid_next++;} static int uid_next = 0;
	public Xoac_lang_grp Grp() {return grp;} public Xoac_lang_itm Grp_(Xoac_lang_grp v) {grp = v; return this;} private Xoac_lang_grp grp;
	public int Uid() {return uid;} private int uid;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte[] Local_name_bry() {return local_name_bry;} public Xoac_lang_itm Local_name_bry_(byte[] v) {local_name_bry = v; return this;} private byte[] local_name_bry;
	public byte[] Nde_key() {return key_bry;}
	public boolean Nde_typ_is_grp() {return false;}
	public Cfg_nde_obj Nde_subs_make(byte[] itm_type, byte[] itm_key, byte[][] itm_atrs) {throw Err_.new_wo_type("langs cannot have itms", "type", itm_type, "key", itm_key);}
	public Cfg_nde_obj Nde_subs_get(byte[] key) {throw Err_.new_wo_type("langs cannot have itms", "key", key);}
	public int Nde_subs_len() {return 0;}
	public Cfg_nde_obj Nde_subs_get_at(int i) {throw Err_.new_wo_type("langs cannot have itms", "idx", i);}
	public void Nde_subs_add(byte[] itm_key, Cfg_nde_obj itm_obj) {throw Err_.new_wo_type("langs cannot have itms", "key", String_.new_u8(itm_key));}
	public void Nde_subs_del(byte[] key) {throw Err_.new_wo_type("langs cannot delete itms", "key", String_.new_u8(key));}
	public void Nde_atrs_set(byte[][] ary) {
		if (ary.length != 1) throw Err_.new_wo_type("expecting name only", "name", String_.AryXtoStr(String_.Ary(ary)));
		local_name_bry = ary[0];
	}
}
