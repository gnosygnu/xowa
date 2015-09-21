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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.core.intls.*;
public class Hiero_mw_tables_parser_tst {
	@Before public void init() {fxt.Reset();} private Hiero_mw_tables_parser_fxt fxt = new Hiero_mw_tables_parser_fxt();
	@Test  public void Basic() {// DATE:2014-04-19
		fxt.Test_bld_all
		( String_.Concat_lines_nl_skip_last
		( "$wh_prefabs = array("
		, "  \"a&A1\","
		, "  \"Z6&A1\","
		, ");"
		, ""
		, "$wh_files   = array("
		, "  \"a&A1\" => array( 37, 38 ),"
		, "  \"Z98A\" => array( 5, 15 ),"
		, ");"
		, ""
		, "$wh_phonemes	=	array("
		, "	\"mSa\"	=>	\"A12\","
		, "	\"\\\"]\"	=>	\"\","
		, ");"
		)
		, String_.Concat_lines_nl_skip_last
		( "prefabs.srl.load_by_str('"
		, "a&A1"
		, "Z6&A1"
		, "');"
		, ""
		, "files.srl.load_by_str('"
		, "a&A1|37|38"
		, "Z98A|5|15"
		, "');"
		, ""
		, "phonemes.srl.load_by_str("
		, "<:['"
		, "mSa|A12"
		, "\"]|"
		, "']:>"
		, ");"
		)
		);
	}
}
class Hiero_mw_tables_parser_fxt {
	private Hiero_mw_tables_parser parser = new Hiero_mw_tables_parser();
	public void Reset() {}
	public void Test_bld_all(String raw, String expd) {
		Io_url load_url = Io_url_.mem_fil_("mem/hiero/load.php");
		Io_url save_url = Io_url_.mem_fil_("mem/hiero/save.php");
		Io_mgr.I.SaveFilStr(load_url, raw);
		parser.Bld_all(load_url, save_url);
		Tfds.Eq_str_lines(expd, Io_mgr.I.LoadFilStr(save_url));
	}
	public void Exec_bld_all(String load, String save) {
		parser.Bld_all(Io_url_.new_fil_(load), Io_url_.new_fil_(save));
	}
}
