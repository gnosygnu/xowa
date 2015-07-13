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
package gplx;
public class Gfo_msg_itm implements Gfo_msg_obj {
	public Gfo_msg_itm(Gfo_msg_grp owner, int uid, byte cmd, byte[] key_bry, byte[] fmt, boolean add_to_owner) {
		this.owner = owner; this.uid = uid; this.cmd = cmd; this.key_bry = key_bry; this.fmt = fmt;
		this.key_str = String_.new_a7(key_bry);
		this.path_bry = Gfo_msg_grp_.Path(owner.Path(), key_bry);
		if (add_to_owner) owner.Subs_add(this);
	}
	public Gfo_msg_grp Owner() {return owner;} Gfo_msg_grp owner;
	public int Uid() {return uid;} int uid;
	public byte[] Path_bry() {return path_bry;} private byte[] path_bry;
	public String Path_str() {return String_.new_u8(path_bry);}
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public String Key_str() {return key_str;} private String key_str;
	public Gfo_msg_obj Subs_get_by_key(String sub_key) {return null;}
	public byte Cmd() {return cmd;} private byte cmd;
	public byte[] Fmt() {return fmt;} private byte[] fmt;
	public Bry_fmtr Fmtr() {if (fmtr == null) fmtr = Bry_fmtr.new_bry_(fmt).Compile(); return fmtr;} Bry_fmtr fmtr; 
	public String Gen_str_many(Object... vals) {return Gen_str_ary(vals);}
	public String Gen_str_ary(Object[] vals) {
		if (fmtr == null) fmtr = Bry_fmtr.new_bry_(fmt).Compile(); 
		if (fmtr.Fmt_args_exist()) {
			fmtr.Bld_bfr_many(tmp_bfr, vals);
			return tmp_bfr.Xto_str_and_clear();
		}
		else
			return String_.new_u8(fmt);
	}
	public String Gen_str_one(Object val) {
		if (fmtr == null) fmtr = Bry_fmtr.new_bry_(fmt).Compile(); 
		if (fmtr.Fmt_args_exist()) {
			fmtr.Bld_bfr_one(tmp_bfr, val);
			return tmp_bfr.Xto_str_and_clear();
		}
		else
			return String_.new_u8(fmt);
	}
	public String Gen_str_none() {return key_str;}
	static Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
}
