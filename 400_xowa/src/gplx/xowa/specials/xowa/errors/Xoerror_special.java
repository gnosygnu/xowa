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
package gplx.xowa.specials.xowa.errors;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.net.qargs.*;
public class Xoerror_special implements Xow_special_page {
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__error;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xoae_page page = (Xoae_page)pagei;
		Gfo_qarg_mgr qarg_mgr = new Gfo_qarg_mgr();
		qarg_mgr.Init(url.Qargs_ary());
		String type = qarg_mgr.Read_str_or("type", "unknown type");
		String data = qarg_mgr.Read_str_or("data", "unknown data");
		String title = type;
		String msg = data;
		if (StringUtl.Eq(type, "InvalidTitle")) {
			title = "Invalid title";
			msg = "The title has invalid characters: <span style='color:red;font-weight:bold'>" + data + "</span>";
		}
		else if (StringUtl.Eq(type, "InvalidWiki")) {
			title = "Invalid wiki";
			msg = "The wiki is not installed: <span style='color:red;font-weight:bold'>" + data + "</span>";
		}

		page.Db().Text().Text_bry_(fmt.Bld_many_to_bry(BryWtr.New(), title, msg));
	}
	private static final BryFmt fmt = BryFmt.Auto(StringUtl.ConcatLinesNlSkipLast
	( "<h2>~{title}</h2>"
	, "<p>~{msg}"
	, "</p>"
	));

	public Xow_special_page Special__clone() {return this;}
	public static byte[] Make_url__invalidTitle(byte[] ttl_bry) {
		return BryUtl.Add(Xow_special_meta_.Itm__error.Ttl_bry(), BryUtl.NewA7("?type=InvalidTitle&data="), ttl_bry);
	}
	public static byte[] Make_url__invalidWiki(byte[] wiki) {
		return BryUtl.Add(Xow_special_meta_.Itm__error.Ttl_bry(), BryUtl.NewA7("?type=InvalidWiki&data="), wiki);
	}
}
