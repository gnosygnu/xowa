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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_params_mto {
	public boolean desc_link;
	public byte[] alt = null;
	public byte[] title = null;
	public byte[] img_cls = null;
	public byte[] file_link = null;
	public byte[] valign = null;
	public byte[] desc_query = null;
	public byte[] override_width = null;
	public byte[] override_height = null;
	public byte[] no_dimensions = null;
	public byte[] custom_url_link = null;
	public byte[] custom_title_link = null;
	public byte[] custom_target_link = null;
	public byte[] parser_extlink_rel = null;
	public byte[] parser_extlink_target = null;
	public Xomw_params_mto Clear() {
		desc_link = false;
		alt = title = file_link = valign
			= desc_query = override_width = override_height = no_dimensions
			= custom_url_link = custom_title_link 
			= parser_extlink_rel = parser_extlink_target
			= null;
		return this;
	}
}
