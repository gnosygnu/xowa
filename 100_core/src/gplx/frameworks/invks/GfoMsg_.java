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
package gplx.frameworks.invks;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.Yn;
import gplx.core.interfaces.ParseAble;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BrySplit;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.StringVal;
public class GfoMsg_ {
	public static GfoMsg as_(Object obj) {return obj instanceof GfoMsg ? (GfoMsg)obj : null;}
	public static final GfoMsg Null = new GfoMsg_base().ctor_("<<NULL MSG>>", false);
	public static GfoMsg new_parse_(String key)    {return new GfoMsg_base().ctor_(key, true);}
	public static GfoMsg new_cast_(String key)    {return new GfoMsg_base().ctor_(key, false);}
	public static GfoMsg srl_(GfoMsg owner, String key)    {
		GfoMsg rv = new_parse_(key);
		owner.Subs_add(rv);
		return rv;
	}
	public static GfoMsg root_(String... ary) {return root_leafArgs_(ary);}
	public static GfoMsg root_leafArgs_(String[] ary, KeyVal... kvAry) {
		int len = ArrayUtl.Len(ary); if (len == 0) throw ErrUtl.NewInvalidArg("@len", len);
		GfoMsg root = new GfoMsg_base().ctor_(ary[0], false);
		GfoMsg owner = root;
		for (int i = 1; i < len; i++) {
			String key = ary[i];
			GfoMsg cur = new GfoMsg_base().ctor_(key, false);
			owner.Subs_add(cur);
			owner = cur;
		}
		for (int i = 0; i < kvAry.length; i++) {
			KeyVal kv = kvAry[i];
			owner.Add(kv.KeyToStr(), kv.Val());
		}
		return root;
	}
	public static GfoMsg chain_(GfoMsg owner, String key) {
		GfoMsg sub = owner;
		List_adp list = List_adp_.New();
		list.Add(sub.Key());
		while (sub != null) {
			if (sub.Subs_count() == 0) break;
			sub = (GfoMsg)sub.Subs_getAt(0);
			list.Add(sub.Key());
		}
		list.Add(key);

		GfoMsg root = GfoMsg_.new_parse_((String)list.GetAt(0));
		GfoMsg cur = root;
		for (int i = 1; i < list.Len(); i++) {
			String k = (String)list.GetAt(i);
			GfoMsg mm = GfoMsg_.new_parse_(k);
			cur.Subs_add(mm);
			cur = mm;
		}
		return root;
	}
	public static GfoMsg wtr_() {return new GfoMsg_wtr().ctor_("", false);}
	public static GfoMsg rdr_(String cmd) {return new GfoMsg_rdr().ctor_(cmd, false);}
	public static GfoMsg basic_(String cmd, Object... vals) {
		GfoMsg rv = new_cast_(cmd);
		int len = vals.length;
		for (int i = 0; i < len; i++)
			rv.Add("", vals[i]);
		return rv;
	}
	public static Hash_adp Read_str_ary_as_hash(GfoMsg m, String k) {
		String[] ary = m.ReadStrAry(k, "|");
		int ary_len = ary.length;
		if (ary_len == 0) return Hash_adp_.Noop;
		Hash_adp rv = Hash_adp_.New();
		for (int i = 0; i < ary_len; i++) {
				rv.AddIfDupeUse1st(ary[i], ary[i]);
		}
		return rv;
	}
}
class GfoMsg_wtr extends GfoMsg_base {
	@Override protected Object ReadOr(String k, Object defaultOr) {
		if (args == null) args = List_adp_.New();
		args.Add(KeyVal.NewStr(k, null));
		return defaultOr;
	}
}
class GfoMsg_rdr extends GfoMsg_base {
	@Override protected Object ReadOr(String k, Object defaultOr) {
		if (args == null) args = List_adp_.New();
		args.Add(KeyVal.NewStr(k, defaultOr));
		return defaultOr;
	}
}
class GfoMsg_base implements GfoMsg {
	public String Key() {return key;} private String key; 
	public int Subs_count() {return subs == null ? 0 : subs.Len();}
	public GfoMsg Subs_getAt(int i) {return subs == null ? null : (GfoMsg)subs.GetAt(i);}
	public GfoMsg Subs_add(GfoMsg m) {if (subs == null) subs = List_adp_.New(); subs.Add(m); return this;}
	public GfoMsg Subs_(GfoMsg... ary) {for (GfoMsg m : ary) Subs_add(m); return this;}
	public int Args_count() {return args == null ? 0 : args.Len();}
	public void Args_reset() {
		counter = 0;
		Args_reset(this);
	}
	public GfoMsg Clear() {
		this.Args_reset();
		if (subs != null) subs.Clear();
		if (args != null) args.Clear();
		return this;
	}
	static void Args_reset(GfoMsg owner) {
		int len = owner.Subs_count();
		for (int i = 0; i < len; i++) {
			GfoMsg sub = owner.Subs_getAt(i);
			sub.Args_reset();
		}
	}
	public KeyVal Args_getAt(int i) {return args == null ? null : (KeyVal)args.GetAt(i);}
	public GfoMsg Args_ovr(String k, Object v) {
		if (args == null) args = List_adp_.New();
		for (int i = 0; i < args.Len(); i++) {
			KeyVal kv = (KeyVal)args.GetAt(i);
			if (StringUtl.Eq(k, kv.KeyToStr())) {
				kv.ValSet(v);
				return this;
			}
		}
		args.Add(KeyVal.NewStr(k, v));
		return this;
	}
	public GfoMsg Parse_(boolean v) {parse = v; return this;}
	public GfoMsg    Add(String k, Object v) {
		if (args == null) args = List_adp_.New();
		args.Add(KeyVal.NewStr(k, v));
		return this;
	}
	public boolean        ReadBool(String k)                        {Object rv = ReadOr(k,false); if (rv == Nil) ThrowNotFound(k); return parse ? Yn.parse_or((String)rv, false) : BoolUtl.Cast(rv);}
	public int        ReadInt(String k)                        {Object rv = ReadOr(k, 0)    ; if (rv == Nil) ThrowNotFound(k); return parse ? IntUtl.Parse((String)rv) : IntUtl.Cast(rv);}
	public byte        ReadByte(String k)                        {Object rv = ReadOr(k, 0)    ; if (rv == Nil) ThrowNotFound(k); return parse ? ByteUtl.Parse((String)rv) : ByteUtl.Cast(rv);}
	public long        ReadLong(String k)                        {Object rv = ReadOr(k, 0)    ; if (rv == Nil) ThrowNotFound(k); return parse ? LongUtl.Parse((String)rv) : LongUtl.Cast(rv);}
	public float    ReadFloat(String k)                        {Object rv = ReadOr(k, 0)    ; if (rv == Nil) ThrowNotFound(k); return parse ? FloatUtl.Parse((String)rv) : FloatUtl.Cast(rv);}
	public double    ReadDouble(String k)                    {Object rv = ReadOr(k, 0)    ; if (rv == Nil) ThrowNotFound(k); return parse ? DoubleUtl.Parse((String)rv) : DoubleUtl.Cast(rv);}
	public GfoDecimal ReadDecimal(String k)                    {Object rv = ReadOr(k, 0)    ; if (rv == Nil) ThrowNotFound(k); return parse ? GfoDecimalUtl.Parse((String)rv) : (GfoDecimal)rv;}
	public String    ReadStr(String k)                        {Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return (String)rv;}
	public GfoDate ReadDate(String k)                        {Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return parse ? GfoDateUtl.ParseGplx((String)rv) : (GfoDate)rv;}
	public Io_url ReadIoUrl(String k)                        {Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return parse ? Io_url_.new_any_((String)rv) : Io_url_.cast(rv);}
	public Object    CastObj(String k)                        {Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return rv;}
	public boolean        ReadBoolOr(String k, boolean or)            {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? Yn.parse_or((String)rv, or) : BoolUtl.Cast(rv);}
	public int        ReadIntOr(String k, int or)                {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? IntUtl.Parse((String)rv) : IntUtl.Cast(rv);}
	public long        ReadLongOr(String k, long or)            {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? LongUtl.Parse((String)rv) : LongUtl.Cast(rv);}
	public float    ReadFloatOr(String k, float or)            {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? FloatUtl.Parse((String)rv) : FloatUtl.Cast(rv);}
	public double    ReadDoubleOr(String k,double or)        {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? DoubleUtl.Parse((String)rv) : DoubleUtl.Cast(rv);}
	public GfoDecimal ReadDecimalOr(String k, GfoDecimal or)    {Object rv = ReadOr(k, or); if (rv == Nil) return or    ; return parse ? GfoDecimalUtl.Parse((String)rv) : (GfoDecimal)rv;}
	public String    ReadStrOr(String k, String or)            {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return (String)rv;}
	public GfoDate ReadDateOr(String k, GfoDate or)        {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? GfoDateUtl.ParseGplx((String)rv) : (GfoDate)rv;}
	public Io_url    ReadIoUrlOr(String k, Io_url or)        {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? Io_url_.new_any_((String)rv) : Io_url_.cast(rv);}
	public boolean        ReadBoolOrFalse(String k)                {Object rv = ReadOr(k,false); if (rv == Nil) return false    ; return parse ? Yn.parse_or((String)rv, false) : BoolUtl.Cast(rv);}
	public boolean        ReadBoolOrTrue(String k)                {Object rv = ReadOr(k, true); if (rv == Nil) return true    ; return parse ? Yn.parse_or((String)rv, true) : BoolUtl.Cast(rv);}
	public boolean        ReadYnOrY(String k)                        {Object rv = ReadOr(k, true); if (rv == Nil) return true    ; return parse ? Yn.parse_or((String)rv, true) : BoolUtl.Cast(rv);}
	public boolean        ReadYn(String k)                        {Object rv = ReadOr(k,false); if (rv == Nil) ThrowNotFound(k); return parse ? Yn.parse_or((String)rv, false) : Yn.coerce_(rv);}
	public boolean        ReadYn_toggle(String k, boolean cur) {
		Object rv = ReadOr(k, "!");
		if (rv == Nil) ThrowNotFound(k);
		if (!parse) throw ErrUtl.NewArgs("only parse supported");
		String rv_str = (String)rv;
		return (StringUtl.Eq(rv_str, "!")) ? !cur : Yn.parse(rv_str);
	}
	public byte[]    ReadBry(String k)                        {Object rv = ReadOr(k,false); if (rv == Nil) ThrowNotFound(k); return parse ? BryUtl.NewU8((String)rv) : (byte[])rv;}
	public byte[]    ReadBryOr(String k, byte[] or)            {Object rv = ReadOr(k, or); if (rv == Nil) return or; return parse ? BryUtl.NewU8((String)rv) : (byte[])rv;}
	public Object    CastObjOr(String k, Object or)    {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return rv;}
	public Object    ReadObj(String k)                                    {Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return rv;}
	public Object    ReadObj(String k, ParseAble parseAble)                {Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return parse ? parseAble.ParseAsObj((String)rv) : rv;}
	public Object    ReadObjOr(String k, ParseAble parseAble, Object or) {Object rv = ReadOr(k, or)    ; if (rv == Nil) return or        ; return parse ? parseAble.ParseAsObj((String)rv) : rv;}
	public String[]    ReadStrAry(String k, String spr)        {return StringUtl.Split(ReadStr(k), spr);}
	public byte[][] ReadBryAry(String k, byte spr)            {return BrySplit.Split(ReadBry(k), spr);}
	public String[] ReadStrAryIgnore(String k, String spr, String ignore) {return StringUtl.Split(StringUtl.Replace(ReadStr(k), ignore, ""), spr);}
	public Object   ReadValAt(int i) {return Args_getAt(i).Val();}
	protected Object ReadOr(String k, Object defaultOr) {
		if (args == null) return Nil; // WORKAROUND.gfui: args null for DataBndr_whenEvt_execCmd
		if (!StringUtl.Eq(k, "")) {
			for (int i = 0; i < args.Len(); i++) {
				KeyVal kv = (KeyVal)args.GetAt(i);
				if (StringUtl.Eq(k, kv.KeyToStr())) return kv.Val();
			}
		}
		if (counter >= args.Len()) return Nil;
		for (int i = 0; i < args.Len(); i++) {
			KeyVal kv = (KeyVal)args.GetAt(i);
			if (StringUtl.Eq(kv.KeyToStr(), "") && i >= counter) {
				counter++;
				return kv.Val();
			}
		}
		return Nil;
	}   int counter = 0;
	void ThrowNotFound(String k) {throw ErrUtl.NewArgs("arg not found in msg", "k", k, "counter", counter, "args", args);}
	String ArgsXtoStr() {
		if (this.Args_count() == 0) return "<<EMPTY>>";
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < this.Args_count(); i++) {
			KeyVal rv = (KeyVal)this.Args_getAt(i);
			sb.AddFmt("{0};", rv.KeyToStr());
		}
		return sb.ToStr();
	}
	public GfoMsg CloneNew() {
		GfoMsg_base rv = new GfoMsg_base().ctor_(key, parse);
		if (args != null) {
			rv.args = List_adp_.New();
			for (int i = 0; i < args.Len(); i++)
				rv.args.Add(args.GetAt(i));
		}
		if (subs != null) {
			rv.subs = List_adp_.New();
			for (int i = 0; i < args.Len(); i++) {
				GfoMsg sub = (GfoMsg)args.GetAt(i);
				rv.subs.Add(sub.CloneNew());    // NOTE: recursion
			}
		}
		return rv;
	}

	protected List_adp args;
	List_adp subs;
	public String To_str() {
		String_bldr sb = String_bldr_.new_();
		To_str(sb, new XtoStrWkr_gplx(), this);
		return sb.ToStrAndClear();
	}
	void To_str(String_bldr sb, XtoStrWkr wkr, GfoMsg m) {
		sb.Add(m.Key());
		if (m.Subs_count() == 0) {
			sb.Add(":");
			boolean first = true;
			for (int i = 0; i < m.Args_count(); i++) {
				KeyVal kv = m.Args_getAt(i);
				if (kv.Val() == null) continue;
				if (!first) sb.Add(" ");
				sb.Add(kv.KeyToStr());
				sb.Add("='");
				sb.Add(wkr.To_str(kv.Val()));
				sb.Add("'");
				first = false;
			}
			sb.Add(";");
		}
		else {
			sb.Add(".");
			To_str(sb, wkr, m.Subs_getAt(0));
		}
	}

	public GfoMsg_base ctor_(String key, boolean parse)  {this.key = key; this.parse = parse; return this;} private boolean parse;
	public GfoMsg_base(){}
	static final StringVal Nil = StringVal.New("<<NOT FOUND>>");
}
interface XtoStrWkr {
	String To_str(Object o);
}
class XtoStrWkr_gplx implements XtoStrWkr {
	public String To_str(Object o) {
		if (o == null) return "<<NULL>>";
		Class<?> type = ClassUtl.TypeByObj(o);
		String rv = null;
		if        (ClassUtl.Eq(type, StringUtl.ClsRefType))      rv = StringUtl.Cast(o);
		else if (ClassUtl.Eq(type, IntUtl.ClsRefType))         return IntUtl.ToStr(IntUtl.Cast(o));
		else if (ClassUtl.Eq(type, BoolUtl.ClsRefType))        return Yn.To_str(BoolUtl.Cast(o));
		else if (ClassUtl.Eq(type, GfoDateUtl.ClsRefType))     return ((GfoDate)o).ToStrGplx();
		else                                                rv = ObjectUtl.ToStrOrEmpty(o);
		return StringUtl.Replace(rv, "'", "''");
	}
}
