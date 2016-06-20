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
package gplx.xowa.apps.servers.tcp; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
public class Xosrv_socket_wtr {
	public String Host() {return host;} private String host = "localhost";
	public int Port() {return port;} private int port;
	private Socket_wtr wtr; private Bry_bfr msg_bfr = Bry_bfr_.Reset(4 * Io_mgr.Len_kb);
	public void Init(String host, int port) {this.host = host; this.port = port; wtr = new Socket_wtr().Ctor(host, port);}
	public void Write(Xosrv_msg msg) {
		wtr.Open();
		msg.Print(msg_bfr);
		byte[] msg_bry = msg_bfr.To_bry_and_clear();
		wtr.Write(msg_bry);
		wtr.Close();
	}
	public void Rls() {
		wtr.Rls();
	}
}
