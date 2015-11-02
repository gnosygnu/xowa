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
package gplx.xowa.specials.xowa.file_browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.ios.*;
class Xosp_fbrow_data_dir {
	private final Ordered_hash hash = Ordered_hash_.New();
	public Xosp_fbrow_data_dir(Io_url url) {this.url = url;}
	public Io_url Url() {return url;} private final Io_url url;
	public int Count() {return hash.Count();}
	public Xosp_fbrow_data_sub Get_at(int i) {return (Xosp_fbrow_data_sub)hash.Get_at(i);}
	public void Add(Xosp_fbrow_data_sub itm) {hash.Add(itm.Url().NameAndExt(), itm);}
	public static Xosp_fbrow_data_dir new_(IoItmDir owner) {
		Xosp_fbrow_data_dir rv = new Xosp_fbrow_data_dir(owner.Url());
		new_subs(rv, Bool_.Y, owner.SubDirs());
		new_subs(rv, Bool_.N, owner.SubFils());
		return rv;
	}
	private static void new_subs(Xosp_fbrow_data_dir owner, boolean dir, IoItmList itms) {
		int len = itms.Count();
		for (int i = 0; i < len; ++i) {
			IoItm_base src = (IoItm_base)itms.Get_at(i);
			Io_url src_url = src.Url();
			if (String_.Has_at_bgn(src_url.NameAndExt(), ".")) continue;	// ignore hidden "." files; NameAndExt() b/c ".ext" has NameOnly of ""; EX: "/dir/.hidden"
			Xosp_fbrow_data_sub trg = null;
			if (dir)
				trg = new Xosp_fbrow_data_sub(src_url, -1, null);
			else {
				IoItmFil fil = (IoItmFil)src;
				trg = new Xosp_fbrow_data_sub(src_url, fil.Size(), fil.ModifiedTime());
			}
			owner.Add(trg);
		}
	}
}
class Xosp_fbrow_data_sub {
	public Xosp_fbrow_data_sub(Io_url url, long size, DateAdp modified) {
		this.url = url; this.size = size; this.modified = modified;
		this.tid_is_dir = url.Type_dir();
	}
	public boolean Selectable() {return selectable;} public void Selectable_(boolean v) {selectable = v;} private boolean selectable = true;
	public String Selectable_style() {return selectable ? "" : "none";}
	public boolean Tid_is_dir() {return tid_is_dir;} private final boolean tid_is_dir;
	public Io_url Url() {return url;} private final Io_url url;
	public long Size() {return size;} private final long size;
	public DateAdp Modified() {return modified;} private final DateAdp modified;
}
