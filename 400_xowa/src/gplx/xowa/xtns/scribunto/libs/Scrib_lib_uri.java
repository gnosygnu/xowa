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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.pfuncs.ttls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_uri implements Scrib_lib {
	public Scrib_lib_uri(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_uri(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.uri.lua"));	// NOTE: defaultUrl handled by Init_lib_url
		notify_page_changed_fnc = mod.Fncs_get_by_key("notify_page_changed");
		return mod;
	}	private Scrib_lua_proc notify_page_changed_fnc;
	public void Notify_page_changed() {if (notify_page_changed_fnc != null) core.Interpreter().CallFunction(notify_page_changed_fnc.Id(), Keyval_.Ary_empty);}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_anchorEncode:							return AnchorEncode(args, rslt);
			case Proc_localUrl:								return Url_func(args, rslt, Pfunc_urlfunc.Tid_local);
			case Proc_fullUrl:								return Url_func(args, rslt, Pfunc_urlfunc.Tid_full);
			case Proc_canonicalUrl:							return Url_func(args, rslt, Pfunc_urlfunc.Tid_canonical);
			case Proc_init_uri_for_page:					return Init_uri_for_page(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_anchorEncode = 0, Proc_localUrl = 1, Proc_fullUrl = 2, Proc_canonicalUrl = 3, Proc_init_uri_for_page = 4;
	public static final String Invk_anchorEncode = "anchorEncode", Invk_localUrl = "localUrl", Invk_fullUrl = "fullUrl", Invk_canonicalUrl = "canonicalUrl", Invk_init_uri_for_page = "init_uri_for_page";
	private static final    String[] Proc_names = String_.Ary(Invk_anchorEncode, Invk_localUrl, Invk_fullUrl, Invk_canonicalUrl, Invk_init_uri_for_page);
	public boolean AnchorEncode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] raw_bry = args.Pull_bry(0);
		Bry_bfr bfr = core.Wiki().Utl__bfr_mkr().Get_b512();
//			Pfunc_anchorencode.Func_init(core.Ctx());
		Pfunc_anchorencode.Anchor_encode(bfr, core.Ctx(), raw_bry);
//			Bry_bfr tmp_bfr = core.Wiki().Utl__bfr_mkr().Get_b512();
//			tmp_bfr.Clear_and_rls();
		return rslt.Init_obj(bfr.To_str_and_rls());
	}
	public boolean Url_func(Scrib_proc_args args, Scrib_proc_rslt rslt, byte url_tid) {
		Xowe_wiki wiki = core.Wiki();
		byte[] ttl_bry = args.Pull_bry(0);
		byte[] qry_bry = args.Extract_qry_args(wiki, 1);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		if (ttl == null) return rslt.Init_null();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		if (ttl.Ns().Id() == Xow_ns_.Tid__media) {	// change "Media:" -> "File:"
			bfr.Add(wiki.Ns_mgr().Ns_file().Name_db_w_colon());
			bfr.Add(ttl.Page_db());
			ttl_bry = bfr.To_bry_and_clear();
		}				
		Pfunc_urlfunc.UrlString(core.Ctx(), url_tid, false, ttl_bry, bfr, qry_bry);
		return rslt.Init_obj(bfr.To_str_and_rls());
	}
	private boolean Init_uri_for_page(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xop_ctx ctx = core.Ctx();
		byte[] ttl_bry = ctx.Page().Ttl().Raw();
		Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
		Pfunc_urlfunc.UrlString(ctx, Pfunc_urlfunc.Tid_full, false, ttl_bry, tmp_bfr, Bry_.Empty);
		return rslt.Init_obj(tmp_bfr.To_bry_and_rls());
	}
}
