/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.log_msgs;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
public class Gfo_msg_itm implements Gfo_msg_obj {
	public Gfo_msg_itm(Gfo_msg_grp owner, int uid, byte cmd, byte[] key_bry, byte[] fmt, boolean add_to_owner) {
		this.owner = owner; this.uid = uid; this.cmd = cmd; this.key_bry = key_bry; this.fmt = fmt;
		this.key_str = StringUtl.NewA7(key_bry);
		this.path_bry = Gfo_msg_grp_.Path(owner.Path(), key_bry);
		if (add_to_owner) owner.Subs_add(this);
	}
	public Gfo_msg_grp Owner() {return owner;} Gfo_msg_grp owner;
	public int Uid() {return uid;} int uid;
	public byte[] Path_bry() {return path_bry;} private byte[] path_bry;
	public String Path_str() {return StringUtl.NewU8(path_bry);}
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public String Key_str() {return key_str;} private String key_str;
	public Gfo_msg_obj Subs_get_by_key(String sub_key) {return null;}
	public byte Cmd() {return cmd;} private byte cmd;
	public byte[] Fmt() {return fmt;} private byte[] fmt;
	public BryFmtr Fmtr() {if (fmtr == null) fmtr = BryFmtr.NewBry(fmt).Compile(); return fmtr;} BryFmtr fmtr;
	public String Gen_str_many(Object... vals) {return Gen_str_ary(vals);}
	public String Gen_str_ary(Object[] vals) {
		if (fmtr == null) fmtr = BryFmtr.NewBry(fmt).Compile();
		if (fmtr.FmtArgsExist()) {
			fmtr.BldToBfrMany(tmp_bfr, vals);
			return tmp_bfr.ToStrAndClear();
		}
		else
			return StringUtl.NewU8(fmt);
	}
	public String Gen_str_one(Object val) {
		if (fmtr == null) fmtr = BryFmtr.NewBry(fmt).Compile();
		if (fmtr.FmtArgsExist()) {
			fmtr.BldToBfrObj(tmp_bfr, val);
			return tmp_bfr.ToStrAndClear();
		}
		else
			return StringUtl.NewU8(fmt);
	}
	public String Gen_str_none() {return key_str;}
	static BryWtr tmp_bfr = BryWtr.NewAndReset(255);
}
