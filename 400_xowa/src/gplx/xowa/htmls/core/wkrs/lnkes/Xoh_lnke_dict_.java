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
package gplx.xowa.htmls.core.wkrs.lnkes;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.errs.ErrUtl;
public class Xoh_lnke_dict_ {
	public static final byte	// SERIALIZED
	  Type__free					= 0
	, Type__auto					= 1
	, Type__text					= 2
	;
	public static final byte[]
	  Html__atr__0					= BryUtl.NewA7("\" rel=\"nofollow\" class=\"external ")
	, Html__class__free				= BryUtl.NewA7("free")
	, Html__class__auto				= BryUtl.NewA7("autonumber")
	, Html__class__text				= BryUtl.NewA7("text")
	, Html__rhs_end					= BryUtl.NewA7("\">")
	;
	public static byte[] 
	  Html__rel__nofollow		= BryUtl.NewA7("nofollow")
	, Html__cls__external		= BryUtl.NewA7("external")
	;
	public static final Hash_adp_bry Hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Html__class__free, Type__free)
	.Add_bry_byte(Html__class__auto, Type__auto)
	.Add_bry_byte(Html__class__text, Type__text)
	;
	public static byte[] To_html_class(byte tid) {
		switch (tid) {
			case Xoh_lnke_dict_.Type__free:	return Xoh_lnke_dict_.Html__class__free;
			case Xoh_lnke_dict_.Type__auto:	return Xoh_lnke_dict_.Html__class__auto;
			case Xoh_lnke_dict_.Type__text:	return Xoh_lnke_dict_.Html__class__text;
			default:						throw ErrUtl.NewUnhandled(tid);
		}
	}
}
