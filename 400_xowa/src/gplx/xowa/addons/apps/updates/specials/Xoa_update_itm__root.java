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
class Xoa_update_itm__root extends Xoa_update_itm__leaf { 	private final    String current_version, current_date, major;
	private Xoa_update_itm__leaf[] itms = Xoa_update_itm__leaf.Ary__empty;
	public Xoa_update_itm__root
		( String current_version, String current_date, String major
		, String version, String date, String priority, String summary, String details
		) {super(version, date, priority, summary, details);
		this.current_version = current_version;
		this.current_date = current_date;
		this.major = major;
	}
	public void Itms_(Xoa_update_itm__leaf[] v) {
		this.itms = v;
	}
	@Override public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "current_version"))		bfr.Add_str_u8(current_version);
		else if	(String_.Eq(k, "current_date"))			bfr.Add_str_u8(current_date);
		else if	(String_.Eq(k, "major"))				bfr.Add_str_u8(major);
		return super.Mustache__write (k, bfr);
	}
	@Override public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "itms"))			return itms;
		return super.Mustache__subs(key);
	}
}
