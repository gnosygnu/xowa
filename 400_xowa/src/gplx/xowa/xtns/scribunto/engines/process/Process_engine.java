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
package gplx.xowa.xtns.scribunto.engines.process; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.texts.*;
import gplx.xowa.xtns.scribunto.lib.*;
public class Process_engine implements Scrib_engine {
	private Scrib_core core; private Xoa_app app; private Scrib_xtn_mgr scrib_opts;
	private Process_recv_msg rsp = new Process_recv_msg(); private Process_send_wtr msg_encoder;
	private Scrib_proc_mgr proc_mgr;
	public Process_engine(Xoa_app app, Scrib_core core) {
		this.app = app; this.core = core; this.proc_mgr = core.Proc_mgr();
		msg_encoder = new Process_send_wtr(app.Usr_dlg()); 
		server = new Process_server();
		scrib_opts = (Scrib_xtn_mgr)app.Xtn_mgr().Get_or_fail(Scrib_xtn_mgr.XTN_KEY);
	}
	public boolean Dbg_print() {return dbg_print;} public void Dbg_print_(boolean v) {dbg_print = v;} private boolean dbg_print;
	public Scrib_server Server() {return server;} public void Server_(Scrib_server v) {server = v;} Scrib_server server;
	public Scrib_lua_proc LoadString(String name, String text) {
		KeyVal[] rslt = this.Dispatch("op", "loadString", "text", text, "chunkName", name);
		return new Scrib_lua_proc(name, Int_.cast_(rslt[0].Val()));
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
		Bry_bfr bfr = app.Utl_bry_bfr_mkr().Get_k004().Clear();
		while (true) {
			Dispatch_bld_send(bfr, ary);
			boolean log_enabled = scrib_opts.Lua_log_enabled();
			if (log_enabled) app.Usr_dlg().Log_direct("sent:" + bfr.XtoStr() + "\n");
			byte[] rsp_bry = server.Server_comm(bfr.XtoAryAndClear(), ary);
			if (log_enabled) app.Usr_dlg().Log_direct("rcvd:" + String_.new_utf8_(rsp_bry) + "\n\n");
			String op = rsp.Extract(rsp_bry);
			if		(String_.Eq(op, "return")) {
				bfr.Mkr_rls();
				return rsp.Values();
			}
			else if	(String_.Eq(op, "error")) {
				core.Handle_error(rsp.Rslt_ary()[0].Val_to_str_or_empty(), "");
				return KeyVal_.Ary_empty;
			}
			else if (String_.Eq(op, "call")) {
				String id = rsp.Call_id();
				KeyVal[] args = rsp.Call_args();
				Scrib_proc proc = proc_mgr.Get_by_key(id); if (proc == null) throw Scrib_xtn_mgr.err_("could not find proc with id of {0}", id);
				Scrib_proc_args proc_args = new Scrib_proc_args(args);
				Scrib_proc_rslt proc_rslt = new Scrib_proc_rslt();
				proc.Proc_exec(proc_args, proc_rslt);
				String fail_msg = proc_rslt.Fail_msg();
				if (fail_msg == null) {
					KeyVal[] cbk_rslts = proc_rslt.Ary();
					ary = Object_.Ary("op", "return", "nvalues", cbk_rslts.length, "values", cbk_rslts);
				}
				else {
					ary = Object_.Ary("op", "error", "value", fail_msg);
				}
			}
			else {
				bfr.Mkr_rls();
//					app.Usr_dlg().Warn_many("", "", "invalid dispatch: op=~{0} page=~{1}", op, String_.new_utf8_(core.Ctx().Cur_page().Page_ttl().Page_db()));
				return KeyVal_.Ary_empty;
			}
		}
	}	private static final byte[] Dispatch_hdr = Bry_.new_ascii_("0000000000000000");	// itm_len + itm_chk in 8-len HexDec
	private void Dispatch_bld_send(Bry_bfr bfr, Object[] ary) {
		int len = ary.length; if (len % 2 != 0) throw Err_.new_fmt_("arguments must be factor of 2: {0}", len);
		bfr.Add(Dispatch_hdr);
		bfr.Add_byte(Byte_ascii.Curly_bgn);
		for (int i = 0; i < len; i++) {
			Object itm = ary[i];
			if (i % 2 == 0)	{
				if (i != 0) bfr.Add_byte(Byte_ascii.Comma);
				msg_encoder.Encode_key(bfr, itm);			
			}
			else
				msg_encoder.Encode_obj(bfr, itm);
		}
		bfr.Add_byte(Byte_ascii.Curly_end);
		int msg_len = bfr.Len() - 16;		// 16 for Dispatch_hdr_len
		int chk_len = (msg_len * 2) -1;		// defined by Scribunto
		HexDecUtl.Write(bfr.Bfr(), 0,  8, msg_len);
		HexDecUtl.Write(bfr.Bfr(), 9, 16, chk_len);
	}
}
