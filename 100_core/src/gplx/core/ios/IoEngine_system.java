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
import gplx.types.errs.Err;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.Io_mgr;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.ClassUtl;
import gplx.core.envs.Op_sys;
import gplx.core.ios.atrs.Io_itm_atr_req;
import gplx.core.ios.atrs.Io_itm_atr_wkr;
import gplx.core.ios.streams.IoStream;
import gplx.core.ios.streams.IoStream_;
import gplx.core.ios.streams.IoStream_base;
import gplx.core.ios.streams.Io_stream_rdr;
import gplx.core.ios.streams.Io_stream_tid_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class IoEngine_system extends IoEngine_base {
	@Override public String Key() {return IoEngine_.SysKey;}
	@Override public void DeleteDirDeep(IoEngine_xrg_deleteDir args) {utl.DeleteDirDeep(this, args.Url(), args);}
	@Override public void XferDir(IoEngine_xrg_xferDir args) {Io_url trg = args.Trg(); utl.XferDir(this, args.Src(), IoEnginePool.Instance.Get_by(trg.Info().EngineKey()), trg, args);}
	@Override public void XferFil(IoEngine_xrg_xferFil args) {utl.XferFil(this, args);}
	@Override public IoItmDir QueryDirDeep(IoEngine_xrg_queryDir args) {return utl.QueryDirDeep(this, args);}
	@Override public void CopyDir(Io_url src, Io_url trg) {IoEngine_xrg_xferDir.copy_(src, trg).Recur_().Exec();}
	@Override public void MoveDirDeep(IoEngine_xrg_xferDir args) {Io_url trg = args.Trg(); utl.XferDir(this, args.Src(), IoEnginePool.Instance.Get_by(trg.Info().EngineKey()), trg, args);}
	@Override public void DeleteFil_api(IoEngine_xrg_deleteFil args) {
		Io_url url = args.Url();
		File fil = Fil_(url);    
		if (!Fil_Exists(fil)) return;
		MarkFileWritable(fil, url, args.ReadOnlyFails(), "DeleteFile");
		DeleteFil_lang(fil, url);
	}
	@Override public Io_itm_atr_req Query_itm_atrs(Io_url url, Io_itm_atr_req req) {
		return Io_itm_atr_wkr.New(url).Process(req);
	}
		@Override public boolean ExistsFil_api(Io_url url) {
		File f = new File(url.Xto_api());
		return f.exists();
	}
	@Override public void SaveFilText_api(IoEngine_xrg_saveFilStr mpo) {
		Io_url url = mpo.Url();

		// encode string
		byte[] textBytes = null;
		textBytes = BryUtl.NewU8(mpo.Text());

		FileChannel fc = null; FileOutputStream fos = null;
		if (!ExistsDir(url.OwnerDir())) CreateDir(url.OwnerDir());
		try {
			// open file
			try     {fos = new FileOutputStream(url.Xto_api(), mpo.Append());}
			catch     (FileNotFoundException e) {throw Err_Fil_NotFound(e, url);}
			fc = fos.getChannel();
		
			// write text
			try     {fc.write(ByteBuffer.wrap(textBytes));}
			catch    (IOException e) {
				Closeable_close(fc, url, false);
				Closeable_close(fos, url, false);
				throw ErrUtl.NewArgs(e, "write data to file failed", "url", url.Xto_api());
			}
			if (!Op_sys.Cur().Tid_is_drd()) {
				File fil = new File(url.Xto_api());
				IoEngine_system_xtn.SetExecutable(fil, true);
			}
		}
		finally {        
			// cleanup
			Closeable_close(fc, url, false);
			Closeable_close(fos, url, false);
		}
	}
	@Override public String LoadFilStr(IoEngine_xrg_loadFilStr args) {
		Io_url url = args.Url(); String url_str = url.Xto_api();
		boolean file_exists = ExistsFil_api(url);            // check if file exists first to avoid throwing exception; note that most callers pass Missing_ignored; DATE:2015-02-24
		if (!file_exists) {
			if (args.MissingIgnored())     return "";
			else                         throw Err_Fil_NotFound(url);
		}
		// get reader for file
		InputStream stream = null;        
		try     {stream = new FileInputStream(url_str);}
		catch     (FileNotFoundException e) {
			if (args.MissingIgnored()) return "";
			throw Err_Fil_NotFound(e, url);
		}
		return Load_from_stream_as_str(stream, url_str);
	}
	public static String Load_from_stream_as_str(InputStream stream, String url_str) {
		InputStreamReader reader = null;
		try     {reader = new InputStreamReader(stream, IoEngineArgs.Instance.LoadFilStr_Encoding);}
		catch     (UnsupportedEncodingException e) {
			Closeable_close(stream, url_str, false);
			throw Err_text_unsupported_encoding(IoEngineArgs.Instance.LoadFilStr_Encoding, "", url_str, e);
		}
		
		// make other objects
		char[] readerBuffer = new char[IoEngineArgs.Instance.LoadFilStr_BufferSize];
		int pos = 0;
		StringWriter sw = new StringWriter();
		
		// transfer data 
		while (true) {
			try     {pos = reader.read(readerBuffer);}
			catch    (IOException e) {
				try     {
					stream.close();
					reader.close();
				}
				catch     (IOException e2) {}                
				throw ErrUtl.NewArgs(e, "read data from file failed", "url", url_str, "pos", pos);
			}
			if (pos == -1) break;
			sw.write(readerBuffer, 0, pos);
		}
		
		// cleanup
		Closeable_close(stream, url_str, false);
		Closeable_close(reader, url_str, false);
		return sw.toString();
	}
	public static byte[] Load_from_stream_as_bry(InputStream stream, String url_str) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[4096];
		int read = 0;
		try {
			while ((read = stream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, read);
			}
			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toByteArray();
	}
	@Override public boolean ExistsDir(Io_url url) {return new File(url.Xto_api()).exists();}
	@Override public void CreateDir(Io_url url) {new File(url.Xto_api()).mkdirs();}
	@Override public void DeleteDir(Io_url url) {
		File dir = new File(url.Xto_api());
		if (!dir.exists()) return;
		boolean rv = dir.delete();
		if (!rv) throw ErrUtl.NewArgs("delete dir failed", "url", url.Xto_api());
	}
	@Override public IoItmDir QueryDir(Io_url url) {
		IoItmDir rv = IoItmDir_.scan_(url);
		String url_api = url.Xto_api();
		if (    gplx.core.envs.Op_sys.Cur().Tid_is_wnt()            // op_sys is wnt 
			&&     StringUtl.Eq(url.OwnerDir().Raw(), StringUtl.Empty)        // folder is drive; EX: "C:"
			)
			url_api = url_api + "\\";                                // add "\\"; else listFiles will return working folder's files, not C:; DATE:2016-04-07

		File dirInfo = new File(url_api);
		rv.ReadOnly_(!dirInfo.canWrite()); // get read-only flag for directories; ISSUE#:509; DATE:2019-07-11
		if (!dirInfo.exists()) {
			rv.Exists_set(false);
			return rv;
		}
		IoUrlInfo urlInfo = url.Info();
		File[] subItmAry = dirInfo.listFiles();
		if (subItmAry == null) return rv;    // directory has no files
		for (int i = 0; i < subItmAry.length; i++) {
			File subItm = subItmAry[i];
			if (subItm.isFile()) {
				IoItmFil subFil = QueryMkr_fil(urlInfo, subItm);
				rv.SubFils().Add(subFil);
			}
			else {
				IoItmDir subDir = QueryMkr_dir(urlInfo, subItm);                
				rv.SubDirs().Add(subDir);
			}
		}
		return rv;
	}
	IoItmFil QueryMkr_fil(IoUrlInfo urlInfo, File apiFil) {
		Io_url filUrl = Io_url_.new_inf_(apiFil.getPath(), urlInfo);    // NOTE: may throw PathTooLongException when url is > 248 (exception messages states 260)
		long fil_len = apiFil.exists() ? apiFil.length() : IoItmFil.Size_invalid;    // NOTE: if file doesn't exist, set len to -1; needed for "boolean Exists() {return size != Size_Invalid;}"; DATE:2014-06-21
		IoItmFil rv = IoItmFil_.new_(filUrl, fil_len, GfoDateUtl.MinValue, GfoDateUtl.NewUnixtimeLclMs(apiFil.lastModified()));
		rv.ReadOnly_(!apiFil.canWrite());
		return rv;
	}
	IoItmDir QueryMkr_dir(IoUrlInfo urlInfo, File apiDir) {
		Io_url dirUrl = Io_url_.new_inf_(apiDir.getPath() + urlInfo.DirSpr(), urlInfo);    // NOTE: may throw PathTooLongException when url is > 248 (exception messages states 260)
		return IoItmDir_.scan_(dirUrl);
	}
	@Override public IoItmFil QueryFil(Io_url url) {
		File fil = new File(url.Xto_api());
		return QueryMkr_fil(url.Info(), fil);
	}
	@Override public void UpdateFilAttrib(Io_url url, IoItmAttrib atr) {
		File f = new File(url.Xto_api());
		boolean rv = true;
		if (atr.ReadOnly() != Fil_ReadOnly(f)) {
			if (atr.ReadOnly())
				rv = f.setReadOnly();
			else {
				if (!Op_sys.Cur().Tid_is_drd())
					IoEngine_system_xtn.SetWritable(f, true);
			}
			if (!rv) throw ErrUtl.NewArgs("set file attribute failed", "attribute", "readOnly", "cur", Fil_ReadOnly(f), "new", atr.ReadOnly(), "url", url.Xto_api());
		}
		if (atr.Hidden() != f.isHidden()) {
			//Runtime.getRuntime().exec("attrib +H myHiddenFile.java");            
		}
	}
	@Override public void UpdateFilModifiedTime(Io_url url, GfoDate modified) {
		File f = new File(url.Xto_api());
		long timeInt = modified.UnderCalendar().getTimeInMillis();
//        if (timeInt < 0) {
//            UsrDlg_._.Notify("{0} {1}", url.Xto_api(), timeInt);
//            return;
//        }
		if (!f.setLastModified(timeInt)) {
			if (Fil_ReadOnly(f)) {
				boolean success = false;
				try {
					UpdateFilAttrib(url, IoItmAttrib.normal_());
					success = f.setLastModified(timeInt);
				}
				finally {
					UpdateFilAttrib(url, IoItmAttrib.readOnly_());
				}
				if (!success) throw ErrUtl.NewArgs("could not update file modified time", "url", url.Xto_api(), "modifiedTime", modified.ToStrGplxLong());
			}
		}
	}
	@Override public IoStream OpenStreamRead(Io_url url) {return IoStream_base.new_(url, IoStream_.Mode_rdr);}
	@Override public IoStream OpenStreamWrite(IoEngine_xrg_openWrite args) {
		Io_url url = args.Url();
		if (!ExistsFil_api(url)) SaveFilText_api(IoEngine_xrg_saveFilStr.new_(url, ""));
		return IoStream_base.new_(url, args.Mode());
	}
	@Override public void CopyFil(IoEngine_xrg_xferFil args) {
		// TODO:JAVA6 hidden property ignored; 1.6 does not allow OS-independent way of setting isHidden (wnt only possible through jni)
		boolean overwrite = args.Overwrite();
		Io_url srcUrl = args.Src(), trgUrl = args.Trg();
		File srcFil = new File(srcUrl.Xto_api()), trgFil = new File(trgUrl.Xto_api());
		if (trgFil.isFile()) {        // trgFil exists; check if overwrite set and trgFil is writable
			Chk_TrgFil_Overwrite(overwrite, trgUrl);
			MarkFileWritable(trgFil, trgUrl, args.ReadOnlyFails(), "copy");
		}
		else {                        // trgFil doesn't exist; must create file first else fileNotFound exception thrown
			boolean rv = true;
			if (!ExistsDir(trgUrl.OwnerDir())) CreateDir(trgUrl.OwnerDir());
			try     {
				trgFil.createNewFile();
				if (!Op_sys.Cur().Tid_is_drd())
					IoEngine_system_xtn.SetExecutable(trgFil, true);
			}
			catch     (IOException e) {
				rv = false;
			}
			if (!rv)
				throw ErrUtl.NewArgs("create file failed", "trg", trgUrl.Xto_api());
		}
		FileInputStream srcStream = null; FileOutputStream trgStream = null;
		FileChannel srcChannel = null, trgChannel = null;
		try {
			// make objects
			try     {srcStream = new FileInputStream(srcFil);}
			catch     (FileNotFoundException e) {throw IoErr.FileNotFound("copy", srcUrl);}
			try     {trgStream = new FileOutputStream(trgFil);}
			catch     (FileNotFoundException e) {
				trgStream = TryToUnHideFile(trgFil, trgUrl);
				if (trgStream == null)
					throw IoErr.FileNotFound("copy", trgUrl);
//                else
//                    wasHidden = true;
			}
			srcChannel = srcStream.getChannel();        
			trgChannel = trgStream.getChannel();
			
			// transfer data
			long pos = 0, count = 0, read = 0;
			try     {count = srcChannel.size();}
			catch     (IOException e) {throw ErrUtl.NewArgs(e, "size failed", "src", srcUrl.Xto_api());}
			int totalBufferSize = IoEngineArgs.Instance.LoadFilStr_BufferSize;
			long transferSize = (count > totalBufferSize) ? totalBufferSize : count;    // transfer as much as fileSize, but limit to LoadFilStr_BufferSize 
			while     (pos < count) {
				try     {read = trgChannel.transferFrom(srcChannel, pos, transferSize);}
				catch     (IOException e) {
					Closeable_close(srcChannel, srcUrl, false);
					Closeable_close(trgChannel, trgUrl, false);
					Closeable_close(srcStream, srcUrl, false);
					Closeable_close(trgStream, srcUrl, false);
					throw ErrUtl.NewArgs(e, "transfer data failed", "src", srcUrl.Xto_api(), "trg", trgUrl.Xto_api());
				}
				if (read == -1) break;
				pos += read;
			}
//            if (wasHidden)
//                
		}
		finally {
			// cleanup
			Closeable_close(srcChannel, srcUrl, false);
			Closeable_close(trgChannel, trgUrl, false);
			Closeable_close(srcStream, srcUrl, false);
			Closeable_close(trgStream, srcUrl, false);
		}
		UpdateFilModifiedTime(trgUrl, QueryFil(srcUrl).ModifiedTime());    // must happen after file is closed
	}
	public void CopyFil(gplx.core.progs.Gfo_prog_ui prog_ui, Io_url src_url, Io_url trg_url, boolean overwrite, boolean readonly_fails) {
		// TODO:JAVA6 hidden property ignored; 1.6 does not allow OS-independent way of setting isHidden (wnt only possible through jni)
		File src_fil = new File(src_url.Xto_api()), trg_fil = new File(trg_url.Xto_api());
		if (trg_fil.isFile()) {        // trg_fil exists; check if overwrite set and trg_fil is writable
			Chk_TrgFil_Overwrite(overwrite, trg_url);
			MarkFileWritable(trg_fil, trg_url, readonly_fails, "copy");
		}
		else {                        // trg_fil doesn't exist; must create file first else fileNotFound exception thrown
			boolean rv = true;
			if (!ExistsDir(trg_url.OwnerDir())) CreateDir(trg_url.OwnerDir());
			try     {
				trg_fil.createNewFile();
				if (!Op_sys.Cur().Tid_is_drd())
					IoEngine_system_xtn.SetExecutable(trg_fil, true);
			}
			catch     (IOException e) {
				rv = false;
			}
			if (!rv)
				throw ErrUtl.NewArgs("create file failed", "trg", trg_url.Xto_api());
		}
		FileInputStream src_stream = null; FileOutputStream trg_stream = null;
		FileChannel src_channel = null, trg_channel = null;
		try {
			// make objects
			try     {src_stream = new FileInputStream(src_fil);}
			catch     (FileNotFoundException e) {throw IoErr.FileNotFound("copy", src_url);}
			try     {trg_stream = new FileOutputStream(trg_fil);}
			catch     (FileNotFoundException e) {
				trg_stream = TryToUnHideFile(trg_fil, trg_url);
				if (trg_stream == null)
					throw IoErr.FileNotFound("copy", trg_url);
//                else
//                    wasHidden = true;
			}
			src_channel = src_stream.getChannel();        
			trg_channel = trg_stream.getChannel();
			
			// transfer data
			long pos = 0, count = 0, read = 0;
			try     {count = src_channel.size();}
			catch     (IOException e) {throw ErrUtl.NewArgs(e, "size failed", "src", src_url.Xto_api());}
			int buffer_size = IoEngineArgs.Instance.LoadFilStr_BufferSize;
			long transfer_size = (count > buffer_size) ? buffer_size : count;    // transfer as much as fileSize, but limit to LoadFilStr_BufferSize 
			while     (pos < count) {
				try     {read = trg_channel.transferFrom(src_channel, pos, transfer_size);}
				catch     (IOException e) {
					Closeable_close(src_channel, src_url, false);
					Closeable_close(trg_channel, trg_url, false);
					Closeable_close(src_stream, src_url, false);
					Closeable_close(trg_stream, src_url, false);
					throw ErrUtl.NewArgs(e, "transfer data failed", "src", src_url.Xto_api(), "trg", trg_url.Xto_api());
				}
				if (read == -1) break;
				if (prog_ui.Prog_notify_and_chk_if_suspended(pos, count)) return;
				pos += read;
			}
//            if (wasHidden)
//                
		}
		finally {
			// cleanup
			Closeable_close(src_channel, src_url, false);
			Closeable_close(trg_channel, trg_url, false);
			Closeable_close(src_stream, src_url, false);
			Closeable_close(trg_stream, src_url, false);
		}
		UpdateFilModifiedTime(trg_url, QueryFil(src_url).ModifiedTime());    // must happen after file is closed
	}
	FileOutputStream TryToUnHideFile(File trgFil, Io_url trgUrl) {
		FileOutputStream trgStream = null;
		if (trgFil.exists()) {    // WORKAROUND: java fails when writing to hidden files; unmark hidden and try again
			Process p = null;
			try {
				String d = "attrib -H \"" + trgUrl.Xto_api() + "\"";
				p = Runtime.getRuntime().exec(d);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				p.waitFor();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try     {trgStream = new FileOutputStream(trgFil);}
			catch     (FileNotFoundException e) {
				return null;
			}
		}
		return trgStream;
	} 
	@Override public void MoveFil(IoEngine_xrg_xferFil args) {
		Io_url srcUrl = args.Src(), trgUrl = args.Trg();
		String src_api = srcUrl.Xto_api(), trg_api = trgUrl.Xto_api();
		if (StringUtl.Eq(src_api, trg_api)) return;     // ignore command if src and trg is same; EX: C:\a.txt -> C:\a.txt should be noop
		File srcFil = new File(src_api), trgFil = new File(trg_api);
		
		// if drive is same, then rename file
		if (StringUtl.Eq(srcUrl.OwnerRoot().Raw(), trgUrl.OwnerRoot().Raw())) {
			boolean overwrite = args.Overwrite();
			if (!srcFil.exists() && args.MissingFails()) throw IoErr.FileNotFound("move", srcUrl);
			if (trgFil.exists()) {
				Chk_TrgFil_Overwrite(overwrite, trgUrl);
				MarkFileWritable(trgFil, trgUrl, args.ReadOnlyFails(), "move");
				DeleteFil_lang(trgFil, args.Trg());    // overwrite is specified and file is writable -> delete
			}
			if (!ExistsDir(trgUrl.OwnerDir())) CreateDir(trgUrl.OwnerDir());
			srcFil.renameTo(trgFil);
		}
		// else copy fil and delete
		else {
			if (!srcFil.exists() && !args.MissingFails()) return;
			CopyFil(args);
			DeleteFil_lang(srcFil, srcUrl);
		}
	}
	void Chk_TrgFil_Overwrite(boolean overwrite, Io_url trg) {
		if (!overwrite)
			throw ErrUtl.NewInvalidOp("trgFile exists but overwriteFlag not set").ArgsAdd("trg", trg.Xto_api());
	}
	@Override public void MoveDir(Io_url src, Io_url trg) {
		String srcStr = src.Xto_api(), trgStr = trg.Xto_api();
		File srcFil = new File(srcStr), trgFil = new File(trgStr);
		if (trgFil.exists())     {throw ErrUtl.NewInvalidOp("cannot move dir if trg exists").ArgsAdd("src", src).ArgsAdd("trg", trg);}
		if (StringUtl.Eq(src.OwnerRoot().Raw(), trg.OwnerRoot().Raw())) {
			srcFil.renameTo(trgFil);
		}
		else {
			XferDir(IoEngine_xrg_xferDir.copy_(src, trg));
		}
	}
	public static void Closeable_close(Closeable closeable, Io_url url, boolean throwErr) {Closeable_close(closeable, url.Xto_api(), throwErr);}
	public static void Closeable_close(Closeable closeable, String url_str, boolean throwErr) {
		if         (closeable == null) return;
		try     {closeable.close();}
		catch     (IOException e) {
			if (throwErr)
				throw ErrUtl.NewArgs(e, "close object failed", "class", ClassUtl.NameByObj(closeable), "url", url_str);
//            else
//                UsrDlg_._.Finally("failed to close FileChannel", "url", url, "apiErr", Err_.Message_err_arg(e));
		}
	}

	File Fil_(Io_url url) {return new File(url.Xto_api());}
	boolean Fil_Exists(File fil) {return fil.exists();}
	boolean Fil_ReadOnly(File fil) {return !fil.canWrite();}
	boolean Fil_Delete(File fil) {return fil.delete();}
	void Fil_Writable(File fil) {
		if (!Op_sys.Cur().Tid_is_drd())
			IoEngine_system_xtn.SetWritable(fil, true);
	}
	private static Err Err_text_unsupported_encoding(String encodingName, String text, String url_str, Exception e) {
		return ErrUtl.NewArgs(e, "text is in unsupported encoding").ArgsAdd("encodingName", encodingName).ArgsAdd("text", text).ArgsAdd("url", url_str);
	}
	boolean user_agent_needs_resetting = true;
	@Override public Io_stream_rdr DownloadFil_as_rdr(IoEngine_xrg_downloadFil xrg) {
		Io_stream_rdr_http rdr = new Io_stream_rdr_http(xrg);
		rdr.Open();
		return rdr;
	}
	@Override public boolean DownloadFil(IoEngine_xrg_downloadFil xrg) {
		IoStream trg_stream = null;
		java.io.BufferedInputStream src_stream = null;
		java.net.URL src_url = null;
		HttpURLConnection src_conn = null;
		if (user_agent_needs_resetting) {user_agent_needs_resetting = false; System.setProperty("http.agent", "");}
		boolean exists = Io_mgr.Instance.ExistsDir(xrg.Trg().OwnerDir());
		Gfo_usr_dlg prog_dlg = null;
		String src_str = xrg.Src();
		Io_download_fmt xfer_fmt = xrg.Download_fmt();
		prog_dlg = xfer_fmt.Usr_dlg();
		if (!Web_access_enabled) {
			if (prog_dlg != null) {
				if (session_fil == null) session_fil = prog_dlg.Log_wkr().Session_dir().GenSubFil("internet.txt");
				prog_dlg.Log_wkr().Log_msg_to_url_fmt(session_fil, "download disabled: src='~{0}' trg='~{1}'", xrg.Src(), xrg.Trg().Raw());                
			}
			return false;
		}
		try {
			trg_stream = Io_mgr.Instance.OpenStreamWrite(xrg.Trg());
			src_url = new java.net.URL(src_str);
			src_conn = (HttpURLConnection)src_url.openConnection();
//            src_conn.setReadTimeout(5000);    // do not set; if file does not exist, will wait 5 seconds before timing out; want to fail immediately
			String user_agent = xrg.User_agent(); if (user_agent != null) src_conn.setRequestProperty("User-Agent", user_agent);
			long content_length = LongUtl.ParseOr(src_conn.getHeaderField("Content-Length"), IoItmFil.Size_invalid_int);
			xrg.Src_content_length_(content_length);
			if (xrg.Src_last_modified_query())    // NOTE: only files will have last modified (api calls will not); if no last_modified, then src_conn will throw get nullRef; avoid nullRef 
				xrg.Src_last_modified_(GfoDateUtl.NewUnixtimeLclMs(src_conn.getLastModified()));
			if (xrg.Exec_meta_only()) return true;
			src_stream = new java.io.BufferedInputStream(src_conn.getInputStream());
			if (!exists) {
				Io_mgr.Instance.CreateDir(xrg.Trg().OwnerDir());    // dir must exist for OpenStreamWrite; create dir at last possible moment in case stream does not exist.
			}
			byte[] download_bfr = new byte[Download_bfr_len];    // NOTE: download_bfr was originally member variable; DATE:2013-05-03
			xfer_fmt.Bgn(content_length);
			int count = 0;
			while ((count = src_stream.read(download_bfr, 0, Download_bfr_len)) != -1) {
				if (xrg.Prog_cancel()) {
					src_stream.close();
					trg_stream.Rls();
					Io_mgr.Instance.DeleteFil(xrg.Trg());
				}
				xfer_fmt.Prog(count);
				trg_stream.Write(download_bfr, 0, count);
			}
			if (prog_dlg != null) {
				xfer_fmt.Term();
				if (session_fil == null) session_fil = prog_dlg.Log_wkr().Session_dir().GenSubFil("internet.txt");
				prog_dlg.Log_wkr().Log_msg_to_url_fmt(session_fil, "download pass: src='~{0}' trg='~{1}'", src_str, xrg.Trg().Raw());
			}
			return true;
		}
		catch (Exception exc) {
			xrg.Rslt_err_(exc);
			if         (ClassUtl.EqByObj(java.net.UnknownHostException.class, exc))     xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_host_not_found);
			else if (ClassUtl.EqByObj(FileNotFoundException.class, exc))    xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_file_not_found);
			else                                                                        xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_unknown);
			if (prog_dlg != null && !xrg.Prog_cancel()) {
				if (session_fil == null) session_fil = prog_dlg.Log_wkr().Session_dir().GenSubFil("internet.txt");
				prog_dlg.Log_wkr().Log_msg_to_url_fmt(session_fil, "download fail: src='~{0}' trg='~{1}' error='~{2}'", src_str, xrg.Trg().Raw(), ErrUtl.Message(exc));
			}
			if (trg_stream != null) {
				try {
					trg_stream.Rls();
					DeleteFil_api(IoEngine_xrg_deleteFil.new_(xrg.Trg()));
				}
				catch (Exception e2) {
				}
			}
			return false;
		}
		finally {
			xrg.Prog_running_(false);
			try {
				if (src_stream != null) src_stream.close();
				if (src_conn != null) {
					src_conn.disconnect();
					src_conn.getInputStream().close();
				}
			}
			catch (Exception exc) {
			}
			if (trg_stream != null) trg_stream.Rls();
		}
	}   Io_url session_fil; BryWtr prog_fmt_bfr;
	@Override public boolean Truncate_fil(Io_url url, long size) {
		FileOutputStream stream = null;
		FileChannel channel = null;
		try     {stream = new FileOutputStream(url.Xto_api(), true);}
		catch   (FileNotFoundException e) {throw ErrUtl.NewArgs("truncate: open failed", "url", url.Xto_api(), "err", ErrUtl.ToStrLog(e));}
		channel = stream.getChannel();
		try     {channel.truncate(size); return true;}
		catch     (IOException e) {return false;}
		finally {
			try {
				if (stream != null) stream.close();
				if (channel != null) channel.close();
			} catch     (IOException e) {return false;}
		}
	}
		byte[] download_bfr; static final int Download_bfr_len = IoConsts.LenKB * 128;
	public static Err Err_Fil_NotFound(Io_url url) {
		return ErrUtl.NewArgs("file not found", "url", url.Xto_api());
	}
	public static Err Err_Fil_NotFound(Exception e, Io_url url) {
		return ErrUtl.NewArgs(e, "file not found", "url", url.Xto_api());
	}
	void MarkFileWritable(File fil, Io_url url, boolean readOnlyFails, String op) {    
		if (Fil_ReadOnly(fil)) {
			if (readOnlyFails)    // NOTE: java will always allow final files to be deleted; programmer api is responsible for check
				throw ErrUtl.NewArgs("writable operation attempted on readOnly file", "op", op, "url", url.Xto_api());
			else
				Fil_Writable(fil);
		}
	}
	void DeleteFil_lang(File fil, Io_url url) {    
		boolean rv = Fil_Delete(fil);
		if (!rv)
			throw ErrUtl.NewArgs("file not deleted", "url", url.Xto_api());
	}
	IoEngineUtl utl = IoEngineUtl.new_();
	public static IoEngine_system new_() {return new IoEngine_system();} IoEngine_system() {}
	static final String GRP_KEY = "Io_engine";
	public static boolean Web_access_enabled = true;
}
class IoEngineArgs {
	public int        LoadFilStr_BufferSize = 4096 * 256;
	public String    LoadFilStr_Encoding = "UTF-8";
	public String    Err_ReadonlyFileNotWritable = "gplx.core.ios.ReadonlyFileNotWritable";
	public String    Err_FileNotFound             = "gplx.core.ios.FileNotFound";
	public String    Err_IoException                = "gplx.core.ios.IoException";
	public static final IoEngineArgs Instance = new IoEngineArgs();
}
class IoEngine_system_xtn {
	// PATCH.DROID:VerifyError if file.setExecutable is referenced directly in IoEngine_system. However, if placed in separate class
	public static void SetExecutable(java.io.File file, boolean v)     {file.setExecutable(v);}
	public static void SetWritable(java.io.File file, boolean v)     {file.setWritable(v);}
}
class Io_download_http {
	public static boolean User_agent_reset_needed = true;
	public static void User_agent_reset() {
		User_agent_reset_needed = false;
		System.setProperty("http.agent", "");    // need to set http.agent to '' in order for "User-agent" to take effect
	}
	public static void Save_to_fsys(IoEngine_xrg_downloadFil xrg) {
		Io_stream_rdr_http rdr = new Io_stream_rdr_http(xrg); 
		IoStream trg_stream = null;
		try {            
			boolean exists = Io_mgr.Instance.ExistsDir(xrg.Trg().OwnerDir());
			if (!exists)
				Io_mgr.Instance.CreateDir(xrg.Trg().OwnerDir());    // dir must exist for OpenStreamWrite; create dir at last possible moment in case stream does not exist.
			trg_stream = Io_mgr.Instance.OpenStreamWrite(xrg.Trg());
			byte[] bfr = new byte[Download_bfr_len];
			rdr.Open();
			while (rdr.Read(bfr, 0, Download_bfr_len) != Read_done) {                
			}
		}
		finally {
			rdr.Rls();
			if (trg_stream != null) trg_stream.Rls();
		}
		if (xrg.Rslt() != IoEngine_xrg_downloadFil.Rslt_pass)
			Io_mgr.Instance.DeleteFil_args(xrg.Trg()).MissingFails_off().Exec();
	}
	public static final int Read_done = -1;
	public static final int Download_bfr_len = IoConsts.LenKB * 128;
}
class Io_stream_rdr_http implements Io_stream_rdr {
	public Io_stream_rdr_http(IoEngine_xrg_downloadFil xrg) {
		this.xrg = xrg;
	}   private IoEngine_xrg_downloadFil xrg;
	public byte Tid() {return Io_stream_tid_.Tid__raw;}
	public boolean Exists() {return exists;} private boolean exists = false;
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {url = v; return this;} private Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len = IoItmFil.Size_invalid;    // NOTE: must default size to -1; DATE:2014-06-21    
	private String src_str; private HttpURLConnection src_conn; private java.io.BufferedInputStream src_stream;
	private Io_download_fmt xfer_fmt; private Gfo_usr_dlg prog_dlg;
	private boolean read_done = true, read_failed = false;
	public Io_stream_rdr Open() {
		if (Io_download_http.User_agent_reset_needed) Io_download_http.User_agent_reset();
		if (!IoEngine_system.Web_access_enabled) {
			read_done = read_failed = true;
			if (prog_dlg != null)
				prog_dlg.Log_wkr().Log_msg_to_url_fmt(session_fil, "download disabled: src='~{0}' trg='~{1}'", xrg.Src(), xrg.Trg().Raw());
			return this;
		}
		src_str = xrg.Src();
		xfer_fmt = xrg.Download_fmt(); prog_dlg = xfer_fmt.Usr_dlg();
		try {
			src_conn = (HttpURLConnection)new java.net.URL(src_str).openConnection();
			String user_agent = xrg.User_agent();
			if (user_agent != null) src_conn.setRequestProperty("User-Agent", user_agent);    // NOTE: must be set right after openConnection
//            src_conn.setReadTimeout(5000);    // do not set; if file does not exist, will wait 5 seconds before timing out; want to fail immediately
			long content_length = LongUtl.ParseOr(src_conn.getHeaderField("Content-Length"), IoItmFil.Size_invalid_int);
			xrg.Src_content_length_(content_length);
			this.len = content_length;
			if (xrg.Src_last_modified_query())    // NOTE: only files will have last modified (api calls will not); if no last_modified, then src_conn will throw get nullRef; avoid nullRef
				xrg.Src_last_modified_(GfoDateUtl.NewUnixtimeLclMs(src_conn.getLastModified()));
			if (xrg.Exec_meta_only()) {
				read_done = true;
				return this;
			}
			read_done = false;
			this.exists = IntUtl.In(src_conn.getResponseCode(), 200, 301);    // ASSUME: response code of 200 (OK) or 301 (Redirect) means that file exists; note that content_length seems to always be -1; DATE:2015-05-20
			src_stream = new java.io.BufferedInputStream(src_conn.getInputStream());
			xfer_fmt.Bgn(content_length);
		}
		catch (Exception e) {Err_handle(e);}
		return this;
	}
	public void Open_mem(byte[] v) {}
	public Object Under() {return src_stream;}
	public int Read(byte[] bry, int bgn, int len) {
		if (read_done) return Io_download_http.Read_done;
		if (xrg.Prog_cancel()) {read_failed = true; return Io_download_http.Read_done;}
		try {
			int read = src_stream.read(bry, bgn, len); 
			xfer_fmt.Prog(read);
			return read;
		}
		catch (Exception e) {
			Err_handle(e);
			return Io_download_http.Read_done;
		}
	}
	private Io_url session_fil = null;
	private boolean rls_done = false;
	public long Skip(long len) {return 0;}
	public void Rls() {
		if (rls_done) return;
		try {
			read_done = true;
			if (prog_dlg != null) {
				xfer_fmt.Term();
			}
			if (session_fil == null && prog_dlg != null) session_fil = prog_dlg.Log_wkr().Session_dir().GenSubFil("internet.txt");
			if (read_failed) {
			}
			else {
				if (prog_dlg != null)
					prog_dlg.Log_wkr().Log_msg_to_url_fmt(session_fil, "download pass: src='~{0}' trg='~{1}'", src_str, xrg.Trg().Raw());
				xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_pass);
			}
			xrg.Prog_running_(false);
		}
		catch (Exception e) {}    // ignore close errors; also Err_handle calls Rls() so it would be circular
		finally {
			try {if (src_stream != null) src_stream.close();} 
			catch (Exception e) {}    // ignore failures when cleaning up
			if (src_conn != null) src_conn.disconnect();
			src_stream = null;
			src_conn = null;
			rls_done = true;
		}
	}
	private void Err_handle(Exception exc) {
		read_done = read_failed = true;
		len = -1;
		xrg.Rslt_err_(exc);
		if         (ClassUtl.EqByObj(java.net.UnknownHostException.class, exc))     xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_host_not_found);
		else if (ClassUtl.EqByObj(FileNotFoundException.class, exc))    xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_file_not_found);
		else                                                                        xrg.Rslt_(IoEngine_xrg_downloadFil.Rslt_fail_unknown);
		if (prog_dlg != null && !xrg.Prog_cancel()) {
			if (session_fil == null) session_fil = prog_dlg.Log_wkr().Session_dir().GenSubFil("internet.txt");
			prog_dlg.Log_wkr().Log_msg_to_url_fmt(session_fil, "download fail: src='~{0}' trg='~{1}' error='~{2}'", src_str, xrg.Trg().Raw(), ErrUtl.Message(exc));
		}
		this.Rls();
	}
}
