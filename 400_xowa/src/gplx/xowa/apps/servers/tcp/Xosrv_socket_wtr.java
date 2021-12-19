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
package gplx.xowa.apps.servers.tcp;
import gplx.libs.ios.IoConsts;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Xosrv_socket_wtr {
	public String Host() {return host;} private String host = "localhost";
	public int Port() {return port;} private int port;
	private Socket_wtr wtr; private BryWtr msg_bfr = BryWtr.NewAndReset(4 * IoConsts.LenKB);
	public void Init(String host, int port) {this.host = host; this.port = port; wtr = new Socket_wtr().Ctor(host, port);}
	public void Write(Xosrv_msg msg) {
		wtr.Open();
		msg.Print(msg_bfr);
		byte[] msg_bry = msg_bfr.ToBryAndClear();
		wtr.Write(msg_bry);
		wtr.Close();
	}
	public void Rls() {
		wtr.Rls();
	}
}
