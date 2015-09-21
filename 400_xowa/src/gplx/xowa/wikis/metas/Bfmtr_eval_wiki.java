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
package gplx.xowa.wikis.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Bfmtr_eval_wiki implements Bry_fmtr_eval_mgr {
	public Bfmtr_eval_wiki(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = true;
	public byte[] Eval(byte[] cmd) {
		Object rslt = GfsCore._.Exec_bry(cmd, wiki);
		return Bry_.new_u8(Object_.Xto_str_strict_or_null_mark(rslt));
	}
	public void Eval_mgr_(Bry_fmtr... fmtrs) {
		int fmtrs_len = fmtrs.length;
		for (int i = 0; i < fmtrs_len; i++)
			fmtrs[i].Eval_mgr_(this);
	}
}
