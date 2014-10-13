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
package gplx;
import org.junit.*;
import gplx.xowa.net.*;
public class Gfo_url_parser_tst {
	Gfo_url_parser_chkr fxt = new Gfo_url_parser_chkr();
	@Before public void init() {fxt.Reset();}
	@Test  public void All() {
		fxt.Raw_("http://en.wikipedia.org/wiki/mock/Page 0#a?b=c&d=e")
			.Protocol_tid_(Xoo_protocol_itm.Tid_http)
			.Site_("en.wikipedia.org")
			.Site_sub_("en")
			.Site_name_("wikipedia")
			.Site_domain_("org")
			.Segs_("wiki")
			.Segs_("mock")
			.Page_("Page 0")
			.Anchor_("a")
			.Args_("b", "c")
			.Args_("d", "e")
			.tst_();
	}
	@Test  public void Site_slash_none() {
		fxt.Raw_("http:en.wikipedia.org").Protocol_tid_(Xoo_protocol_itm.Tid_http).Site_("en.wikipedia.org").tst_();
	}
	@Test  public void Site_slash_many() {
		fxt.Raw_("http:////en.wikipedia.org").Protocol_tid_(Xoo_protocol_itm.Tid_http).Site_("en.wikipedia.org").tst_();
	}
	@Test  public void Site_slash_trailing() {
		fxt.Raw_("http://en.wikipedia.org/").Protocol_tid_(Xoo_protocol_itm.Tid_http).Site_("en.wikipedia.org").tst_();
	}
	@Test  public void Site_dot_1() {
		fxt.Raw_("http://wikipedia.org").Site_("wikipedia.org").Site_name_("wikipedia").Site_domain_("org").tst_();
	}
	@Test  public void Page_encoded() {
		fxt.Raw_("http://site/A%27s").Site_("site").Page_("A's").tst_();
	}
	@Test  public void Args() {
		fxt.Raw_("http://site/page?a=b").Site_("site").Args_("a", "b").tst_();
	}
	@Test  public void Args_protocol_less() {
		fxt.Raw_("Special:Search/Earth?fulltext=yes").Segs_("Special:Search").Page_("Earth").Args_("fulltext", "yes").tst_();
	}
	@Test  public void Err_protocol_missing() {
		fxt.Raw_("httpen.wikipedia.org").Err_(Gfo_url.Err_protocol_missing).tst_();
	}
	@Test  public void Err_protocol_missing__site_only() {
		fxt.Raw_("site").Site_("site").Err_(Gfo_url.Err_protocol_missing).tst_();
	}
	@Test  public void Err_protocol_missing__site_and_page() {
		fxt.Raw_("site/page").Site_("site").Page_("page").Err_(Gfo_url.Err_protocol_missing).tst_();
	}
	@Test  public void Err_protocol_missing__page_anchor() {
		fxt.Raw_("page#a").Page_("page").Anchor_("a").Err_(Gfo_url.Err_protocol_missing).tst_();
	}
	@Test  public void Legacy() {
		fxt.Reset().Raw_("http://en.wikipedia.org/wiki/A" ).Page_("A").tst_();
		fxt.Reset().Raw_("http://en.wikipedia.org/wiki/A?").Page_("A").Args_("", "").tst_();
		fxt.Reset().Raw_("http://en.wikipedia.org/wiki/A#").Page_("A").tst_();
		fxt.Reset().Raw_("http://en.wikipedia.org/wiki/A%27s").Page_("A's").tst_();
		fxt.Reset().Raw_("https://en.wikipedia.org/wiki/A").Page_("A").tst_();
		fxt.Reset().Raw_("http://en.m.wikipedia.org/wiki/A").Page_("A").tst_();
		fxt.Reset().Raw_("http://en.wikipedia.org/w/index.php?title=A").Args_("title", "A").tst_();
	}
	@Test  public void Relative() {
		fxt.Raw_("//en.wikipedia.org").Protocol_tid_(Xoo_protocol_itm.Tid_http).Site_("en.wikipedia.org").tst_();
	}
	@Test  public void Parse_site_fast() {
		fxt.Parse_site_fast_tst("http://a.org/B"		, "a.org");
		fxt.Parse_site_fast_tst("http://a.org"			, "a.org");
		fxt.Parse_site_fast_tst("//a.org/B"				, "a.org");
		fxt.Parse_site_fast_tst("//a.org/B:C"			, "a.org");
	}
}
class Gfo_url_parser_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Gfo_url.class;}
	public Gfo_url_parser_chkr Protocol_tid_(byte v) 		{this.protocol_tid = v; return this;} private byte protocol_tid;
	public Gfo_url_parser_chkr Site_(String v) 				{this.site = v; return this;} private String site;
	public Gfo_url_parser_chkr Site_sub_(String v) 			{this.site_sub = v; return this;} private String site_sub;
	public Gfo_url_parser_chkr Site_name_(String v) 		{this.site_name = v; return this;} private String site_name;
	public Gfo_url_parser_chkr Site_domain_(String v) 		{this.site_domain = v; return this;} private String site_domain;
	public Gfo_url_parser_chkr Segs_(String v) 				{segs.Add(v); return this;} ListAdp segs = ListAdp_.new_();
	public Gfo_url_parser_chkr Page_(String v) 				{this.page = v; return this;} private String page;
	public Gfo_url_parser_chkr Anchor_(String v) 			{this.anchor = v; return this;} private String anchor;
	public Gfo_url_parser_chkr Args_(String k, String v) 	{args.Add(new Gfo_url_arg_chkr(k, v)); return this;} ListAdp args = ListAdp_.new_();
	public Gfo_url_parser_chkr Err_(byte v) 				{err = v; return this;} private byte err;
	public Gfo_url_parser_chkr Reset() {
		protocol_tid = Xoo_protocol_itm.Tid_null;
		site = site_sub = site_name = site_domain = page = anchor = null;
		err = Gfo_url.Err_none;
		segs.Clear();
		args.Clear();
		return this;
	}
	ByteAryAry_chkr bry_ary_chkr = new ByteAryAry_chkr();
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Gfo_url actl = (Gfo_url)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(err == Gfo_url.Err_none, path, "err", err, actl.Err());
		rv += mgr.Tst_val(protocol_tid == Xoo_protocol_itm.Tid_null, path, "protocol_tid", protocol_tid, actl.Protocol_tid());
		rv += mgr.Tst_val(site == null, path, "site", site, String_.new_utf8_(actl.Site()));
		rv += mgr.Tst_val(site_sub == null, path, "site_sub", site_sub, String_.new_utf8_(actl.Site_sub()));
		rv += mgr.Tst_val(site_name == null, path, "site_name", site_name, String_.new_utf8_(actl.Site_name()));
		rv += mgr.Tst_val(site_domain == null, path, "site_domain", site_domain, String_.new_utf8_(actl.Site_domain()));
		bry_ary_chkr.Val_(Bry_.Ary(segs.XtoStrAry()));
		rv += bry_ary_chkr.Chk(mgr, "segs", actl.Segs());
		rv += mgr.Tst_val(page == null, path, "page", page, String_.new_utf8_(actl.Page()));
		rv += mgr.Tst_val(anchor == null, path, "anchor", anchor, String_.new_utf8_(actl.Anchor()));
		mgr.Tst_sub_ary((Gfo_url_arg_chkr[])args.Xto_ary(Gfo_url_arg_chkr.class), actl.Args(), "args", rv);
		return rv;
	}
	public Gfo_url_parser_chkr Raw_(String v) 				{this.raw = v; return this;} private String raw;
	public void tst_() {			
		byte[] bry = Bry_.new_utf8_(raw);
		Gfo_url url = new Gfo_url();
		parser.Parse(url, bry, 0, bry.length);
		Tst_mgr tst_mgr = new Tst_mgr();
		tst_mgr.Tst_obj(this, url);
	}	Gfo_url_parser parser = new Gfo_url_parser();
	public void Parse_site_fast_tst(String raw, String expd) 	{
		byte[] raw_bry = Bry_.new_utf8_(raw);
		parser.Parse_site_fast(site_data, raw_bry, 0, raw_bry.length);
		String actl = String_.new_utf8_(raw_bry, site_data.Site_bgn(), site_data.Site_end());
		Tfds.Eq(expd, actl);
	}	Gfo_url_site_data site_data = new Gfo_url_site_data();
}
class Gfo_url_arg_chkr implements Tst_chkr {
	public Gfo_url_arg_chkr(String key, String val) {this.key = key; this.val = val;} private String key; String val;
	public Class<?> TypeOf() {return Gfo_url_arg.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Gfo_url_arg actl = (Gfo_url_arg)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(key == null, path, "key", key, String_.new_utf8_(actl.Key_bry()));
		rv += mgr.Tst_val(val == null, path, "val", val, String_.new_utf8_(actl.Val_bry()));
		return rv;
	}
}
