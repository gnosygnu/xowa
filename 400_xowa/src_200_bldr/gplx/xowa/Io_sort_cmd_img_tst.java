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
public class Io_sort_cmd_img_tst {
	@Test  public void Basic() {
		tst_cmd(String_.Concat_lines_nl
		(	"Abc.png|0|220|180|-1|Page 1|"
		,	"Abc.png|0|220|180|-1|Page 2|"
		,	"Abc.png|1|-1|-1|-1|Page 2|"
		), String_.Concat_lines_nl
		(	"Abc.png|0|220|180|-1|"
		,	"Abc.png|1|-1|-1|-1|"			
		));
	}
	private void tst_cmd(String raw, String expd) {
		Io_url src_fil = Io_url_.mem_fil_("mem/src.csv");
		Io_url trg_fil = Io_url_.mem_fil_("mem/trg.csv");
		Io_mgr._.SaveFilStr(src_fil, raw);
		Io_sort_cmd_img cmd = new Io_sort_cmd_img().Make_url_gen_(Io_url_gen_.fil_(trg_fil));
		Io_line_rdr rdr = new Io_line_rdr(Gfo_usr_dlg_.Test(), src_fil).Key_gen_(new Io_line_rdr_key_gen_img());
		cmd.Sort_bgn();
		while (rdr.Read_next()) {
			cmd.Sort_do(rdr);
		}
		cmd.Sort_end();
		String actl = Io_mgr._.LoadFilStr(trg_fil);
		Tfds.Eq_str_lines(expd, actl);
	}
}
