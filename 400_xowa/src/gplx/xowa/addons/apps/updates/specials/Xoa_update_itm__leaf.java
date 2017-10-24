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
import gplx.xowa.addons.apps.updates.dbs.*;
class Xoa_update_itm__leaf implements Mustache_doc_itm {
	private final    String version, date, summary, details, package_url;
	private final    int priority;
	public Xoa_update_itm__leaf(String version, String date, int priority, String summary, String details, String package_url) {
		this.version = version;
		this.date = date;
		this.priority = priority;
		this.summary = summary;
		this.details = details;
		this.package_url = package_url;
	}
	@gplx.Virtual public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "version"))			bfr.Add_str_u8(version);
		else if	(String_.Eq(k, "date"))				bfr.Add_str_u8(date);
		else if	(String_.Eq(k, "priority"))			bfr.Add_str_u8(Xoa_app_version_itm.Priority__to_name(priority));
		else if	(String_.Eq(k, "summary"))			bfr.Add_str_u8(summary);
		else if	(String_.Eq(k, "details"))			bfr.Add_str_u8(details);
		else if	(String_.Eq(k, "package_url"))		bfr.Add_str_u8(package_url);
		return true;
	}
	@gplx.Virtual public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "priority_is_major"))		return Mustache_doc_itm_.Ary__bool(priority >= Xoa_app_version_itm.Priority__major);
		return Mustache_doc_itm_.Ary__empty;
	}
	
	protected static final    Xoa_update_itm__leaf[] Ary__empty = new Xoa_update_itm__leaf[0];
}
