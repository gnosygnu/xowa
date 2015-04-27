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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bry_rdr {
	private byte[] src; private int src_len;
	public Bry_rdr Src_(byte[] src, int src_len) {this.src = src; this.src_len = src_len; pos = 0; return this;} public Bry_rdr Src_(byte[] src) {return Src_(src, src.length);}
	public int Pos() {return pos;} public Bry_rdr Pos_(int v) {this.pos = v; return this;} private int pos;
	public boolean Pos_is_eos() {return pos == src_len;}
	public void Pos_add_one() {++pos;}
	public int Or_int() {return or_int;} public void Or_int_(int v) {or_int = v;} private int or_int = Int_.MinValue;
	public byte[] Or_bry() {return or_bry;} public void Or_bry_(byte[] v) {or_bry = v;} private byte[] or_bry;
	public int Find_fwd__pos_at_lhs(byte[] find_bry) {return Find_fwd__pos_at(find_bry, Bool_.N);}
	public int Find_fwd__pos_at_rhs(byte[] find_bry) {return Find_fwd__pos_at(find_bry, Bool_.Y);}
	public int Find_fwd__pos_at(byte[] find_bry, boolean pos_at_rhs) {
		int find_pos = Bry_finder.Find_fwd(src, find_bry, pos, src_len);
		if (pos_at_rhs) find_pos += find_bry.length;
		if (find_pos != Bry_finder.Not_found) pos = find_pos;
		return find_pos;
	}
	public int Read_int_to_semic()	{return Read_int_to(Byte_ascii.Semic);}
	public int Read_int_to_comma()	{return Read_int_to(Byte_ascii.Comma);}
	public int Read_int_to_pipe()	{return Read_int_to(Byte_ascii.Pipe);}
	public int Read_int_to_nl()		{return Read_int_to(Byte_ascii.NewLine);}
	public int Read_int_to_quote()	{return Read_int_to(Byte_ascii.Quote);}
	public int Read_int_to(byte to_char) {
		int bgn = pos;
		int rv = 0;
		int negative = 1;
		while (pos < src_len) {
			byte b = src[pos++];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					rv = (rv * 10) + (b - Byte_ascii.Num_0);
					break;
				case Byte_ascii.Dash:
					if (negative == -1)		// 2nd negative
						return or_int;		// return or_int
					else					// 1st negative
						negative = -1;		// flag negative
					break;
				default:
					return b == to_char ? rv * negative : or_int;
			}
		}
		return bgn == pos ? or_int : rv * negative;
	}
	public byte[] Read_bry_to_nl()		{return Read_bry_to(Byte_ascii.NewLine);}
	public byte[] Read_bry_to_semic()	{return Read_bry_to(Byte_ascii.Semic);}
	public byte[] Read_bry_to_pipe()	{return Read_bry_to(Byte_ascii.Pipe);}
	public byte[] Read_bry_to_quote()	{return Read_bry_to(Byte_ascii.Quote);}
	public byte[] Read_bry_to_apos()	{return Read_bry_to(Byte_ascii.Apos);}
	public byte[] Read_bry_to(byte to_char) {
		int bgn = pos;
		while (pos < src_len) {
			byte b = src[pos];
			if	(b == to_char) 
				return Bry_.Mid(src, bgn, pos++);
			else
				++pos;
		}
		return bgn == pos ? or_bry : Bry_.Mid(src, bgn, src_len);
	}
	public boolean Read_yn_to_pipe() {return Read_byte_to_pipe() == Byte_ascii.Ltr_y;}
	public byte Read_byte_to_pipe() {
		byte rv = src[pos];
		pos += 2;	// 1 for byte; 1 for pipe;
		return rv;
	}
	public double Read_double_to_pipe() {return Read_double_to(Byte_ascii.Pipe);}
	public double Read_double_to(byte to_char) {
		byte[] double_bry = Read_bry_to(to_char);
		return Double_.parse_(String_.new_ascii_(double_bry));	// double will never have utf8
	}
}