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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.langs.msgs.*;
public class Wdata_hwtr_msgs {
	public Wdata_hwtr_msgs(byte[][] brys) {							 int offset = 0; // String[] strs = String_.Ary(brys); // TEST
		toc_tbl_hdr								= brys[offset +  0];
		label_tbl_hdr							= brys[offset +  1];
		label_col_hdr_lang						= brys[offset +  2];
		label_col_hdr_text						= brys[offset +  3];
		alias_tbl_hdr							= brys[offset +  4];
		alias_col_hdr_lang						= brys[offset +  5];
		alias_col_hdr_text						= brys[offset +  6];
		descr_tbl_hdr							= brys[offset +  7];
		descr_col_hdr_lang						= brys[offset +  8];
		descr_col_hdr_text						= brys[offset +  9]; offset += 10;
		slink_tbl_hdr							= brys[offset +  0]; 
		slink_tbl_hdr_w							= brys[offset +  1];
		slink_tbl_hdr_d							= brys[offset +  2];
		slink_tbl_hdr_s							= brys[offset +  3];
		slink_tbl_hdr_v							= brys[offset +  4];
		slink_tbl_hdr_q							= brys[offset +  5];
		slink_tbl_hdr_b							= brys[offset +  6];
		slink_tbl_hdr_u							= brys[offset +  7];
		slink_tbl_hdr_n							= brys[offset +  8];
		slink_tbl_hdr_x							= brys[offset +  9]; offset += 10;
		slink_col_hdr_site						= brys[offset +  0];
		slink_col_hdr_text						= brys[offset +  1];
		slink_col_hdr_bdgs						= brys[offset +  2];
		claim_tbl_hdr							= brys[offset +  3];
		claim_col_hdr_prop_id					= brys[offset +  4];
		claim_col_hdr_prop_name					= brys[offset +  5];
		claim_col_hdr_val						= brys[offset +  6];
		claim_col_hdr_ref						= brys[offset +  7];
		claim_col_hdr_qual						= brys[offset +  8];
		claim_col_hdr_rank						= brys[offset +  9];
		json_tbl_hdr							= brys[offset + 10]; offset += 11;
		sym_list_comma							= brys[offset +  0];
		sym_list_word							= brys[offset +  1];
		sym_fmt_parentheses						= brys[offset +  2];
		sym_plus								= brys[offset +  3];
		sym_minus								= brys[offset +  4];
		sym_plusminus							= brys[offset +  5]; offset += 6;
		val_tid_novalue							= brys[offset +  0];
		val_tid_somevalue						= brys[offset +  1];
		rank_preferred							= brys[offset +  2];
		rank_normal								= brys[offset +  3];
		rank_deprecated							= brys[offset +  4]; offset += 5;
		time_month_01							= brys[offset +  0];
		time_month_02							= brys[offset +  1];
		time_month_03							= brys[offset +  2];
		time_month_04							= brys[offset +  3];
		time_month_05							= brys[offset +  4];
		time_month_06							= brys[offset +  5];
		time_month_07							= brys[offset +  6];
		time_month_08							= brys[offset +  7];
		time_month_09							= brys[offset +  8];
		time_month_10							= brys[offset +  9];
		time_month_11							= brys[offset + 10];
		time_month_12							= brys[offset + 11]; offset += 12;
		time_year_1e10_01						= brys[offset +  0];
		time_year_1e10_02						= brys[offset +  1];
		time_year_1e10_03						= brys[offset +  2];
		time_year_1e10_04						= brys[offset +  3];
		time_year_1e10_05						= brys[offset +  4];
		time_year_1e10_06						= brys[offset +  5];
		time_year_1e10_07						= brys[offset +  6];
		time_year_1e10_08						= brys[offset +  7];
		time_year_1e10_09						= brys[offset +  8]; offset += 9;
		time_relative_bc						= brys[offset +  0];
		time_relative_ago						= brys[offset +  1];
		time_relative_in						= brys[offset +  2];
		time_julian								= brys[offset +  3]; offset += 4;
		geo_dir_n								= brys[offset +  0];
		geo_dir_s								= brys[offset +  1];
		geo_dir_e								= brys[offset +  2];
		geo_dir_w								= brys[offset +  3];
		geo_unit_degree							= brys[offset +  4];
		geo_unit_minute							= brys[offset +  5];
		geo_unit_second							= brys[offset +  6];
		geo_meters								= brys[offset +  7];
	}
	public static Wdata_hwtr_msgs new_(Xow_wiki wiki) {
		byte[][] brys = new_brys(wiki
		, "toc"
		, "xowa-wikidata-labels"			, "xowa-wikidata-language"				, "xowa-wikidata-label"
		, "xowa-wikidata-aliasesHead"		, "xowa-wikidata-language"				, "xowa-wikidata-aliases"
		, "xowa-wikidata-descriptions"		, "xowa-wikidata-language"				, "xowa-wikidata-description"
		, "xowa-wikidata-links"
		, "xowa-wikidata-links-wiki"		, "xowa-wikidata-links-wiktionary"		, "xowa-wikidata-links-wikisource", "xowa-wikidata-links-wikivoyage"
		, "xowa-wikidata-links-wikiquote"	, "xowa-wikidata-links-wikibooks"		, "xowa-wikidata-links-wikiversity", "xowa-wikidata-links-wikinews", "xowa-wikidata-links-special"
		, "xowa-wikidata-wiki"				, "xowa-wikidata-link"					, "xowa-wikidata-badges"
		, "xowa-wikidata-claims"			, "xowa-wikidata-property-id"			, "xowa-wikidata-property", "xowa-wikidata-value", "xowa-wikidata-references", "xowa-wikidata-qualifiers", "Wikibase-diffview-rank"
		, "xowa-wikidata-json"
		, "comma-separator", "word-separator", "parentheses"
		, "xowa-wikidata-plus", "xowa-wikidata-minus", "xowa-wikidata-plusminus"
		, "xowa-wikidata-novalue", "xowa-wikidata-somevalue"
		, "xowa-wikidata-preferred", "xowa-wikidata-normal", "xowa-wikidata-deprecated"
		, "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"
		, "xowa-wikidata-decade", "xowa-wikidata-century", "xowa-wikidata-millenium", "xowa-wikidata-years1e4", "xowa-wikidata-years1e5", "xowa-wikidata-years1e6", "xowa-wikidata-years1e7", "xowa-wikidata-years1e8", "xowa-wikidata-years1e9"
		, "ago", "xowa-wikidata-bc", "xowa-wikidata-inTime"
		, "xowa-wikidata-julian"
		, "xowa-wikidata-north", "xowa-wikidata-south", "xowa-wikidata-east", "xowa-wikidata-west"
		, "xowa-wikidata-degree", "xowa-wikidata-minute", "xowa-wikidata-second"
		, "xowa-wikidata-meters"
		);
		return new Wdata_hwtr_msgs(brys);
	}
	private static byte[][] new_brys(Xow_wiki wiki, String... ids) {
		int len = ids.length;
		byte[][] rv = new byte[len][];
		for (int i = 0; i < len; ++i)
			rv[i] = gplx.html.Html_utl.Escape_html_as_bry(wiki.Msg_mgr().Val_by_key_obj(ids[i]));
		return rv;
	}
	public byte[] Toc_tbl_hdr() {return toc_tbl_hdr;} private final byte[] toc_tbl_hdr;
	public byte[] Label_tbl_hdr() {return label_tbl_hdr;} private final byte[] label_tbl_hdr;
	public byte[] Label_col_hdr_lang() {return label_col_hdr_lang;} private final byte[] label_col_hdr_lang;
	public byte[] Label_col_hdr_text() {return label_col_hdr_text;} private final byte[] label_col_hdr_text;
	public byte[] Alias_tbl_hdr() {return alias_tbl_hdr;} private final byte[] alias_tbl_hdr;
	public byte[] Alias_col_hdr_lang() {return alias_col_hdr_lang;} private final byte[] alias_col_hdr_lang;
	public byte[] Alias_col_hdr_text() {return alias_col_hdr_text;} private final byte[] alias_col_hdr_text;
	public byte[] Descr_tbl_hdr() {return descr_tbl_hdr;} private final byte[] descr_tbl_hdr;
	public byte[] Descr_col_hdr_lang() {return descr_col_hdr_lang;} private final byte[] descr_col_hdr_lang;
	public byte[] Descr_col_hdr_text() {return descr_col_hdr_text;} private final byte[] descr_col_hdr_text;
	public byte[] Slink_tbl_hdr() {return slink_tbl_hdr;} private final byte[] slink_tbl_hdr;
	public byte[] Slink_tbl_hdr_w() {return slink_tbl_hdr_w;} private final byte[] slink_tbl_hdr_w;
	public byte[] Slink_tbl_hdr_d() {return slink_tbl_hdr_d;} private final byte[] slink_tbl_hdr_d;
	public byte[] Slink_tbl_hdr_s() {return slink_tbl_hdr_s;} private final byte[] slink_tbl_hdr_s;
	public byte[] Slink_tbl_hdr_v() {return slink_tbl_hdr_v;} private final byte[] slink_tbl_hdr_v;
	public byte[] Slink_tbl_hdr_q() {return slink_tbl_hdr_q;} private final byte[] slink_tbl_hdr_q;
	public byte[] Slink_tbl_hdr_b() {return slink_tbl_hdr_b;} private final byte[] slink_tbl_hdr_b;
	public byte[] Slink_tbl_hdr_u() {return slink_tbl_hdr_u;} private final byte[] slink_tbl_hdr_u;
	public byte[] Slink_tbl_hdr_n() {return slink_tbl_hdr_n;} private final byte[] slink_tbl_hdr_n;
	public byte[] Slink_tbl_hdr_x() {return slink_tbl_hdr_x;} private final byte[] slink_tbl_hdr_x;
	public byte[] Slink_col_hdr_site() {return slink_col_hdr_site;} private final byte[] slink_col_hdr_site;
	public byte[] Slink_col_hdr_text() {return slink_col_hdr_text;} private final byte[] slink_col_hdr_text;
	public byte[] Slink_col_hdr_bdgs() {return slink_col_hdr_bdgs;} private final byte[] slink_col_hdr_bdgs;
	public byte[] Claim_tbl_hdr() {return claim_tbl_hdr;} private final byte[] claim_tbl_hdr;
	public byte[] Claim_col_hdr_prop_id() {return claim_col_hdr_prop_id;} private final byte[] claim_col_hdr_prop_id;
	public byte[] Claim_col_hdr_prop_name() {return claim_col_hdr_prop_name;} private final byte[] claim_col_hdr_prop_name;
	public byte[] Claim_col_hdr_val() {return claim_col_hdr_val;} private final byte[] claim_col_hdr_val;
	public byte[] Claim_col_hdr_ref() {return claim_col_hdr_ref;} private final byte[] claim_col_hdr_ref;
	public byte[] Claim_col_hdr_qual() {return claim_col_hdr_qual;} private final byte[] claim_col_hdr_qual;
	public byte[] Claim_col_hdr_rank() {return claim_col_hdr_rank;} private final byte[] claim_col_hdr_rank;
	public byte[] Json_tbl_hdr() {return json_tbl_hdr;} private final byte[] json_tbl_hdr;
	public byte[] Val_tid_novalue() {return val_tid_novalue;} private final byte[] val_tid_novalue;
	public byte[] Val_tid_somevalue() {return val_tid_somevalue;} private final byte[] val_tid_somevalue;
	public byte[] Rank_preferred() {return rank_preferred;} private final byte[] rank_preferred;
	public byte[] Rank_normal() {return rank_normal;} private final byte[] rank_normal;
	public byte[] Rank_deprecated() {return rank_deprecated;} private final byte[] rank_deprecated;
	public byte[] Sym_list_comma() {return sym_list_comma;} private final byte[] sym_list_comma;
	public byte[] Sym_list_word() {return sym_list_word;} private final byte[] sym_list_word;
	public byte[] Sym_fmt_parentheses() {return sym_fmt_parentheses;} private final byte[] sym_fmt_parentheses;
	public byte[] Sym_plus() {return sym_plus;} private final byte[] sym_plus;
	public byte[] Sym_minus() {return sym_minus;} private final byte[] sym_minus;
	public byte[] Sym_plusminus() {return sym_plusminus;} private final byte[] sym_plusminus;
	public byte[] Time_month_01() {return time_month_01;} private final byte[] time_month_01;
	public byte[] Time_month_02() {return time_month_02;} private final byte[] time_month_02;
	public byte[] Time_month_03() {return time_month_03;} private final byte[] time_month_03;
	public byte[] Time_month_04() {return time_month_04;} private final byte[] time_month_04;
	public byte[] Time_month_05() {return time_month_05;} private final byte[] time_month_05;
	public byte[] Time_month_06() {return time_month_06;} private final byte[] time_month_06;
	public byte[] Time_month_07() {return time_month_07;} private final byte[] time_month_07;
	public byte[] Time_month_08() {return time_month_08;} private final byte[] time_month_08;
	public byte[] Time_month_09() {return time_month_09;} private final byte[] time_month_09;
	public byte[] Time_month_10() {return time_month_10;} private final byte[] time_month_10;
	public byte[] Time_month_11() {return time_month_11;} private final byte[] time_month_11;
	public byte[] Time_month_12() {return time_month_12;} private final byte[] time_month_12;
	public byte[] Time_year_1e10_01() {return time_year_1e10_01;} private final byte[] time_year_1e10_01;
	public byte[] Time_year_1e10_02() {return time_year_1e10_02;} private final byte[] time_year_1e10_02;
	public byte[] Time_year_1e10_03() {return time_year_1e10_03;} private final byte[] time_year_1e10_03;
	public byte[] Time_year_1e10_04() {return time_year_1e10_04;} private final byte[] time_year_1e10_04;
	public byte[] Time_year_1e10_05() {return time_year_1e10_05;} private final byte[] time_year_1e10_05;
	public byte[] Time_year_1e10_06() {return time_year_1e10_06;} private final byte[] time_year_1e10_06;
	public byte[] Time_year_1e10_07() {return time_year_1e10_07;} private final byte[] time_year_1e10_07;
	public byte[] Time_year_1e10_08() {return time_year_1e10_08;} private final byte[] time_year_1e10_08;
	public byte[] Time_year_1e10_09() {return time_year_1e10_09;} private final byte[] time_year_1e10_09;
	public byte[] Time_relative_bc() {return time_relative_bc;} private final byte[] time_relative_bc;
	public byte[] Time_relative_ago() {return time_relative_ago;} private final byte[] time_relative_ago;
	public byte[] Time_relative_in() {return time_relative_in;} private final byte[] time_relative_in;
	public byte[] Time_julian() {return time_julian;} private final byte[] time_julian;
	public byte[] Geo_dir_n() {return geo_dir_n;} private final byte[] geo_dir_n;
	public byte[] Geo_dir_s() {return geo_dir_s;} private final byte[] geo_dir_s;
	public byte[] Geo_dir_e() {return geo_dir_e;} private final byte[] geo_dir_e;
	public byte[] Geo_dir_w() {return geo_dir_w;} private final byte[] geo_dir_w;
	public byte[] Geo_unit_degree() {return geo_unit_degree;} private final byte[] geo_unit_degree;
	public byte[] Geo_unit_minute() {return geo_unit_minute;} private final byte[] geo_unit_minute;
	public byte[] Geo_unit_second() {return geo_unit_second;} private final byte[] geo_unit_second;
	public byte[] Geo_meters() {return geo_meters;} private final byte[] geo_meters;
}
