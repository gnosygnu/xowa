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
package gplx.xowa; import gplx.*;
public class Xol_msg_itm {
	public Xol_msg_itm(int id, byte[] key) {this.id = id; this.key = key;}
	public int Id() {return id;} public void Id_(int v) {this.id = v;} private int id;
	public boolean Src_is_missing() {return src == Src_missing;}
	public byte Src() {return src;} public Xol_msg_itm Src_(byte v) {src = v; return this;} private byte src;
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Val() {return val;} private byte[] val;
	public boolean Has_fmt_arg() {return has_fmt_arg;} private boolean has_fmt_arg;
	public boolean Has_tmpl_txt() {return has_tmpl_txt;} private boolean has_tmpl_txt;
	public void Atrs_set(byte[] val, boolean has_fmt_arg, boolean has_tmpl_txt) {
		this.val = val; this.has_fmt_arg = has_fmt_arg; this.has_tmpl_txt = has_tmpl_txt;
	}
	public boolean Dirty() {return dirty;} public Xol_msg_itm Dirty_(boolean v) {dirty = v; return this;} private boolean dirty;
	public byte[] Fmt(Bry_bfr bfr, Object... args) {
		synchronized (tmp_fmtr) {
			tmp_fmtr.Fmt_(val);
			tmp_fmtr.Bld_bfr_many(bfr, args);
			return bfr.Xto_bry_and_clear();
		}
	}
	public byte[] Fmt_tmp(Bry_bfr bfr, byte[] tmp_val, Object... args) {
		synchronized (tmp_fmtr) {
			tmp_fmtr.Fmt_(tmp_val);
			tmp_fmtr.Bld_bfr_many(bfr, args);
			return bfr.Xto_bry_and_clear();
		}
	}
	public static final byte Src_null = 0, Src_lang = 1, Src_wiki = 2, Src_missing = 3;
	private static Bry_fmtr tmp_fmtr = Bry_fmtr.tmp_();
}
