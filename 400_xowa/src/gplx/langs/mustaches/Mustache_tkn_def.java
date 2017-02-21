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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
class Mustache_tkn_def {
	public byte[] Variable_lhs = Dflt_variable_lhs;
	public byte[] Variable_rhs = Dflt_variable_rhs;
	public int Variable_lhs_len;
	public int Variable_rhs_len;
	public static final byte[]
	  Dflt_variable_lhs = Bry_.new_a7("{{")
	, Dflt_variable_rhs = Bry_.new_a7("}}")
	;
	public static final byte
	  Variable		= Byte_ascii.Curly_end		// {{=<% %>=}}
	, Escape_bgn	= Byte_ascii.Curly_bgn		// {{{escape}}}
	, Escape_end	= Byte_ascii.Curly_end		// {{{escape}}}
	, Section		= Byte_ascii.Hash			// {{#section}}
	, Grp_end		= Byte_ascii.Slash			// {{/section}}
	, Inverted		= Byte_ascii.Pow			// {{^inverted}}
	, Comment		= Byte_ascii.Bang			// {{!comment}}
	, Partial		= Byte_ascii.Angle_bgn		// {{>partial}}
	, Delimiter_bgn	= Byte_ascii.Eq				// {{=<% %>=}}
	, Delimiter_end	= Byte_ascii.Curly_end		// {{=<% %>=}}
	;
	public Mustache_tkn_def() {
		Variable_lhs_len = Variable_lhs.length;
		Variable_rhs_len = Variable_rhs.length;
	}
}
