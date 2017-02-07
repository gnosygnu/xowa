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
package gplx.xowa.addons.wikis.ctgs.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.core.btries.*;
public class Xoctg_type_enum {
	private final    Btrie_rv trv = new Btrie_rv();
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs()
		.Add_str_byte("subcat"		, Tid__subc)
		.Add_str_byte("file"		, Tid__file)
		.Add_str_byte("page"		, Tid__page);
	public byte To_tid_or_fail(byte[] raw) {
		byte tid = trie.Match_byte_or(trv, raw, 0, raw.length, Byte_.Max_value_127);
		if (tid == Byte_.Max_value_127) throw Err_.new_unhandled_default(raw);
		return tid;
	}
	public static final byte Tid__subc = 0, Tid__file = 1, Tid__page = 2, Tid_max = 3;
}
