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
