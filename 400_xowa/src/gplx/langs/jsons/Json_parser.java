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

import gplx.objects.primitives.BoolUtl;
import gplx.Bry_;
import gplx.objects.strings.AsciiByte;
import gplx.Char_;
import gplx.Err;
import gplx.Err_;
import gplx.Int_;
import gplx.String_;
import gplx.core.primitives.Gfo_number_parser;

public class Json_parser {
	private byte[] src;
	private int src_len, pos;
	private final Gfo_number_parser num_parser = new Gfo_number_parser();
	public Json_doc Parse_by_apos_ary(String... ary) {return Parse_by_apos(String_.Concat_lines_nl(ary));}
	public Json_doc Parse_by_apos(String s) {return Parse(Bry_.Replace(Bry_.new_u8(s), AsciiByte.Apos, AsciiByte.Quote));}
	public Json_doc Parse(String src) {return Parse(Bry_.new_u8(src));}
	public Json_doc Parse(byte[] src) {
		this.src = src;				if (src == null) return null;
		this.src_len = src.length;	if (src_len == 0) return null;
		this.pos = 0;
		Skip_ws();
		boolean root_is_nde = true;
		switch (src[pos]) {
			case AsciiByte.CurlyBgn:	root_is_nde = BoolUtl.Y; break;
			case AsciiByte.BrackBgn:	root_is_nde = BoolUtl.N; break;
			default:					return null;
		}
		Skip_ws();
		Json_doc doc = new Json_doc();
		Json_grp root = null;
		if (root_is_nde)
			root = Make_nde(doc);
		else
			root = Make_ary(doc);
		doc.Ctor(src, root);
		return doc;
	}
	private Json_nde Make_nde(Json_doc doc) {
		++pos;	// brack_bgn
		Json_nde nde = Json_nde.NewByDoc(doc, pos);
		while (pos < src_len) {
			Skip_ws();
			if (src[pos] == AsciiByte.CurlyEnd) 	{++pos; return nde;}
			else									nde.Add(Make_kv(doc));
			Skip_ws();
			switch (src[pos++]) {
				case AsciiByte.Comma:			break;
				case AsciiByte.CurlyEnd:		return nde;
				default: throw Err_.new_unhandled(src[pos - 1]);
			}
		}
		throw Err_.new_wo_type("eos inside nde");
	}
	private Json_itm Make_kv(Json_doc doc) {
		Json_itm key = Make_string(doc);
		Skip_ws();
		Chk(AsciiByte.Colon);
		Skip_ws();
		Json_itm val = Make_val(doc);
		return new Json_kv(key, val);
	}
	private Json_itm Make_val(Json_doc doc) {
		while (pos < src_len) {
			byte b = src[pos];
			switch (b) {
				case AsciiByte.Ltr_n:		return Make_literal(Bry_null_ull	, 3, Json_itm_null.Null);
				case AsciiByte.Ltr_f:		return Make_literal(Bry_bool_alse	, 4, Json_itm_bool.Bool_n);
				case AsciiByte.Ltr_t:		return Make_literal(Bry_bool_rue	, 3, Json_itm_bool.Bool_y);
				case AsciiByte.Quote:		return Make_string(doc);
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
				case AsciiByte.Dash:		return Make_num(doc);
				case AsciiByte.BrackBgn:	return Make_ary(doc);
				case AsciiByte.CurlyBgn:	return Make_nde(doc);
			}
			throw Err_.new_unhandled(Char_.To_str(b));
		}
		throw Err_.new_wo_type("eos reached in val");
	}
	private Json_itm Make_literal(byte[] remainder, int remainder_len, Json_itm singleton) {
		++pos;	// 1st char
		int literal_end = pos + remainder_len;
		if (Bry_.Eq(src, pos, literal_end, remainder)) {
			pos = literal_end;
			return singleton;
		}
		throw Err_.new_("json.parser", "invalid literal", "excerpt", Bry_.Mid_by_len_safe(src, pos - 1, 16));
	}
	private Json_itm Make_string(Json_doc doc) {
		int bgn = pos++;	// ++: quote_bgn
		boolean escaped = false;
		while (pos < src_len) {
			switch (src[pos]) {
				case AsciiByte.Backslash:
					++pos;	// backslash
					switch (src[pos]) {
						case AsciiByte.Ltr_u:		pos += 5; break;	// \uFFFF	1 u + 4 hex-dec; ISSUE#:486; DATE:2019-06-02
						default:					++pos;	break;		// \?		" \ / b f n r t
					}
					escaped = true;
					break;
				case AsciiByte.Quote:
					return Json_itm_str.NewByDoc(doc, bgn, ++pos, escaped);	// ++: quote_end
				default:
					++pos;
					break;
			}		
		}
		throw Err_.new_wo_type("eos reached inside quote");
	}
	private Json_itm Make_num(Json_doc doc) {
		int num_bgn = pos;
		boolean loop = true;
		while (loop) {
			if (pos == src_len) throw Err_.new_wo_type("eos reached inside num");
			switch (src[pos]) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					++pos;
					break;
				case AsciiByte.Dot:
				case AsciiByte.Dash: case AsciiByte.Plus:
				case AsciiByte.Ltr_E: case AsciiByte.Ltr_e:	// e e+ e- E E+ E-
					++pos;
					break;
				default:
					loop = false;
					break;
			}
		}
		num_parser.Parse(src, num_bgn, pos);
		if (num_parser.Has_frac())
			return Json_itm_decimal.NewByDoc(doc, num_bgn, pos);
		else {
			if (num_parser.Is_int())
				return Json_itm_int.NewByDoc(doc, num_bgn, pos);
			else
				return Json_itm_long.NewByDoc(doc, num_bgn, pos);
		}
	}
	private Json_ary Make_ary(Json_doc doc) {
		Json_ary rv = Json_ary.NewByDoc(doc, pos++, pos);	// brack_bgn
		while (pos < src_len) {
			Skip_ws();
			if (src[pos] == AsciiByte.BrackEnd) 	{++pos; return rv;}
			else									rv.Add(Make_val(doc));
			Skip_ws();
			switch (src[pos]) {
				case AsciiByte.Comma:			++pos; break;
				case AsciiByte.BrackEnd:		++pos; return rv;
			}
		}
		throw Err_.new_wo_type("eos inside ary");
	}
	private void Skip_ws() {
		while (pos < src_len) {
			switch (src[pos]) {
				case AsciiByte.Space: case AsciiByte.Nl: case AsciiByte.Tab: case AsciiByte.Cr: ++pos; break;
				default: return;
			}
		}
	}
	private void Chk(byte expd) {
		if (src[pos] == expd)
			++pos;
		else
			throw err_(src, pos, "expected '{0}' but got '{1}'", Char_.To_str(expd), Char_.To_str(src[pos]));
	}
	private Err err_(byte[] src, int bgn, String fmt, Object... args) {return err_(src, bgn, src.length, fmt, args);}
	private Err err_(byte[] src, int bgn, int src_len, String fmt, Object... args) {
		String msg = String_.Format(fmt, args) + " " + Int_.To_str(bgn) + " " + String_.new_u8__by_len(src, bgn, 20);
		return Err_.new_wo_type(msg);
	}
	private static final byte[] Bry_bool_rue = Bry_.new_a7("rue"), Bry_bool_alse = Bry_.new_a7("alse"), Bry_null_ull = Bry_.new_a7("ull");
	public static Json_doc ParseToJdoc(String src) {
		Json_parser parser = new Json_parser();
		return parser.Parse(src);
	}
}
