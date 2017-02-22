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
package gplx.langs.htmls.docs; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import org.junit.*;
public class Gfh_tag_rdr_tst {
	private final    Gfh_tag_rdr_fxt fxt = new Gfh_tag_rdr_fxt();
	@Test   public void Basic() {
		fxt.Init("1<div id='1'>2</div>3<div id='2'>4</div>5<div id='3'>6</div>7");
		fxt.Test__move_fwd_head("<div id='1'>"); fxt.Test__pos("2");
		fxt.Test__peek_fwd_head("<div id='2'>"); fxt.Test__pos("2");
		fxt.Test__move_fwd_head("<div id='2'>"); fxt.Test__pos("4");
		fxt.Test__peek_bwd_tail("</div>3")	   ; fxt.Test__pos("4");
	}
	@Test   public void Comment() {
		fxt.Init("1<!--2-->3<!--4-->5<div id='1'>6</div>");
		fxt.Test__move_fwd_head(Gfh_tag_.Id__any		, "<div id='1'>")	; fxt.Test__pos("6");
	}
	@Test   public void Meta() {
		fxt.Init("<!DOCTYPE html>1<div id='1'>2</div>3");
		fxt.Test__move_fwd_head(Gfh_tag_.Id__div		, "<div id='1'>")	; fxt.Test__pos("2");
	}
	@Test   public void Recursive__same_tags() {
		fxt.Init("1<a>2<a>3</a>4</a>5");
		fxt.Test__move_fwd_head(Gfh_tag_.Id__a		, "<a>")	; fxt.Test__pos("2");
		fxt.Test__move_fwd_tail(Gfh_tag_.Id__a		, "</a>")	; fxt.Test__pos("5");
	}
	@Test   public void Recursive__diff_tags() {
		fxt.Init("1<div>2<a>3<img/>4</a>5</div>6");
		fxt.Test__move_fwd_head(Gfh_tag_.Id__div	, "<div>")	; fxt.Test__pos("2");
		fxt.Test__move_fwd_tail(Gfh_tag_.Id__div	, "</div>")	; fxt.Test__pos("6");
	}
	@Test   public void Inline() {
		fxt.Init("1<br/>2");
		fxt.Test__move_fwd_head(Gfh_tag_.Id__br		, "<br/>")	; fxt.Test__pos("2");
	}
}
class Gfh_tag_rdr_fxt {
	private final    Gfh_tag_rdr rdr = Gfh_tag_rdr.New__html();
	public void Init(String src_str) {
		byte[] src_bry = Bry_.new_u8(src_str);
		rdr.Init(Bry_.Empty, src_bry, 0, src_bry.length);
	}
	public void Test__move_fwd_head(String expd) {Test__move_fwd_head(Gfh_tag_.Id__any, expd);}
	public void Test__move_fwd_head(int match_name_id, String expd) {
		Gfh_tag actl_tag = rdr.Tag__move_fwd_head(match_name_id).Chk_name_or_fail(match_name_id);
		Tfds.Eq_str(expd, String_.new_u8(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_end()));
	}
	public void Test__move_fwd_tail(int match_name_id, String expd) {
		Gfh_tag actl_tag = rdr.Tag__move_fwd_tail(match_name_id);
		Tfds.Eq_str(expd, String_.new_u8(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_end()));
	}
	public void Test__peek_fwd_head(String expd) {
		Gfh_tag actl_tag = rdr.Tag__peek_fwd_head();
		Tfds.Eq_str(expd, String_.new_u8(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_end()));
	}
	public void Test__peek_bwd_tail(String expd_str) {
		byte[] expd_bry = Bry_.new_u8(expd_str);
		Gfh_tag actl_tag = rdr.Tag__peek_bwd_tail(-1);
		Tfds.Eq_bry(expd_bry, Bry_.Mid(rdr.Src(), actl_tag.Src_bgn(), actl_tag.Src_bgn() + expd_bry.length));
	}
	public void Test__pos(String expd_str) {
		byte[] expd_bry = Bry_.new_u8(expd_str);
		Tfds.Eq_bry(expd_bry, Bry_.Mid(rdr.Src(), rdr.Pos(), rdr.Pos() + expd_bry.length));
	}
}
