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
class Xoa_update_itm__root extends Xoa_update_itm__leaf { 	private final    String current_version, current_date, download_url, check_date;
	private final    boolean web_access_enabled;
	private Xoa_update_itm__leaf[] itms = Xoa_update_itm__leaf.Ary__empty;
	public Xoa_update_itm__root
		( String current_version, String current_date, String download_url, String check_date, boolean web_access_enabled
		, String version, String date, int priority, String summary, String details
		) {super(version, date, priority, summary, details);
		this.current_version = current_version;
		this.current_date = current_date;
		this.download_url = download_url;
		this.check_date = check_date;
		this.web_access_enabled = web_access_enabled;
	}
	public void Itms_(Xoa_update_itm__leaf[] v) {
		this.itms = v;
	}
	@Override public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "current_version"))		bfr.Add_str_u8(current_version);
		else if	(String_.Eq(k, "current_date"))			bfr.Add_str_u8(current_date);
		else if	(String_.Eq(k, "download_url"))			bfr.Add_str_u8(download_url);
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
