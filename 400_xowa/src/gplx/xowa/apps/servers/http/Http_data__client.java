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
