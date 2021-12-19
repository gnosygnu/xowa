/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/

package gplx.xowa.parsers.tmpls;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.Xop_fxt;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.wikis.nss.Xow_ns_;
import org.junit.Test;

public class Xot_defn_tmpl_Test {
	private final Xot_defn_tmpl__fxt fxt = new Xot_defn_tmpl__fxt();

	@Test
    public void CopyNewMain() {
    	fxt.Test_CopyNew_FrameTtl(Xow_ns_.Tid__main, "a", "A");
	}

	@Test
    public void CopyNewUppercaseNonAscii() {
    	fxt.Test_CopyNew_FrameTtl(Xow_ns_.Tid__main, "à", "À");
	}

	@Test
    public void CopyNewSpaces() {
    	fxt.Test_CopyNew_FrameTtl(Xow_ns_.Tid__help_talk, "a_b", "Help_talk:A b");
	}

	@Test
    public void CopyNewTemplate() {
    	fxt.Test_CopyNew_FrameTtl(Xow_ns_.Tid__template, "a", "Template:A");
	}

	@Test
    public void CopyNewNonMain() {
    	fxt.Test_CopyNew_FrameTtl(Xow_ns_.Tid__project, "a", "Wikipedia:A");
	}
}
class Xot_defn_tmpl__fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	public void Test_CopyNew_FrameTtl(int frameNs, String frameTtlStr, String expdFrameTtl) {
		Xowe_wiki wiki = fxt.Wiki();
		Xop_ctx ctx = Xop_ctx.New__top(wiki);
		byte[] frameTtlBry = BryUtl.NewU8(frameTtlStr);
		Xot_defn_tmpl orig_defn = new Xot_defn_tmpl();
		Xot_invk orig = Xot_invk_temp.New_root(BryUtl.NewU8("orig"));
		Xot_invk caller = Xot_invk_temp.New_root(BryUtl.NewU8("caller"));
		byte[] src = BryUtl.Empty;

		Xot_invk tmpl =  Xot_defn_tmpl_.CopyNew(ctx, orig_defn, orig, caller, src, frameNs, frameTtlBry);
		GfoTstr.Eq(BryUtl.NewU8(expdFrameTtl), tmpl.Frame_ttl());
	}
}