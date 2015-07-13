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
import gplx.core.criterias.*;
public class IoItmDir extends IoItm_base {
	public boolean Exists() {return exists;} public void Exists_set(boolean v) {exists = v;} private boolean exists = true;
	@Override public int TypeId() {return Type_Dir;} @Override public boolean Type_dir() {return true;} @Override public boolean Type_fil() {return false;} public static final int Type_Dir = 1;
	@gplx.New public IoItmDir XtnProps_set(String key, Object val) {return (IoItmDir)super.XtnProps_set(key, val);}
	public IoItmList SubDirs() {return subDirs;} IoItmList subDirs;
	public IoItmList SubFils() {return subFils;} IoItmList subFils;
	public IoItmHash XtoIoItmList(Criteria crt) {
		IoItmHash rv = IoItmHash.list_(this.Url());
		XtoItmList_recur(rv, this, crt);
		return rv;
	}
	Io_url[] XtoIoUrlAry() {
		IoItmHash list = this.XtoIoItmList(Criteria_.All);
//#plat_wce			list.Sort(); // NOTE: on wce, subFils retrieved in unexpected order; createTime vs pathString
		int count = list.Count();
		Io_url[] rv = new Io_url[count];
		for (int i = 0; i < count; i++)
			rv[i] = list.Get_at(i).Url();
		return rv;
	}
	public IoItmDir FetchDeepOrNull(Io_url findDirUrl) {
		String dirSpr = this.Url().Info().DirSpr(); int dirSprLen = String_.Len(dirSpr);
		String currDirStr = this.Url().Raw();
		String findDirStr = findDirUrl.Raw();
		if (!String_.Has_at_bgn(findDirStr, currDirStr)) return null;	// findUrl must start with currUrl;
		String findName = String_.DelEnd(currDirStr, dirSprLen);	// seed findName for String_.MidByLen below;
		IoItmDir curDir = this;			
		while (true) {
			findDirStr = String_.DelBgn(findDirStr, String_.Len(findName) + dirSprLen);	// NOTE: findName will never have trailingDirSpr; subDirs.Get_by() takes NameOnly; ex: "dir" not "dir\"
			int nextDirSprPos = String_.FindFwd(findDirStr, dirSpr); if (nextDirSprPos == String_.Find_none) nextDirSprPos = String_.Len(findDirStr);
			findName = String_.MidByLen(findDirStr, 0, nextDirSprPos);
			if (String_.Eq(findDirStr, "")) return curDir;	// findDirStr completely removed; all parts match; return curDir
			curDir = IoItmDir_.as_(curDir.subDirs.Get_by(findName)); // try to find dir
			if (curDir == null) return null;	// dir not found; exit; NOTE: if dir found, loop restarts; with curDir as either findDir, or owner of findDir
		}
	}
	void XtoItmList_recur(IoItmHash list, IoItmDir curDir, Criteria dirCrt) {
		for (Object subFilObj : curDir.SubFils()) {
			IoItmFil subFil = (IoItmFil)subFilObj;
			list.Add(subFil);
		}
		for (Object subDirObj : curDir.SubDirs()) {
			IoItmDir subDir = (IoItmDir)subDirObj;
			if (dirCrt.Matches(subDir)) list.Add(subDir);
			XtoItmList_recur(list, subDir, dirCrt);
		}
	}
	@gplx.Internal protected IoItmDir(boolean caseSensitive) {
		subDirs = IoItmList.new_(this, caseSensitive);
		subFils = IoItmList.new_(this, caseSensitive);
	}
}
