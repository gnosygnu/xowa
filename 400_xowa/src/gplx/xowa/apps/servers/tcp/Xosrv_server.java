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
package gplx.xowa.apps.servers.tcp; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.core.primitives.*; import gplx.core.ios.*; import gplx.core.envs.*; import gplx.core.threads.*;
import gplx.gfui.controls.standards.*;
import gplx.langs.jsons.*;
public class Xosrv_server implements Gfo_invk {
	private long last_cmd;
	public Xosrv_socket_rdr Rdr() {return rdr;} private Xosrv_socket_rdr rdr = new Xosrv_socket_rdr();
	public Xosrv_socket_wtr Wtr() {return wtr;} private Xosrv_socket_wtr wtr = new Xosrv_socket_wtr();
	public int Rdr_port() {return rdr_port;} public Xosrv_server Rdr_port_(int v) {rdr_port = v; return this;} private int rdr_port = 55000;
	public int Wtr_port() {return wtr_port;} public Xosrv_server Wtr_port_(int v) {wtr_port = v; return this;} private int wtr_port = 55001;
	public int Shutdown_interval() {return shutdown_interval;} public Xosrv_server Shutdown_interval_(int v) {shutdown_interval = v; return this;} private int shutdown_interval = -1;
	public String Wtr_host() {return wtr_host;} private String wtr_host = "localhost";
	public boolean Running() {return running;} public Xosrv_server Running_(boolean v) {running = v; running_str = Bool_.To_str_lower(running); return this;} private boolean running = false;
	public String Running_str() {return running_str;} String running_str = "false";
	public void App_ctor(Xoae_app app) {this.app = app;}
	public Xoae_app App() {return app;} private Xoae_app app;
	public void Run() {
		rdr.Init(this, rdr_port);
		wtr.Init(wtr_host, wtr_port);
		Gxw_html_server.Init_gui_for_server(app, wtr);
		Thread_adp_.Start_by_key(gplx.xowa.apps.Xoa_thread_.Key_http_server_main, rdr, Xosrv_socket_rdr.Invk_start);
		app.Usr_dlg().Note_many("", "", "server started: listening on ~{0}. Press Ctrl+C to exit", rdr_port);
		last_cmd = System_.Ticks();
		Running_(true);
		while (running) {
			if (shutdown_interval != -1 && System_.Ticks() - last_cmd > shutdown_interval) break;
			Thread_adp_.Sleep(1000);
		}
		rdr.Rls();
		wtr.Rls();
		app.Usr_dlg().Note_many("", "", "server stopped", rdr_port);
	}
	public void Msg_rcvd(Xosrv_msg msg) {
		try {
			byte[] cmd_name = msg.Cmd_name();
			byte[] rsp_name = Bry_.Empty;
			long time_bgn = System_.Ticks();
			last_cmd = time_bgn;
			byte[] msg_bry = msg.Msg_text();
			String msg_str = String_.new_u8(msg_bry);
			app.Usr_dlg().Note_many("", "", "processing cmd: ~{0}", msg_str);
			String rsp_str = null;
			if		(Bry_.Eq(cmd_name, Xosrv_cmd_types.Cmd_exec)) 	{rsp_name = Xosrv_cmd_types.Cmd_pass; rsp_str = Exec_cmd(msg_str);}
			else if	(Bry_.Eq(cmd_name, Xosrv_cmd_types.Js_exec)) 	{rsp_name = Xosrv_cmd_types.Js_pass;  rsp_str = Exec_js(msg.Sender(), msg_bry);}
			Xosrv_msg rsp_msg = Xosrv_msg.new_(rsp_name, msg.Msg_id(), msg.Recipient(), msg.Sender(), msg.Msg_date(), Bry_.new_u8(rsp_str));
			app.Usr_dlg().Note_many("", "", "sending rsp: bytes=~{0}", String_.Len(rsp_str));
			wtr.Write(rsp_msg);		
			app.Usr_dlg().Note_many("", "", "rsp sent: elapsed=~{0}", Time_span_.fracs_(System_.Ticks() - time_bgn).XtoStrUiAbbrv());
		} catch (Exception e) {app.Usr_dlg().Warn_many("", "", "server error: ~{0}", Err_.Message_gplx_full(e));}
	}
	private String Exec_cmd(String msg_text) {
		Object rv_obj = app.Gfs_mgr().Run_str(msg_text);
		String rv = Type_adp_.Eq_typeSafe(rv_obj, String_.Cls_ref_type) ? (String)rv_obj : Object_.Xto_str_strict_or_null(rv_obj);
		return rv;
	}
	public String Exec_js(byte[] sender, byte[] msg_text) {
		String_obj_ref trace = String_obj_ref.new_("exec_js");
		try {
			Object[] xowa_exec_args = xowa_exec_parser.Parse_xowa_exec(msg_text);
			trace.Val_("js_args");
//				xowa_exec_args = (Object[])Array_.Resize(xowa_exec_args, xowa_exec_args.length + 1);
//				xowa_exec_args[xowa_exec_args.length - 1] = sender;
			Object rv_obj = Gfui_html.Js_args_exec(app.Gui_mgr().Browser_win().Active_html_itm().Js_cbk(), xowa_exec_args);
			trace.Val_("json_write: " + Object_.Xto_str_strict_or_null_mark(rv_obj));
			return json_wtr.Write_root(Bry_xowa_js_result, rv_obj).Bld_as_str();
		} catch (Exception e) {throw Err_.new_exc(e, "http", "exec_js error", "trace", trace, "msg", msg_text);}
	}	private Xosrv_xowa_exec_parser xowa_exec_parser = new Xosrv_xowa_exec_parser(); private Json_doc_srl json_wtr = new Json_doc_srl(); private static final    byte[] Bry_xowa_js_result = Bry_.new_a7("xowa_js_result");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_rdr_port))				return rdr_port;
		else if	(ctx.Match(k, Invk_rdr_port_))				rdr_port = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_wtr_port))				return wtr_port;
		else if	(ctx.Match(k, Invk_wtr_port_))				wtr_port = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_wtr_host))				return wtr_host;
		else if	(ctx.Match(k, Invk_wtr_host_))				wtr_host = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_shutdown_interval))		return shutdown_interval;
		else if	(ctx.Match(k, Invk_shutdown_interval_))		shutdown_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_stop))					running = false;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final    String Invk_stop = "stop", Invk_rdr_port = "rdr_port", Invk_rdr_port_ = "rdr_port_", Invk_wtr_port = "wtr_port", Invk_wtr_port_ = "wtr_port_", Invk_wtr_host = "wtr_host", Invk_wtr_host_ = "wtr_host_"
	, Invk_shutdown_interval = "shutdown_interval", Invk_shutdown_interval_ = "shutdown_interval_";
}
class Xosrv_xowa_exec_parser {
	private Json_parser json_parser = new Json_parser();
	public Object[] Parse_xowa_exec(byte[] msg_text) {	// parses JSON with format '{"args":["arg0","arg1","arg2"]}'
		Json_doc doc = json_parser.Parse(msg_text);
		Json_kv args_kv = (Json_kv)doc.Root_nde().Get_at(0);	// get "args" kv
		Json_ary args_ary = (Json_ary)args_kv.Val();			// get []
		int len = args_ary.Len();			
		Object[] rv = new Object[len];
		for (int i = 0; i < len; i++) {	// extract args
			Json_itm itm = args_ary.Get_at(i);
			rv[i] = Parse_ary_itm(itm);
		}
		return rv;
	}
	private Object Parse_ary_itm(Json_itm itm) {
		switch (itm.Tid()) {
			case Json_itm_.Tid__str:
				return String_.new_u8(itm.Data_bry());
			case Json_itm_.Tid__ary: 
				Json_ary ary = (Json_ary)itm;
				int len = ary.Len();
				String[] rv = new String[len];
				for (int i = 0; i < len; i++)
					rv[i] = String_.new_u8(ary.Get_at(i).Data_bry());
				return rv;
			default:
				throw Err_.new_unhandled(itm.Tid());
		}
	}
}
