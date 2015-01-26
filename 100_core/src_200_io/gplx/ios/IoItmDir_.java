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
package gplx.ios; import gplx.*;
public class IoItmDir_ {
	public static IoItmDir as_(Object obj) {return obj instanceof IoItmDir ? (IoItmDir)obj : null;}
	public static final IoItmDir Null = null_();
	public static IoItmDir top_(Io_url url) {return scan_(url);}
	public static IoItmDir scan_(Io_url url) {
		IoItmDir rv = new IoItmDir(url.Info().CaseSensitive());
		rv.ctor_IoItmBase_url(url);
		return rv;
	}
	public static IoItmDir sub_(String name) {
		IoItmDir rv = new IoItmDir(Bool_.Y);
		rv.ctor_IoItmBase_url(Io_url_.mem_dir_("mem/" + name));
		return rv;
	}
	static IoItmDir null_() {
		IoItmDir rv = new IoItmDir(true);	// TODO: NULL should be removed
		rv.ctor_IoItmBase_url(Io_url_.Null);
		rv.Exists_set(false);
		return rv;
	}
	public static void Make(IoItmDir dir) {
		Io_mgr._.CreateDir(dir.Url());
		int len = dir.SubDirs().Count();
		for (int i = 0; i < len; ++i) {
			IoItmDir sub_dir = (IoItmDir)dir.SubDirs().FetchAt(i);
			Make(sub_dir);
		}
		len = dir.SubFils().Count();
		for (int i = 0; i < len; ++i) {
			IoItmFil sub_fil = (IoItmFil)dir.SubFils().FetchAt(i);
			String text = String_.Repeat("a", (int)sub_fil.Size());
			Io_url sub_url = sub_fil.Url();
			Io_mgr._.SaveFilStr(sub_url, text);
			Io_mgr._.UpdateFilModifiedTime(sub_url, sub_fil.ModifiedTime());
		}
	}
}
