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
package gplx.xowa.bldrs.wiki_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xoi_wiki_props_ns {
	public int Id() {return id;} private int id;
	public boolean Subpages_enabled() {return subpages_enabled;} private boolean subpages_enabled;
	public Xoi_wiki_props_ns Init_by_ctor(int id, boolean subpages_enabled) {this.id = id; this.subpages_enabled = subpages_enabled; return this;}
	public void Init_by_xml(gplx.langs.xmls.XmlNde ns_nde) {
		this.id = Int_.parse(ns_nde.Atrs().FetchValOr("id", "-1"));
		this.subpages_enabled = ns_nde.Atrs().Fetch_or_null("subpages") != null;// per api, subpages="" means ns has subpages; no subpages attribute means no subpages
	}
}
