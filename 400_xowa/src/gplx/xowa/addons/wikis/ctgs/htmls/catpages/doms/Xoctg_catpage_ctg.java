/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
