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
import gplx.core.ios.streams.*;
public class IoEngine_memory extends IoEngine_base {
	@Override public String Key() {return key;} private String key = IoEngine_.MemKey;
	@Override public boolean ExistsFil_api(Io_url url) {return FetchFil(url) != IoItmFil_mem.Null;}
	@Override public void DeleteFil_api(IoEngine_xrg_deleteFil args) {
		Io_url url = args.Url();
		IoItmDir dir = FetchDir(url.OwnerDir()); if (dir == null) return; // url doesn't exist; just exit
		IoItmFil fil = IoItmFil_.as_(dir.SubFils().Get_by(url.NameAndExt()));
		if (fil != null && fil.ReadOnly() && args.ReadOnlyFails()) throw IoErr.FileIsReadOnly(url);
		dir.SubFils().Del(url);
	}
	void DeleteFil(Io_url url) {DeleteFil_api(IoEngine_xrg_deleteFil.new_(url));}
	@Override public void XferFil(IoEngine_xrg_xferFil args) {utl.XferFil(this, args);}
	@Override public void MoveFil(IoEngine_xrg_xferFil args) {
		Io_url src = args.Src(), trg = args.Trg(); boolean overwrite = args.Overwrite();
		if (String_.Eq(src.Xto_api(), trg.Xto_api())) throw Err_.new_wo_type("move failed; src is same as trg", "raw", src.Raw());
		CheckTransferArgs("move", src, trg, overwrite);
		if (overwrite) DeleteFil(trg);
		IoItmFil_mem curFil = FetchFil(src); curFil.Name_(trg.NameAndExt());
		AddFilToDir(trg.OwnerDir(), curFil);
		DeleteFil(src);
	}
	@Override public void CopyFil(IoEngine_xrg_xferFil args) {
		Io_url src = args.Src(), trg = args.Trg(); boolean overwrite = args.Overwrite();
		CheckTransferArgs("copy", src, trg, overwrite);
		if (overwrite) DeleteFil(trg);
		IoItmFil_mem srcFil = FetchFil(src);
		IoItmFil_mem curFil = srcFil.Clone(); curFil.Name_(trg.NameAndExt());
		AddFilToDir(trg.OwnerDir(), curFil);
	}
	@Override public IoItmDir QueryDirDeep(IoEngine_xrg_queryDir args) {return utl.QueryDirDeep(this, args);}
	@Override public void UpdateFilAttrib(Io_url url, IoItmAttrib atr)	{FetchFil(url).ReadOnly_(atr.ReadOnly());}
	@Override public void UpdateFilModifiedTime(Io_url url, DateAdp modified)	{FetchFil(url).ModifiedTime_(modified);}
	@Override public IoItmFil QueryFil(Io_url url)								{return FetchFil(url);}
	@Override public void SaveFilText_api(IoEngine_xrg_saveFilStr args) {
		Io_url url = args.Url();
		IoItmDir dir = FetchDir(url.OwnerDir());
		if (dir != null) {
			IoItmFil fil = IoItmFil_.as_(dir.SubFils().Get_by(url.NameAndExt()));
			if (fil != null && fil.ReadOnly()) throw IoErr.FileIsReadOnly(url);
		}

		if (args.Append())
			AppendFilStr(args);
		else
			SaveFilStr(args.Url(), args.Text());
	}
	@Override public boolean Truncate_fil(Io_url url, long size) {throw Err_.new_unimplemented();}
	@Override public String LoadFilStr(IoEngine_xrg_loadFilStr args) {
		return FetchFil(args.Url()).Text();
	}
	void SaveFilStr(Io_url url, String text) {
		DateAdp time = Datetime_now.Get();
		IoItmFil_mem fil = IoItmFil_mem.new_(url, String_.Len(text), time, text);
		AddFilToDir(url.OwnerDir(), fil);
	}		
	void AppendFilStr(IoEngine_xrg_saveFilStr args) {
		Io_url url = args.Url(); String text = args.Text();
		if (ExistsFil_api(url)) {
			IoItmFil_mem fil = FetchFil(url);
			fil.ModifiedTime_(Datetime_now.Get());
			fil.Text_set(fil.Text() + text);
		}
		else
			SaveFilStr(args.Url(), args.Text());
	}
	@Override public IoStream OpenStreamRead(Io_url url) {
		IoItmFil_mem fil = FetchFil(url);
		fil.Stream().Position_set(0);
		return fil.Stream();
	}
	@Override public IoStream OpenStreamWrite(IoEngine_xrg_openWrite args) {
		Io_url url = args.Url();
		IoItmFil_mem fil = FetchFil(url);
		if (fil == IoItmFil_mem.Null) {	// file doesn't exist; create new one
			SaveFilStr(url, "");
			fil = FetchFil(url);
		}
		else {
			if (args.Mode() == IoStream_.Mode_wtr_create)
				fil.Text_set("");			// NOTE: clear text b/c it still has pointer to existing stream
		}
		return fil.Stream();
	}

	@Override public boolean ExistsDir(Io_url url) {return FetchDir(url) != null;}
	@Override public void CreateDir(Io_url url) {
		IoItmDir dir = FetchDir(url); if (dir != null) return;	// dir exists; exit
		dir = IoItmDir_.top_(url);
		dirs.Add(dir);
		IoItmDir ownerDir = FetchDir(url.OwnerDir());
		if (ownerDir == null && !url.OwnerDir().Eq(Io_url_.Empty)) {	// no owner dir && not "driveDir" -> create
			CreateDir(url.OwnerDir());	// recursive
			ownerDir = FetchDir(url.OwnerDir());
		}
		if (ownerDir != null)
			ownerDir.SubDirs().Add(dir);
	}
	@Override public void DeleteDir(Io_url url) {
		FetchDir(url); // force creation if exists?
		dirs.Del(url);
		IoItmDir ownerDir = FetchDir(url.OwnerDir()); if (ownerDir == null) return; // no ownerDir; no need to unregister
		ownerDir.SubDirs().Del(url);
	}
	@Override public void XferDir(IoEngine_xrg_xferDir args) {Io_url trg = args.Trg(); utl.XferDir(this, args.Src(), IoEnginePool.Instance.Get_by(trg.Info().EngineKey()), trg, args);}
	@Override public void MoveDirDeep(IoEngine_xrg_xferDir args) {Io_url trg = args.Trg(); utl.XferDir(this, args.Src(), IoEnginePool.Instance.Get_by(trg.Info().EngineKey()), trg, args);}
	@Override public void MoveDir(Io_url src, Io_url trg) {if (ExistsDir(trg)) throw Err_.new_wo_type("trg already exists", "trg", trg);
		IoItmDir dir = FetchDir(src); dir.Name_(trg.NameAndExt());
		for (Object filObj : dir.SubFils()) {			// move all subFiles
			IoItmFil fil = (IoItmFil)filObj;
			fil.OwnerDir_set(dir);
		}
		dirs.Add(dir);
		DeleteDir(src);
	}
	@Override public IoItmDir QueryDir(Io_url url) {
		IoItmDir dir = FetchDir(url); 
		IoItmDir rv = IoItmDir_.top_(url);		// always return copy b/c caller may add/del itms directly
		if (dir == null) {
			rv.Exists_set(false);
			return rv;
		}
		for (Object subDirObj : dir.SubDirs()) {
			IoItmDir subDir = (IoItmDir)subDirObj;
			rv.SubDirs().Add(IoItmDir_.scan_(subDir.Url()));
		}
		for (Object subFilObj : dir.SubFils()) {
			IoItmFil subFil = (IoItmFil)subFilObj;
			rv.SubFils().Add(subFil);
		}
		return rv;
	}
	@Override public void DeleteDirDeep(IoEngine_xrg_deleteDir args) {utl.DeleteDirDeep(this, args.Url(), args);}
	@Override public void CopyDir(Io_url src, Io_url trg) {
		IoEngine_xrg_xferDir.copy_(src, trg).Recur_().Exec();
	}
	void AddFilToDir(Io_url dirPath, IoItmFil fil) {
		IoItmDir dir = FetchDir(dirPath);
		if (dir == null) {
			CreateDir(dirPath);
			dir = FetchDir(dirPath);
		}
		dir.SubFils().Del(fil.Url());
		dir.SubFils().Add(fil);
	}
	IoItmDir FetchDir(Io_url url) {return IoItmDir_.as_(dirs.Get_by(url));}
	IoItmFil_mem FetchFil(Io_url url) {
		IoItmDir ownerDir = FetchDir(url.OwnerDir());
		if (ownerDir == null) return IoItmFil_mem.Null;
		IoItmFil_mem rv = IoItmFil_mem.as_(ownerDir.SubFils().Get_by(url.NameAndExt())); 
		if (rv == null) rv = IoItmFil_mem.Null;
		return rv;
	}
	void CheckTransferArgs(String op, Io_url src, Io_url trg, boolean overwrite) {
		if (!ExistsFil_api(src)) throw Err_.new_wo_type("src does not exist", "src", src);
		if (ExistsFil_api(trg) && !overwrite) throw Err_.new_invalid_op("trg already exists").Args_add("op", op, "overwrite", false, "src", src, "trg", trg);
	}
	public void Clear() {dirs.Clear();}
	@Override public boolean DownloadFil(IoEngine_xrg_downloadFil xrg) {
		Io_url src = Io_url_.mem_fil_(xrg.Src());
		if (!ExistsFil_api(src)) {
			xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_file_not_found);
			return false;
		}
		XferFil(IoEngine_xrg_xferFil.copy_(src, xrg.Trg()).Overwrite_());
		return true;
	}
	@Override public Io_stream_rdr DownloadFil_as_rdr(IoEngine_xrg_downloadFil xrg) {
		Io_url src = Io_url_.mem_fil_(xrg.Src());
		if (!ExistsFil_api(src)) {
			xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_file_not_found);
			return Io_stream_rdr_.Noop;
		}
		byte[] bry = Bry_.new_u8(FetchFil(Io_url_.mem_fil_(xrg.Src())).Text());
		return Io_stream_rdr_.New__mem(bry);
	}

	IoItmHash dirs = IoItmHash.new_();
	IoEngineUtl utl = IoEngineUtl.new_();
	@gplx.Internal protected static IoEngine_memory new_(String key) {
		IoEngine_memory rv = new IoEngine_memory();
		rv.key = key;
		return rv;
	}	IoEngine_memory() {}
}
