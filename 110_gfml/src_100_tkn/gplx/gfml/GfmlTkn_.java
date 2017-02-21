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
package gplx.gfml; import gplx.*;
public class GfmlTkn_ {
	@gplx.Internal protected static final String NullRaw = "", NullVal = ""; static final String Type_base = "gfml.baseTkn";
	@gplx.Internal protected static final GfmlTkn
		  Null = new GfmlTkn_base().ctor_GfmlTkn_base("gfml.nullTkn", NullRaw, NullVal, GfmlBldrCmd_.Null)
		, EndOfStream = GfmlTkn_.raw_("<<End Of Stream>>")
		, IgnoreOutput = GfmlTkn_.raw_("<<Ignore Output>>");
	public static GfmlTkn as_(Object obj) {return obj instanceof GfmlTkn ? (GfmlTkn)obj : null;}
	@gplx.Internal protected static GfmlTkn new_(String raw, String val)							{return new GfmlTkn_base().ctor_GfmlTkn_base(Type_base, raw, val, GfmlBldrCmd_.Null);}
	public   static GfmlTkn raw_(String raw)										{return new GfmlTkn_base().ctor_GfmlTkn_base(Type_base, raw, raw, GfmlBldrCmd_.Null);}
	@gplx.Internal protected static GfmlTkn val_(String val)										{return new GfmlTkn_base().ctor_GfmlTkn_base(Type_base, NullRaw, val, GfmlBldrCmd_.Null);}
	@gplx.Internal protected static GfmlTkn cmd_(String tknType, GfmlBldrCmd cmd)					{return new GfmlTkn_base().ctor_GfmlTkn_base(tknType, NullRaw, NullVal, cmd);}
	@gplx.Internal protected static GfmlTkn valConst_(String tknType, String val, GfmlBldrCmd cmd) {return new GfmlTkn_valConst().ctor_GfmlTkn_base(tknType, GfmlTkn_.NullRaw, val, cmd);}
	@gplx.Internal protected static GfmlTkn singleton_(String tknType, String raw, String val, GfmlBldrCmd cmd) {return new GfmlTkn_singleton().ctor_GfmlTkn_base(tknType, raw, val, cmd);}
	@gplx.Internal protected static GfmlTkn composite_(String tknType, GfmlTkn[] ary) {return new GfmlTkn_composite(tknType, ary);}
	@gplx.Internal protected static GfmlTkn composite_list_(String tknType, GfmlObjList list) {
		GfmlTkn[] ary = new GfmlTkn[list.Count()];
		for (int i = 0; i < list.Count(); i++)
			ary[i] = (GfmlTkn)list.Get_at(i);
		return GfmlTkn_.composite_(tknType, ary);
	}
}
class GfmlTkn_base implements GfmlTkn {
	public int ObjType() {return GfmlObj_.Type_tkn;}
	public String TknType() {return tknType;} private String tknType;
	public String Raw() {return raw;} private String raw;
	public String Val() {return val;} private String val;
	public GfmlBldrCmd Cmd_of_Tkn() {return cmd;} GfmlBldrCmd cmd;
	public GfmlTkn[] SubTkns() {return GfmlTknAry_.Empty;}
	@gplx.Virtual public GfmlTkn MakeNew(String rawNew, String valNew) {return new GfmlTkn_base().ctor_GfmlTkn_base(tknType, rawNew, valNew, cmd);}
	@gplx.Internal protected GfmlTkn_base ctor_GfmlTkn_base(String tknType, String raw, String val, GfmlBldrCmd cmd) {this.tknType = tknType; this.raw = raw; this.val = val; this.cmd = cmd; return this;}
}
class GfmlTkn_valConst extends GfmlTkn_base {
	@Override public GfmlTkn MakeNew(String rawNew, String valNew) {return new GfmlTkn_base().ctor_GfmlTkn_base(this.TknType(), rawNew, this.Val(), this.Cmd_of_Tkn());}
}
class GfmlTkn_singleton extends GfmlTkn_base {
	@Override public GfmlTkn MakeNew(String rawNew, String valNew) {return this;}
}
class GfmlTkn_composite implements GfmlTkn {
	public int ObjType() {return GfmlObj_.Type_tkn;}
	public String TknType() {return tknType;} private String tknType;
	public String Raw() {return GfmlTknAry_.XtoRaw(ary);}
	public String Val() {return GfmlTknAry_.XtoVal(ary);}
	public GfmlBldrCmd Cmd_of_Tkn() {return GfmlBldrCmd_.Null;}
	public GfmlTkn[] SubTkns() {return ary;} GfmlTkn[] ary;
	public GfmlTkn MakeNew(String rawNew, String valNew) {throw Err_.new_unimplemented_w_msg(".MakeNew cannot be invoked on GfmlTkn_composite (raw is available, but not val)", "tknType", tknType, "rawNew", rawNew, "valNew", valNew);}
	@gplx.Internal protected GfmlTkn_composite(String tknType, GfmlTkn[] ary) {this.tknType = tknType; this.ary = ary;}
}
