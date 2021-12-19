/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.stores;
import gplx.types.errs.Err;
import gplx.core.type_xtns.*;
import gplx.libs.dlgs.UsrDlg_;
import gplx.libs.dlgs.UsrMsg;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.KeyVal;
import gplx.types.commons.String_bldr;
public abstract class DataRdr_base implements SrlMgr {
	public boolean Parse() {return parse;} public void Parse_set(boolean v) {parse = v;} private boolean parse;
	public Io_url Uri() {return uri;} public void Uri_set(Io_url s) {uri = s;} Io_url uri = Io_url_.Empty;
	public abstract String NameOfNode();
	public boolean Type_rdr() {return true;}
	public Hash_adp EnvVars() {return envVars;} Hash_adp envVars = Hash_adp_.New();
	public abstract Object Read(String key);
	public abstract int FieldCount();
	public abstract String KeyAt(int i);
	public abstract Object ReadAt(int i);
	public KeyVal KeyValAt(int idx)    {return KeyVal.NewStr(this.KeyAt(idx), ReadAt(idx));}
	public String ReadStr(String key) {
		Object val = Read(key);
		try {return (String)val;} 
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(String.class, key, val, exc);}
	}
	public String ReadStrOr(String key, String or) {
		Object val = Read(key); if (val == null) return or;
		try {return (String)val;} 
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, String.class, key, val, or); return or;}
	}
	public byte[] ReadBryByStr(String key) {return BryUtl.NewU8(ReadStr(key));}
	public byte[] ReadBryByStrOr(String key, byte[] or) {
		Object val = Read(key); if (val == null) return or;
		try {return BryUtl.NewU8((String)val);}
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, byte[].class, key, val, or); return or;}
	}
	public void SrlList(String key, List_adp list, SrlObj proto, String itmKey) {
		list.Clear();
		DataRdr subRdr = this.Subs_byName_moveFirst(key); // collection node
		subRdr = subRdr.Subs();
		while (subRdr.MoveNextPeer()) {
			SrlObj itm = proto.SrlObj_New(null);
			itm.SrlObj_Srl(subRdr);
			list.Add(itm);
		}
	}
	public Object StoreRoot(SrlObj root, String key) {
		SrlObj clone = root.SrlObj_New(null);
		clone.SrlObj_Srl(this);
		return clone;
	}
	public abstract DataRdr Subs_byName_moveFirst(String name);

	public int ReadInt(String key) {
		Object val = Read(key);
		try {return (parse) ? IntUtl.Parse(StringUtl.CastOrNull(val)) : IntUtl.Cast(val);}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(int.class, key, val, exc);}
	}
	public int ReadIntOr(String key, int or) {
		Object val = Read(key); if (val == null) return or;
		try {return (parse) ? IntUtl.Parse(StringUtl.CastOrNull(val)) : IntUtl.Cast(val);}
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, int.class, key, val, or); return or;}
	}
	public long ReadLongOr(String key, long or) {
		Object val = Read(key); if (val == null) return or;
		try {return (parse) ? LongUtl.Parse(StringUtl.CastOrNull(val)) : LongUtl.Cast(val);}
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, long.class, key, val, or); return or;}
	}
	public boolean ReadBool(String key) {
		Object val = Read(key);
		try {return (parse) ? BoolUtl.Cast(BoolClassXtn.Instance.ParseOrNull(StringUtl.CastOrNull(val))) : BoolUtl.Cast(val);}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(boolean.class, key, val, exc);}
	}
	public boolean ReadBoolOr(String key, boolean or) {
		Object val = Read(key); if (val == null) return or;
		try {return (parse) ? BoolUtl.Parse(StringUtl.CastOrNull(val)) : BoolUtl.Cast(val);}
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, boolean.class, key, val, or); return or;}
	}
	public long ReadLong(String key) {
		Object val = Read(key);
		try {return (parse) ? LongUtl.Parse(StringUtl.CastOrNull(val)) : LongUtl.Cast(val);}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(long.class, key, val, exc);}
	}
	public float ReadFloat(String key) {
		Object val = Read(key);
		try {return (parse) ? FloatUtl.Parse(StringUtl.CastOrNull(val)) : FloatUtl.Cast(val);}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(float.class, key, val, exc);}
	}
	public float ReadFloatOr(String key, float or) {
		Object val = Read(key); if (val == null) return or;
		try {return (parse) ? FloatUtl.Parse(StringUtl.CastOrNull(val)) : FloatUtl.Cast(val);}
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, float.class, key, val, or); return or;}
	}
	public double ReadDouble(String key) {
		Object val = Read(key);
		try {return (parse) ? DoubleUtl.Parse(StringUtl.CastOrNull(val)) : DoubleUtl.Cast(val);}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(double.class, key, val, exc);}
	}
	public double ReadDoubleOr(String key, double or) {
		Object val = Read(key); if (val == null) return or;
		try {return (parse) ? DoubleUtl.Parse(StringUtl.CastOrNull(val)) : DoubleUtl.Cast(val);}
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, double.class, key, val, or); return or;}
	}
	public byte ReadByte(String key) {
		Object val = Read(key);
		try {return (parse) ? ByteUtl.Parse(StringUtl.CastOrNull(val)) : ByteUtl.Cast(val);}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(byte.class, key, val, exc);}
	}
	public byte ReadByteOr(String key, byte or) {
		Object val = Read(key); if (val == null) return or;
		try {return (parse) ? ByteUtl.Parse(StringUtl.CastOrNull(val)) : ByteUtl.Cast(val);}
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, byte.class, key, val, or); return or;}
	}
	public GfoDate ReadDate(String key) {
		Object val = Read(key);
		try {return (parse) ? GfoDateUtl.ParseGplx(StringUtl.CastOrNull(val)) : (GfoDate)val;}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(GfoDate.class, key, val, exc);}
	}
	public GfoDate ReadDateOr(String key, GfoDate or) {
		Object val = Read(key); if (val == null) return or;
		try {return (parse) ? GfoDateUtl.ParseGplx(StringUtl.CastOrNull(val)) : (GfoDate)val;}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(GfoDate.class, key, val, exc);}
	}
	public GfoDecimal ReadDecimal(String key) {
		Object val = Read(key);
		try {
			if (parse) return GfoDecimalUtl.Parse(StringUtl.CastOrNull(val));
			GfoDecimal rv = GfoDecimalUtl.CastOrNull(val);
			return (rv == null) 
				? GfoDecimalUtl.NewDb(val)    // HACK: GfoNde_.rdr_ will call ReadAt(int i) on Db_data_rdr; since no Db_data_rdr knows about Decimal_adp, it will always return decimalType
				: rv;
		}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(GfoDecimal.class, key, val, exc);}
	}
	public GfoDecimal ReadDecimalOr(String key, GfoDecimal or) {
		Object val = Read(key); if (val == null) return or;
		try {
			if (parse) return GfoDecimalUtl.Parse(StringUtl.CastOrNull(val));
			GfoDecimal rv = GfoDecimalUtl.CastOrNull(val);
			return (rv == null) 
				? GfoDecimalUtl.NewDb(val)    // HACK: GfoNde_.rdr_ will call ReadAt(int i) on Db_data_rdr; since no Db_data_rdr knows about Decimal_adp, it will always return decimalType
				: rv;
		}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(GfoDecimal.class, key, val, exc);}
	}
	public char ReadChar(String key) {
		Object val = Read(key);
		try {
			if (parse) return CharUtl.Parse(StringUtl.CastOrNull(val));
			return CharUtl.Cast(val);
		}
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(char.class, key, val, exc);}
	}
	public char ReadCharOr(String key, char or) {
		Object val = Read(key); if (val == null) return or;
		try {
			if (parse) return CharUtl.Parse(StringUtl.CastOrNull(val));
			return CharUtl.Cast(val);
		}
		catch (Exception exc) {return or;}
	}
	public byte[] ReadBry(String key) {
		Object val = Read(key);
		try {return (byte[])val;} 
		catch (Exception exc) {throw Err_dataRdr_ReadFailed_err(byte[].class, key, val, exc);}
	}
	public byte[] ReadBryOr(String key, byte[] or) {
		Object val = Read(key); if (val == null) return or;
		try {return (byte[])val;} 
		catch (Exception exc) {Err_dataRdr_ReadFailed_useOr(exc, byte[].class, key, val, or); return or;}
	}
	public gplx.core.ios.streams.Io_stream_rdr ReadRdr(String key) {return gplx.core.ios.streams.Io_stream_rdr_.Noop;}
	public boolean SrlBoolOr(String key, boolean or) {return ReadBoolOr(key, or);}
	public byte SrlByteOr(String key, byte or) {return ReadByteOr(key, or);}
	public int SrlIntOr(String key, int or) {return ReadIntOr(key, or);}
	public long SrlLongOr(String key, long or) {return ReadLongOr(key, or);}
	public String SrlStrOr(String key, String or) {return ReadStrOr(key, or);}
	public GfoDate SrlDateOr(String key, GfoDate or) {return ReadDateOr(key, or);}
	public GfoDecimal SrlDecimalOr(String key, GfoDecimal or) {return ReadDecimalOr(key, or);}
	public double SrlDoubleOr(String key, double or) {return ReadDoubleOr(key, or);}
	public Object SrlObjOr(String key, Object or) {throw ErrUtl.NewUnimplemented();}
	public void XtoStr_gfml(String_bldr sb) {
		sb.Add(this.NameOfNode()).Add(":");
		for (int i = 0; i < this.FieldCount(); i++) {
			KeyVal kv = this.KeyValAt(i);
			if (i != 0) sb.Add(" ");
			sb.AddFmt("{0}='{1}'", kv.KeyToStr(), StringUtl.Replace(kv.ValToStrOrEmpty(), "'", "\""));
		}
		sb.Add(";");
	}
	public abstract DataRdr Subs();
	public void TypeKey_(String v) {}
	public abstract SrlMgr SrlMgr_new(Object o);
	static Err Err_dataRdr_ReadFailed_err(Class<?> type, String key, Object val, Exception inner) {
		String innerMsg = inner == null ? "" : ErrUtl.Message(inner);
		return ErrUtl.NewArgs("failed to read data", "key", key, "val", val, "type", type, "innerMsg", innerMsg);
	}
	static void Err_dataRdr_ReadFailed_useOr(Class<?> type, String key, Object val, Object or) {
		UsrDlg_.Instance.Warn(UsrMsg.new_("failed to read data; substituting default").Add("key", key).Add("val", val).Add("default", or).Add("type", type));
	}
	static void Err_dataRdr_ReadFailed_useOr(Exception exc, Class<?> type, String key, Object val, Object or) {
		UsrDlg_.Instance.Warn(UsrMsg.new_("failed to read data; substituting default").Add("key", key).Add("val", val).Add("default", or).Add("type", type));
	}
}
