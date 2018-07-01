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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_ns_tst {
	@Before public void init() {fxt.Clear();} private Xow_ns_fxt fxt = new Xow_ns_fxt();
	@Test   public void Category() {
		fxt	.Expd_id_subjId_(Xow_ns_.Tid__category)
			.Expd_id_talkId_(Xow_ns_.Tid__category_talk)
			.Expd_id_subj_(Bool_.Y)
			.Expd_id_talk_(Bool_.N)
			.Test(Xow_ns_.Tid__category)
			;
	}		
	@Test   public void Category_talk() {
		fxt	.Expd_id_subjId_(Xow_ns_.Tid__category)
			.Expd_id_talkId_(Xow_ns_.Tid__category_talk)
			.Expd_id_subj_(Bool_.N)
			.Expd_id_talk_(Bool_.Y)
			.Test(Xow_ns_.Tid__category_talk)
			;
	}
	@Test   public void Special() {
		fxt	.Expd_id_subjId_(Xow_ns_.Tid__special)
			.Expd_id_talkId_(Xow_ns_.Tid__special)
			.Expd_id_subj_(Bool_.Y)
			.Expd_id_talk_(Bool_.N)
			.Test(Xow_ns_.Tid__special)
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
		Tfds.Eq(expd_id_subj, actl.Id_is_subj());
		Tfds.Eq(expd_id_talk, actl.Id_is_talk());
	}	
}
