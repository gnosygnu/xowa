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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_ns_tst {
	@Before public void init() {fxt.Clear();} private Xow_ns_fxt fxt = new Xow_ns_fxt();
	@Test   public void Category() {
		fxt	.Expd_id_subjId_(Xow_ns_.Id_category)
			.Expd_id_talkId_(Xow_ns_.Id_category_talk)
			.Expd_id_subj_(Bool_.Y)
			.Expd_id_talk_(Bool_.N)
			.Test(Xow_ns_.Id_category)
			;
	}		
	@Test   public void Category_talk() {
		fxt	.Expd_id_subjId_(Xow_ns_.Id_category)
			.Expd_id_talkId_(Xow_ns_.Id_category_talk)
			.Expd_id_subj_(Bool_.N)
			.Expd_id_talk_(Bool_.Y)
			.Test(Xow_ns_.Id_category_talk)
			;
	}
	@Test   public void Special() {
		fxt	.Expd_id_subjId_(Xow_ns_.Id_special)
			.Expd_id_talkId_(Xow_ns_.Id_special)
			.Expd_id_subj_(Bool_.Y)
			.Expd_id_talk_(Bool_.N)
			.Test(Xow_ns_.Id_special)
			;
	}
}
class Xow_ns_fxt {
	public void Clear() {
		expd_id_subjId = expd_id_talkId = Int_.Max_value;
		expd_id_subj = expd_id_talk = false;
	}
	public Xow_ns_fxt Expd_id_subjId_(int v) {expd_id_subjId = v; return this;} private int expd_id_subjId;
	public Xow_ns_fxt Expd_id_talkId_(int v) {expd_id_talkId = v; return this;} private int expd_id_talkId;
	public Xow_ns_fxt Expd_id_subj_(boolean v) {expd_id_subj = v; return this;} private boolean expd_id_subj;
	public Xow_ns_fxt Expd_id_talk_(boolean v) {expd_id_talk = v; return this;} private boolean expd_id_talk;
	public void Test(int nsId) {
		Xow_ns actl = new Xow_ns(nsId, Xow_ns_case_.Tid__1st, Bry_.Empty, false);
		Tfds.Eq(expd_id_subjId, actl.Id_subj_id());
		Tfds.Eq(expd_id_talkId, actl.Id_talk_id());
		Tfds.Eq(expd_id_subj, actl.Id_subj());
		Tfds.Eq(expd_id_talk, actl.Id_talk());
	}	
}
