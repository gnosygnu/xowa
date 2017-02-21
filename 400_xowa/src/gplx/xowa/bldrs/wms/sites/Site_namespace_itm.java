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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
class Site_namespace_itm implements To_str_able {
	public Site_namespace_itm(int id, byte[] case_tid, byte[] canonical, byte[] localized, boolean subpages, boolean content, byte[] defaultcontentmodel) {
		this.id = id; this.case_tid = case_tid; this.canonical = canonical; this.localized = localized;
		this.subpages = subpages; this.content = content; this.defaultcontentmodel = defaultcontentmodel;
	}
	public int Id() {return id;} private final int id;
	public byte[] Case_tid() {return case_tid;} private final byte[] case_tid;
	public byte[] Canonical() {return canonical;} private final byte[] canonical;
	public byte[] Localized() {return localized;} private final byte[] localized;
	public boolean Subpages() {return subpages;} private final boolean subpages;
	public boolean Content() {return content;} private final boolean content;
	public byte[] Defaultcontentmodel() {return defaultcontentmodel;} private final byte[] defaultcontentmodel;
	public String To_str() {
		return String_.Concat_with_obj("|", id, case_tid, canonical, localized, subpages, content, defaultcontentmodel);
	}
}
