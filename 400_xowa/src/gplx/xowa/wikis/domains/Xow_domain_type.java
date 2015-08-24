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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_domain_type {
	public Xow_domain_type(boolean multi_lang, int src, int tid, byte[] key_bry, byte[] abrv, byte[] domain_bry) {
		this.multi_lang = multi_lang; this.src = src; this.tid = tid; this.key_bry = key_bry; this.abrv = abrv; this.domain_bry = domain_bry;
		this.key_str = String_.new_u8(key_bry);			
	}
	public boolean Multi_lang() {return multi_lang;} private final boolean multi_lang;		// EX: y
	public int Src() {return src;} private final int src;							// EX: 1 (wm,mw,wk,xo)
	public int Tid() {return tid;} private final int tid;							// EX: 1 (Tid_wikipedia)
	public String Key_str() {return key_str;} private final String key_str;			// EX: wikipedia
	public byte[] Key_bry() {return key_bry;} private final byte[] key_bry;			// EX: wikipedia
	public byte[] Abrv() {return abrv;} private final byte[] abrv;					// EX: w
	public byte[] Domain_bry() {return domain_bry;} private byte[] domain_bry;			// EX: .wikipedia.org
}
class Xow_domain_type_src_ {
	public static final int 
	  Int__wmf		= 1		// administered by wmf; wikipedia, etc.
	, Int__wikia	= 2		// *.wikia.com
	, Int__mw		= 3		// mediawiki installations not part of wmf, wikia
	, Int__xowa		= 4		// xowa
	;
}
