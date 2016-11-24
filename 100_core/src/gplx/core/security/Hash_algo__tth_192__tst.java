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
