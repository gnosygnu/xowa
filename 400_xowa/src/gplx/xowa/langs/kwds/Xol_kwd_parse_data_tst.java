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
package gplx.xowa.langs.kwds; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*; import gplx.core.primitives.*;
public class Xol_kwd_parse_data_tst {
	@Before public void init() {Clear();}
	@Test  public void Basic()			{Key_("upright" ).Tst_strip("upright");}
	@Test  public void Eq_arg()			{Key_("upright" ).Tst_strip("upright=$1");}
	@Test  public void Space()			{Key_("upright ").Tst_strip("upright $1");}
	@Test  public void Px()				{Key_("px").Tst_strip("$1px");}

	private void Clear() {
		key = null;
	}
	Xol_kwd_parse_data_tst Key_(String v) {this.key = v; return this;} private String key;
	Xol_kwd_parse_data_tst Tst_strip(String v) {
		Bry_bfr tmp = Bry_bfr_.New();
		Byte_obj_ref rslt = Byte_obj_ref.zero_();
		byte[] actl = Xol_kwd_parse_data.Strip(tmp, Bry_.new_a7(v), rslt);
		Tfds.Eq(key, String_.new_a7(actl));
		return this;
	}
}
