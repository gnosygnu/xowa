/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.htmls;

import gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_;
import gplx.xowa.htmls.hrefs.Xoh_href_;

public class Xoh_wtr_ctx {
	Xoh_wtr_ctx(int mode, int hzip_tid, byte[] anch__href__bgn, byte[] anch__href__end) {
		this.mode = mode;
		this.hzip_tid = hzip_tid;
		this.anch__href__bgn = anch__href__bgn;
		this.anch__href__end = anch__href__end;
	}
	public int Mode()                             {return mode;}              private final int mode;
	public byte[] Anch__href__bgn()               {return anch__href__bgn;}   private final byte[] anch__href__bgn;
	public byte[] Anch__href__end()               {return anch__href__end;}   private final byte[] anch__href__end;
	public int Hzip_tid()                         {return hzip_tid;}          private final int hzip_tid;
	public boolean Mode_is_hdump()                {return mode == TID_HDUMP || mode == TID_EMBEDDABLE || mode == TID_HTTP_SERVER;}
	public boolean Mode_is_file_dump()            {return mode == TID_HDUMP || mode == TID_EMBEDDABLE || mode == TID_HTTP_SERVER;}
	public boolean Mode_is_hdump_wo_db()          {return mode == TID_EMBEDDABLE || mode == TID_HTTP_SERVER;}
	public boolean Mode_is_alt()                  {return mode == TID_ALT;}
	public boolean Mode_is_display_title()        {return mode == TID_DISPLAY_TITLE;}
	public boolean Mode_is_popup()                {return mode == TID_POPUP;}

	private static final int
	  TID_BASIC = 0
	, TID_ALT = 1
	, TID_DISPLAY_TITLE = 2
	, TID_POPUP = 3
	, TID_HDUMP = 4
	, TID_FILE_DUMP = 5
	, TID_EMBEDDABLE = 6
	, TID_HTTP_SERVER = 7
	;

	public static final Xoh_wtr_ctx
	  Basic          = new Xoh_wtr_ctx(TID_BASIC, Xoh_hzip_dict_.Hdb__htxt, Xoh_href_.Bry__wiki, null)
	, Hdump          = new Xoh_wtr_ctx(TID_HDUMP, Xoh_hzip_dict_.Hdb__hzip, Xoh_href_.Bry__wiki, null)
	, Alt            = new Xoh_wtr_ctx(TID_ALT, Xoh_hzip_dict_.Hdb__htxt, Xoh_href_.Bry__wiki, null)
	, Display_title  = new Xoh_wtr_ctx(TID_DISPLAY_TITLE, Xoh_hzip_dict_.Hdb__htxt, Xoh_href_.Bry__wiki, null)
	, Popup          = new Xoh_wtr_ctx(TID_POPUP, Xoh_hzip_dict_.Hdb__htxt, Xoh_href_.Bry__wiki, null)
	, Embeddable     = new Xoh_wtr_ctx(TID_EMBEDDABLE, Xoh_hzip_dict_.Hdb__htxt, Xoh_href_.Bry__wiki, null)
	, HttpServer     = new Xoh_wtr_ctx(TID_HTTP_SERVER, Xoh_hzip_dict_.Hdb__htxt, Xoh_href_.Bry__wiki, null)
	;

	public static Xoh_wtr_ctx File_dump(byte[] anch__href__bgn, byte[] anch__href__end) {
		return new Xoh_wtr_ctx(TID_FILE_DUMP, Xoh_hzip_dict_.Hdb__htxt, anch__href__bgn, anch__href__end);
	}
	public static Xoh_wtr_ctx Hdump_by_hzip_tid(int hzip_tid) {
		return new Xoh_wtr_ctx(TID_HDUMP, hzip_tid, Xoh_href_.Bry__wiki, null);
	}
}
