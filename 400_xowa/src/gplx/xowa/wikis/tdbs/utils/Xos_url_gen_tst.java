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
package gplx.xowa.wikis.tdbs.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*;
import gplx.core.ios.*;
public class Xos_url_gen_tst {
	@Test  public void Url_gen() {
		tst_url_gen("mem/root/",          0, "mem/root/00/00/00/00/0000000000.csv");
		tst_url_gen("mem/root/",         99, "mem/root/00/00/00/00/0000000099.csv");
		tst_url_gen("mem/root/", 1234567890, "mem/root/12/34/56/78/1234567890.csv");
	}
	private void tst_url_gen(String root_str, int idx, String expd) {
		Io_url root = Io_url_.mem_fil_(root_str);
		Io_url actl_url = Xos_url_gen.bld_fil_(root, idx, Bry_.new_a7(".csv"));
		Tfds.Eq(expd, actl_url.Xto_api());
	}
}
