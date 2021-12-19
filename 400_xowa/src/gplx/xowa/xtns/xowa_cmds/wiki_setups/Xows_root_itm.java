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
package gplx.xowa.xtns.xowa_cmds.wiki_setups;
import gplx.types.commons.lists.ComparerAble;
import gplx.langs.mustaches.*;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
class Xows_root_itm implements Mustache_doc_itm {
	private final byte[] language, url_list;
	private final Xows_wiki_itm[] wiki_ary;
	public Xows_root_itm(byte[] language, byte[] url_list, Xows_wiki_itm[] wiki_ary) {
		this.language = language;
		this.url_list = url_list;
		this.wiki_ary = wiki_ary;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(StringUtl.Eq(key, "language"))			bfr.Add_bry(language);
		else if	(StringUtl.Eq(key, "url_list"))			bfr.Add_bry(url_list);
		else if	(StringUtl.Eq(key, "wikis__1st"))			bfr.Add_bry(wiki_ary[0].Wiki_domain());
		else											return false;
		return false;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(StringUtl.Eq(key, "wikis"))				return wiki_ary;
		return Mustache_doc_itm_.Ary__empty;
	}
}
class Xows_wiki_itm implements Mustache_doc_itm {
	private final Xows_task_itm[] task_ary;
	public Xows_wiki_itm(byte[] wiki_domain, Xows_task_itm[] task_ary) {
		this.wiki_domain = wiki_domain;
		this.task_ary = task_ary;
	}
	public byte[] Wiki_domain() {return wiki_domain;} private final byte[] wiki_domain;
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(StringUtl.Eq(key, "wiki_domain"))		bfr.Add_bry(wiki_domain);
		else											return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(StringUtl.Eq(key, "tasks"))				return task_ary;
		return Mustache_doc_itm_.Ary__empty;
	}
}
class Xows_task_itm implements Mustache_doc_itm {
	private final byte[] wiki_domain, task_full_name, task_name, task_date;
	private final Xows_file_itm[] file_ary;
	public Xows_task_itm(int seqn_id, byte[] wiki_domain, byte[] task_full_name, byte[] task_name, byte[] task_date, Xows_file_itm[] file_ary) {
		this.seqn_id = seqn_id;
		this.wiki_domain = wiki_domain; this.task_full_name = task_full_name; this.task_name = task_name; this.task_date = task_date;
		this.file_ary = file_ary;
	}
	public int Seqn_id() {return seqn_id;} private final int seqn_id;
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(StringUtl.Eq(key, "wiki_domain"))		bfr.Add_bry(wiki_domain);
		else if	(StringUtl.Eq(key, "task_name"))			bfr.Add_bry(task_name);
		else if	(StringUtl.Eq(key, "task_full_name"))		bfr.Add_bry(task_full_name);
		else if	(StringUtl.Eq(key, "task_date"))			bfr.Add_bry(task_date);
		else											return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(StringUtl.Eq(key, "files"))				return file_ary;
		return Mustache_doc_itm_.Ary__empty;
	}
}
class Xows_file_itm implements Mustache_doc_itm {
	private final byte[] file_href;
	public Xows_file_itm(int step_id, byte[] file_href) {
		this.step_id = step_id;
		this.file_href = file_href;
	}
	public int Step_id() {return step_id;} private final int step_id;
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(StringUtl.Eq(key, "file_href"))			bfr.Add_bry(file_href);
		else											return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}
}
class Xows_task_itm_sorter implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xows_task_itm lhs = (Xows_task_itm)lhsObj;
		Xows_task_itm rhs = (Xows_task_itm)rhsObj;
		return IntUtl.Compare(lhs.Seqn_id(), rhs.Seqn_id());
	}
}
class Xows_file_itm_sorter implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xows_file_itm lhs = (Xows_file_itm)lhsObj;
		Xows_file_itm rhs = (Xows_file_itm)rhsObj;
		return IntUtl.Compare(lhs.Step_id(), rhs.Step_id());
	}
}
