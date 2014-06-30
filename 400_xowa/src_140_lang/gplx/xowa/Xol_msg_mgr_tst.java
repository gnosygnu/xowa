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
public class Xol_msg_mgr_tst {
	Xol_msg_mgr_fxt fxt = new Xol_msg_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Template_msg() {fxt.Test_val_by_key("About {{SITENAME}}", "About Wikipedia");}	// PURPOSE.fix: {{Template}} not working inside label tags; EX:de.wikisource.org; DATE:2013-02-10
	@Test  public void Template_mediawiki() {	// PURPOSE.fix: {{Template}} not working inside MediaWiki template
		fxt.Test_mediaWiki_msg("About {{SITENAME}}", "About Wikipedia");
	}	
	@Test  public void Val_html_accesskey_and_title() {
		fxt.Clear().Test_val_html_accesskey_and_title("test_title"	, "a"	, " accesskey=\"a\" title=\"test_title [a]\"");
		fxt.Clear().Test_val_html_accesskey_and_title("test_title"	, null	, " title=\"test_title\"");		// accesskey is missing
		fxt.Clear().Test_val_html_accesskey_and_title("test_title"	, ""	, " title=\"test_title\"");		// accesskey is ""
		fxt.Clear().Test_val_html_accesskey_and_title(null			, "a"	, " title=\"\"");				// no title; leave blank
	}
}
class Xol_msg_mgr_fxt {
	public Xol_msg_mgr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			mgr = wiki.Msg_mgr();
		}
		mgr.Clear();
		wiki.Lang().Msg_mgr().Clear();
		return this;
	}	private Xoa_app app; Xow_wiki wiki; Xow_msg_mgr mgr;
	public void Test_val_by_key(String val, String expd) {
		Xol_msg_itm itm = wiki.Lang().Msg_mgr().Itm_by_key_or_new(Bry_.new_ascii_("test"));
		itm.Atrs_set(Bry_.new_ascii_(val), false, true);			
		Tfds.Eq(expd, String_.new_utf8_(wiki.Msg_mgr().Val_by_key_obj(Bry_.new_ascii_("test"))), "has_tmpl_txt");
	}
	public void Test_mediaWiki_msg(String raw, String expd) {
		byte[] msg_ttl = Bry_.new_ascii_("MediaWiki:msg_ttl");
		wiki.Db_mgr().Save_mgr().Data_create(Xoa_ttl.parse_(wiki, msg_ttl), Bry_.new_ascii_(raw));
		Tfds.Eq(expd, String_.new_utf8_(wiki.Msg_mgr().Val_by_key_obj(Bry_.new_ascii_("msg_ttl"))));
	}
	public void Test_val_html_accesskey_and_title(String init_title, String init_accesskey, String expd) {
		if (init_title		!= null) new_msg_itm_("tooltip-test"	, init_title);
		if (init_accesskey	!= null) new_msg_itm_("accesskey-test"	, init_accesskey);
		Tfds.Eq(expd, String_.new_ascii_(wiki.Msg_mgr().Val_html_accesskey_and_title(Bry_.new_ascii_("test"))));
	}
	private void new_msg_itm_(String key, String val) {
		Xol_msg_itm itm = wiki.Lang().Msg_mgr().Itm_by_key_or_new(Bry_.new_ascii_(key));
		itm.Atrs_set(Bry_.new_ascii_(val), false, true);			
	}
}
