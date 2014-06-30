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
public class Xol_fragment_mgr_tst {
	private Xol_fragment_mgr_fxt fxt = new Xol_fragment_mgr_fxt();
	@Before public void init()	{fxt.Clear();}
	@Test   public void Html_wikidata() {
		fxt.Test_fragment(Xol_fragment_mgr.Invk_html_js_wikidata, String_.Concat_lines_nl
		(	"  <script>"
		,	"  var xowa_wikidata_i18n = {"
		,	"    'languages'          : 'en',"
		,	"    'toc'                : 'Contents',"
		,	"    'labels'             : 'Labels',"
		,	"    'aliasesHead'        : 'Aliases',"
		,	"    'descriptions'       : 'Descriptions',"
		,	"    'links'              : 'Links',"
		,	"    'claims'             : 'Claims',"
		,	"    'json'               : 'JSON',"
		,	"    'language'           : 'language',"
		,	"    'wiki'               : 'wiki',"
		,	"    'label'              : 'label',"
		,	"    'aliases'            : 'aliases',"
		,	"    'description'        : 'description',"
		,	"    'link'               : 'link',"
		,	"    'property'           : 'property',"
		,	"    'value'              : 'value',"
		,	"    'references'         : 'references',"
		,	"    'qualifiers'         : 'qualifiers',"
		,	"    'comma-separator'    : ', ',"
		,	"    'word-separator'     : ' ',"
		,	"    'parentheses'        : '($1)',"
		,	"    'jan'                : 'Jan',"
		,	"    'feb'                : 'Feb',"
		,	"    'mar'                : 'Mar',"
		,	"    'apr'                : 'Apr',"
		,	"    'may'                : 'May',"
		,	"    'jun'                : 'Jun',"
		,	"    'jul'                : 'Jul',"
		,	"    'aug'                : 'Aug',"
		,	"    'sep'                : 'Sep',"
		,	"    'oct'                : 'Oct',"
		,	"    'nov'                : 'Nov',"
		,	"    'dec'                : 'Dec',"
		,	"    'ago'                : '$1 ago',"
		,	"    'novalue'            : '—',"
		,	"    'somevalue'          : '?',"
		,	"    'links-wiki'         : 'Links (Wikipedia)',"
		,	"    'links-wiktionary'   : 'Links (Wiktionary)',"
		,	"    'links-wikisource'   : 'Links (Wikisource)',"
		,	"    'links-wikivoyage'   : 'Links (Wikivoyage)',"
		,	"    'links-wikiquote'    : 'Links (Wikiquote)',"
		,	"    'links-wikibooks'    : 'Links (Wikibooks)',"
		,	"    'links-wikiversity'  : 'Links (Wikiversity)',"
		,	"    'links-wikinews'     : 'Links (Wikinews)',"
		,	"    'links-special'      : 'Links (special wikis)',"
		,	"    'plus'               : '+',"
		,	"    'minus'              : '−',"
		,	"    'plusminus'          : '±',"
		,	"    'degree'             : '°',"
		,	"    'minute'             : '′',"
		,	"    'second'             : '″',"
		,	"    'north'              : 'N',"
		,	"    'south'              : 'S',"
		,	"    'west'               : 'W',"
		,	"    'east'               : 'E',"
		,	"    'meters'             : '" + String_.new_utf8_(Xol_msg_itm_.Bry_nbsp) + "m',"
		,	"    'julian'             : '<sup>jul</sup>',"
		,	"    'decade'             : '$10s',"
		,	"    'century'            : '$1. century',"
		,	"    'millenium'          : '$1. millenium',"
		,	"    'years1e4'           : '$10,000 years',"
		,	"    'years1e5'           : '$100,000 years',"
		,	"    'years1e6'           : '$1 million years',"
		,	"    'years1e7'           : '$10 million years',"
		,	"    'years1e8'           : '$100 million years',"
		,	"    'years1e9'           : '$1 billion years',"
		,	"    'bc'                 : '$1 BC',"
		,	"    'inTime'             : 'in $1',"
		,	"    'rank'               : 'rank',"
		,	"    'preferred'          : 'preferred',"
		,	"    'normal'             : 'normal',"
		,	"    'deprecated'         : 'deprecated'"
		,	"  };"
		,	"  </script>"
		,	"  <script src=\"file:///mem/xowa/bin/any/javascript/xowa/wikidata/wikidata.js\" type='text/javascript'></script>"
		));
	}
}
class Xol_fragment_mgr_fxt {
	public void Clear() {
		if (lang == null) {
			Xoa_app app = Xoa_app_fxt.app_();
			lang = app.User().Lang();
//				wiki = Xoa_app_fxt.wiki_tst_(app);
		}
	}	private Xol_lang lang;
	public void Test_fragment(String key, String expd) {
		byte[] actl = (byte[])GfoInvkAble_.InvkCmd(lang.Fragment_mgr(), key);
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
	}
}