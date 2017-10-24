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
import gplx.core.consoles.*; import gplx.core.criterias.*;
import gplx.core.ios.streams.*;
public interface IoEngine {
	String		Key();
	boolean		ExistsFil_api(Io_url url);
	void		SaveFilText_api(IoEngine_xrg_saveFilStr args);
	String		LoadFilStr(IoEngine_xrg_loadFilStr args);
	void		DeleteFil_api(IoEngine_xrg_deleteFil args);
	void		CopyFil(IoEngine_xrg_xferFil args);
	void		MoveFil(IoEngine_xrg_xferFil args);
	IoItmFil	QueryFil(Io_url url);
	void		UpdateFilAttrib(Io_url url, IoItmAttrib atr); // will fail if file does not exists
	void		UpdateFilModifiedTime(Io_url url, DateAdp modified);
	IoStream	OpenStreamRead(Io_url url);
	IoStream	OpenStreamWrite(IoEngine_xrg_openWrite args);
	void		XferFil(IoEngine_xrg_xferFil args);
	void		RecycleFil(IoEngine_xrg_recycleFil xrg);
	boolean		Truncate_fil(Io_url url, long size);

	boolean		ExistsDir(Io_url url);
	void		CreateDir(Io_url url); // creates all folder levels (EX: C:\a\b\c\ will create C:\a\ and C:\a\b\). will not fail if called on already existing folders.
	void		DeleteDir(Io_url url);
	void		MoveDir(Io_url src, Io_url trg);		// will fail if trg exists
	void		CopyDir(Io_url src, Io_url trg);
	IoItmDir	QueryDir(Io_url url);

	void		DeleteDirDeep(IoEngine_xrg_deleteDir args);
	void		MoveDirDeep(IoEngine_xrg_xferDir args);		// will fail if trg exists
	IoItmDir	QueryDirDeep(IoEngine_xrg_queryDir args);
	void		XferDir(IoEngine_xrg_xferDir args);
	boolean		DownloadFil(IoEngine_xrg_downloadFil xrg);
	Io_stream_rdr DownloadFil_as_rdr(IoEngine_xrg_downloadFil xrg);
}
class IoEngineUtl {
	public int BufferLength() {return bufferLength;} public void BufferLength_set(int v) {bufferLength = v;} int bufferLength = 4096; // 0x1000	
	public void DeleteRecycleGplx(IoEngine engine, IoEngine_xrg_recycleFil xrg) {
		Io_url recycleUrl = xrg.RecycleUrl();
		if (recycleUrl.Type_fil())
			engine.MoveFil(IoEngine_xrg_xferFil.move_(xrg.Url(), recycleUrl).Overwrite_(false).ReadOnlyFails_(true));
		else
			engine.MoveDirDeep(IoEngine_xrg_xferDir.move_(xrg.Url(), recycleUrl).Overwrite_(false).ReadOnlyFails_(true));
	}
	public void DeleteDirDeep(IoEngine engine, Io_url dirUrl, IoEngine_xrg_deleteDir args) {
		Console_adp usrDlg = args.UsrDlg();
		IoItmDir dir = engine.QueryDir(dirUrl); if (!dir.Exists()) return;
		for (Object subDirObj : dir.SubDirs()) {
			IoItmDir subDir = (IoItmDir)subDirObj;
			if (!args.SubDirScanCrt().Matches(subDir)) continue;
			if (args.Recur()) DeleteDirDeep(engine, subDir.Url(), args);
		}
		for (Object subFilObj : dir.SubFils()) {
			IoItmFil subFil = (IoItmFil)subFilObj;
			if (!args.MatchCrt().Matches(subFil)) continue;
			Io_url subFilUrl = subFil.Url();
			try {engine.DeleteFil_api(IoEngine_xrg_deleteFil.new_(subFilUrl).ReadOnlyFails_(args.ReadOnlyFails()));}
			catch (Exception exc) {usrDlg.Write_fmt_w_nl(Err_.Message_lang(exc));}
		}
		// all subs deleted; now delete dir
		if (!args.MatchCrt().Matches(dir)) return;
		try {engine.DeleteDir(dir.Url());}
		catch (Exception exc) {usrDlg.Write_fmt_w_nl(Err_.Message_lang(exc));}
	}
	public void XferDir(IoEngine srcEngine, Io_url src, IoEngine trgEngine, Io_url trg, IoEngine_xrg_xferDir args) {
		trgEngine.CreateDir(trg);
		IoItmDir srcDir = QueryDirDeep(srcEngine, IoEngine_xrg_queryDir.new_(src).Recur_(false));
		for (Object subSrcObj : srcDir.SubDirs()) {
			IoItmDir subSrc = (IoItmDir)subSrcObj;
			if (!args.SubDirScanCrt().Matches(subSrc)) continue;
			if (!args.MatchCrt().Matches(subSrc)) continue;
			Io_url subTrg = trg.GenSubDir_nest(subSrc.Url().NameOnly());	//EX: C:\abc\def\ -> C:\123\ + def\
			if (args.Recur()) XferDir(srcEngine, subSrc.Url(), trgEngine, subTrg, args);
		}
		IoItmList srcFils = IoItmList.list_(src.Info().CaseSensitive());
		for (Object srcFilObj : srcDir.SubFils()) {
			IoItmFil srcFil = (IoItmFil)srcFilObj;
			if (args.MatchCrt().Matches(srcFil)) srcFils.Add(srcFil);
		}
		for (Object srcFilObj : srcFils) {
			IoItmFil srcFil = (IoItmFil)srcFilObj;
			Io_url srcFilPath = srcFil.Url();
			Io_url trgFilPath = trg.GenSubFil(srcFilPath.NameAndExt());	//EX: C:\abc\fil.txt -> C:\123\ + fil.txt
			IoEngine_xrg_xferFil xferArgs = args.Type_move() ? IoEngine_xrg_xferFil.move_(srcFilPath, trgFilPath).Overwrite_(args.Overwrite()) : IoEngine_xrg_xferFil.copy_(srcFilPath, trgFilPath).Overwrite_(args.Overwrite());
			XferFil(srcEngine, xferArgs);
		}
		if (args.Type_move()) srcEngine.DeleteDirDeep(IoEngine_xrg_deleteDir.new_(src).Recur_(args.Recur()).ReadOnlyFails_(args.ReadOnlyFails()));// this.DeleteDirDeep(srcEngine, src, IoEngine_xrg_deleteItm.new_(src).Recur_(args.Recur()).ReadOnlyIgnored_(args.ReadOnlyIgnored()));
	}
	public void XferFil(IoEngine srcEngine, IoEngine_xrg_xferFil args) {
		Io_url src = args.Src(), trg = args.Trg();
		if (String_.Eq(srcEngine.Key(), trg.Info().EngineKey())) {
			if (args.Type_move())
				srcEngine.MoveFil(args);
			else
				srcEngine.CopyFil(args);
		}
		else {
			TransferStream(src, trg);
			if (args.Type_move()) srcEngine.DeleteFil_api(IoEngine_xrg_deleteFil.new_(src));
		}
	}
	public IoItmDir QueryDirDeep(IoEngine engine, IoEngine_xrg_queryDir args) {
		IoItmDir rv = IoItmDir_.top_(args.Url());
		rv.Exists_set(QueryDirDeepCore(rv, args.Url(), engine, args.Recur(), args.SubDirScanCrt(), args.DirCrt(), args.FilCrt(), args.UsrDlg(), args.DirInclude()));
		return rv;
	}
	static boolean QueryDirDeepCore(IoItmDir ownerDir, Io_url url, IoEngine engine, boolean recur, Criteria subDirScanCrt, Criteria dirCrt, Criteria filCrt, Console_adp usrDlg, boolean dirInclude) {
		if (usrDlg.Canceled_chk()) return false;
		if (usrDlg.Enabled()) usrDlg.Write_tmp(String_.Concat("scan: ", url.Raw()));
		IoItmDir scanDir = engine.QueryDir(url);
		for (Object subDirObj : scanDir.SubDirs()) {
			IoItmDir subDir = (IoItmDir)subDirObj;
			if (!subDirScanCrt.Matches(subDir)) continue;
			if (dirCrt.Matches(subDir)) {
				ownerDir.SubDirs().Add(subDir);		// NOTE: always add subDir; do not use dirCrt here, else its subFils will be added to non-existent subDir
			}
			if (recur)
				QueryDirDeepCore(subDir, subDir.Url(), engine, recur, subDirScanCrt, dirCrt, filCrt, usrDlg, dirInclude);
		}
		for (Object subFilObj : scanDir.SubFils()) {
			IoItmFil subFil = (IoItmFil)subFilObj;
			if (filCrt.Matches(subFil)) ownerDir.SubFils().Add(subFil);
		}
		return scanDir.Exists();
	}
	void TransferStream(Io_url src, Io_url trg) {
		IoStream srcStream = null;
		IoStream trgStream = null;
		try {
			srcStream = IoEnginePool.Instance.Get_by(src.Info().EngineKey()).OpenStreamRead(src);
			trgStream = IoEngine_xrg_openWrite.new_(trg).Exec();
			srcStream.Transfer(trgStream, bufferLength);
		}
		finally {
			if (srcStream != null) srcStream.Rls();
			if (trgStream != null) trgStream.Rls();
		}
	}
	public static IoEngineUtl new_() {return new IoEngineUtl();} IoEngineUtl() {}
}
