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
package gplx.core.security; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.consoles.*; import gplx.core.ios.*; /*IoStream*/
public class Hash_algo__tth_192__tst {	// REF: http://open-content.net/specs/draft-jchapweske-thex-02.html; DC++ 0.698
	private final    Hash_algo__fxt fxt = new Hash_algo__fxt(Hash_algo_.New__tth_192());
	@Test  public void Empty()				{fxt.Test__hash("LWPNACQDBZRYXW3VHJVCJ64QBZNGHOHHHZWCLNQ", "");}
	@Test  public void Null__1()			{fxt.Test__hash("VK54ZIEEVTWNAUI5D5RDFIL37LX2IQNSTAXFKSA", "\0");}
	@Test  public void ab()					{fxt.Test__hash("XQXRSGMB3PSN2VGZYJMNJG6SOOQ3JIGQHD2I6PQ", "ab");}
	@Test  public void abc()				{fxt.Test__hash("ASD4UJSEH5M47PDYB46KBTSQTSGDKLBHYXOMUIA", "abc");}
	@Test  public void abde()				{fxt.Test__hash("SQF2PFTVIFRR5KJSI45IDENXMB43NI7EIXYGHGI", "abcd");}
	@Test  public void abcde()				{fxt.Test__hash("SKGLNP5WV7ZUMF6IUK5CYXBE3PI4C6PHWNVM2YQ", "abcde");}
	@Test  public void abcdefghi()			{fxt.Test__hash("RUIKHZFO4NIY6NNUHJMAC2I26U3U65FZWCO3UFY", "abcdefghi");}
	// @Test 
	public void a__x_1024()					{fxt.Test__hash("L66Q4YVNAFWVS23X2HJIRA5ZJ7WXR3F26RSASFA", String_.Repeat("A", 1024));}
	// @Test 
	public void a__x_1025()					{fxt.Test__hash("PZMRYHGY6LTBEH63ZWAHDORHSYTLO4LEFUIKHWY", String_.Repeat("A", 1025));}
	// @Test  
	public void A__Pow27()					{fxt.Test__hash("QNIJO36QDIQREUT3HWK4MDVKD2T6OENAEKYADTQ", String_.Repeat("A", (int)Math_.Pow(2, 27)));
	}
}
