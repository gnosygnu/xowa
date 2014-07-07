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
package gplx.core.regxs; import gplx.*; import gplx.core.*;
public class Gfo_pattern {
	private Gfo_pattern_itm[] itms; int itms_len;
	public Gfo_pattern(byte[] raw) {
		this.raw = raw;
		itms = Gfo_pattern_itm_.Compile(raw);
		itms_len = itms.length;
	}
	public byte[] Raw() {return raw;} private byte[] raw;
	private Gfo_pattern_ctx ctx = new Gfo_pattern_ctx();
	public boolean Match(byte[] val) {
		int val_len = val.length;
		int val_pos = 0;
		ctx.Init(itms_len);
		for (int i = 0; i < itms_len; ++i) {
			Gfo_pattern_itm itm = itms[i];
			ctx.Itm_idx_(i);
			val_pos = itm.Match(ctx, val, val_len, val_pos);
			if (!ctx.Rslt_pass()) return false;
		}
		return ctx.Rslt_pass() && val_pos == val_len;
	}
	public static Gfo_pattern[] Parse_to_ary(byte[] raw) {
		byte[][] patterns = Bry_.Split(raw, Byte_ascii.Semic, true);
		int patterns_len = patterns.length;
		Gfo_pattern[] rv = new Gfo_pattern[patterns_len];
		for (int i = 0; i < patterns_len; ++i) {
			byte[] pattern = patterns[i];
			rv[i] = new Gfo_pattern(pattern);
		}
		return rv;
	}
}
