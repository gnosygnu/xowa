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
import org.junit.*; import gplx.xowa.files.*;
public class Xof_meta_fil_tst {
	Xof_meta_fil_fxt fxt = new Xof_meta_fil_fxt();
	@Before public void init() {fxt.Ini();}
	@Test  public void Bld_url() {fxt.Bld_url("mem/root/", "abcdef", 3, "mem/root/a/b/abc.csv");}
}
class Xof_meta_fil_fxt {
	byte[] md5_(byte[] name) {return Xof_file_wkr_.Md5_(name);}
	public void Ini() {}
	public void Bld_url(String root, String md5, int depth, String expd) {Tfds.Eq(expd, Xof_meta_fil.Bld_url(Io_url_.new_dir_(root), Bry_.new_a7(md5), depth).Raw());}
}
