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
package gplx.core.ios.streams; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
public interface Io_stream_wtr extends Rls_able {
	byte			Tid();
	Io_url			Url(); Io_stream_wtr Url_(Io_url v);
	void			Trg_bfr_(Bry_bfr v);
	Io_stream_wtr	Open();
	byte[]			To_ary_and_clear();

	void			Write(byte[] bry, int bgn, int len);
	void			Flush();
}
