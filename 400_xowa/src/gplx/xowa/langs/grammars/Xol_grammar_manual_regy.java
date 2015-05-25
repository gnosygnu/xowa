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
package gplx.xowa.langs.grammars; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_grammar_manual_regy {
	private Hash_adp_bry[] ary = new Hash_adp_bry[Xol_grammar_.Tid__max];
	public byte[] Itms_get(byte type_tid, byte[] word) {
		Hash_adp_bry hash = ary[type_tid]; if (hash == null) return null;
		return (byte[])hash.Get_by_bry(word);
	}
	public Xol_grammar_manual_regy Itms_add(byte type_tid, String orig, String repl) {
		Hash_adp_bry hash = ary[type_tid];
		if (hash == null) {
			hash = Hash_adp_bry.ci_ascii_();	// ASCII:currently only being used for Wikiuutiset; DATE:2014-07-07
			ary[type_tid] = hash;
		}
		hash.Add_str_obj(orig, Bry_.new_a7(repl));
		return this;
	}
}
