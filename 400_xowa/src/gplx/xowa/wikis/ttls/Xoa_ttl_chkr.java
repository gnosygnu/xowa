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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.tests.*;
public class Xoa_ttl_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Xoa_ttl.class;}
	public int Chk(Tst_mgr mgr, String path, Object o) {
		Xoa_ttl actl = (Xoa_ttl)o;
		int rv = 0;
		rv += mgr.Tst_val(expd_str == null, path, "raw", expd_str, String_.new_u8(actl.Raw()));
		return rv;
	}
	public String Expd_str() {return expd_str;} public Xoa_ttl_chkr Expd_str_(String v) {expd_str = v; return this;} private String expd_str;
	public static Xoa_ttl_chkr new_(String v) {return new Xoa_ttl_chkr().Expd_str_(v);} private Xoa_ttl_chkr() {}
}
