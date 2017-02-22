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
package gplx.xowa.xtns.wbases.claims.itms.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
import org.junit.*; import gplx.core.brys.fmtrs.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.parsers.*; import gplx.xowa.xtns.wbases.hwtrs.*;
public class Wbase_date_tst {
	@Before public void init() {fxt.Clear();} private Wbase_date_fxt fxt = new Wbase_date_fxt();
	@Test   public void Parse() {
		fxt.Test_parse("+00000002001-02-03T04:05:06Z",          2001, 2, 3, 4, 5, 6);
		fxt.Test_parse("-98765432109-02-03T04:05:06Z", -98765432109L, 2, 3, 4, 5, 6);
	}
	@Test   public void Julian() {
		fxt.Test_julian(Int_.Ary(1600, 1, 2), Int_.Ary(1600, 1, 18));
	}
	@Test   public void Xto_str() {
		String date = "+00000002001-02-03T04:05:06Z";
		fxt.Test_xto_str(date, Wbase_date.Fmt_ym		, "Feb 2001");
		fxt.Test_xto_str(date, Wbase_date.Fmt_ymd		, "3 Feb 2001");
		fxt.Test_xto_str(date, Wbase_date.Fmt_ymdh		, "4:00 3 Feb 2001");
		fxt.Test_xto_str(date, Wbase_date.Fmt_ymdhn		, "4:05 3 Feb 2001");
		fxt.Test_xto_str(date, Wbase_date.Fmt_ymdhns	, "4:05:06 3 Feb 2001");
	}
	@Test   public void Xto_str_year() {
		fxt.Test_xto_str("+00000001970-01-01T00:00:00Z",  9, "1970");
		fxt.Test_xto_str("-00000001234-01-01T00:00:00Z",  9, "1234 BC");
		fxt.Test_xto_str("+00000001987-01-01T00:00:00Z",  8, "1980s");
		fxt.Test_xto_str("+00000001987-01-01T00:00:00Z",  7, "19. century");
		fxt.Test_xto_str("+00000001987-01-01T00:00:00Z",  6, "1. millenium");
		fxt.Test_xto_str("+00000012345-01-01T00:00:00Z",  5, "10,000 years");
		fxt.Test_xto_str("+00000123456-01-01T00:00:00Z",  4, "in 100,000 years");
	}
	@Test   public void Xto_str_julian() {
		fxt.Init_calendar_is_julian_(Bool_.Y).Test_xto_str("+00000001600-01-02T00:00:00Z", Wbase_date.Fmt_ymd, "2 Jan 1600");
	}
	@Test   public void Xto_str_before_after() {
		String date = "+00000002001-02-03T04:05:06Z";
		fxt.Clear().Init_before_(1).Test_xto_str(date, Wbase_date.Fmt_ymd, "3 Feb 2001 (-1)");
		fxt.Clear().Init_after_ (1).Test_xto_str(date, Wbase_date.Fmt_ymd, "3 Feb 2001 (+1)");
		fxt.Clear().Init_before_(1).Init_after_(1).Test_xto_str(date, Wbase_date.Fmt_ymd, "3 Feb 2001 (±1)");
		fxt.Clear().Init_before_(1).Init_after_(2).Test_xto_str(date, Wbase_date.Fmt_ymd, "3 Feb 2001 (-1,&#32;+2)");
	}
}
class Wbase_date_fxt {
	private Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(16);
	private Wdata_hwtr_msgs msgs;
	private final    Bry_fmtr tmp_time_fmtr = Bry_fmtr.new_(); private final    Bry_bfr tmp_time_bfr = Bry_bfr_.New_w_size(32);		
	public Wbase_date_fxt Clear() {
		init_before = init_after = 0;
		init_calendar_is_julian = false;
		return this;
	}
	public boolean Init_calendar_is_julian() {return init_calendar_is_julian;} public Wbase_date_fxt Init_calendar_is_julian_(boolean v) {init_calendar_is_julian = v; return this;} private boolean init_calendar_is_julian;
	public int Init_before() {return init_before;} public Wbase_date_fxt Init_before_(int v) {init_before = v; return this;} private int init_before;
	public int Init_after() {return init_after;} public Wbase_date_fxt Init_after_(int v) {init_after = v; return this;} private int init_after;
	public void Test_parse(String raw, long expd_y, int expd_m, int expd_d, int expd_h, int expd_n, int expd_s) {
		Wbase_date actl_date = Wbase_date_.Parse(Bry_.new_a7(raw), Wbase_date.Fmt_ymdhns, init_before, init_after, init_calendar_is_julian);
		Tfds.Eq(expd_y, actl_date.Year());
		Tfds.Eq(expd_m, actl_date.Month());
		Tfds.Eq(expd_d, actl_date.Day());
		Tfds.Eq(expd_h, actl_date.Hour());
		Tfds.Eq(expd_n, actl_date.Minute());
		Tfds.Eq(expd_s, actl_date.Second());
	}
	public void Test_julian(int[] orig_ary, int[] expd) {
		Wbase_date orig = new Wbase_date(orig_ary[0], orig_ary[1], orig_ary[2], 0, 0, 0, 0, 0, 0, init_calendar_is_julian);
		Wbase_date actl = Wbase_date_.To_julian(orig);
		Tfds.Eq(expd[0], (int)actl.Year(), "y");
		Tfds.Eq(expd[1], actl.Month(), "m");
		Tfds.Eq(expd[2], actl.Day(), "d");
	}
	public void Test_xto_str(String raw, int precision, String expd) {
		if (msgs == null) msgs = Wdata_hwtr_msgs.new_en_();
		Wbase_date date = Wbase_date_.Parse(Bry_.new_a7(raw), precision, init_before, init_after, init_calendar_is_julian);
		Wbase_date_.To_bfr(tmp_bfr, tmp_time_fmtr, tmp_time_bfr, msgs, date);
		Tfds.Eq(expd, tmp_bfr.To_str_and_clear());
	}
}
