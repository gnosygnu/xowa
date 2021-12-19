/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios;
import gplx.core.caches.Lru_cache;
import gplx.core.consoles.Console_adp;
import gplx.core.criterias.Criteria;
import gplx.core.envs.Op_sys;
import gplx.core.ios.atrs.Io_itm_atr_req;
import gplx.core.ios.streams.IoStream;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class IoEngineUtl {
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
			catch (Exception exc) {usrDlg.Write_fmt_w_nl(ErrUtl.Message(exc));}
		}
		// all subs deleted; now delete dir
		if (!args.MatchCrt().Matches(dir)) return;
		try {engine.DeleteDir(dir.Url());}
		catch (Exception exc) {usrDlg.Write_fmt_w_nl(ErrUtl.Message(exc));}
	}
	public void XferDir(IoEngine srcEngine, Io_url src, IoEngine trgEngine, Io_url trg, IoEngine_xrg_xferDir args) {
		trgEngine.CreateDir(trg);
		IoItmDir srcDir = QueryDirDeep(srcEngine, IoEngine_xrg_queryDir.new_(src).Recur_(false));
		for (Object subSrcObj : srcDir.SubDirs()) {
			IoItmDir subSrc = (IoItmDir)subSrcObj;
			if (!args.SubDirScanCrt().Matches(subSrc)) continue;
			if (!args.MatchCrt().Matches(subSrc)) continue;
			Io_url subTrg = trg.GenSubDir_nest(subSrc.Url().NameOnly());    //EX: C:\abc\def\ -> C:\123\ + def\
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
			Io_url trgFilPath = trg.GenSubFil(srcFilPath.NameAndExt());    //EX: C:\abc\fil.txt -> C:\123\ + fil.txt
			IoEngine_xrg_xferFil xferArgs = args.Type_move() ? IoEngine_xrg_xferFil.move_(srcFilPath, trgFilPath).Overwrite_(args.Overwrite()) : IoEngine_xrg_xferFil.copy_(srcFilPath, trgFilPath).Overwrite_(args.Overwrite());
			XferFil(srcEngine, xferArgs);
		}
		if (args.Type_move()) srcEngine.DeleteDirDeep(IoEngine_xrg_deleteDir.new_(src).Recur_(args.Recur()).ReadOnlyFails_(args.ReadOnlyFails()));// this.DeleteDirDeep(srcEngine, src, IoEngine_xrg_deleteItm.new_(src).Recur_(args.Recur()).ReadOnlyIgnored_(args.ReadOnlyIgnored()));
	}
	public void XferFil(IoEngine srcEngine, IoEngine_xrg_xferFil args) {
		Io_url src = args.Src(), trg = args.Trg();
		if (StringUtl.Eq(srcEngine.Key(), trg.Info().EngineKey())) {
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
		if (usrDlg.Enabled()) usrDlg.Write_tmp(StringUtl.Concat("scan: ", url.Raw()));
		IoItmDir scanDir = engine.QueryDir(url);
		for (Object subDirObj : scanDir.SubDirs()) {
			IoItmDir subDir = (IoItmDir)subDirObj;
			if (!subDirScanCrt.Matches(subDir)) continue;
			if (dirCrt.Matches(subDir)) {
				ownerDir.SubDirs().Add(subDir);        // NOTE: always add subDir; do not use dirCrt here, else its subFils will be added to non-existent subDir
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
	private static final Lru_cache Dir_cache = new Lru_cache(BoolUtl.Y, "gplx.ios.dir_cache", 128, 256);
	public static boolean Query_read_only(IoEngine engine, Io_url url, int read_only_type) {
		switch (read_only_type) {
			case Io_mgr.Read_only__basic__file:
				return engine.QueryFil(url).Attrib().ReadOnly();
			case Io_mgr.Read_only__basic__file_and_dirs:
				if (Op_sys.Cur().Tid_is_wnt()) // only examine owner_dirs if wnt
					return Query_read_only__file_and_dirs(engine, url);
				else
					return engine.QueryFil(url).Attrib().ReadOnly();
			case Io_mgr.Read_only__perms__file:
				return engine.Query_itm_atrs(url, Io_itm_atr_req.New__read_only()).Is_read_only();
			default:
				throw ErrUtl.NewUnhandled(read_only_type);
		}
	}
	private static boolean Query_read_only__file_and_dirs(IoEngine engine, Io_url url) {
		// if fil
		if (url.Type_fil()) {
			IoItmFil fil = engine.QueryFil(url);
			// if read-only, return true
			if (fil.ReadOnly())
				return true;
			// else, set to owner dir
			else
				url = url.OwnerDir();
		}

		// loop until top
		while (url != Io_url_.Empty) {
			String dir_key = url.Raw();

			// check cache first
			IoItmDir dir = (IoItmDir)Dir_cache.Get_or_null(dir_key);
			if (dir == null) {
				// not in cache; query file_system
				dir = engine.QueryDir(url);
				Dir_cache.Set(dir_key, dir, 1);
			}

			// if read-only, return true
			if (dir.ReadOnly())
				return true;
			// else, set to owner dir
			else
				url = url.OwnerDir();
		}

		return false;
	}

	public static IoEngineUtl new_() {return new IoEngineUtl();} IoEngineUtl() {}
}
