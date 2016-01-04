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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xop_lnki_wkr__video_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Thumbtime() {
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=123]]"		, fxt.tkn_lnki_().Thumbtime_(123));
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=1:23]]"	, fxt.tkn_lnki_().Thumbtime_(83));
		fxt.Test_parse_page_wiki("[[File:A.ogv|thumbtime=1:01:01]]"	, fxt.tkn_lnki_().Thumbtime_(3661));
		fxt.Init_log_(Xop_lnki_log.Upright_val_is_invalid).Test_parse_page_wiki("[[File:A.ogv|thumbtime=a]]", fxt.tkn_lnki_().Thumbtime_(-1));
	}
	@Test  public void Size__null() {	// NOTE: make sure that no size defaults to -1; needed for Xof_img_size logic of defaulting -1 to orig_w; DATE:2015-08-07
		fxt.Test_parse_page_wiki("[[File:A.ogv]]"					, fxt.tkn_lnki_().Width_(Xof_img_size.Size__neg1));
	}
}
