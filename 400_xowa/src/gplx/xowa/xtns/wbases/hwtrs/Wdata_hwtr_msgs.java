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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.domains.*;
public class Wdata_hwtr_msgs {
	public Wdata_hwtr_msgs(byte[][] brys) {							 int offset = 0; // String[] strs = String_.Ary(brys); // TEST
		this.ary = brys;
		toggle_title_n							= brys[offset +  0];
		toggle_title_y							= brys[offset +  1];
		toc_tbl_hdr								= brys[offset +  2];
		oview_alias_y							= brys[offset +  3];
		oview_alias_n							= brys[offset +  4]; offset += 5;
		langtext_col_lang_name					= brys[offset +  0];
		langtext_col_lang_code					= brys[offset +  1];
		slink_tbl_hdr_fmt						= brys[offset +  2];
		slink_tbl_hdr_fmt_other					= brys[offset +  3];
		slink_col_hdr_text						= brys[offset +  4]; offset += 5;
		label_tbl_hdr							= brys[offset +  0];
		label_col_hdr							= brys[offset +  1];
		alias_tbl_hdr							= brys[offset +  2];
		alias_col_hdr							= brys[offset +  3];
		descr_tbl_hdr							= brys[offset +  4];
		descr_col_hdr							= brys[offset +  5];
		claim_tbl_hdr							= brys[offset +  6];
		json_div_hdr							= brys[offset +  7]; offset += 8;
		sym_list_comma							= brys[offset +  0];
		sym_list_word							= brys[offset +  1];
		sym_time_spr							= brys[offset +  2];
		sym_plus								= brys[offset +  3];
		sym_minus								= brys[offset +  4];
		sym_plusminus							= brys[offset +  5]; 
		sym_fmt_parentheses						= brys[offset +  6]; offset += 7;
		val_tid_novalue							= brys[offset +  0];
		val_tid_somevalue						= brys[offset +  1]; offset += 2;
		this.month_bgn_idx = offset;
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
		time_year_idx = offset;
		time_year_1e10_00						= brys[offset +  0];
		time_year_1e10_01						= brys[offset +  1];
		time_year_1e10_02						= brys[offset +  2];
		time_year_1e10_03						= brys[offset +  3];
		time_year_1e10_04						= brys[offset +  4];
		time_year_1e10_05						= brys[offset +  5];
		time_year_1e10_06						= brys[offset +  6];
		time_year_1e10_07						= brys[offset +  7];
		time_year_1e10_08						= brys[offset +  8];
		time_year_1e10_09						= brys[offset +  9]; offset += 10;
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
		Bry_fmtr fmtr = Bry_fmtr.new_( slink_tbl_hdr_fmt, "wiki_type");
		Bry_bfr bfr = Bry_bfr_.New_w_size(64);
		slink_tbl_hdr_w = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wikipedia));
		slink_tbl_hdr_d = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wiktionary));
		slink_tbl_hdr_s = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wikisource));
		slink_tbl_hdr_v = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wikivoyage));
		slink_tbl_hdr_q = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wikiquote));
		slink_tbl_hdr_b = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wikibooks));
		slink_tbl_hdr_u = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wikiversity));
		slink_tbl_hdr_n = fmtr.Bld_bry_many(bfr, Name_(Xow_domain_tid_.Bry__wikinews));
		slink_tbl_hdr_x = fmtr.Bld_bry_many(bfr, slink_tbl_hdr_fmt_other);
	}
	public byte[][] Ary() {return ary;} private final    byte[][] ary;
	public int Month_bgn_idx() {return month_bgn_idx;} private final    int month_bgn_idx;
	public byte[] Toggle_title_y() {return toggle_title_y;} private byte[] toggle_title_y;
	public byte[] Toggle_title_n() {return toggle_title_n;} private byte[] toggle_title_n;
	public byte[] Toc_tbl_hdr() {return toc_tbl_hdr;} private final    byte[] toc_tbl_hdr;
	public byte[] Oview_alias_y() {return oview_alias_y;} private final    byte[] oview_alias_y;
	public byte[] Oview_alias_n() {return oview_alias_n;} private final    byte[] oview_alias_n;
	public byte[] Langtext_col_lang_name() {return langtext_col_lang_name;} private final    byte[] langtext_col_lang_name;
	public byte[] Langtext_col_lang_code() {return langtext_col_lang_code;} private final    byte[] langtext_col_lang_code;
	public byte[] Label_tbl_hdr() {return label_tbl_hdr;} private final    byte[] label_tbl_hdr;
	public byte[] Label_col_hdr() {return label_col_hdr;} private final    byte[] label_col_hdr;
	public byte[] Alias_tbl_hdr() {return alias_tbl_hdr;} private final    byte[] alias_tbl_hdr;
	public byte[] Alias_col_hdr() {return alias_col_hdr;} private final    byte[] alias_col_hdr;
	public byte[] Descr_tbl_hdr() {return descr_tbl_hdr;} private final    byte[] descr_tbl_hdr;
	public byte[] Descr_col_hdr() {return descr_col_hdr;} private final    byte[] descr_col_hdr;
	public byte[] Slink_tbl_hdr_fmt()		{return slink_tbl_hdr_fmt;} private final    byte[] slink_tbl_hdr_fmt;
	public byte[] Slink_tbl_hdr_fmt_other() {return slink_tbl_hdr_fmt_other;} private final    byte[] slink_tbl_hdr_fmt_other;
	public byte[] Slink_tbl_hdr_w() {return slink_tbl_hdr_w;} private final    byte[] slink_tbl_hdr_w;
	public byte[] Slink_tbl_hdr_d() {return slink_tbl_hdr_d;} private final    byte[] slink_tbl_hdr_d;
	public byte[] Slink_tbl_hdr_s() {return slink_tbl_hdr_s;} private final    byte[] slink_tbl_hdr_s;
	public byte[] Slink_tbl_hdr_v() {return slink_tbl_hdr_v;} private final    byte[] slink_tbl_hdr_v;
	public byte[] Slink_tbl_hdr_q() {return slink_tbl_hdr_q;} private final    byte[] slink_tbl_hdr_q;
	public byte[] Slink_tbl_hdr_b() {return slink_tbl_hdr_b;} private final    byte[] slink_tbl_hdr_b;
	public byte[] Slink_tbl_hdr_u() {return slink_tbl_hdr_u;} private final    byte[] slink_tbl_hdr_u;
	public byte[] Slink_tbl_hdr_n() {return slink_tbl_hdr_n;} private final    byte[] slink_tbl_hdr_n;
	public byte[] Slink_tbl_hdr_x() {return slink_tbl_hdr_x;} private final    byte[] slink_tbl_hdr_x;
	public byte[] Slink_col_hdr_text() {return slink_col_hdr_text;} private final    byte[] slink_col_hdr_text;
	public byte[] Claim_tbl_hdr() {return claim_tbl_hdr;} private final    byte[] claim_tbl_hdr;
	public byte[] Json_div_hdr() {return json_div_hdr;} private final    byte[] json_div_hdr;
	public byte[] Val_tid_novalue() {return val_tid_novalue;} private final    byte[] val_tid_novalue;
	public byte[] Val_tid_somevalue() {return val_tid_somevalue;} private final    byte[] val_tid_somevalue;
	public byte[] Sym_list_comma() {return sym_list_comma;} private final    byte[] sym_list_comma;
	public byte[] Sym_list_word() {return sym_list_word;} private final    byte[] sym_list_word;
	public byte[] Sym_time_spr() {return sym_time_spr;} private final    byte[] sym_time_spr;
	public byte[] Sym_plus() {return sym_plus;} private final    byte[] sym_plus;
	public byte[] Sym_minus() {return sym_minus;} private final    byte[] sym_minus;
	public byte[] Sym_plusminus() {return sym_plusminus;} private final    byte[] sym_plusminus;
	public byte[] Sym_fmt_parentheses() {return sym_fmt_parentheses;} private final    byte[] sym_fmt_parentheses;
	public byte[] Time_month_01() {return time_month_01;} private final    byte[] time_month_01;
	public byte[] Time_month_02() {return time_month_02;} private final    byte[] time_month_02;
	public byte[] Time_month_03() {return time_month_03;} private final    byte[] time_month_03;
	public byte[] Time_month_04() {return time_month_04;} private final    byte[] time_month_04;
	public byte[] Time_month_05() {return time_month_05;} private final    byte[] time_month_05;
	public byte[] Time_month_06() {return time_month_06;} private final    byte[] time_month_06;
	public byte[] Time_month_07() {return time_month_07;} private final    byte[] time_month_07;
	public byte[] Time_month_08() {return time_month_08;} private final    byte[] time_month_08;
	public byte[] Time_month_09() {return time_month_09;} private final    byte[] time_month_09;
	public byte[] Time_month_10() {return time_month_10;} private final    byte[] time_month_10;
	public byte[] Time_month_11() {return time_month_11;} private final    byte[] time_month_11;
	public byte[] Time_month_12() {return time_month_12;} private final    byte[] time_month_12;
	public int Time_year_idx() {return time_year_idx;} private final    int time_year_idx;
	public byte[] Time_year_1e10_00() {return time_year_1e10_00;} private final    byte[] time_year_1e10_00;
	public byte[] Time_year_1e10_01() {return time_year_1e10_01;} private final    byte[] time_year_1e10_01;
	public byte[] Time_year_1e10_02() {return time_year_1e10_02;} private final    byte[] time_year_1e10_02;
	public byte[] Time_year_1e10_03() {return time_year_1e10_03;} private final    byte[] time_year_1e10_03;
	public byte[] Time_year_1e10_04() {return time_year_1e10_04;} private final    byte[] time_year_1e10_04;
	public byte[] Time_year_1e10_05() {return time_year_1e10_05;} private final    byte[] time_year_1e10_05;
	public byte[] Time_year_1e10_06() {return time_year_1e10_06;} private final    byte[] time_year_1e10_06;
	public byte[] Time_year_1e10_07() {return time_year_1e10_07;} private final    byte[] time_year_1e10_07;
	public byte[] Time_year_1e10_08() {return time_year_1e10_08;} private final    byte[] time_year_1e10_08;
	public byte[] Time_year_1e10_09() {return time_year_1e10_09;} private final    byte[] time_year_1e10_09;
	public byte[] Time_relative_bc() {return time_relative_bc;} private final    byte[] time_relative_bc;
	public byte[] Time_relative_ago() {return time_relative_ago;} private final    byte[] time_relative_ago;
	public byte[] Time_relative_in() {return time_relative_in;} private final    byte[] time_relative_in;
	public byte[] Time_julian() {return time_julian;} private final    byte[] time_julian;
	public byte[] Geo_dir_n() {return geo_dir_n;} private final    byte[] geo_dir_n;
	public byte[] Geo_dir_s() {return geo_dir_s;} private final    byte[] geo_dir_s;
	public byte[] Geo_dir_e() {return geo_dir_e;} private final    byte[] geo_dir_e;
	public byte[] Geo_dir_w() {return geo_dir_w;} private final    byte[] geo_dir_w;
	public byte[] Geo_unit_degree() {return geo_unit_degree;} private final    byte[] geo_unit_degree;
	public byte[] Geo_unit_minute() {return geo_unit_minute;} private final    byte[] geo_unit_minute;
	public byte[] Geo_unit_second() {return geo_unit_second;} private final    byte[] geo_unit_second;
	public byte[] Geo_meters() {return geo_meters;} private final    byte[] geo_meters;
	public static Wdata_hwtr_msgs new_en_() {
		byte[][] brys = Bry_.Ary
		( "hide", "show", "Contents"
		, "Also known as:", "No aliases defined."
		, "Language", "Code"
		, "Links (~{wiki_type})", "other sites", "Linked page"
		, "Labels", "Label"
		, "Aliases", "Alias"
		, "Descriptions", "Description"
		, "Statements"
		, "JSON"
		, ",&#32;", "&#32;", ":"
		, "+", "-", "±", "(~{0})"
		, "—", "?"
		, "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
		, "~{0}", "~{0}0s", "~{0}. century", "~{0}. millenium", "~{0}0,000 years", "~{0}00,000 years", "~{0} million years", "~{0}0 million years", "~{0}00 million years", "~{0} billion years"
		, "~{0} BC", "~{0} ago", "in ~{0}", "<sup>jul</sup>"
		, "N", "S", "E", "W"
		, "°", "′", "″"
		, "&nbsp;m"
		);
		return new Wdata_hwtr_msgs(brys);
	}
	public static Wdata_hwtr_msgs new_(Xow_msg_mgr msg_mgr) {
		byte[][] brys = new_brys(msg_mgr
		, "hide", "show", "toc"
		, "wikibase-aliases-label"						, "wikibase-aliases-empty"
		, "wikibase-sitelinks-sitename-columnheading"	, "wikibase-sitelinks-siteid-columnheading"
		, "xowa-wikidata-sitelinks-hdr"					, "xowa-wikidata-sitelinks-hdr-special", "wikibase-sitelinks-link-columnheading"
		, "xowa-wikidata-labels-hdr"					, "xowa-wikidata-labels-col"
		, "xowa-wikidata-aliases-hdr"					, "xowa-wikidata-aliases-col"
		, "xowa-wikidata-descriptions-hdr"				, "xowa-wikidata-descriptions-col"
		, "wikibase-statements"						
		, "xowa-wikidata-json"
		, "comma-separator", "word-separator", "xowa-wikidata-time-spr"
		, "xowa-wikidata-plus", "xowa-wikidata-minus", "xowa-wikidata-plusminus"
		, "parentheses"
		, "xowa-wikidata-novalue", "xowa-wikidata-somevalue"
		, "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"
		, "xowa-wikidata-year", "xowa-wikidata-decade", "xowa-wikidata-century", "xowa-wikidata-millenium", "xowa-wikidata-years1e4", "xowa-wikidata-years1e5", "xowa-wikidata-years1e6", "xowa-wikidata-years1e7", "xowa-wikidata-years1e8", "xowa-wikidata-years1e9"
		, "ago", "xowa-wikidata-bc", "xowa-wikidata-inTime"
		, "xowa-wikidata-julian"
		, "xowa-wikidata-north", "xowa-wikidata-south", "xowa-wikidata-east", "xowa-wikidata-west"
		, "xowa-wikidata-degree", "xowa-wikidata-minute", "xowa-wikidata-second"
		, "xowa-wikidata-meters"
		);
		return new Wdata_hwtr_msgs(brys);
	}
	private static byte[][] new_brys(Xow_msg_mgr msg_mgr, String... ids) {
		int len = ids.length;
		byte[][] rv = new byte[len][];
		for (int i = 0; i < len; ++i)
			rv[i] = msg_mgr.Val_by_key_obj(ids[i]);	// TOMBSTONE: do not call "Gfh_utl.Escape_html_as_bry" else "<sup>jul</sup>" will be rendered literally; PAGE:wd:Q2 DATE:2016-11-10
		return rv;
	}
	private static byte[] Name_(byte[] v) {return Bry_.Ucase__1st(Bry_.Copy(v));}
}
