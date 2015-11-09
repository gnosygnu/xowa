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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.langs.htmls.parsers.styles.*;
import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_thm_parse {
/*
<div class="thumb tleft">
  <div id="xowa_file_div_60" class="thumbinner" style="width:220px;">
    <a href="/wiki/File:Protoplanetary-disk.jpg" class="image" xowa_title="Protoplanetary-disk.jpg">
      <img id="xowa_file_img_60" alt="" 
        src="file:///J:/xowa/file/commons.wikimedia.org/thumb/7/1/7/c/Protoplanetary-disk.jpg/220px.jpg" 
        width="220" height="119" class="thumbimage">
    </a> 
    <div class="thumbcaption">
      <div class="magnify">
        <a href="/wiki/File:Protoplanetary-disk.jpg" class="@gplx.Internal protected" title="Enlarge">
          <img src="file:///J:/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png" width="15" height="11" alt="">
        </a>
      </div>Artist's impression of the early Solar System's planetary disk
    </div>
  </div>
</div>
*/
	private final Xoh_anch_href_parser href_parser = new Xoh_anch_href_parser();
	private final Xoh_img_parser img_parser = new Xoh_img_parser();
	private final Bry_rdr rdr = new Bry_rdr();
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Html_tag_rdr tag_rdr, byte[] src, Html_tag div_0) {
		int rng_bgn = div_0.Src_bgn(), rng_end = div_0.Src_end();
		byte thm_align = div_0.Atrs__cls_find_1st(Xop_lnki_align_h.Hash);
		Html_tag div_1 = tag_rdr.Tag__move_fwd_head(Bry__cls__thumbinner);
		int div_width = div_1.Atrs__style_get_as_int(Html_atr_style_.Bry__width);
		Html_tag anch = tag_rdr.Tag__move_fwd_head(Xoh_img_parser.Bry__cls__anch__image);
		Html_atr href = anch.Atrs__get_by(Html_atr_.Bry__href);
		rdr.Ctor_by_page(Bry_.Empty, src, src.length);
		href_parser.Parse(rdr, hdoc_wkr.Ctx().Wiki(), href.Val_bgn(), href.Val_end());
		img_parser.Parse(hdoc_wkr, src, tag_rdr, anch);
		tag_rdr.Tag__move_fwd_head(Bry__cls__thumbcaption);
		tag_rdr.Tag__move_fwd_head(Bry__cls__magnify);
		Html_tag div_3_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		int capt_bgn = div_3_tail.Src_end();
		Html_tag div_4_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		int capt_end = div_4_tail.Src_bgn();
		tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		Html_tag div_0_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__div);
		rng_end = div_0_tail.Src_end();
            Tfds.Write(thm_align, div_width, Bry_.Mid(src, href_parser.Page_bgn(), href_parser.Page_end()), img_parser.Img_w(), img_parser.Img_h(), Bry_.Mid_w_trim(src, capt_bgn, capt_end), rng_end);
		hdoc_wkr.On_img_thm(rng_bgn, rng_end);
		return rng_end;
	}
	private static final byte[]
	  Bry__cls__thumbinner		= Bry_.new_a7("thumbinner")
	, Bry__cls__thumbcaption	= Bry_.new_a7("thumbcaption")
	, Bry__cls__magnify			= Bry_.new_a7("magnify")
	;
}
