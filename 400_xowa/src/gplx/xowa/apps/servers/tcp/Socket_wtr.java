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
