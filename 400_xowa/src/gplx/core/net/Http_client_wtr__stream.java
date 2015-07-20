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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.ios.*;
import java.io.*;
class Http_client_wtr__stream implements Http_client_wtr {	
	private final byte[] tmp_stream_bry = new byte[1024];
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
