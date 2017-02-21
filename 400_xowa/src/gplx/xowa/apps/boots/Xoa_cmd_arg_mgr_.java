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
package gplx.xowa.apps.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.envs.*;
class Xoa_cmd_arg_mgr_ {
	public static String GenHdr(boolean forSourceCode, String programName, String hdr_bgn, String hdr_end) {
		String newLine = Op_sys.Lnx.Nl_str();
		String lineEnd = Op_sys.Lnx.Nl_str();
		String codeBgn = forSourceCode ? "/*" + newLine : "";
		String codeEnd = forSourceCode ? "*/" + newLine : "";
		String codeHdr = forSourceCode ? "This file is part of {0}." + newLine + newLine : "";
		String fmt = String_.Concat
			( codeBgn
			, codeHdr
			, hdr_bgn
			, "Copyright (c) 2012-2016 gnosygnu@gmail.com", newLine
			, newLine
			, "This program is free software: you can redistribute it and/or modify", lineEnd
			, "it under the terms of the GNU Affero General Public License as", lineEnd
			, "published by the Free Software Foundation, either version 3 of the", lineEnd
			, "License, or (at your option) any later version.", newLine
			, newLine
			, "This program is distributed in the hope that it will be useful,", lineEnd
			, "but WITHOUT ANY WARRANTY; without even the implied warranty of", lineEnd
			, "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the", lineEnd
			, "GNU Affero General Public License for more details.", newLine
			, newLine
			, "You should have received a copy of the GNU Affero General Public License", lineEnd
			, "along with this program.  If not, see <http://www.gnu.org/licenses/>.", newLine
			, codeEnd
			, hdr_end
			);
		return String_.Format(fmt, programName);
	}
}
