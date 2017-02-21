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
package gplx.xowa.wikis.pages.redirects; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_redirect_itm {
	public Xopg_redirect_itm(Xoa_url url, Xoa_ttl ttl, byte[] wikitext) {
		this.url = url;
		this.ttl = ttl;
		this.wikitext = wikitext;
	}
	public Xoa_url		Url()				{return url;} private final    Xoa_url url;					// EX: "en.wikipedia.org/wiki/A"
	public Xoa_ttl		Ttl()				{return ttl;} private final    Xoa_ttl ttl;					// EX: "A"
	public byte[]		Wikitext()			{return wikitext;} private final    byte[] wikitext;		// EX: "#REDIRECT [[A]]"
	public boolean			By_wikitext()		{return wikitext != null;}									// true if by "#REDIRECT [[A]]"; false if by Special:Random, Special:Search, etc.; 
}
