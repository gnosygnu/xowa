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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xoud_site_row {
	public Xoud_site_row(int id, int priority, String domain, String name, String path, String date, String xtn) {
		this.id = id; this.priority = priority; this.domain = domain; this.name = name; this.path = path; this.date = date; this.xtn = xtn;
	}
	public int			Id()			{return id;} private final    int id;
	public int			Priority()		{return priority;} private final    int priority;
	public String		Domain()		{return domain;} private final    String domain;
	public String		Name()			{return name;} private final    String name;
	public String		Path()			{return path;} private final    String path;
	public String		Date()			{return date;} private String date; public void Date_(String v) {this.date = v;}
	public String		Xtn()			{return xtn;} private String xtn;
}
