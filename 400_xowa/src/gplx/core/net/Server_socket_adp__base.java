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
