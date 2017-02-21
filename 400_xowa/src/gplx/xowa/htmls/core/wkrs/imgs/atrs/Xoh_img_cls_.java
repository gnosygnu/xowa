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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.core.btries.*;
public class Xoh_img_cls_ {
	public static final byte		// SERIALIZED
	  Tid__none				= 0	// EX: [[File:A.png]]			-> "<img>"
	, Tid__thumbimage		= 1	// EX: [[File:A.png|thumb]]		-> "<img class='thumbimage'>"
	, Tid__thumbborder		= 2	// EX: [[File:A.png|border]]	-> "<img class='thumbborder'>"
	, Tid__manual			= 3	// EX: [[File:A.png|class=x]]	-> "<img class='x'>"
	;
	public static final String 
	  Str__thumbimage		= "thumbimage"
	, Str__thumbborder		= "thumbborder"
	;
	public static final byte[]
	  Bry__none				= Bry_.Empty
	;
	public static final byte[] 
	  Bry__thumbimage		= Bry_.new_a7(Str__thumbimage)
	, Bry__thumbborder		= Bry_.new_a7(Str__thumbborder)
	;
	public static final Btrie_slim_mgr Trie = Btrie_slim_mgr.cs()
	.Add_bry_byte(Bry__thumbimage	, Tid__thumbimage)
	.Add_bry_byte(Bry__thumbborder	, Tid__thumbborder)
	;
	private static final byte[] Bry__html_class = Bry_.new_a7(" class=\"");
	public static byte[] To_html(int tid, byte[] other) {
		boolean other_is_empty = Bry_.Len_eq_0(other);
		if (tid == Xoh_img_cls_.Tid__none && other_is_empty) return Bry_.Empty;
		byte[] cls = null;
		switch (tid) {
			case Xoh_img_cls_.Tid__thumbimage:	cls = Bry__thumbimage; break;
			case Xoh_img_cls_.Tid__thumbborder:	cls = Bry__thumbborder; break;
		}
		byte[] other_0 = Bry_.Empty, other_1 = Bry_.Empty;
		if (!other_is_empty) {
			if (cls != null) other_0 = Byte_ascii.Space_bry;
			other_1 = other;
		}
		return Bry_.Add(Bry__html_class, cls, other_0, other_1, Byte_ascii.Quote_bry);
	}
	public static byte[] To_val_or_null(int tid, byte[] other) {
		boolean other_is_empty = Bry_.Len_eq_0(other);
		if (tid == Xoh_img_cls_.Tid__none && other_is_empty) return null;
		byte[] cls = null;
		switch (tid) {
			case Xoh_img_cls_.Tid__thumbimage:	cls = Bry__thumbimage; break;
			case Xoh_img_cls_.Tid__thumbborder:	cls = Bry__thumbborder; break;
		}
		byte[] other_0 = Bry_.Empty, other_1 = Bry_.Empty;
		if (!other_is_empty) {
			if (cls != null) other_0 = Byte_ascii.Space_bry;
			other_1 = other;
		}
		return Bry_.Add(cls, other_0, other_1);
	}
}
