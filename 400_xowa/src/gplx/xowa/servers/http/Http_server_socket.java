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
package gplx.xowa.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.servers.*;
import gplx.core.net.*; import gplx.core.threads.*;
class Http_server_socket implements GfoInvkAble {
	private final Http_server_mgr server_mgr;
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
			Http_server_wkr_v2 wkr = new Http_server_wkr_v2(server_mgr);
			wkr.Init_by_thread(server_socket.Accept());
			Thread_adp_.invk_("thread:xowa.http_server.client", wkr, Http_server_wkr_v2.Invk_run).Start();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run)) this.Run();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_run = "run";
}
