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
package gplx.xowa.addons.bldrs.centrals.tasks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.xowa.wikis.domains.*;
public class Xobc_task_key {
	public Xobc_task_key(String wiki_domain, String wiki_date, String task_type) {
		this.wiki_domain = wiki_domain;
		this.wiki_date = wiki_date;
		this.task_type = task_type;
	}
	public String Wiki_domain() {return wiki_domain;} private final    String wiki_domain;
	public String Wiki_date() {return wiki_date;} private final    String wiki_date;
	public String Wiki_date_ui() {return String_.Replace(wiki_date, ".", "-");}
	public String Task_type() {return task_type;} private final    String task_type;
	public String Task_type_ui() {
		if		(String_.Eq(task_type, "html"))		return "Articles";
		else if	(String_.Eq(task_type, "file"))		return "Images";
		else if	(String_.Eq(task_type, "text"))		return "Source";
		else if	(String_.Eq(task_type, "patch"))	return "Patch";
		else										return task_type;
	}
	public Xow_domain_itm Wiki_domain_itm() {return Xow_domain_itm_.parse(Bry_.new_u8(wiki_domain));}

	public static Xobc_task_key To_itm(String task_key) {
		String[] ary = String_.Split(task_key, "|");
		return new Xobc_task_key(ary[0], ary[1], ary[2]);
	}
	public static String To_str(String wiki_domain, String wiki_date, String task_type) {
		return String_.Concat(wiki_domain, "|", wiki_date, "|", task_type);
	}
}
