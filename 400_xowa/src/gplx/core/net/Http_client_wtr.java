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
import gplx.core.ios.*; import gplx.core.ios.streams.*;
public interface Http_client_wtr {
	void Stream_(Object o);
	void Write_bry(byte[] bry);
	void Write_str(String s);
	void Write_mid(byte[] bry, int bgn, int end);
	void Write_stream(Io_stream_rdr stream_rdr);
	void Rls();
}
