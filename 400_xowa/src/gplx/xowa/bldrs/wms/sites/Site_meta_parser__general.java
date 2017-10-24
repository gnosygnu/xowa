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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.core.primitives.*; import gplx.langs.jsons.*;
class Site_meta_parser__general extends Json_parser__list_nde__base {
	private final    Site_meta_parser__general__kv parser__image = new Site_meta_parser__general__kv("imagelimits", "width", "height");
	private final    Site_meta_parser__general__lone parser__fallback = new Site_meta_parser__general__lone("fallback", "code");
	private final    Site_meta_parser__general__kv parser__variants = new Site_meta_parser__general__kv("variants", "code", "name");
	private String cur_context;
	public void Parse(String context, Ordered_hash list, Json_nde nde) {
		this.cur_context = context + ".general";
		this.Parse_to_list_as_kv(cur_context, nde, list);
	}
	@Override protected byte[] Parse_to_list_as_kv__get_val(Json_kv sub, byte[] key) {
		Object o = complex_props.Get_by_bry(key);
		if (o == null) return sub.Val_as_bry();
		switch (((Int_obj_val)o).Val()) {
			case Tid__thumblimits:	return Bry_.Add_w_dlm(Byte_ascii.Pipe, sub.Val_as_ary().Xto_bry_ary());	// [120, 150, 180] -> "120|150|180"
			case Tid__fallback:		return parser__fallback.Parse(cur_context, tmp_bfr, sub.Val_as_ary());	// [{'code':'zh'},{'code':'zh-hans'}] -> "zh|zh-hans"
			case Tid__variants:		return parser__variants.Parse(cur_context, tmp_bfr, sub.Val_as_ary());	// [{'code':'zh','name':'a'},{'code':'zh-hans','name':'b'}] -> "zh=a|zh-hans=b"
			case Tid__imagelimits:	return parser__image.Parse(cur_context, tmp_bfr, sub.Val_as_ary());		// [{'width':320,'height':240},{'width':640,'height':480}] -> '320=240|640=480'
			default: throw Err_.new_unhandled(o);
		}
	}
	private static final int Tid__fallback = 1, Tid__variants = 2, Tid__thumblimits = 3, Tid__imagelimits = 4, Tid__imagelimits__width = 5, Tid__imagelimits__height = 6;
	private static final    Hash_adp_bry complex_props = Hash_adp_bry.cs()
	.Add_str_int("fallback"		,  Tid__fallback)
	.Add_str_int("variants"		,  Tid__variants)
	.Add_str_int("thumblimits"	,  Tid__thumblimits)
	.Add_str_int("imagelimits"	,  Tid__imagelimits)
	.Add_str_int("width"		,  Tid__imagelimits__width)
	.Add_str_int("height"		,  Tid__imagelimits__height)
	;
}
class Site_meta_parser__general__lone extends Json_parser__list_nde__base {
	private Bry_bfr bfr; private String context_name;
	public Site_meta_parser__general__lone(String context_name, String key) {
		this.context_name = context_name;
		this.Ctor(key);
	}
	public byte[] Parse(String context, Bry_bfr bfr, Json_ary ary) {
		if (ary.Len() == 0) return Bry_.Empty;	// no fallbacks
		this.bfr = bfr;
		this.Parse_grp(context + "." + context_name, ary);
		bfr.Del_by_1();	// delete trailing dlm at end of fallbacks
		return bfr.To_bry_and_clear();
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		bfr.Add(Kv__bry(atrs, 0)).Add_byte_pipe();
	}
}
class Site_meta_parser__general__kv extends Json_parser__list_nde__base {
	private Bry_bfr bfr; private String context_name;
	public Site_meta_parser__general__kv(String context_name, String key, String val) {
		this.context_name = context_name;
		this.Ctor(key, val);
	}
	public byte[] Parse(String context, Bry_bfr bfr, Json_ary ary) {
		this.bfr = bfr;
		this.Parse_grp(context + "." + context_name, ary);
		bfr.Del_by_1();
		return bfr.To_bry_and_clear();
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		bfr.Add(Kv__bry(atrs, 0)).Add_byte_eq().Add(Kv__bry(atrs, 1)).Add_byte_pipe();
	}
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
		this.Ctor("pages", "articles", "edits", "images", "users", "activeusers", "admins", "jobs", "dispatch", "queued-massmessages");
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
		, Kv__long_or_0(atrs, 9)
		);
	}
}
class Site_meta_parser__interwikimap extends Json_parser__list_nde__base {
	private Ordered_hash list;
	public Site_meta_parser__interwikimap() {
		this.Ctor("prefix", "local", "extralanglink", "linktext", "sitename", "language", "localinterwiki", "url", "protorel");
	}
	public void Parse(String context, Ordered_hash list, Json_ary nde) {
		this.list = list;
		this.Parse_grp(context + ".interwikimap", nde);
	}
	@Override protected void Parse_hook_nde(Json_nde sub, Json_kv[] atrs) {
		byte[] key = Kv__bry(atrs, 0);
		list.Add(key
		, new Site_interwikimap_itm(key
		, Kv__mw_bool(atrs, 1), Kv__mw_bool(atrs, 2), Kv__bry_or_empty(atrs, 3), Kv__bry_or_empty(atrs, 4)
		, Kv__bry_or_empty(atrs, 5), Kv__mw_bool(atrs, 6), Kv__bry(atrs, 7), Kv__mw_bool(atrs, 8)));
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
				if (!Bry_.Eq(atr.Key_as_bry(), Key__scribunto)) {Warn("unknown subscriber key", atr); continue;}
				scribunto = atr.Val_as_bry();
			}
		}
		list.Add(key, new Site_showhook_itm(key, scribunto, subscribers_bry_ary));
	}
	private final    static byte[] Key__scribunto = Bry_.new_a7("scribunto");
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
