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
import org.junit.*;
public class Xop_lnki_wkr__frame_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final Xop_fxt fxt = new Xop_fxt();
	// PURPOSE:use 1st imgtype param; changed between mw1.22.2 and mw1.25.2  PAGE:he.w:מספן_המודיעין EX: [[File:Osa-I class Project205 DN-SN-84-01770.jpg|thumb|frame|abcde]]
	@Test  public void Use_1st__thumb()			{fxt.Test_parse_page_wiki("[[File:A.png|thumb|frame]]", fxt.tkn_lnki_().ImgType_(Xop_lnki_type.Id_thumb));}
	@Test  public void Use_1st__frame()			{fxt.Test_parse_page_wiki("[[File:A.png|frame|thumb]]", fxt.tkn_lnki_().ImgType_(Xop_lnki_type.Id_frame));}
}
