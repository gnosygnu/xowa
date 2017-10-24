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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
class DsvSymbols {
	public String FldSep() {return fldSep;} public DsvSymbols FldSep_(String v) {fldSep = v; CmdSequence_set(); return this;} private String fldSep;
	public String RowSep() {return rowSep;}
	public DsvSymbols RowSep_(String v) {
		rowSep = v;
		rowSepIsNewLine = String_.Has(v, "\n") || String_.Has(v, "\r");
		return this;
	}	String rowSep;
	public String QteDlm() {return qteDlm;} public void QteDlm_set(String v) {qteDlm = v; CmdSequence_set();} private String qteDlm;
	public String CmdDlm() {return cmdDlm;} public void CmdDlm_set(String v) {cmdDlm = v; CmdSequence_set();} private String cmdDlm;
	public String CmdSequence() {return cmdSequence;} private String cmdSequence;
	public String CommentSym() {return commentSym;} public void CommentSym_set(String v) {commentSym = v;} private String commentSym;
	public String TblNameSym() {return tblNameSym;} public void TblNamesSym_set(String v) {tblNameSym = v;} private String tblNameSym;
	public String FldNamesSym() {return fldNamesSym;} public void FldNamesSym_set(String v) {fldNamesSym = v;} private String fldNamesSym;
	public String FldTypesSym() {return fldTypesSym;} public void FldTypesSym_set(String v) {fldTypesSym = v;} private String fldTypesSym;
	public boolean RowSepIsNewLine() {return rowSepIsNewLine;} private boolean rowSepIsNewLine;
	public void Reset() {
		fldSep			= ",";
		RowSep_			("\r\n");

		qteDlm			= "\"";
		cmdDlm			= " ";
		CmdSequence_set();

		commentSym		= "//";
		tblNameSym		= "#";
		fldNamesSym		= "@";
		fldTypesSym		= "$";
	}
	void CmdSequence_set() { // commandDelimiters are repeated; once without quotes and once with quotes; ex: , ," ",
		cmdSequence = String_.Concat(cmdDlm, fldSep, qteDlm, cmdDlm, qteDlm);
	}
	public static DsvSymbols default_() {return new DsvSymbols();} DsvSymbols() {this.Reset();}
}
