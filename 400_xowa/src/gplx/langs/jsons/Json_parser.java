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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
import gplx.core.primitives.*;
public class Json_parser {
	private byte[] src; private int src_len, pos; private final    Gfo_number_parser num_parser = new Gfo_number_parser();
	public Json_factory Factory() {return factory;} private final    Json_factory factory = new Json_factory();
	public Json_doc Parse_by_apos_ary(String... ary) {return Parse_by_apos(String_.Concat_lines_nl(ary));}
	public Json_doc Parse_by_apos(String s) {return Parse(Bry_.Replace(Bry_.new_u8(s), Byte_ascii.Apos, Byte_ascii.Quote));}
	public Json_doc Parse(String src) {return Parse(Bry_.new_u8(src));}
	public Json_doc Parse(byte[] src) {
		synchronized (factory) {
			this.src = src;				if (src == null) return null;
			this.src_len = src.length;	if (src_len == 0) return null;
			this.pos = 0;
			Skip_ws();
			boolean root_is_nde = true;
			switch (src[pos]) {
				case Byte_ascii.Curly_bgn:	root_is_nde = Bool_.Y; break;
				case Byte_ascii.Brack_bgn:	root_is_nde = Bool_.N; break;
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
	}
	private Json_nde Make_nde(Json_doc doc) {
		++pos;	// brack_bgn
		Json_nde nde = new Json_nde(doc, pos);
		while (pos < src_len) {
			Skip_ws();
			if (src[pos] == Byte_ascii.Curly_end) 	{++pos; return nde;}
			else									nde.Add(Make_kv(doc));
			Skip_ws();
			switch (src[pos++]) {
				case Byte_ascii.Comma:			break;
				case Byte_ascii.Curly_end:		return nde;
				default: throw Err_.new_unhandled(src[pos - 1]);
			}
		}
		throw Err_.new_wo_type("eos inside nde");
	}
	private Json_itm Make_kv(Json_doc doc) {
		Json_itm key = Make_string(doc);
		Skip_ws();
		Chk(Byte_ascii.Colon);
		Skip_ws();
		Json_itm val = Make_val(doc);
		return new Json_kv(key, val);
	}
	private Json_itm Make_val(Json_doc doc) {
		while (pos < src_len) {
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Ltr_n:		return Make_literal(Bry_null_ull	, 3, factory.Null());
				case Byte_ascii.Ltr_f:		return Make_literal(Bry_bool_alse	, 4, factory.Bool_n());
				case Byte_ascii.Ltr_t:		return Make_literal(Bry_bool_rue	, 3, factory.Bool_y());
				case Byte_ascii.Quote:		return Make_string(doc);
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Dash:		return Make_num(doc);
				case Byte_ascii.Brack_bgn:	return Make_ary(doc);
				case Byte_ascii.Curly_bgn:	return Make_nde(doc);
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
		boolean exact = true; 
		while (pos < src_len) {
			switch (src[pos]) {
				case Byte_ascii.Backslash:
					++pos;	// backslash
					switch (src[pos]) {
						case Byte_ascii.Ltr_u:		pos += 4; break;	// \uFFFF	4 hex-dec
						default:					++pos;	break;		// \?		" \ / b f n r t
					}
					exact = false;
					break;
				case Byte_ascii.Quote:
					return factory.Str(doc, bgn, ++pos, exact);	// ++: quote_end
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
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					++pos;
					break;
				case Byte_ascii.Dot:
				case Byte_ascii.Dash: case Byte_ascii.Plus:
				case Byte_ascii.Ltr_E: case Byte_ascii.Ltr_e:	// e e+ e- E E+ E-
					++pos;
					break;
				default:
					loop = false;
					break;
			}
		}
		num_parser.Parse(src, num_bgn, pos);
		if (num_parser.Has_frac())
			return factory.Decimal(doc, num_bgn, pos);
		else {
			if (num_parser.Is_int())
				return factory.Int(doc, num_bgn, pos);
			else
				return factory.Long(doc, num_bgn, pos);
		}
	}
	private Json_ary Make_ary(Json_doc doc) {
		Json_ary rv = factory.Ary(pos++, pos);	// brack_bgn
		while (pos < src_len) {
			Skip_ws();
			if (src[pos] == Byte_ascii.Brack_end) 	{++pos; return rv;}
			else									rv.Add(Make_val(doc));
			Skip_ws();
			switch (src[pos]) {
				case Byte_ascii.Comma:			++pos; break;
				case Byte_ascii.Brack_end:		++pos; return rv;
			}
		}
		throw Err_.new_wo_type("eos inside ary");
	}
	private void Skip_ws() {
		while (pos < src_len) {
			switch (src[pos]) {
				case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab: case Byte_ascii.Cr: ++pos; break;
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
	private static final    byte[] Bry_bool_rue = Bry_.new_a7("rue"), Bry_bool_alse = Bry_.new_a7("alse"), Bry_null_ull = Bry_.new_a7("ull");
}
