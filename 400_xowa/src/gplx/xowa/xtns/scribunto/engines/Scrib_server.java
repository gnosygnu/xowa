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
