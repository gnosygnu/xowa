/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.encoders.HexUtl;
import gplx.types.basics.strings.unicodes.Ustring;
import gplx.types.basics.strings.unicodes.UstringUtl;

public class Json_doc_wtr {
	private int indent = -2;
	private BryWtr bfr = BryWtr.NewAndReset(255);
	public void Opt_unicode_y_() {opt_unicode = true;} private boolean opt_unicode;
	public Json_doc_wtr Indent() {return Indent(indent);}
	private Json_doc_wtr Indent(int v) {if (v > 0) bfr.AddByteRepeat(AsciiByte.Space, v); return this;}
	public Json_doc_wtr Indent_add() {indent += 2; return this;}
	public Json_doc_wtr Indent_del() {indent -= 2; return this;}
	public Json_doc_wtr Nde_bgn() {Indent_add();	Indent(); bfr.AddByte(AsciiByte.CurlyBgn).AddByteNl(); return this;}
	public Json_doc_wtr Nde_end() {					Indent(); bfr.AddByte(AsciiByte.CurlyEnd).AddByteNl(); Indent_del(); return this;}
	public Json_doc_wtr Ary_bgn() {Indent_add();	Indent(); bfr.AddByte(AsciiByte.BrackBgn).AddByteNl(); return this;}
	public Json_doc_wtr Ary_end() {					Indent(); bfr.AddByte(AsciiByte.BrackEnd).AddByteNl(); Indent_del(); return this;}
	public Json_doc_wtr New_line() {bfr.AddByteNl(); return this;}
	public Json_doc_wtr Str(byte[] v) {
		if (v == null)
			bfr.Add(ObjectUtl.NullBry);
		else {
			bfr.AddByte(AsciiByte.Quote);
			if (opt_unicode) {
				Ustring ustr = UstringUtl.NewCodepoints(StringUtl.NewU8(v));
				int ustr_len = ustr.LenInData();
				for (int i = 0; i < ustr_len; i++) {
					int cp = ustr.GetData(i);
					Write_str_codepoint(bfr, cp);
				}
			}
			else {
				bfr.AddBryEscape(AsciiByte.Quote, Escaped__quote, v, 0, v.length);
			}
			bfr.AddByte(AsciiByte.Quote);
		}
		return this;
	}
	private void Write_str_codepoint(BryWtr bfr, int val) {
		switch (val) { // REF: https://www.json.org/
			case AsciiByte.Quote:
				bfr.AddByteBackslash().AddByte(AsciiByte.Quote);
				break;
			case AsciiByte.Backslash:
				bfr.AddByteBackslash().AddByte(AsciiByte.Backslash);
				break;
			case AsciiByte.Backfeed:
				bfr.AddByteBackslash().AddByte(AsciiByte.Ltr_b);
				break;
			case AsciiByte.Formfeed:
				bfr.AddByteBackslash().AddByte(AsciiByte.Ltr_f);
				break;
			case AsciiByte.Nl:
				bfr.AddByteBackslash().AddByte(AsciiByte.Ltr_n);
				break;
			case AsciiByte.Cr:
				bfr.AddByteBackslash().AddByte(AsciiByte.Ltr_r);
				break;
			case AsciiByte.Tab:
				bfr.AddByteBackslash().AddByte(AsciiByte.Ltr_t);
				break;
			default:
				if (   val < AsciiByte.Space // control characters
					|| val == 160  // nbsp
					|| val == 8206 // left to right
					|| val == 8207 // right to left
					) {
					// convert to \u1234
					bfr.AddByteBackslash().AddByte(AsciiByte.Ltr_u).AddStrA7(HexUtl.ToStr(val, 4));
				}
				else {
					bfr.AddU8Int(val);
				}
				break;
		}
	}
	public Json_doc_wtr Int(int v) {bfr.AddIntVariable(v); return this;}
	public Json_doc_wtr Double(double v) {bfr.AddDouble(v); return this;}
	public Json_doc_wtr Comma() {Indent(); bfr.AddByte(AsciiByte.Comma).AddByteNl(); return this;}
	public Json_doc_wtr Kv_ary_empty(boolean comma, byte[] key) {
		Key_internal(comma, key);
		bfr.AddByte(AsciiByte.BrackBgn).AddByte(AsciiByte.BrackEnd);
		bfr.AddByteNl();
		return this;
	}
	public Json_doc_wtr Kv(boolean comma, String key, byte[] val) {return Kv(comma, BryUtl.NewU8(key), val);}
	public Json_doc_wtr Kv(boolean comma, byte[] key, byte[] val) {
		Key_internal(comma, key);
		Str(val);
		bfr.AddByteNl();
		return this;
	}
	public Json_doc_wtr Kv_double(boolean comma, byte[] key, double v) {
		Key_internal(comma, key);
		Double(v);
		bfr.AddByteNl();
		return this;
	}
	public Json_doc_wtr Kv(boolean comma, String key, int v) {return Kv(comma, BryUtl.NewU8(key), v);}
	public Json_doc_wtr Kv(boolean comma, byte[] key, int v) {
		Key_internal(comma, key);
		Int(v);
		bfr.AddByteNl();
		return this;
	}
	public Json_doc_wtr Kv(boolean comma, String key, double v) {return Kv(comma, BryUtl.NewU8(key), BryUtl.NewU8(DoubleUtl.ToStr(v)));}
	public Json_doc_wtr Kv(boolean comma, String key, boolean v) {return Kv(comma, BryUtl.NewU8(key), v ? BoolUtl.YBry : BoolUtl.NBry);}
	public Json_doc_wtr Key(boolean comma, String key) {return Key(comma, BryUtl.NewU8(key));}
	public Json_doc_wtr Key(boolean comma, byte[] key) {
		Key_internal(comma, key);
		bfr.AddByteNl();
		return this;
	}
	public Json_doc_wtr Val(boolean comma, int v) {
		Val_internal(comma);
		Int(v);
		New_line();
		return this;
	}
	public Json_doc_wtr Val(boolean comma, byte[] v) {
		Val_internal(comma);
		Str(v);
		New_line();
		return this;
	}
	Json_doc_wtr Val_internal(boolean comma) {
		Indent();
		bfr.AddByte(comma ? AsciiByte.Comma : AsciiByte.Space);
		bfr.AddByte(AsciiByte.Space);
		return this;
	}
	Json_doc_wtr Key_internal(boolean comma, byte[] key) {
		Indent();
		bfr.AddByte(comma ? AsciiByte.Comma : AsciiByte.Space);
		bfr.AddByte(AsciiByte.Space);
		Str(key);
		bfr.AddByte(AsciiByte.Colon);
		return this;
	}
	public byte[] Bld() {return bfr.ToBryAndClear();}
	public String Bld_as_str() {return bfr.ToStrAndClear();}
	private static final byte[] Escaped__quote = BryUtl.NewA7("\\\"");
}
