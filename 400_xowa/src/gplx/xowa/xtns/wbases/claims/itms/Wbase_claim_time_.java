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
package gplx.xowa.xtns.wbases.claims.itms;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.times.*;
public class Wbase_claim_time_ {
	public static final byte
	  Tid__time									= 0
	, Tid__timezone								= 1
	, Tid__before								= 2
	, Tid__after								= 3
	, Tid__precision							= 4
	, Tid__calendarmodel						= 5
	;
	public static final Wbase_enum_hash Reg = new Wbase_enum_hash("claim.val.time", 6);
	public static final Wbase_enum_itm
	  Itm__time						= Reg.Add(Tid__time				, "time")
	, Itm__timezone					= Reg.Add(Tid__timezone			, "timezone")
	, Itm__before					= Reg.Add(Tid__before			, "before")
	, Itm__after					= Reg.Add(Tid__after			, "after")
	, Itm__precision				= Reg.Add(Tid__precision		, "precision")
	, Itm__calendarmodel			= Reg.Add(Tid__calendarmodel	, "calendarmodel")
	;
	public static final Wbase_data_itm
	  Dflt__precision		= Wbase_data_itm.New_int(11)
	, Dflt__before			= Wbase_data_itm.New_int(0)
	, Dflt__after			= Wbase_data_itm.New_int(0)
	, Dflt__timezone		= Wbase_data_itm.New_int(0)
	, Dflt__calendarmodel	= Wbase_data_itm.New_str("http://www.wikidata.org/entity/Q1985727")
	;

	private static final byte[] Bry_year_prefix = BryUtl.NewA7("+0000000");
	public static byte[] To_bry(BryWtr bfr, String date) {return To_bry(bfr, GfoDateUtl.ParseFmt(date, "yyyy-MM-dd HH:mm:ss"));}
	public static byte[] To_bry(BryWtr bfr, GfoDate date) {// +0000000yyyy-MM-ddTHH:mm:ssZ
		bfr
		.Add(Bry_year_prefix)
		.AddIntFixed(date.Year(), 4)
		.AddByte(AsciiByte.Dash)
		.AddIntFixed(date.Month(), 2)
		.AddByte(AsciiByte.Dash)
		.AddIntFixed(date.Day(), 2)
		.AddByte(AsciiByte.Ltr_T)
		.AddIntFixed(date.Hour(), 2)
		.AddByte(AsciiByte.Colon)
		.AddIntFixed(date.Minute(), 2)
		.AddByte(AsciiByte.Colon)
		.AddIntFixed(date.Second(), 2)
		.AddByte(AsciiByte.Ltr_Z)
		;
		return bfr.ToBryAndClear();
	}
}
