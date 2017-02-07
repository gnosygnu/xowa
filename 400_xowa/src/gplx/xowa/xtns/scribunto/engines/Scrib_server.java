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
package gplx.xowa.xtns.scribunto.engines; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public interface Scrib_server {
	void		Init(String... process_args);
	int			Server_timeout(); Scrib_server Server_timeout_(int v);
	int			Server_timeout_polling(); Scrib_server Server_timeout_polling_(int v);
	int			Server_timeout_busy_wait(); Scrib_server Server_timeout_busy_wait_(int v);
	byte[]		Server_comm(byte[] cmd, Object[] cmd_objs);
	void		Server_send(byte[] cmd, Object[] cmd_objs);
	byte[]		Server_recv();
	void		Term();
}
