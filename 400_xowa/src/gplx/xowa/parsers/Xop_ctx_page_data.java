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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
public class Xop_ctx_page_data {
	public boolean Hdr_toc()                   {return toc;}                     public void Hdr_toc_y_()                  {this.toc = true;} private boolean toc;
	public boolean Hdr_forcetoc()              {return forcetoc;}                public void Hdr_forcetoc_y_()             {this.forcetoc = true;} private boolean forcetoc;
	public boolean Hdr_notoc()                 {return notoc;}                   public void Hdr_notoc_y_()                {this.notoc = true;} private boolean notoc;
	public boolean Lang_convert_content()      {return lang_convert_content;}    public void Lang_convert_content_(boolean v) {this.lang_convert_content = v;} private boolean lang_convert_content = true;
	public boolean Lang_convert_title()        {return lang_convert_title;}      public void Lang_convert_title_(boolean v)   {this.lang_convert_title = v;} private boolean lang_convert_title = true;
	public void Clear() {
		toc = forcetoc = notoc = false;
		lang_convert_content = lang_convert_title = true;
	}
	public void Copy_to(Xoae_page page) {
		gplx.xowa.wikis.pages.wtxts.Xopg_toc_mgr hdr_mgr = page.Wtxt().Toc();
		hdr_mgr.Flag__toc_(toc);
		hdr_mgr.Flag__forcetoc_(forcetoc);
		hdr_mgr.Flag__notoc_(notoc);
		page.Html_data().Lang_convert_content_(lang_convert_content);
		page.Html_data().Lang_convert_title_(lang_convert_title);
	}
}
