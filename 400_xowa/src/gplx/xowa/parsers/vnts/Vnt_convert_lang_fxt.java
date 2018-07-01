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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.langs.vnts.converts.*;
class Vnt_convert_lang_fxt {
	private final    Vnt_convert_lang converter_lang;
	private final    Xol_convert_mgr convert_mgr = new Xol_convert_mgr();
	private final    Xol_vnt_regy vnt_regy = Xol_vnt_regy_fxt.new_chinese();
	private Xol_vnt_itm vnt_itm;
	public Vnt_convert_lang_fxt() {
		converter_lang = new Vnt_convert_lang(convert_mgr, vnt_regy);
		this.Clear();
	}
	public void Clear() {
		convert_mgr.Init(vnt_regy);
		Init_cur("zh-cn");
	}
	public Vnt_convert_lang_fxt Init_cur(String vnt) {
		byte[] cur_vnt = Bry_.new_a7(vnt);
		this.vnt_itm = vnt_regy.Get_by(cur_vnt);
		return this;
	}
	public void Test_parse(String raw, String expd) {
		Tfds.Eq_str(expd, String_.new_u8(converter_lang.Parse_page(vnt_itm, -1, Bry_.new_u8(raw))));
	}
	public void Test_parse_many(String raw, String expd, String... vnts) {
		int len = vnts.length;
		for (int i = 0; i < len; ++i) {
			String vnt_key = vnts[i];
			Init_cur(vnt_key);
			Xol_vnt_itm vnt = vnt_regy.Get_by(Bry_.new_a7(vnt_key));
			Tfds.Eq_str(expd, String_.new_u8(converter_lang.Parse_page(vnt, -1, Bry_.new_u8(raw))), vnt_key);
		}
	}
	public void Test_parse_title(String raw, String expd_title, String expd_text, String vnt_key) {
		Init_cur(vnt_key);
		Xol_vnt_itm vnt = vnt_regy.Get_by(Bry_.new_a7(vnt_key));
		Tfds.Eq_str(expd_text, String_.new_u8(converter_lang.Parse_page(vnt, -1, Bry_.new_u8(raw))), vnt_key);
		Tfds.Eq_str(expd_title, converter_lang.Converted_title());
	}
}
