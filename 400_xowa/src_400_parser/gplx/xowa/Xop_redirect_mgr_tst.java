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
public class Xop_redirect_mgr_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic()					{tst_Redirect("#REDIRECT [[a]]", "A");}
	@Test  public void Basic_colon()			{tst_Redirect("#REDIRECT:[[a]]", "A");}
	@Test  public void Ns_help()				{tst_Redirect("#REDIRECT [[Help:a]]", "Help:A");}
	@Test  public void First()					{tst_Redirect("#REDIRECT [[a]] [[b]]", "A");}
	@Test  public void Exc_false_match()		{tst_Redirect("#REDIRECTA [[a]]", Xop_redirect_log.False_match);}
	@Test  public void Exc_lnki_not_found()		{tst_Redirect("#REDIRECT test", Xop_redirect_log.Lnki_not_found);}
	@Test  public void Ws()						{tst_Redirect("\n#REDIRECT [[a]]", "A");}	// EX.WP:Germany; {{Template group}} -> \n#REDIRECT [[Template:Navboxes]]
	private void tst_Redirect(String src_str, Object expd) {
		Xop_redirect_mgr redirect_mgr = new Xop_redirect_mgr(fxt.Ctx().Wiki());
		fxt.Log_clear();

		byte[] src = Bry_.new_utf8_(src_str);
		Xoa_ttl actl_ttl = redirect_mgr.Extract_redirect(src, src.length);
		byte[] actl = actl_ttl == null ? Bry_.Empty : actl_ttl.Full_txt();
		String expd_str = String_.as_(expd);
		if (actl == Bry_.Empty) {
			if (expd_str != null) throw Err_.new_("result is null; expecting " + expd);
			Gfo_msg_itm itm = (Gfo_msg_itm)expd;
			String[] actl_ary = fxt.Log_xtoAry();
			Tfds.Eq_ary_str(new String[] {String_.new_utf8_(itm.Owner().Path()) + "." + String_.new_utf8_(itm.Key_bry())}, actl_ary);
			return;
		}
		if (expd_str == null) {
			Gfo_msg_itm expd_hdr = (Gfo_msg_itm)expd;
			throw Err_.new_("result is valued; expecting error " + expd_hdr.Path_str());
		}
		Tfds.Eq(expd_str, String_.new_utf8_(actl));
	}
}
