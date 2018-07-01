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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Pfunc_gender_tst {	// REF.MW:https://translatewiki.net/wiki/Gender
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void No_args()				{fxt.Test_parse_template("{{gender:}}"					, "");}
	@Test  public void Username_m()				{fxt.Test_parse_template("{{gender:xowa_male|m|f|?}}"	, "m");}	
	@Test  public void Username_f()				{fxt.Test_parse_template("{{gender:xowa_female|m|f|?}}"	, "f");}
	@Test  public void Username_unknown()		{fxt.Test_parse_template("{{gender:wmf_user|m|f|?}}"	, "?");}	// should look up gender of "wmf_user", but since not avaliable, default to unknown
	@Test  public void Username_unknown_m()		{fxt.Test_parse_template("{{gender:wmf_user|m}}"		, "m");}	// if only m is provided, use it
	@Test  public void Username_unknown_f()		{fxt.Test_parse_template("{{gender:wmf_user|m|f}}"		, "m");}	// if only m is provided; same as above, but make sure "f" doesn't change anything
	@Test  public void Default()				{fxt.Test_parse_template("{{gender:.|m|f|?}}"			, "?");}	// "." means use wiki's default gender; default to unknown
	@Test  public void Unknown()				{fxt.Test_parse_template("{{gender:|m|f|?}}"			, "?");}	// "" always is unknown
}
