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
package gplx.xowa.htmls.bridges; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
public class Bridge_msg_bldr_tst {
	@Before public void init() {fxt.Clear();} private Bridge_msg_bldr_fxt fxt = new Bridge_msg_bldr_fxt();
	@Test   public void Bld() {
		fxt.Bldr().Rslt_pass_y_().Notify_pass_("passed").Data("key1", true).Data("key2", 1).Data("key3", "val3");
		fxt.Test_to_json_str("{'rslt':{'pass':true},'notify':{'text':'passed','status':'success'},'data':{'key1':true,'key2':1,'key3':'val3'}}");
	}
}
class Bridge_msg_bldr_fxt {
	public Bridge_msg_bldr Bldr() {return bldr;} private final Bridge_msg_bldr bldr = new Bridge_msg_bldr().Opt_quote_byte_apos_();
	public void Clear() {}
	public void Test_to_json_str(String expd) {
            Tfds.Eq_str_lines(expd, bldr.To_json_str());
	}
}
