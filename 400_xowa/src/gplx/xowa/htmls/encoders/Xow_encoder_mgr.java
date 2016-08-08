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
package gplx.xowa.htmls.encoders; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.htmls.encoders.*; 
public class Xow_encoder_mgr {
	public Gfo_url_encoder Id()				{return id;}			private final    Gfo_url_encoder id				= Gfo_url_encoder_.New__html_id().Make();
	public Gfo_url_encoder Href()			{return href;}			private final    Gfo_url_encoder href			= Gfo_url_encoder_.New__html_href_mw(Bool_.Y).Make();
	public Gfo_url_encoder Href_wo_anch()	{return href_wo_anch;}	private final    Gfo_url_encoder href_wo_anch	= Gfo_url_encoder_.New__html_href_mw(Bool_.N).Make();
//		, Href_quotes		= Gfo_url_encoder_.New__html_href_quotes().Make()
//		, Href_quotes_v2	= Gfo_url_encoder_.New__html_href_quotes_v2().Make()
//		, Href_qarg			= Gfo_url_encoder_.New__html_href_qarg().Make()
//		, Xourl				= Gfo_url_encoder_.New__html_href_mw(Bool_.Y).Init__same__many(Byte_ascii.Underline).Make()
//		, Http_url			= Gfo_url_encoder_.New__http_url().Make()
//		, Http_url_ttl		= Gfo_url_encoder_.New__http_url_ttl().Make()
//		, Fsys_lnx			= Gfo_url_encoder_.New__fsys_lnx().Make()
//		, Fsys_wnt			= Gfo_url_encoder_.New__fsys_wnt().Make()
//		, Gfs				= Gfo_url_encoder_.New__gfs().Make()
}
