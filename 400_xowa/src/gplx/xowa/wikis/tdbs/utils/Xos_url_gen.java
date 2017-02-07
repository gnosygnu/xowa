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
package gplx.xowa.wikis.tdbs.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.core.ios.*;
public class Xos_url_gen implements Io_url_gen {
	public Xos_url_gen(Io_url root) {this.root = root;} Io_url root; int idx = 0;
	public byte[] Ext() {return ext;} public Xos_url_gen Ext_(byte[] v) {ext = v; return this;} private byte[] ext = Bry_.new_a7(".csv");
	public Io_url Cur_url() {return cur_url;} Io_url cur_url;
	public Io_url Nxt_url() {cur_url = bld_fil_(root, idx++, ext); return cur_url;}
	public Io_url[] Prv_urls() {
		int len = idx + 1;
		Io_url[] rv = new Io_url[len];
		for (int i = 0; i < len; i++)
			rv[i] = bld_fil_(root, i, ext);
		return rv;
	}
	public void Del_all() {}
	public static Io_url bld_fil_(Io_url root, int idx, byte[] ext) {
		byte dir_spr = root.Info().DirSpr_byte();
		tmp_bfr.Add(root.RawBry());
		int cur_mod = 100000000, cur_idx = idx;
		while (cur_mod > 99) {
			int val = cur_idx / cur_mod;
			tmp_bfr.Add_int_fixed(val, 2).Add_byte(dir_spr);
			cur_idx -= (val * cur_mod);
			cur_mod /= 100;
		}
		tmp_bfr.Add_int_fixed(idx, 10);
		tmp_bfr.Add(ext);
		return Io_url_.new_fil_(tmp_bfr.To_str_and_clear());
	}
	private static Bry_bfr tmp_bfr = Bry_bfr_.Reset(256);
}
