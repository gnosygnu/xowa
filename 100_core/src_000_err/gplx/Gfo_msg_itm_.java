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
public class Gfo_msg_itm_ {
	public static final byte Cmd_null = 0, Cmd_log = 1, Cmd_note = 2, Cmd_warn = 3, Cmd_stop = 4, Cmd_fail = 5;
	public static final byte[][] CmdBry = new byte[][] {Bry_.new_ascii_("null"), Bry_.new_ascii_("log"), Bry_.new_ascii_("note"), Bry_.new_ascii_("warn"), Bry_.new_ascii_("stop"), Bry_.new_ascii_("fail")};
	public static Gfo_msg_itm new_note_(Gfo_msg_grp owner, String key)								{return new_(owner, Cmd_note, key, key);}
	public static Gfo_msg_itm new_fail_(Gfo_msg_grp owner, String key, String fmt)					{return new_(owner, Cmd_warn, key, fmt);}
	public static Gfo_msg_itm new_warn_(Gfo_msg_grp owner, String key)								{return new_(owner, Cmd_warn, key, key);}
	public static Gfo_msg_itm new_warn_(Gfo_msg_grp owner, String key, String fmt)					{return new_(owner, Cmd_warn, key, fmt);}
	public static Gfo_msg_itm new_(Gfo_msg_grp owner, byte cmd, String key, String fmt) {return new Gfo_msg_itm(owner, Gfo_msg_grp_.Uid_next(), cmd, Bry_.new_ascii_(key), Bry_.new_ascii_(fmt), false);}
}
