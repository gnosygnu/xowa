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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xoi_css_offline_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoi_css_offline_mgr_fxt fxt = new Xoi_css_offline_mgr_fxt();
	@Test  public void Basic() {
//			fxt.Test_extract_url_warn("url("				, "end of stream");
//			fxt.Test_extract_url_warn("url(abc"				, "end not found");
//			fxt.Test_extract_url_warn("url()"				, "url is empty");
		fxt.Test_extract_url_pass("url('a/b')"			, "url is empty");
	}
}
class Xoi_css_offline_mgr_fxt {
	private Xoi_css_url_info info = new Xoi_css_url_info();
	private Gfo_usr_dlg usr_dlg = null;
//		private Xoi_css_offline_mgr mgr;
	public void Clear() {
		info.Init(usr_dlg);
//			mgr = new Xoi_css_offline_mgr();
	}
	public void Test_extract_url_warn(String raw, String err) {
	}
	public void Test_extract_url_pass(String src_str, String expd) {
		byte[] src = Bry_.new_utf8_(src_str);
		Test_extract_url(src);
//			String actl = String_.new_ascii_(src, info.Bgn_pos(), info.End_pos());
//			Tfds.Eq(expd, actl);
	}
	private void Test_extract_url(byte[] src) {
		int src_len = src.length;
		info.Clear();
		int tkn_bgn = Bry_finder.Find_fwd(src, Xoi_css_offline_mgr.Tkn_url_bry, 0, src_len);
		int tkn_end = tkn_bgn + Xoi_css_offline_mgr.Tkn_url_bry.length;
		Xoi_css_offline_mgr.Process_url(src, src_len, tkn_bgn, tkn_end, info);
	}
}	
