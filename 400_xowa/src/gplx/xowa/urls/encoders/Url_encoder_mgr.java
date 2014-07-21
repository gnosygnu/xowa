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
package gplx.xowa.urls.encoders; import gplx.*; import gplx.xowa.*; import gplx.xowa.urls.*;
public class Url_encoder_mgr {
	public Url_encoder	Id()			{return id;}			private Url_encoder id			= Url_encoder.new_html_id_();
	public Url_encoder	Url()			{return url;}			private Url_encoder url			= Url_encoder.new_http_url_();
	public Url_encoder	Url_ttl()		{return url_ttl;}		private Url_encoder url_ttl		= Url_encoder.new_http_url_ttl_();
	public Url_encoder	Href()			{return href;}		private Url_encoder href			= Url_encoder.new_html_href_mw_();
	public Url_encoder	Href_quotes()	{return href_quotes;} private Url_encoder href_quotes	= Url_encoder.new_html_href_quotes_();
	public Url_encoder	Comma()			{return comma;}		private Url_encoder comma			= Url_encoder.url_comma();
	public Url_encoder	Gfs()			{return gfs;}			private Url_encoder gfs			= Url_encoder.new_gfs_();
	public Url_encoder	Fsys()			{return fsys;}		private Url_encoder fsys			= Url_encoder.new_fsys_lnx_();
	public Url_encoder	Fsys_safe()		{return fsys_safe;}	private Url_encoder fsys_safe		= Url_encoder.new_fsys_wnt_();
}
