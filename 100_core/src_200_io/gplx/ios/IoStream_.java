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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
public class IoStream_ {
	public static final IoStream Null = new IoStream_null();
	public static IoStream				mem_txt_(Io_url url, String v)	{return IoStream_mem.rdr_txt_(url, v);}
	public static IoStream				ary_(byte[] v)					{return IoStream_mem.rdr_ary_(Io_url_.Empty, v);}
	public static final byte Mode_rdr = 0, Mode_wtr_create = 1, Mode_wtr_append = 2, Mode_wtr_update = 3;
	public static IoStream				stream_rdr_()					{return new IoStream_stream_rdr();}
	public static IoStream				stream_input_(Io_url url)		{return new IoStream_stream_rdr().UnderRdr_(input_stream_(url));}
	public static Object				input_stream_(Io_url url)		{
				try {
			return new java.io.FileInputStream(url.Raw());
		} catch (Exception e) {throw Err_.new_fmt_("file not found: {0}", url.Raw());}
			}
}
class IoStream_null implements IoStream {
	public Object UnderRdr() {return null;}
	public Io_url Url() {return Io_url_.Empty;}
	public long Pos() {return -1;}
	public long Len() {return -1;}
	public int ReadAry(byte[] array) {return -1;}
	public int Read(byte[] array, int offset, int count) {return -1;}
	public long Seek(long pos) {return -1;}
	public void WriteAry(byte[] ary) {}
	public void Write(byte[] array, int offset, int count) {}
	public void Transfer(IoStream trg, int bufferLength) {}
	public void Flush() {}
	public void Write_and_flush(byte[] bry, int bgn, int end) {}
	public void Rls() {}
}
class IoStream_base implements IoStream {
	@gplx.Virtual public Io_url Url() {return url;} Io_url url = Io_url_.Empty;
	public void Transfer(IoStream trg, int bufferLength) {
		byte[] buffer = new byte[bufferLength];
		int read = -1;
		while (read != 0) {
			read = this.Read(buffer, 0, bufferLength);
			trg.Write(buffer, 0, read);
		}
		trg.Flush();
	}
	public int ReadAry(byte[] ary) {return this.Read(ary, 0, ary.length);}
	public void WriteAry(byte[] ary) {this.Write(ary, 0, ary.length);}
		@gplx.Virtual public Object UnderRdr() {return under;}
	@gplx.Virtual public void UnderRdr_(Object v) {this.under = (RandomAccessFile)v;}
	@gplx.Virtual public long Pos() {return pos;} long pos;
	@gplx.Virtual public long Len() {return length;} long length;
	@gplx.Virtual public int Read(byte[] array, int offset, int count) {
		try 	{
			int rv = under.read(array, offset, count);
			return rv == -1 ? 0 : rv;	// NOTE: fis returns -1 if nothing read; .NET returned 0; Hash will fail if -1 returned (will try to create array of 0 length)
		}	// NOTE: fis keeps track of offset, only need to pass in array (20110606: this NOTE no longer seems to make sense; deprecate)
		catch 	(IOException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "file read failed").Add("url", url);}
	}
	public long Seek(long seek_pos) {
		try {
			under.seek(seek_pos);
			pos = under.getFilePointer();
			return pos;
		}
		catch 	(IOException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "seek failed").Add("url", url);}
	}
	@gplx.Virtual public void Write(byte[] array, int offset, int count) {bfr.Add_mid(array, offset, offset + count); this.Flush();} Bry_bfr bfr = Bry_bfr.reset_(16);
	public void Write_and_flush(byte[] bry, int bgn, int end) {
//		ConsoleAdp._.WriteLine(bry.length +" " + bgn + " " + end);
		Flush();// flush anything already in buffer
		int buffer_len = Io_mgr.Len_kb * 16;
		byte[] buffer = new byte[buffer_len];
		int buffer_bgn = bgn; boolean loop = true;
		while (loop) {
			int buffer_end = buffer_bgn + buffer_len;
			if (buffer_end > end) {
				buffer_end = end;
				buffer_len = end - buffer_bgn;
				loop = false;
			}
			for (int i = 0; i < buffer_len; i++)
				buffer[i] = bry[i + buffer_bgn];
			try 	{under.write(buffer, 0, buffer_len);}
			catch 	(IOException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "write failed").Add("url", url);}
			buffer_bgn = buffer_end;
		}
//		this.Rls();
//		OutputStream output_stream = null;
//		try 	{
//			output_stream = new FileOutputStream(url.Xto_api());
//			bry = ByteAry_.Mid(bry, bgn, end);
//			output_stream.write(bry, 0, bry.length);
//		}
//		catch 	(IOException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "write failed").Add("url", url);}
//		finally {
//		    if (output_stream != null) {
//		    	try {output_stream.close();}
//		    	catch (IOException ignore) {}			
//		    }
//		}
	}
	@gplx.Virtual public void Flush() {
		try {
			if (mode_is_append) under.seek(under.length());
//			else				under.seek(0);
		}
		catch 	(IOException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "seek failed").Add("url", url);}
		try {under.write(bfr.Bfr(), 0, bfr.Len());}
		catch 	(IOException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "write failed").Add("url", url);}
		bfr.Clear();
	}
	@gplx.Virtual public void Rls() {
		IoEngine_system.Closeable_close(under, url, true);
	}
	RandomAccessFile under; boolean mode_is_append; byte mode;
	public static IoStream_base rdr_wrapper_() {return new IoStream_base();}
	public static IoStream_base new_(Io_url url, int mode) {
		IoStream_base rv = new IoStream_base();
		rv.url = url;
		rv.mode = (byte)mode;
		File file = new File(url.Xto_api());
		String ctor_mode = "";
		switch (mode) {	// mode; SEE:NOTE_1
			case IoStream_.Mode_wtr_append:
				rv.mode_is_append = mode == IoStream_.Mode_wtr_append;
				ctor_mode = "rws";
				break;
			case IoStream_.Mode_wtr_create:
				ctor_mode = "rws";
				break;
			case IoStream_.Mode_rdr:
				ctor_mode = "r";
				break;
		}
		try {rv.under = new RandomAccessFile(file, ctor_mode);} 
		catch (FileNotFoundException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "file open failed").Add("url", url);}
		if (mode == IoStream_.Mode_wtr_create) {
			try {rv.under.setLength(0);}
			catch (IOException e) {throw Err_.err_key_(e, IoEngineArgs._.Err_IoException, "file truncate failed").Add("url", url);}
		}
		rv.length = file.length();
		return rv;
	}
	public static IoStream_base new_(Object stream) {
		IoStream_base rv = new IoStream_base();
//		rv.stream = (System.IO.Stream)stream;
		rv.url = Io_url_.Empty;
		return rv;
	}
	}
/*
NOTE_1:stream mode
my understanding of mode
rw: read/write async?
rws: read/write sync; write content + metadata changes
rwd: read/write sync; write content
*/
//#}