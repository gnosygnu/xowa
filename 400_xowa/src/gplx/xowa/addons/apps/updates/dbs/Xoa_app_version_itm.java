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
