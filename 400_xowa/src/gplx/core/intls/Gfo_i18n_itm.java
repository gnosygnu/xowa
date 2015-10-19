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
package gplx.core.intls; import gplx.*; import gplx.core.*;
class Gfo_i18n_itm {
	public Gfo_i18n_itm(int src, byte[] key, byte[] val, boolean val_fmt_exists, Gfo_i18n_val_cmd val_cmd) {
		this.src = src; this.key = key; this.val = val; this.val_fmt_exists = val_fmt_exists; this.val_cmd = val_cmd;
	}
	public int Src() {return src;} private final int src;
	public byte[] Key() {return key;} private final byte[] key;
	public byte[] Val() {return val;} private final byte[] val;
	public boolean Val_fmt_exists() {return val_fmt_exists;} private final boolean val_fmt_exists;
	public Gfo_i18n_val_cmd Val_cmd() {return val_cmd;} private final Gfo_i18n_val_cmd val_cmd;
	public byte[] Bld_none() {
		return val_cmd == null ? val : val_cmd.Process(src, key, val);
	}
	public byte[] Bld_many(Object... args) {
		byte[] rv = null;
		synchronized (tmp_fmtr) {
			tmp_fmtr.Fmt_(val);
			tmp_fmtr.Bld_bfr_many(tmp_bfr, args);
			rv = tmp_bfr.To_bry_and_clear();
		}
		return val_cmd == null ? rv : val_cmd.Process(src, key, rv);
	}
	private static final Bry_fmtr tmp_fmtr = Bry_fmtr.new_();
	private static final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
}
