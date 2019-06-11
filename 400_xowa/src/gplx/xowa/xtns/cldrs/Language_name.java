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
package gplx.xowa.xtns.cldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Language_name implements gplx.core.brys.Bry_bfr_able {
	public Language_name(byte[] code, byte[] name, byte[] note) {
		this.code = code;
		this.name = name;
		this.note = note;
	}
	public byte[] Code() {return code;} private final    byte[] code;
	public byte[] Name() {return name;} private final    byte[] name;
	public byte[] Note() {return note;} private final    byte[] note;
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add(code).Add_byte_pipe();
		bfr.Add(name).Add_byte_pipe();
		bfr.Add(note);
	}

	public static final    Language_name[] Ary_empty = new Language_name[0];
}
