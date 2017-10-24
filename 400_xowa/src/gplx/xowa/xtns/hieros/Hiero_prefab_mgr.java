/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.dsvs.*;
class Hiero_prefab_mgr implements Gfo_invk {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	private Hiero_prefab_srl srl;
	public Hiero_prefab_mgr() {srl = new Hiero_prefab_srl(this);}
	public int Len() {return hash.Count();}
	public Hiero_prefab_itm Get_at(int i) {return (Hiero_prefab_itm)hash.Get_at(i);}
	public Hiero_prefab_itm Get_by_key(byte[] key) {return (Hiero_prefab_itm)hash.Get_by(key);}
	public void Add(byte[] key) {hash.Add(key, new Hiero_prefab_itm(key));}
	public void Clear() {hash.Clear();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_srl))			return srl;
		else	return Gfo_invk_.Rv_unhandled;
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
