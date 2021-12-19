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
package gplx.xowa.apps.gfs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Xoa_gfs_bldr {		
	public BryWtr Bfr() {return bfr;} private BryWtr bfr = BryWtr.New();
	public byte[] Xto_bry() {return bfr.ToBryAndClear();}
	public Xoa_gfs_bldr Add_byte(byte b)		{bfr.AddByte(b); return this;}
	public Xoa_gfs_bldr Add_blob(byte[] bry)	{bfr.Add(bry); return this;}
	public Xoa_gfs_bldr Add_proc_init_many(String... ary) {return Add_proc_core_many(false, ary);}
	public Xoa_gfs_bldr Add_proc_init_one(String itm)			{return Add_proc_core_many(false, itm);}
	public Xoa_gfs_bldr Add_proc_cont_one(String itm)			{return Add_proc_core_many(true, itm);}
	public Xoa_gfs_bldr Add_proc_cont_many(String... ary) {return Add_proc_core_many(true, ary);}
	Xoa_gfs_bldr Add_proc_core_many(boolean cont, String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			if (i != 0 || cont) bfr.AddByte(AsciiByte.Dot);
			bfr.AddStrU8(ary[i]);
		}
		return this;
	}
	public Xoa_gfs_bldr Add_indent(int i)			{bfr.AddByteRepeat(AsciiByte.Space, i * 2); return this;}
	public Xoa_gfs_bldr Add_parens_str(String v)	{return Add_parens_str(BryUtl.NewU8(v));}
	public Xoa_gfs_bldr Add_parens_str(byte[] v)	{return this.Add_paren_bgn().Add_arg_str(v).Add_paren_end();}
	public Xoa_gfs_bldr Add_parens_str_many(String... ary) {
		this.Add_paren_bgn();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) this.Add_comma();
			this.Add_arg_str(BryUtl.NewU8(ary[i]));
		}
		this.Add_paren_end();
		return this;
	}
	public Xoa_gfs_bldr Add_arg_int(int v)			{bfr.AddIntVariable(v); return this;}
	public Xoa_gfs_bldr Add_arg_yn(boolean v)			{Add_quote_0(); bfr.AddByte(v ? AsciiByte.Ltr_y : AsciiByte.Ltr_n); Add_quote_0(); return this;}
	public Xoa_gfs_bldr Add_arg_str(byte[] v)		{bfr.AddByte(AsciiByte.Apos); Add_str_escape_apos(bfr, v); bfr.AddByte(AsciiByte.Apos); return this;}
	public Xoa_gfs_bldr Add_indent()				{bfr.AddByte(AsciiByte.Space).AddByte(AsciiByte.Space); return this;}
	public Xoa_gfs_bldr Add_nl()					{bfr.AddByteNl(); return this;}
	public Xoa_gfs_bldr Add_comma()					{bfr.AddByte(AsciiByte.Comma).AddByte(AsciiByte.Space); return this;}
	public Xoa_gfs_bldr Add_curly_bgn_nl()			{bfr.AddByte(AsciiByte.Space).AddByte(AsciiByte.CurlyBgn).AddByteNl(); return this;}
	public Xoa_gfs_bldr Add_curly_end_nl()			{bfr.AddByte(AsciiByte.CurlyEnd).AddByteNl(); return this;}
	public Xoa_gfs_bldr Add_paren_bgn()				{bfr.AddByte(AsciiByte.ParenBgn); return this;}
	public Xoa_gfs_bldr Add_paren_end()				{bfr.AddByte(AsciiByte.ParenEnd); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_bgn()			{bfr.Add(Bry_xquote_bgn); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_end()			{bfr.Add(Bry_xquote_end); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_apos_bgn()	{bfr.AddByte(AsciiByte.ParenBgn).AddByte(AsciiByte.Apos).AddByte(AsciiByte.Nl); return this;}
	public Xoa_gfs_bldr Add_quote_xtn_apos_end()	{bfr.AddByte(AsciiByte.Apos).AddByte(AsciiByte.ParenEnd).AddByte(AsciiByte.Semic).AddByte(AsciiByte.Nl); return this;}
	public Xoa_gfs_bldr Add_quote_0()				{bfr.AddByte(AsciiByte.Apos); return this;}
	public Xoa_gfs_bldr Add_term_nl()				{bfr.Add(Bry_semic_nl); return this;}
	public Xoa_gfs_bldr Add_eq_str(String k, byte[] v) {
		bfr.AddStrU8(k);
		bfr.Add(Bry_eq);
		bfr.AddByteApos();
		bfr.Add(v);
		bfr.AddByteApos();
		bfr.Add(Bry_semic_nl);
		return this;
	}
	private static final byte[] Bry_eq = BryUtl.NewA7(" = "), Bry_semic_nl = BryUtl.NewA7(";\n");
	private void Add_str_escape_apos(BryWtr bfr, byte[] src) {
		int len = src.length;
		for (int i = 0; i < len; i++) {
			byte b = src[i];
			if	(b == AsciiByte.Apos)
				bfr.AddByte(AsciiByte.Apos).AddByte(AsciiByte.Apos);
			else
				bfr.AddByte(b);
		}
	}
	public static final byte[]
		Bry_xquote_bgn			= BryUtl.NewA7("<:['\n")
	,	Bry_xquote_end			= BryUtl.NewA7("']:>\n")
	;
}
