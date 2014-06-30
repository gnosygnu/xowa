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
import gplx.ios.*;
public class Xobc_img_dump_ttl_tst {
	Xobc_img_dump_ttl_fxt fxt = new Xobc_img_dump_ttl_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Run
		(	fxt.page_("File:B",  "a")
		,	fxt.page_("Image:A",  "#REDIRECT [[File:B]]")
		);
		fxt.Tst(String_.Concat_lines_nl
		(	"A|B"
		,	"B|"
		));
	}
}
class Xobc_img_dump_ttl_fxt extends Xobc_base_fxt {
	Xobc_img_dump_ttl cmd;
	public Xobc_img_dump_ttl Run(Xodb_page... page_ary) {return Run(new Xobc_img_dump_ttl(this.Bldr(), this.Wiki()), page_ary);}
	public Xobc_img_dump_ttl Run(Xobc_img_dump_ttl rv, Xodb_page[] page_ary) {
		this.cmd = rv;
		Run_wkr(this.Bldr(), rv, page_ary);
		return rv;
	}
	public void Tst(String expd) {
		Io_url trg_fil = cmd.Make_url_gen().Prv_urls()[0];
		String actl = Io_mgr._.LoadFilStr(trg_fil);
		Tfds.Eq_str_lines(expd, actl);
	}
}
