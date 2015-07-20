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
import java.io.*;
class Http_client_rdr__stream implements Http_client_rdr {
		private BufferedReader br;
		public void Stream_(Object o) {
				this.br = new BufferedReader(new InputStreamReader((InputStream)o, java.nio.charset.Charset.forName("UTF-8")));
			}
	public String Read_line() {
				try {return br.readLine();}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Read_line failed");}			
			}
	public byte[] Read_line_as_bry() {return Bry_.new_u8(Read_line());}
	public void Rls() {
				try {br.close();}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Rls failed");}			
		}
}
