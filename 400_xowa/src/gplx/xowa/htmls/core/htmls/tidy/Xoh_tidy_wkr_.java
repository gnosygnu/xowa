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
package gplx.xowa.htmls.core.htmls.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
public class Xoh_tidy_wkr_ {
	public static final byte Tid_null = 0, Tid_tidy = 1, Tid_jtidy = 2;
	public static final    Xoh_tidy_wkr Wkr_null = new Xoh_tidy_wkr_null();
}
class Xoh_tidy_wkr_null implements Xoh_tidy_wkr {
	public byte Tid() {return Xoh_tidy_wkr_.Tid_null;}
	public void Indent_(boolean v) {}
	public void Init_by_app(Xoae_app app) {}
	public void Exec_tidy(Bry_bfr bfr, byte[] page_url) {}
}
