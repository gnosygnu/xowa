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
class Hiero_prefab_mgr implements GfoInvkAble {
	private Ordered_hash hash = Ordered_hash_.new_bry_();
	private Hiero_prefab_srl srl;
	public Hiero_prefab_mgr() {srl = new Hiero_prefab_srl(this);}
	public int Len() {return hash.Count();}
	public Hiero_prefab_itm Get_at(int i) {return (Hiero_prefab_itm)hash.Get_at(i);}
	public Hiero_prefab_itm Get_by_key(byte[] key) {return (Hiero_prefab_itm)hash.Get_by(key);}
	public void Add(byte[] key) {hash.Add(key, new Hiero_prefab_itm(key));}
	public void Clear() {hash.Clear();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_srl))			return srl;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_srl = "srl";
}
class Hiero_prefab_itm {
	public Hiero_prefab_itm(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private byte[] key;
}
class Hiero_prefab_srl extends Dsv_wkr_base {
	private Hiero_prefab_mgr mgr;
	private byte[] key;
	public Hiero_prefab_srl(Hiero_prefab_mgr mgr) {this.mgr = mgr;}
	@Override public Dsv_fld_parser[] Fld_parsers() {return new Dsv_fld_parser[] {Dsv_fld_parser_.Bry_parser};}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: key = Bry_.Mid(src, bgn, end); return true;
			default: return false;
		}
	}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		if (key == null) throw parser.Err_row_bgn("prefab missing key", pos);
		mgr.Add(key);
	}
}
