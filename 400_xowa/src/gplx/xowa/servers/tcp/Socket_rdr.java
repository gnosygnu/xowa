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
public class Socket_rdr {
		private java.net.ServerSocket server;
	private java.net.Socket client;
	private java.io.InputStream stream;
		public IoStream Rdr_stream() {return rdr_stream;} private IoStream rdr_stream = null;
	public int Port() {return port;} private int port;	
	public Socket_rdr Ctor(int port) {this.port = port; return this;}
	public Socket_rdr Open() {
				try {
//			this.Rls();
			if (server == null)
				server = new java.net.ServerSocket(port);
			client = server.accept();
			client.setSoTimeout(10000);
			stream = client.getInputStream();			
			rdr_stream = new IoStream_stream_rdr().UnderRdr_(stream);
		}	catch (Exception e) {throw Err_.err_(e, "failed to open socket; port:{0} err:{1}", port, Err_.Message_gplx_brief(e));}
		return this;
			}
	public void Close() {
				try {
//			if (server != null) server.close();
			if (client != null) client.close();
			if (stream != null) stream.close();
		} 	catch (Exception e) {throw Err_.err_(e, "failed to close socket; port:{0} err:{1}", port, Err_.Message_gplx_brief(e));}
			}
	public void Rls() {
				try {
			if (server != null) server.close();
			if (client != null) client.close();
			if (stream != null) stream.close();
		} 	catch (Exception e) {throw Err_.err_(e, "failed to rls socket; port:{0} err:{1}", port, Err_.Message_gplx_brief(e));}
			}
}
