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
package gplx.core.ios.streams; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
public class Io_stream_rdr_ {
	public static Io_stream_rdr file_(Io_url url)				{return new Io_stream_rdr_file().Url_(url);}
	public static Io_stream_rdr file_(java.io.InputStream strm)	{return new Io_stream_rdr_file().Under_(strm);}	
	public static Io_stream_rdr zip_(Io_url url)				{return new Io_stream_rdr_zip().Url_(url);}
	public static Io_stream_rdr gzip_(Io_url url)				{return new Io_stream_rdr_gzip().Url_(url);}
	public static Io_stream_rdr bzip2_(Io_url url)				{return new Io_stream_rdr_bzip2().Url_(url);}
	public static Io_stream_rdr new_by_url_(Io_url url) {
		String ext = url.Ext();
		if		(String_.Eq(ext, Io_stream_.Ext_zip)) 	return gplx.core.ios.streams.Io_stream_rdr_.zip_(url);
		else if	(String_.Eq(ext, Io_stream_.Ext_gz)) 	return gplx.core.ios.streams.Io_stream_rdr_.gzip_(url);
		else if	(String_.Eq(ext, Io_stream_.Ext_bz2)) 	return gplx.core.ios.streams.Io_stream_rdr_.bzip2_(url);
		else 											return gplx.core.ios.streams.Io_stream_rdr_.file_(url);
	}
	public static Io_stream_rdr new_by_tid_(byte tid) {
		switch (tid) {
			case Io_stream_.Tid_raw:					return new Io_stream_rdr_file();
			case Io_stream_.Tid_zip: 					return new Io_stream_rdr_zip();
			case Io_stream_.Tid_gzip: 					return new Io_stream_rdr_gzip();
			case Io_stream_.Tid_bzip2: 					return new Io_stream_rdr_bzip2();
			default:									throw Err_.new_unhandled(tid);
		}
	}
	public static byte[] Load_all(Io_url url) {
		Io_stream_rdr rdr = new_by_url_(url);
		Bry_bfr rv = Bry_bfr_.New();
		try {
			rdr.Open();
			return Load_all_as_bry(rv, rdr);
		}
		finally {rdr.Rls();}
	}
	public static String Load_all_as_str(Io_stream_rdr rdr) {return String_.new_u8(Load_all_as_bry(rdr));}
	public static byte[] Load_all_as_bry(Io_stream_rdr rdr) {return Load_all_as_bry(Bry_bfr_.New(), rdr);}
	public static byte[] Load_all_as_bry(Bry_bfr rv, Io_stream_rdr rdr) {
		Load_all_to_bfr(rv, rdr);
		return rv.To_bry_and_clear();
	}
	public static void Load_all_to_bfr(Bry_bfr rv, Io_stream_rdr rdr) {
		try {
			byte[] bry = new byte[4096];
			while (true) {
				int read = rdr.Read(bry, 0, 4096);
				if (read < gplx.core.ios.streams.Io_stream_rdr_.Read_done_compare) break; 
				rv.Add_mid(bry, 0, read);
			}
		} finally {rdr.Rls();}
	}
	public static final    Io_stream_rdr Noop = new Io_stream_rdr_noop();
	public static Io_stream_rdr mem_(String v) {return mem_(Bry_.new_u8(v));}
	public static Io_stream_rdr mem_(byte[] v) {
		Io_stream_rdr rv = new Io_stream_rdr_adp(Stream_new_mem(v));
		rv.Len_(v.length);
		return rv;
	}	
	public static java.io.InputStream Stream_new_mem(byte[] v) {	
		return new java.io.ByteArrayInputStream(v);					
	}
	public static boolean Stream_close(java.io.InputStream stream) {	
		try {
			if (stream != null)
				stream.close();									
			return true;
		} catch (Exception e) {Err_.Noop(e); return false;}
	}
	public static int Stream_read_by_parts(java.io.InputStream stream, int part_len, byte[] bry, int bgn, int len) {
		/*
		NOTE: BZip2CompressorInputStream will fail if large len is used
		Instead, make smaller requests and fill bry
		*/
		try {
			int rv = 0;
			int end = bgn + len;
			int cur = bgn;
			while (true) {
				int bry_len = part_len;		// read in increments of part_len
				if (cur + bry_len > end)	// if cur + 8 kb > bry_len, trim to end; EX: 9 kb bry passed; 1st pass is 8kb, 2nd pass should be 1kb, not 8 kb;   
					bry_len = end - cur;
				if (cur == end) break;		// no more bytes needed; break; EX: 8 kb bry passed; 1st pass is 8kb; 2nd pass is 0 and cur == end
				int read = stream.read(bry, cur, bry_len);		
				if (read == gplx.core.ios.streams.Io_stream_rdr_.Read_done) // read done; end
					break;
				rv += read;
				cur += read;
			}
			return rv;
		}
		catch (Exception exc) {
			throw Err_.new_exc(exc, "io", "read failed", "bgn", bgn, "len", len);
		}
	}
	public static final int Read_done = -1;
	public static final int Read_done_compare = 1;
}
class Io_stream_rdr_noop implements Io_stream_rdr {
	public Object Under() {return null;}
	public byte Tid() {return Io_stream_.Tid_null;}
	public boolean Exists() {return false;}
	public Io_url Url() {return Io_url_.Empty;} public Io_stream_rdr Url_(Io_url v) {return this;}
	public long Len() {return Io_mgr.Len_null;} public Io_stream_rdr Len_(long v) {return this;}
	public void Open_mem(byte[] v) {}
	public Io_stream_rdr Open() {return this;}
	public int Read(byte[] bry, int bgn, int len) {return Io_stream_rdr_.Read_done;}
	public long Skip(long len) {return Io_stream_rdr_.Read_done;}
	public void Rls() {}
}
class Io_stream_rdr_adp implements Io_stream_rdr {
	private java.io.InputStream strm;	
	public Io_stream_rdr_adp(java.io.InputStream strm) {this.strm = strm;} 
	public Object Under() {return strm;}
	public byte Tid() {return Io_stream_.Tid_raw;}
	public boolean Exists() {return len > 0;}
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {this.url = v; return this;} private Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len = Io_mgr.Len_null;
	public void Open_mem(byte[] v) {}
	public Io_stream_rdr Open() {return this;}
	public int Read(byte[] bry, int bgn, int len) {
		try {return strm.read(bry, bgn, len);}	
		catch (Exception e) {throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);}
	}
	public long Skip(long len) {
		try {return strm.skip(len);}	
		catch (Exception e) {throw Err_.new_exc(e, "io", "skip failed", "len", len);}
	}	
	public void Rls() {
		try {strm.close();} 
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Xto_api());}
	}
}
abstract class Io_stream_rdr_base implements Io_stream_rdr {	
	public abstract byte Tid();
	public boolean Exists() {return this.Len() > 0;}
	public Object Under() {return stream;} public Io_stream_rdr Under_(java.io.InputStream v) {this.stream = v; return this;} protected java.io.InputStream stream;
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {this.url = v; return this;} protected Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len = Io_mgr.Len_null;
	public void Open_mem(byte[] v) {
		stream = Wrap_stream(new java.io.ByteArrayInputStream(v));
	}
	public Io_stream_rdr Open() {
		try {stream = Wrap_stream(new java.io.FileInputStream(url.Xto_api()));}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Xto_api());}
		return this;
	}
	public int Read(byte[] bry, int bgn, int len) {
		try {return stream.read(bry, bgn, len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);}
	}
	public long Skip(long len) {		
		try {return stream.skip(len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "skip failed", "len", len);}
	}
	public void Rls() {
		try {stream.close();}
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Xto_api());}
	}
	public abstract java.io.InputStream Wrap_stream(java.io.InputStream stream);
}
class Io_stream_rdr_file extends Io_stream_rdr_base {
	@Override public byte Tid() {return Io_stream_.Tid_raw;}
	public Io_stream_rdr Open() {
		try {
			if (!Io_mgr.Instance.Exists(url))
				stream = Wrap_stream(new java.io.ByteArrayInputStream(Bry_.Empty));
			else {
				if (url.Info().EngineKey() == IoEngine_.MemKey)
					stream = Wrap_stream(new java.io.ByteArrayInputStream(Io_mgr.Instance.LoadFilBry(url.Xto_api())));
				else
					stream = Wrap_stream(new java.io.FileInputStream(url.Xto_api()));
			}
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Xto_api());}
		return this;
	}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {return stream;}
}
class Io_stream_rdr_zip implements Io_stream_rdr {
	@Override public byte Tid() {return Io_stream_.Tid_zip;}
	public boolean Exists() {return this.Len() > 0;}
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {this.url = v; return this;} Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len = Io_mgr.Len_null;
	public Object Under() {return zip_stream;} private java.util.zip.ZipInputStream zip_stream;
	public void Src_bfr_(Bry_bfr v) {this.src_bfr = v;} Bry_bfr src_bfr;
	public void Open_mem(byte[] v) {
		Wrap_stream(new java.io.ByteArrayInputStream(v));
	}
	public Io_stream_rdr Open() {
		try {Wrap_stream(new java.io.FileInputStream(url.Xto_api()));}
		catch (Exception e) {throw Err_.new_exc(e, "io", "open failed", "url", url.Xto_api());}
		return this;
	}
	void Wrap_stream(java.io.InputStream input_stream) {zip_stream = new java.util.zip.ZipInputStream(input_stream);}
	public int Read(byte[] bry, int bgn, int len) {
		try {
			while (true){ 
				int read = zip_stream.read(bry, bgn, len);
				if (read == Io_stream_rdr_.Read_done) {
					if (zip_stream.getNextEntry() == null)
						return Io_stream_rdr_.Read_done;
				}
				else
					return read;
			}
		}
		catch (Exception e) {throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);}
	}
	public long Skip(long len) {		
		try {return zip_stream.skip(len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "skip failed", "len", len);}
	}
	public void Rls() {
		try {zip_stream.close();}
		catch (Exception e) {throw Err_.new_exc(e, "io", "close failed", "url", url.Xto_api());}
	}
}
class Io_stream_rdr_gzip extends Io_stream_rdr_base {
	@Override public byte Tid() {return Io_stream_.Tid_gzip;}
	@Override public int Read(byte[] bry, int bgn, int len) {
		synchronized (this) {
		try {
			int total_read = 0;
			while (true) {  // NOTE: the gz stream reads partially; (request 100; only get back 10); keep reading until entire bfr is full or -1
				int read = stream.read(bry, bgn, len);
				if (read == Io_stream_rdr_.Read_done) break;
				total_read += read;
				if (total_read >= len) break;  // entire bfr full; stop
				bgn += read;  // increase bgn by amount read
				len -= read;  // decrease len by amount read 
			}
			return total_read == 0 ? Io_stream_rdr_.Read_done : total_read;	// gzip seems to allow 0 bytes read (bz2 and zip return -1 instead); normalize return to -1;
		}
		catch (Exception e) {
			throw Err_.new_exc(e, "io", "read failed", "bgn", bgn, "len", len);
		}
		}
	}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {
		try {return new java.util.zip.GZIPInputStream(stream);}
		catch (Exception exc) {throw Err_.new_wo_type("failed to open gz stream");}
	}
}
class Io_stream_rdr_bzip2 extends Io_stream_rdr_base {
	@Override public byte Tid() {return Io_stream_.Tid_bzip2;}
	@Override public java.io.InputStream Wrap_stream(java.io.InputStream stream) {
		try {return new org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream(stream, true);}
		catch (Exception exc) {throw Err_.new_wo_type("failed to open bzip2 stream");}
	}
	@Override public int Read(byte[] bry, int bgn, int len) {
		return Io_stream_rdr_.Stream_read_by_parts(stream, Read_len, bry, bgn, len);
	}
	private static final int Read_len = Io_mgr.Len_mb * 128;
}
