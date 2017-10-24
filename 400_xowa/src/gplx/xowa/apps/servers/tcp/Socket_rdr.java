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
import gplx.core.ios.*; import gplx.core.ios.streams.*;
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
			if (server == null) {
				server = new java.net.ServerSocket(port);
				server.setReuseAddress(true);
			}
			client = server.accept();
			client.setSoTimeout(10000);
			stream = client.getInputStream();			
			rdr_stream = new IoStream_stream_rdr().UnderRdr_(stream);
					return this;
		}	catch (Exception e) {throw Err_.new_exc(e, "net", "failed to open socket", "port", port);}
	}
	public void Close() {
		try {
		//			if (server != null) server.close();
			if (client != null) client.close();
			if (stream != null) stream.close();
				} 	catch (Exception e) {throw Err_.new_exc(e, "net", "failed to close socket", "port", port);}
	}
	public void Rls() {
		try {
					if (server != null) server.close();
			if (client != null) client.close();
			if (stream != null) stream.close();
				} 	catch (Exception e) {throw Err_.new_exc(e, "net", "failed to rls socket", "port", port);}
	}
}
