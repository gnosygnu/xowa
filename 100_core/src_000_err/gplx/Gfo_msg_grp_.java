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
public class Gfo_msg_grp_ {
	public static final Gfo_msg_grp Root_gplx = new Gfo_msg_grp(null, Gfo_msg_grp_.Uid_next(), Bry_.new_ascii_("gplx"));
	public static final Gfo_msg_grp Root = new Gfo_msg_grp(null, Gfo_msg_grp_.Uid_next(), Bry_.Empty);
	public static Gfo_msg_grp prj_(String key)						{return new Gfo_msg_grp(Root	, Gfo_msg_grp_.Uid_next(), Bry_.new_ascii_(key));}
	public static Gfo_msg_grp new_(Gfo_msg_grp owner, String key)	{return new Gfo_msg_grp(owner	, Gfo_msg_grp_.Uid_next(), Bry_.new_ascii_(key));}
	public static int Uid_next() {return uid_next++;} static int uid_next = 0;
	public static byte[] Path(byte[] owner_path, byte[] key) {
		if (owner_path != Bry_.Empty) tmp_bfr.Add(owner_path).Add_byte(Byte_ascii.Dot);	// only add "." if owner_path is available; prevents creating ".gplx"
		return tmp_bfr.Add(key).XtoAryAndClear();
	}
	static Bry_bfr tmp_bfr = Bry_bfr.reset_(256);
}
