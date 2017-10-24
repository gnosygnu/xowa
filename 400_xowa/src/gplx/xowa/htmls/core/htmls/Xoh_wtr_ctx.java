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
package gplx.xowa.htmls.core.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
public class Xoh_wtr_ctx {
	Xoh_wtr_ctx(int mode, byte[] anch__href__bgn, byte[] anch__href__end) {
		this.mode = mode; this.anch__href__bgn = anch__href__bgn; this.anch__href__end = anch__href__end;
	}
	public int Mode() {return mode;} private final    int mode;
	public boolean Mode_is_alt()			{return mode == Mode_alt;}
	public boolean Mode_is_display_title() {return mode == Mode_display_title;}
	public boolean Mode_is_popup()			{return mode == Mode_popup;}
	public boolean Mode_is_hdump()			{return mode == Mode_hdump;}
	public byte[] Anch__href__bgn()		{return anch__href__bgn;} private final    byte[] anch__href__bgn;
	public byte[] Anch__href__end()		{return anch__href__end;} private final    byte[] anch__href__end;

	public static final int Mode_basic = 0, Mode_alt = 1, Mode_display_title = 2, Mode_popup = 3, Mode_hdump = 4, Mode_file_dump = 5;

	public static final    Xoh_wtr_ctx
	  Basic				= new Xoh_wtr_ctx(Mode_basic			, gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, null)
	, Alt				= new Xoh_wtr_ctx(Mode_alt				, gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, null)
	, Display_title		= new Xoh_wtr_ctx(Mode_display_title	, gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, null)
	, Popup				= new Xoh_wtr_ctx(Mode_popup			, gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, null)
	, Hdump				= new Xoh_wtr_ctx(Mode_hdump			, gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, null)
	;
	public static Xoh_wtr_ctx File_dump(byte[] anch__href__bgn, byte[] anch__href__end) {
		return new Xoh_wtr_ctx(Mode_file_dump, anch__href__bgn, anch__href__end);
	}
}
