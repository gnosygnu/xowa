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
package gplx.xowa.parsers.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.log_msgs.*;
public class Xop_hdr_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "hdr");
	public static final Gfo_msg_itm
		  Dangling_hdr						= Gfo_msg_itm_.new_warn_(owner, "dangling_hdr")
		, Mismatched						= Gfo_msg_itm_.new_warn_(owner, "mismatched")
		, Len_1								= Gfo_msg_itm_.new_warn_(owner, "len_1")
		, Len_7_or_more						= Gfo_msg_itm_.new_warn_(owner, "len_7_or_more")
		;
}
