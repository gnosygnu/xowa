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
import gplx.xowa.guis.views.*;
public class Xopg_tab_data {
	public Xog_tab_itm	Tab()				{return tab;}			public void Tab_(Xog_tab_itm v) {this.tab = v;} private Xog_tab_itm tab;
	public boolean			Cancel_show()		{return cancel_show;}	public void Cancel_show_y_()	{this.cancel_show = true;} private boolean cancel_show;	// used for Special:Search
	public Xog_tab_close_mgr Close_mgr() {return close_mgr;} private final    Xog_tab_close_mgr close_mgr = new Xog_tab_close_mgr();
	public void Clear() {
		this.cancel_show = false;
		this.tab = null;
		close_mgr.Clear();
	}
}
