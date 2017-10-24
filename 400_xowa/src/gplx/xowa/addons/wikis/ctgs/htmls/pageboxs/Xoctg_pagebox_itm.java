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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import gplx.xowa.wikis.pages.wtxts.*;
public class Xoctg_pagebox_itm {
	public Xoctg_pagebox_itm(Xoa_ttl ttl) {
		this.ttl = ttl;
	}
	public Xoa_ttl		Ttl()		{return ttl;} private final    Xoa_ttl ttl;
	public int			Id()		{return id;} private int id;
	public DateAdp		Timestamp() {return timestamp;} private DateAdp timestamp;
	public boolean			Hidden()	{return hidden;} private boolean hidden;
	public int			Count__subcs()	{return count__subcs;} private int count__subcs;
	public int			Count__pages()	{return count__pages;} private int count__pages;
	public int			Count__files()	{return count__files;} private int count__files;
	public int			Count__all()    {return count__all;} private int count__all;

	public void Load_by_db(int id, DateAdp timestamp) {
		this.id = id; this.timestamp = timestamp;
	}
	public void Load_by_cat_core(boolean hidden, int count__subcs, int count__pages, int count__files) {
		this.hidden = hidden;
		this.count__subcs = count__subcs; this.count__pages = count__pages; this.count__files = count__files;
		this.count__all = count__subcs + count__pages + count__files;
	}

	public static Xoctg_pagebox_itm New_by_ttl(Xoa_ttl ttl) {return new Xoctg_pagebox_itm(ttl);}
	public static Xoctg_pagebox_itm[] New_ary(Xoa_page pg) {
		int len = pg.Wtxt().Ctgs__len();
		Xoctg_pagebox_itm[] rv = new Xoctg_pagebox_itm[len];
		for (int i = 0; i < len; ++i)
			rv[i] = New_by_ttl(pg.Wtxt().Ctgs__get_at(i));
		return rv;
	}
}
