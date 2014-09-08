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
package gplx;
class ErrProcData {
	public String Raw() {return raw;} public ErrProcData Raw_(String val) {raw = val; return this;} private String raw = String_.Empty;
	public String SignatureRaw() {return signatureRaw;} public ErrProcData SignatureRaw_(String val) {signatureRaw = val; return this;} private String signatureRaw = String_.Empty;
	public String SourceFileRaw() {return sourceFileRaw;} public ErrProcData SourceFileRaw_(String val) {sourceFileRaw = val; return this;} private String sourceFileRaw = String_.Empty;
	public int SourceLine() {return sourceLine;} public ErrProcData SourceLine_(int val) {sourceLine = val; return this;} int sourceLine;
	public String IdeAddress() {return ideAddress;} public ErrProcData IdeAddress_(String val) {ideAddress = val; return this;} private String ideAddress = String_.Empty;
	
	public static ErrProcData[] parse_ary_(String stackTrace) {
		/*
		.java
		'   at gplx.Err.new_(Err.java:92)
			at gplx.Err.exc_(Err.java:43)
			at gplx.Err_.err_(Err_.java:4)
			at gplx._tst.Err__tst.RdrLoad(Err__tst.java:77)
			at gplx._tst.Err__tst.MgrInit(Err__tst.java:76)'
		.cs
		'	at gplx._tst.Err__tst.RdrLoad() in c:\000\200_dev\100.gplx\100.framework\100.core\gplx\tst\gplx\err__tst.cs:line 77
			at gplx._tst.Err__tst.MgrInit(String s) in c:\000\200_dev\100.gplx\100.framework\100.core\gplx\tst\gplx\err__tst.cs:line 76'
		*/
		if (stackTrace == null) return new ErrProcData[0];
				String[] lines = String_.SplitLines_any(stackTrace);
		ListAdp list = ListAdp_.new_();
		int len = Array_.Len(lines);		
		for (int i = 0; i < len; i++) {
			ErrProcData md = ErrProcData.parse_(lines[i]);
			if (md.SourceLine() == 0) break;	// ASSUME: java code; not interested
			if (String_.HasAtBgn(md.signatureRaw, "gplx.Err_") || String_.HasAtBgn(md.signatureRaw, "gplx.Err.")) continue;	// java includes entire stackTrace from point of creation; only care about point of throw
			list.Add(md);
		}			
		return (ErrProcData[])list.XtoAry(ErrProcData.class);
			}
	public static ErrProcData parse_(String raw) {
		ErrProcData rv = new ErrProcData().Raw_(raw);
				// ex:'gplx.Err.new_(Err.java:92)'
		int sigEnd = String_.FindFwd(raw, "("); if (sigEnd == String_.Find_none) return rv;
		rv.signatureRaw = String_.Mid(raw, 0, sigEnd);
		int filBgn = sigEnd + 1; // 1="("
		int filEnd = String_.FindFwd(raw, ":", filBgn); if (filEnd == String_.Find_none) return rv;
		rv.sourceFileRaw = String_.Mid(raw, filBgn, filEnd);
		int linBgn = filEnd + 1; // 1=":"
		int linEnd = String_.FindFwd(raw, ")", linBgn); if (linEnd == String_.Find_none) return rv;
		String linRaw = String_.Mid(raw, linBgn, linEnd);
		rv.sourceLine = Int_.parse_(linRaw);
		rv.ideAddress = String_.Concat("(", rv.sourceFileRaw, ":", Int_.Xto_str(rv.sourceLine), ")");
				return rv;
	}
	public static ErrProcData new_() {return new ErrProcData();} ErrProcData() {}
	public static final ErrProcData Null = new ErrProcData();
}
