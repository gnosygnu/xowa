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
import java.io.InputStream;
public class Io_stream_rdr_process implements Io_stream_rdr {
	    private Process process;
    private InputStream stream_read;
		private String[] process_args;
	Io_stream_rdr_process(Io_url process_exe, Io_url stream_url, String[] process_args) {this.process_exe = process_exe; this.url = stream_url; this.process_args = process_args;}
	public byte Tid() {return Io_stream_.Tid_bzip2;}	// for now, classify as bzip2; not sure if separate tid is necessary
	public Io_url Url() {return url;} public Io_stream_rdr Url_(Io_url v) {url = v; return this;} private Io_url url;
	public long Len() {return len;} public Io_stream_rdr Len_(long v) {len = v; return this;} private long len;
	public Io_url Process_exe() {return process_exe;} private Io_url process_exe;
	public Io_stream_rdr Open() {
				ProcessBuilder pb = new ProcessBuilder(process_args);
    	pb.redirectErrorStream(false);
    	try {process = pb.start();}
    	catch (Exception e) {throw Err_.err_(e, "process init failed: {0} {1}", String_.AryXtoStr(process_args), Err_.Message_gplx_brief(e));}
    	stream_read = process.getInputStream();
		return this;
			}
	public void Open_mem(byte[] v) {throw Err_.not_implemented_();}
	public Object Under() {throw Err_.not_implemented_();}

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
		} catch (Exception e) {throw Err_.err_(e, "process read failed: bgn={0} len={1} err={2}", bgn, len, Err_.Message_gplx_brief(e));}
			}
	public long Skip(long len) {
				try {return stream_read.skip(len);}
		catch (Exception e) {throw Err_.err_(e, "process skip failed: len={0} err={1}", len, Err_.Message_gplx_brief(e));}
			}
	public void Rls() {
				try {stream_read.close();}
		catch (Exception e) {throw Err_.err_(e, "process rls failed: err={0}", Err_.Message_gplx_brief(e));}
		process.destroy();
			}
	public static Io_stream_rdr_process new_(Io_url process_exe, Io_url stream_url, String... process_args) {return new Io_stream_rdr_process(process_exe, stream_url, process_args);}
}
