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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public interface Io_sort_filCmd {
	void Bfr_add(Io_line_rdr stream);
	void Fil_bgn(Io_line_rdr stream);
	void Fil_end();
}
class Io_sort_filCmd_null implements Io_sort_filCmd {
	public void Bfr_add(Io_line_rdr stream) {}
	public void Fil_bgn(Io_line_rdr stream) {}
	public void Fil_end() {}
	public static final    Io_sort_filCmd_null Instance = new Io_sort_filCmd_null(); Io_sort_filCmd_null() {}	// TS.static
}
