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
package gplx.xowa.apps.gfs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_gfs_bldr {		
	public Bry_bfr Bfr() {return bfr;} private Bry_bfr bfr = Bry_bfr.new_();
	public byte[] Xto_bry() {return bfr.To_bry_and_clear();}
	public Xoa_gfs_bldr Add_byte(byte b)		{bfr.Add_byte(b); return this;}
	public Xoa_gfs_bldr Add_blob(byte[] bry)	{bfr.Add(bry); return this;}
	public Xoa_gfs_bldr Add_proc_init_many(String... ary) {return Add_proc_core_many(false, ary);}
	public Xoa_gfs_bldr Add_proc_init_one(String itm)			{return Add_proc_core_many(false, itm);}
	public Xoa_gfs_bldr Add_proc_cont_one(String itm)			{return Add_proc_core_many(true, itm);}
	public Xoa_gfs_bldr Add_proc_cont_many(String... ary) {return Add_proc_core_many(true, ary);}
	Xoa_gfs_bldr Add_proc_core_many(boolean cont, String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			if (i != 0 || cont) bfr.Add_byte(Byte_ascii.Dot);
			bfr.Add_str(ary[i]);
		}
		return this;
	}
	public Xoa_gfs_bldr Add_indent(int i)			{bfr.Add_byte_repeat(Byte_ascii.Space, i * 2); return this;}
	public Xoa_gfs_bldr Add_parens_str(String v)	{return Add_parens_str(Bry_.new_u8(v));}
	public Xoa_gfs_bldr Add_parens_str(byte[] v)	{return this.Add_paren_bgn().Add_arg_str(v).Add_paren_end();}
	public Xoa_gfs_bldr Add_parens_str_many(String... ary) {
		this.Add_paren_bgn();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) this.Add_comma();
			this.Add_arg_str(Bry_.new_u8(ary[i]));
		}
		this.Add_paren_end();
		return this;
	}
	public Xoa_gfs_bldr Add_arg_int(int v)			{bfr.Add_int_variable(v); return this;}
	public Xoa_gfs_bldr Add_arg_yn(boolean v)			{Add_quote_0(); bfr.Add_byte(v ? Byte_ascii.Ltr_y : Byte_ascii.Ltr_n); Add_quote_0(); return this;}
	public Xoa_gfs_bldr Add_arg_str(byte[] v)		{bfr.Add_byte(Byte_ascii.Apos); Add_str_escape_apos(bfr, v); bfr.Add_byte(Byte_ascii.Apos); return this;}
	public Xoa_gfs_bldr Add_indent()				{bfr.Add_byte(Byte_ascii.Space).Add_byte(Byte_ascii.Space); return this;}
	public Xoa_gfs_bldr Add_nl()					{bfr.Add_byte_nl(); return this;}
	public Xoa_gfs_bldr Add_comma()					{bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space); return this;}
	public Xoa_gfs_bldr Add_curly_bgn_nl()			{bfr.Add_byte(Byte_ascii.Space).Add_byte(Byte_ascii.Curly_bgn).Add_byte_nl(); return this;}
	public Xoa_gfs_bldr Add_curly_end_nl()			{bfr.Add_byte(Byte_ascii.Curly_end).Add_byte_nl(); return this;}
	public Xoa_gfs_bldr Add_paren_bgn()				{bfr.Add_byte(Byte_ascii.Paren_bgn); return this;}
	public Xoa_gfs_bldr Add_paren_end()				{bfr.Add_byte(Byte_ascii.Paren_end); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_bgn()			{bfr.Add(Bry_xquote_bgn); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_end()			{bfr.Add(Bry_xquote_end); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_apos_bgn()	{bfr.Add_byte(Byte_ascii.Paren_bgn).Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Nl); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_apos_end()	{bfr.Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Paren_end).Add_byte(Byte_ascii.Semic).Add_byte(Byte_ascii.Nl); return this;}
	public Xoa_gfs_bldr Add_quote_0()				{bfr.Add_byte(Byte_ascii.Apos); return this;}
	public Xoa_gfs_bldr Add_term_nl()				{bfr.Add(Bry_semic_nl); return this;}
	public Xoa_gfs_bldr Add_eq_str(String k, byte[] v) {
		bfr.Add_str(k);
		bfr.Add(Bry_eq);
		bfr.Add_byte_apos();
		bfr.Add(v);
		bfr.Add_byte_apos();
		bfr.Add(Bry_semic_nl);
		return this;
	}
	private static final byte[] Bry_eq = Bry_.new_a7(" = "), Bry_semic_nl = Bry_.new_a7(";\n");
	private void Add_str_escape_apos(Bry_bfr bfr, byte[] src) {
		int len = src.length;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			if	(b == Byte_ascii.Apos)
				bfr.Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Apos);
			else
				bfr.Add_byte(b);
		}
	}
	public static final byte[]
		Bry_xquote_bgn			= Bry_.new_a7("<:['\n")
	,	Bry_xquote_end			= Bry_.new_a7("']:>\n")
	;
}
