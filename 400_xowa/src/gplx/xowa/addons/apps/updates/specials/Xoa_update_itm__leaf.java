/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
