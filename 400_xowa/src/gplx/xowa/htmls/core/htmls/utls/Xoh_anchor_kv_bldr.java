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
package gplx.xowa.htmls.core.htmls.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.langs.htmls.*;
public class Xoh_anchor_kv_bldr {
	private byte[] base_url; private boolean has_qarg;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(16), apos_bfr = Bry_bfr_.New_w_size(16);
	public Xoh_anchor_kv_bldr Init_w_qarg(byte[] base_url) {return Init(Bool_.Y, base_url);}
	public Xoh_anchor_kv_bldr Init(boolean has_qarg, byte[] base_url) {
		this.has_qarg = has_qarg; this.base_url = base_url;
		tmp_bfr.Clear();
		tmp_bfr.Add(base_url);
		return this;
	}
	public Xoh_anchor_kv_bldr Add_int(byte[] key, int val) {
		tmp_bfr.Add_byte(has_qarg ? Byte_ascii.Amp : Byte_ascii.Question);
		tmp_bfr.Add(key);
		tmp_bfr.Add_byte(Byte_ascii.Eq);
		tmp_bfr.Add_int_variable(val);
		return this;
	}
	public Xoh_anchor_kv_bldr Add_bry(byte[] key, byte[] bry) {
		tmp_bfr.Add_byte(has_qarg ? Byte_ascii.Amp : Byte_ascii.Question);
		tmp_bfr.Add(key);
		tmp_bfr.Add_byte(Byte_ascii.Eq);
		tmp_bfr.Add(Gfh_utl.Escape_for_atr_val_as_bry(apos_bfr, Byte_ascii.Apos, bry));
		return this;
	}
	public byte[] Bld_to_bry() {
		byte[] rv = tmp_bfr.To_bry_and_clear();
		tmp_bfr.Add(base_url);
		return rv;
	}
}
