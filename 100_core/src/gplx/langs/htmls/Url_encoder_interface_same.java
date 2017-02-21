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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
public class Url_encoder_interface_same implements Url_encoder_interface {
	public String Encode_str(String v) {return v;}
	public byte[] Encode_bry(String v) {return Bry_.new_u8(v);}
        public static final Url_encoder_interface_same Instance = new Url_encoder_interface_same(); Url_encoder_interface_same() {}
}
