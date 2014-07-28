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
package gplx.xowa; import gplx.*;
public interface Xot_invk {
	byte Defn_tid();
	int Src_bgn();
	int Src_end();
	boolean Frame_is_root();
	byte Frame_tid(); void Frame_tid_(byte v);
	byte[] Frame_ttl(); void Frame_ttl_(byte[] v);
	int Frame_lifetime(); void Frame_lifetime_(int v);
	boolean Rslt_is_redirect(); void Rslt_is_redirect_(boolean v);
	int Args_len();
	Arg_nde_tkn Name_tkn();
	Arg_nde_tkn Args_get_by_idx(int i);
	Arg_nde_tkn Args_eval_by_idx(byte[] src, int idx);
	Arg_nde_tkn Args_get_by_key(byte[] src, byte[] key);
}
