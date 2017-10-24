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
public class Gfo_msg_itm_ {
	public static final byte Cmd_null = 0, Cmd_log = 1, Cmd_note = 2, Cmd_warn = 3, Cmd_stop = 4, Cmd_fail = 5;
	public static final byte[][] CmdBry = new byte[][] {Object_.Bry__null, Bry_.new_a7("log"), Bry_.new_a7("note"), Bry_.new_a7("warn"), Bry_.new_a7("stop"), Bry_.new_a7("fail")};
	public static Gfo_msg_itm new_note_(Gfo_msg_grp owner, String key)								{return new_(owner, Cmd_note, key, key);}
	public static Gfo_msg_itm new_fail_(Gfo_msg_grp owner, String key, String fmt)					{return new_(owner, Cmd_warn, key, fmt);}
	public static Gfo_msg_itm new_warn_(Gfo_msg_grp owner, String key)								{return new_(owner, Cmd_warn, key, key);}
	public static Gfo_msg_itm new_warn_(Gfo_msg_grp owner, String key, String fmt)					{return new_(owner, Cmd_warn, key, fmt);}
	public static Gfo_msg_itm new_(Gfo_msg_grp owner, byte cmd, String key, String fmt) {return new Gfo_msg_itm(owner, Gfo_msg_grp_.Uid_next(), cmd, Bry_.new_a7(key), Bry_.new_a7(fmt), false);}
}
