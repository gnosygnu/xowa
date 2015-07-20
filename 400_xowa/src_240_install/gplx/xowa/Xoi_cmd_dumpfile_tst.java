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
public class Xoi_cmd_dumpfile_tst {		
	@Before public void init() {fxt.Clear();} private Xoi_cmd_dumpfile_fxt fxt = new Xoi_cmd_dumpfile_fxt();
	@Test   public void Bz2__unzip() {
		fxt	.Exec_parse_msg("mem/en.wikipedia.org/fil.xml.bz2", "", "unzip")
			.Test_domain("en.wikipedia.org")
			.Test_vals("mem/en.wikipedia.org/fil.xml.bz2", "mem/en.wikipedia.org/fil.xml", true)
			;
	}
	@Test   public void Bz2__unzip__assert_xml_ext() {	// xml ext relies on removing ".bz2" from ".xml.bz2"; if just ".bz2" add an ".xml"
		fxt	.Exec_parse_msg("mem/en.wikipedia.org/fil.bz2", "", "unzip")
			.Test_vals("mem/en.wikipedia.org/fil.bz2", "mem/en.wikipedia.org/fil.xml", true)
			;
	}
	@Test   public void Bz2__direct() {
		fxt	.Exec_parse_msg("mem/en.wikipedia.org/fil.bz2", "", "")
			.Test_vals("mem/en.wikipedia.org/fil.bz2", null, false)				
			;
	}
	@Test   public void Xml__unzip_n() {
		fxt	.Exec_parse_msg("mem/en.wikipedia.org/fil.xml", "", "")
			.Test_vals(null, "mem/en.wikipedia.org/fil.xml", false)
			;
	}
	@Test   public void Xml__unzip_y() {
		fxt	.Exec_parse_msg("mem/en.wikipedia.org/fil.xml", "", "")
			.Test_vals(null, "mem/en.wikipedia.org/fil.xml", false)
			;
	}
}
class Xoi_cmd_dumpfile_fxt {
	public void Clear() {
		dumpfile.Clear();
	}	private Xoi_cmd_dumpfile dumpfile = new Xoi_cmd_dumpfile();
	public Xoi_cmd_dumpfile_fxt Exec_parse_msg(String url, String domain, String args) {
		GfoMsg m = GfoMsg_.new_parse_("").Add("url", url).Add("domain", domain).Add("args", args);
		dumpfile.Parse_msg(m);
		return this;
	}
	public Xoi_cmd_dumpfile_fxt Test_vals(String expd_bz2, String expd_xml, boolean expd_unzip) {
		Eq_url(expd_bz2, dumpfile.Bz2_url());
		Eq_url(expd_xml, dumpfile.Xml_url());
		Tfds.Eq(expd_unzip, dumpfile.Bz2_unzip());
		return this;
	}
	public Xoi_cmd_dumpfile_fxt Test_domain(String expd_domain) {
		Tfds.Eq(expd_domain, String_.new_u8(dumpfile.Domain()));
		return this;
	}
	private void Eq_url(String expd, Io_url actl) {
		if		(expd == null && actl == null) return;
		else if	(expd != null && actl != null) {
			Tfds.Eq(expd, actl.Raw());
		}
		else if	(expd == null) throw Err_.new_wo_type("actl should be null", "expd", expd);
		else if	(actl == null) throw Err_.new_wo_type("actl should not be null", "expd", expd);
	}
}
