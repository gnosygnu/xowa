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
package gplx.xowa.bldrs.imports.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.imports.*;
import org.junit.*;
public class Xob_categorylinks_base_tst {
	@Before public void init() {fxt.Clear();} private Xob_base_fxt fxt = new Xob_base_fxt();
	@Test   public void Basic() {
		Io_url src_fil = Io_url_.new_fil_("mem/temp/sql_dump.sql");
		fxt	.Init_fil(src_fil, String_.Concat
		(	Xob_categorylinks_sql.Tbl_categorylinks
		,	"INSERT INTO `categorylinks` VALUES"
		,	" (1,'Ctg_2','PAGE_2a','2013-04-15 01:02:03','','uppercase','page')"
		,	",(2,'Ctg_1','PAGE_1b','2013-04-15 01:02:03','','uppercase','page')"
		,	",(3,'Ctg_1','PAGE_1a','2013-04-15 01:02:03','','uppercase','page')"
		,	";"
		))
		.Exec_cmd(Xob_categorylinks_txt.KEY, GfoMsg_.basic_(Xob_categorylinks_base.Invk_src_fil_, src_fil))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.link_sql/make/0000000000.csv", String_.Concat_lines_nl
		(	"Ctg_1|p|PAGE_1a|!!!!$|#8DDL|"
		,	"Ctg_1|p|PAGE_1b|!!!!#|#8DDL|"
		,	"Ctg_2|p|PAGE_2a|!!!!\"|#8DDL|"
		))
		;
	}
	@Test   public void SuperEarths() {	// PURPOSE: handle multi-field sort
		Io_url src_fil = Io_url_.new_fil_("mem/temp/sql_dump.sql");
		fxt	.Init_fil(src_fil, String_.Concat
		(	Xob_categorylinks_sql.Tbl_categorylinks
		,	"INSERT INTO `categorylinks` VALUES"
		,	" (1,'Super-Earths','PAGE_1a','2013-04-15 01:02:03','','uppercase','page')"
		,	",(2,'Super-Earths_in_the_habitable_zone','PAGE_1b','2013-04-15 01:02:03','','uppercase','page')"
		,	";"
		))
		.Exec_cmd(Xob_categorylinks_txt.KEY, GfoMsg_.basic_(Xob_categorylinks_base.Invk_src_fil_, src_fil))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.link_sql/make/0000000000.csv", String_.Concat_lines_nl
		(	"Super-Earths|p|PAGE_1a|!!!!\"|#8DDL|"
		,	"Super-Earths_in_the_habitable_zone|p|PAGE_1b|!!!!#|#8DDL|"
		))
		;
	}
	@Test   public void Sortkey_has_newline() {	// PURPOSE: sortkey sometimes has format of "sortkey\ntitle"; EX: "WALES, JIMMY\nJIMMY WALES"; discard 2nd for hard-disk space savings
		Io_url src_fil = Io_url_.new_fil_("mem/temp/sql_dump.sql");
		fxt	.Init_fil(src_fil, String_.Concat
		(	Xob_categorylinks_sql.Tbl_categorylinks
		,	"INSERT INTO `categorylinks` VALUES"
		,	" (1,'Ctg_1','LAST,FIRST\\nFIRST LAST','2013-04-15 01:02:03','','uppercase','page')"
		,	";"
		))
		.Exec_cmd(Xob_categorylinks_txt.KEY, GfoMsg_.basic_(Xob_categorylinks_base.Invk_src_fil_, src_fil))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.link_sql/make/0000000000.csv", String_.Concat_lines_nl
		(	"Ctg_1|p|LAST,FIRST|!!!!\"|#8DDL|"
		))
		;
	}
}
