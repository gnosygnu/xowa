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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
public class Xoh_cfg_file {
	public Xoh_cfg_file(Url_encoder url_encoder, Io_url xowa_dir) {
		Io_url mw_file_dir = xowa_dir.GenSubDir_nest("file", "mw.file");
		img_media_play_btn = url_encoder.Encode_http(mw_file_dir.GenSubFil("play.png"));
		img_media_info_btn = url_encoder.Encode_http(mw_file_dir.GenSubFil("info.png"));
		img_thumb_magnify = url_encoder.Encode_http(mw_file_dir.GenSubFil("magnify-clip.png"));
	}
	public byte[] Img_media_play_btn() {return img_media_play_btn;} private final byte[] img_media_play_btn;
	public byte[] Img_media_info_btn() {return img_media_info_btn;} private final byte[] img_media_info_btn;
	public byte[] Img_thumb_magnify() {return img_thumb_magnify;} private final byte[] img_thumb_magnify;
}
