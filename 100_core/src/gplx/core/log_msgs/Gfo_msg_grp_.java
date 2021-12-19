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
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Gfo_msg_grp_ {
	public static final Gfo_msg_grp Root_gplx = new Gfo_msg_grp(null, Gfo_msg_grp_.Uid_next(), BryUtl.NewA7("gplx"));
	public static final Gfo_msg_grp Root = new Gfo_msg_grp(null, Gfo_msg_grp_.Uid_next(), BryUtl.Empty);
	public static Gfo_msg_grp prj_(String key)                        {return new Gfo_msg_grp(Root    , Gfo_msg_grp_.Uid_next(), BryUtl.NewA7(key));}
	public static Gfo_msg_grp new_(Gfo_msg_grp owner, String key)    {return new Gfo_msg_grp(owner    , Gfo_msg_grp_.Uid_next(), BryUtl.NewA7(key));}
	public static int Uid_next() {return uid_next++;} static int uid_next = 0;
	public static byte[] Path(byte[] owner_path, byte[] key) {
		if (owner_path != BryUtl.Empty) tmp_bfr.Add(owner_path).AddByte(AsciiByte.Dot);    // only add "." if owner_path is available; prevents creating ".gplx"
		return tmp_bfr.Add(key).ToBryAndClear();
	}
	static BryWtr tmp_bfr = BryWtr.NewAndReset(256);
}
