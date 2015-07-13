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
package gplx.xowa.servers.tcp; import gplx.*; import gplx.xowa.*; import gplx.xowa.servers.*;
import gplx.ios.*;
public class Xosrv_socket_rdr implements GfoInvkAble {
	private Socket_rdr rdr = new Socket_rdr();
	public int Port() {return port;} private int port;
	public void Init(Xosrv_server server, int port) {this.server = server; this.port = port;} private Xosrv_server server;
	public void Start() {
		rdr = new Socket_rdr();
		try {
			rdr.Ctor(port);
			while (true) {
				rdr.Open();
				IoStream rdr_stream = rdr.Rdr_stream();
				Xosrv_msg_rdr msg_rdr = new Xosrv_msg_rdr(new byte[24], rdr_stream);
				Xosrv_msg msg = msg_rdr.Read();
				if (msg == Xosrv_msg.Exit) continue;
				server.Msg_rcvd(msg);
				rdr.Close();
			}
		}	catch (Exception e) {Exc_.Noop(e);}
		finally {rdr.Rls();}
	}
	public void Rls() {
		rdr.Rls();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_start))	this.Start();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_start = "start";
}
