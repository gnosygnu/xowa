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
public class Xomw_param_itm {
	public final    byte[] magic;
	public final    int type_uid;
	public final    byte[] name;
	public final    int name_uid;
	public Xomw_param_itm(byte[] magic, int type_uid, byte[] name) {
		this.magic = magic;
		this.type_uid = type_uid;
		this.name = name;
		this.name_uid = name_uids.Get_as_int_or(name, -1);
	}
	public static final int 
	  Name__width             = 0
	, Name__height            = 1
	, Name__manual_thumb      = 2
	, Name__alt               = 3
	, Name__class             = 4
	, Name__link              = 5
	, Name__frameless         = 6
	, Name__framed            = 7
	, Name__thumbnail         = 8
	;
	private static final    Hash_adp_bry name_uids = Hash_adp_bry.cs()
	.Add_str_int("width"                , Name__width)
	.Add_str_int("manual_thumb"         , Name__manual_thumb)
	.Add_str_int("alt"                  , Name__alt)
	.Add_str_int("class"                , Name__class)
	.Add_str_int("link"                 , Name__link)
	.Add_str_int("frameless"            , Name__frameless)
	.Add_str_int("framed"               , Name__framed)
	.Add_str_int("thumbnail"            , Name__thumbnail)
	;
	public static final    byte[]
	  Mw__img_width = Bry_.new_a7("img_width")
	;
	public static final    byte[]
	  Name_bry__width = Bry_.new_a7("width")
	;
}
