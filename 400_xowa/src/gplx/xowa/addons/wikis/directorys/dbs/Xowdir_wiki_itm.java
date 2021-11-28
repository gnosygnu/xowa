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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
public class Xowdir_wiki_itm {
	public Xowdir_wiki_itm(int id, String domain, Io_url url, Xowdir_wiki_json json) {
		this.id = id;
		this.domain = domain;
		this.url = url;
		this.json = json;
	}
	public int Id() {return id;} private final int id;
	public String Domain() {return domain;} private final String domain;
	public Io_url Url() {return url;} private final Io_url url;
	public Xowdir_wiki_json Json() {return json;} private final Xowdir_wiki_json json;
}
