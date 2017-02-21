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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import java.io.*;
class Http_client_wtr__stream implements Http_client_wtr {	
	private final    byte[] tmp_stream_bry = new byte[1024];
		private DataOutputStream stream;
		public void Stream_(Object o) {
				this.stream = new DataOutputStream((OutputStream)o);
			}
	public void Write_bry(byte[] bry) {
				try {stream.write(bry);}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Write_bry failed");} 			
			}
	public void Write_str(String s) {
				try {stream.writeBytes(s);}
		catch (Exception e) {throw Err_.new_exc(e, "net", "Write_str failed");} 
			}
	public void Write_mid(byte[] bry, int bgn, int end) {
				try {stream.write(bry, bgn, end - bgn);}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Write_mid failed");} 			
			}
	public void Write_stream(Io_stream_rdr stream_rdr) {
		synchronized (tmp_stream_bry) {
			int read = 0;
			while (true) {
				read = stream_rdr.Read(tmp_stream_bry, 0, 1024);
				if (read == -1) break;
				Write_mid(tmp_stream_bry, 0, read);
			}
		}
	}	
	public void Rls() {
				try {stream.close();}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Rls failed");}			
			}
}
