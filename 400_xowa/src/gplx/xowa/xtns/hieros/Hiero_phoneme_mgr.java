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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.dsvs.*;
class Hiero_phoneme_mgr implements GfoInvkAble {
	private Ordered_hash hash = Ordered_hash_.new_bry_();
	private Hiero_phoneme_srl srl;
	public Hiero_phoneme_mgr() {srl = new Hiero_phoneme_srl(this);}
	public int Len() {return hash.Count();}
	public Hiero_phoneme_itm Get_at(int i) {return (Hiero_phoneme_itm)hash.Get_at(i);}
	public void Add(byte[] key, byte[] val) {hash.Add(key, new Hiero_phoneme_itm(key, val));}
	public void Clear() {hash.Clear();}
	public Hiero_phoneme_itm Get_by_key(byte[] key) {return (Hiero_phoneme_itm)hash.Get_by(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_srl))			return srl;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	public static final String Invk_srl = "srl";
}
class Hiero_phoneme_itm {
	public Hiero_phoneme_itm(byte[] key, byte[] gardiner_code) {this.key = key; this.gardiner_code = gardiner_code;}
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Gardiner_code() {return gardiner_code;} private byte[] gardiner_code;
}
class Hiero_phoneme_srl extends Dsv_wkr_base {
	private Hiero_phoneme_mgr mgr;
	private byte[] key;
	private byte[] val;
	public Hiero_phoneme_srl(Hiero_phoneme_mgr mgr) {this.mgr = mgr;}
	@Override public Dsv_fld_parser[] Fld_parsers() {return new Dsv_fld_parser[] {Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser};}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: key = Bry_.Mid(src, bgn, end); return true;
			case 1: val = Bry_.Mid(src, bgn, end); return true;
			default: return false;
		}
	}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		if (key == null) throw parser.Err_row_bgn("phoneme missing key", pos);
		if (val == null) throw parser.Err_row_bgn("phoneme missing val", pos);
		mgr.Add(key, val);
		key = val = null;
	}
}
