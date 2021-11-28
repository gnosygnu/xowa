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
import gplx.core.primitives.*; import gplx.langs.jsons.*; import gplx.langs.phps.*; import gplx.langs.gfs.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.parsers.*;
interface Json_itm_wkr {
	void Read_kv_sub(byte[] key, byte[] val);
}
abstract class Json_itm_wkr__base implements Json_itm_wkr {
	private Json_parser json_parser = new Json_parser();
	private Php_text_itm_parser php_quote_parser = new Php_text_itm_parser().Quote_is_single_(true);	// assume values are equivalent to php single quote; DATE:2014-08-06
	public void Exec(byte[] src) {
		List_adp tmp_list = List_adp_.New(); Byte_obj_ref tmp_result = Byte_obj_ref.zero_(); Bry_bfr tmp_bfr = Bry_bfr_.Reset(16); 
		Json_doc jdoc = json_parser.Parse(src);
		this.Exec_bgn();
		Json_nde root = jdoc.Root_nde();
		int subs_len = root.Len();
		for (int i = 0; i < subs_len; ++i) {
			Json_itm itm = root.Get_at(i);
			switch (itm.Tid()) {
				case Json_itm_.Tid__kv:
					Json_kv kv = (Json_kv)itm;
					if (kv.Key().Data_eq(Name_metadata)) continue;		// ignore @metadata node
					byte[] kv_key = kv.Key().Data_bry();
					byte[] kv_val = kv.Val().Data_bry();						
					kv_val = php_quote_parser.Parse_as_bry(tmp_list, kv_val, tmp_result, tmp_bfr);
					Read_kv_sub(kv_key, kv_val);
					break;
			}
		}
		this.Exec_end();
	}
	public void Exec_bgn() {}
	public void Exec_end() {}
	public abstract void Read_kv_sub(byte[] key, byte[] val);
	private static final byte[] Name_metadata = Bry_.new_a7("@metadata");
}
