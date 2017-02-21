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
package gplx;
import gplx.core.primitives.*; import gplx.core.strings.*; import gplx.core.brys.*; import gplx.core.interfaces.*;
public class GfoMsg_ {
	public static GfoMsg as_(Object obj) {return obj instanceof GfoMsg ? (GfoMsg)obj : null;}
	public static final    GfoMsg Null = new GfoMsg_base().ctor_("<<NULL MSG>>", false);
	public static GfoMsg new_parse_(String key)	{return new GfoMsg_base().ctor_(key, true);}
	public static GfoMsg new_cast_(String key)	{return new GfoMsg_base().ctor_(key, false);}
	public static GfoMsg srl_(GfoMsg owner, String key)	{
		GfoMsg rv = new_parse_(key);
		owner.Subs_add(rv);
		return rv;
	}
	public static GfoMsg root_(String... ary) {return root_leafArgs_(ary);}
	public static GfoMsg root_leafArgs_(String[] ary, Keyval... kvAry) {
		int len = Array_.Len(ary); if (len == 0) throw Err_.new_invalid_arg("== 0", "@len", len);
		GfoMsg root = new GfoMsg_base().ctor_(ary[0], false);
		GfoMsg owner = root;
		for (int i = 1; i < len; i++) {
			String key = ary[i];
			GfoMsg cur = new GfoMsg_base().ctor_(key, false);
			owner.Subs_add(cur);
			owner = cur;
		}
		for (int i = 0; i < kvAry.length; i++) {
			Keyval kv = kvAry[i];
			owner.Add(kv.Key(), kv.Val());
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

		GfoMsg root = GfoMsg_.new_parse_((String)list.Get_at(0));
		GfoMsg cur = root;
		for (int i = 1; i < list.Count(); i++) {
			String k = (String)list.Get_at(i);
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
}
class GfoMsg_wtr extends GfoMsg_base {
	@Override protected Object ReadOr(String k, Object defaultOr) {
		if (args == null) args = List_adp_.New();
		args.Add(Keyval_.new_(k, null));
		return defaultOr;
	}
}
class GfoMsg_rdr extends GfoMsg_base {
	@Override protected Object ReadOr(String k, Object defaultOr) {
		if (args == null) args = List_adp_.New();
		args.Add(Keyval_.new_(k, defaultOr));
		return defaultOr;
	}
}
class GfoMsg_base implements GfoMsg {
	public String Key() {return key;} private String key; 
	public int Subs_count() {return subs == null ? 0 : subs.Count();}
	public GfoMsg Subs_getAt(int i) {return subs == null ? null : (GfoMsg)subs.Get_at(i);}
	public GfoMsg Subs_add(GfoMsg m) {if (subs == null) subs = List_adp_.New(); subs.Add(m); return this;}
	public GfoMsg Subs_(GfoMsg... ary) {for (GfoMsg m : ary) Subs_add(m); return this;}
	public int Args_count() {return args == null ? 0 : args.Count();}
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
	public Keyval Args_getAt(int i) {return args == null ? null : (Keyval)args.Get_at(i);}
	public GfoMsg Args_ovr(String k, Object v) {
		if (args == null) args = List_adp_.New();
		for (int i = 0; i < args.Count(); i++) {
			Keyval kv = (Keyval)args.Get_at(i);
			if (String_.Eq(k, kv.Key())) {
				kv.Val_(v);
				return this;
			}
		}
		args.Add(Keyval_.new_(k, v));
		return this;
	}
	public GfoMsg Parse_(boolean v) {parse = v; return this;}
	public GfoMsg	Add(String k, Object v) {
		if (args == null) args = List_adp_.New();
		args.Add(Keyval_.new_(k, v));
		return this;
	}
	public boolean		ReadBool(String k)						{Object rv = ReadOr(k,false); if (rv == Nil) ThrowNotFound(k); return parse ? Yn.parse_or((String)rv, false) : Bool_.Cast(rv);}
	public int		ReadInt(String k)						{Object rv = ReadOr(k, 0)	; if (rv == Nil) ThrowNotFound(k); return parse ? Int_.parse((String)rv) : Int_.cast(rv);}
	public byte		ReadByte(String k)						{Object rv = ReadOr(k, 0)	; if (rv == Nil) ThrowNotFound(k); return parse ? Byte_.parse((String)rv) : Byte_.cast(rv);}
	public long		ReadLong(String k)						{Object rv = ReadOr(k, 0)	; if (rv == Nil) ThrowNotFound(k); return parse ? Long_.parse((String)rv) : Long_.cast(rv);}
	public float	ReadFloat(String k)						{Object rv = ReadOr(k, 0)	; if (rv == Nil) ThrowNotFound(k); return parse ? Float_.parse((String)rv) : Float_.cast(rv);}
	public double	ReadDouble(String k)					{Object rv = ReadOr(k, 0)	; if (rv == Nil) ThrowNotFound(k); return parse ? Double_.parse((String)rv) : Double_.cast(rv);}
	public Decimal_adp ReadDecimal(String k)					{Object rv = ReadOr(k, 0)	; if (rv == Nil) ThrowNotFound(k); return parse ? Decimal_adp_.parse((String)rv) : Decimal_adp_.cast(rv);}
	public String	ReadStr(String k)						{Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return (String)rv;}
	public DateAdp	ReadDate(String k)						{Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return parse ? DateAdp_.parse_gplx((String)rv) : DateAdp_.cast(rv);}
	public Io_url	ReadIoUrl(String k)						{Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return parse ? Io_url_.new_any_((String)rv) : Io_url_.cast(rv);}
	public Object	CastObj(String k)						{Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return rv;}
	public boolean		ReadBoolOr(String k, boolean or)			{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? Yn.parse_or((String)rv, or) : Bool_.Cast(rv);}
	public int		ReadIntOr(String k, int or)				{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? Int_.parse((String)rv) : Int_.cast(rv);}
	public long		ReadLongOr(String k, long or)			{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? Long_.parse((String)rv) : Long_.cast(rv);}
	public float	ReadFloatOr(String k, float or)			{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? Float_.parse((String)rv) : Float_.cast(rv);}
	public double	ReadDoubleOr(String k,double or)		{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? Double_.parse((String)rv) : Double_.cast(rv);}
	public Decimal_adp ReadDecimalOr(String k,Decimal_adp or)	{Object rv = ReadOr(k, or); if (rv == Nil) return or	; return parse ? Decimal_adp_.parse((String)rv) : Decimal_adp_.cast(rv);}
	public String	ReadStrOr(String k, String or)			{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return (String)rv;}
	public DateAdp	ReadDateOr(String k, DateAdp or)		{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? DateAdp_.parse_gplx((String)rv) : DateAdp_.cast(rv);}
	public Io_url	ReadIoUrlOr(String k, Io_url or)		{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? Io_url_.new_any_((String)rv) : Io_url_.cast(rv);}
	public boolean		ReadBoolOrFalse(String k)				{Object rv = ReadOr(k,false); if (rv == Nil) return false	; return parse ? Yn.parse_or((String)rv, false) : Bool_.Cast(rv);}
	public boolean		ReadBoolOrTrue(String k)				{Object rv = ReadOr(k, true); if (rv == Nil) return true	; return parse ? Yn.parse_or((String)rv, true) : Bool_.Cast(rv);}
	public boolean		ReadYnOrY(String k)						{Object rv = ReadOr(k, true); if (rv == Nil) return true	; return parse ? Yn.parse_or((String)rv, true) : Bool_.Cast(rv);}
	public boolean		ReadYn(String k)						{Object rv = ReadOr(k,false); if (rv == Nil) ThrowNotFound(k); return parse ? Yn.parse_or((String)rv, false) : Yn.coerce_(rv);}
	public boolean		ReadYn_toggle(String k, boolean cur) {
		Object rv = ReadOr(k, "!");
		if (rv == Nil) ThrowNotFound(k);
		if (!parse) throw Err_.new_wo_type("only parse supported");
		String rv_str = (String)rv;
		return (String_.Eq(rv_str, "!")) ? !cur : Yn.parse(rv_str);
	}
	public byte[]	ReadBry(String k)						{Object rv = ReadOr(k,false); if (rv == Nil) ThrowNotFound(k); return parse ? Bry_.new_u8((String)rv) : (byte[])rv;}
	public byte[]	ReadBryOr(String k, byte[] or)			{Object rv = ReadOr(k, or); if (rv == Nil) return or; return parse ? Bry_.new_u8((String)rv) : (byte[])rv;}
	public Object	CastObjOr(String k, Object or)	{Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return rv;}
	public Object	ReadObj(String k)									{Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return rv;}
	public Object	ReadObj(String k, ParseAble parseAble)				{Object rv = ReadOr(k, null); if (rv == Nil) ThrowNotFound(k); return parse ? parseAble.ParseAsObj((String)rv) : rv;}
	public Object	ReadObjOr(String k, ParseAble parseAble, Object or) {Object rv = ReadOr(k, or)	; if (rv == Nil) return or		; return parse ? parseAble.ParseAsObj((String)rv) : rv;}
	public String[]	ReadStrAry(String k, String spr)		{return String_.Split(ReadStr(k), spr);}
	public byte[][] ReadBryAry(String k, byte spr)			{return Bry_split_.Split(ReadBry(k), spr);}
	public String[] ReadStrAryIgnore(String k, String spr, String ignore) {return String_.Split(String_.Replace(ReadStr(k), ignore, ""), spr);}
	public Object   ReadValAt(int i) {return Args_getAt(i).Val();}
	@gplx.Virtual protected Object ReadOr(String k, Object defaultOr) {
		if (args == null) return Nil; // WORKAROUND.gfui: args null for DataBndr_whenEvt_execCmd
		if (!String_.Eq(k, "")) {
			for (int i = 0; i < args.Count(); i++) {
				Keyval kv = (Keyval)args.Get_at(i);
				if (String_.Eq(k, kv.Key())) return kv.Val();
			}
		}
		if (counter >= args.Count()) return Nil;
		for (int i = 0; i < args.Count(); i++) {
			Keyval kv = (Keyval)args.Get_at(i);
			if (String_.Eq(kv.Key(), "") && i >= counter) {
				counter++;
				return kv.Val();
			}
		}
		return Nil;
	}	int counter = 0;
	void ThrowNotFound(String k) {throw Err_.new_wo_type("arg not found in msg", "k", k, "counter", counter, "args", args);}
	String ArgsXtoStr() {
		if (this.Args_count() == 0) return "<<EMPTY>>";
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < this.Args_count(); i++) {
			Keyval rv = (Keyval)this.Args_getAt(i);
			sb.Add_fmt("{0};", rv.Key());
		}
		return sb.To_str();
	}
	public GfoMsg CloneNew() {
		GfoMsg_base rv = new GfoMsg_base().ctor_(key, parse);
		if (args != null) {
			rv.args = List_adp_.New();
			for (int i = 0; i < args.Count(); i++)
				rv.args.Add(args.Get_at(i));
		}
		if (subs != null) {
			rv.subs = List_adp_.New();
			for (int i = 0; i < args.Count(); i++) {
				GfoMsg sub = (GfoMsg)args.Get_at(i);
				rv.subs.Add(sub.CloneNew());	// NOTE: recursion
			}
		}
		return rv;
	}

	protected List_adp args;
	List_adp subs;
	public String To_str() {
		String_bldr sb = String_bldr_.new_();
		To_str(sb, new XtoStrWkr_gplx(), this);
		return sb.To_str_and_clear();
	}
	void To_str(String_bldr sb, XtoStrWkr wkr, GfoMsg m) {
		sb.Add(m.Key());
		if (m.Subs_count() == 0) {
			sb.Add(":");
			boolean first = true;
			for (int i = 0; i < m.Args_count(); i++) {
				Keyval kv = m.Args_getAt(i);
				if (kv.Val() == null) continue;
				if (!first) sb.Add(" ");
				sb.Add(kv.Key());
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
	@gplx.Internal protected GfoMsg_base(){}
	static final    String_obj_val Nil = String_obj_val.new_("<<NOT FOUND>>");
}
interface XtoStrWkr {
	String To_str(Object o);
}
class XtoStrWkr_gplx implements XtoStrWkr {
	public String To_str(Object o) {
		if (o == null) return "<<NULL>>";
		Class<?> type = Type_adp_.ClassOf_obj(o);
		String rv = null;
		if		(type == String.class)	rv = String_.cast(o);
		else if (Int_.TypeMatch(type))		return Int_.To_str(Int_.cast(o));
		else if (Type_adp_.Eq(type, Bool_.Cls_ref_type))		return Yn.To_str(Bool_.Cast(o));
		else if (type == DateAdp.class)	return DateAdp_.cast(o).XtoStr_gplx();
		else								rv = Object_.Xto_str_strict_or_empty(o);
		return String_.Replace(rv, "'", "''");
	}
}
