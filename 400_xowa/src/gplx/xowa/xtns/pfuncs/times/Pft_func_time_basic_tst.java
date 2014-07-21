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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pft_func_time_basic_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before	public void init()					{fxt.Reset(); Tfds.Now_set(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));}
	@After public void term()				{Tfds.Now_enabled_n_();}
	@Test   public void Utc_date()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2012-01-02 03:04:05}}"			, "{{test}}"			, "2012-01-02");}
	@Test   public void Utc_time()				{fxt.Test_parse_tmpl_str_test("{{#time:h:i:s A|2012-01-02 03:04:05}}"		, "{{test}}"			, "03:04:05 AM");}
	@Test   public void Utc_dayOfYear()			{fxt.Test_parse_tmpl_str_test("{{#time:z|2012-01-01 03:04:05}}"				, "{{test}}"			, "0");}
	@Test   public void Utc_escape_basic()		{fxt.Test_parse_tmpl_str_test("{{#time:\\Y Y|2012-01-02 03:04:05}}"			, "{{test}}"			, "Y 2012");}
	@Test   public void Utc_escape_double()		{fxt.Test_parse_tmpl_str_test("{{#time:\\\\ Y|2012-01-02 03:04:05}}"		, "{{test}}"			, "\\ 2012");}
	@Test   public void Utc_escape_eos()		{fxt.Test_parse_tmpl_str_test("{{#time:b\\|2012-01-02 03:04:05}}"			, "{{test}}"			, "b\\");}
	@Test   public void Utc_escape_newLine()	{fxt.Test_parse_tmpl_str_test("{{#time:b\\\nb|2012-01-02 03:04:05}}"		, "{{test}}"			, "b\nb");}
	@Test   public void Utc_quote_basic()		{fxt.Test_parse_tmpl_str_test("{{#time:b \"Y m d\" b|2012-01-02 03:04:05}}"	, "{{test}}"			, "b Y m d b");}
	@Test   public void Utc_quote_double()		{fxt.Test_parse_tmpl_str_test("{{#time:b \"\" b|2012-01-02 03:04:05}}"		, "{{test}}"			, "b \" b");}
	@Test   public void Utc_quote_eos()			{fxt.Test_parse_tmpl_str_test("{{#time:b \"|2012-01-02 03:04:05}}"			, "{{test}}"			, "b \"");}
	@Test   public void Utc_ws()				{fxt.Test_parse_tmpl_str_test("{{#time: Y-m-d |2012-01-02 03:04:05}}"		, "{{test}}"			, "2012-01-02");}
	@Test   public void Lcl_date()				{fxt.Test_parse_tmpl_str_test("{{#timel:Y-m-d|2012-01-02 03:04:05}}"		, "{{test}}"			, "2012-01-02");}
	@Test   public void Utc_dow_abrv()			{fxt.Test_parse_tmpl_str_test("{{#time:D|20120301}}"						, "{{test}}"			, "Thu");}
	@Test   public void Utc_ymd()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|20120102}}"					, "{{test}}"			, "2012-01-02");}
	@Test   public void Utc_ym()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|201201}}"						, "{{test}}"			, "2012-01-01");}
	@Test   public void Utc_md()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2-13}}"						, "{{test}}"			, "2012-02-13");}	// PURPOSE.fix: m-d failed
	@Test   public void Slashes()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2/13/12}}"						, "{{test}}"			, "2012-02-13");}	// PURPOSE: assert slashes work
	@Test   public void Utc_day()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|March 27}}"					, "{{test}}"			, "2012-03-27");}
	@Test   public void Parse_day()				{fxt.Test_parse_tmpl_str_test("{{#time:m d|March 27}}"						, "{{test}}"			, "03 27");}
	@Test   public void Month_name()			{fxt.Test_parse_tmpl_str_test("{{#time:M|May 1 2012}}"						, "{{test}}"			, "May");}
	@Test   public void Error()					{fxt.Test_parse_tmpl_str_test("{{#time:M|2}}"								, "{{test}}"			, "<strong class=\"error\">Invalid year: 2</strong>");}
	@Test   public void Error2()				{fxt.Test_parse_tmpl_str_test("{{#time:Y|July 28 - August 1, 1975}}"		, "{{test}}"			, "<strong class=\"error\">Invalid time</strong>");}
	@Test   public void Error3()				{fxt.Test_parse_tmpl_str_test("{{#time:Y|106BC-43BC}}"						, "{{test}}"			, "<strong class=\"error\">Invalid time</strong>");}
	@Test   public void Timestamp()				{fxt.Test_parse_tmpl_str_test("{{#time:F j, Y|20060827072854}}"				, "{{test}}"			, "August 27, 2006");}	// PAGE:en.w:Great Fire of London
	@Test   public void Unixtime_read()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d h:i:s A|@0}}"					, "{{test}}"			, "1970-01-01 12:00:00 AM");}	// EX:w:Wikipedia:WikiProject_Articles_for_creation/BLD_Preload
	@Test   public void Unixtime_read_neg()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d h:i:s A|@-3600}}"				, "{{test}}"			, "1969-12-31 11:00:00 PM");}	// EX:w:Wikipedia:WikiProject_Articles_for_creation/October_-_November_2012_Backlog_Elimination_Drive; DATE:2014-05-10
	@Test   public void Unixtime_8_digit()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d h:i:s A|@20120304}}"			, "{{test}}"			, "1970-08-21 08:58:24 PM");}	// PURPOSE: make sure yyyy-MM-dd is gobbled by "@" and not evaluated again as date; EX:w:Wikipedia:WikiProject_Articles_for_creation/October_-_November_2012_Backlog_Elimination_Drive; DATE:2014-05-10
	@Test   public void Unixtime_write()		{fxt.Test_parse_tmpl_str_test("{{#time:U|2012-08-02}}"						, "{{test}}"			, "1343865600");}	// PAGE:en.w:Opa (programming language)
	@Test   public void Year_4_digit()			{fxt.Test_parse_tmpl_str_test("{{#time:Y|June 20, 451}}"					, "{{test}}"			, "0451");}	// PAGE:en.w:Battle of the Catalaunian Plains
	@Test   public void Year_month()			{fxt.Test_parse_tmpl_str_test("{{#time:F Y|November 2012}}"					, "{{test}}"			, "November 2012");}	// PAGE:en.w:Project:Current events
	@Test   public void Day_addition_with_dash(){fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2011-11-13 +1 day}}"			, "{{test}}"			, "2011-11-14");}	// PURPOSE: +1 day was becoming -1 day b/c it was picking up - at -13; PAGE:en.w:Template:POTD/2012-10-09	
	@Test   public void Fmt_time_before_day()	{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d H:i|04:50, 17 December 2010}}"	, "{{test}}"			, "2010-12-17 04:50");}	// PURPOSE: strange day time format; PAGE:en.w:Talk:Battle of Fort Washington
	@Test   public void Fmt_time_before_day_2()	{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d H:i|04:50, December 11, 2010}}", "{{test}}"			, "2010-12-11 04:50");}	// PURPOSE: handle hh:nn ymd; PAGE:en.w:Wikipedia:WikiProject_Maine/members; DATE:2014-06-25
	@Test   public void Hour_zero()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d H:i|August 18 2006 00:14}}"	, "{{test}}"			, "2006-08-18 00:14");}	// PURPOSE: fix; invalid hour; PAGE:en.w:Talk:Martin Luther
	@Test   public void Iso()					{fxt.Test_parse_tmpl_str_test("{{#time:c|2012-01-02 03:04:05}}"				, "{{test}}"			, "2012-01-02T03:04:05-05:00");}
	@Test   public void Ymdh()					{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2012-01-02-99}}"				, "{{test}}"			, "2012-01-06");}	// PURPOSE: "99" is treated as 99th hour; EX:w:LimeWire; DATE:2014-03-24
	@Test   public void Ymdh_noop()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2012-01-02-100}}"				, "{{test}}"			, "2012-01-02");}	// PURPOSE: "100" is ignored
	@Test   public void Month_is_0()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2012-00-01}}"					, "{{test}}"			, "2011-12-01");}	// PURPOSE: "0" for month is treated as -1; EX:w:Mariyinsky_Palace; DATE:2014-03-25
	@Test   public void Day_is_0()				{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2012-12-00}}"					, "{{test}}"			, "2012-11-30");}	// PURPOSE: "0" for day is treated as -1; EX:w:Mariyinsky_Palace; DATE:2014-03-25
	@Test   public void Day_suffix_y()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|11th Dec 2013}}"				, "{{test}}"			, "2013-12-11");}	// PURPOSE: ignore suffix days; EX:11th; DATE:2014-03-25
	@Test   public void Day_suffix_n()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|32nd Dec 2013}}"				, "{{test}}"			, "<strong class=\"error\">Invalid day: 32</strong>");}
	@Test   public void Day_rel_today()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|today}}"						, "{{test}}"			, "2012-01-02");}
	@Test   public void Day_rel_tomorrow()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|tomorrow}}"					, "{{test}}"			, "2012-01-03");}
	@Test   public void Day_rel_yesterday()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|yesterday}}"					, "{{test}}"			, "2012-01-01");}
	@Test   public void Unit_rel_year_next()	{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|next year}}"					, "{{test}}"			, "2013-01-02");}	// DATE:2014-05-02
	@Test   public void Unit_rel_year_last()	{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|last year}}"					, "{{test}}"			, "2011-01-02");}
	@Test   public void Unit_rel_year_previous(){fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|previous year}}"				, "{{test}}"			, "2011-01-02");}
	@Test   public void Unit_rel_year_this()	{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|this year}}"					, "{{test}}"			, "2012-01-02");}
	@Test   public void Time_rel_now()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d h:i:s A|now}}"					, "{{test}}"			, "2012-01-02 03:05:05 AM");}	// NOTE: minute is 5, not 4, b/c each call to DateAdp_.Now() automatically increments by 1 minute; DATE:2014-04-13
	@Test   public void Empty_is_today()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|}}"							, "{{test}}"			, "2012-01-02");}	// tested on MW
	@Test   public void Day_name_today()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|Monday}}"						, "{{test}}"			, "2012-01-02");}	// 2012-01-02 is Monday, so return Monday; DATE:2014-05-02
	@Test   public void Day_name_future_1()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|Saturday}}"					, "{{test}}"			, "2012-01-07");}	// return next Sunday; DATE:2014-05-02
	@Test   public void Day_name_future_2()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|Sunday}}"						, "{{test}}"			, "2012-01-08");}	// return next Saturday; DATE:2014-05-02
	@Test   public void Day_name_dow()			{fxt.Test_parse_tmpl_str_test("{{#time:w|Monday}}"							, "{{test}}"			, "1");}
	@Test   public void Timezone_offset()		{
		DateAdp.Timezone_offset_test = -18000;
		fxt.Test_parse_tmpl_str_test("{{#time:Z|}}"								, "{{test}}"			, "-18000");
		DateAdp.Timezone_offset_test = Int_.MinValue;
	}			// Z=timezone offset in seconds; http://php.net/manual/en/function.date.php;
	@Test   public void Rfc5322()					{fxt.Test_parse_tmpl_str_test("{{#time:r|}}"				, "{{test}}"			, "Mon, 02 Jan 2012 08:04:05 +0000");}
	@Test   public void Lang() {
		Xol_lang fr_lang = fxt.App().Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_("fr"));
		Xol_msg_itm msg_itm = fr_lang.Msg_mgr().Itm_by_key_or_new(Bry_.new_ascii_("January"));
		msg_itm.Atrs_set(Bry_.new_ascii_("Janvier"), false, false);
		fxt.Test_parse_tmpl_str_test("{{#time:F|2012-01|fr}}"		, "{{test}}"			, "Janvier");
//			fxt.Test_parse_tmpl_str_test("{{#time:F|2012-01|fr_bad}}"	, "{{test}}"			, "January");	// default to english	// commented out; fails when running all at once
	}
	@Test   public void Hour_with_dash()				{fxt.Test_parse_tmpl_str_test("{{#time:c|January 2, 2001-06}}"					, "{{test}}"			, "2001-01-02T06:00:00-05:00");}	// PURPOSE.fix: w:Vim_(text_editor) generates this during {{time ago|November 2, 1991-06-19|min_magnitude=days}}; DATE:2013-06-19
	@Test   public void Multiple_dates_gt_12()		{fxt.Test_parse_tmpl_str_test("{{#time:c|January 2, 2001-06-19}}"				, "{{test}}"			, "2001-01-02T06:00:00-05:00");}	// PURPOSE.fix: w:Vim_(text_editor)
	@Test   public void Multiple_dates_lt_12()		{fxt.Test_parse_tmpl_str_test("{{#time:c|January 2, 2001-06-11}}"				, "{{test}}"			, "2001-01-02T06:00:00-05:00");}	// PURPOSE.fix: w:Vim_(text_editor)
	@Test   public void Raw_H()						{fxt.Test_parse_tmpl_str_test("{{#time:xnH}}"									, "{{test}}"			, "08");}	// PURPOSE: ignore "xn" for now; chk 0-padded number is generated; DATE:2013-12-31
	@Test   public void Raw_h()						{fxt.Test_parse_tmpl_str_test("{{#time:xnh}}"									, "{{test}}"			, "08");}	// PURPOSE: ignore "xn" for now; chk 0-padded number is generated; DATE:2013-12-31
}
