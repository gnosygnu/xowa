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
	public int Read_char_ary(char[] ary, int bgn, int len) {
				try {return br.read(ary, bgn, len);}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Read_line failed");}			
			}
	public byte[] Read_line_as_bry() {return Bry_.new_u8(Read_line());}
	public void Rls() {
				try {br.close();}
		catch (IOException e) {throw Err_.new_exc(e, "net", "Rls failed");}			
		}
}
