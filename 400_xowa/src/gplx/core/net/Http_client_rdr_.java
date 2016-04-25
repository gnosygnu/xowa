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
public class Http_client_rdr_ {
	public static Http_client_rdr new_stream()	{return new Http_client_rdr__stream();}
	public static Http_client_rdr new_mem()		{return new Http_client_rdr__mem();}
}
class Http_client_rdr__mem implements Http_client_rdr {
	private String[] ary; private int ary_len; private int idx;
	public void Stream_(Object o) {
		this.ary = (String[])o;
		this.ary_len = ary.length;
		this.idx = 0;
	}
	public String Read_line() {
		return idx == ary_len ? null : ary[idx++];
	}
	public byte[] Read_line_as_bry() {return Bry_.new_u8(Read_line());}
	public int Read_char_ary(char[] ary, int bgn, int len) {throw Err_.new_unimplemented();}
	public void Rls() {}
}
