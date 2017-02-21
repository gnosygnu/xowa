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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_domain_tid {
	public Xow_domain_tid(boolean multi_lang, int src, int tid, byte[] key_bry, byte[] abrv, byte[] domain_bry) {
		this.multi_lang = multi_lang; this.src = src; this.tid = tid; this.key_bry = key_bry; this.abrv = abrv; this.domain_bry = domain_bry;
		this.key_str = String_.new_u8(key_bry);			
	}
	public boolean Multi_lang() {return multi_lang;} private final    boolean multi_lang;		// EX: y
	public int Src() {return src;} private final    int src;							// EX: 1 (wm,mw,wk,xo)
	public int Tid() {return tid;} private final    int tid;							// EX: 1 (Tid_wikipedia)
	public String Key_str() {return key_str;} private final    String key_str;			// EX: wikipedia
	public byte[] Key_bry() {return key_bry;} private final    byte[] key_bry;			// EX: wikipedia
	public byte[] Abrv() {return abrv;} private final    byte[] abrv;					// EX: w
	public byte[] Domain_bry() {return domain_bry;} private byte[] domain_bry;			// EX: .wikipedia.org
	public byte[] Display_bry() {return Bry_.Ucase__1st(key_bry);}						// EX: Wikipedia

	public static final int 
	  Src__wmf		= 1		// administered by wmf; wikipedia, etc.
	, Src__wikia	= 2		// *.wikia.com
	, Src__mw		= 3		// mediawiki installations not part of wmf, wikia
	, Src__xowa		= 4		// xowa
	;
}
