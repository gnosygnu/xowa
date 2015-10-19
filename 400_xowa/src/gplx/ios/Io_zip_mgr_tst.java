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
package gplx.ios; import gplx.*;
import org.junit.*;
public class Io_zip_mgr_tst {
	@Test  public void Zip_unzip() {
		Zip_unzip_tst("abcdefghijklmnopqrstuvwxyz"); 
	}
	private void Zip_unzip_tst(String s) {
		Io_zip_mgr zip_mgr = Io_zip_mgr_base.Instance;
		byte[] src = Bry_.new_a7(s);
		byte[] zip = zip_mgr.Zip_bry(src, 0, src.length);
		byte[] unz = zip_mgr.Unzip_bry(zip, 0, zip.length);
		Tfds.Eq_ary(src, unz);
	}
}
