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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.langs.htmls.encoders.*;
public class Xoh_cfg_file {
	public Xoh_cfg_file(Gfo_url_encoder url_encoder, Io_url xowa_dir) {
		Io_url mw_file_dir = xowa_dir.GenSubDir_nest("file", "mediawiki.file");
		img_media_play_btn = url_encoder.Encode_to_file_protocol(mw_file_dir.GenSubFil("play.png"));
		img_media_info_btn = url_encoder.Encode_to_file_protocol(mw_file_dir.GenSubFil("info.png"));
		img_thumb_magnify = url_encoder.Encode_to_file_protocol(mw_file_dir.GenSubFil("magnify-clip.png"));
	}
	public byte[] Img_media_play_btn() {return img_media_play_btn;} private final byte[] img_media_play_btn;
	public byte[] Img_media_info_btn() {return img_media_info_btn;} private final byte[] img_media_info_btn;
	public byte[] Img_thumb_magnify() {return img_thumb_magnify;} private final byte[] img_thumb_magnify;
}
