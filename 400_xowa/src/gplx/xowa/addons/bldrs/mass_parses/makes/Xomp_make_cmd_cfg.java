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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
public class Xomp_make_cmd_cfg implements Gfo_invk {
	public Hash_adp Mode() {return mode;} private Hash_adp mode = Hash_adp_.New().Add_and_more("html", "html").Add_and_more("lnki", "lnki").Add_and_more("stat", "stat");
	public boolean Delete_html_dbs() {return delete_html_dbs;} private boolean delete_html_dbs = true;
	public Ordered_hash Merger_wkrs() {return merger_wkrs;} private final    Ordered_hash merger_wkrs = Ordered_hash_.New();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__mode_))            mode = GfoMsg_.Read_str_ary_as_hash(m, "v");
		else if	(ctx.Match(k, Invk__delete_html_dbs_)) delete_html_dbs = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__merger_wkrs_)) {
			String[] ary = m.ReadStrAry("k", "|");
			for (String itm : ary)
				merger_wkrs.Add(itm, itm);
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__mode_ = "mode_", Invk__delete_html_dbs_ = "delete_html_dbs_", Invk__merger_wkrs_ = "merger_wkrs_";
}
