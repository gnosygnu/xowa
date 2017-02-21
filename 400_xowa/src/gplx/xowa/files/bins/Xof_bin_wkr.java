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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.xowa.files.fsdb.*;
public interface Xof_bin_wkr {
	byte			Tid();
	String			Key();
	boolean			Resize_allowed(); void Resize_allowed_(boolean v);
	Io_stream_rdr	Get_as_rdr	(Xof_fsdb_itm itm, boolean is_thumb, int w);
	boolean			Get_to_fsys	(Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url);
}
