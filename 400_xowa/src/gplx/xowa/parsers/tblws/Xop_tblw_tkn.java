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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public interface Xop_tblw_tkn extends Xop_tkn_itm {
	int Tblw_tid();
	boolean Tblw_xml();
	int Tblw_subs_len(); void Tblw_subs_len_add_();
	int Atrs_bgn();
	int Atrs_end();
	void Atrs_rng_set(int bgn, int end);
	Mwh_atr_itm[] Atrs_ary(); Xop_tblw_tkn Atrs_ary_as_tblw_(Mwh_atr_itm[] v);
}
