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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_amp_tkn_ent extends Xop_tkn_itm_base {
	private Xop_amp_trie_itm html_ref_itm;
	public Xop_amp_tkn_ent(int bgn, int end, Xop_amp_trie_itm html_ref_itm) {
		this.html_ref_itm = html_ref_itm;
		this.Tkn_ini_pos(false, bgn, end);
	}
	@Override public byte Tkn_tid()			{return Xop_tkn_itm_.Tid_html_ref;}
	public int Char_int()					{return html_ref_itm.Char_int();}
	public byte[] Xml_name_bry()			{return html_ref_itm.Xml_name_bry();}
	public boolean Itm_is_custom()				{return html_ref_itm.Tid() == Xop_amp_trie_itm.Tid_name_xowa;}
	public void Print_ncr(Bry_bfr bfr)		{html_ref_itm.Print_ncr(bfr);}
	public void Print_literal(Bry_bfr bfr)	{html_ref_itm.Print_literal(bfr);}
}
