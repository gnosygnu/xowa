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
public class Socket_wtr {
	private String host;
	private int port;
		private java.net.Socket socket;
	private java.io.OutputStream stream;
		public Socket_wtr Ctor(String host, int port) {this.host = host; this.port = port; return this;}
	public Socket_wtr Open() {
		try {
					this.Rls();
			socket = new java.net.Socket(host, port);
			socket.setSoTimeout(10000);
			stream = socket.getOutputStream();
					return this;
		}	catch (Exception e) {throw Err_.new_exc(e, "net", "failed to open socket", "host", host, "port", port);}
	}
	public void Write(byte[] bry) {
		try {
					stream.write(bry, 0, bry.length);
				}	catch (Exception e) {throw Err_.new_exc(e, "net", "failed to write stream", "host", host, "port", port);}
	}
	public void Close() {
		try {
					if (stream != null) stream.close();
			if (socket != null) socket.close();
				} 	catch (Exception e) {throw Err_.new_exc(e, "net", "failed to close socket", "host", host, "port", port);}
	}
	public void Rls() {
		try {
					if (stream != null) stream.close();
			if (socket != null) socket.close();
				} 	catch (Exception e) {throw Err_.new_exc(e, "net", "failed to release socket", "host", host, "port", port);}
	}
}
