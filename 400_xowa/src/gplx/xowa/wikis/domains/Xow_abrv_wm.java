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
import gplx.xowa.langs.*;
public class Xow_abrv_wm {
	public Xow_abrv_wm(byte[] raw, byte[] lang_domain, Xol_lang_stub lang_actl, int domain_type) {
		this.raw = raw;
		this.lang_domain = lang_domain;
		this.lang_actl = lang_actl;
		this.domain_type = domain_type;
	}
	public byte[] Raw() {return raw;} private final byte[] raw;
	public byte[] Lang_domain() {return lang_domain;} private final byte[] lang_domain;
	public Xol_lang_stub Lang_actl() {return lang_actl;} private final Xol_lang_stub lang_actl;
	public int Domain_type() {return domain_type;} private final int domain_type;
}
