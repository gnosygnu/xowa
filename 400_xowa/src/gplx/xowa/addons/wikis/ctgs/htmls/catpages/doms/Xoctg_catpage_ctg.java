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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
public class Xoctg_catpage_ctg {
	public Xoctg_catpage_ctg(int id, byte[] name) {
		this.id = id; this.name = name;
	}
	public int					Id()		{return id;} private final    int id;
	public byte[]				Name()		{return name;} private final    byte[] name;
	public Xoctg_catpage_grp	Subcs()		{return subcs;} private final    Xoctg_catpage_grp subcs = new Xoctg_catpage_grp(Xoa_ctg_mgr.Tid__subc);
	public Xoctg_catpage_grp	Pages()		{return pages;} private final    Xoctg_catpage_grp pages = new Xoctg_catpage_grp(Xoa_ctg_mgr.Tid__page);
	public Xoctg_catpage_grp	Files()		{return files;} private final    Xoctg_catpage_grp files = new Xoctg_catpage_grp(Xoa_ctg_mgr.Tid__file);
	public int					Total()		{return subcs.Count_all() + pages.Count_all() + files.Count_all();}
	public Xoctg_catpage_grp Grp_by_tid(byte tid) {
		switch (tid) {
			case Xoa_ctg_mgr.Tid__subc: return subcs;
			case Xoa_ctg_mgr.Tid__page: return pages;
			case Xoa_ctg_mgr.Tid__file: return files;
			default: throw Err_.new_unhandled(tid);
		}
	}
}
