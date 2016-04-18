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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*;
class Xo_custom_html_pos_ {
	public static final byte Tid__head_end = 1, Tid__tail = 2;
	private static final    Hash_adp_bry hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("head_end", Tid__head_end)
	.Add_str_byte("tail", Tid__tail)
	;
	public static byte To_tid(byte[] bry) {
		byte tid = hash.Get_as_byte_or(bry, Byte_.Max_value_127);
		if (tid == Byte_.Max_value_127) throw Err_.new_wo_type("unknown pos", "pos", bry);
		return tid;
	}
}
class Xo_custom_html_type_ {
	public static final byte Tid__css_code = 1, Tid__js_code = 2;
	private static final    Hash_adp_bry hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Gfh_tag_.Bry__style	, Tid__css_code)
	.Add_bry_byte(Gfh_tag_.Bry__script	, Tid__js_code)
	;
	public static byte[] To_bry(byte tid) {
		switch (tid) {
			case Tid__css_code: return Gfh_tag_.Bry__style;
			case Tid__js_code : return Gfh_tag_.Bry__script;
			default: throw Err_.new_unhandled_default(tid);
		}
	}
	public static byte To_tid(byte[] bry) {
		byte tid = hash.Get_as_byte_or(bry, Byte_.Max_value_127); if (tid == Byte_.Max_value_127) throw Err_.new_wo_type("unknown type", "type", bry);
		return tid;
	}
}
