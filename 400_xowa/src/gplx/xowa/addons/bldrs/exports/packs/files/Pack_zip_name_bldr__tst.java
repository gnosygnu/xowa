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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
import org.junit.*; import gplx.core.tests.*;
public class Pack_zip_name_bldr__tst {
	private Pack_zip_name_bldr__fxt fxt = new Pack_zip_name_bldr__fxt();
	@Test   public void Basic() {
		fxt.Test__to_wiki_url("mem/wiki/en.wikipedia.org/", "mem/wiki/en.wikipedia.org/tmp/Xowa_enwiki_2016-09_file_deletion_2016.09/", "mem/wiki/en.wikipedia.org/en.wikipedia.org-file-deletion-2016.09.xowa");
	}
}
class Pack_zip_name_bldr__fxt {
	public void Test__to_wiki_url(String wiki_dir, String zip_fil, String expd) {
		Gftest.Eq__str(expd, Pack_zip_name_bldr.To_wiki_url(Io_url_.mem_fil_(wiki_dir), Io_url_.mem_dir_(zip_fil)).Raw(), "wiki_url");
	}
}
