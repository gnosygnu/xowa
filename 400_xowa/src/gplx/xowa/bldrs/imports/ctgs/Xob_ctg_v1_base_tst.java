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
package gplx.xowa.bldrs.imports.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.imports.*;
import org.junit.*;
import gplx.ios.*;
public class Xob_ctg_v1_base_tst {
	Xodb_page_wkr_ctg_fxt fxt = new Xodb_page_wkr_ctg_fxt();
	@Test  public void One() 		{fxt.ini_("[[Category:A]]").tst_("A");}
	@Test  public void Many() 		{fxt.ini_("[[Category:A]] [[Category:B]] [[Category:C]]").tst_("A", "B", "C");}
	@Test  public void Pipe() 		{fxt.ini_("[[Category:A|B]]").tst_("A");}
	@Test  public void Nl() 		{fxt.ini_("[[Category:A\nB]]").tst_();}
	@Test  public void Brack_bgn() 	{fxt.ini_("[[Category:A[[Category:B]]").tst_("B");}
	@Test  public void Mix() 		{fxt.ini_("[[Category:A]] [[Category:B\nc]][[Category:D|E]]").tst_("A", "D");}
	@Test  public void Ws() 		{fxt.ini_("[[Category: A ]]").tst_("A");}
	@Test  public void Eos() 		{fxt.ini_("[[Category:abcdef").tst_();}
}
class Xodb_page_wkr_ctg_fxt {
	byte[] src;
	public Xodb_page_wkr_ctg_fxt ini_(String s) {src = Bry_.new_utf8_(s); return this;}
	public Xodb_page_wkr_ctg_fxt tst_(String... expd) {
		Xobd_parser mgr = new Xobd_parser();
		Xoa_app app = Xoa_app_fxt.app_();
		Xow_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		Xob_bldr bldr = Xoa_app_fxt.bldr_(app);
		Xobd_parser_wkr_ctg_tstr wkr = (Xobd_parser_wkr_ctg_tstr)new Xobd_parser_wkr_ctg_tstr().Ctor(bldr, wiki);
		byte[] bry = Bry_.new_utf8_("[[Category:");
		wkr.Wkr_hooks().Add(bry, bry);
		mgr.Wkr_add(wkr);
		Xodb_page page = new Xodb_page().Text_(src);//.Ttl_(Bry_.new_utf8_("Test"), new Xow_ns_mgr());
		mgr.Wkr_bgn(bldr);
		mgr.Wkr_run(page);
		byte[][] ttl = (byte[][])wkr.Found().XtoAry(byte[].class);
		String[] actl = new String[ttl.length];
		for (int i = 0; i < actl.length; i++) {
			actl[i] = String_.new_utf8_(ttl[i]);
		}
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
}
class Xobd_parser_wkr_ctg_tstr extends Xob_ctg_v1_txt {	public ListAdp Found() {return found;} ListAdp found = ListAdp_.new_();
	@Override public void Process_ctg(Xodb_page page, byte[] src, int src_len, int bgn, int end) {
		found.Add(Bry_.Mid(src, bgn, end));
	}
	@Override public void Log(byte err_tid, Xodb_page page, byte[] src, int ctg_bgn) {
	}
}
