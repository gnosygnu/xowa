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
package gplx.xowa.addons.bldrs.xodirs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.langs.mustaches.*;
class Xobc_xodir_doc implements Mustache_doc_itm {
	private final    byte[] import_root, app_root_dir;
	private final    Xobc_xodir_dir[] dirs;
	public Xobc_xodir_doc(Xobc_xodir_dir[] dirs, byte[] import_root, byte[] app_root_dir) {
		this.dirs = dirs; this.import_root = import_root; this.app_root_dir = app_root_dir;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "import_root"))	bfr.Add_bry(import_root);
		else if	(String_.Eq(key, "app_root_dir"))	bfr.Add_bry(app_root_dir);
		return false;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "dirs"))			return dirs;
		return Mustache_doc_itm_.Ary__empty;
	}
}
public class Xobc_xodir_dir implements Mustache_doc_itm {
	private final    boolean is_selected, is_custom;
	private final    byte[] path;
	public Xobc_xodir_dir(boolean is_selected, boolean is_custom, byte[] path) {
		this.is_selected = is_selected; this.is_custom = is_custom; this.path = path;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "path"))			bfr.Add_bry(path);
		return false;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "is_selected"))	return Mustache_doc_itm_.Ary__bool(is_selected);
		else if	(String_.Eq(key, "is_custom"))		return Mustache_doc_itm_.Ary__bool(is_custom);
		return Mustache_doc_itm_.Ary__empty;
	}
}
