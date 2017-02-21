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
package gplx.xowa.xtns.scribunto.engines.mocks; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Mock_scrib_fxt {		 
	private final    Mock_engine engine = new Mock_engine();
	private final    Mock_server server = new Mock_server();		
	public Scrib_core Core() {return core;} private Scrib_core core;
	public Xop_fxt Parser_fxt() {return parser_fxt;} private Xop_fxt parser_fxt; 
	public void Clear() {Clear("en.wikipedia.org", "en");}
	public void Clear(String domain, String lang) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, domain, app.Lang_mgr().Get_by_or_new(Bry_.new_u8(lang)));
		parser_fxt = new Xop_fxt(app, wiki); // NOTE: always new(); don't try to cache; causes errors in Language_lib
		core = wiki.Parser_mgr().Scrib().Core_init(wiki.Parser_mgr().Ctx());
		core.Engine_(engine); engine.Clear();
		core.Interpreter().Server_(server);
		Xot_invk parent_frame = new Xot_invk_temp(true); parent_frame.Frame_tid_(Scrib_frame_.Tid_null); 
		Xot_invk current_frame = Xot_invk_mock.test_(Bry_.new_a7("Module:Mod_0"));
		core.Invoke_init(core.Wiki(), core.Ctx(), Bry_.Empty, parent_frame, current_frame);
		core.When_page_changed(parser_fxt.Page());
	}
	public void Init__cbk(Mock_proc_fxt... ary) {
		engine.Clear();
		for (Mock_proc_fxt proc : ary)
			engine.RegisterLibraryForTest(proc);
	}
	public void Init__page(String ttl, String txt) {parser_fxt.Init_page_create(ttl, txt);}
	public void Test__proc__ints      (Scrib_lib lib, String proc_name, Object[] args, int expd)		{Test__proc__kvps(lib, proc_name, Bool_.Y, Int_.To_str(expd), Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__objs__flat(Scrib_lib lib, String proc_name, Object[] args, String expd)		{Test__proc__kvps(lib, proc_name, Bool_.Y, expd, Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__objs__nest(Scrib_lib lib, String proc_name, Object[] args, Keyval[] expd)	{Test__proc__kvps(lib, proc_name, Bool_.N, Keyval_.Ary__to_str__nest(new Keyval[] {Keyval_.int_(Scrib_core.Base_1, expd)}), Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__objs__nest(Scrib_lib lib, String proc_name, Object[] args, String expd)		{Test__proc__kvps(lib, proc_name, Bool_.N, expd, Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__kvps__flat(Scrib_lib lib, String proc_name, Keyval[] args, String expd) {Test__proc__kvps(lib, proc_name, Bool_.Y, expd, args);}
	public void Test__proc__kvps__nest(Scrib_lib lib, String proc_name, Keyval[] args, String expd) {Test__proc__kvps(lib, proc_name, Bool_.N, expd, args);}
	private static void Test__proc__kvps(Scrib_lib lib, String proc_name, boolean flat, String expd, Keyval[] args) {
		Keyval[] actl_ary = Mock_scrib_fxt_.Test__lib_proc__core(lib, proc_name, args);
		if (flat)
			Tfds.Eq(expd, Mock_scrib_fxt_.Kvp_vals_to_str(actl_ary));
		else
			Tfds.Eq_str_lines(expd, Keyval_.Ary__to_str__nest(actl_ary));
	}
	public void Test__proc__objs__empty(Scrib_lib lib, String proc_name, Object[] args) {Test__proc__kvps__empty(lib, proc_name, Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__kvps__empty(Scrib_lib lib, String proc_name, Keyval[] args) {
		Tfds.Eq(0, Mock_scrib_fxt_.Test__lib_proc__core(lib, proc_name, args).length);
	}
	public void Test__proc__kvps__vals(Scrib_lib lib, String proc_name, Keyval[] args, Object... expd_ary) {
		Keyval[] actl_kvs = Mock_scrib_fxt_.Test__lib_proc__core(lib, proc_name, args);
		Object[] actl_ary = Mock_scrib_fxt_.Kvp_vals_to_objs(actl_kvs);
		Tfds.Eq_ary(expd_ary, actl_ary);
	}
}
class Mock_scrib_fxt_ {
	public static Keyval[] Test__lib_proc__core(Scrib_lib lib, String proc_name, Keyval[] args) {
		Scrib_proc proc = lib.Procs().Get_by_key(proc_name);
		Scrib_proc_rslt proc_rslt = new Scrib_proc_rslt();
		proc.Proc_exec(new Scrib_proc_args(args), proc_rslt);
		return proc_rslt.Ary();
	}
	public static Object[] Kvp_vals_to_objs(Keyval[] kvps) {
		int len = kvps.length;
		Object[] rv = new Object[len];
		for (int i = 0; i < len; ++i)
			rv[i] = kvps[i].Val();
		return rv;
	}
	public static String Kvp_vals_to_str(Keyval[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Semic);
			Keyval kv = ary[i];
			bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(kv.Val()));
		}
		return bfr.To_str_and_clear();
	}
}
class Mock_server implements Scrib_server {
	public void		Init(String... process_args) {}
	public int		Server_timeout() {return server_timeout;} public Scrib_server Server_timeout_(int v) {server_timeout = v; return this;} private int server_timeout = 60;
	public int		Server_timeout_polling() {return server_timeout_polling;} public Scrib_server Server_timeout_polling_(int v) {server_timeout_polling = v; return this;} private int server_timeout_polling = 1;
	public int		Server_timeout_busy_wait() {return server_timeout_busy_wait;} public Scrib_server Server_timeout_busy_wait_(int v) {server_timeout_busy_wait = v; return this;} private int server_timeout_busy_wait = 1;
	public byte[]	Server_comm(byte[] cmd, Object[] cmd_objs) {return Bry_.Empty;}
	public void		Server_send(byte[] cmd, Object[] cmd_objs) {}
	public byte[]	Server_recv() {return Bry_.Empty;}
	public void		Term() {}
}
