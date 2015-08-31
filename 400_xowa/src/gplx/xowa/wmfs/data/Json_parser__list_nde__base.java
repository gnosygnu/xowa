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
package gplx.xowa.wmfs.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
import gplx.core.primitives.*; import gplx.core.json.*;
abstract class Json_parser__base {
	protected String context;
	protected final Hash_adp_bry hash = Hash_adp_bry.cs();
	protected final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	protected String[] keys;
	protected Json_kv[] atrs;
	protected Json_itm cur_itm;
	protected int keys_len;
	public void Ctor(String... keys) {
		this.keys = keys;
		this.keys_len = keys.length;
		for (int i = 0; i < keys_len; ++i)
			hash.Add(Bry_.new_u8(keys[i]), Int_obj_val.new_(i));
		this.atrs = new Json_kv[keys_len];
	}
	public int Kv__int(Json_kv[] ary, int i)			{return Bry_.To_int(ary[i].Val_as_bry());}
	public long Kv__long(Json_kv[] ary, int i)			{return Bry_.To_long_or(ary[i].Val_as_bry(), 0);}
	public long Kv__long_or_0(Json_kv[] ary, int i)		{
		Json_kv kv = ary[i]; if (kv == null) return 0;
		return Bry_.To_long_or(kv.Val_as_bry(), 0);
	}
	public byte[] Kv__bry(Json_kv[] ary, int i)	{
		byte[] rv = Kv__bry_or_null(ary, i); if (rv == null) throw Err_.new_("json.parser", "missing val", "key", context + "." + keys[i], "excerpt", Json_itm_.To_bry(tmp_bfr, cur_itm));
		return rv;
	}
	public byte[][] Kv__bry_ary(Json_kv[] ary, int i) {
		return ary[i].Val_as_ary().Xto_bry_ary();
	}
	public byte[] Kv__bry_or_empty(Json_kv[] ary, int i) {
		byte[] rv = Kv__bry_or_null(ary, i);
		return rv == null ? Bry_.Empty : rv;
	}
	public byte[] Kv__bry_or_null(Json_kv[] ary, int i)	{
		Json_kv kv = ary[i]; if (kv == null) return null;
		Json_itm val = kv.Val();			
		return  kv == null ? null : val.Data_bry();
	}
	public boolean Kv__mw_bool(Json_kv[] ary, int i)	{
		Json_kv kv = ary[i]; if (kv == null) return false;
		Json_itm val = kv.Val();
		if (	val.Tid() == Json_itm_.Tid__str
			&&	Bry_.Len_eq_0(val.Data_bry())) {
			return true;
		}
		else {
			Warn("unknown val: val=" + String_.new_u8(kv.Data_bry()) + " excerpt=" + String_.new_u8(Json_itm_.To_bry(tmp_bfr, cur_itm)), kv);
			return false;
		}
	}
	public boolean Kv__has(Json_kv[] ary, int i)			{return Kv__bry_or_empty(ary, i) != null;}
	protected abstract void Parse_hook_nde(Json_nde sub, Json_kv[] atrs);
	protected void Warn(String msg, Json_kv kv) {
		Gfo_usr_dlg_.I.Warn_many("", "", msg + ": path=~{0}.~{1} excerpt=~{2}", context, kv.Key_as_bry(), Json_itm_.To_bry(tmp_bfr, cur_itm));
	}
}
class Json_parser__list_nde__base extends Json_parser__base {
	public void Parse_grp(String context, Json_grp grp) {
		this.context = context;
		int len = grp.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde sub = null;
			if (grp.Tid() == Json_itm_.Tid__nde) {
				Json_kv kv = Json_nde.cast(grp).Get_at_as_kv(i);
				sub = kv.Val_as_nde();
			}
			else {
				sub = Json_nde.cast(grp.Get_at(i));
			}
			Parse_nde(context, sub);
		}
	}
	public void Parse_nde(String context, Json_nde nde) {
		this.cur_itm = nde;
		for (int j = 0; j < keys_len; ++j)
			atrs[j] = null;
		int atr_len = nde.Len();
		for (int j = 0; j < atr_len; ++j) {
			Json_kv atr = nde.Get_at_as_kv(j);
			Object idx_obj = hash.Get_by_bry(atr.Key_as_bry());
			if (idx_obj == null) {Warn("unknown key", atr); continue;}
			int idx_int = ((Int_obj_val)idx_obj).Val();
			atrs[idx_int] = atr;
		}
		Parse_hook_nde(nde, atrs);
	}
	public void Parse_to_list_as_bry(String context, Json_ary ary, Ordered_hash list) {
		this.cur_itm = ary;
		int len = ary.Len();
		for (int i = 0; i < len; ++i) {
			byte[] val = ary.Get_at(i).Data_bry();
			list.Add(val, val);
		}
	}
	public void Parse_to_list_as_kv(String context, Json_nde nde, Ordered_hash list) {
		this.cur_itm = nde;
		int len = nde.Len();
		for (int i = 0; i < len; ++i) {
			Json_kv sub = nde.Get_at_as_kv(i);
			byte[] key = sub.Key_as_bry();
			byte[] val = Parse_to_list_as_kv__get_val(sub, key);
			list.Add(key, KeyVal_.new_(String_.new_u8(key), String_.new_u8(val)));
		}
	}
	@gplx.Virtual protected byte[] Parse_to_list_as_kv__get_val(Json_kv sub, byte[] key) {return sub.Val_as_bry();}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {}
}
class Site_meta_parser__general extends Json_parser__list_nde__base {
	public void Parse(String context, Ordered_hash list, Json_nde nde) {
		this.Parse_to_list_as_kv(context + ".general", nde, list);
	}
	@Override protected byte[] Parse_to_list_as_kv__get_val(Json_kv sub, byte[] key) {
		Object o = complex_props.Get_by_bry(key);
		if (o == null) return sub.Val_as_bry();
		switch (((Int_obj_val)o).Val()) {
			case Tid__thumblimits: return Bry_.Add_w_dlm(Byte_ascii.Pipe, sub.Val_as_ary().Xto_bry_ary());	// [120, 150, 180] -> "120|150|180"
			case Tid__imagelimits: return Parse_thumblimits(sub); // [ {'width':320,'height':240},  {'width':640,'height':480}] -> '320=240|640=480'
			default: throw Err_.new_unhandled(o);
		}
	}
	private byte[] Parse_thumblimits(Json_kv sub) {
		Json_ary ary = sub.Val_as_ary();
		int ary_len = ary.Len();
		for (int i = 0; i < ary_len; ++i) {
			Json_nde nde = ary.Get_at_as_nde(i);
			int atr_len = nde.Len();
			for (int j = 0; j < atr_len; ++j) {
				Json_kv atr = nde.Get_at_as_kv(j);
				Object idx_obj = complex_props.Get_by_bry(atr.Key_as_bry());
				if (idx_obj == null) {Warn("unknown key", atr); continue;}
				int atr_tid = ((Int_obj_val)idx_obj).Val();
				switch (atr_tid) {
					case Tid__imagelimits__width:	if (i != 0) tmp_bfr.Add_byte_pipe(); break;
					case Tid__imagelimits__height:	tmp_bfr.Add_byte_eq(); break;
					default: throw Err_.new_unhandled(atr_tid);
				}
				tmp_bfr.Add(atr.Val_as_bry());
			}
		}
		return tmp_bfr.Xto_bry_and_clear();
	}
	private static final int Tid__thumblimits = 1, Tid__imagelimits = 2, Tid__imagelimits__width = 3, Tid__imagelimits__height = 4;
	private static final Hash_adp_bry complex_props = Hash_adp_bry.cs()
	.Add_str_int("thumblimits"	,  Tid__thumblimits)
	.Add_str_int("imagelimits"	,  Tid__imagelimits)
	.Add_str_int("width"		,  Tid__imagelimits__width)
	.Add_str_int("height"		,  Tid__imagelimits__height)
	;
}
class Site_meta_parser__namespace extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__namespace() {
		this.Ctor("id", "canonical", "ca"+"se", "*", "subpages", "content", "defaultcontentmodel");
	}
	public void Parse(String context, Ordered_hash list, Json_nde nde) {
		this.list = list;
		this.Parse_grp(context + ".namespace", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		int id = Kv__int(atrs, 0);
		list.Add(id, new Site_namespace_itm(id, Kv__bry(atrs, 2), Kv__bry_or_empty(atrs, 1), Kv__bry(atrs, 3), Kv__mw_bool(atrs, 4), Kv__mw_bool(atrs, 5), Kv__bry_or_empty(atrs, 6)));
	}
}
class Site_meta_parser__statistic extends Json_parser__list_nde__base {
	private Site_statistic_itm itm;
	public Site_meta_parser__statistic() {
		this.Ctor("pages", "articles", "edits", "images", "users", "activeusers", "admins", "jobs", "queued-massmessages");
	}
	public void Parse(String context, Site_statistic_itm itm, Json_nde nde) {
		this.itm = itm;
		this.Parse_nde(context + ".statistic", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		itm.Ctor
		( Kv__long(atrs, 0)
		, Kv__long(atrs, 1)
		, Kv__long(atrs, 2)
		, Kv__long(atrs, 3)
		, Kv__long(atrs, 4)
		, Kv__long(atrs, 5)
		, Kv__long(atrs, 6)
		, Kv__long(atrs, 7)
		, Kv__long_or_0(atrs, 8)
		);
	}
}
class Site_meta_parser__interwikimap extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__interwikimap() {
		this.Ctor("prefix", "local", "language", "localinterwiki", "url", "protorel");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".interwikimap", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		list.Add(key, new Site_interwikimap_itm(key, Kv__mw_bool(atrs, 1), Kv__bry_or_empty(atrs, 2), Kv__mw_bool(atrs, 3), Kv__bry(atrs, 4), Kv__mw_bool(atrs, 5)));
	}
}
class Site_meta_parser__namespacealias extends Json_parser__list_nde__base {
	private List_adp list;
	public Site_meta_parser__namespacealias() {
		this.Ctor("id", "*");
	}
	public void Parse(String context, List_adp list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".namespacealias", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		Site_namespacealias_itm itm = new Site_namespacealias_itm(Kv__int(atrs, 0), Kv__bry(atrs, 1));
		list.Add(itm);
	}
}
class Site_meta_parser__specialpagealias extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__specialpagealias() {
		this.Ctor("realname", "aliases");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".specialpagealias", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		list.Add(key, new Site_specialpagealias_itm(key, Kv__bry_ary(atrs, 1)));
	}
}
class Site_meta_parser__library extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__library() {
		this.Ctor("name", "version");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".library", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		list.Add(key, new Site_library_itm(key, Kv__bry(atrs, 1)));
	}
}
class Site_meta_parser__extension extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__extension() {
		this.Ctor("type", "name", "namemsg", "description", "descriptionmsg", "author", "url", "version", "vcs-system", "vcs-version", "vcs-url", "vcs-date", "license-name", "license", "credits");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".extension", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		Site_extension_itm itm = new Site_extension_itm(Kv__bry(atrs, 0), Kv__bry(atrs, 1), Kv__bry_or_empty(atrs, 2), Kv__bry_or_empty(atrs, 3), Kv__bry_or_empty(atrs, 4)
		, Kv__bry_or_empty(atrs, 5), Kv__bry_or_empty(atrs, 6)
		, Kv__bry_or_empty(atrs, 7), Kv__bry_or_empty(atrs, 8), Kv__bry_or_empty(atrs, 9), Kv__bry_or_empty(atrs, 10), Kv__bry_or_empty(atrs, 11)
		, Kv__bry_or_empty(atrs, 12), Kv__bry_or_empty(atrs, 13), Kv__bry_or_empty(atrs, 14)
		);
		list.Add(itm.Key(), itm);
	}
}
class Site_meta_parser__skin extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__skin() {
		this.Ctor("code", "de"+"fault", "*", "unusable");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".skin", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		list.Add(key, new Site_skin_itm(key, Kv__mw_bool(atrs, 1), Kv__bry(atrs, 2), Kv__mw_bool(atrs, 3)));
	}
}
class Site_meta_parser__magicword extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__magicword() {
		this.Ctor("name", "case-sensitive", "aliases");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".magicword", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		list.Add(key, new Site_magicword_itm(key, Kv__mw_bool(atrs, 1), Kv__bry_ary(atrs, 2)));
	}
}
class Site_meta_parser__showhook extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__showhook() {
		this.Ctor("name", "subscribers");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".showhook", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		Json_kv subscribers_kv = atrs[1];
		byte[] scribunto = Bry_.Empty;
		byte[][] subscribers_bry_ary = Bry_.Ary_empty;
		if (subscribers_kv.Val().Tid() == Json_itm_.Tid__ary)
			subscribers_bry_ary = Kv__bry_ary(atrs, 1);
		else {
			Json_nde subscribers_nde = subscribers_kv.Val_as_nde();
			int atr_len = subscribers_nde.Len();
			for (int j = 0; j < atr_len; ++j) {
				Json_kv atr = subscribers_nde.Get_at_as_kv(j);
				if (!Bry_.Eq(atr.Key_as_bry(), Key__scribunto)) {Warn("unknown key", atr); continue;}
				scribunto = atr.Val_as_bry();
			}
		}
		list.Add(key, new Site_showhook_itm(key, scribunto, subscribers_bry_ary));
	}
	private final static byte[] Key__scribunto = Bry_.new_a7("scribunto");
}
class Site_meta_parser__language extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__language() {
		this.Ctor("code", "*");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".language", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		list.Add(key, new Site_language_itm(key, Kv__bry(atrs, 1)));
	}
}
