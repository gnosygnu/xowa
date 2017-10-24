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
package gplx.xowa.addons.apps.updates.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.langs.mustaches.*;
class Xoa_update_itm__root extends Xoa_update_itm__leaf { 	private final    String current_version, current_date, check_date;
	private final    boolean web_access_enabled;
	private Xoa_update_itm__leaf[] itms = Xoa_update_itm__leaf.Ary__empty;
	public Xoa_update_itm__root
		( String current_version, String current_date, String check_date, boolean web_access_enabled
		, String version, String date, int priority, String summary, String details, String package_url
		) {super(version, date, priority, summary, details, package_url);
		this.current_version = current_version;
		this.current_date = current_date;
		this.check_date = check_date;
		this.web_access_enabled = web_access_enabled;
	}
	public void Itms_(Xoa_update_itm__leaf[] v) {
		this.itms = v;
	}
	@Override public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "current_version"))		bfr.Add_str_u8(current_version);
		else if	(String_.Eq(k, "current_date"))			bfr.Add_str_u8(current_date);
		else if	(String_.Eq(k, "check_date"))			bfr.Add_str_u8(check_date);
		return super.Mustache__write (k, bfr);
	}
	@Override public Mustache_doc_itm[] Mustache__subs(String k) {
		if		(String_.Eq(k, "itms"))					return itms;
		else if	(String_.Eq(k, "itms_exist"))			return Mustache_doc_itm_.Ary__bool(itms.length > 0);
		else if	(String_.Eq(k, "web_access_enabled"))	return Mustache_doc_itm_.Ary__bool(web_access_enabled);
		return super.Mustache__subs(k);
	}
}
