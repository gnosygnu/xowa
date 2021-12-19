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
package gplx.xowa.langs.msgs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.*;
public class Xow_mainpage_finder {
	public static byte[] Find_or(Xowe_wiki wiki, byte[] or) {
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetB512();
		Xol_msg_itm msg_itm = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, wiki.Lang(), Msg_mainpage);
		byte[] rv = msg_itm.Defined_in_none()
			? or
			: Xol_msg_mgr_.Get_msg_val(tmp_bfr, wiki, msg_itm, BryUtl.AryEmpty)
			;
		tmp_bfr.MkrRls();
		return rv;
	}
	public static final byte[] Msg_mainpage = BryUtl.NewA7("mainpage");
}
