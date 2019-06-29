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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import gplx.core.brys.*;
public class Xophp_ary_itm implements Bry_bfr_able {
	public Xophp_ary_itm(int key_as_int, String key_as_str, Object val) {
		this.key_as_int = key_as_int;
		this.key_as_str = key_as_str;
		this.val = val;
	}
	public int Key_as_int() {return key_as_int;} private final    int key_as_int;
	public String Key_as_str() {return key_as_str;} private final    String key_as_str;
	public Object Val() {return val;} private final    Object val;
	public void To_bfr(Bry_bfr bfr) {
		String key = key_as_str == null ? Int_.To_str(key_as_int) : key_as_str;
		bfr.Add_str_u8(key).Add_byte_eq();

		if (Type_.Type_by_obj(val) == Xophp_ary.class) {
			Xophp_ary sub_ary = (Xophp_ary)val;
			bfr.Add_byte_nl();
			sub_ary.To_bfr(bfr);
		}
		else {
			bfr.Add_obj(val);
		}
	}

	public static Xophp_ary_itm New(int key, Object val)    {return new Xophp_ary_itm(key, null, val);}
	public static Xophp_ary_itm New(String key, Object val) {return new Xophp_ary_itm(-1 , key , val);}
}
