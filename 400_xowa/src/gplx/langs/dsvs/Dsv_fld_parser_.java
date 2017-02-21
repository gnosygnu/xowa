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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
public class Dsv_fld_parser_ {
	public static final Dsv_fld_parser Bry_parser = Dsv_fld_parser_bry.Instance;
	public static final Dsv_fld_parser Int_parser = Dsv_fld_parser_int.Instance;
	public static final Dsv_fld_parser Line_parser__comment_is_pipe = new Dsv_fld_parser_line(Byte_ascii.Pipe);
	public static Err err_fld_unhandled(Dsv_fld_parser parser, Dsv_wkr_base wkr, int fld_idx, byte[] src, int bgn, int end) {
		throw Err_.new_wo_type("fld unhandled", "parser", Type_adp_.NameOf_obj(parser), "wkr", Type_adp_.NameOf_obj(wkr), "fld_idx", fld_idx, "val", String_.new_u8(src, bgn, end)).Trace_ignore_add_1_();
	}
}
class Dsv_fld_parser_line implements Dsv_fld_parser {
	private byte row_dlm = Byte_ascii.Nl; private final byte comment_dlm;
	public Dsv_fld_parser_line(byte comment_dlm) {this.comment_dlm = comment_dlm;}
	public void Init(byte fld_dlm, byte row_dlm) {
		this.row_dlm = row_dlm;
	}
	public int Parse(Dsv_tbl_parser parser, Dsv_wkr_base wkr, byte[] src, int pos, int src_len, int fld_idx, int fld_bgn) {
		while (true) {
			boolean pos_is_last = pos == src_len;				
			byte b = pos_is_last ? row_dlm : src[pos];
			if		(b == comment_dlm) {
				pos = Bry_find_.Find_fwd_until(src, pos, src_len, row_dlm);
				if (pos == Bry_find_.Not_found)
					pos = src_len;
			}
			else if (b == row_dlm) {
				boolean pass = wkr.Write_bry(parser, fld_idx, src, fld_bgn, pos);
				if (!pass) throw Dsv_fld_parser_.err_fld_unhandled(this, wkr, fld_idx, src, fld_bgn, pos);
				wkr.Commit_itm(parser, pos);
				int rv = pos + 1; // row_dlm is always 1 byte
				parser.Update_by_row(rv);
				return rv; 
			}
			else
				++pos;
		}
	}
}
class Dsv_fld_parser_bry implements Dsv_fld_parser {
	private byte fld_dlm = Byte_ascii.Pipe, row_dlm = Byte_ascii.Nl;
	public void Init(byte fld_dlm, byte row_dlm) {
		this.fld_dlm = fld_dlm; this.row_dlm = row_dlm;
	}
	public int Parse(Dsv_tbl_parser parser, Dsv_wkr_base wkr, byte[] src, int pos, int src_len, int fld_idx, int fld_bgn) {
		while (true) {
			boolean pos_is_last = pos == src_len;				
			byte b = pos_is_last ? row_dlm : src[pos];
			if		(b == fld_dlm) {
				boolean pass = wkr.Write_bry(parser, fld_idx, src, fld_bgn, pos);
				if (!pass) throw Dsv_fld_parser_.err_fld_unhandled(this, wkr, fld_idx, src, fld_bgn, pos);
				int rv = pos + 1; // fld_dlm is always 1 byte
				parser.Update_by_fld(rv);
				return rv;
			}
			else if (b == row_dlm) {
				boolean pass = wkr.Write_bry(parser, fld_idx, src, fld_bgn, pos);
				if (!pass) throw Dsv_fld_parser_.err_fld_unhandled(this, wkr, fld_idx, src, fld_bgn, pos);
				wkr.Commit_itm(parser, pos);
				int rv = pos + 1; // row_dlm is always 1 byte
				parser.Update_by_row(rv);
				return rv; 
			}
			else
				++pos;
		}
	}
	public static final Dsv_fld_parser_bry Instance = new Dsv_fld_parser_bry(); Dsv_fld_parser_bry() {}
}
class Dsv_fld_parser_int implements Dsv_fld_parser {
	private byte fld_dlm = Byte_ascii.Pipe, row_dlm = Byte_ascii.Nl;
	public void Init(byte fld_dlm, byte row_dlm) {
		this.fld_dlm = fld_dlm; this.row_dlm = row_dlm;
	}
	public int Parse(Dsv_tbl_parser parser, Dsv_wkr_base wkr, byte[] src, int pos, int src_len, int fld_idx, int fld_bgn) {
		while (true) {
			boolean pos_is_last = pos == src_len;				
			byte b = pos_is_last ? row_dlm : src[pos];
			if		(b == fld_dlm) {
				boolean pass = wkr.Write_int(parser, fld_idx, pos, Bry_.To_int_or(src, fld_bgn, pos, -1));
				if (!pass) throw Dsv_fld_parser_.err_fld_unhandled(this, wkr, fld_idx, src, fld_bgn, pos);
				int rv = pos + 1; // fld_dlm is always 1 byte
				parser.Update_by_fld(rv);
				return rv;
			}
			else if (b == row_dlm) {
				boolean pass = wkr.Write_int(parser, fld_idx, pos, Bry_.To_int_or(src, fld_bgn, pos, -1));
				if (!pass) throw Dsv_fld_parser_.err_fld_unhandled(this, wkr, fld_idx, src, fld_bgn, pos);
				wkr.Commit_itm(parser, pos);
				int rv = pos + 1; // row_dlm is always 1 byte
				parser.Update_by_row(rv);
				return rv; 
			}
			else
				++pos;
		}
	}
	public static final Dsv_fld_parser_int Instance = new Dsv_fld_parser_int(); Dsv_fld_parser_int() {}
}
