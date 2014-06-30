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
			ary[i] = (GfmlTkn)list.FetchAt(i);
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
	public GfmlTkn MakeNew(String rawNew, String valNew) {throw Err_.not_implemented_msg_(".MakeNew cannot be invoked on GfmlTkn_composite (raw is available, but not val)").Add("tknType", tknType).Add("rawNew", rawNew).Add("valNew", valNew);}
	@gplx.Internal protected GfmlTkn_composite(String tknType, GfmlTkn[] ary) {this.tknType = tknType; this.ary = ary;}
}
