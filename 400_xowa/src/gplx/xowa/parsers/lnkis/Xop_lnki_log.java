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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.log_msgs.*;
public class Xop_lnki_log {		
	private static final Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "lnki");
	public static final Gfo_msg_itm
	  Upright_val_is_invalid			= Gfo_msg_itm_.new_warn_(owner, "upright_val_is_invalid")
	, Escaped_lnki						= Gfo_msg_itm_.new_warn_(owner, "escaped_lnki")
	, Key_is_empty						= Gfo_msg_itm_.new_warn_(owner, "key_is_empty")
	, Ext_is_missing					= Gfo_msg_itm_.new_warn_(owner, "ext_is_missing")
	, Invalid_ttl						= Gfo_msg_itm_.new_warn_(owner, "invalid_ttl")
	;
}
