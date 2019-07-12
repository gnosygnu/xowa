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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_prepro_accum__dom implements Xomw_prepro_accum {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public Xomw_prepro_accum__dom(String val) {
		this.Add_str_literal(val);
	}
	public Xomw_prepro_accum__dom Add_str_literal(String val) {
		bfr.Add_str_u8(val);
		return this;
	}
	public Bry_bfr Bfr() {return bfr;}
	public byte[] Bfr_bry() {return bfr.Bfr();}
	public int Len() {return bfr.Len();}
	public void Clear() {bfr.Clear();}
	public Xomw_prepro_accum__dom Add_str_escaped(byte[] src, int bgn, int end) {
		bfr.Add_bry_escape_html(src, bgn, end);
		return this;
	}
	public Xomw_prepro_accum__dom Add_bry(byte[] val) {
		bfr.Add(val);
		return this;
	}
	public Xomw_prepro_accum__dom Add_bry(byte[] val, int bgn, int end) {
		bfr.Add_mid(val, bgn, end);
		return this;
	}
	public void Del_at_end(int count) {
		bfr.Del_by(count);
	}
	public String To_str() {
		return bfr.To_str();
	}
	public byte[] To_bry() {
		return bfr.To_bry();
	}

	public static final    Xomw_prepro_accum__dom Instance = new Xomw_prepro_accum__dom("");
	public Xomw_prepro_accum Make_new() {return new Xomw_prepro_accum__dom("");}
}
