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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import org.junit.*; import gplx.xowa.bldrs.*;
public class Xoctg_hiddencat_ttl_wkr_tst {
	@Before public void init() {fxt.Clear();} private Xob_base_fxt fxt = new Xob_base_fxt();
	@Test   public void Basic() {
		fxt
		.Init_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.hiddencat_sql/make/0000000000.csv", String_.Concat_lines_nl
		(	"!!!!#|"
		,	"!!!!$|"
		,	"!!!!%|"
		))
		.Init_fil("mem/xowa/wiki/en.wikipedia.org/site/id/00/00/00/00/0000000000.xdat", String_.Concat_lines_nl
		(	"header_line"
		,	"!!!!#|C"
		,	"!!!!%|A"
		))
		.Exec_cmd(Xob_cmd_keys.Key_tdb_cat_hidden_ttl)
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.hiddencat_ttl/dump/0000000000.csv", String_.Concat_lines_nl
		(	"C|!!!!#"
		,	"A|!!!!%"
		))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.hiddencat_ttl/make/0000000000.csv", String_.Concat_lines_nl
		(	"A|!!!!%"
		,	"C|!!!!#"
		))
		;
	}
}
