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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
class Gfo_cmd_arg_mgr_ {
	public static final String
	  Err__key__unknown				= "unknown key"
	, Err__key__duplicate			= "duplicate key"
	, Err__key__missing				= "first argument must be prefixed with --"
	, Err__val__required			= "value is required"
	, Err__val__invalid__yn			= "value must be either y or n"
	;
	public static Object Parse_ary_to_str(Gfo_cmd_arg_mgr mgr, int val_tid, String[] ary) {
		String itm = ary.length == 0 ? "" : ary[0];
		switch (val_tid) {
			case Gfo_cmd_arg_itm_.Val_tid_string:	return itm;
			case Gfo_cmd_arg_itm_.Val_tid_url:		return itm; // NOTE: do not parse urls as it can either be absolute (C:\dir\fil.txt) or relative (fil.txt). relative cannot be parsed without knowing owner dir
			case Gfo_cmd_arg_itm_.Val_tid_yn:
				int itm_as_int = Yn.parse_as_int(itm);
				if (itm_as_int == Bool_.__int) {
					mgr.Errs__add(Gfo_cmd_arg_mgr_.Err__val__invalid__yn, itm);
					return null;
				}
				return itm_as_int == Bool_.Y_int;
			default: throw Err_.new_unhandled(val_tid);
		}
	}
}
