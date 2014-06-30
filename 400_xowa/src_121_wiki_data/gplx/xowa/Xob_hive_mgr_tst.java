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
public class Xob_hive_mgr_tst {
	Xow_hive_mgr_fxt fxt = new Xow_hive_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Drilldown() {
//			fxt.Files_create_range(10, 10);
//			fxt.Drilldown("A00", "J09", "A00", "B09", "C00", "D09", "E00", "F09", "G00", "H09", "I00", "J09");
//			fxt.Drilldown("E00", "F09", "E00", "E03", "E04", "E07", "E08", "F01", "F02", "F05", "F06", "F09");
//			fxt.Drilldown("E08", "F01", "E08", "E09", "F00", "F01");
	}
}
class Xob_reg_wtr {
	Bry_bfr bfr = Bry_bfr.new_(); int fil_count = 0;
	public void Add(byte[] bgn, byte[] end, int itm_count) {
		bfr
		.Add_int_variable(fil_count++).Add_byte(Byte_ascii.Pipe)
		.Add(bgn).Add_byte(Byte_ascii.Pipe)
		.Add(end).Add_byte(Byte_ascii.Pipe)
		.Add_int_variable(itm_count).Add_byte(Byte_ascii.NewLine);		
	}
	public void Flush(Io_url url) {
		Io_mgr._.SaveFilBfr(url, bfr);
//			Tfds.Write(url.Raw() + "\n" + Io_mgr._.LoadFilStr(url));
	}
}
