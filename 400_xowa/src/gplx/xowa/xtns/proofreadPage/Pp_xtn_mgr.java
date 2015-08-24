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
package gplx.xowa.xtns.proofreadPage; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.html.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.*;
public class Pp_xtn_mgr extends Xox_mgr_base {
	@Override public boolean Enabled_default() {return false;}
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_a7("ProofreadPages");
	@Override public Xox_mgr Clone_new() {return new Pp_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		if (!this.Enabled_manually())
			this.Enabled_(wiki.Domain_tid() == Xow_domain_type_.Int__wikisource);	// only enable for wikisource
	}
}
