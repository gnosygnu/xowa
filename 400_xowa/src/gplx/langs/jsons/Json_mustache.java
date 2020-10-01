/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;

public class Json_mustache {

	public static Json_kv Add_text(String keystr, byte[] bytes) {
		if (bytes == null) return null;
		Json_itm_str key = new Json_itm_str(Bry_.new_a7(keystr), true);
		Json_itm_str val = new Json_itm_str(bytes, false);
		return new Json_kv(key, val);
	}

	public static Json_kv Add_int(String keystr, int intval) {
		Json_itm_str key = new Json_itm_str(Bry_.new_a7(keystr), true);
		Json_itm_int val = new Json_itm_int(intval);
		return new Json_kv(key, val);
	}

	public static Json_kv Add_double(String keystr, double doubleval) {
		// eek
		Json_itm_str key = new Json_itm_str(Bry_.new_a7(keystr), true);
		Bry_bfr bfr = Bry_bfr_.New();
		byte[] bytes = bfr.Add_double(doubleval).To_bry();
		Json_itm_str val = new Json_itm_str(bytes, true);
		return new Json_kv(key, val);
	}

	public static Json_kv Add_bool(String keystr, boolean bool) {
		Json_itm_str key = new Json_itm_str(Bry_.new_a7(keystr), true);
		Json_itm_bool val = new Json_itm_bool(bool);
		return new Json_kv(key, val);
	}

	public static Json_kv Add_nde(String keystr, Json_nde nde) {
		Json_itm_str key = new Json_itm_str(Bry_.new_a7(keystr), true);
		return new Json_kv(key, nde);
	}

	public static Json_kv Add_ary(String keystr, Json_ary ary) {
		if (ary == null) return null;
		Json_itm_str key = new Json_itm_str(Bry_.new_a7(keystr), true);
		return new Json_kv(key, ary);
	}
}