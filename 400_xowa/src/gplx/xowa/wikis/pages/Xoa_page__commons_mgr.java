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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xoa_page__commons_mgr {
	public boolean			Xowa_mockup() {return xowa_mockup;} public void Xowa_mockup_(boolean v) {xowa_mockup = v;} private boolean xowa_mockup;
	public Xow_wiki		Source_wiki() {return source_wiki;} public void Source_wiki_(Xow_wiki v) {source_wiki = v;} private Xow_wiki source_wiki;
	public Xow_wiki		Source_wiki_or(Xow_wiki or) {return source_wiki == null ? or : source_wiki;}
	public void Clear() {
		this.xowa_mockup = false;
		this.source_wiki = null;
	}
}
