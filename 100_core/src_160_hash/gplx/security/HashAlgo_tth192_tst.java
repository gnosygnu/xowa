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
package gplx.security; import gplx.*;
import org.junit.*; import gplx.ios.*; /*IoStream*/
public class HashAlgo_tth192_tst {
	@Test  public void Char0000() {tst_CalcBase32FromString("", "LWPNACQDBZRYXW3VHJVCJ64QBZNGHOHHHZWCLNQ");}
	@Test  public void Char0001() {tst_CalcBase32FromString("\0", "VK54ZIEEVTWNAUI5D5RDFIL37LX2IQNSTAXFKSA");}
	@Test  public void Char0002() {tst_CalcBase32FromString("ab", "XQXRSGMB3PSN2VGZYJMNJG6SOOQ3JIGQHD2I6PQ");}
	@Test  public void Char0003() {tst_CalcBase32FromString("abc", "ASD4UJSEH5M47PDYB46KBTSQTSGDKLBHYXOMUIA");}
	@Test  public void Char0004() {tst_CalcBase32FromString("abcd", "SQF2PFTVIFRR5KJSI45IDENXMB43NI7EIXYGHGI");}
	@Test  public void Char0005() {tst_CalcBase32FromString("abcde", "SKGLNP5WV7ZUMF6IUK5CYXBE3PI4C6PHWNVM2YQ");}
	@Test  public void Char0009() {tst_CalcBase32FromString("abcdefghi", "RUIKHZFO4NIY6NNUHJMAC2I26U3U65FZWCO3UFY");}
	@Test  public void Char1024() {tst_CalcBase32FromString(String_.Repeat("A", 1024), "L66Q4YVNAFWVS23X2HJIRA5ZJ7WXR3F26RSASFA");}
	@Test  public void Char1025() {tst_CalcBase32FromString(String_.Repeat("A", 1025), "PZMRYHGY6LTBEH63ZWAHDORHSYTLO4LEFUIKHWY");}
//		@Test  // commented out due to time (approx 17.94 seconds)
	public void Ax2Pow27() {		// 134 MB
		tst_CalcBase32FromString(String_.Repeat("A", (int)Math_.Pow(2, 27)), "QNIJO36QDIQREUT3HWK4MDVKD2T6OENAEKYADTQ");
	}
	void tst_CalcBase32FromString(String raw, String expd) {
		IoStream stream = IoStream_.mem_txt_(Io_url_.Empty, raw);
		String actl = HashAlgo_.Tth192.CalcHash(ConsoleDlg_.Null, stream);
		Tfds.Eq(expd, actl);
	}
}
/* 
		The empty (zero-length) file:
		urn:tree:tiger:LWPNACQDBZRYXW3VHJVCJ64QBZNGHOHHHZWCLNQ
			
		A file with a single zero byte:
		urn:tree:tiger:VK54ZIEEVTWNAUI5D5RDFIL37LX2IQNSTAXFKSA

		A file with 1024 'A' characters:
		urn:tree:tiger:L66Q4YVNAFWVS23X2HJIRA5ZJ7WXR3F26RSASFA
			
		A file with 1025 'A' characters:
		urn:tree:tiger:PZMRYHGY6LTBEH63ZWAHDORHSYTLO4LEFUIKHWY

		http://open-content.net/specs/draft-jchapweske-thex-02.html

		A file with 134,217,728 'A' characters (2 Pow 27)
		urn:tree:tiger:QNIJO36QDIQREUT3HWK4MDVKD2T6OENAEKYADTQ
		queried against DC++ 0.698
	*/
