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
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import org.junit.*;
public class Html_tag_rdr_tst {
	private final Html_tag_rdr_fxt fxt = new Html_tag_rdr_fxt();
	@Test   public void Basic() {
		fxt.Init("1<div id='1'>2</div>3<div id='2'>4</div>5<div id='3'>6</div>7");
		fxt.Test__move_fwd_head("<div id='1'>"); fxt.Test__pos("2");
		fxt.Test__peek_fwd_head("<div id='2'>"); fxt.Test__pos("2");
		fxt.Test__move_fwd_head("<div id='2'>"); fxt.Test__pos("4");
		fxt.Test__peek_bwd_tail("</div>3")	   ; fxt.Test__pos("4");
	}
	@Test   public void Comment() {
		fxt.Init("1<!--2-->3<!--4-->5<div id='1'>6</div>");
		fxt.Test__move_fwd_head(Html_tag_.Id__comment	, "<!--2-->")		; fxt.Test__pos("3");
		fxt.Test__move_fwd_head(Html_tag_.Id__any		, "<div id='1'>")	; fxt.Test__pos("6");
	}
	@Test   public void Meta() {
		fxt.Init("<!DOCTYPE html>1<div id='1'>2</div>3");
		fxt.Test__move_fwd_head(Html_tag_.Id__div		, "<div id='1'>")	; fxt.Test__pos("2");
	}
	@Test   public void Recursive() {
		fxt.Init("1<a>2<a>3</a>4</a>5");
		fxt.Test__move_fwd_head(Html_tag_.Id__a		, "<a>")	; fxt.Test__pos("2");
		fxt.Test__move_fwd_tail(Html_tag_.Id__a		, "</a>")	; fxt.Test__pos("5");
	}
}
class Html_tag_rdr_fxt {
	private final Html_tag_rdr rdr = new Html_tag_rdr();
//		private final Html_doc_log log = new Html_doc_log();
	public void Init(String src_str) {
		byte[] src_bry = Bry_.new_u8(src_str);
		rdr.Init(src_bry, 0, src_bry.length);
	}
	public void Test__move_fwd_head(String expd) {Test__move_fwd_head(Html_tag_.Id__any, expd);}
	public void Test__move_fwd_head(int match_name_id, String expd) {
		Html_tag actl_tag = rdr.Tag__move_fwd_head(match_name_id);
		Tfds.Eq_str(expd, String_.new_u8(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_end()));
	}
	public void Test__move_fwd_tail(int match_name_id, String expd) {
		Html_tag actl_tag = rdr.Tag__move_fwd_tail(match_name_id);
		Tfds.Eq_str(expd, String_.new_u8(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_end()));
	}
	public void Test__peek_fwd_head(String expd) {
		Html_tag actl_tag = rdr.Tag__peek_fwd_head();
		Tfds.Eq_str(expd, String_.new_u8(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_end()));
	}
	public void Test__peek_bwd_tail(String expd_str) {
		byte[] expd_bry = Bry_.new_u8(expd_str);
		Html_tag actl_tag = rdr.Tag__peek_bwd_tail(-1);
		Tfds.Eq_bry(expd_bry, Bry_.Mid(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_bgn() + expd_bry.length));
	}
	public void Test__pos(String expd_str) {
		byte[] expd_bry = Bry_.new_u8(expd_str);
		Tfds.Eq_bry(expd_bry, Bry_.Mid(rdr.Src(), rdr.Pos(), rdr.Pos() + expd_bry.length));
	}
}
