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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.tests.*;
public class Io_fil_chkr implements Tst_chkr {
	public Io_fil_chkr(Io_url url, String data) {this.expd_url = url; this.expd_data = data;}
	public Io_url Expd_url() {return expd_url;} public Io_fil_chkr Expd_url_(Io_url v) {expd_url = v; return this;} Io_url expd_url;
	public String Expd_data() {return expd_data;} public Io_fil_chkr Expd_data_(String v) {expd_data = v; return this;} private String expd_data;
	public Class<?> TypeOf() {return gplx.core.ios.Io_fil.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl) {
		gplx.core.ios.Io_fil fil = (gplx.core.ios.Io_fil)actl;
		int rv = 0;
		rv += mgr.Tst_val(expd_url == null, path, "url", expd_url, fil.Url());
		rv += mgr.Tst_val(expd_data == null, path, "data", expd_data, fil.Data());
		return rv;
	}
}
