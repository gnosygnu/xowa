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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.wikis.nss.*;
public class Xoh_img_hzip__dump__orig__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic() {	// [[File:A.png|border|class=other|220px|abc]]
		fxt.Test__bicode
		( "~%|\"\\QA.png~$m#Tabc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A.png/330px.png' width='330' height='220' alt='abc'></a>"
		);
	}
	@Test   public void Orig() {	// PURPOSE: orig with width was causing "!!" in alt; EX:[[File:A.png|abc]]; DATE:2016-08-09
		fxt.Test__bicode
		( "~%|#gqA.png~!b!Babc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img src='file:///mem/xowa/file/commons.wikimedia.org/orig/7/0/A.png' width='64' height='32' alt='abc'></a>"
		);
	}
	@Test   public void Gallery() {
		fxt.Test__bicode
		( "~%|3]3A.png~!b!B\"Mabc~"
		, "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/128px.png' width='64' height='32' alt='abc'></a>"
		);
	}
	@Test   public void Encode() {	// [[File:A+C.png|abc]]
		fxt.Wiki().File__fsdb_mode().Tid__v2__mp__y_();
		fxt.Test__bicode
		( "~%|\"\\qA%2BC.png~A+C.png~$m#Tabc~"
		, "<a href='/wiki/File:A%2BC.png' class='image' title='abc' xowa_title='A+C.png'><img src='file:///mem/xowa/file/en.wikipedia.org/thumb/2/7/A%2BC.png/330px.png' width='330' height='220' alt='abc'></a>"
		);
	}
	@Test   public void Link() {	// [[File:A.png|link=File:a.ogg|abc]]; [[File:Speakerlink-new.svg|11px|link=file:///C:/xowa/file/commons.wikimedia.org/orig/9/7/4/e/En-us-Alabama.ogg|Listen]]
		fxt.Test__bicode
		( "~%}'h(qA.ogg~\"M!babc~A.png~"
		, "<a href='/wiki/File:A.ogg' class='image' title='abc' xowa_title='A.ogg'><img src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/128px.png' width='128' height='64' alt='abc'></a>"
		);
	}
	@Test   public void Link__empty() {	// [[File:A.png|link=|abc]]; PAGE:en.w:Lackawanna_Cut-Off; DATE:2016-08-19
		fxt.Test__bicode
		( "~%}([^KA.png~#\"M!babc~A.png~"
		, "<img src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/128px.png' width='128' height='64' alt='abc'>"
		);
	}
	@Test   public void Encode_2() {	// [[File:A*C.png|abc]]; PAGE:en.w:Fibonacci_number; DATE:2016-08-10
		// Tfds.Write(gplx.xowa.files.Xof_file_wkr_.Md5(Bry_.new_a7("A*C.png")));
		fxt.Test__bicode
		( "~%|\"\\QA*C.png~$m#Tabc~"
		, "<a href='/wiki/File:A*C.png' class='image' title='abc' xowa_title='A*C.png'><img src='file:///mem/xowa/file/en.wikipedia.org/thumb/d/d/A*C.png/330px.png' width='330' height='220' alt='abc'></a>"
		);
	}
	@Test   public void Video() {	// handle thumbnail
		fxt.Test__bicode
		( "~%|=nTA.ogv~$m#T#T4.5~abc~"
		, "<a href='/wiki/File:A.ogv' class='image' title='abc' xowa_title='A.ogv'><img src='file:///mem/xowa/file/en.wikipedia.org/thumb/d/0/A.ogv/220px-4.5.jpg' width='330' height='220' alt='abc'></a>"
		);
	}
}
