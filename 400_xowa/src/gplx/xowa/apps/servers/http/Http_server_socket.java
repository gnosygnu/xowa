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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.core.net.*; import gplx.core.threads.*; import gplx.core.primitives.*;
class Http_server_socket implements Gfo_invk {
	private final    Http_server_mgr server_mgr;
	private Server_socket_adp server_socket;
	public Http_server_socket(Http_server_mgr server_mgr) {this.server_mgr = server_mgr;}
	public boolean Canceled() {return canceled;}
	public Http_server_socket Canceled_(boolean v) {
		canceled = v;
		if (canceled) {
			server_socket.Rls();
			server_socket = null;
		}
		return this;
	}	private boolean canceled;
	public void Run() {		
		if (server_socket == null) server_socket = new Server_socket_adp__base().Ctor(server_mgr.Port());
		while (true) {	// listen for incoming requests
			Socket_adp client_socket = server_socket.Accept();	// NOTE: blocking call
			int wkr_uid = -1;	// NOTE: default to -1 to handle cases when http_server.max_clients is not set; DATE:2015-10-11
			Http_server_wkr_pool wkr_pool = server_mgr.Wkr_pool();
			if (wkr_pool.Enabled()) {
				Http_server_wtr server_wtr = server_mgr.Server_wtr();
				int timeout = wkr_pool.Timeout();
				boolean print_msg = true;
				while (wkr_pool.Full()) {
					if (print_msg) {
						print_msg = false;
						server_wtr.Write_str_w_nl("maximum # of concurrent connections reached; max=" + wkr_pool.Max() + " timeout=" + timeout);
					}
					Thread_adp_.Sleep(timeout);
				}
				wkr_uid = server_mgr.Uid_pool().Get_next();
				wkr_pool.Add(wkr_uid);
				// server_wtr.Write_str_w_nl("added new worker; uid=" + wkr_uid);
			}
			Http_server_wkr wkr = new Http_server_wkr(server_mgr, wkr_uid);
			wkr.Init_by_thread(client_socket);
			Thread_adp_.Start_by_key("thread:xowa.http_server.client", wkr, Http_server_wkr.Invk_run);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run)) this.Run();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final String Invk_run = "run";
}
