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
package gplx.xowa.addons.apps.updates.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
public class Xoa_app_version_itm {
	public Xoa_app_version_itm(int id, String name, String date, int priority, String url, String summary, String details) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.priority = priority;
		this.url = url;
		this.summary = summary;
		this.details = details;
	}
	public int Id() {return id;} private final    int id;
	public String Name() {return name;} private final    String name;
	public String Date() {return date;} private final    String date;
	public int Priority() {return priority;} private final    int priority;
	public String Url() {return url;} private final    String url;
	public String Summary() {return summary;} private final    String summary;
	public String Details() {return details;} private final    String details;
	public String Package_url() {
		String folder = url;
		if (String_.Len_eq_0(folder)) 
			folder = "https://github.com/gnosygnu/xowa/releases/releases/tag";
		return String_.Format("{0}/v{1}/xowa_app_{2}_v{1}.zip", folder, name, Xoa_app_.Op_sys_str);
	}

	public static final int Priority__major = 7, Priority__minor = 5, Priority__trivial = 3;
	public static String Priority__to_name(int v) {
		switch (v) {
			case Priority__trivial: return "trivial";
			case Priority__minor: return "minor";
			case Priority__major: return "major";
			default: throw Err_.new_unhandled_default(v);
		}
	}
}
