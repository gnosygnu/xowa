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
package gplx.xowa.apps.site_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.langs.jsons.*;
import gplx.xowa.bldrs.wms.sites.*;
public abstract class Xoa_site_cfg_itm__base {
	public void Ctor(String key_str) {
		this.key_str = key_str; this.key_bry = Bry_.new_u8(key_str);
	}
	public String Key_str() {return key_str;} private String key_str;
	public byte[] Key_bry() {return key_bry;} private byte[] key_bry;
	public byte[] Parse_json(Xow_wiki wiki, Json_itm js_itm) {
		Json_ary ary = Json_ary.cast(js_itm);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		int len = ary.Len();
		for (int i = 0; i < len; ++i)
			Parse_json_ary_itm(bfr, wiki, i, ary.Get_at(i));
		return bfr.To_bry_and_rls();
	}
	public abstract void Parse_json_ary_itm(Bry_bfr bfr, Xow_wiki wiki, int i, Json_itm itm);
	public abstract void Exec_csv(Xow_wiki wiki, int loader_tid, byte[] dsv_bry);
}
