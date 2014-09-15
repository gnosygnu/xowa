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
package gplx.xowa.html.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.w3c.tidy.Tidy;
import org.junit.*; 
public class Xoh_tidy_wkr_jtidy_tst {
	@Before public void init() {fxt.Clear();} private Jtidy_fxt fxt = new Jtidy_fxt();
	@Test   public void Image_full() {
		fxt.Test_tidy("<a href='http://𐎍𐎁_𐎜'>𐎍𐎁_𐎜</a>", "<a href='http://%F0%90%8E%8D%F0%90%8E%81_%F0%90%8E%9C'>&eth;&#144;&#142;&#141;&eth;&#144;&#142;&#129;_&eth;&#144;&#142;&#156;</a>\r\n");
	}
}
class Jtidy_fxt {
	public void Clear() {		
	}
	public void Test_tidy(String raw, String expd) {
		Tidy tidy = new Tidy();
		tidy.setPrintBodyOnly(true);
		tidy.setWraplen(0);
		tidy.setQuiet(true);
		tidy.setShowWarnings(false);
		tidy.setShowErrors(0);
		ByteArrayInputStream rdr = null;
		try {
			rdr = new ByteArrayInputStream(raw.getBytes("UTF-8"));
		} catch (Exception e) {}
		ByteArrayOutputStream wtr = new ByteArrayOutputStream();
		tidy.parse(rdr, wtr);
		String actl = wtr.toString();
		Test_mgr.Eq_str(expd, actl);
	}
}
class Test_mgr {
	public static void Eq_str(String expd, String actl) {
		if (!expd.equals(actl)) throw new RuntimeException(String.format("expd != actl; expd:%s actl:%s", expd, actl));
	}
}
