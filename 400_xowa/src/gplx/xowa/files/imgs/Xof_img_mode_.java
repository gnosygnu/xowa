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
package gplx.xowa.files.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xof_img_mode_ {
	public static final byte 
	  Tid__orig = 0
	, Tid__thumb = 1
	;
	public static byte By_bool(boolean is_thumb) {return is_thumb ? Tid__thumb : Tid__orig;}
	public static final    byte[][] Names_ary = new byte[][] {Bry_.new_a7("orig"), Bry_.new_a7("thumb")};
}
