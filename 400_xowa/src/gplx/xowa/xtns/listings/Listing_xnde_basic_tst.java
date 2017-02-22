/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.listings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
public class Listing_xnde_basic_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	private Listing_xtn_mgr listings_xtn_mgr;
	@Before public void init() {
		fxt.Reset_for_msgs();
		Listing_xnde_basic_tst.Add_listing_msgs(fxt.Wiki());
		listings_xtn_mgr = (Listing_xtn_mgr)fxt.Wiki().Xtn_mgr().Get_or_fail(Listing_xtn_mgr.Xtn_key_static);
		listings_xtn_mgr.Enabled_y_();
		listings_xtn_mgr.Xtn_init_by_wiki(fxt.Wiki());
	}
	@Test  public void Url() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' url='http://site.org'/>"
		,	"<a href=\"http://site.org\" rel=\"nofollow\" class=\"external text\" title=\"name_0\"><strong>name_0</strong></a>. "
		);
	}
	@Test  public void Alt() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' alt=\"''alt_0''\"/>"
		,	"<strong>name_0</strong> (<em><i>alt_0</i></em>). "
		);
	}
	@Test  public void Address_directions() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' address='address_0' directions='directions_0'/>"
		,	"<strong>name_0</strong>, address_0 (<em>directions_0</em>). "
		);
	}
	@Test  public void Phone_tollfree() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' phone='phone_0' tollfree='tollfree_0'/>"
		,	"<strong>name_0</strong>, <abbr title=\"phone\">☎</abbr> phone_0 (toll-free: tollfree_0). "
		);
	}
	@Test  public void Fax() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' fax='fax_0'/>"
		,	"<strong>name_0</strong>, fax: fax_0. "
		);
	}
	@Test  public void Email() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' email='email_0'/>"
		,	"<strong>name_0</strong>, email: <a href=\"mailto:email_0\" class=\"email\">email_0</a>. "
		);
	}
	@Test  public void Hours() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' hours='hours_0'/>"
		,	"<strong>name_0</strong>. hours_0. "
		);
	}
	@Test  public void Checkin_checkout() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' checkin='checkin_0' checkout='checkout_0'/>"
		,	"<strong>name_0</strong>. Check-in: checkin_0, check-out: checkout_0. "
		);
	}
	@Test  public void Price() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' price='price_0'/>"
		,	"<strong>name_0</strong>. price_0. "
		);
	}
	@Test  public void Content() {
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0'>content_0</sleep>"
		,	"<strong>name_0</strong>. content_0"
		);
	}
	public static void Add_listing_msgs(Xowe_wiki wiki) {
		Xol_msg_mgr msg_mgr = wiki.Lang().Msg_mgr();
		msg_mgr.Itm_by_key_or_new("listings-unknown", "Unknown destination");
		msg_mgr.Itm_by_key_or_new("listings-desc", "Add tags for listing locations");
		msg_mgr.Itm_by_key_or_new("listings-phone", "phone");
		msg_mgr.Itm_by_key_or_new("listings-phone-symbol", "☎");
		msg_mgr.Itm_by_key_or_new("listings-fax", "fax");
		msg_mgr.Itm_by_key_or_new("listings-fax-symbol", "");
		msg_mgr.Itm_by_key_or_new("listings-email", "email");
		msg_mgr.Itm_by_key_or_new("listings-email-symbol", "");
		msg_mgr.Itm_by_key_or_new("listings-tollfree", "toll-free");
		msg_mgr.Itm_by_key_or_new("listings-tollfree-symbol", "");
		msg_mgr.Itm_by_key_or_new("listings-checkin", "Check-in: ~{0}", true);
		msg_mgr.Itm_by_key_or_new("listings-checkout", "check-out: ~{0}");
		msg_mgr.Itm_by_key_or_new("listings-desc", "Add tags for listing locations");
		msg_mgr.Itm_by_key_or_new("listings-position", "position: ~{0}");
		msg_mgr.Itm_by_key_or_new("listings-position-template", "");			
	}
}
