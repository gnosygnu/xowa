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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
public class Pft_func_time__basic__tst {
	@Before	public void init()					{fxt.Reset(); Datetime_now.Manual_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));} private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term()				{Datetime_now.Manual_n_();}
	@Test   public void Utc_date()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2012-01-02 03:04:05}}"				, "2012-01-02");}
	@Test   public void Utc_time()				{fxt.Test_parse_tmpl_str("{{#time:h:i:s A|2012-01-02 03:04:05}}"			, "03:04:05 AM");}
	@Test   public void Utc_dayOfYear()			{fxt.Test_parse_tmpl_str("{{#time:z|2012-01-01 03:04:05}}"					, "0");}
	@Test   public void Utc_escape_basic()		{fxt.Test_parse_tmpl_str("{{#time:\\Y Y|2012-01-02 03:04:05}}"				, "Y 2012");}
	@Test   public void Utc_escape_double()		{fxt.Test_parse_tmpl_str("{{#time:\\\\ Y|2012-01-02 03:04:05}}"				, "\\ 2012");}
	@Test   public void Utc_escape_eos()		{fxt.Test_parse_tmpl_str("{{#time:b\\|2012-01-02 03:04:05}}"				, "b\\");}
	@Test   public void Utc_escape_newLine()	{fxt.Test_parse_tmpl_str("{{#time:b\\\nb|2012-01-02 03:04:05}}"				, "b\nb");}
	@Test   public void Utc_quote_basic()		{fxt.Test_parse_tmpl_str("{{#time:b \"Y m d\" b|2012-01-02 03:04:05}}"		, "b Y m d b");}
	@Test   public void Utc_quote_double()		{fxt.Test_parse_tmpl_str("{{#time:b \"\" b|2012-01-02 03:04:05}}"			, "b \" b");}
	@Test   public void Utc_quote_eos()			{fxt.Test_parse_tmpl_str("{{#time:b \"|2012-01-02 03:04:05}}"				, "b \"");}
	@Test   public void Utc_ws()				{fxt.Test_parse_tmpl_str("{{#time: Y-m-d |2012-01-02 03:04:05}}"			, "2012-01-02");}
	@Test   public void Lcl_date()				{fxt.Test_parse_tmpl_str("{{#timel:Y-m-d|2012-01-02 03:04:05}}"				, "2012-01-02");}
	@Test   public void Utc_dow_abrv()			{fxt.Test_parse_tmpl_str("{{#time:D|20120301}}"								, "Thu");}
	@Test   public void Utc_ymd()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|20120102}}"							, "2012-01-02");}
	@Test   public void Utc_ym()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|201201}}"							, "2012-01-02");}	// PURPOSE: default to today's date
	@Test   public void Utc_md()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2-13}}"								, "2012-02-13");}	// PURPOSE.fix: m-d failed
	@Test   public void Slashes()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2/13/12}}"							, "2012-02-13");}	// PURPOSE: assert slashes work
	@Test   public void Slashes_yyyy()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2/13/2012}}"						, "2012-02-13");}	// PURPOSE: assert slashes work with yyyy; DATE:2017-04-25
	@Test   public void Utc_day()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|March 27}}"							, "2012-03-27");}
	@Test   public void Parse_day()				{fxt.Test_parse_tmpl_str("{{#time:m d|March 27}}"							, "03 27");}
	@Test   public void Month_name()			{fxt.Test_parse_tmpl_str("{{#time:M|May 1 2012}}"							, "May");}
	@Test   public void Time_before_date__dmy()	{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i|04:50, 17 December 2010}}"		, "2010-12-17 04:50");}	// PAGE:en.w:Talk:Battle of Fort Washington
	@Test   public void Time_before_date__mdy()	{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i|04:50, December 11, 2010}}"		, "2010-12-11 04:50");}	// PAGE:en.w:Wikipedia:WikiProject_Maine/members; DATE:2014-06-25
	@Test   public void Error()					{fxt.Test_parse_tmpl_str("{{#time:M|2}}"									, "<strong class=\"error\">Invalid year: 2</strong>");}
	@Test   public void Error2()				{fxt.Test_parse_tmpl_str("{{#time:Y|July 28 - August 1, 1975}}"				, "<strong class=\"error\">Invalid time</strong>");}
	@Test   public void Error3()				{fxt.Test_parse_tmpl_str("{{#time:Y|106BC-43BC}}"							, "<strong class=\"error\">Invalid time</strong>");}
	@Test   public void Timestamp()				{fxt.Test_parse_tmpl_str("{{#time:F j, Y|20060827072854}}"					, "August 27, 2006");}	// PAGE:en.w:Great Fire of London
	@Test   public void Unixtime_read()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d h:i:s A|@0}}"						, "1970-01-01 12:00:00 AM");}	// EX:w:Wikipedia:WikiProject_Articles_for_creation/BLD_Preload
	@Test   public void Unixtime_read_neg()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d h:i:s A|@-3600}}"					, "1969-12-31 11:00:00 PM");}	// EX:w:Wikipedia:WikiProject_Articles_for_creation/October_-_November_2012_Backlog_Elimination_Drive; DATE:2014-05-10
	@Test   public void Unixtime_8_digit()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d h:i:s A|@20120304}}"				, "1970-08-21 08:58:24 PM");}	// PURPOSE: make sure yyyy-MM-dd is gobbled by "@" and not evaluated again as date; EX:w:Wikipedia:WikiProject_Articles_for_creation/October_-_November_2012_Backlog_Elimination_Drive; DATE:2014-05-10
	@Test   public void Unixtime_write()		{fxt.Test_parse_tmpl_str("{{#time:U|2012-08-02}}"							, "1343865600");}	// PAGE:en.w:Opa (programming language)
	@Test   public void Year_4_digit()			{fxt.Test_parse_tmpl_str("{{#time:Y|June 20, 451}}"							, "0451");}	// PAGE:en.w:Battle of the Catalaunian Plains
	@Test   public void Year_month()			{fxt.Test_parse_tmpl_str("{{#time:F Y|November 2012}}"						, "November 2012");}	// PAGE:en.w:Project:Current events
	@Test   public void Day_addition_with_dash(){fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2011-11-13 +1 day}}"				, "2011-11-14");}	// PURPOSE: +1 day was becoming -1 day b/c it was picking up - at -13; PAGE:en.w:Template:POTD/2012-10-09	
	@Test   public void Hour_zero()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i|August 18 2006 00:14}}"			, "2006-08-18 00:14");}	// PURPOSE: fix; invalid hour; PAGE:en.w:Talk:Martin Luther
	@Test   public void Iso()					{fxt.Test_parse_tmpl_str("{{#time:c|2012-01-02 03:04:05}}"					, "2012-01-02T03:04:05-05:00");}
	@Test   public void Ymdh()					{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2012-01-02-99}}"					, "2012-01-06");}	// PURPOSE: "99" is treated as 99th hour; EX:w:LimeWire; DATE:2014-03-24
	@Test   public void Ymdh_noop()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2012-01-02-100}}"					, "2012-01-02");}	// PURPOSE: "100" is ignored
	@Test   public void Month_is_0()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2012-00-01}}"						, "2011-12-01");}	// PURPOSE: "0" for month is treated as -1; EX:w:Mariyinsky_Palace; DATE:2014-03-25
	@Test   public void Day_is_0()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|2012-12-00}}"						, "2012-11-30");}	// PURPOSE: "0" for day is treated as -1; EX:w:Mariyinsky_Palace; DATE:2014-03-25
	@Test   public void Day_suffix_y()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|11th Dec 2013}}"					, "2013-12-11");}	// PURPOSE: ignore suffix days; EX:11th; DATE:2014-03-25
	@Test   public void Day_suffix_n()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|32nd Dec 2013}}"					, "<strong class=\"error\">Invalid day: 32</strong>");}
	@Test   public void Day_rel_today()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|today}}"							, "2012-01-02");}
	@Test   public void Day_rel_tomorrow()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|tomorrow}}"							, "2012-01-03");}
	@Test   public void Day_rel_yesterday()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|yesterday}}"						, "2012-01-01");}
	@Test   public void Unit_rel_year_next()	{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|next year}}"						, "2013-01-02");}	// DATE:2014-05-02
	@Test   public void Unit_rel_year_last()	{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|last year}}"						, "2011-01-02");}
	@Test   public void Unit_rel_year_previous(){fxt.Test_parse_tmpl_str("{{#time:Y-m-d|previous year}}"					, "2011-01-02");}
	@Test   public void Unit_rel_year_this()	{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|this year}}"						, "2012-01-02");}
	@Test   public void Time_rel_now()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d h:i:s A|now}}"						, "2012-01-02 03:05:05 AM");}	// NOTE: minute is 5, not 4, b/c each call to Datetime_now.Get() automatically increments by 1 minute; DATE:2014-04-13
	@Test   public void Empty_is_today()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|}}"									, "2012-01-02");}	// tested on MW
	@Test   public void Day_name_today()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|Monday}}"							, "2012-01-02");}	// 2012-01-02 is Monday, so return Monday; DATE:2014-05-02
	@Test   public void Day_name_future_1()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|Saturday}}"							, "2012-01-07");}	// return next Sunday; DATE:2014-05-02
	@Test   public void Day_name_future_2()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d|Sunday}}"							, "2012-01-08");}	// return next Saturday; DATE:2014-05-02
	@Test   public void Day_name_dow()			{fxt.Test_parse_tmpl_str("{{#time:w|Monday}}"								, "1");}
	@Test   public void Timezone_offset()		{
		DateAdp.Timezone_offset_test = -18000;
		fxt.Test_parse_tmpl_str("{{#time:Z|}}"											, "-18000");
		DateAdp.Timezone_offset_test = Int_.Min_value;
	}			// Z=timezone offset in seconds; http://php.net/manual/en/function.date.php;
	@Test   public void Timezone_plus()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i:s|2012-01-02 03:04:05+06:30}}"	, "2012-01-02 09:34:05");}	// PURPOSE: handle timezone plus ; EX: +01:30; DATE:2014-08-26
	@Test   public void Timezone_minus()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i:s|2012-01-02 09:34:05-06:30}}"	, "2012-01-02 03:04:05");}	// PURPOSE: handle timezone minus; EX: -01:30; DATE:2014-08-26 
	@Test   public void Timezone_wrap()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i:s|2012-01-31 22:30:05+01:30}}"	, "2012-02-01 00:00:05");}	// PURPOSE: handle timezone wrap ; DATE:2014-08-26
	@Test   public void Rfc5322()				{fxt.Test_parse_tmpl_str("{{#time:r|}}"							, "Mon, 02 Jan 2012 08:04:05 +0000");}
	@Test   public void Lang() {
		Xol_lang_itm fr_lang = fxt.App().Lang_mgr().Get_by_or_new(Bry_.new_a7("fr"));
		Xol_msg_itm msg_itm = fr_lang.Msg_mgr().Itm_by_key_or_new(Bry_.new_a7("January"));
		msg_itm.Atrs_set(Bry_.new_a7("Janvier"), false, false);
		fxt.Test_parse_tmpl_str("{{#time:F|2012-01|fr}}"					, "Janvier");
//			fxt.Test_parse_tmpl_str("{{#time:F|2012-01|fr_bad}}"				, "January");	// default to english	// commented out; fails when running all at once
	}
	@Test   public void Hour_with_dash()		{fxt.Test_parse_tmpl_str("{{#time:c|January 2, 2001-06}}"					, "2001-01-02T06:00:00-05:00");}	// PURPOSE.fix: w:Vim_(text_editor) generates this during {{time ago|November 2, 1991-06-19|min_magnitude=days}}; DATE:2013-06-19
	@Test   public void Multiple_dates_gt_12()	{fxt.Test_parse_tmpl_str("{{#time:c|January 2, 2001-06-19}}"				, "2001-01-02T06:00:00-05:00");}	// PURPOSE.fix: w:Vim_(text_editor)
	@Test   public void Multiple_dates_lt_12()	{fxt.Test_parse_tmpl_str("{{#time:c|January 2, 2001-06-11}}"				, "2001-01-02T06:00:00-05:00");}	// PURPOSE.fix: w:Vim_(text_editor)
	@Test   public void Raw_H()					{fxt.Test_parse_tmpl_str("{{#time:xnH}}"									, "08");}	// PURPOSE: ignore "xn" for now; chk 0-padded number is generated; DATE:2013-12-31
	@Test   public void Raw_h()					{fxt.Test_parse_tmpl_str("{{#time:xnh}}"									, "08");}	// PURPOSE: ignore "xn" for now; chk 0-padded number is generated; DATE:2013-12-31
	@Test   public void Iso8601_T()				{fxt.Test_parse_tmpl_str("{{#time:Y-m-d h:i:s A|T1:23}}"					, "2012-01-02 01:23:00 AM");}	// handle "T" flag; PAGE:pl.w:StarCraft_II:_Wings_of_Liberty
	@Test   public void Iso8601_T_ws()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d h:i:s A|T 1:23}}"					, "2012-01-02 01:23:00 AM");}	// handle "T" flag and ws
	@Test   public void Iso8601_T_fail()		{fxt.Test_parse_tmpl_str("{{#time:Y-m-d h:i:s A|T2012-01-02}}"				, "<strong class=\"error\">Invalid hour: T</strong>");}	// handle "T" flag and ws
}
