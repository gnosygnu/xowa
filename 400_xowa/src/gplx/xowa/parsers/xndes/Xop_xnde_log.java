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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.log_msgs.*;
public class Xop_xnde_log {
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "xnde");
	public static final Gfo_msg_itm
		  Dangling_xnde						= Gfo_msg_itm_.new_warn_(owner, "dangling_xnde")
		, End_tag_not_allowed				= Gfo_msg_itm_.new_note_(owner, "end_tag_not_allowed")
		, Escaped_xnde						= Gfo_msg_itm_.new_warn_(owner, "escaped_xnde")
		, Invalid_char						= Gfo_msg_itm_.new_warn_(owner, "invalid_char")
		, Xtn_end_not_found					= Gfo_msg_itm_.new_warn_(owner, "xtn_end_not_found")
		, Invalid_tbl_sub					= Gfo_msg_itm_.new_warn_(owner, "invalid_tbl_sub")
		, Invalid_nest						= Gfo_msg_itm_.new_warn_(owner, "invalid_nest")
		, No_inline							= Gfo_msg_itm_.new_warn_(owner, "no_inline")
		, Tbl_sub_already_opened			= Gfo_msg_itm_.new_warn_(owner, "tbl_sub_already_opened")
		, Auto_closing_section				= Gfo_msg_itm_.new_warn_(owner, "auto_closing_section")
		, Eos_while_closing_tag				= Gfo_msg_itm_.new_warn_(owner, "eos_while_closing_tag")
		, Sub_sup_swapped					= Gfo_msg_itm_.new_warn_(owner, "sub_sup_swapped")
		, Restricted_tag					= Gfo_msg_itm_.new_warn_(owner, "restricted_tag")
		;
}
