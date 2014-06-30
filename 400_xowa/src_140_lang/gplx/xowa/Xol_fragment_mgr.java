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
public class Xol_fragment_mgr implements GfoInvkAble {
	public Xol_fragment_mgr(Xol_lang lang) {this.lang = lang;} private Xol_lang lang;
	public byte[] Html_js_wikidata() {if (html_js_wikidata == null) html_js_wikidata = Html_js_wikidata_init(); return html_js_wikidata;} private byte[] html_js_wikidata;
	private byte[] Html_js_wikidata_init() {
		Xow_wiki wdata_wiki = lang.App().Wiki_mgr().Wdata_mgr().Wdata_wiki(); 
		if (wdata_wiki != null) wdata_wiki.Init_assert();
		Bry_bfr bfr = lang.App().Utl_bry_bfr_mkr().Get_b512();
		html_js_wikidata_fmtr.Bld_bfr_many(bfr
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_languages)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_toc)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_labels)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_aliasesHead)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_descriptions)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_claims)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_json)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_language)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_wiki)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_label)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_aliases)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_description)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_link)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_property)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_value)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_references)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_qualifiers)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_symbol_comma_separator)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_symbol_word_separator)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_symbol_parentheses)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_jan)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_feb)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_mar)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_apr)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_may)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_jun)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_jul)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_aug)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_sep)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_oct)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_nov)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_dte_month_abrv_dec)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_duration_ago)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_novalue)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_somevalue)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wiki)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wiktionary)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wikisource)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wikivoyage)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wikiquote)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wikibooks)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wikiversity)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_wikinews)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_links_special)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_plus)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_minus)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_plusminus)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_degree)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_minute)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_second)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_north)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_south)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_west)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_east)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_meters)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_julian)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_decade)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_century)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_millenium)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_years1e4)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_years1e5)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_years1e6)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_years1e7)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_years1e8)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_years1e9)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_bc)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_inTime)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_wikibase_diffview_rank)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_preferred)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_normal)
			, Html_js_wikidata_val(wdata_wiki, Xol_msg_itm_.Id_xowa_wikidata_deprecated)
			, lang.App().Fsys_mgr().Root_dir().To_http_file_str()
			);
		return bfr.Mkr_rls().XtoAryAndClear();
	}
	private byte[] Html_js_wikidata_val(Xow_wiki wdata_wiki, int id) {
		Xol_msg_itm msg = lang.Msg_mgr().Itm_by_id_or_null(id);
		byte[] rv = wdata_wiki == null ? msg.Val() : Pf_msg_mgr.Get_msg_val(wdata_wiki, lang, msg.Key(), Bry_.Ary_empty);
		if (Bry_finder.Find_fwd(rv, Byte_ascii.Apos) != Bry_.NotFound)
			rv = Bry_.Replace(rv, Bry_find, Bry_repl);
		return rv;
	}	private static final byte[] Bry_find = new byte[] {Byte_ascii.Apos}, Bry_repl = new byte[] {Byte_ascii.Backslash, Byte_ascii.Apos};
	private Bry_fmtr html_js_wikidata_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
		(	"  <script>"
		,	"  var xowa_wikidata_i18n = {"
		,	"    'languages'          : '~{languages}',"
		,	"    'toc'                : '~{toc}',"
		,	"    'labels'             : '~{labels}',"
		,	"    'aliasesHead'        : '~{aliasesHead}',"
		,	"    'descriptions'       : '~{descriptions}',"
		,	"    'links'              : '~{links}',"
		,	"    'claims'             : '~{claims}',"
		,	"    'json'               : '~{json}',"
		,	"    'language'           : '~{language}',"
		,	"    'wiki'               : '~{wiki}',"
		,	"    'label'              : '~{label}',"
		,	"    'aliases'            : '~{aliases}',"
		,	"    'description'        : '~{description}',"
		,	"    'link'               : '~{link}',"
		,	"    'property'           : '~{property}',"
		,	"    'value'              : '~{value}',"
		,	"    'references'         : '~{references}',"
		,	"    'qualifiers'         : '~{qualifiers}',"
		,	"    'comma-separator'    : '~{comma_separator}',"
		,	"    'word-separator'     : '~{word_separator}',"
		,	"    'parentheses'        : '~{parentheses}',"
		,	"    'jan'                : '~{jan}',"
		,	"    'feb'                : '~{feb}',"
		,	"    'mar'                : '~{mar}',"
		,	"    'apr'                : '~{apr}',"
		,	"    'may'                : '~{may}',"
		,	"    'jun'                : '~{jun}',"
		,	"    'jul'                : '~{jul}',"
		,	"    'aug'                : '~{aug}',"
		,	"    'sep'                : '~{sep}',"
		,	"    'oct'                : '~{oct}',"
		,	"    'nov'                : '~{nov}',"
		,	"    'dec'                : '~{dec}',"
		,	"    'ago'                : '~{ago}',"
		,	"    'novalue'            : '~{novalue}',"
		,	"    'somevalue'          : '~{somevalue}',"
		,	"    'links-wiki'         : '~{links_wiki}',"
		,	"    'links-wiktionary'   : '~{links_wiktionary}',"
		,	"    'links-wikisource'   : '~{links_wikisource}',"
		,	"    'links-wikivoyage'   : '~{links_wikivoyage}',"
		,	"    'links-wikiquote'    : '~{links_wikiquote}',"
		,	"    'links-wikibooks'    : '~{links_wikibooks}',"
		,	"    'links-wikiversity'  : '~{links_wikiversity}',"
		,	"    'links-wikinews'     : '~{links_wikinews}',"
		,	"    'links-special'      : '~{links_special}',"
		,	"    'plus'               : '~{plus}',"
		,	"    'minus'              : '~{minus}',"
		,	"    'plusminus'          : '~{plusminus}',"
		,	"    'degree'             : '~{degree}',"
		,	"    'minute'             : '~{minute}',"
		,	"    'second'             : '~{second}',"
		,	"    'north'              : '~{north}',"
		,	"    'south'              : '~{south}',"
		,	"    'west'               : '~{west}',"
		,	"    'east'               : '~{east}',"
		,	"    'meters'             : '~{meters}',"
		,	"    'julian'             : '~{julian}',"
		,	"    'decade'             : '~{decade}',"
		,	"    'century'            : '~{century}',"
		,	"    'millenium'          : '~{millenium}',"
		,	"    'years1e4'           : '~{years1e4}',"
		,	"    'years1e5'           : '~{years1e5}',"
		,	"    'years1e6'           : '~{years1e6}',"
		,	"    'years1e7'           : '~{years1e7}',"
		,	"    'years1e8'           : '~{years1e8}',"
		,	"    'years1e9'           : '~{years1e9}',"
		,	"    'bc'                 : '~{bc}',"
		,	"    'inTime'             : '~{inTime}',"
		,	"    'rank'               : '~{rank}',"
		,	"    'preferred'          : '~{preferred}',"
		,	"    'normal'             : '~{normal}',"
		,	"    'deprecated'         : '~{deprecated}'"
		,	"  };"
		,	"  </script>"
		,	"  <script src=\"~{app_root_dir}bin/any/javascript/xowa/wikidata/wikidata.js\" type='text/javascript'></script>"
		)
		, "languages", "toc", "labels", "aliasesHead", "descriptions", "links", "claims", "json", "language", "wiki", "label", "aliases", "description", "link", "property", "value", "references", "qualifiers"
		, "comma_separator", "word_separator", "parentheses", "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", "ago", "novalue", "somevalue"
		, "links_wiki", "links_wiktionary", "links_wikisource", "links_wikivoyage", "links_wikiquote", "links_wikibooks", "links_wikiversity", "links_wikinews", "links_special"
		, "plus", "minus", "plusminus", "degree", "minute", "second", "north", "south", "west", "east", "meters", "julian"
		, "decade", "century", "millenium", "years1e4", "years1e5", "years1e6", "years1e7", "years1e8", "years1e9", "bc", "inTime"
		, "rank", "preferred", "normal", "deprecated"
		, "app_root_dir"
		);
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_js_wikidata_fmt_))				html_js_wikidata_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_js_wikidata))					return this.Html_js_wikidata();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_html_js_wikidata_fmt_ = "html_js_wikidata_fmt_", Invk_html_js_wikidata = "html_js_wikidata";
}
