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
package gplx;
import gplx.core.primitives.*; import gplx.ios.*; /*IoItmFil, IoItmDir..*/
public class Io_mgr {	// exists primarily to gather all cmds under gplx namespace; otherwise need to use gplx.ios whenever copying/deleting file
	public boolean							Exists(Io_url url) {return url.Type_dir() ? ExistsDir(url) : ExistsFil(url);}
	public boolean							ExistsFil(Io_url url) {return IoEnginePool._.Get_by(url.Info().EngineKey()).ExistsFil_api(url);}
	public void							ExistsFilOrFail(Io_url url) {if (!ExistsFil(url)) throw Err_.new_("could not find file").Add("url", url);}
	public void							SaveFilStr(String url, String text) {SaveFilStr_args(Io_url_.new_fil_(url), text).Exec();}
	public void							SaveFilStr(Io_url url, String text) {SaveFilStr_args(url, text).Exec();}
	public IoEngine_xrg_saveFilStr		SaveFilStr_args(Io_url url, String text) {return IoEngine_xrg_saveFilStr.new_(url, text);}
	public void							AppendFilStr(String url, String text) {AppendFilStr(Io_url_.new_fil_(url), text);}
	public void							AppendFilStr(Io_url url, String text) {SaveFilStr_args(url, text).Append_(true).Exec();}
	public void							DeleteFil(Io_url url) {DeleteFil_args(url).Exec();}
	public IoEngine_xrg_deleteFil		DeleteFil_args(Io_url url) {return IoEngine_xrg_deleteFil.new_(url);}
	public void							MoveFil(Io_url src, Io_url trg) {IoEngine_xrg_xferFil.move_(src, trg).Exec();}
	public IoEngine_xrg_xferFil			MoveFil_args(Io_url src, Io_url trg, boolean overwrite) {return IoEngine_xrg_xferFil.move_(src, trg).Overwrite_(overwrite);}
	public void							CopyFil(Io_url src, Io_url trg, boolean overwrite) {IoEngine_xrg_xferFil.copy_(src, trg).Overwrite_(overwrite).Exec();}
	public IoEngine_xrg_xferFil			CopyFil_args(Io_url src, Io_url trg, boolean overwrite) {return IoEngine_xrg_xferFil.copy_(src, trg).Overwrite_(overwrite);}
	public IoRecycleBin RecycleBin()	{return recycleBin;} IoRecycleBin recycleBin = IoRecycleBin._; 

	public IoStream						OpenStreamWrite(Io_url url)		{return OpenStreamWrite_args(url).Exec();}
	public IoEngine_xrg_openWrite		OpenStreamWrite_args(Io_url url) {return IoEngine_xrg_openWrite.new_(url);}
	public IoItmFil						QueryFil(Io_url url) {return IoEnginePool._.Get_by(url.Info().EngineKey()).QueryFil(url);}
	public void							UpdateFilAttrib(Io_url url, IoItmAttrib attrib) {IoEnginePool._.Get_by(url.Info().EngineKey()).UpdateFilAttrib(url, attrib);}
	public void							UpdateFilModifiedTime(Io_url url, DateAdp modified) {IoEnginePool._.Get_by(url.Info().EngineKey()).UpdateFilModifiedTime(url, modified);}

	public boolean							ExistsDir(Io_url url) {return IoEnginePool._.Get_by(url.Info().EngineKey()).ExistsDir(url);}
	public void							CreateDir(Io_url url) {IoEnginePool._.Get_by(url.Info().EngineKey()).CreateDir(url);}
	public boolean							CreateDirIfAbsent(Io_url url) {
		boolean exists = ExistsDir(url);
		if (!exists) {
			CreateDir(url);
			return true;
		}
		return false;
	}
	public void Create_fil_ary(Io_fil[] fil_ary) {
		for (Io_fil fil : fil_ary)
			SaveFilStr(fil.Url(), fil.Data());
	}
	public Io_url[]						QueryDir_fils(Io_url dir) {return QueryDir_args(dir).ExecAsUrlAry();}
	public IoEngine_xrg_queryDir		QueryDir_args(Io_url dir) {return IoEngine_xrg_queryDir.new_(dir);}
	public void							DeleteDirSubs(Io_url url) {IoEngine_xrg_deleteDir.new_(url).Exec();}
	public IoEngine_xrg_deleteDir		DeleteDir_cmd(Io_url url) {return IoEngine_xrg_deleteDir.new_(url);}
	public void							DeleteDirDeep(Io_url url) {IoEngine_xrg_deleteDir.new_(url).Recur_().Exec();}
	public void							DeleteDirDeep_ary(Io_url... urls) {for (Io_url url : urls) IoEngine_xrg_deleteDir.new_(url).Recur_().Exec();}
	public int							Delete_dir_empty(Io_url url) {return Io_mgr_.Delete_dir_empty(url);}
	public void							MoveDirDeep(Io_url src, Io_url trg) {IoEngine_xrg_xferDir.move_(src, trg).Recur_().Exec();}
	public IoEngine_xrg_xferDir			CopyDir_cmd(Io_url src, Io_url trg) {return IoEngine_xrg_xferDir.copy_(src, trg);}
	public void							CopyDirSubs(Io_url src, Io_url trg) {IoEngine_xrg_xferDir.copy_(src, trg).Exec();}
	public void							CopyDirDeep(Io_url src, Io_url trg) {IoEngine_xrg_xferDir.copy_(src, trg).Recur_().Exec();}
	public void DeleteDirIfEmpty(Io_url url) {
		if (Array_.Len(QueryDir_fils(url)) == 0)
			this.DeleteDirDeep(url);
	}
	public void AliasDir_sysEngine(String srcRoot, String trgRoot)			{AliasDir(srcRoot, trgRoot, IoEngine_.SysKey);}
	public void AliasDir(String srcRoot, String trgRoot, String engineKey)	{IoUrlInfoRegy._.Reg(IoUrlInfo_.alias_(srcRoot, trgRoot, engineKey));}
	public IoStream						OpenStreamRead(Io_url url)		{return OpenStreamRead_args(url).ExecAsIoStreamOrFail();}
	public IoEngine_xrg_openRead		OpenStreamRead_args(Io_url url)	{return IoEngine_xrg_openRead.new_(url);}
	public String						LoadFilStr(String url) {return LoadFilStr_args(Io_url_.new_fil_(url)).Exec();}
	public String						LoadFilStr(Io_url url) {return LoadFilStr_args(url).Exec();}
	public IoEngine_xrg_loadFilStr		LoadFilStr_args(Io_url url) {return IoEngine_xrg_loadFilStr.new_(url);}
	public byte[]						LoadFilBry(String url) {return LoadFilBry_reuse(Io_url_.new_fil_(url), Bry_.Empty, Int_obj_ref.zero_());}
	public byte[]						LoadFilBry(Io_url url) {return LoadFilBry_reuse(url, Bry_.Empty, Int_obj_ref.zero_());}
	public void							LoadFilBryByBfr(Io_url url, Bry_bfr bfr) {
		Int_obj_ref len = Int_obj_ref.zero_();
		byte[] bry = LoadFilBry_reuse(url, Bry_.Empty, len);
		bfr.Bfr_init(bry, len.Val());
	}
	public static final byte[] LoadFilBry_fail = Bry_.Empty;
	public byte[]						LoadFilBry_reuse(Io_url url, byte[] ary, Int_obj_ref aryLen) {
		if (!ExistsFil(url)) {aryLen.Val_(0); return LoadFilBry_fail;}
		IoStream stream = IoStream_.Null;
		try {
			stream = OpenStreamRead(url);
			int streamLen = (int)stream.Len();
			aryLen.Val_(streamLen);
			if (streamLen > ary.length)
				ary = new byte[streamLen];
			stream.ReadAry(ary);
			return ary;
		}
		catch (Exception e) {throw Err_.new_("failed to load file").Add("url", url.Xto_api()).Add("e", Err_.Message_lang(e));}
		finally {stream.Rls();}
	}
	public byte[]						LoadFilBry_loose(Io_url url) {return Bry_.new_u8(LoadFilStr_loose(url));}
	public String						LoadFilStr_loose(Io_url url) {
		String rv = LoadFilStr_args(url).BomUtf8Convert_(Bool_.Y).MissingIgnored_(Bool_.Y).Exec();
		if (String_.Has(rv, "\r\n"))
			rv = String_.Replace(rv, "\r\n", "\n");
		return rv;
	}
	public void AppendFilBfr(Io_url url, Bry_bfr bfr) {AppendFilByt(url, bfr.Bfr(), 0, bfr.Len()); bfr.ClearAndReset();}
	public void AppendFilByt(Io_url url, byte[] val) {AppendFilByt(url, val, 0, val.length);}
	public void AppendFilByt(Io_url url, byte[] val, int len) {AppendFilByt(url, val, 0, len);}
	public void AppendFilByt(Io_url url, byte[] val, int bgn, int len) {
		IoStream stream = IoStream_.Null;
		try {
			stream = OpenStreamWrite_args(url).Mode_(IoStream_.Mode_wtr_append).Exec();
			stream.Write(val, bgn, len);
		}	finally {stream.Rls();}
	}
	public void SaveFilBfr(Io_url url, Bry_bfr bfr) {SaveFilBry(url, bfr.Bfr(), bfr.Len()); bfr.Clear();}
	public void SaveFilBry(String urlStr, byte[] val) {SaveFilBry(Io_url_.new_fil_(urlStr), val);}
	public void SaveFilBry(Io_url url, byte[] val) {SaveFilBry(url, val, val.length);}
	public void SaveFilBry(Io_url url, byte[] val, int len) {SaveFilBry(url, val, 0, len);}
	public void SaveFilBry(Io_url url, byte[] val, int bgn, int len) {
		IoStream stream = IoStream_.Null;
		try {
			stream = OpenStreamWrite(url);
			stream.Write(val, bgn, len);
		}	finally {stream.Rls();}
	}
	public IoEngine InitEngine_mem() {return IoEngine_.Mem_init_();}
	public IoEngine InitEngine_mem_(String key) {
		IoEngine engine = IoEngine_.mem_new_(key);
		IoEnginePool._.Add_if_dupe_use_nth(engine);
		IoUrlInfoRegy._.Reg(IoUrlInfo_.mem_(key, key));
		return engine;
	}
	public boolean DownloadFil(String src, Io_url trg) {return IoEngine_xrg_downloadFil.new_(src, trg).Exec();}
	public IoEngine_xrg_downloadFil DownloadFil_args(String src, Io_url trg) {return IoEngine_xrg_downloadFil.new_(src, trg);}
	public static final Io_mgr I = new Io_mgr(); public Io_mgr() {}
	public static final int Len_kb = 1024, Len_mb = 1048576, Len_gb = 1073741824, Len_gb_2 = 2147483647;
	public static final long Len_mb_long = Len_mb;
	public static final long Len_null = -1;
}
class Io_mgr_ {
	public static int Delete_dir_empty(Io_url url) {
		IoItmDir dir = Io_mgr.I.QueryDir_args(url).ExecAsDir();
		int sub_dirs_len = dir.SubDirs().Count();
		int deleted_dirs = 0;
		for (int i = 0; i < sub_dirs_len; ++i) {
			IoItmDir sub_dir = (IoItmDir)dir.SubDirs().Get_at(i);
			deleted_dirs += Io_mgr.I.Delete_dir_empty(sub_dir.Url());
		}
		if (	dir.SubFils().Count() == 0
			&&	deleted_dirs == sub_dirs_len
			) {
			Io_mgr.I.DeleteDirIfEmpty(url);
			return 1;
		}
		else
			return 0;
	}
}
