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
import java.io.InputStream;
import gplx.core.ios.streams.*;
public class Io_stream_rdr_process implements Io_stream_rdr {
	    private Process process;
    private InputStream stream_read;
		private String[] process_args;
	Io_stream_rdr_process(Io_url process_exe, Io_url stream_url, String[] process_args) {this.process_exe = process_exe; this.url = stream_url; this.process_args = process_args;}
	public byte Tid() {return Io_stream_tid_.Tid__bzip2;}	// for now, classify as bzip2; not sure if separate tid is necessary
	public boolean Exists() {return this.Len() > 0;}
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {url = v; return this;} private Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len;
	public Io_url Process_exe() {return process_exe;} private Io_url process_exe;
	public Io_stream_rdr Open() {
				ProcessBuilder pb = new ProcessBuilder(process_args);
    	pb.redirectErrorStream(false);
    	try {process = pb.start();}
    	catch (Exception e) {throw Err_.new_exc(e, "core", "process init failed", "args", String_.AryXtoStr(process_args));}
    	stream_read = process.getInputStream();
		return this;
			}
	public void Open_mem(byte[] v) {throw Err_.new_unimplemented();}
	public Object Under() {throw Err_.new_unimplemented();}

	public int Read(byte[] bry, int bgn, int len) {
				try {
			int rv = 0;
			int cur_pos = bgn;
			int cur_len = len;
			while (true) {
				int read = stream_read.read(bry, cur_pos, cur_len);
				if (read <= 0) break;
				rv += read;
				cur_pos += read;
				cur_len -= read;
				if (rv >= len) break;
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "io", "process read failed", "bgn", bgn, "len", len);}
			}
	public long Skip(long len) {
				try {return stream_read.skip(len);}
		catch (Exception e) {throw Err_.new_exc(e, "io", "process skip failed", "len", len);}
			}
	public void Rls() {
				try {stream_read.close();}
		catch (Exception e) {throw Err_.new_exc(e, "io", "process rls failed");}
		process.destroy();
			}
	public static Io_stream_rdr_process new_(Io_url process_exe, Io_url stream_url, String... process_args) {return new Io_stream_rdr_process(process_exe, stream_url, process_args);}
}
