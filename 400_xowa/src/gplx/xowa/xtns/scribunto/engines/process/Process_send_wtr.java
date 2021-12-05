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
package gplx.xowa.xtns.scribunto.engines.process;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Double_;
import gplx.Gfo_usr_dlg;
import gplx.Int_;
import gplx.Io_mgr;
import gplx.Keyval;
import gplx.Long_;
import gplx.Object_;
import gplx.Type_;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.Scrib_xtn_mgr;
public class Process_send_wtr {
	public Process_send_wtr(Gfo_usr_dlg usr_dlg) {this.usr_dlg = usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public String Encode(Object o) {
		Bry_bfr tmp_bfr = Bry_bfr_.Reset(Io_mgr.Len_kb);
		Encode_obj(tmp_bfr, o);
		return tmp_bfr.To_str_and_clear();
	}
	public void Encode_bool(Bry_bfr bfr, boolean v)		{bfr.Add(v ? CONST_bool_true : CONST_bool_false);}
	public void Encode_int(Bry_bfr bfr, int v)			{bfr.Add_int_variable(v);}
	public boolean Encode_double(Bry_bfr bfr, double v)	{
		if (Double_.IsNaN(v)) {usr_dlg.Warn_many(GRP_KEY, "fail_encode_double", "cannot convert non-finite number"); return false;}
		bfr.Add_double(v);
		return true;
	}
	public boolean Encode_str(Bry_bfr bfr, String v) {return Encode_str(bfr, Bry_.new_u8(v));}
	public boolean Encode_str(Bry_bfr bfr, byte[] bry) {
		int len = bry.length;
		bfr.Add_byte(AsciiByte.Quote);
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case AsciiByte.Quote: 				bfr.Add_byte(AsciiByte.Backslash).Add_byte(b); break;
				case AsciiByte.Nl:			bfr.Add_byte(AsciiByte.Backslash).Add_byte(AsciiByte.Ltr_n); break;
				case AsciiByte.Cr:		bfr.Add_byte(AsciiByte.Backslash).Add_byte(AsciiByte.Ltr_r); break;
				case AsciiByte.Null:				bfr.Add(CONST_escape_000); break;
				case AsciiByte.Backslash:			bfr.Add_byte(AsciiByte.Backslash).Add_byte(AsciiByte.Backslash); break;
				default: 							bfr.Add_byte(b); break;
			}
		}
		bfr.Add_byte(AsciiByte.Quote);
		return true;
	}
	public boolean Encode_prc(Bry_bfr bfr, Scrib_lua_proc prc) {
		bfr.Add(Prc_bgn);
		bfr.Add_int_variable(prc.Id());
		bfr.Add_byte(AsciiByte.BrackEnd);
		return true;		
	}	private static final byte[] Prc_bgn = Bry_.new_a7("chunks[");
	private boolean Encode_ary(Bry_bfr bfr, Keyval[] ary) {
		int len = ary.length;
		bfr.Add_byte(AsciiByte.CurlyBgn);
		for (int i = 0; i < len; i++) {
			if (i != 0) bfr.Add_byte(AsciiByte.Comma);
			Keyval itm = ary[i];
			Encode_key(bfr, itm.Key_as_obj());
			Encode_obj(bfr, itm.Val());
		}
		bfr.Add_byte(AsciiByte.CurlyEnd);
		return true;
	}
	private boolean Encode_kv(Bry_bfr bfr, Keyval kv) {
		bfr.Add_byte(AsciiByte.CurlyBgn);
		Encode_key(bfr, kv.Key_as_obj());
		Encode_obj(bfr, kv.Val());
		bfr.Add_byte(AsciiByte.CurlyEnd);
		return true;
	}
	public void Encode_key(Bry_bfr bfr, Object key) {
		bfr.Add_byte(AsciiByte.BrackBgn);
		Encode_obj(bfr, key);
		bfr.Add_byte(AsciiByte.BrackEnd);
		bfr.Add_byte(AsciiByte.Eq);
	}
	public boolean Encode_obj(Bry_bfr bfr, Object o) {
		if (o == null) {bfr.Add(CONST_nil); return true;}
		Class<?> c = Type_.Type_by_obj(o);
		if		(Object_.Eq(c, BoolUtl.ClsRefType))			Encode_bool(bfr, BoolUtl.Cast(o));
		else if	(Object_.Eq(c, Int_.Cls_ref_type))			Encode_int(bfr, Int_.Cast(o));
		else if	(Object_.Eq(c, Long_.Cls_ref_type))			bfr.Add_long_variable(Long_.cast(o));
		else if	(Object_.Eq(c, Double_.Cls_ref_type))		{if (!Encode_double(bfr, Double_.cast(o))) return false;}	
		else if	(Object_.Eq(c, String.class))				{if (!Encode_str(bfr, (String)o)) return false;}
		else if	(Object_.Eq(c, byte[].class))				{if (!Encode_str(bfr, (byte[])o)) return false;}	// NOTE: not in Scribunto; added here for PERF of not re-creating a String Object
		else if	(Object_.Eq(c, Scrib_lua_proc.class))		{if (!Encode_prc(bfr, (Scrib_lua_proc)o)) return false;}
		else if	(Object_.Eq(c, Keyval.class))				{if (!Encode_kv(bfr, (Keyval)o)) return false;}
		else if	(Object_.Eq(c, Keyval[].class))			{if (!Encode_ary(bfr, (Keyval[])o)) return false;}
		else												{throw Scrib_xtn_mgr.err_("Object cannot be serialized: ~{0}", Type_.Name_by_obj(o));}
		return true;
	}
	private static final byte[] CONST_nil = Bry_.new_a7("nil"), CONST_bool_true = Bry_.new_a7("true"), CONST_bool_false = Bry_.new_a7("false"), CONST_escape_000 = Bry_.new_a7("\\000");
	private static final String GRP_KEY = "xowa-scribunto-lua-srl";
}
