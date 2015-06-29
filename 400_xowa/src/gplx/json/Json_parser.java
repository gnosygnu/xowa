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
package gplx.json; import gplx.*;
public class Json_parser {
	public Json_factory Factory() {return factory;} private Json_factory factory = new Json_factory();
	private byte[] src; private int src_len, pos; private Number_parser num_parser = new Number_parser();
	private static final byte[] Bry_bool_rue = Bry_.new_a7("rue"), Bry_bool_alse = Bry_.new_a7("alse"), Bry_null_ull = Bry_.new_a7("ull");
	public Json_doc Parse(byte[] src) {
		Json_doc doc = new Json_doc();
		this.src = src; this.src_len = src.length; pos = 0;
		Skip_ws();
		if (src.length == 0) return null;
		if (src[pos] != Byte_ascii.Curly_bgn) return null;
		Skip_ws();
//			if (src[pos + 1] != Byte_ascii.Quote) return null;
//				throw Err_.new_("doc must start with {");
		Json_itm_nde root = Make_nde(doc);
		doc.Ctor(src, root);
		return doc;
	}
	Json_itm_nde Make_nde(Json_doc doc) {
		++pos;	// brack_bgn
		Json_itm_nde nde = new Json_itm_nde(pos);
		while (pos < src_len) {
			Skip_ws();
			if (src[pos] == Byte_ascii.Curly_end) 	{++pos; return nde;}
			else									nde.Subs_add(Make_kv(doc));
			Skip_ws();
			switch (src[pos++]) {
				case Byte_ascii.Comma:			break;
				case Byte_ascii.Curly_end:		return nde;
				default: throw Err_.unhandled(src[pos - 1]);
			}
		}
		throw Err_.new_("eos inside nde");
	}
	Json_itm Make_kv(Json_doc doc) {
		Json_itm key = Make_string(doc);
		Skip_ws();
		Chk(Byte_ascii.Colon);
		Skip_ws();
		Json_itm val = Make_val(doc);
		return new Json_itm_kv(key, val);
	}
	Json_itm Make_val(Json_doc doc) {
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
			throw Err_.unhandled(Char_.XtoStr(b));
		}
		throw Err_.new_("eos reached in val");
	}
	Json_itm Make_literal(byte[] remainder, int remainder_len, Json_itm singleton) {
		++pos;	// 1st char
		int literal_end = pos + remainder_len;
		if (Bry_.Eq(remainder, src, pos, literal_end)) {
			pos = literal_end;
			return singleton;
		}
		throw Err_.new_("invalid literal");
	}
	Json_itm Make_string(Json_doc doc) {
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
		throw Err_.new_("eos reached inside quote");
	}
	Json_itm Make_num(Json_doc doc) {
		int num_bgn = pos;
		boolean loop = true;
		while (loop) {
			if (pos == src_len) throw Err_.new_("eos reached inside num");
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
		return num_parser.Has_frac() 
			? factory.Decimal(doc, num_bgn, pos)
			: factory.Int(doc, num_bgn, pos);
	}
	Json_itm_ary Make_ary(Json_doc doc) {
		Json_itm_ary rv = factory.Ary(pos++, pos);	// brack_bgn
		while (pos < src_len) {
			Skip_ws();
			if (src[pos] == Byte_ascii.Brack_end) 	{++pos; return rv;}
			else									rv.Subs_add(Make_val(doc));
			Skip_ws();
			switch (src[pos]) {
				case Byte_ascii.Comma:			++pos; break;
				case Byte_ascii.Brack_end:		++pos; return rv;
			}
		}
		throw Err_.new_("eos inside ary");
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
			throw err_(src, pos, "expected '{0}' but got '{1}'", Char_.XtoStr(expd), Char_.XtoStr(src[pos]));
	}
	Err err_(byte[] src, int bgn, String fmt, Object... args) {return err_(src, bgn, src.length, fmt, args);}
	Err err_(byte[] src, int bgn, int src_len, String fmt, Object... args) {
		String msg = String_.Format(fmt, args) + " " + Int_.Xto_str(bgn) + " " + String_.new_u8_by_len(src, bgn, 20);
		return Err_.new_(msg);
	}
}
