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
package gplx.xowa.xtns.scribunto.engines.process;
import gplx.types.basics.encoders.HexUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_kv_utl_;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.Scrib_xtn_mgr;
import gplx.xowa.xtns.scribunto.engines.Scrib_engine;
import gplx.xowa.xtns.scribunto.engines.Scrib_server;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Process_engine implements Scrib_engine {
	private Scrib_core core; private Xoae_app app; private Scrib_xtn_mgr scrib_opts;
	private Process_recv_msg rsp = new Process_recv_msg(); private Process_send_wtr msg_encoder;
	private Scrib_proc_mgr proc_mgr;
	public Process_engine(Xoae_app app, Xowe_wiki wiki, Scrib_core core) {
		this.app = app; this.core = core; this.proc_mgr = core.Proc_mgr();
		msg_encoder = new Process_send_wtr(app.Usr_dlg()); 
		server = new Process_server();
		scrib_opts = (Scrib_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Scrib_xtn_mgr.XTN_KEY);
	}
	public boolean Dbg_print() {return dbg_print;} public void Dbg_print_(boolean v) {dbg_print = v;} private boolean dbg_print;
	public Scrib_server Server() {return server;} public void Server_(Scrib_server v) {server = v;} Scrib_server server;
	public Scrib_lua_proc LoadString(String name, String text) {
		KeyVal[] rslt = this.Dispatch("op", "loadString", "text", text, "chunkName", name);
		return new Scrib_lua_proc(name, IntUtl.Cast(rslt[0].Val()));
	}
	public KeyVal[] CallFunction(int id, KeyVal[] args) {
		return this.Dispatch("op", "call", "id", id, "nargs", args.length, "args", args);
	}
	public void RegisterLibrary(KeyVal[] functions_ary) {
		this.Dispatch("op", "registerLibrary", "name", Scrib_core.Key_mw_interface, "functions", functions_ary);
	}
	public void CleanupChunks(KeyVal[] ids) {
		this.Dispatch("op", "cleanupChunks", "ids", ids);
	}
	public KeyVal[] ExecuteModule(int mod_id) {
		return this.CallFunction(core.Lib_mw().Mod().Fncs_get_id("executeModule"), Scrib_kv_utl_.base1_obj_(new Scrib_lua_proc("", mod_id)));
	}
	private KeyVal[] Dispatch(Object... ary) {
		BryWtr bfr = app.Utl__bfr_mkr().GetK004().Clear();
		while (true) {
			Dispatch_bld_send(bfr, ary);
			boolean log_enabled = scrib_opts.Lua_log_enabled();
			if (log_enabled) app.Usr_dlg().Log_direct("sent:" + bfr.ToStr() + "\n");
			byte[] rsp_bry = server.Server_comm(bfr.ToBryAndClear(), ary);
			if (log_enabled) app.Usr_dlg().Log_direct("rcvd:" + StringUtl.NewU8(rsp_bry) + "\n\n");
			String op = rsp.Extract(rsp_bry);
			if		(StringUtl.Eq(op, "return")) {
				bfr.MkrRls();
				return rsp.Values();
			}
			else if	(StringUtl.Eq(op, "error")) {
				core.Handle_error(rsp.Rslt_ary()[0].ValToStrOrEmpty());
				return KeyValUtl.AryEmpty;
			}
			else if (StringUtl.Eq(op, "call")) {
				String id = rsp.Call_id();
				KeyVal[] args = rsp.Call_args();
				Scrib_proc proc = proc_mgr.Get_by_key(id); if (proc == null) throw Scrib_xtn_mgr.err_("could not find proc with id of {0}", id);
				Scrib_proc_args proc_args = new Scrib_proc_args(args);
				Scrib_proc_rslt proc_rslt = new Scrib_proc_rslt();
				proc.Proc_exec(proc_args, proc_rslt);
				String fail_msg = proc_rslt.Fail_msg();
				if (fail_msg == null) {
					KeyVal[] cbk_rslts = proc_rslt.Ary();
					ary = ObjectUtl.Ary("op", "return", "nvalues", cbk_rslts.length, "values", cbk_rslts);
				}
				else {
					ary = ObjectUtl.Ary("op", "error", "value", fail_msg);
				}
			}
			else {
				bfr.MkrRls();
//					app.Usr_dlg().Warn_many("", "", "invalid dispatch: op=~{0} page=~{1}", op, String_.new_u8(core.Ctx().Page().Page_ttl().Page_db()));
				return KeyValUtl.AryEmpty;
			}
		}
	}	private static final byte[] Dispatch_hdr = BryUtl.NewA7("0000000000000000");	// itm_len + itm_chk in 8-len HexDec
	private void Dispatch_bld_send(BryWtr bfr, Object[] ary) {
		int len = ary.length; if (len % 2 != 0) throw ErrUtl.NewArgs("arguments must be factor of 2", "len", len);
		bfr.Add(Dispatch_hdr);
		bfr.AddByte(AsciiByte.CurlyBgn);
		for (int i = 0; i < len; i++) {
			Object itm = ary[i];
			if (i % 2 == 0)	{
				if (i != 0) bfr.AddByte(AsciiByte.Comma);
				msg_encoder.Encode_key(bfr, itm);			
			}
			else
				msg_encoder.Encode_obj(bfr, itm);
		}
		bfr.AddByte(AsciiByte.CurlyEnd);
		int msg_len = bfr.Len() - 16;		// 16 for Dispatch_hdr_len
		int chk_len = (msg_len * 2) -1;		// defined by Scribunto
		HexUtl.Write(bfr.Bry(), 0,  8, msg_len);
		HexUtl.Write(bfr.Bry(), 9, 16, chk_len);
	}
}
