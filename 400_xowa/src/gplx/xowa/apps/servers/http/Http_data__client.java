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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.langs.jsons.*;
public class Http_data__client {
	public Http_data__client(byte[] server_host, String ip_address_str) {
		this.server_host = server_host;
		this.ip_address = Bry_.new_a7(ip_address_str);
	}
	public byte[] Server_host() {return server_host;} private final byte[] server_host;
	public byte[] Ip_address() {return ip_address;} private final byte[] ip_address;
}
