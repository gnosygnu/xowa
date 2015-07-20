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
package gplx.xowa.xtns.scribunto.engines.process; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
public class Process_send_wtr {
	public Process_send_wtr(Gfo_usr_dlg usr_dlg) {this.usr_dlg = usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public String Encode(Object o) {
		Bry_bfr tmp_bfr = Bry_bfr.reset_(Io_mgr.Len_kb);
		Encode_obj(tmp_bfr, o);
		return tmp_bfr.Xto_str_and_clear();
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
		bfr.Add_byte(Byte_ascii.Quote);
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Quote: 				bfr.Add_byte(Byte_ascii.Backslash).Add_byte(b); break;
				case Byte_ascii.Nl:			bfr.Add_byte(Byte_ascii.Backslash).Add_byte(Byte_ascii.Ltr_n); break;
				case Byte_ascii.Cr:		bfr.Add_byte(Byte_ascii.Backslash).Add_byte(Byte_ascii.Ltr_r); break;
				case Byte_ascii.Null:				bfr.Add(CONST_escape_000); break;
				case Byte_ascii.Backslash:			bfr.Add_byte(Byte_ascii.Backslash).Add_byte(Byte_ascii.Backslash); break;
				default: 							bfr.Add_byte(b); break;
			}
		}
		bfr.Add_byte(Byte_ascii.Quote);
		return true;
	}
	public boolean Encode_prc(Bry_bfr bfr, Scrib_lua_proc prc) {
		bfr.Add(Prc_bgn);
		bfr.Add_int_variable(prc.Id());
		bfr.Add_byte(Byte_ascii.Brack_end);
		return true;		
	}	static final byte[] Prc_bgn = Bry_.new_a7("chunks[");
	private boolean Encode_ary(Bry_bfr bfr, KeyVal[] ary) {
		int len = ary.length;
		bfr.Add_byte(Byte_ascii.Curly_bgn);
		for (int i = 0; i < len; i++) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Comma);
			KeyVal itm = ary[i];
			Encode_key(bfr, itm.Key_as_obj());
			Encode_obj(bfr, itm.Val());
		}
		bfr.Add_byte(Byte_ascii.Curly_end);
		return true;
	}
	private boolean Encode_kv(Bry_bfr bfr, KeyVal kv) {
		bfr.Add_byte(Byte_ascii.Curly_bgn);
		Encode_key(bfr, kv.Key_as_obj());
		Encode_obj(bfr, kv.Val());
		bfr.Add_byte(Byte_ascii.Curly_end);
		return true;
	}
	public void Encode_key(Bry_bfr bfr, Object key) {
		bfr.Add_byte(Byte_ascii.Brack_bgn);
		Encode_obj(bfr, key);
		bfr.Add_byte(Byte_ascii.Brack_end);
		bfr.Add_byte(Byte_ascii.Eq);		
	}
	public boolean Encode_obj(Bry_bfr bfr, Object o) {
		if (o == null) {bfr.Add(CONST_nil); return true;}
		Class<?> c = ClassAdp_.ClassOf_obj(o);
		if		(Object_.Eq(c, Bool_.Cls_ref_type))				Encode_bool(bfr, Bool_.cast_(o));
		else if	(Object_.Eq(c, Int_.Cls_ref_type))				Encode_int(bfr, Int_.cast_(o));
		else if	(Object_.Eq(c, Double_.Cls_ref_type))			{if (!Encode_double(bfr, Double_.cast_(o))) return false;}	
		else if	(Object_.Eq(c, String.class))				{if (!Encode_str(bfr, (String)o)) return false;}
		else if	(Object_.Eq(c, byte[].class))				{if (!Encode_str(bfr, (byte[])o)) return false;}	// NOTE: not in Scribunto; added here for PERF of not re-creating a String Object
		else if	(Object_.Eq(c, Scrib_lua_proc.class))		{if (!Encode_prc(bfr, (Scrib_lua_proc)o)) return false;}
		else if	(Object_.Eq(c, KeyVal.class))				{if (!Encode_kv(bfr, (KeyVal)o)) return false;}
		else if	(Object_.Eq(c, KeyVal[].class))			{if (!Encode_ary(bfr, (KeyVal[])o)) return false;}
		else												{throw Scrib_xtn_mgr.err_("Object cannot be serialized: {0}", ClassAdp_.NameOf_obj(o));}
		return true;
	}
	private static final byte[] CONST_nil = Bry_.new_a7("nil"), CONST_bool_true = Bry_.new_a7("true"), CONST_bool_false = Bry_.new_a7("false"), CONST_escape_000 = Bry_.new_a7("\\000");
	private static final String GRP_KEY = "xowa-scribunto-lua-srl";
}
