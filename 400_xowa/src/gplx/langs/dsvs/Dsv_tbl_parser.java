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
public class Dsv_tbl_parser implements Gfo_invk, Rls_able {
	private Dsv_wkr_base mgr;
	private Dsv_fld_parser[] fld_parsers = new Dsv_fld_parser[2]; private int fld_parsers_len = 2;
	public byte[] Src() {return src;} private byte[] src;
	public int Fld_bgn() {return fld_bgn;} private int fld_bgn = 0;
	public int Fld_idx() {return fld_idx;} private int fld_idx = 0;
	public int Row_bgn() {return row_bgn;} private int row_bgn = 0;
	public int Row_idx() {return row_idx;} private int row_idx = 0;
	public boolean Skip_blank_lines() {return skip_blank_lines;} public Dsv_tbl_parser Skip_blank_lines_(boolean v) {skip_blank_lines = v; return this;} private boolean skip_blank_lines = true;
	public byte Fld_dlm() {return fld_dlm;} public Dsv_tbl_parser Fld_dlm_(byte v) {fld_dlm = v; return this;} private byte fld_dlm = Byte_ascii.Pipe;
	public byte Row_dlm() {return row_dlm;} public Dsv_tbl_parser Row_dlm_(byte v) {row_dlm = v; return this;} private byte row_dlm = Byte_ascii.Nl;
	public void Init(Dsv_wkr_base mgr, Dsv_fld_parser... fld_parsers) {
		this.mgr = mgr;
		this.fld_parsers = fld_parsers;
		this.fld_parsers_len = fld_parsers.length;
		for (int i = 0; i < fld_parsers_len; i++)
			fld_parsers[i].Init(fld_dlm, row_dlm);
	}
	public void Clear() {
		fld_bgn = fld_idx = row_bgn = row_idx = 0;
	}
	public Err Err_row_bgn(String fmt, int pos) {
		return Err_.new_wo_type(fmt, "line", String_.new_u8(src, row_bgn, pos)).Trace_ignore_add_1_();
	}
	public void Update_by_fld(int pos) {
		fld_bgn = pos;
		++fld_idx;
	}
	public void Update_by_row(int pos) {
		row_bgn = fld_bgn = pos;
		++row_idx;
		fld_idx = 0;
	}
	public void Parse(byte[] src) {
		int src_len = src.length; if (src_len == 0) return; // NOTE: do not process if empty; note that loop below will process once for empty row
		this.src = src;
		int pos = 0;
		while (true) {
			if (fld_idx == 0 && skip_blank_lines) {	// row committed; skip blank lines
				while (pos < src_len) {
					if (src[pos] == row_dlm) {
						++pos;
						row_bgn = fld_bgn = pos;
					}
					else
						break;
				}
			}
			if (fld_idx == fld_parsers_len) break;
			Dsv_fld_parser fld_parser = fld_parsers[fld_idx];
			pos = fld_parser.Parse(this, mgr, src, pos, src_len, fld_idx, fld_bgn);
			if (	pos > src_len						// pos is now fully past src_len; exit
				||	pos == src_len && fld_idx == 0		// last pos but fld_idx > 0; do one more iteration which will "commit row; EX: 2 fields and src of "a|"; EOS should close out row
				) break;
		}
	}
	public void Rls() {
		src = null; fld_parsers = null; mgr = null; fld_parsers_len = 0;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_load_by_str))		Parse(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_load_by_str = "load_by_str";
}
