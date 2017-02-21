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
class Hiero_file_mgr implements Gfo_invk {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	private Hiero_file_srl srl;
	public Hiero_file_mgr() {srl = new Hiero_file_srl(this);}
	public int Len() {return hash.Count();}
	public Hiero_file_itm Get_at(int i) {return (Hiero_file_itm)hash.Get_at(i);}
	public void Add(byte[] key, int file_w, int file_h) {hash.Add(key, new Hiero_file_itm(key, file_w, file_h));}
	public void Clear() {hash.Clear();}
	public Hiero_file_itm Get_by_key(byte[] key) {return (Hiero_file_itm)hash.Get_by(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_srl))			return srl;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_srl = "srl";
}
class Hiero_file_itm {
	public Hiero_file_itm(byte[] key, int file_w, int file_h) {this.key = key; this.file_w = file_w; this.file_h = file_h;}
	public byte[] Key() {return key;} private byte[] key;
	public int File_w() {return file_w;} private int file_w;
	public int File_h() {return file_h;} private int file_h;
}
class Hiero_file_srl extends Dsv_wkr_base {
	private Hiero_file_mgr mgr;
	private byte[] key;
	private int file_w = -1, file_h = -1;
	public Hiero_file_srl(Hiero_file_mgr mgr) {this.mgr = mgr;}
	@Override public Dsv_fld_parser[] Fld_parsers() {return new Dsv_fld_parser[] {Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Int_parser, Dsv_fld_parser_.Int_parser};}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: key = Bry_.Mid(src, bgn, end); return true;
			default: return false;
		}
	}
	@Override public boolean Write_int(Dsv_tbl_parser parser, int fld_idx, int pos, int val_int) {
		switch (fld_idx) {
			case 1: file_w = val_int; return true;
			case 2: file_h = val_int; return true;
			default: return false;
		}
	}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		if (key == null)	throw parser.Err_row_bgn("file missing key", pos);
		if (file_w == -1)	throw parser.Err_row_bgn("file missing w", pos);
		if (file_h == -1)	throw parser.Err_row_bgn("file missing h", pos);
		mgr.Add(key, file_w, file_h);
		key = null; file_w = file_h = -1;
	}
}
