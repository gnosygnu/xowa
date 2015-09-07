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
package gplx.core.net; import gplx.*; import gplx.core.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class Server_socket_adp__base implements Server_socket_adp {
		private ServerSocket server_socket;
		public Server_socket_adp Ctor(int port) {
				try {
			this.server_socket = new ServerSocket();
			server_socket.setReuseAddress(true);			
			server_socket.bind(new InetSocketAddress(port));
		}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Get_input_stream failed");}
		return this;
			}
	public Socket_adp Accept() {
				Socket client_socket = null;
		try {client_socket = server_socket.accept();}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Get_input_stream failed");}
		return new Socket_adp__base(client_socket);
			}
	public void Rls() {
				try {server_socket.close();} 
		catch (IOException e) {throw Err_.new_exc(e, "net", "Rls failed");}
			}
}
