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
package gplx.xowa.apps.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xoa_ttl__err_tst {
	@Before public void init() {fxt.Reset();} private Xoa_ttl_fxt fxt = new Xoa_ttl_fxt();
	@Test   public void Invalid()				{fxt.Init_ttl("<!--A").Expd_err(Xop_ttl_log.Comment_eos).Test();}
	@Test   public void Invalid_brace()			{fxt.Init_ttl("[[a]]").Expd_err(Xop_ttl_log.Invalid_char).Test();}
	@Test   public void Invalid_curly()			{fxt.Init_ttl("{{a}}").Expd_err(Xop_ttl_log.Invalid_char).Test();}
	@Test   public void Len_0() {
		fxt.Init_ttl("").Expd_err(Xop_ttl_log.Len_0).Test();
		fxt.Init_ttl(" ").Expd_err(Xop_ttl_log.Len_0).Test();
		fxt.Init_ttl("_").Expd_err(Xop_ttl_log.Len_0).Test();
		fxt.Init_ttl("_ _").Expd_err(Xop_ttl_log.Len_0).Test();
	}
	@Test   public void Len_max() {
		fxt.Init_ttl(String_.Repeat("A", 512)).Expd_page_txt(String_.Repeat("A", 512)).Test();
//			fxt.Init_ttl("File:" + String_.Repeat("A", 255)).Expd_page_txt(String_.Repeat("A", 255)).Test();	// DELETE: removing multi-byte check; DATE:2013-02-02
//			fxt.Init_ttl(String_.Repeat("A", 256)).Expd_err(Xop_ttl_log.Len_max).Test();
//			fxt.Init_ttl("Special:" + String_.Repeat("A", 255)).Expd_ns_id(Xow_ns_.Id_special).Expd_page_txt(String_.Repeat("A", 255)).Test();
//			fxt.Init_ttl("Special:" + String_.Repeat("A", 512 + 8)).Expd_err(Xop_ttl_log.Len_max).Test();	// 8="Special:".length
	}
	@Test   public void Colon_is_last_ns() {fxt.Init_ttl("Help:").Expd_err(Xop_ttl_log.Ttl_is_ns_only).Test();}
}
