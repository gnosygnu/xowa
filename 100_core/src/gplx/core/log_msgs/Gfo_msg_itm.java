/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.log_msgs; import gplx.*; import gplx.core.*;
import gplx.core.brys.fmtrs.*;
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
			return tmp_bfr.To_str_and_clear();
		}
		else
			return String_.new_u8(fmt);
	}
	public String Gen_str_one(Object val) {
		if (fmtr == null) fmtr = Bry_fmtr.new_bry_(fmt).Compile(); 
		if (fmtr.Fmt_args_exist()) {
			fmtr.Bld_bfr_one(tmp_bfr, val);
			return tmp_bfr.To_str_and_clear();
		}
		else
			return String_.new_u8(fmt);
	}
	public String Gen_str_none() {return key_str;}
	static Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
}
