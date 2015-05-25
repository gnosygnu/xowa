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
public class Io_stream_wtr_ {
	public static Io_stream_wtr bzip2_(Io_url url)		{return new Io_stream_wtr_bzip2().Url_(url);}
	public static Io_stream_wtr gzip_(Io_url url)		{return new Io_stream_wtr_gzip().Url_(url);}
	public static Io_stream_wtr zip_(Io_url url)		{return new Io_stream_wtr_zip().Url_(url);}
	public static Io_stream_wtr file_(Io_url url)		{return new Io_stream_wtr_file().Url_(url);}
	public static Io_stream_wtr new_by_url_(Io_url url) {
		String ext = url.Ext();
		if		(String_.Eq(ext, Io_stream_.Ext_zip)) 	return gplx.ios.Io_stream_wtr_.zip_(url);
		else if	(String_.Eq(ext, Io_stream_.Ext_gz)) 	return gplx.ios.Io_stream_wtr_.gzip_(url);
		else if	(String_.Eq(ext, Io_stream_.Ext_bz2)) 	return gplx.ios.Io_stream_wtr_.bzip2_(url);
		else 											return gplx.ios.Io_stream_wtr_.file_(url);
	}
	public static Io_stream_wtr new_by_mem(Bry_bfr bfr, byte tid) {
		Io_stream_wtr wtr = new_by_tid_(tid).Url_(Io_url_.Empty);
		wtr.Trg_bfr_(bfr);
		return wtr;
	}
	public static Io_stream_wtr new_by_tid_(byte v) {
		switch (v) {
			case gplx.ios.Io_stream_.Tid_raw	: return new Io_stream_wtr_file();
			case gplx.ios.Io_stream_.Tid_zip	: return new Io_stream_wtr_zip();
			case gplx.ios.Io_stream_.Tid_gzip	: return new Io_stream_wtr_gzip();
			case gplx.ios.Io_stream_.Tid_bzip2	: return new Io_stream_wtr_bzip2();
			default								: throw Err_.unhandled(v);
		}
	}
	public static void Save_all(Io_url url, byte[] bry, int bgn, int end) {
		Io_stream_wtr wtr = new_by_url_(url);
		try {
			wtr.Open();
			wtr.Write(bry, bgn, end);
			wtr.Flush();
		}
		finally {wtr.Rls();}
	}
	public static void Save_rdr(Io_url url, Io_stream_rdr rdr, Io_download_fmt download_progress) {
		byte[] bry = new byte[4096];
		Io_stream_wtr wtr = new_by_url_(url);
		try {
			wtr.Open();
			if (download_progress != Io_download_fmt.Null)
				download_progress.Bgn(rdr.Len());
			while (true) {
				int read = rdr.Read(bry, 0, 4096);
				if (read < gplx.ios.Io_stream_rdr_.Read_done_compare) break; 
				if (download_progress != Io_download_fmt.Null)
					download_progress.Prog(read);
				wtr.Write(bry, 0, read);
			}
			wtr.Flush();
			if (download_progress != Io_download_fmt.Null)
				download_progress.Term();
		}
		finally {wtr.Rls(); rdr.Rls();}
	}
}
abstract class Io_stream_wtr_base implements Io_stream_wtr {
	java.io.OutputStream zip_stream;
	public Io_url Url() {return url;} public Io_stream_wtr Url_(Io_url v) {url = v; trg_bfr = null; return this;} Io_url url;
	public void Trg_bfr_(Bry_bfr v) {trg_bfr = v;} Bry_bfr trg_bfr; java.io.ByteArrayOutputStream mem_stream;
	public byte[] To_ary_and_clear() {return trg_bfr.Xto_bry_and_clear();}
	@SuppressWarnings("resource") public Io_stream_wtr Open() {
		java.io.OutputStream bry_stream = null;
		if (trg_bfr == null) {
			if (!Io_mgr.I.ExistsFil(url)) Io_mgr.I.SaveFilStr(url, "");			
			try {bry_stream = new java.io.FileOutputStream(url.Raw());}
			catch (Exception exc) {throw Err_.new_fmt_("open failed: url={0}", url.Raw());}		
		}
		else {
			mem_stream = new java.io.ByteArrayOutputStream();
			bry_stream = mem_stream;
		}
		zip_stream = Wrap_stream(bry_stream);
		return this;
	}
	public void Write(byte[] bry, int bgn, int len) {
		try {zip_stream.write(bry, bgn, len);}
		catch (Exception exc) {throw Err_.new_fmt_("write failed: bgn={0} len={1}", bgn, len);}
	}
	public void Flush() {
		if (trg_bfr != null) {
			try {zip_stream.close();} catch (Exception exc) {throw Err_.new_fmt_("flush failed");}	// must close zip_stream to flush all bytes
			trg_bfr.Add(mem_stream.toByteArray());
		}
	}
	public void Rls() {
		try {
			if (zip_stream != null) zip_stream.close();
			if (mem_stream != null) mem_stream.close();
		}
		catch (Exception e) {throw Err_.new_fmt_("close failed: url={0}", url.Raw());}
	}
	public abstract java.io.OutputStream Wrap_stream(java.io.OutputStream stream);
}
class Io_stream_wtr_bzip2 extends Io_stream_wtr_base {
	@Override public byte Tid() {return Io_stream_.Tid_bzip2;}
	@Override public java.io.OutputStream Wrap_stream(java.io.OutputStream stream) {
		try {return new org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream(stream);}
		catch (Exception exc) {throw Err_.new_fmt_("failed to open bzip2 stream");}
	}
	static final byte[] Bz2_header = new byte[] {Byte_ascii.Ltr_B, Byte_ascii.Ltr_Z};
}
class Io_stream_wtr_gzip extends Io_stream_wtr_base {
	@Override public byte Tid() {return Io_stream_.Tid_gzip;}
	@Override public java.io.OutputStream Wrap_stream(java.io.OutputStream stream) {
		try {return new java.util.zip.GZIPOutputStream(stream);}
		catch (Exception exc) {throw Err_.new_fmt_("failed to open gz stream");}
	}
}
class Io_stream_wtr_zip implements Io_stream_wtr {
	private  java.util.zip.ZipOutputStream zip_stream;	
	@Override public byte Tid() {return Io_stream_.Tid_zip;}
	public Io_url Url() {return url;} public Io_stream_wtr Url_(Io_url v) {url = v; trg_bfr = null; return this;} private Io_url url = Io_url_.Empty;
	public void Trg_bfr_(Bry_bfr v) {trg_bfr = v;} private Bry_bfr trg_bfr; private java.io.ByteArrayOutputStream mem_stream;
	@SuppressWarnings("resource") // rely on zip_stream to close bry_stream 
	public Io_stream_wtr Open() {
		java.io.OutputStream bry_stream;
		if (trg_bfr == null) {
			if (!Io_mgr.I.ExistsFil(url)) Io_mgr.I.SaveFilStr(url, "");	// create file if it doesn't exist
			try {bry_stream = new java.io.FileOutputStream(url.Xto_api());}
			catch (Exception exc) {throw Err_.new_fmt_("open failed: url={0}", url.Raw());}
		}
		else {
			mem_stream = new java.io.ByteArrayOutputStream();
			bry_stream = mem_stream;
		}
		zip_stream = new java.util.zip.ZipOutputStream(bry_stream);
		java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("file");
		try {zip_stream.putNextEntry(entry);}
		catch (Exception exc) {throw Err_.new_fmt_("open failed: url={0}", url.Raw());}
		return this;
	}
	public void Write(byte[] bry, int bgn, int len) {
		try {zip_stream.write(bry, bgn, len);}
		catch (Exception exc) {throw Err_.new_fmt_("write failed: url={0} bgn={1} len={2}", url.Raw(), bgn, len);}
	}
	public void Flush() {// fixed as of DATE:2014-04-15
		try {
			zip_stream.closeEntry();
			zip_stream.close();
			if (trg_bfr != null)
				trg_bfr.Add(mem_stream.toByteArray());
			zip_stream.flush();
		}
		catch (Exception e) {throw Err_.new_fmt_("flush failed: url={0}", url.Raw());}
	}
	public void Rls() {
		try {
			if (zip_stream != null) zip_stream.close();
			if (mem_stream != null) mem_stream.close();
		}
		catch (Exception e) {throw Err_.new_fmt_("close failed: url={0}", url.Raw());}
	}
	public byte[] To_ary_and_clear() {
		byte[] rv = trg_bfr.Xto_bry_and_clear();
		this.Rls();
		return rv;
	}
}
class Io_stream_wtr_file implements Io_stream_wtr {
	IoStream bry_stream; 
	@Override public byte Tid() {return Io_stream_.Tid_raw;}
	public Io_url Url() {return url;} public Io_stream_wtr Url_(Io_url v) {url = v; return this;} Io_url url;
	public void Trg_bfr_(Bry_bfr v) {trg_bfr = v;} private Bry_bfr trg_bfr; java.io.ByteArrayOutputStream mem_stream;
	public Io_stream_wtr Open() {
		try {
			if (trg_bfr == null)
				bry_stream = Io_mgr.I.OpenStreamWrite(url);
		}
		catch (Exception exc) {throw Err_.new_fmt_("open failed: url={0}", url.Raw());}
		return this;
	}
	public void Write(byte[] bry, int bgn, int len) {
		if (trg_bfr == null) {
			try {bry_stream.Write(bry, bgn, len);}
			catch (Exception exc) {throw Err_.new_fmt_("write failed: url={0} bgn={1} len={2}", url.Raw(), bgn, len);}
		}
		else
			trg_bfr.Add_mid(bry, bgn, bgn + len);
	}
	public byte[] To_ary_and_clear() {		 
		return trg_bfr == null ? Io_mgr.I.LoadFilBry(url) : trg_bfr.Xto_bry_and_clear();
	}
	public void Flush() {
		if (trg_bfr == null)
			bry_stream.Flush();
	}
	public void Rls() {
		try {
			if (trg_bfr == null)
				bry_stream.Rls();
		}
		catch (Exception e) {throw Err_.new_fmt_("close failed: url={0}", url.Raw());}
	}
}
