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
package gplx.xowa.langs.msgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xow_mainpage_finder {
	public static byte[] Find_or(Xowe_wiki wiki, byte[] or) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Xol_msg_itm msg_itm = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, wiki.Lang(), Msg_mainpage);
		byte[] rv = msg_itm.Defined_in_none()
			? or
			: Xol_msg_mgr_.Get_msg_val(tmp_bfr, wiki, msg_itm, Bry_.Ary_empty)
			;
		tmp_bfr.Mkr_rls();
		return rv;
	}
	public static final    byte[] Msg_mainpage = Bry_.new_a7("mainpage");
}
