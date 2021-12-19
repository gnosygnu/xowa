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
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.Scrib_xtn_mgr;
public class Process_send_wtr {
	public Process_send_wtr(Gfo_usr_dlg usr_dlg) {this.usr_dlg = usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public String Encode(Object o) {
		BryWtr tmp_bfr = BryWtr.NewAndReset(IoConsts.LenKB);
		Encode_obj(tmp_bfr, o);
		return tmp_bfr.ToStrAndClear();
	}
	public void Encode_bool(BryWtr bfr, boolean v)		{bfr.Add(v ? CONST_bool_true : CONST_bool_false);}
	public void Encode_int(BryWtr bfr, int v)			{bfr.AddIntVariable(v);}
	public boolean Encode_double(BryWtr bfr, double v)	{
		if (DoubleUtl.IsNaN(v)) {usr_dlg.Warn_many(GRP_KEY, "fail_encode_double", "cannot convert non-finite number"); return false;}
		bfr.AddDouble(v);
		return true;
	}
	public boolean Encode_str(BryWtr bfr, String v) {return Encode_str(bfr, BryUtl.NewU8(v));}
	public boolean Encode_str(BryWtr bfr, byte[] bry) {
		int len = bry.length;
		bfr.AddByte(AsciiByte.Quote);
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case AsciiByte.Quote: 				bfr.AddByte(AsciiByte.Backslash).AddByte(b); break;
				case AsciiByte.Nl:			bfr.AddByte(AsciiByte.Backslash).AddByte(AsciiByte.Ltr_n); break;
				case AsciiByte.Cr:		bfr.AddByte(AsciiByte.Backslash).AddByte(AsciiByte.Ltr_r); break;
				case AsciiByte.Null:				bfr.Add(CONST_escape_000); break;
				case AsciiByte.Backslash:			bfr.AddByte(AsciiByte.Backslash).AddByte(AsciiByte.Backslash); break;
				default: 							bfr.AddByte(b); break;
			}
		}
		bfr.AddByte(AsciiByte.Quote);
		return true;
	}
	public boolean Encode_prc(BryWtr bfr, Scrib_lua_proc prc) {
		bfr.Add(Prc_bgn);
		bfr.AddIntVariable(prc.Id());
		bfr.AddByte(AsciiByte.BrackEnd);
		return true;		
	}	private static final byte[] Prc_bgn = BryUtl.NewA7("chunks[");
	private boolean Encode_ary(BryWtr bfr, KeyVal[] ary) {
		int len = ary.length;
		bfr.AddByte(AsciiByte.CurlyBgn);
		for (int i = 0; i < len; i++) {
			if (i != 0) bfr.AddByte(AsciiByte.Comma);
			KeyVal itm = ary[i];
			Encode_key(bfr, itm.KeyAsObj());
			Encode_obj(bfr, itm.Val());
		}
		bfr.AddByte(AsciiByte.CurlyEnd);
		return true;
	}
	private boolean Encode_kv(BryWtr bfr, KeyVal kv) {
		bfr.AddByte(AsciiByte.CurlyBgn);
		Encode_key(bfr, kv.KeyAsObj());
		Encode_obj(bfr, kv.Val());
		bfr.AddByte(AsciiByte.CurlyEnd);
		return true;
	}
	public void Encode_key(BryWtr bfr, Object key) {
		bfr.AddByte(AsciiByte.BrackBgn);
		Encode_obj(bfr, key);
		bfr.AddByte(AsciiByte.BrackEnd);
		bfr.AddByte(AsciiByte.Eq);
	}
	public boolean Encode_obj(BryWtr bfr, Object o) {
		if (o == null) {bfr.Add(CONST_nil); return true;}
		Class<?> c = ClassUtl.TypeByObj(o);
		if		(ObjectUtl.Eq(c, BoolUtl.ClsRefType))			Encode_bool(bfr, BoolUtl.Cast(o));
		else if	(ObjectUtl.Eq(c, IntUtl.ClsRefType))			Encode_int(bfr, IntUtl.Cast(o));
		else if	(ObjectUtl.Eq(c, LongUtl.ClsRefType))			bfr.AddLongVariable(LongUtl.Cast(o));
		else if	(ObjectUtl.Eq(c, DoubleUtl.ClsRefType))		{if (!Encode_double(bfr, DoubleUtl.Cast(o))) return false;}
		else if	(ObjectUtl.Eq(c, String.class))				{if (!Encode_str(bfr, (String)o)) return false;}
		else if	(ObjectUtl.Eq(c, byte[].class))				{if (!Encode_str(bfr, (byte[])o)) return false;}	// NOTE: not in Scribunto; added here for PERF of not re-creating a String Object
		else if	(ObjectUtl.Eq(c, Scrib_lua_proc.class))		{if (!Encode_prc(bfr, (Scrib_lua_proc)o)) return false;}
		else if	(ObjectUtl.Eq(c, KeyVal.class))				{if (!Encode_kv(bfr, (KeyVal)o)) return false;}
		else if	(ObjectUtl.Eq(c, KeyVal[].class))			{if (!Encode_ary(bfr, (KeyVal[])o)) return false;}
		else												{throw Scrib_xtn_mgr.err_("Object cannot be serialized: ~{0}", ClassUtl.NameByObj(o));}
		return true;
	}
	private static final byte[] CONST_nil = BryUtl.NewA7("nil"), CONST_bool_true = BryUtl.NewA7("true"), CONST_bool_false = BryUtl.NewA7("false"), CONST_escape_000 = BryUtl.NewA7("\\000");
	private static final String GRP_KEY = "xowa-scribunto-lua-srl";
}
