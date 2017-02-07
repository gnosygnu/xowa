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
