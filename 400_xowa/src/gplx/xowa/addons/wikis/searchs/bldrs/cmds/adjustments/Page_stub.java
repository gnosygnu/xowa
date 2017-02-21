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
package gplx.xowa.addons.wikis.searchs.bldrs.cmds.adjustments; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.bldrs.*; import gplx.xowa.addons.wikis.searchs.bldrs.cmds.*;
class Page_stub implements CompareAble {
	public Page_stub(int id, boolean is_redirect, int len, int score) {
		this.Id = id;
		this.Is_redirect = is_redirect;
		this.Len = len;
		this.Score = score;
	}
	public final    int Id;
	public final    boolean Is_redirect;
	public final    int Len;
	public final    int Score;

	public int compareTo(Object obj) {
		Page_stub comp = (Page_stub)obj;
		// sort redirects and small pages to bottom
		int is_redirect_compare = -Bool_.Compare(Is_redirect, comp.Is_redirect);
		if (is_redirect_compare == CompareAble_.Same)
			return Int_.Compare(Len, comp.Len);
		else
			return is_redirect_compare;
	}
}
