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
package gplx.xowa.xtns.listings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Listing_xnde_template_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	private Listing_xtn_mgr listings_xtn_mgr;
	@Before public void init() {
		fxt.Reset_for_msgs();
		Listing_xnde_basic_tst.Add_listing_msgs(fxt.Wiki());
		listings_xtn_mgr = (Listing_xtn_mgr)fxt.Wiki().Xtn_mgr().Get_or_fail(Listing_xtn_mgr.Xtn_key_static);
		listings_xtn_mgr.Clear();
		listings_xtn_mgr.Enabled_y_();			
	}
	private void Init_xtn_mgr() {listings_xtn_mgr.Xtn_init_by_wiki(fxt.Wiki());}
	@Test  public void Disabled() {
		listings_xtn_mgr.Enabled_n_();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' address='address_0'>content_0</sleep>"
		,	"&lt;sleep name='name_0' address='address_0'&gt;content_0&lt;/sleep&gt;"
		);
	}
	@Test  public void Ignore_empty() {
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' alt='' address='' directions='' phone='' url='' checkin='' checkout='' price='' lat='' long=''>  </sleep>"
		,	"<strong>name_0</strong>. "
		);
	}
	@Test  public void Ignore_invalid() {	// PURPOSE: invalid atrs were causing null reference exception; PAGE:nl.v:Rome;EX:<sleep phone='' "abc"/> DATE:2014-06-04
		fxt.Init_page_create("Template:ListingsSample", "{{{name|nil_name}}};{{{address|nil_address}}};{{{1|nil_content}}}");
		fxt.Init_page_create("MediaWiki:listings-template", "ListingsSample");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' 'invalid'></sleep>"
		,	"name_0;nil_address;"
		);
	}
	@Test  public void Template() {
		fxt.Init_page_create("Template:ListingsSample", "{{{name|nil_name}}};{{{address|nil_address}}};{{{1|nil_content}}}");
		fxt.Init_page_create("MediaWiki:listings-template", "ListingsSample");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' address='address_0'>content_0</sleep>"
		,	"name_0;address_0;content_0"
		);
	}
	@Test  public void Template_lat() {	// PURPOSE: lat / long was not being set for listings sample; PAGE:fr.v:Marrakech; DATE:2014-05-21
		fxt.Init_page_create("Template:ListingsSample2", "{{{name|nil_name}}};{{{lat|nil_lat}}};{{{long|nil_long}}}");
		fxt.Init_page_create("MediaWiki:listings-template", "ListingsSample2");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' lat='12' long=''></sleep>"
		,	"name_0;12;"
		);
	}
	@Test  public void Position_template_as_text() {
		fxt.Init_page_create("MediaWiki:listings-position-template"	, "position_template|$1|$2");
		fxt.Init_page_create("Template:position_template"			, "tmpl:lat={{{1}}} long={{{2}}}");
		fxt.Init_page_create("MediaWiki:listings-position"			, "msg: $1");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' lat='100' long='200'></sleep>"
		,	"<strong>name_0</strong> (<em>msg: tmpl:lat=100 long=200</em>). "
		);
	}
	@Test  public void Phone__text() {
		fxt.Init_page_create("MediaWiki:listings-phone"				, "phone");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' phone='0'></sleep>"
		,	"<strong>name_0</strong>, <abbr title=\"phone\">☎</abbr> 0. "
		);
	}
	@Test  public void Phone__text_and_symbol() {
		fxt.Init_page_create("MediaWiki:listings-phone"				, "phone");
		fxt.Init_page_create("MediaWiki:listings-phone-symbol"		, "P");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' phone='0'></sleep>"
		,	"<strong>name_0</strong>, <abbr title=\"phone\">P</abbr> 0. "
		);
	}
	@Test  public void Tollfree__text_and_symbol() {
		fxt.Init_page_create("MediaWiki:listings-tollfree"				, "tollfree");
		fxt.Init_page_create("MediaWiki:listings-tollfree-symbol"		, "tf");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' tollfree='0'></sleep>"
		,	"<strong>name_0</strong>, <abbr title=\"phone\">☎</abbr>  (<abbr title=\"tollfree\">tf</abbr>: 0). "
		);
	}
	@Test  public void Fax__text_and_symbol() {
		fxt.Init_page_create("MediaWiki:listings-fax"				, "fax");
		fxt.Init_page_create("MediaWiki:listings-fax-symbol"		, "f");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' fax='0'></sleep>"
		,	"<strong>name_0</strong>, <abbr title=\"fax\">f</abbr>: 0. "
		);
	}
	@Test  public void Email__text_and_symbol() {
		fxt.Init_page_create("MediaWiki:listings-email"				, "email");
		fxt.Init_page_create("MediaWiki:listings-email-symbol"		, "e");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' email='a@b.org'></sleep>"
		,	"<strong>name_0</strong>, <abbr title=\"email\">e</abbr>: <a href=\"mailto:a@b.org\" class=\"email\">a@b.org</a>. "
		);
	}
	@Test  public void Checkin__template() {
		fxt.Init_page_create("MediaWiki:listings-checkin"				, "checkin: $1");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' checkin='checkin_0'></sleep>"
		,	"<strong>name_0</strong>. checkin: checkin_0. "
		);
	}
	@Test  public void Checkout__template() {
		fxt.Init_page_create("MediaWiki:listings-checkout"				, "checkout: $1");
		Init_xtn_mgr();
		fxt.Test_parse_page_all_str
		(	"<sleep name='name_0' checkout='checkout_0'></sleep>"
		,	"<strong>name_0</strong>. checkout: checkout_0. "
		);
	}
}

