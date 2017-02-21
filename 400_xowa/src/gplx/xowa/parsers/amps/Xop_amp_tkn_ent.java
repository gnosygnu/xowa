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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.langs.htmls.entitys.*;
public class Xop_amp_tkn_ent extends Xop_tkn_itm_base {
	private Gfh_entity_itm html_ref_itm;
	public Xop_amp_tkn_ent(int bgn, int end, Gfh_entity_itm html_ref_itm) {
		this.html_ref_itm = html_ref_itm;
		this.Tkn_ini_pos(false, bgn, end);
	}
	@Override public byte Tkn_tid()			{return Xop_tkn_itm_.Tid_html_ref;}
	public int Char_int()					{return html_ref_itm.Char_int();}
	public byte[] Xml_name_bry()			{return html_ref_itm.Xml_name_bry();}
	public boolean Itm_is_custom()				{return html_ref_itm.Tid() == Gfh_entity_itm.Tid_name_xowa;}
	public void Print_ncr(Bry_bfr bfr)		{html_ref_itm.Print_ncr(bfr);}
	public void Print_literal(Bry_bfr bfr)	{html_ref_itm.Print_literal(bfr);}
}
