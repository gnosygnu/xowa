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
import gplx.xowa.files.*;
public class Xof_xfer_queue_html_offline_tst {
	Xof_xfer_queue_html_fxt fxt = new Xof_xfer_queue_html_fxt();
	@Before public void init()		{fxt.Clear(true); fxt.Src_commons_repo().Tarball_(true); fxt.Src_en_wiki_repo().Tarball_(true);}
	@Test  public void Missing() {	// PURPOSE.fix: missing image was not being marked as missing; DATE:20121227
		fxt	.Lnki_("A.png", true, 220, -1, Xop_lnki_tkn.Upright_null, Xof_doc_thumb.Null_as_int)
			.Src()
			.Trg(	fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/7/70.csv"		, "A.png|x||0?0,0|")
				)
			.tst();
	}
}
