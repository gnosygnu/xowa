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
package gplx.xowa.xtns.scribunto.engines.process; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import org.junit.*;
import gplx.ios.*;
public class Process_stream_rdr_tst {
	@Before public void init() {fxt.Clear();} Scrib_lua_srl_fxt fxt = new Scrib_lua_srl_fxt();
	@Test  public void Body_basic() {
		Process_stream_rdr_fxt fxt2 = new Process_stream_rdr_fxt().Init();
		fxt2.Init_src_str_("abcd").Test_read_body();				// read_1
		fxt2.Init_src_str_("abcde").Test_read_body();				// read_2
		fxt2.Init_src_str_("abcdefghijklm").Test_read_body();		// read_3
		fxt2.Init_src_str_w_nl_("a\\nb").Test_read_body();			// nl; read_1
		fxt2.Init_src_str_w_nl_("\\n\\\\ab").Test_read_body();		// nl; read_2
		fxt2.Init_src_str_w_nl_("\\n\\\\a").Test_read_body();		// nl; backslash
		fxt2.Init_src_str_w_nl_("a\\nb\\nc").Test_read_body();		// nl; straddling reads
	}
}
class Process_stream_rdr_fxt {
	public Process_stream_rdr_fxt Init() {
		if (process == null) {
			bry_header = new byte[16];
			bry_body = Bry_.Empty;
			rdr = new IoStream_mock().Read_limit_(5);
			process = new Process_stream_rdr(new byte[16], new byte[16]);
		}
		return this;
	}	byte[] bry_header, bry_body; Process_stream_rdr process; IoStream_mock rdr;
	public Process_stream_rdr_fxt Init_src_str_(String v) {this.src_bry = Bry_.new_a7(v); src_len = src_bry.length; expd_str = v; return this;} private byte[] src_bry;
	public Process_stream_rdr_fxt Init_src_len_(int v) {this.src_len = v; return this;} private int src_len;
	public Process_stream_rdr_fxt Expd_str_(String v) {this.expd_str = v; return this;} private String expd_str;
	public Process_stream_rdr_fxt Init_src_str_w_nl_(String v) {
		this.Init_src_str_(v);
		int len = src_bry.length;
		for (int i = 0; i < len; i++) {
			byte b = src_bry[i];
			switch (b) {
				case Byte_ascii.Backslash:
					++i;
					b = src_bry[i];
					switch (b) {
						case Byte_ascii.Backslash: 	bfr.Add_byte(Byte_ascii.Backslash); break;
						case Byte_ascii.Ltr_n: 		bfr.Add_byte(Byte_ascii.NewLine); break;
						case Byte_ascii.Ltr_r: 		bfr.Add_byte(Byte_ascii.CarriageReturn); break;
						default: 					throw Err_.unhandled(b);
					}
					break;
				default:
					bfr.Add_byte(b);
					break;
			}
		}
		expd_str = bfr.Xto_str_and_clear();
		return this;
	} 	Bry_bfr bfr = Bry_bfr.reset_(128);
	public void Test_read_body() {
		rdr.Data_bry_(src_bry);
		byte[] bry_body = new byte[src_len];
		rdr.Reset();
		byte[] rv = process.Read_body(bry_body, src_len, rdr);
		Tfds.Eq(expd_str, String_.new_a7(rv));
		Tfds.Eq(src_bry.length, rdr.Data_bry_pos());
	}
}
