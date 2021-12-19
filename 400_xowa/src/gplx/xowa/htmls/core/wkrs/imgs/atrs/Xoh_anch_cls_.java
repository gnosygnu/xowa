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
package gplx.xowa.htmls.core.wkrs.imgs.atrs;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.langs.htmls.Gfh_bldr_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
public class Xoh_anch_cls_ {
	public static final byte	// SERIALIZED
	  Tid__image			= 0	// EX: [[File:A.png]]			-> "<a class='image'>"
	, Tid__none				= 1	// EX: [[File:A.png|link=A]]	-> "<a class=''>"
//		, Tid__xowa_media_play  = 2 // EX: [[File:A.ogg]]			-> "<a class='xowa_media_play'>"
	;
	public static final String 
	  Str__image			= "image"
//		, Str__xowa_media_play	= "xowa_media_play"
	;
	public static final byte[]
	  Bry__image			= BryUtl.NewA7(Str__image)
//		, Bry__xowa_media_play	= Bry_.new_a7(Str__xowa_media_play)
	;
	private static final byte[]
	  Html__image			= BryUtl.Add(Gfh_bldr_.Bry__cls__nth, Bry__image)
//		, Html__xowa_media_play	= Bry_.Add(Gfh_bldr_.Bry__cls__nth, Bry__xowa_media_play)
	;
	public static final Btrie_slim_mgr Trie = Btrie_slim_mgr.cs()
	.Add_bry_byte(Bry__image			, Tid__image)
//		.Add_bry_byte(Bry__xowa_media_play	, Tid__xowa_media_play)
	;
	public static byte[] To_html(int tid) {
		switch (tid) {
			case Xoh_anch_cls_.Tid__none:				return BryUtl.Empty;
			case Xoh_anch_cls_.Tid__image:				return Html__image;
//				case Xoh_anch_cls_.Tid__xowa_media_play:	return Html__xowa_media_play;
			default:									throw ErrUtl.NewUnhandled(tid);
		}
	}
	public static byte[] To_val(int tid) {
		switch (tid) {
			case Xoh_anch_cls_.Tid__none:				return BryUtl.Empty;
			case Xoh_anch_cls_.Tid__image:				return Bry__image;
//				case Xoh_anch_cls_.Tid__xowa_media_play:	return Bry__xowa_media_play;
			default:									throw ErrUtl.NewUnhandled(tid);
		}
	}
}
