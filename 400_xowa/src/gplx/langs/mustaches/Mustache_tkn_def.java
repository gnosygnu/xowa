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
