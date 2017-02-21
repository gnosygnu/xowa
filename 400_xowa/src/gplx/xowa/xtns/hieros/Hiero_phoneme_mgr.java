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
class Hiero_phoneme_mgr implements Gfo_invk {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	private Hiero_phoneme_srl srl;
	public Hiero_phoneme_mgr() {srl = new Hiero_phoneme_srl(this);}
	public int Len() {return hash.Count();}
	public Hiero_phoneme_itm Get_at(int i) {return (Hiero_phoneme_itm)hash.Get_at(i);}
	public void Add(byte[] key, byte[] val) {hash.Add(key, new Hiero_phoneme_itm(key, val));}
	public void Clear() {hash.Clear();}
	public Hiero_phoneme_itm Get_by_key(byte[] key) {return (Hiero_phoneme_itm)hash.Get_by(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_srl))			return srl;
		else	return Gfo_invk_.Rv_unhandled;
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
