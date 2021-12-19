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
package gplx.langs.xmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Gfo_xml_wtr {
	private final BryWtr bfr = BryWtr.NewAndReset(255), txt_bfr = BryWtr.NewAndReset(32);
	private byte quote_byte = AsciiByte.Apos;
	private byte[] quote_escape = Bry_quote_1_escape;
	private List_adp nde_stack = List_adp_.New();
	private Gfo_xml_nde nde_cur = null;
	private int indent = 0;
	public void Quote_(boolean apos) {
		if (apos) {
			this.quote_byte = AsciiByte.Apos;
			this.quote_escape = Bry_quote_1_escape;
		}
		else {
			this.quote_byte = AsciiByte.Quote;
			this.quote_escape = Bry_quote_2_escape;
		}
	}
	public Gfo_xml_wtr Nde_lhs_bgn_grp(String v) {return Nde_lhs_bgn(BoolUtl.Y, v);}
	public Gfo_xml_wtr Nde_lhs_bgn_itm(String v) {return Nde_lhs_bgn(BoolUtl.N, v);}
	private Gfo_xml_wtr Nde_lhs_bgn(boolean grp, String v) {
		nde_cur = new Gfo_xml_nde(grp, v);
		nde_stack.Add(nde_cur);
		bfr.AddByteRepeat(AsciiByte.Space, indent);
		bfr.AddByte(AsciiByte.AngleBgn).AddStrU8(v);
		indent += 2;
		return this;
	}
	public Gfo_xml_wtr Nde_lhs_end() {
		bfr.AddByte(AsciiByte.AngleEnd);
		if (nde_cur.Grp()) bfr.AddByteNl();
		return this;
	}
	public Gfo_xml_wtr Nde_lhs(String v) {return Nde_lhs(BoolUtl.Y, v);}
	private Gfo_xml_wtr Nde_lhs(boolean grp, String v) {
		this.Nde_lhs_bgn(grp, v);
		this.Nde_lhs_end();
		return this;
	}
	public Gfo_xml_wtr Nde_rhs() {
		Gfo_xml_nde nde = (Gfo_xml_nde)List_adp_.Pop(nde_stack);
		indent -= 2;
		if (nde.Grp()) bfr.AddByteRepeat(AsciiByte.Space, indent);
		bfr.Add(Bry_nde_rhs_bgn).AddStrU8(nde.Name()).AddByte(AsciiByte.AngleEnd);	// EX: </node>
		bfr.AddByteNl();
		return this;
	}
	public Gfo_xml_wtr Nde_txt_str(String name, String text) {
		this.Nde_lhs(BoolUtl.N, name);
		this.Txt_str_u8(text);
		this.Nde_rhs();
		return this;
	}
	public Gfo_xml_wtr Nde_txt_bry(String name, byte[] text) {
		this.Nde_lhs(BoolUtl.N, name);
		this.Txt_bry(text);
		this.Nde_rhs();
		return this;
	}
	public Gfo_xml_wtr Nde_txt_int(String name, int text) {
		this.Nde_lhs(BoolUtl.N, name);
		this.Txt_bry(IntUtl.ToBry(text));
		this.Nde_rhs();
		return this;
	}
	public Gfo_xml_wtr Atr_bgn(String key) {
		bfr.AddByteSpace().AddStrU8(key).AddByte(AsciiByte.Eq).AddByte(quote_byte);
		return this;
	}
	public Gfo_xml_wtr Atr_val_str_a7(String v)		{bfr.AddStrA7(v); return this;}
	public Gfo_xml_wtr Atr_val_str_u8(String v)		{bfr.AddStrU8(v); return this;}
	public Gfo_xml_wtr Atr_val_bry		(byte[] v)	{bfr.Add(v); return this;}
	public Gfo_xml_wtr Atr_val_int		(int v)		{bfr.AddIntVariable(v); return this;}
	public Gfo_xml_wtr Atr_end() {
		bfr.AddByte(quote_byte);
		return this;
	}
	public Gfo_xml_wtr Atr_kv_int(String key, int val)			{return Atr_kv_bry(key, IntUtl.ToBry(val));}
	public Gfo_xml_wtr Atr_kv_str_a7(String key, String val)	{return Atr_kv_bry(key, BryUtl.NewA7(val));}
	public Gfo_xml_wtr Atr_kv_str_u8(String key, String val)	{return Atr_kv_bry(key, BryUtl.NewU8(val));}
	public Gfo_xml_wtr Atr_kv_bry(String key, byte[] val) {
		bfr.AddByteSpace().AddStrU8(key);
		bfr.AddByte(AsciiByte.Eq);
		Atr_val_quote(val);
		return this;
	}
	private Gfo_xml_wtr Atr_val_quote(byte[] val_bry) {
		bfr.AddByte(quote_byte);
		bfr.AddBryEscape(quote_byte, quote_escape, val_bry, 0, val_bry.length);
		bfr.AddByte(quote_byte);
		return this;
	}
	public Gfo_xml_wtr Txt_bry(byte[] txt) {
		int len = txt.length;
		boolean dirty = false;
		for (int i = 0; i < len; ++i) {
			byte[] escape = null;
			byte b = txt[i];
			switch (b) {
				case AsciiByte.Lt:		escape = Bry_escape_lt; break;
				case AsciiByte.Gt:		escape = Bry_escape_gt; break;
				case AsciiByte.Amp:	escape = Bry_escape_amp; break;
				default: break;
			}
			if (escape != null && !dirty) {
				bfr.AddMid(txt, 0, i);
				dirty = true;
			}
			if (dirty) {
				if (escape == null) bfr.AddByte(b);
				else				bfr.Add(escape);
			}
		}
		if (dirty)	bfr.AddBfrAndClear(txt_bfr);
		else		bfr.Add(txt);
		return this;
	}
	public Gfo_xml_wtr Txt_str_u8(String txt)	{return Txt_bry(BryUtl.NewU8(txt));}
	public String Bld_str() {return bfr.ToStrAndClear();}
	private static final byte[]
	  Bry_nde_rhs_bgn		= BryUtl.NewA7("</")
//		, Bry_nde_inline		= Bry_.new_a7("/>")
	, Bry_quote_1_escape	= BryUtl.NewA7("&apos;")
	, Bry_quote_2_escape	= BryUtl.NewA7("&quot;")
	, Bry_escape_lt			= BryUtl.NewA7("&lt;")
	, Bry_escape_gt			= BryUtl.NewA7("&gt;")
	, Bry_escape_amp		= BryUtl.NewA7("&amp;")
	;
}
class Gfo_xml_nde {
	public Gfo_xml_nde(boolean grp, String name) {this.grp = grp; this.name = name;}
	public boolean Grp() {return grp;} private final boolean grp;
	public String Name() {return name;} private final String name;
}
