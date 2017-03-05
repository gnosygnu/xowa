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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
import gplx.core.encoders.*; import gplx.core.interfaces.*;
public class ColorAdp_ implements ParseAble {
	public static ColorAdp as_(Object obj) {return obj instanceof ColorAdp ? (ColorAdp)obj : null;}
	public static ColorAdp cast(Object obj) {try {return (ColorAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, ColorAdp.class, obj);}}
	public static ColorAdp new_(int a, int r, int g, int b) {return ColorAdp.new_((int)a, (int)r, (int)g, (int)b);}
	public static final    ColorAdp_ Parser = new ColorAdp_();
	public Object ParseAsObj(String raw) {return ColorAdp_.parse(raw);}
	public static ColorAdp parseOr_(String raw, ColorAdp or) {
		ColorAdp rv = parse_internal_(raw); if (rv == null) return or;
		return rv;
	}
	public static ColorAdp parse(String raw) {
		ColorAdp rv = parse_internal_(raw); if (rv == null) throw Err_.new_parse_type(ColorAdp.class, raw);
		return rv;
	}
	static ColorAdp parse_internal_(String raw) {
		if (raw == null) return null;
		char firstChar = String_.CharAt(raw, 0);
		if		(firstChar == '#')				return parse_hex_(raw);
		else if (Char_.IsNumber(firstChar))		return parse_int_(raw);
		String rawLower = String_.Lower(raw);
		if		(String_.Eq(rawLower, "black")) return Black;
		else if (String_.Eq(rawLower, "white")) return White;
		else if (String_.Eq(rawLower, "gray")) return Gray;
		else if (String_.Eq(rawLower, "red")) return Red;
		else if (String_.Eq(rawLower, "lime")) return Lime;
		else if (String_.Eq(rawLower, "blue")) return Blue;
		else if (String_.Eq(rawLower, "yellow")) return Yellow;
		else if (String_.Eq(rawLower, "fuchsia")) return Fuchsia;
		else if (String_.Eq(rawLower, "maroon")) return Maroon;
		else if (String_.Eq(rawLower, "green")) return Green;
		else if (String_.Eq(rawLower, "navy")) return Navy;
		else if (String_.Eq(rawLower, "olive")) return Olive;
		else if (String_.Eq(rawLower, "purple")) return Purple;
		else if (String_.Eq(rawLower, "teal")) return Teal;
		else if (String_.Eq(rawLower, "brown")) return Brown;
		else if (String_.Eq(rawLower, "lightgray")) return LightGray;
		else
			return null;
	}
	public static ColorAdp parse_hex_(String raw) {
		try {
			int[] ary = new int[4];						// make ARGB ary
			int idx = 0;
			int rawLen = String_.Len(raw);
			for (int i = 1; i < rawLen; i += 2) {		// fill ARGB ary by parsing raw 2 at a time; EX: #FFFFFFFF -> 255,255,255,255; NOTE: start at 1 to ignore leading #
				String hexStr = String_.MidByLen(raw, i, 2);
				ary[idx++] = Hex_utl_.Parse(hexStr);
			}
			return ColorAdp.new_(ary[0], ary[1], ary[2], ary[3]);
		}	catch (Exception exc) {throw Err_.new_parse_exc(exc, ColorAdp.class, raw);}
	}
	@gplx.Internal protected static ColorAdp parse_int_(String v) {
		String[] ary = String_.Split(v, ",");
		switch (ary.length) {
			case 1: return new_int_(Int_.parse(ary[0]));
			case 3:
			case 4: return parse_int_ary_(ary);
			default: throw Err_.new_wo_type("invalid array", "len", ary.length);
		}
	}
	static ColorAdp parse_int_ary_(String[] ary) {
		int a;
		int idx = 0;
		if (ary.length == 3)	{idx = 0; a = 255;}
		else					{idx = 1; a = Int_.parse(ary[0]);}
		int r = Int_.parse(ary[idx++]);
		int g = Int_.parse(ary[idx++]);
		int b = Int_.parse(ary[idx++]);
		return ColorAdp_.new_(a, r, g, b);
	}
	public static ColorAdp new_int_(int val) {
		int a = ((val >> 24) & 255);
		int r = ((val >> 16) & 255);
		int g = ((val >> 8) & 255);
		int b = ((val) & 255);
		return ColorAdp.new_(a, r, g, b);
	}
	public static ColorAdp read_(Object o) {String s = String_.as_(o); return s != null ? ColorAdp_.parse(s) : ColorAdp_.cast(o);}
	public static final    ColorAdp 
		  Null			= new_(  0,   0,   0,   0)
		, Black			= new_(255,   0,   0,   0)
		, White			= new_(255, 255, 255, 255)
		, Gray			= new_(255, 128, 128, 128)
		, Red			= new_(255, 255,   0,   0)
		, Lime			= new_(255,   0, 255,   0)
		, Blue			= new_(255,   0,   0, 255)
		, Yellow		= new_(255, 255, 255,   0)
		, Fuchsia		= new_(255, 255,   0, 255)
		, Aqua			= new_(255,   0, 255, 255)
		, Maroon		= new_(255, 128,   0,   0)
		, Green			= new_(255,   0, 128,   0)
		, Navy			= new_(255,   0,   0, 128)
		, Olive			= new_(255, 128, 128,   0)
		, Purple		= new_(255, 128,   0, 128)
		, Teal			= new_(255,   0, 128, 128)
		, Brown			= new_(255, 165,  42,  42)
		, LightGray		= new_(255, 211, 211, 211)
		;
}
