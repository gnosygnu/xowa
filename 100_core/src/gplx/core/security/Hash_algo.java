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
package gplx.core.security; import gplx.*; import gplx.core.*;
public interface Hash_algo {
	String Key();
	byte[] Hash_bry_as_bry(byte[] src);
	String Hash_bry_as_str(byte[] src);
	String Hash_stream_as_str(gplx.core.consoles.Console_adp console, gplx.core.ios.streams.IoStream src_stream);
	byte[] Hash_stream_as_bry(gplx.core.progs.Gfo_prog_ui prog_ui, gplx.core.ios.streams.IoStream src_stream);
}
