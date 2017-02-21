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
package gplx.core.ios; import gplx.*; import gplx.core.*;
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
