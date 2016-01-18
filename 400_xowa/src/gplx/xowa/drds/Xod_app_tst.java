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
package gplx.xowa.drds; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.drds.pages.*; import gplx.xowa.wikis.*; import gplx.xowa.htmls.sections.*;
public class Xod_app_tst {
	private final Xod_app_tstr tstr = new Xod_app_tstr();
	@Before		public void init() {tstr.Init_mem();}
	@Test  public void Get() {
		tstr.Data_mgr().Page__insert(1, "A", "2015-10-19 00:01:02");
		tstr.Data_mgr().Html__insert(1, "abc");
		tstr.Test__get("A", tstr.Make_page(1, "A", "2015-10-19T00:01:02Z", tstr.Make_section(0, 2, "", "", "abc")));
	}
}
class Xod_app_tstr {
	private final gplx.xowa.apps.Xoav_app app; private final Xowv_wiki wiki;
	private final Xod_app drd_provider;
	public Xod_app_tstr() {
		this.app = Xoa_app_fxt.Make__app__view();
		this.wiki = Xoa_app_fxt.Make__wiki__view(app);
		data_mgr.Wiki_(wiki);
		Xoa_test_.Init__db__mem(wiki);
		drd_provider = new Xod_app(app);
	}
	public Xowd_data_tstr Data_mgr() {return data_mgr;} private final Xowd_data_tstr data_mgr = new Xowd_data_tstr();
	public void Init_mem() {
		Io_mgr.Instance.InitEngine_mem();
	}
	public void Test__get(String ttl, Xod_page_itm expd) {
		Xow_wiki wiki = drd_provider.Wikis__get_by_domain("en.wikipedia.org");
		Xoa_url page_url = wiki.Utl__url_parser().Parse(Bry_.new_u8(ttl));
		Xod_page_itm itm = drd_provider.Wiki__get_by_url(wiki, page_url);
		Tfds.Eq(expd.To_str(), itm.To_str());
	}
	public Xod_page_itm Make_page(int page_id, String ttl, String modified_on, Xoh_section_itm... section_ary) {
		Xod_page_itm rv = new Xod_page_itm();
		rv.Init(page_id, page_id, ttl, ttl, null, null, modified_on, Bool_.N, Bool_.N, Bool_.N, 1, null, null, null);
		int len = section_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoh_section_itm itm = section_ary[i];
			rv.Section_list().Add(itm);
		}			
		return rv;
	}
	public Xoh_section_itm Make_section(int id, int level, String anchor, String heading, String content) {return new Xoh_section_itm(id, level, Bry_.new_u8(anchor), Bry_.new_u8(heading)).Content_(Bry_.new_u8(content));}
}
