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
package gplx.security; import gplx.*;
import org.junit.*; import gplx.ios.*; /*IoStream*/
public class HashDlgWtr_tst {
	@Before public void setup() {
		HashAlgo_tth192 algo = HashAlgo_tth192.new_();
		algo.BlockSize_set(10);
		calc = algo;
	}
	@Test  public void Basic() {
		tst_Status(10, stringAry_(" - hash: 100%"));
		tst_Status(11, stringAry_(" - hash: 66%"));
		tst_Status(30, stringAry_(" - hash: 40%", " - hash: 60%", " - hash: 100%"));
	}
	void tst_Status(int count, String[] expdWritten) {
		ConsoleDlg_dev dialog = ConsoleDlg_.Dev();
		String data = String_.Repeat("A", count);
		IoStream stream = IoStream_.mem_txt_(Io_url_.Empty, data);
		calc.CalcHash(dialog, stream);
		String[] actlWritten = dialog.Written().To_str_ary();
		Tfds.Eq_ary(actlWritten, expdWritten);
	}
	String[] stringAry_(String... ary) {return ary;}
	HashAlgo calc;
}
