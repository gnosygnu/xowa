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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.langs.*;
public class Xob_i18n_parser {
	public static void Load_msgs(boolean dirty, Xol_lang_itm lang, Io_url i18n_fil) {
		String i18n_str = Io_mgr.Instance.LoadFilStr_args(i18n_fil).MissingIgnored_().Exec(); if (String_.Len_eq_0(i18n_str)) return;
		Json_itm_wkr__msgs wkr = new Json_itm_wkr__msgs();
		wkr.Ctor(dirty, lang.Msg_mgr());
		wkr.Exec(Bry_.new_u8(i18n_str));
	}
	public static byte[] Xto_gfs(byte[] raw) {
		Json_itm_wkr__gfs wkr = new Json_itm_wkr__gfs();
		wkr.Exec(raw);
		return wkr.Xto_bry();
	}
}
