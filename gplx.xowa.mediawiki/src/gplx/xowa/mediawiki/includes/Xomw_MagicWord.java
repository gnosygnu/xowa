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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
public class Xomw_MagicWord {
	public boolean case_match;
	public byte[] name;
	public Xomw_MagicWordSynonym[] synonyms;
	public Xomw_MagicWord(byte[] name, boolean case_match, byte[][] synonyms_ary) {
		this.name = name;
		this.case_match = case_match;

		int synonyms_len = synonyms_ary.length;
		this.synonyms = new Xomw_MagicWordSynonym[synonyms_len];
		for (int i = 0; i < synonyms_len; i++) {
			synonyms[i] = new Xomw_MagicWordSynonym(name, case_match, synonyms_ary[i]);
		}
	}
}
