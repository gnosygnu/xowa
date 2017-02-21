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
import java.net.*;
public class Socket_adp__base implements Socket_adp {
		private final Socket socket;
	public Socket_adp__base(Socket socket) {this.socket = socket;}
		public String Ip_address() {
				return socket.getRemoteSocketAddress().toString();
			}
	public Object Get_input_stream() {
				try {return socket.getInputStream();}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Get_input_stream failed");}
			}
	public Object Get_output_stream() {
				try {return socket.getOutputStream();}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Get_output_stream failed");}
			}
	public void Rls() {
				try {socket.close();} 
		catch (IOException e) {throw Err_.new_exc(e, "net", "Rls failed");}
			}
}
