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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
abstract class Xob_css_tkn__base {
	public void Init(int tid, int pos_bgn, int pos_end) {
		this.tid = tid; this.pos_bgn = pos_bgn; this.pos_end = pos_end;
	}
	public int Tid() {return tid;} protected int tid;
	public int Pos_bgn() {return pos_bgn;} protected int pos_bgn;
	public int Pos_end() {return pos_end;} protected int pos_end;
	@gplx.Virtual public void Process(Xob_mirror_mgr mgr) {}
	public abstract int Write(Bry_bfr bfr, byte[] src);
	public static final int Tid_warn = 1, Tid_base64 = 2, Tid_url = 3, Tid_import = 4;
}
class Xob_css_tkn__warn extends Xob_css_tkn__base {
	public String Fail_msg() {return fail_msg;} private String fail_msg;
	@Override public void Process(Xob_mirror_mgr mgr) {
		mgr.Usr_dlg().Warn_many("", "", fail_msg); 
	}
	@Override public int Write(Bry_bfr bfr, byte[] src) {
		bfr.Add_mid(src, pos_bgn, pos_end);
		return pos_end;
	}
	public static Xob_css_tkn__warn new_(int pos_bgn, int pos_end, String fmt, Object... fmt_args) {
		Xob_css_tkn__warn rv = new Xob_css_tkn__warn();
		rv.Init(Tid_warn, pos_bgn, pos_end);
		rv.fail_msg = String_.Format(fmt, fmt_args);
		return rv;
	}
}
class Xob_css_tkn__base64 extends Xob_css_tkn__base {
	@Override public int Write(Bry_bfr bfr, byte[] src) {
		bfr.Add_mid(src, pos_bgn, pos_end);
		return pos_end;
	}
	public static Xob_css_tkn__base64 new_(int pos_bgn, int pos_end) {
		Xob_css_tkn__base64 rv = new Xob_css_tkn__base64();
		rv.Init(Tid_base64, pos_bgn, pos_end);
		return rv;
	}
}
class Xob_css_tkn__url extends Xob_css_tkn__base {
	public byte Quote_byte() {return quote_byte;} private byte quote_byte;
	public byte[] Src_url() {return src_url;} private byte[] src_url;
	public byte[] Trg_url() {return trg_url;} private byte[] trg_url;
	@Override public void Process(Xob_mirror_mgr mgr) {
		mgr.File_hash().Add_if_new(src_url, new Xobc_download_itm(Xobc_download_itm.Tid_file, String_.new_utf8_(src_url), trg_url));
	}
	@Override public int Write(Bry_bfr bfr, byte[] src) {
		byte quote = quote_byte; if (quote == Byte_ascii.Nil) quote = Byte_ascii.Apos;
		bfr.Add_str_ascii(" url(");							// EX: ' url('
		bfr.Add_byte(quote).Add(trg_url).Add_byte(quote);	// EX: '"a.png"'
		bfr.Add_byte(Byte_ascii.Paren_end);					// EX: ')'
		return pos_end;
	}
	public static Xob_css_tkn__url new_(int pos_bgn, int pos_end, byte[] src_url, byte quote_byte) {
		Xob_css_tkn__url rv = new Xob_css_tkn__url();
		rv.Init(Tid_url, pos_bgn, pos_end);
		rv.src_url = src_url; rv.trg_url = To_fsys(src_url); rv.quote_byte = quote_byte;
		return rv;
	}
	public static byte[] To_fsys(byte[] src) {
		if (!Op_sys.Cur().Tid_is_wnt()) return src;
		src = Bry_.Copy(src); // NOTE: must call ByteAry.Copy else url_actl will change *inside* bry
		int len = src.length;
		for (int i = 0; i < len; ++i) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Slash:
				case Byte_ascii.Backslash:
					break;
				case Byte_ascii.Lt: case Byte_ascii.Gt: case Byte_ascii.Colon: case Byte_ascii.Pipe: case Byte_ascii.Question: case Byte_ascii.Asterisk: case Byte_ascii.Quote:
					src[i] = Byte_ascii.Underline;
					break;
				default:
					break;
			}
		}
		return src;
	}
}
class Xob_css_tkn__import extends Xob_css_tkn__base {
	public byte Quote_byte() {return quote_byte;} private byte quote_byte;
	public byte[] Src_url() {return src_url;} private byte[] src_url;
	public byte[] Trg_url() {return trg_url;} private byte[] trg_url;
	@Override public void Process(Xob_mirror_mgr mgr) {
		mgr.Code_add(src_url);
	}
	@Override public int Write(Bry_bfr bfr, byte[] src) {
		byte quote = quote_byte; if (quote == Byte_ascii.Nil) quote = Byte_ascii.Apos;
		bfr.Add_str_ascii(" @import url(");					// EX: ' @import url('
		bfr.Add_byte(quote).Add(trg_url).Add_byte(quote);	// EX: '"a.png"'
		bfr.Add_byte(Byte_ascii.Paren_end);					// EX: ')'
		return pos_end;
	}
	public static Xob_css_tkn__import new_(int pos_bgn, int pos_end, byte[] src_url, byte[] trg_url, byte quote_byte) {
		Xob_css_tkn__import rv = new Xob_css_tkn__import();
		rv.Init(Tid_import, pos_bgn, pos_end);
		rv.src_url = src_url; rv.trg_url = trg_url; rv.quote_byte = quote_byte;
		return rv;
	}
}
