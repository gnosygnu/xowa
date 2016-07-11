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
