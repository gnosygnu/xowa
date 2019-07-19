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
package gplx.core.ios.atrs; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
public class Io_itm_atr_req {
	Io_itm_atr_req(boolean ignore_errors, boolean check_read_only) {
		this.ignore_errors = ignore_errors;
		this.check_read_only = check_read_only;
	}
	public boolean Check_read_only() {return check_read_only;} private final    boolean check_read_only;
	public boolean Is_read_only() {return is_read_only;} public void Is_read_only_(boolean v) {this.is_read_only = v;} private boolean is_read_only;
	public boolean Ignore_errors() {return ignore_errors;} private final    boolean ignore_errors;
	public String To_str() {
		Keyval[] ary = new Keyval[2];
		ary[0] = Keyval_.new_("check_read_only", check_read_only);
		ary[1] = Keyval_.new_("is_read_only", is_read_only);
		return Keyval_.Ary_to_str(ary);
	}

	public static Io_itm_atr_req New__read_only() {
		return new Io_itm_atr_req(true, true);
	}
}
