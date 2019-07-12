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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.FILE:Preprocessor_DOM
/**
* @ingroup Parser
*/
public abstract class XomwPPDPart {
	/**
	* @var String Output accumulator String
	*/
//		private final    byte[] output;

	// Optional member variables:
	//   eqpos        Position of equals sign in output accumulator
	//   commentEnd   Past-the-end input pointer for the last comment encountered
	//   visualEnd    Past-the-end input pointer for the end of the accumulator minus comments
	public int eqpos = -1;
	public int commentEnd = -1;
	public int visualEnd = -1;

	public XomwPPDPart(String output) {
//			accum.Add_bry(Bry_.new_u8(output));
//			bfr = ((Xomw_prepro_accum__dom)accum).Bfr();
	}
//		private final    Xomw_prepro_accum__dom accum = new Xomw_prepro_accum__dom("");
//		private final    Bry_bfr bfr;
	public abstract Xomw_prepro_accum Accum();
//
//		public Bry_bfr Bfr() {return bfr;}
//		public int Len() {return bfr.Len();}
//		public byte[] To_bry() {return bfr.To_bry();}

	public abstract XomwPPDPart Make_new(String val);
}
