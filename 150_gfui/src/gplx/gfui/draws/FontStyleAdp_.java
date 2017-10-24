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
import gplx.core.primitives.*; import gplx.core.interfaces.*;
public class FontStyleAdp_ implements ParseAble {
	public static final    FontStyleAdp 
		  Plain			= new FontStyleAdp(0)
		, Bold			= new FontStyleAdp(1)
		, Italic		= new FontStyleAdp(2)
		, BoldItalic	= new FontStyleAdp(3)
		;
	public static final    FontStyleAdp_ Parser = new FontStyleAdp_();
	public Object ParseAsObj(String raw) {return FontStyleAdp_.parse(raw);}

	public static FontStyleAdp cast(Object obj) {try {return (FontStyleAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, FontStyleAdp.class, obj);}}
	public static FontStyleAdp parseOr_(String raw, FontStyleAdp or) {
		FontStyleAdp rv = parse_internal_(raw); if (rv == null) return or;
		return rv;
	}
	public static FontStyleAdp parts_(Bool_obj_val bold, Bool_obj_val italic) {
		int v = bold == Bool_obj_val.True ? 1 : 0;
		v += italic == Bool_obj_val.True ? 2 : 0;
		return lang_(v);
	}
	public static FontStyleAdp parse(String raw) {
		FontStyleAdp rv = parse_internal_(raw); if (rv == null) throw Err_.new_unhandled(raw);
		return rv;
	}
	public static FontStyleAdp read_(Object o) {String s = String_.as_(o); return s != null ? FontStyleAdp_.parse(s) : FontStyleAdp_.cast(o);}
	static FontStyleAdp parse_internal_(String raw) {
		if		(String_.Eq(raw, "plain"))		return FontStyleAdp_.Plain;
		else if (String_.Eq(raw, "bold"))		return FontStyleAdp_.Bold;
		else if (String_.Eq(raw, "italic"))		return FontStyleAdp_.Italic;
		else if (String_.Eq(raw, "bold+italic"))return FontStyleAdp_.BoldItalic;
		else if (String_.Eq(raw, "italic+bold"))return FontStyleAdp_.BoldItalic;
		else									return null;
	}
	public static FontStyleAdp lang_(int v) {
		if		(v == Plain.Val())		return Plain;
		else if	(v == Bold.Val())		return Bold;
		else if	(v == Italic.Val())		return Italic;
		else if	(v == BoldItalic.Val())	return BoldItalic;
		else							throw Err_.new_unhandled(v);
	}
	public static String XtoStr_(FontStyleAdp fontStyle) {
		int val = fontStyle.Val();
		if		(val == FontStyleAdp_.Plain.Val())		return "plain";
		else if	(val == FontStyleAdp_.Bold.Val())		return "bold";
		else if	(val == FontStyleAdp_.Italic.Val())		return "italic";
		else if	(val == FontStyleAdp_.BoldItalic.Val()) return "bold+italic";
		else											throw Err_.new_unhandled(val);
	}
}
