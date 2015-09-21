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
package gplx.langs.htmls.encoders; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Url_encoder_mgr {
	public Url_encoder	File()			{return file;}			private final Url_encoder file			= Url_encoder.new_file_();
	public Url_encoder	Http_url()		{return http_url;}		private final Url_encoder http_url		= Url_encoder.new_http_url_();
	public Url_encoder	Http_url_ttl()	{return http_url_ttl;}	private final Url_encoder http_url_ttl	= Url_encoder.new_http_url_ttl_();
	public Url_encoder	Id()			{return html_id;}		private final Url_encoder html_id		= Url_encoder.new_html_id_();
	public Url_encoder	Href()			{return href;}			private final Url_encoder href			= Url_encoder.new_html_href_mw_();
	public Url_encoder	Href_quotes()	{return href_quotes;}	private final Url_encoder href_quotes	= Url_encoder.new_html_href_quotes_();
	public Url_encoder	Gfs()			{return gfs;}			private final Url_encoder gfs			= Url_encoder.new_gfs_();
	public Url_encoder	Fsys()			{return fsys;}			private final Url_encoder fsys			= Url_encoder.new_fsys_lnx_();
	public Url_encoder	Fsys_safe()		{return fsys_safe;}		private final Url_encoder fsys_safe		= Url_encoder.new_fsys_wnt_();
	public Url_encoder	Xourl()			{return xourl;}			private final Url_encoder xourl			= Url_encoder.new_html_href_mw_().Itms_raw_same_many(Byte_ascii.Underline);
}
